import React, {useEffect, useState} from 'react';
import Button from '@/components/atoms/Button/Button';
import {useNavigate} from 'react-router-dom';
import KindergartenCard from '@/components/atoms/KindergartenCard';
import LabelInput from '@/components/atoms/Input/LabelInput';
import AllergyView from '@/components/organisms/Food/AllergyView';
import {ALLERGIES, Allergy} from '@/constants/allergy';
import {useRecoilState} from 'recoil';
import {useMutation} from '@tanstack/react-query';
import {SignupGuardianState} from '@/recoil/atoms/signup/signupGuardian';
import {postGuardian} from '@/apis/signup/postGuardian';
import {toast, ToastContainer} from 'react-toastify';
import dayjs, {Dayjs} from 'dayjs';
import CalendarButton from '@/components/molecules/Button/CalendarButton';
import {Gender} from '@/enum/gender';
import {genderLabels} from '@/constants/genderLabel';
import ImageUploadButton from '@/components/molecules/Button/ImageUploadButton';
import noProfile from '@/assets/image/no-profile.png';
// const genderItems = [
//   {value: 'MALE', label: '남아'},
//   {value: 'FEMALE', label: '여아'},
// ];

const KindergartenChild: React.FC = () => {
  const [childname, setChildname] = useState('');
  const [childbirth, setChildbirth] = useState(dayjs());
  const [image, setImage] = useState<string | ArrayBuffer | null>(null);
  const [selectedGender, setSelectedGender] = useState<string>('');
  const [datas, setDatas] = useState<Allergy[]>(ALLERGIES);
  const [userpicture, setPicture] = useState<string | undefined>();
  const [imageFile, setImageFile] = useState<File | null>(null);
  const navigate = useNavigate();
  const [isStateUpdated, setIsStateUpdated] = useState(false);
  const [signupGuardian, setSignupGuardian] =
    useRecoilState(SignupGuardianState);
  const handleGenderChange = (gender: Gender) => {
    setSelectedGender(gender);
  };
  const handleFileChange = (file: File) => {
    setImageFile(file);
  };
  const handlePreviewChange = (preview: string) => {
    setPicture(preview);
  };

  const signupChildMutate = useMutation({
    mutationFn: async () => {
      const {
        dto: {banName, ...restDto},
        ...restGuardian
      } = signupGuardian;
      const postsignupGuardian = {
        ...restGuardian,
        dto: restDto,
      };
      console.log(postsignupGuardian);
      console.log(banName);
      return postGuardian(postsignupGuardian, imageFile);
    },
    onSuccess: () => {
      navigate('/auth/signup/complete');
    },
    onError: error => {
      toast.error(`회원가입에 실패했습니다: ${error.message}`);
    },
  });

  const handleCompletedButtonClick = () => {
    const formattedBirthday = childbirth.format('YYYY-MM-DD');
    const checkedAllergies = datas
      .filter(allergy => allergy.isChecked)
      .map(allergy => allergy.value);
    console.log(checkedAllergies);
    setSignupGuardian(prevState => ({
      ...prevState,
      dto: {
        ...prevState.dto,
        kidName: childname,
        birthday: formattedBirthday,
        gender: selectedGender as Gender,
        allergies: checkedAllergies,
      },
      picture: typeof image === 'string' ? image : '',
    }));
    setIsStateUpdated(true);
  };

  const handleAllergyChange = (updatedDatas: Allergy[]) => {
    setDatas(updatedDatas);
  };

  const handleDateChange = (date: Dayjs) => {
    setChildbirth(date);
  };

  useEffect(() => {
    if (
      signupGuardian.dto.banId == 0 ||
      signupGuardian.dto.kindergartenId == 0
    ) {
      toast.error('잘못 들어오셨습니다!', {
        onClose: () => navigate('/auth/signup/kindergarten/search'),
      });
    }

    if (isStateUpdated) {
      signupChildMutate.mutate();
      setIsStateUpdated(false);
    }
  }, [navigate, isStateUpdated, signupGuardian, signupChildMutate]);
  return (
    <div className="flex flex-col w-full h-full max-h-full px-5 space-y-5">
      <ToastContainer
        position="top-center"
        autoClose={1000}
        hideProgressBar={true}
        pauseOnFocusLoss
        limit={1}
      />
      <div className="flex space-x-2 h-fit">
        <KindergartenCard
          kindergartenName={`${signupGuardian.dto.kindergartenName} - ${signupGuardian.dto.banName || ''}`}
        />
        {/* `${getKindergartenNameById(signupGuardian.dto.kindergartenId)} - ${signupGuardian.dto.banName || ''}` */}
      </div>
      <div className="flex flex-col flex-grow gap-5 overflow-y-auto">
        <div className="flex items-center justify-center space-x-2 h-fit w-fit">
          <div className="flex flex-col items-center">
            {/* <input
            type="file"
            onChange={handleImageChange}
            className="hidden mb-4"
            id="fileInput"
          /> */}
            {/* <div
            className="flex items-center justify-center w-32 h-32 border-2 border-gray-400 border-dashed rounded-lg cursor-pointer"
            onClick={() => document.getElementById('fileInput')?.click()}
          >
            {image ? (
              <img
                src={image as string}
                alt="프로필 미리보기"
                className="object-cover w-full h-full rounded-lg "
              />
            ) : (
              <span className="text-gray-500">이미지 선택</span>
            )}
          </div> */}
            <ImageUploadButton
              userPicture={userpicture === undefined ? noProfile : userpicture}
              onChangeFile={handleFileChange}
              onChangePreview={handlePreviewChange}
            />
          </div>
          <div className="flex flex-col space-y-2">
            <LabelInput
              value={childname}
              placeholder="이름을 적어주세요"
              onChange={e => setChildname(e.target.value)}
            />
            {/* <LabelInput
            value={childbirth ? childbirth.format('YYYY-MM-DD') : ''}
            placeholder="클릭하여 생일을 입력해주세요"
            onChange={handleBirthChange}
          /> */}
            {/* <CustomCalendar
            defaultDate={childbirth}
            onChange={handleDateChange}
            activeStartDate={false}
          /> */}
            <CalendarButton
              render={() => (
                <LabelInput
                  value={childbirth ? childbirth.format('YYYY-MM-DD') : ''}
                  placeholder="클릭하여 생일을 입력해주세요"
                  onChange={() => {}}
                />
              )}
              defaultDate={childbirth}
              onClick={handleDateChange}
            />

            <div className="flex justify-center space-x-2">
              {Object.entries(genderLabels).map(([key, label]) => (
                <Button
                  key={key}
                  variant={selectedGender === key ? 'positive' : 'negative'}
                  label={label}
                  onClick={() => handleGenderChange(key as Gender)}
                  size="small"
                />
              ))}
            </div>
          </div>
        </div>
        <div className="flex flex-col items-center justify-center border rounded-lg">
          <h2 className="mt-2">알레르기</h2>
          <AllergyView datas={datas} onChangeData={handleAllergyChange} />
        </div>
      </div>
      <div className="h-fit">
        <Button
          label="입력 완료"
          size="large"
          onClick={handleCompletedButtonClick}
        />
      </div>
    </div>
  );
};

export default KindergartenChild;
