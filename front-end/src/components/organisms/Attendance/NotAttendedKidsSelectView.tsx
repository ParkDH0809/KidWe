import CheckListItem from '@/components/organisms/Check/CheckListItem';
import ModalPortal from '@/components/organisms/Modal/ModalPortal';
import Modal from '@/components/organisms/Modal/Modal';
import {useEffect, useState} from 'react';
import XSmallButton from '@/components/atoms/Button/XSmallButton';
import type {GetAttendance} from '@/types/attendance/GetAttendance';
import {usePutAttendanceInfo} from '@/hooks/attendance/usePutAttendanceInfo';
import {getBanId} from '@/utils/userData';
import {useGetDateBySearchParam} from '@/hooks/useGetDateBySearchParam';
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
  onClickButton: onClickTabChangeButton,
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

  const [isPositiveModalOpen, setIsPositiveModalOpen] = useState(false);

  const handleClosePositiveModal = () => {
    setIsPositiveModalOpen(false);
  };

  const handleOpenPositiveModal = () => {
    setIsPositiveModalOpen(true);
  };

  const submitMutate = usePutAttendanceInfo(isDirector() ? ban : getBanId()!);
  const submitCheckedList = () => {
    if (checkedAttendances !== undefined) {
      const [year, month, day] = date
        .format('YYYY-MM-DD')
        .split('-')
        .map(Number);
      const selectedList: number[] = checkedAttendances
        ?.filter(value => value.isChecked)
        .map(value => value.kidId);

      if (selectedList.length === 0) {
        toast.info('선택된 학생이 없습니다.');
        setIsPositiveModalOpen(false);
        onClickTabChangeButton?.();
      } else {
        // 로직 처리 성공시
        submitMutate.mutate(
          {
            year,
            month,
            day,
            attendedToday: 'ATTENDANCE',
            kidIds: selectedList,
            reason: '',
          },
          {
            onSuccess: () => {
              toast.info('일괄 출석 처리 되었습니다.');
              setIsPositiveModalOpen(false);
              onClickTabChangeButton?.();
            },
            onError: () => {
              // error handling
            },
          }
        );
      }
    }
  };

  return (
    <>
      <div className="flex items-center justify-between px-8 py-2 border-b border-gray-200 min-h-14">
        <div>
          <XSmallButton
            label="전체선택"
            onClick={() => handleAllCheck()}
            variant="negative"
          />
        </div>
        <div className="space-x-2">
          <XSmallButton
            label="취소"
            onClick={() => {
              onClickTabChangeButton?.();
            }}
            variant="negative"
          />
          <XSmallButton
            label="출석"
            onClick={() => handleOpenPositiveModal()}
            variant="positive"
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
        <Modal isOpen={isPositiveModalOpen}>
          <Modal.Header title="출결내용 작성" />
          <Modal.Body>
            <div className="flex flex-col items-center justify-center py-10">
              <p>체크한 원생들에 대해</p>
              <p>출석 처리를 하시겠습니까?</p>
            </div>
          </Modal.Body>
          <Modal.BottomButton
            label="취소"
            onClick={handleClosePositiveModal}
            round="full"
            size="large"
            variant="negative"
          ></Modal.BottomButton>
          <Modal.BottomButton
            label="확인"
            onClick={submitCheckedList}
            round="full"
            size="large"
            variant="positive"
          ></Modal.BottomButton>
          <Modal.Background
            onClick={handleClosePositiveModal}
          ></Modal.Background>
        </Modal>
      </ModalPortal>
    </>
  );
};

export default AttendedKidsSelectView;
