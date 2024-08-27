import {useMutation, useQueryClient} from '@tanstack/react-query';
import {
  deleteDailyNoteComment,
  DeleteDailyNoteCommentParams,
} from '@/apis/daily-note/deleteDailyNoteComment';

export const useDeleteDailyNoteComment = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (params: DeleteDailyNoteCommentParams) =>
      deleteDailyNoteComment(params),
    onSuccess: () => {
      // 댓글 삭제 후 해당 알림장의 댓글 목록 캐시를 무효화하여 UI 업데이트
      queryClient.invalidateQueries({
        queryKey: ['dailyNoteDetail'],
      });
    },
    onError: (error: Error) => {
      console.error('Failed to delete comment:', error);
    },
  });
};
