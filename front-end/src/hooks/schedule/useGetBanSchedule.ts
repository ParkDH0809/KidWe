import {useQuery, UseQueryResult} from '@tanstack/react-query';
import {scheduleKeys} from '@/hooks/schedule/scheduleKeys';
import {getBanSchedule} from '@/apis/schedule/getBanSchedule';
import {GetSchedule} from '@/types/schedule/GetSchedule';
import {AxiosError} from 'axios';

export const useGetBanSchedule = (
  banId: number | null,
  date: string
): UseQueryResult<GetSchedule[], AxiosError> => {
  const result = useQuery({
    queryKey: scheduleKeys.banSchedules(banId!, date),
    queryFn: () => getBanSchedule(banId!, date),
    enabled: !!banId,
  });
  return result;
};
