import React, {useState} from 'react';
import TabList from '@/components/molecules/List/TabList';

interface TabsProps {
  tabs: {
    id: number;
    label: string;
    content: React.ReactNode;
  }[];
}

const Tabs = ({tabs}: TabsProps) => {
  const [activeTab, setActiveTab] = useState(tabs[0].id);

  const handleTabClick = (id: number) => {
    setActiveTab(id);
  };

  return (
    <>
      <div className="py-3 bg-white border-b">
        <TabList
          tabs={tabs}
          activeTab={activeTab}
          onClickTab={handleTabClick}
        />
      </div>
      <div>{tabs.find(tab => tab.id === activeTab)?.content}</div>
    </>
  );
};

export default Tabs;
