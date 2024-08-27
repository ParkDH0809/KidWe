import {GetAttendance} from '@/types/attendance/GetAttendance';
import XSmallButton from '@/components/atoms/Button/XSmallButton';
import UserCardItemWithButton from '@/components/molecules/Item/UserCardItemWithButton';
import {usePutAttendanceInfo} from '@/hooks/attendance/usePutAttendanceInfo';
import {useGetDateBySearchParam} from '@/hooks/useGetDateBySearchParam';
import {getBanId} from '@/utils/userData';
import {toast} from 'react-toastify';
import {RoleItem} from '@/enum/roleItem';
import DirectorSelectItem from '../Medication/DirectorSelectItem';

interface AttendedKidsButtonViewProps {
  attendances?: GetAttendance[];
  onClickButton?: () => void;
  memberRole: RoleItem;
  onBanChange: (value: number) => void;
}

const AttendedKidsButtonView = ({
  attendances,
  onClickButton,
  memberRole,
  onBanChange,
}: AttendedKidsButtonViewProps) => {
  const date = useGetDateBySearchParam();
  const putMutate = usePutAttendanceInfo(getBanId()!);

  const handleSubmit = (kidId: number) => {
    if (date !== undefined) {
      putMutate.mutate(
        {
          kidIds: [kidId],
          year: date.get('year'),
          month: date.get('month') + 1,
          day: date.get('date'),
          attendedToday: 'NOTHING',
          reason: '',
        },
        {
          onSuccess: () => {
            toast.info('미출석 처리 되었습니다.');
          },
        }
      );
    }
  };

  return (
    <>
      <div className="flex items-center justify-between px-8 py-2 border-b border-gray-200 min-h-14">
        <div className="flex items-center justify-between w-full">
          <XSmallButton
            label="선택"
            onClick={() => {
              onClickButton?.();
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
              negativeLabel="미처리"
              onClickNegative={() => {
                handleSubmit(attendance.kidId);
              }}
            />
          ))}
      </div>
    </>
  );
};

export default AttendedKidsButtonView;
