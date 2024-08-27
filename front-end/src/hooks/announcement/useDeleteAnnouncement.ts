import {useMutation} from '@tanstack/react-query';
import {deleteAnnouncement} from '@/apis/announcement/deleteAnnouncement';

export const useDeleteAnnouncement = () => {
  return useMutation<void, Error, {announcementId: string}>({
    mutationFn: ({announcementId}) => deleteAnnouncement(announcementId),
    onSuccess: () => {},
    onError: (error: Error) => {
      console.error(error);
    },
  });
};
