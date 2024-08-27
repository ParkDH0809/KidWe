import axiosInstace from '@/apis/axiosInstance';

export interface PutDailyNoteParams {
  memberId: number;
  dailyNoteId: string;
  body: {
    sendTime: string;
    content: string;
  };
}

export const putDailyNote = async ({
  memberId,
  dailyNoteId,
  body,
}: PutDailyNoteParams) => {
  console.log(body);
  const response = await axiosInstace.put(
    `/dailynotes/${memberId}/${dailyNoteId}`,
    body
  );
  return response.data;
};
