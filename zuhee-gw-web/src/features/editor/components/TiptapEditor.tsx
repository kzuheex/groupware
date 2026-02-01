'use client';

import { useEditor, EditorContent } from '@tiptap/react';
import StarterKit from '@tiptap/starter-kit';
import { Table } from '@tiptap/extension-table';
import { TableRow } from '@tiptap/extension-table-row';
import { TableCell } from '@tiptap/extension-table-cell';
import { TableHeader } from '@tiptap/extension-table-header';
import { Button } from '@/components/ui/button';

const TiptapEditor = () => {
  const editor = useEditor({
    extensions: [
      StarterKit,
      Table.configure({
        resizable: true,
      }),
      TableRow,
      TableHeader,
      TableCell,
    ],
    content: '<p>Hello Zuhee-GW!</p>',
    editorProps: {
      attributes: {
        class: 'prose prose-sm sm:prose lg:prose-lg xl:prose-2xl m-5 focus:outline-none border p-4 min-h-[400px]',
      },
    },
  });

  if (!editor) {
    return null;
  }

  return (
    <div className="flex flex-col gap-2">
      <div className="flex flex-wrap gap-2 border-b p-2 bg-slate-50">
        <Button
          variant="outline"
          size="sm"
          onClick={() => editor.chain().focus().toggleBold().run()}
          className={editor.isActive('bold') ? 'bg-slate-200' : ''}
        >
          Bold
        </Button>
        <Button
          variant="outline"
          size="sm"
          onClick={() => editor.chain().focus().insertTable({ rows: 3, cols: 3, withHeaderRow: true }).run()}
        >
          Insert Table
        </Button>
        <Button
          variant="outline"
          size="sm"
          onClick={() => editor.chain().focus().addColumnAfter().run()}
        >
          Add Column
        </Button>
        <Button
          variant="outline"
          size="sm"
          onClick={() => editor.chain().focus().addRowAfter().run()}
        >
          Add Row
        </Button>
        <Button
          variant="outline"
          size="sm"
          onClick={() => editor.chain().focus().deleteTable().run()}
        >
          Delete Table
        </Button>
      </div>
      <EditorContent editor={editor} />
    </div>
  );
};

export default TiptapEditor;
