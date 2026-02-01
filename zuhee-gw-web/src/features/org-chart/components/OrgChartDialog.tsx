'use client';

import { useState } from 'react';
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogTrigger } from '@/components/ui/dialog';
import { Button } from '@/components/ui/button';
import { ChevronRight, ChevronDown, Building2 } from 'lucide-react';

interface Department {
  code: string;
  name: string;
  children?: Department[];
}

const mockData: Department[] = [
  {
    code: 'ROOT',
    name: '주희그룹',
    children: [
      {
        code: 'DEPT001',
        name: '경영지원본부',
        children: [
          { code: 'DEPT002', name: '인사팀' },
          { code: 'DEPT003', name: '재무팀' },
        ],
      },
      {
        code: 'DEPT004',
        name: '기술개발본부',
        children: [
          { code: 'DEPT005', name: '플랫폼개발팀' },
          { code: 'DEPT006', name: '솔루션개발팀' },
        ],
      },
    ],
  },
];

const DeptNode = ({ dept, onSelect }: { dept: Department; onSelect: (dept: Department) => void }) => {
  const [isOpen, setIsOpen] = useState(true);
  const hasChildren = dept.children && dept.children.length > 0;

  return (
    <div className="ml-4">
      <div className="flex items-center gap-2 py-1 cursor-pointer hover:bg-slate-100 px-2 rounded" onClick={() => onSelect(dept)}>
        {hasChildren ? (
          <span onClick={(e) => { e.stopPropagation(); setIsOpen(!isOpen); }}>
            {isOpen ? <ChevronDown className="w-4 h-4" /> : <ChevronRight className="w-4 h-4" />}
          </span>
        ) : (
          <span className="w-4" />
        )}
        <Building2 className="w-4 h-4 text-slate-500" />
        <span className="text-sm">{dept.name}</span>
      </div>
      {isOpen && hasChildren && (
        <div>
          {dept.children?.map((child) => (
            <DeptNode key={child.code} dept={child} onSelect={onSelect} />
          ))}
        </div>
      )}
    </div>
  );
};

export const OrgChartDialog = ({ onSelect }: { onSelect: (dept: Department) => void }) => {
  const [open, setOpen] = useState(false);

  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <DialogTrigger asChild>
        <Button variant="outline">부서 선택</Button>
      </DialogTrigger>
      <DialogContent className="sm:max-w-[425px]">
        <DialogHeader>
          <DialogTitle>조직도</DialogTitle>
        </DialogHeader>
        <div className="py-4 max-h-[400px] overflow-y-auto">
          {mockData.map((dept) => (
            <DeptNode
              key={dept.code}
              dept={dept}
              onSelect={(selected) => {
                onSelect(selected);
                setOpen(false);
              }}
            />
          ))}
        </div>
      </DialogContent>
    </Dialog>
  );
};
