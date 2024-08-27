import {useQuery} from '@tanstack/react-query';
import {
  getTempStorageList,
  TempStorageItem,
} from '@/apis/announcement/getTempStorageList';

export const useGetTempStorageList = (memberId: number) => {
  return useQuery<TempStorageItem[], Error>({
    queryKey: ['tempStorageList', memberId],
    queryFn: () => {
      return getTempStorageList(memberId);
    },
  });
};
