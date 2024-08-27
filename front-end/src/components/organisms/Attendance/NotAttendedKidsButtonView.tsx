import UserCardItemWithButton from '@/components/molecules/Item/UserCardItemWithButton';
import {useRef, useState} from 'react';
import Modal from '@/components/organisms/Modal/Modal';
import ModalPortal from '@/components/organisms/Modal/ModalPortal';
import TextArea from '@/components/atoms/Input/TextArea';
import XSmallButton from '@/components/atoms/Button/XSmallButton';
import type {GetAttendance} from '@/types/attendance/GetAttendance';
import {usePutAttendanceInfo} from '@/hooks/attendance/usePutAttendanceInfo';
import {PutAttendance} from '@/types/attendance/PutAttendance';
import {useGetDateBySearchParam} from '@/hooks/useGetDateBySearchParam';
import {getBanId} from '@/utils/userData';
import {toast} from 'react-toastify';
import {getFullImageSource} from '@/utils/getFullImageSource';
import DirectorSelectItem from '@/components/organisms/Medication/DirectorSelectItem';
import {RoleItem} from '@/enum/roleItem';

interface AttendedKidsButtonViewProps {
  attendances?: GetAttendance[];
  onClickSelect?: () => void;
  memberRole: RoleItem;
  onBanChange: (value: number) => void;
}

const AttendedKidsButtonView = ({
  attendances,
  onClickSelect,
  memberRole,
  onBanChange,
}: AttendedKidsButtonViewProps) => {
  const date = useGetDateBySearchParam();

  const inputRef = useRef<HTMLTextAreaElement>(null);
  const kidRef = useRef<GetAttendance>();

  const attendanceMutate = usePutAttendanceInfo(getBanId()!);

  const [isNegativeModalOpen, setIsNegativeModalOpen] = useState(false);

  const handleOpenNegativeModal = () => {
    setIsNegativeModalOpen(true);
  };

  const handleCloseNegativeModal = () => {
    setIsNegativeModalOpen(false);
  };

  const handleSubmitNegativeModal = async () => {
    // 로직 처리
    if (kidRef.current !== undefined) {
      const {kidId} = kidRef.current;
      const [year, month, day] = date
        .format('YYYY-MM-DD')
        .split('-')
        .map(Number);
      await attendanceMutate.mutateAsync(
        {
          year,
          month,
          day,
          kidIds: [kidId],
          attendedToday: 'ABSENCE',
          reason: inputRef.current ? inputRef.current.value : '',
        },
        {
          onSuccess: () => {
            toast.info('결석 처리 되었습니다.');
          },
        }
      );
      setIsNegativeModalOpen(false);
    }
  };

  const submitAttend = ({
    year,
    month,
    day,
    kidIds,
    attendedToday,
    reason,
  }: PutAttendance) => {
    attendanceMutate.mutate(
      {
        year,
        month,
        day,
        kidIds,
        attendedToday,
        reason,
      },
      {
        onSuccess: () => {
          toast.info('출석 처리 되었습니다.');
        },
      }
    );
  };

  return (
    <>
      <div className="flex items-center justify-between px-8 py-2 border-b border-gray-200 min-h-14">
        <div className="flex items-center justify-between w-full">
          <XSmallButton
            label="선택"
            onClick={() => {
              onClickSelect?.();
            }}
            variant="negative"
          />
          <DirectorSelectItem
            memberRole={memberRole}
            onBanChange={onBanChange}
          />
        </div>
      </div>
      <div className="flex flex-col items-center justify-center w-full h-fit">
        {attendances &&
          attendances.map(attendance => (
            <UserCardItemWithButton
              key={attendance.attendanceId}
              profile={attendance?.image}
              userName={attendance.kidName}
              negativeLabel="결석"
              onClickNegative={() => {
                kidRef.current = attendance;
                handleOpenNegativeModal();
              }}
              positiveLabel="출석"
              onClickPositive={() =>
                submitAttend({
                  year: date.get('year'),
                  month: date.get('month') + 1,
                  day: date.get('date'),
                  attendedToday: 'ATTENDANCE',
                  kidIds: [attendance.kidId],
                  reason: '',
                })
              }
            />
          ))}
      </div>
      <ModalPortal>
        <Modal isOpen={isNegativeModalOpen}>
          <Modal.Header title="출결내용 작성" />
          <Modal.Body>
            <div className="flex flex-col items-center justify-center py-10 h-44">
              <TextArea ref={inputRef} placeholder="출결내용 작성" />
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
            label="확인"
            onClick={handleSubmitNegativeModal}
            round="full"
            size="large"
            variant="positive"
          ></Modal.BottomButton>
          <Modal.Background
            onClick={handleCloseNegativeModal}
          ></Modal.Background>
        </Modal>
      </ModalPortal>
    </>
  );
};

export default AttendedKidsButtonView;
