import axiosInstance from '@/apis/axiosInstance';

export const deleteAnnouncement = async (
  announcementId: string
): Promise<void> => {
  try {
    const response = await axiosInstance.delete(
      `announcements/${announcementId}`
    );
    return response.data;
  } catch (error) {
    console.error(error);
    throw error;
  }
};
