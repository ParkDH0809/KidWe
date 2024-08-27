import axiosInstance from '@/apis/axiosInstance';

export interface TempStorageRequest {
  title: string;
  content: string;
}

export const postTempStorage = async (
  memberId: number,
  data: TempStorageRequest
) => {
  const response = await axiosInstance.post(
    `/announcements/storage/${memberId}`,
    data
  );
  return response.data;
};
