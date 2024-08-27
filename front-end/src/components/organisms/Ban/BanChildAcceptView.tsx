import UserCardItem from '@/components/molecules/Item/UserCardItem';
import {useState, useEffect} from 'react';
import {useNavigate} from 'react-router-dom';
import Modal from '@/components/organisms/Modal/Modal';
import ModalPortal from '@/components/organisms/Modal/ModalPortal';
import {getChildAccept} from '@/apis/management/getChildAccept';
import {ChildInfo} from '@/types/management/ChildInfo';
import {getKindergartenId} from '@/utils/userData';
import {putChildDecline} from '@/apis/management/putChildDecline';
import {getFullImageSource} from '@/utils/getFullImageSource';
const BanChildAcceptView = () => {
  const [childAcceptList, setChildAcceptList] = useState<ChildInfo[]>([]);
  const [kindergartenId, setKindergartenId] = useState(getKindergartenId());
  const [isNegativeModalOpen, setIsNegativeModalOpen] = useState(false);
  const [selectedChildId, setSelectedChildId] = useState<number | null>(null); // 선택된 아이의 ID를 저장하는 상태

  const handleDeclineChild = async (kidId: number) => {
    try {
      await putChildDecline(kidId);
      if (kindergartenId) {
        const updatedList = await getChildAccept(kindergartenId);
        setChildAcceptList(updatedList);
      }
      handleCloseNegativeModal();
    } catch (error) {
      console.error('Failed to Decline Child', error);
    }
  };
  const handleOpenNegativeModal = (kidId: number) => {
    setSelectedChildId(kidId);
    setIsNegativeModalOpen(true);
  };

  const handleCloseNegativeModal = () => {
    setIsNegativeModalOpen(false);
    setSelectedChildId(null);
  };

  useEffect(() => {
    const fetchChildAcceptList = async () => {
      try {
        console.log('kindergartenId', kindergartenId);
        if (kindergartenId) {
          const response = await getChildAccept(kindergartenId);
          setChildAcceptList(response);
        }
      } catch (error) {
        console.error('Failed to fetch child accept list', error);
      }
    };
    fetchChildAcceptList();
  }, [kindergartenId]);

  return (
    <div>
      {childAcceptList.map(child => (
        <UserCardItem
          key={child.kidId}
          cardType="detail"
          profile={getFullImageSource(undefined)}
          userName={child.name}
          options={[
            {
              text: '반 수정',
              onClick: () => {
                console.log('반 수정');
              },
            },
            {
              text: '퇴원 처리',
              onClick: () => {
                handleOpenNegativeModal(child.kidId);
              },
            },
          ]}
        />
      ))}
      <ModalPortal>
        <Modal isOpen={isNegativeModalOpen}>
          <Modal.Header title="알림" />
          <Modal.Body>
            <div className="flex flex-col items-center justify-center py-10 h-44">
              <p>대기 중인 아이를 거절하시겠습니까?</p>
              <p>
                거절한 요청은 복구할 수 <b>없습니다</b>
              </p>
            </div>
          </Modal.Body>
          <Modal.BottomButton
            label="취소"
            onClick={handleCloseNegativeModal}
            round="full"
            size="large"
            variant="negative"
          ></Modal.BottomButton>
          <Modal.BottomButton
            label="거절"
            onClick={() => {
              if (selectedChildId !== null) {
                handleDeclineChild(selectedChildId); // 선택된 교사의 ID로 거절 처리
              }
            }}
            round="full"
            size="large"
            variant="positive"
          ></Modal.BottomButton>
          <Modal.Background
            onClick={handleCloseNegativeModal}
          ></Modal.Background>
        </Modal>
      </ModalPortal>
    </div>
  );
};

export default BanChildAcceptView;
