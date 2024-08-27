import {useMutation, useQueryClient} from '@tanstack/react-query';
import {
  putAnnouncement,
  PutAnnouncementParams,
} from '@/apis/announcement/putAnnouncement';

export const usePutAnnouncement = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (params: PutAnnouncementParams) => putAnnouncement(params),
    onSuccess: () => {
      queryClient.invalidateQueries({queryKey: ['announcementDetail']});
    },
    onError: (error: Error) => {
      console.error(error);
    },
  });
};
