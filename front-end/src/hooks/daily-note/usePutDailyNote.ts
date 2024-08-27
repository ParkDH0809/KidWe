import {useMutation, useQueryClient} from '@tanstack/react-query';
import {putDailyNote, PutDailyNoteParams} from '@/apis/daily-note/putDailyNote';

export const usePutDailyNote = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (params: PutDailyNoteParams) => putDailyNote(params),
    onSuccess: (_, variables) => {
      // 캐시 무효화
      queryClient.invalidateQueries({
        queryKey: ['dailyNoteList', variables.memberId, variables.dailyNoteId],
      });
    },
    onError: (error: Error) => {
      console.error(error);
    },
  });
};
