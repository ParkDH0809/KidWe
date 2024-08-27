import React, {useState, useEffect} from 'react';
import Button from '@/components/atoms/Button/Button';
import {useNavigate} from 'react-router-dom';
import SelectMain from '@/components/molecules/DropdownButton/SelectMain';
import Select from '@/components/molecules/DropdownButton/Select';
import {useMutation} from '@tanstack/react-query';
import {useRecoilState} from 'recoil';
import {SignupTeacherState} from '@/recoil/atoms/signup/signupTeacher';
import {SignupGuardianState} from '@/recoil/atoms/signup/signupGuardian';
import {getKindergartenBan} from '@/apis/signup/getKindergartenBan';
import {GetKindergarten} from '@/types/kindergarten/GetKindergarten';
import {getMemberRole} from '@/utils/userData';
import {toast, ToastContainer} from 'react-toastify';
import {postTeacher} from '@/apis/signup/postTeacher';
const KindergartenBan: React.FC = () => {
  const [signupTeacher, setSignupTeacher] = useRecoilState(SignupTeacherState);
  const [signupGuardian, setSignupGuardian] =
    useRecoilState(SignupGuardianState);
  const [role, setRole] = useState<string | null>(getMemberRole());
  const [isRendering, setIsRendering] = useState(true);
  const [selectedBanId, setSelectedBanId] = useState<number | null>(null);
  const [selectedBanName, setSelectedBanName] = useState<string>('');
  const navigate = useNavigate();
  const [isTeacherUpdated, setIsTeacherUpdated] = useState(false);
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [kindergartenData, setKindergartenData] = useState<
    Partial<
      Pick<GetKindergarten, 'name' | 'address' | 'addressDetail' | 'bans'>
    >
  >({});

  const kindergartenId =
    role === 'ROLE_TEACHER'
      ? signupTeacher.kindergartenId
      : signupGuardian.dto.kindergartenId;

  const signupTeacherMutate = useMutation({
    mutationFn: async () => {
      console.log();
      return postTeacher(signupTeacher);
    },
    onSuccess: data => {
      if (data === '성공') {
        navigate('/auth/signup/complete');
      } else if (data === '실패') {
        toast.error('회원가입에 실패하였습니다.');
      } else {
        toast.error(data);
      }
    },
  });

  const handleCompletedButtonClick = () => {
    if (selectedBanId !== null) {
      // 여기서 선생님인지 학부모인지에 따라서 axios 보내는 곳이 달라짐!!!!
      // 선생님이라면 axios 부를거고, 학부모라면 banId를 업데이트!
      if (role === 'ROLE_GUARDIAN') {
        setSignupGuardian(prevState => ({
          ...prevState,
          dto: {
            ...prevState.dto,
            banId: selectedBanId,
            banName: selectedBanName,
            kindergartenName: kindergartenData.name,
          },
        }));

        navigate('/auth/signup/kindergarten/child');
      } else if (role === 'ROLE_TEACHER') {
        console.log('선생님일 때 signupteacher update');
        setSignupTeacher(prevState => ({
          ...prevState,
          banId: selectedBanId,
        }));
        setIsTeacherUpdated(true);
      }
    } else {
      toast.error('반을 선택해주세요');
    }
  };
  const handleBackButtonClick = () => {
    navigate('/auth/signup/kindergarten/search');
  };

  const handleChange = (value: string) => {
    const numericValue = parseInt(value, 10);
    if (Array.isArray(kindergartenData.bans)) {
      console.log('kindergartenData', kindergartenData);
      console.log('kindergartenData.bans', kindergartenData.bans);

      // selectedBanName이 안 돼 ㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠ
      const selectedBan = kindergartenData.bans?.find(
        ban => ban.id == numericValue
      );
      const selectedBanName = selectedBan ? selectedBan.name : '';

      setSelectedBanId(numericValue);
      setSelectedBanName(selectedBanName);
      console.log('선택된BanId', value);
      console.log('선택된Ban', selectedBan);
      console.log('선택된BanName', selectedBanName);
    }
  };

  useEffect(() => {
    if (isTeacherUpdated) {
      signupTeacherMutate.mutate();
      setIsTeacherUpdated(false);
    }
    if (isRendering) {
      const fetchBans = async () => {
        try {
          if (kindergartenId) {
            const response = await getKindergartenBan(kindergartenId);
            setKindergartenData(response);
          } else {
            toast.error('유치원을 다시 선택해주세요', {
              onClose: () => navigate('/auth/signup/kindergarten/search'),
            });
          }
        } catch (error) {
          console.error('유치원 데이터를 가져오는 데 실패했습니다:', error);
        }
      };

      fetchBans();
      setIsRendering(false);
    }
  }, [
    isRendering,
    navigate,
    kindergartenId,
    role,
    isTeacherUpdated,
    signupTeacherMutate,
  ]);

  return (
    <div className="flex flex-col items-center w-full h-full min-h-full px-5 py-6 pt-20 space-y-16">
      <ToastContainer
        position="top-center"
        autoClose={1000}
        hideProgressBar={true}
        pauseOnFocusLoss
        limit={1}
      />
      <p className="text-lg">
        본인/원아 유치원이 맞는지 확인하고, 반을 선택해주세요.
      </p>
      <div className="flex flex-col items-center space-y-8">
        <h2 className="text-lg text-primary">{kindergartenData.name}</h2>
        <p className="text-sm">
          {kindergartenData.address}&nbsp;
          {kindergartenData.addressDetail}
        </p>
      </div>
      <div className="flex space-x-2">
        <SelectMain size="medium" label="옵션" onChange={handleChange}>
          {kindergartenData.bans?.map(ban => (
            <Select.Option
              key={ban.id}
              text={ban.name}
              id={ban.id.toString()}
            />
          ))}
        </SelectMain>
      </div>

      <div className="flex justify-between px-8 space-x-4">
        <Button label="맞아요" onClick={handleCompletedButtonClick} />
        <Button label="아니에요" onClick={handleBackButtonClick} />
      </div>
    </div>
  );
};

export default KindergartenBan;
