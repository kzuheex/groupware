'use client';

import dynamic from 'next/dynamic';

const TiptapEditor = dynamic(() => import('./TiptapEditor'), {
  ssr: false,
  loading: () => <div className="h-[400px] border animate-pulse bg-slate-100" />,
});

export default TiptapEditor;
