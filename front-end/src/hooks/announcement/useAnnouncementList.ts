import {useQuery} from '@tanstack/react-query';
import {getAnnouncementList} from '@/apis/announcement/getAnnouncementList';
import {AnnouncementItem} from '@/types/announcement/AnnouncementItem';

export const useAnnouncementList = (
  memberId: number,
  kindergartenId: number
) => {
  return useQuery<AnnouncementItem[], Error>({
    queryKey: ['announcementList', memberId, kindergartenId],
    queryFn: () => {
      return getAnnouncementList(memberId, kindergartenId);
    },
  });
};
