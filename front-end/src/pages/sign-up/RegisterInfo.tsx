import React, {useEffect, useState} from 'react';
import LabelInput from '@/components/atoms/Input/LabelInput';
import Button from '@/components/atoms/Button/Button';
import {useRecoilState} from 'recoil';
import {useNavigate} from 'react-router-dom';
import {Signup} from '@/recoil/atoms/signup/Signup';
import {useMutation} from '@tanstack/react-query';
import {postSignup} from '@/apis/signup/postSignup';
import {toast} from 'react-toastify';

import ImageUploadButton from '@/components/molecules/Button/ImageUploadButton';
import {SignupFormState} from '@/types/signup/SignupFormState';
import {signupPictureState} from '@/recoil/atoms/signup/signupPicture';
import noProfile from '@/assets/image/no-profile.png';

const RegisterInfo = () => {
  const [signupregister, setSignupRegister] =
    useRecoilState<SignupFormState>(Signup);
  const [isStateUpdated, setIsStateUpdated] = useState(false);
  const [username, setUsername] = useState('');
  const [useremail, setUseremail] = useState('');
  const [userpassword, setUserpassword] = useState('');
  const [userpassword2, setUserpassword2] = useState('');
  const [usertel, setUsertel] = useState('');
  const [userpicture, setPicture] = useState<string | undefined>();
  const [imageFile, setImageFile] = useRecoilState(signupPictureState);
  const [iswrongpasswordtype, setIsWrongPasswordType] = useState(false);
  const [iswrongemailtype, setIsWrongEmailType] = useState(false);
  const [ismissingvalue, setIsMissingValue] = useState(false);
  const navigate = useNavigate();

  // 이메일 정규 표현식 패턴
  // 1글자이상 @ 1글자이상 . 2글자이상
  const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

  const signupMutate = useMutation({
    mutationFn: async ({
      signupregister,
      imageFile,
    }: {
      signupregister: SignupFormState;
      imageFile: File | null;
    }) => {
      return postSignup(signupregister, imageFile);
    },
    onSuccess: () => {
      navigate('/auth/login');
    },
    onError: error => {
      toast.error(`회원가입에 실패했습니다: ${error.message}`);
    },
  });

  const handleRegisterButtonClick = () => {
    {
      // 회원가입 유효성 검사
      if (
        // 이름과 전화번호가 빈 칸인지 확인
        !username.trim() ||
        !useremail.trim() ||
        !userpassword.trim() ||
        !userpassword2.trim() ||
        !usertel.trim()
      ) {
        console.log('값을 적어주세요');
        setIsMissingValue(true);
        return;
      } else {
        setIsMissingValue(false);
      }

      if (!emailPattern.test(useremail)) {
        setIsWrongEmailType(true);
        console.log('이메일을 다시 입력해주세요');
        return;
      } else {
        setIsWrongEmailType(false);
      }

      if (userpassword !== userpassword2) {
        setIsWrongPasswordType(true);
        console.log('비밀번호 확인이 틀립니다.');
        return;
      } else {
        setIsWrongPasswordType(false);
      }
    }

    signupMutate.mutate({
      signupregister: {
        ...signupregister,
        dto: {
          ...signupregister.dto,
          name: username,
          tel: usertel,
          email: useremail,
          password: userpassword,
        },
      },
      imageFile,
    });

    // setSignupRegister(prevState => ({
    //   ...prevState,
    //   dto: {
    //     ...prevState.dto,
    //     name: username,
    //     tel: usertel,
    //     email: useremail,
    //     password: userpassword,
    //   },
    //   picture: userpicture,
    // }));
    // setIsStateUpdated(true);
  };

  // const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
  //   const file = e.target.files?.[0];
  //   if (file) {
  //     const reader = new FileReader();
  //     // reader.onloadend = () => {
  //     //   setPicture(reader.result);
  //     // };
  //     reader.readAsDataURL(file);
  //     console.log('file', file);
  //     console.log('reader', reader);
  //   }
  // };
  const handleFileChange = (file: File) => {
    setImageFile(file);
  };
  const handlePreviewChange = (preview: string) => {
    setPicture(preview);
  };

  useEffect(() => {
    // 혹시 새로고침하여서 role이 없는 경우 redirect
    if (!signupregister.dto.role) {
      toast.error('역할부터 정해주세요', {
        onClose: () => navigate('/auth/signup/role'),
      });
    }
    // if (isStateUpdated) {
    //   // 여기서 info랑 pircture 타입 맞춰서 인수로 주기@@@@@@@@@@@@@@@@@@@@
    //   // 0813 19:10 기준으로 하기
    //   // 다시 보니 여기서는 인수를 넘기는게 없고, 어차피 api 에서 인수 바꿔줄거임
    //   signupMutate.mutate();
    //   setIsStateUpdated(false);
    // }
  }, [isStateUpdated, navigate, signupMutate, signupregister]);

  return (
    <div>
      <div className="flex flex-col items-center w-full h-full min-h-full px-10 py-6 space-y-8 main-container">
        <div className="flex items-center justify-center">
          <div className="flex items-center justify-center ">
            <img src="/icons/kidwe.png" alt="Kidwe Icon" />
          </div>
        </div>
        {/* <div className="flex flex-col items-center">
          <input
            type="file"
            onChange={handleImageChange}
            className="hidden mb-4"
            id="fileInput"
          />
          <div
            className="flex items-center justify-center w-24 h-24 border-2 border-gray-400 border-dashed rounded-lg cursor-pointer"
            onClick={() => document.getElementById('fileInput')?.click()}
          >
            {userpicture ? (
              <img
                src={userpicture as string}
                alt="프로필 미리보기"
                className="object-cover w-full h-full rounded-lg "
              />
            ) : (
              <span className="text-gray-500">이미지 선택</span>
            )}
          </div>
        </div> */}
        <ImageUploadButton
          userPicture={userpicture === undefined ? noProfile : userpicture}
          onChangeFile={handleFileChange}
          onChangePreview={handlePreviewChange}
        />
        <div className="w-full space-y-8">
          <LabelInput
            label="이름"
            value={username}
            placeholder="이름을 적어주세요"
            onChange={e => setUsername(e.target.value)}
          />
          <LabelInput
            label="이메일"
            value={useremail}
            placeholder="ex)kidwe@kidwe.com"
            onChange={e => setUseremail(e.target.value)}
          />
          {iswrongemailtype && (
            <p className="text-red-600">이메일을 다시 입력해주세요</p>
          )}
          <div className="w-full space-y-4">
            <LabelInput
              label="비밀번호"
              value={userpassword}
              placeholder="비밀번호를 적어주세요"
              type="password"
              onChange={e => setUserpassword(e.target.value)}
            />
            {iswrongpasswordtype && (
              <p className="text-red-600">비밀번호를 다시 입력해주세요</p>
            )}
            <LabelInput
              value={userpassword2}
              placeholder="비밀번호를 다시 적어주세요"
              type="password"
              onChange={e => setUserpassword2(e.target.value)}
            />
          </div>
          <LabelInput
            label="전화번호"
            value={usertel}
            placeholder="전화번호를 적어주세요"
            onChange={e => setUsertel(e.target.value)}
          />
          {ismissingvalue && (
            <p className="text-lg text-red-600">빈 칸을 채워주세요!</p>
          )}
        </div>
      </div>
      <div className={`w-full flex justify-center px-4`}>
        <Button label="회원 가입" onClick={handleRegisterButtonClick} />
      </div>
    </div>
  );
};

export default RegisterInfo;
