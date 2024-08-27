import axiosInstance from '@/apis/axiosInstance';
import {AnnouncementItem} from '@/types/announcement/AnnouncementItem';

export const getAnnouncementList = async (
  memberId: number,
  kindergartenId: number
): Promise<AnnouncementItem[]> => {
  try {
    const response = await axiosInstance.get(
      `/announcements/list/${memberId}?kindergartenId=${kindergartenId}`,
      {}
    );
    return response.data;
  } catch (error) {
    console.error(error);
    throw error;
  }
};
