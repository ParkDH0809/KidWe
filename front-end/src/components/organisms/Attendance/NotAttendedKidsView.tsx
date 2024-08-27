import NotAttendedKidsButtonView from '@/components/organisms/Attendance/NotAttendedKidsButtonView';
import NotAttendedKidsSelectView from '@/components/organisms/Attendance/NotAttendedKidsSelectView';
import {useGetAttendanceInfo} from '@/hooks/attendance/useGetAttendanceInfo';
import {GetAttendance} from '@/types/attendance/GetAttendance';
import {useEffect, useState} from 'react';
import {useLoading} from '@/hooks/loading/useLoading';
import {getBanId, getMemberRole} from '@/utils/userData';
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

  const [absenceKids, setAbsenceKids] = useState<GetAttendance[]>();

  useEffect(() => {
    setAbsenceKids(data?.filter(val => val.attendedToday === 'NOTHING'));
  }, [data]);

  const handleClickSelectButton = () => {
    setIsShowSelect(true);
  };

  const handleClickButtonButton = () => {
    setIsShowSelect(false);
  };

  return (
    <>
      {/* {isLoading && <Spinner />} */}
      <div>
        {isShowSelect ? (
          <NotAttendedKidsSelectView
            attendances={absenceKids}
            onClickButton={handleClickButtonButton}
            ban={ban}
          />
        ) : (
          <NotAttendedKidsButtonView
            memberRole={memberRole}
            onBanChange={handleBanChange}
            attendances={absenceKids}
            onClickSelect={handleClickSelectButton}
          />
        )}
      </div>
    </>
  );
};

export default AttendedKidsView;
