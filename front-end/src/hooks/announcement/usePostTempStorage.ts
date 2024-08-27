import {useMutation} from '@tanstack/react-query';
import {
  postTempStorage,
  TempStorageRequest,
} from '@/apis/announcement/postTempStorage';

export const usePostTempStorage = () => {
  return useMutation({
    mutationFn: (params: {memberId: number; data: TempStorageRequest}) =>
      postTempStorage(params.memberId, params.data),
    onSuccess: () => {},
    onError: (error: Error) => {
      console.error(error);
    },
  });
};
