import {ErrorCode} from '@/types/error/ErrorCode';
import {deleteAccessToken} from '@/utils/userAccessToken';
import {deleteUserData} from '@/utils/userData';
import {deleteRefreshToken} from '@/utils/userRefreshToken';
import {AxiosError} from 'axios';
import {useCallback} from 'react';
import {toast} from 'react-toastify';

const useApiHandler = () => {
  const errorHandler = useCallback((error: AxiosError<ErrorCode>) => {
    if (error.response) {
      const {status} = error.response;
      const {code} = error.response.data;

      if (status === 401 && code === 'UNAUTHENTICATED_EXPIRED_REFRESH_TOKEN') {
        deleteAccessToken();
        deleteRefreshToken();
        deleteUserData();
        // toast.error('세션이 만료되었습니다. 다시 로그인 해주세요.');
        window.location.replace('/login');
      } else {
        // defaultMessage(status);
      }
    }
  }, []);

  return {errorHandler};
};

const defaultMessage = (status: number) => {
  if (status === 400) {
    toast.error('올바르지 않은 입력입니다.');
  } else if (status === 401) {
    toast.error('권한이 없습니다.');
  } else if (status === 404) {
    toast.error('존재하지 않는 요청입니다.');
  } else if (status === 500) {
    toast.error('문제가 발생했습니다.\n잠시 후 다시 시도해주세요.');
  } else {
    toast.error('에러가 발생했습니다.\n관리자에게 문의해주세요.');
  }
};

export default useApiHandler;
