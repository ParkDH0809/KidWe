import {useEffect, useState, ChangeEvent} from 'react';
import {useNavigate} from 'react-router-dom';
import {useRecoilState, useResetRecoilState} from 'recoil';

import {usePostLeaveConsent} from '@/hooks/leave-consent/usePostLeaveConsent';
import {leaveConsentFormState} from '@/recoil/atoms/leave-consent/leaveConsentFormState';
import {formatDateToYMD} from '@/utils/dayjsPlugin';
import {
  LEAVECONSENT_LABELS,
  LEAVECONSENT_PLACEHOLDERS,
  LEAVECONSENT_MESSAGES,
} from '@/constants/leave-consent';
import {DATE_OPTIONS} from '@/constants/dateOptions';
import {containerHeaderClass} from '@/styles/styles';
import {getKidId, getMemberId} from '@/utils/userData';

import Spinner from '@/components/atoms/Loader/Spinner';
import Header from '@/components/organisms/Navigation/Header';
import RadioCircleButton from '@/components/atoms/CheckBox/RadioCircleButton';
import CustomTimePicker from '@/components/molecules/InputForm/CustomTimePicker';
import LabelInput from '@/components/atoms/Input/LabelInput';
import ConsentSection from '@/components/organisms/Signature/ConsentSection';
import AreaDivider from '@/components/atoms/Divider/AreaDivider';
import ButtonBar from '@/components/organisms/Navigation/ButtonBar';
import {useGetUserInfo} from '@/hooks/my-page/useGetUserInfo';

const LeaveConsentnWrite = () => {
  const navigate = useNavigate();
  const {mutate, isPending} = usePostLeaveConsent();
  const [formState, setFormState] = useRecoilState(leaveConsentFormState);
  const resetFormState = useResetRecoilState(leaveConsentFormState);
  const [signImage, setSignImage] = useState<File | null>(null);
  const [isValid, setIsValid] = useState(false);
  const {data: userInfo} = useGetUserInfo(getMemberId()!);

  // 모든 필드가 채워졌는지 검사하여 유효성 업데이트
  useEffect(() => {
    const allFieldsFilled =
      Object.values(formState).every(value => value.trim() !== '') &&
      signImage !== null;
    setIsValid(allFieldsFilled);
  }, [formState, signImage]);

  // 컴포넌트가 언마운트될 때 상태 초기화
  useEffect(() => {
    return () => {
      resetFormState();
      setSignImage(null);
    };
  }, [resetFormState]);

  // 입력 값 변경
  const handleInputChange = (e: ChangeEvent<HTMLInputElement>) => {
    const {name, value} = e.target;
    setFormState(prev => ({
      ...prev,
      [name]: value,
    }));
  };

  // 귀가일
  const handleDateChange = (option: string) => {
    setFormState(prev => ({...prev, leaveDate: option}));
  };

  // 귀가 시간
  const handleTimeChange = (value: string) => {
    setFormState(prev => ({...prev, leaveTime: value}));
  };

  const handleFormSubmit = () => {
    const formData = new FormData();

    formData.append(
      'dto',
      new Blob([JSON.stringify(formState)], {type: 'application/json'})
    );

    if (signImage !== null) {
      formData.append('sign', signImage);
    }

    mutate({kidId: getKidId()!, formData, memberId: getMemberId()!});
    navigate('/leave-consents');
  };

  return (
    <div className="h-screen">
      {isPending && <Spinner />}
      <Header title="귀가동의서" buttonType="back" />
      <div className={`${containerHeaderClass}`}>
        <div className="py-8 space-y-5 px-9">
          <div className="space-y-2">
            <p>{LEAVECONSENT_LABELS.leaveDate}</p>
            <RadioCircleButton
              options={DATE_OPTIONS}
              selectedOption={formState.leaveDate}
              onChange={handleDateChange}
            />
          </div>
          <div className="space-y-2">
            <p>{LEAVECONSENT_LABELS.leaveTime}</p>
            <CustomTimePicker
              value={formState.leaveTime}
              onChange={handleTimeChange}
            />
          </div>

          <LabelInput
            label={LEAVECONSENT_LABELS.leaveMethod}
            name="leaveMethod"
            value={formState.leaveMethod}
            onChange={handleInputChange}
            placeholder={LEAVECONSENT_PLACEHOLDERS.leaveMethod}
          />
        </div>
        <AreaDivider />
        <div className="py-8 space-y-5 px-9">
          <div>
            <LabelInput
              label={LEAVECONSENT_LABELS.guardian}
              name="guardianRelationship"
              value={formState.guardianRelationship}
              onChange={handleInputChange}
              placeholder={LEAVECONSENT_PLACEHOLDERS.relationship}
            />
            <LabelInput
              name="guardianContact"
              value={formState.guardianContact}
              onChange={handleInputChange}
              placeholder={LEAVECONSENT_PLACEHOLDERS.contact}
            />
          </div>
          <div>
            <LabelInput
              label={LEAVECONSENT_LABELS.emergency}
              name="emergencyRelationship"
              value={formState.emergencyRelationship}
              onChange={handleInputChange}
              placeholder={LEAVECONSENT_PLACEHOLDERS.relationship}
            />
            <LabelInput
              name="emergencyContact"
              value={formState.emergencyContact}
              onChange={handleInputChange}
              placeholder={LEAVECONSENT_PLACEHOLDERS.contact}
            />
          </div>
        </div>
        <AreaDivider />
        <div className="py-8 px-9">
          <ConsentSection
            text={LEAVECONSENT_MESSAGES.parentConsent}
            date={formatDateToYMD(formState.leaveDate)}
            parentName={userInfo?.name ?? ''}
            onClick={setSignImage}
          />
        </div>
      </div>
      <ButtonBar
        label={LEAVECONSENT_LABELS.submit}
        variant={isValid ? 'positive' : 'negative'}
        disabled={!isValid}
        onClick={handleFormSubmit}
      />
    </div>
  );
};

export default LeaveConsentnWrite;
