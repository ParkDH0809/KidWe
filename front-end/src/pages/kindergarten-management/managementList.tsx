import NavigationBar from '@/components/organisms/Navigation/NavigationBar';
import Header from '@/components/organisms/Navigation/Header';
import {containerHeaderClass} from '@/styles/styles';
import MyPageItem from '@/components/organisms/MyPage/MyPageItem';
import {useNavigate} from 'react-router-dom';
import KindergartenCard from '@/components/atoms/KindergartenCard';
import {useGetKindergartenInfo} from '@/hooks/schedule/useGetKindergartenInfo';
import {useLoading} from '@/hooks/loading/useLoading';

import {getMemberRole, getKindergartenId} from '@/utils/userData';

const ManagementList = () => {
  const navigate = useNavigate();

  const isDirector = getMemberRole() === 'ROLE_DIRECTOR' ? true : false;

  const {data, isLoading} = useGetKindergartenInfo(getKindergartenId()!);
  console.log(data, 'data는 어떨까+유치원 정보를 보기위함');
  useLoading(isLoading);

  const handleTeacherManagementClick = () => {
    navigate('/kindergarten/teacher');
  };
  const handleChildManagementClick = () => {
    navigate('/kindergarten/child');
  };
  const handleKindergartenManagementClick = () => {
    navigate('/kindergarten/setting');
  };
  const handleBanManagementClick = () => {
    navigate('/kindergarten/ban');
  };
  return (
    <div className={`${containerHeaderClass} w-full h-screen`}>
      <Header title="유치원 관리" buttonType="close" />
      <div className="h-full overflow-y-auto bg-[#F8F8F8]">
        <div className="p-5 mb-5 bg-white">
          <KindergartenCard kindergartenName={data ? data.name : ''} />
        </div>
        <div className="mb-5">
          {isDirector && (
            <MyPageItem
              label="교사 관리"
              onClick={handleTeacherManagementClick}
            />
          )}
          <MyPageItem label="원생 관리" onClick={handleChildManagementClick} />
        </div>
        <div className="mb-5">
          <MyPageItem
            label="유치원 설정"
            onClick={handleKindergartenManagementClick}
          />
          <MyPageItem label="반 설정" onClick={handleBanManagementClick} />
        </div>
      </div>
      <NavigationBar />
    </div>
  );
};

export default ManagementList;
