import AttendedKidsSelectView from '@/components/organisms/Attendance/AttendedKidsSelectView';
import AttendedKidsButtonView from '@/components/organisms/Attendance/AttendedKidsButtonView';
import {useEffect, useState} from 'react';
import {GetAttendance} from '@/types/attendance/GetAttendance';
import {getBanId, getMemberRole} from '@/utils/userData';
import {useLoading} from '@/hooks/loading/useLoading';
import {useGetAttendanceInfo} from '@/hooks/attendance/useGetAttendanceInfo';
import {useGetDateBySearchParam} from '@/hooks/useGetDateBySearchParam';
import {RoleItem} from '@/enum/roleItem';
import {isDirector} from '@/utils/auth/isDirector';

const AttendedKidsView = () => {
  const date = useGetDateBySearchParam();

  const memberRole = getMemberRole() as RoleItem;
  const [ban, setBan] = useState<number | null>(null);
  const handleBanChange = (value: number) => setBan(value);

  const [isShowSelect, setIsShowSelect] = useState(false);
  const {data, isLoading} = useGetAttendanceInfo(
    isDirector() ? ban : getBanId()!,
    date.get('year'),
    date.get('month') + 1,
    date.get('date')
  );
  useLoading(isLoading);

  const [attendedKids, setAttendedKids] = useState<GetAttendance[]>();

  useEffect(() => {
    setAttendedKids(
      data?.filter(
        value =>
          value.attendedToday === 'ABSENCE' ||
          value.attendedToday === 'ATTENDANCE'
      )
    );
  }, [data]);

  const handleClickSelectButton = () => {
    setIsShowSelect(false);
  };

  const handleClickButtonButton = () => {
    setIsShowSelect(true);
  };

  return (
    <>
      {/* {isLoading && <Spinner />} */}
      <div>
        {isShowSelect ? (
          <AttendedKidsSelectView
            attendances={attendedKids}
            onClickButton={handleClickSelectButton}
            ban={ban}
          />
        ) : (
          <AttendedKidsButtonView
            attendances={attendedKids}
            onClickButton={handleClickButtonButton}
            memberRole={memberRole}
            onBanChange={handleBanChange}
          />
        )}
      </div>
    </>
  );
};

export default AttendedKidsView;
