import XSmallButton from '@/components/atoms/Button/XSmallButton';
import {GetAttendance} from '@/types/attendance/GetAttendance';
import ModalPortal from '@/components/organisms/Modal/ModalPortal';
import Modal from '@/components/organisms/Modal/Modal';
import {useEffect, useState} from 'react';
import CheckListItem from '@/components/organisms/Check/CheckListItem';
import {usePutAttendanceInfo} from '@/hooks/attendance/usePutAttendanceInfo';
import {useGetDateBySearchParam} from '@/hooks/useGetDateBySearchParam';
import {getBanId} from '@/utils/userData';
import {toast} from 'react-toastify';
import {getFullImageSource} from '@/utils/getFullImageSource';
import {isDirector} from '@/utils/auth/isDirector';

interface AttendedKidsSelectViewProps {
  attendances?: GetAttendance[];
  onClickButton?: () => void;
  ban: number | null;
}

interface CheckedGetAttendance extends GetAttendance {
  isChecked: boolean;
}

const AttendedKidsSelectView = ({
  attendances,
  onClickButton,
  ban,
}: AttendedKidsSelectViewProps) => {
  const date = useGetDateBySearchParam();
  // checked attendances
  const [checkedAttendances, setCheckedAttendances] =
    useState<CheckedGetAttendance[]>();
  useEffect(() => {
    if (attendances !== undefined) {
      setCheckedAttendances(
        attendances.map(val => {
          return {...val, isChecked: false};
        })
      );
    }
  }, [attendances]);

  const handleAttendancesCheck = (id: number) => {
    setCheckedAttendances(
      checkedAttendances?.map(val =>
        val.attendanceId === id ? {...val, isChecked: !val.isChecked} : {...val}
      )
    );
  };

  const handleAllCheck = () => {
    setCheckedAttendances(
      checkedAttendances?.map(val => {
        return {...val, isChecked: true};
      })
    );
  };

  const [isModalOpen, setIsModalOpen] = useState(false);

  const handleCloseModal = () => {
    setIsModalOpen(false);
  };

  const handleOpenModal = () => {
    setIsModalOpen(true);
  };

  const putMutate = usePutAttendanceInfo(isDirector() ? ban : getBanId()!);

  const handleSubmit = () => {
    if (date !== undefined) {
      const selectedKids = checkedAttendances
        ?.filter(value => value.isChecked)
        .map(value => value.kidId);
      if (selectedKids !== undefined) {
        if (selectedKids.length === 0) {
          toast.info('선택된 학생이 없습니다.');
          onClickButton?.();
        } else {
          putMutate.mutate(
            {
              kidIds: selectedKids,
              year: date.get('year'),
              month: date.get('month') + 1,
              day: date.get('date'),
              attendedToday: 'NOTHING',
              reason: '',
            },
            {
              onSuccess: () => {
                toast.info('일괄 미출석 처리 되었습니다.');
                onClickButton?.();
              },
            }
          );
        }
      }
    }
  };

  return (
    <>
      <div className="flex items-center justify-between px-8 py-2 border-b border-gray-200 min-h-14">
        <div>
          <XSmallButton
            label="전체선택"
            variant="negative"
            onClick={handleAllCheck}
          />
        </div>
        <div className="space-x-2">
          <XSmallButton
            label="취소"
            variant="negative"
            onClick={onClickButton}
          />
          <XSmallButton
            label="출석취소"
            variant="positive"
            onClick={handleOpenModal}
          />
        </div>
      </div>
      <div className="flex flex-col items-center justify-center w-full h-fit">
        {checkedAttendances &&
          checkedAttendances.map(attendance => (
            <CheckListItem
              key={attendance.attendanceId}
              src={getFullImageSource(attendance?.image)}
              text={attendance.kidName}
              isChecked={attendance.isChecked}
              onClick={() => handleAttendancesCheck(attendance.attendanceId)}
            />
          ))}
      </div>
      <ModalPortal>
        <Modal isOpen={isModalOpen}>
          <Modal.Header title="출결내용 작성" />
          <Modal.Body>
            <div className="flex flex-col items-center justify-center py-10">
              <p>체크한 원생들에 대해</p>
              <p>출석 처리를 취소하시겠습니까?</p>
            </div>
          </Modal.Body>
          <Modal.BottomButton
            label="취소"
            onClick={handleCloseModal}
            round="full"
            size="large"
            variant="negative"
          ></Modal.BottomButton>
          <Modal.BottomButton
            label="확인"
            onClick={handleSubmit}
            round="full"
            size="large"
            variant="positive"
          ></Modal.BottomButton>
          <Modal.Background onClick={handleCloseModal}></Modal.Background>
        </Modal>
      </ModalPortal>
    </>
  );
};

export default AttendedKidsSelectView;
