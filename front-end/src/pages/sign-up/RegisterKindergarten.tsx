import React, {useEffect, useState} from 'react';
import LabelInput from '@/components/atoms/Input/LabelInput';
import Button from '@/components/atoms/Button/Button';
import {useNavigate} from 'react-router-dom';
import Modal from '@/components/organisms/Modal/Modal';
import {useRecoilState} from 'recoil';
import {SignupDirectorState} from '@/recoil/atoms/signup/signupDirector';
import {useMutation} from '@tanstack/react-query';
import {postDirectorKindergarten} from '@/apis/signup/postDirectorKindergarten';
import {getMemberId} from '@/utils/userData';

// import {useGetUserInfo} from '@/hooks/user/useGetUserInfo';

declare global {
  interface Window {
    daum: any;
  }
}

interface IAddr {
  address: string;
  zonecode: string;
}

const RegisterKindergarten: React.FC = () => {
  const [isShort, setIsShort] = useState(true);
  const [addr, setAddr] = useState('');
  const [zipNo, setZipNo] = useState('');
  const [kindergartenaddrdetail, setkindergartenaddrdetail] = useState('');
  const [inputAble, setInputAble] = useState(false);
  const [kindergartenname, setKindergartenName] = useState('');
  const [kindergartentel, setKindergartenTel] = useState('');
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isStateUpdated, setIsStateUpdated] = useState(false);
  const [signupregisterkindergarten, setSignupRegisterKindergarten] =
    useRecoilState(SignupDirectorState);

  const signupKindergartenMutate = useMutation({
    mutationFn: () => {
      return postDirectorKindergarten(signupregisterkindergarten);
    },
  });

  const navigate = useNavigate();

  const handleRegisterKindergartenButtonClick = async () => {
    setSignupRegisterKindergarten(prevState => ({
      ...prevState,
      memberId: getMemberId()!,
      name: kindergartenname,
      address: addr,
      addressDetail: kindergartenaddrdetail,
      zipCode: zipNo,
      tel: kindergartentel,
    }));
    setIsStateUpdated(true);
  };
  // 원래 등록 양식이 다 입력되어야만 다음 completed창으로 갈 수 있음

  useEffect(() => {
    if (isStateUpdated) {
      const updateKindergarten = async () => {
        try {
          await signupKindergartenMutate.mutateAsync();
          console.log('원장님 가입 완료');
          navigate('/auth/signup/complete');
        } catch (error) {
          console.error('유치원 정보 못 보냈어요:', error);
        }
      };
      updateKindergarten();
      setIsStateUpdated(false);
    }
  }, [
    isStateUpdated,
    navigate,
    signupKindergartenMutate,
    signupregisterkindergarten,
  ]);

  const closeModal = () => {
    setIsModalOpen(false);
  };
  // 클릭 이벤트 선언
  const onClickAddr = () => {
    new window.daum.Postcode({
      oncomplete: function (data: IAddr) {
        setAddr(data.address);
        setZipNo(data.zonecode);
        setkindergartenaddrdetail(''); // 이미 적혀있던 상세주소 초기화
        setInputAble(true);
        document.getElementById('kindergartenaddrdetail')?.focus();
      },
    }).open();
  };

  // kakao 지도를 띄우는 방법
  useEffect(() => {
    const script = document.createElement('script');
    script.src =
      '//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js';
    script.async = true;
    script.onload = () => {
      console.log('Script loaded');
    };
    document.body.appendChild(script);

    const handleResize = () => {
      const mainContainer = document.querySelector('.main-container');
      if (mainContainer) {
        setIsShort(mainContainer.clientHeight + 56 < window.innerHeight);
      }
    };

    handleResize(); // Initial check
    window.addEventListener('resize', handleResize); // Add resize event listener

    return () => window.removeEventListener('resize', handleResize); // Cleanup event listener on component unmount
  }, []);

  return (
    <div className="min-h-full ">
      <div className="flex flex-col items-center w-full h-full px-10 py-6 space-y-8 main-container">
        <div className="flex items-center justify-center text-lg">
          <p>유치원을 등록해주세요</p>
          <img src="/icons/kid.png" alt="Kid Icon" className="w-5/12" />
        </div>
        <div className="w-full space-y-8">
          <LabelInput
            label="이름"
            value={kindergartenname}
            placeholder="유치원 이름을 적어주세요"
            onChange={e => setKindergartenName(e.target.value)}
          />

          <div className="space-y-2">
            <div className="grid grid-cols-12 gap-x-2">
              <div className="col-span-8 ">
                <LabelInput
                  label="주소"
                  placeholder="클릭하여 주소를 입력해주세요"
                  value={addr}
                  onChange={onClickAddr}
                />
              </div>
              <div className="flex items-end col-span-4">
                <Button label="검색" onClick={onClickAddr} />
              </div>
            </div>

            <div>
              <LabelInput
                value={zipNo}
                label="우편번호"
                disabled={true}
                readOnly
              />
              {/* <LabelInput
                value={addr}
                label="도로명주소"
                disabled={true}
                readOnly
              /> */}
              <LabelInput
                label="상세주소"
                value={kindergartenaddrdetail}
                disabled={!inputAble}
                onChange={e => setkindergartenaddrdetail(e.target.value)}
              />
            </div>
          </div>

          <div className="w-full space-y-4"></div>
          <LabelInput
            label="원 번호"
            value={kindergartentel}
            placeholder="원 번호를 적어주세요"
            onChange={e => setKindergartenTel(e.target.value)}
          />
        </div>
      </div>
      <div className={`py-10 w-full flex items-center justify-center px-4`}>
        <Button
          label="유치원 등록"
          onClick={handleRegisterKindergartenButtonClick}
        />
      </div>
      {isModalOpen && (
        <Modal isOpen={true}>
          <Modal.Header title="경고" />
          <Modal.Body>
            <div className="text-sm text-center text-gray-300">
              <p>내용을 다 작성해주세요</p>
            </div>
          </Modal.Body>
          <Modal.Background onClick={closeModal} />
        </Modal>
      )}
    </div>
  );
};

export default RegisterKindergarten;
