import {useNavigate} from 'react-router-dom';
import NotificationButton from '@/components/atoms/Button/NotificationButton';
import KindergartenCard from '@/components/atoms/KindergartenCard';
import UserCardItem from '@/components/molecules/Item/UserCardItem';
import HomeMenu from '@/components/organisms/Content/HomeMenu';
import MemoShortcut from '@/components/organisms/Content/MemoShortcut';
import NavigationBar from '@/components/organisms/Navigation/NavigationBar';
import {useGetUserInfo} from '@/hooks/my-page/useGetUserInfo';
import {getKidId, getMemberId} from '@/utils/userData';
import {ROLE_NAMES} from '@/constants/roleNames';
import {RoleItem} from '@/enum/roleItem';
import {isTeacher} from '@/utils/auth/isTeacher';
import kidweLogo from '@/assets/kidwe-logo.png';
import {getFullImageSource} from '@/utils/getFullImageSource';
import {useGetKidInfo} from '@/hooks/my-page/useGetKidInfo';
import {isGuardian} from '@/utils/auth/isGuardian';
import {useEffect, useState} from 'react';

const Home = () => {
  const {data: userInfo} = useGetUserInfo(getMemberId()!);
  const {data: kidInfo} = useGetKidInfo(getKidId()!);
  const [name, setName] = useState('');

  useEffect(() => {
    if (isGuardian()) {
      setName(kidInfo?.name ?? '');
    } else {
      setName(userInfo?.name ?? '');
    }
  }, [kidInfo, userInfo]);

  const navigate = useNavigate();
  const handleUserCardItemClick = () => {
    navigate('/my-page');
  };

  return (
    <>
      <div className="min-h-screen px-5 border-t pb-[8rem] bg-secondary">
        <div className="flex justify-between pt-4">
          <div>
            <img className="w-32" src={kidweLogo} />
          </div>
          <NotificationButton />
        </div>
        <div className="mb-2">
          <KindergartenCard
            kindergartenName={userInfo?.kindergartenName ?? ''}
          />
        </div>
        <div className="space-y-3 ">
          <div onClick={handleUserCardItemClick}>
            <UserCardItem
              profile={getFullImageSource(userInfo?.picture)}
              userName={`${name} ${ROLE_NAMES[userInfo?.role as RoleItem] ?? ''}`}
              cardType="arrow"
            />
          </div>
          <HomeMenu />
          {isTeacher() && <MemoShortcut />}
        </div>
      </div>
      <NavigationBar />
    </>
  );
};

export default Home;
