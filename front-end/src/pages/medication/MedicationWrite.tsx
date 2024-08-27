import {useEffect, useState, ChangeEvent} from 'react';
import {useNavigate} from 'react-router-dom';
import {useRecoilState, useResetRecoilState} from 'recoil';

import {usePostMedication} from '@/hooks/medication/usePostMedication';
import {medicationFormState} from '@/recoil/atoms/medication/medicationFormState';
import {formatDateToYMD} from '@/utils/dayjsPlugin';
import {TimeOption, timeOptionValues} from '@/enum/medication/timeOption';
import {
  STORAGE_OPTIONS,
  MEDICATION_LABELS,
  MEDICATION_PLACEHOLDERS,
  MEDICATION_MESSAGES,
} from '@/constants/medication';
import {DATE_OPTIONS} from '@/constants/dateOptions';
import {containerHeaderClass} from '@/styles/styles';
import {getKidId, getMemberId} from '@/utils/userData';

import Spinner from '@/components/atoms/Loader/Spinner';
import Header from '@/components/organisms/Navigation/Header';
import RadioCircleButton from '@/components/atoms/CheckBox/RadioCircleButton';
import LabelInput from '@/components/atoms/Input/LabelInput';
import ImageUpload from '@/components/atoms/Input/ImageUpload';
import Select from '@/components/molecules/DropdownButton/Select';
import RadioCheckBoxButton from '@/components/atoms/CheckBox/RadioCheckBoxButton';
import ConsentSection from '@/components/organisms/Signature/ConsentSection';
import AreaDivider from '@/components/atoms/Divider/AreaDivider';
import ButtonBar from '@/components/organisms/Navigation/ButtonBar';
import {useGetUserInfo} from '@/hooks/my-page/useGetUserInfo';

const MedicationWrite = () => {
  const navigate = useNavigate();
  const {mutate, isPending} = usePostMedication();
  const [formState, setFormState] = useRecoilState(medicationFormState);
  const resetFormState = useResetRecoilState(medicationFormState);
  const [selectedTimeOption, setSelectedTimeOption] = useState('');
  const [medicineImage, setMedicineImage] = useState<File | null>(null);
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

  // 투약일
  const handleDateChange = (option: string) => {
    setFormState(prev => ({...prev, medicationExecuteDueDate: option}));
  };

  // 보관방법
  const handleStorageChange = (option: string) => {
    setFormState(prev => ({...prev, storageMethod: option}));
  };

  // 투약시간
  const handleTimeChange = (option: string) => {
    setSelectedTimeOption(option);
    if (option === TimeOption.Etc) {
      setFormState(prev => ({...prev, medicationExecuteTime: ''}));
    } else {
      setFormState(prev => ({...prev, medicationExecuteTime: option}));
    }
  };

  const handleFormSubmit = () => {
    const formData = new FormData();

    formData.append(
      'dto',
      new Blob([JSON.stringify(formState)], {type: 'application/json'})
    );

    if (medicineImage !== null) {
      formData.append('medicine', medicineImage);
    }

    if (signImage !== null) {
      formData.append('sign', signImage);
    }

    mutate({kidId: getKidId()!, formData, memberId: getMemberId()!});
    navigate('/medications');
  };

  return (
    <div className="h-screen">
      {isPending && <Spinner />}
      <Header title="투약의뢰서" buttonType="back" />
      <div className={`${containerHeaderClass}`}>
        <div className="py-8 space-y-5 px-9">
          <div className="space-y-2">
            <p>{MEDICATION_LABELS.medicationExecuteDueDate}</p>
            <RadioCircleButton
              options={DATE_OPTIONS}
              selectedOption={formState.medicationExecuteDueDate}
              onChange={handleDateChange}
            />
          </div>
          <LabelInput
            label={MEDICATION_LABELS.symptom}
            name="symptom"
            value={formState.symptom}
            onChange={handleInputChange}
            placeholder={MEDICATION_PLACEHOLDERS.symptom}
          />
          <ImageUpload onChange={setMedicineImage} />
        </div>
        <AreaDivider />
        <div className="py-8 space-y-5 px-9">
          <LabelInput
            label={MEDICATION_LABELS.medicineName}
            name="medicineName"
            value={formState.medicineName}
            onChange={handleInputChange}
            placeholder={MEDICATION_PLACEHOLDERS.medicineName}
          />
          <LabelInput
            label={MEDICATION_LABELS.type}
            name="type"
            value={formState.type}
            onChange={handleInputChange}
            placeholder={MEDICATION_PLACEHOLDERS.type}
          />
          <LabelInput
            label={MEDICATION_LABELS.capacity}
            name="capacity"
            value={formState.capacity}
            onChange={handleInputChange}
            placeholder={MEDICATION_PLACEHOLDERS.capacity}
          />
          <LabelInput
            label={MEDICATION_LABELS.numberOfDoses}
            name="numberOfDoses"
            value={formState.numberOfDoses}
            onChange={handleInputChange}
            placeholder={MEDICATION_PLACEHOLDERS.numberOfDoses}
          />
          <div className="space-y-2">
            <p>시간</p>
            <Select label="선택" onChange={handleTimeChange}>
              {timeOptionValues.map(option => (
                <Select.Option key={option} text={option} />
              ))}
            </Select>
            {selectedTimeOption === TimeOption.Etc && (
              <LabelInput
                name="medicationExecuteTime"
                value={formState.medicationExecuteTime}
                onChange={handleInputChange}
                placeholder={MEDICATION_PLACEHOLDERS.etc}
              />
            )}
          </div>
          <div className="space-y-2">
            <p>보관</p>
            <RadioCheckBoxButton
              options={STORAGE_OPTIONS}
              selectedOption={formState.storageMethod}
              onChange={handleStorageChange}
            />
          </div>
          <LabelInput
            label={MEDICATION_LABELS.others}
            name="others"
            value={formState.others}
            onChange={handleInputChange}
            placeholder={MEDICATION_PLACEHOLDERS.others}
          />
        </div>
        <AreaDivider />
        <div className="py-8 px-9">
          <ConsentSection
            text={MEDICATION_MESSAGES.parentConsent}
            date={formatDateToYMD(formState.medicationExecuteDueDate)}
            parentName={userInfo?.name ?? ''}
            onClick={setSignImage}
          />
        </div>
      </div>
      <ButtonBar
        label={MEDICATION_LABELS.submit}
        variant={isValid ? 'positive' : 'negative'}
        disabled={!isValid}
        onClick={handleFormSubmit}
      />
    </div>
  );
};

export default MedicationWrite;
