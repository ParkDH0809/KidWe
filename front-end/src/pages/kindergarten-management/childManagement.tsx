import Header from '@/components/organisms/Navigation/Header';
import NavigationBar from '@/components/organisms/Navigation/NavigationBar';
import {containerHeaderClass} from '@/styles/styles';
import Tabs from '@/components/organisms/Navigation/Tabs';
import BanChildPendingView from '@/components/organisms/Ban/BanChildPendingView';
import BanChildAcceptView from '@/components/organisms/Ban/BanChildAcceptView';
const ChildManagement = () => {
  return (
    <div
      className={`${containerHeaderClass} flex flex-col h-screen w-full max-w-full`}
    >
      <Header title="원생 관리" buttonType="back" />
      <Tabs
        tabs={[
          {
            id: 0,
            label: '승인된 아이',
            content: <BanChildAcceptView />, // 아이 중에서 ACCEPT 된 사람
          },
          {
            id: 1,
            label: '대기중인 아이',
            content: <BanChildPendingView />, // 아이 중에서 PENDING 인사람
          },
        ]}
      />
      <NavigationBar />
    </div>
  );
};

export default ChildManagement;
