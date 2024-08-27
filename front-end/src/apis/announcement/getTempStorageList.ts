import axiosInstance from '@/apis/axiosInstance';

export interface TempStorageItem {
  title: string;
  storedDate: string;
  announcementId: number;
}

export const getTempStorageList = async (
  memberId: number
): Promise<TempStorageItem[]> => {
  const response = await axiosInstance.get(
    `/announcements/storage/list/${memberId}`
  );
  return response.data;
};
