import {containerHeaderClass} from '@/styles/styles';
import Header from '@/components/organisms/Navigation/Header';
import Button from '@/components/atoms/Button/Button';
import MyPageUpdateView from '@/components/organisms/MyPage/MyPageUpdateView';
import NavigationBar from '@/components/organisms/Navigation/NavigationBar';
import {usePatchUserInfo} from '@/hooks/my-page/usePatchUserInfo';
import type {PatchUserInfo} from '@/types/user/PatchUserInfo';
import {patchUserInfoState} from '@/recoil/atoms/my-page/userInfo';
import {useRecoilState, useRecoilValue} from 'recoil';
import {useEffect, useState} from 'react';
import {useGetUserInfo} from '@/hooks/my-page/useGetUserInfo';
import {useNavigate} from 'react-router-dom';
import {patchUserPictureState} from '@/recoil/atoms/my-page/userPicture';
import {getMemberId} from '@/utils/userData';

const MyPageUpdate = () => {
  const navigate = useNavigate();

  const userMutation = usePatchUserInfo(getMemberId()!);

  // 페이지 불러올 때 추가로 data fetch한 후 set 필요
  const {data} = useGetUserInfo(getMemberId()!);
  const [patchUserInfo, setPatchUserInfo] =
    useRecoilState<PatchUserInfo>(patchUserInfoState);
  const userPicture = useRecoilValue(patchUserPictureState);

  useEffect(() => {
    if (data !== undefined) {
      const {id, name, tel, picture} = data;
      setPatchUserInfo({
        dto: {id, name, tel, password: ''},
        picture: picture,
      });
    }
  }, [data, setPatchUserInfo]);

  const handleClickButton = () => {
    userMutation.mutate(
      {info: patchUserInfo, picture: userPicture},
      {
        onSuccess: () => navigate('/my-page'),
      }
    );
  };

  const [isValid, setIsValid] = useState(false);
  const handleIsValid = (value: boolean) => {
    setIsValid(value);
  };

  return (
    <div
      className={`${containerHeaderClass} justify-center h-screen bg-white flex flex-col px-10`}
    >
      <Header title="정보 변경" buttonType="back" />
      <div className="flex-grow overflow-auto ">
        <MyPageUpdateView onChangeValid={handleIsValid} />
      </div>
      <div className="box-border w-full px-3 py-5 h-fit">
        <Button
          label="변경"
          size="large"
          variant={isValid ? 'positive' : 'negative'}
          onClick={() => handleClickButton()}
          disabled={!isValid}
        />
      </div>
      <NavigationBar />
    </div>
  );
};

export default MyPageUpdate;
