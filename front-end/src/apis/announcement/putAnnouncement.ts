import axios from '@/apis/axiosInstance';

export interface PutAnnouncementParams {
  announcementId: string;
  body: {
    title: string;
    content: string;
  };
}

export const putAnnouncement = async ({
  announcementId,
  body,
}: PutAnnouncementParams) => {
  const response = await axios.put(`/announcements/${announcementId}`, body);
  return response.data;
};
