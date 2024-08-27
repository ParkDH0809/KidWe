import axios from '@/apis/axiosInstance';

export interface TempStorageDetailResponse {
  title: string;
  content: string;
}

export const getTempStorageDetail = async (announcementId: string) => {
  const response = await axios.get<TempStorageDetailResponse>(
    `/announcements/storage/${announcementId}`
  );
  return response.data;
};
