import React, {useState} from 'react';
import Button from '@/components/atoms/Button/Button';
import {useNavigate} from 'react-router-dom';
import KidWe from '@/assets/kid-we.svg';
import {getMemberRole} from '@/utils/userData';

const Pending: React.FC = () => {
  const navigate = useNavigate();

  const handleGoLoginButtonClick = () => {
    navigate('/auth/login');
  };
  const [memberRole, setMemberRole] = useState(getMemberRole());
  const callmemberRole = memberRole === 'ROLE_GUARDIAN' ? '선생님' : '원장님';

  return (
    <div className="flex flex-col items-center w-full h-full min-h-screen px-10 py-6 space-y-8">
      <div className="flex items-center justify-center ">
        <div className="flex items-center justify-center mb-7">
          <img src={KidWe} />
        </div>
      </div>
      <div className="text-lg font-medium">
        <p>{callmemberRole}의 승인을 기다리고 있습니다!</p>
      </div>

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

export default Pending;
