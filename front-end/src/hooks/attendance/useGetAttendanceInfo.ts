import {getAttendanceInfo} from '@/apis/attendance/getAttendanceInfo';
import {GetAttendance} from '@/types/attendance/GetAttendance';
import {useQuery} from '@tanstack/react-query';
import {attendanceKeys} from '@/hooks/attendance/attendanceKeys';

export const useGetAttendanceInfo = (
  banId: number | null,
  year: number,
  month: number,
  date: number
) => {
  const result = useQuery<GetAttendance[]>({
    queryKey: attendanceKeys.lists(banId!, year, month, date),
    queryFn: () => getAttendanceInfo(banId!, year, month, date),
    enabled: !!banId,
  });
  return result;
};
