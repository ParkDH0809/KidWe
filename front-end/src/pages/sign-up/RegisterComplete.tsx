import React, {useState} from 'react';
import Button from '@/components/atoms/Button/Button';
import {useNavigate} from 'react-router-dom';
// import {getMemberRole} from '@/utils/userData';
import KidWe from '@/assets/kid-we.svg';
// import {ROLE_NAMES} from '@/constants/roleNames';
import {useNotification} from '@/hooks/notification/useNotification';

const RegisterComplete: React.FC = () => {
  const navigate = useNavigate();
  // const [role, setRole] = useState<string | null>(getMemberRole());

  const handleGoLoginButtonClick = () => {
    navigate('/auth/login');
  };

  // const rolename =
  //   ROLE_NAMES[role] === '학부모' ? '학부모님' : ROLE_NAMES[role];
  // const {requestPermissionAndRegister} = useNotification();

  return (
    <div className="flex flex-col items-center w-full h-full min-h-full px-10 py-6 space-y-8">
      <div className="flex items-center justify-center ">
        <div className="flex items-center justify-center mb-7">
          <img src={KidWe} />
        </div>
      </div>
      <div className="text-lg font-medium">
        {/* <p>{rolename} 키위에 가입해주셔서 감사합니다!</p> */}
        <p>좋은 서비스로 보답하겠습니다!</p>
      </div>
      {/* <Button label="알림 허용" onClick={requestPermissionAndRegister} /> */}

      <div className="absolute w-1/2 bottom-8">
        <Button
          label="우리 유치원 가기"
          size="large"
          onClick={handleGoLoginButtonClick}
        />
      </div>
    </div>
  );
};

export default RegisterComplete;
