import {useEffect} from 'react';
import {useNavigate, useLocation, useParams} from 'react-router-dom';

import {useLeaveConsentDetail} from '@/hooks/leave-consent/useLeaveConsentDetail';
import {useDeleteLeaveConsent} from '@/hooks/leave-consent/useDeleteLeaveConsent';
import {containerHeaderClass} from '@/styles/styles';
import {getFullImageSource} from '@/utils/getFullImageSource';

import Spinner from '@/components/atoms/Loader/Spinner';
import Header from '@/components/organisms/Navigation/Header';
import UserCardItem from '@/components/molecules/Item/UserCardItem';
import DetailLabelItem from '@/components/molecules/Item/DetailLabelItem';
import DetailMultilineLabelItem from '@/components/molecules/Item/DetailMultilineLabelItem';
import ConsentSignatureCard from '@/components/organisms/Signature/ConsentSignatureCard';
import NavigationBar from '@/components/organisms/Navigation/NavigationBar';

const LeaveConsentDetail = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const userInfo = {...location.state};
  const {leaveConsentId} = useParams();
  const deleteMutation = useDeleteLeaveConsent();

  const {data, isLoading} = useLeaveConsentDetail(leaveConsentId ?? '');

  useEffect(() => {
    if (!isLoading && !data) {
      setTimeout(() => {
        navigate(-1);
      }, 1000);
    }
  }, [isLoading, data, navigate]);

  const handleLeaveConsentDelete = () => {
    if (leaveConsentId) {
      deleteMutation.mutate(leaveConsentId, {
        onSuccess: () => {
          navigate('/leave-consents');
        },
      });
    }
  };

  const options = [
    {
      text: '삭제하기',
      onClick: handleLeaveConsentDelete,
    },
  ];

  return (
    <div className="flex flex-col h-screen">
      {isLoading && <Spinner />}
      <Header title="귀가동의서" buttonType="back" />
      <div className={containerHeaderClass}>
        <UserCardItem
          profile={getFullImageSource(data?.kidPicture)}
          userName={userInfo.kidName}
          banName={userInfo.banName}
          cardType="detail"
          options={options}
        />
        <div className="space-y-5 border-b py-7 px-9">
          <DetailLabelItem
            title="날짜"
            content={data?.leaveDate}
            color="#FFC36A"
          />
          <DetailLabelItem title="시간" content={data?.leaveTime} />
          <DetailMultilineLabelItem
            title="귀가 방법"
            content={data?.leaveMethod}
          />
          <DetailMultilineLabelItem
            title="보호자"
            content={data?.guardianRelationship}
            contact={data?.guardianContact}
          />
          <DetailMultilineLabelItem
            title="비상연락처"
            content={data?.emergencyRelationship}
            contact={data?.emergencyContact}
          />
        </div>
        <ConsentSignatureCard
          text="원아의 귀가를 위 보호자에게 인도해 주세요.\n변동이 있다면 사전에 반드시 연락을 취하겠습니다."
          date={data?.signDate || ''}
          parentName={data?.parentName || ''}
          signatureUrl={data?.signUrl || ''}
        />
      </div>
      <NavigationBar />
    </div>
  );
};

export default LeaveConsentDetail;
