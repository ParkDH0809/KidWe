import {putAttendanceInfo} from '@/apis/attendance/putAttendanceInfo';
import {useMutation} from '@tanstack/react-query';
import {attendanceKeys} from '@/hooks/attendance/attendanceKeys';
import {useQueryClient} from '@tanstack/react-query';
import {PutAttendance} from '@/types/attendance/PutAttendance';

export const usePutAttendanceInfo = (banId: number | null) => {
  const queryClient = useQueryClient();
  const result = useMutation({
    mutationFn: ({
      year,
      month,
      day,
      kidIds,
      attendedToday,
      reason,
    }: PutAttendance) =>
      putAttendanceInfo(year, month, day, kidIds, attendedToday, reason),
    onSuccess: (_, {year, month, day}) => {
      if (banId !== null) {
        queryClient.invalidateQueries({
          queryKey: attendanceKeys.lists(banId, year, month, day),
        });
      }
    },
  });
  return result;
};
