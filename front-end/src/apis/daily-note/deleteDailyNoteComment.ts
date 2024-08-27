import axiosInstance from '@/apis/axiosInstance';

export interface DeleteDailyNoteCommentParams {
  memberId: number;
  dailyNoteId: number;
  dailyNoteCommentId: number;
}

export const deleteDailyNoteComment = async ({
  memberId,
  dailyNoteId,
  dailyNoteCommentId,
}: DeleteDailyNoteCommentParams): Promise<void> => {
  try {
    const response = await axiosInstance.delete(
      `dailynotecomments/${memberId}/${dailyNoteId}/${dailyNoteCommentId}`
    );
    return response.data;
  } catch (error) {
    console.error(error);
    throw error;
  }
};
