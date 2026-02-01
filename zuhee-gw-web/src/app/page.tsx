'use client';

import EditorWrapper from '@/features/editor/components/EditorWrapper';
import { OrgChartDialog } from '@/features/org-chart/components/OrgChartDialog';
import { useState } from 'react';

export default function Home() {
  const [selectedDept, setSelectedDept] = useState<string>('미선택');

  return (
    <main className="container mx-auto p-8 space-y-8">
      <header className="flex justify-between items-center border-b pb-4">
        <h1 className="text-3xl font-bold">Zuhee-GW Dashboard</h1>
        <div className="flex items-center gap-4">
          <span className="text-sm text-slate-500">선택된 부서: {selectedDept}</span>
          <OrgChartDialog onSelect={(dept) => setSelectedDept(dept.name)} />
        </div>
      </header>

      <section className="bg-white rounded-lg shadow-sm border p-6">
        <h2 className="text-xl font-semibold mb-4">문서 편집기</h2>
        <EditorWrapper />
      </section>
    </main>
  );
}
