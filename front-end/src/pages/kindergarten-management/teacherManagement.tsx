import Header from '@/components/organisms/Navigation/Header';
import NavigationBar from '@/components/organisms/Navigation/NavigationBar';
import Tabs from '@/components/organisms/Navigation/Tabs';
import {containerNavigatorClass} from '@/styles/styles';
import BanTeacherPendingView from '@/components/organisms/Ban/BanTeacherPendingView';
import BanTeacherAcceptView from '@/components/organisms/Ban/BanTeacherAcceptView';
const TeacherManagement = () => {
  return (
    <div
      className={`${containerNavigatorClass} flex flex-col h-screen w-full max-w-full`}
    >
      <Header title="교사 관리" buttonType="back" />
      <Tabs
        tabs={[
          {
            id: 0,
            label: '승인된 교사',
            content: <BanTeacherAcceptView />, // 선생님 중에서 ACCEPT 된 사람
          },
          {
            id: 1,
            label: '대기중인 교사',
            content: <BanTeacherPendingView />, // 선생님 중에서 PENDING 인사람
          },
        ]}
      />
      <NavigationBar />
    </div>
  );
};

export default TeacherManagement;
