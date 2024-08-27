import Modal from '@/components/organisms/Modal/Modal';
import ModalPortal from '@/components/organisms/Modal/ModalPortal';
import {useGetTempStorageList} from '@/hooks/announcement/useGetTempStorageList';
import {getMemberId} from '@/utils/userData';

interface TempStorageModalProps {
  isOpen: boolean;
  onClose: () => void;
  onSelect: (selectedItem: string) => void;
}

const TempStorageModal = ({
  isOpen,
  onClose,
  onSelect,
}: TempStorageModalProps) => {
  const {data: tempStorageList, isLoading} = useGetTempStorageList(
    getMemberId()!
  );

  console.log(tempStorageList);

  const handleSelect = (itemId: number) => {
    onSelect(itemId.toString());
    onClose();
  };

  return (
    <ModalPortal>
      <Modal isOpen={isOpen}>
        <Modal.Header title="임시 보관함" />
        <Modal.Body>
          {isLoading ? (
            <p>Loading...</p>
          ) : (
            <ul className="space-y-2">
              {tempStorageList?.map(item => (
                <li
                  key={item.announcementId}
                  onClick={() => handleSelect(item.announcementId)}
                  className="p-2 rounded cursor-pointer hover:bg-gray-100"
                >
                  <p>{item.title}</p>
                  <p className="text-xs text-gray-500">{item.storedDate}</p>
                </li>
              ))}
            </ul>
          )}
        </Modal.Body>
        <Modal.BottomButton
          label="닫기"
          onClick={onClose}
          size="large"
          round="full"
          variant="negative"
        />
      </Modal>
    </ModalPortal>
  );
};

export default TempStorageModal;
