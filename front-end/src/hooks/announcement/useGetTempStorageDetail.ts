import {useQuery} from '@tanstack/react-query';
import {
  getTempStorageDetail,
  TempStorageDetailResponse,
} from '@/apis/announcement/getTempStorageDetail';

export const useGetTempStorageDetail = (announcementId: string) => {
  return useQuery<TempStorageDetailResponse, Error>({
    queryKey: ['tempStorageDetail', announcementId],
    queryFn: () => {
      return getTempStorageDetail(announcementId);
    },
  });
};
