import {useEffect} from 'react';
import {useNavigate, useLocation, useParams} from 'react-router-dom';

import {useMedicationDetail} from '@/hooks/medication/useMedicationDetail';
import {useDeleteMedication} from '@/hooks/medication/useDeleteMedication';
import {containerHeaderClass} from '@/styles/styles';
import {useLoading} from '@/hooks/loading/useLoading';
import {getFullImageSource} from '@/utils/getFullImageSource';

import Header from '@/components/organisms/Navigation/Header';
import UserCardItem from '@/components/molecules/Item/UserCardItem';
import DetailLabelItem from '@/components/molecules/Item/DetailLabelItem';
import ConsentSignatureCard from '@/components/organisms/Signature/ConsentSignatureCard';
import NavigationBar from '@/components/organisms/Navigation/NavigationBar';

const MedicationDetail = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const userInfo = {...location.state};
  const {medicationId} = useParams();
  const deleteMutation = useDeleteMedication();

  const {data, isLoading} = useMedicationDetail(medicationId ?? '');
  useLoading(isLoading);

  useEffect(() => {
    if (!isLoading && !data) {
      setTimeout(() => {
        navigate(-1);
      }, 1000);
    }
  }, [isLoading, data, navigate]);

  const medicationDetails = data
    ? [
        {title: '증상', content: data.symptom, color: '#FFC36A'},
        {title: '이름', content: data.medicineName, color: '#FFEC9E'},
        {title: '종류', content: data.type, color: '#FFEC9E'},
        {title: '용량', content: data.numberOfDoses, color: '#FFEC9E'},
        {title: '횟수', content: data.capacity, color: '#FFEC9E'},
        {
          title: '시간',
          content: data.medicationExecuteTime,
          color: '#FFEC9E',
        },
        {title: '보관', content: data.storageMethod, color: '#FFEC9E'},
        {title: '비고', content: data.others, color: '#FFEC9E'},
        {
          title: '사진',
          content: 'image',
          imageUrl: data.medicineUrl,
          color: '#FFEC9E',
        },
      ].filter(item => item.content && item.content.trim() !== '')
    : [];

  const handleMedicationDelete = () => {
    if (medicationId) {
      deleteMutation.mutate(medicationId, {
        onSuccess: () => {
          navigate('/medications');
        },
      });
    }
  };

  const options = [
    {
      text: '삭제하기',
      onClick: handleMedicationDelete,
    },
  ];

  return (
    <div className="flex flex-col h-screen">
      <Header title={'투약의뢰서'} buttonType="back" />
      <div className={containerHeaderClass}>
        <UserCardItem
          profile={getFullImageSource(data?.kidPicture)}
          userName={userInfo.kidName}
          banName={userInfo.banName}
          cardType="detail"
          options={options}
        />
        <div className="space-y-5 border-b py-7 px-9">
          {medicationDetails.map((item, index) => (
            <DetailLabelItem
              key={index}
              color={item.color}
              title={item.title}
              content={item.content === 'image' ? '' : item.content}
              imageUrl={item.imageUrl}
            />
          ))}
        </div>
        <ConsentSignatureCard
          text="학부모가 요청한 투약의뢰서에 따른 책임은\n 학부모에게 있습니다."
          date={data?.medicationExecuteDate || ''}
          parentName={data?.parentName || ''}
          signatureUrl={data?.signUrl || ''}
        />
      </div>
      <NavigationBar />
    </div>
  );
};

export default MedicationDetail;
