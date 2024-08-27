import {login} from '@/apis/login/login';
import {useMutation} from '@tanstack/react-query';
import {LoginResponse} from '@/types/login/LoginResponse';
import {setAccessToken} from '@/utils/userAccessToken';
import {setUserData} from '@/utils/userData';
// import {setRefreshToken} from '@/utils/userRefreshToken';
// import {getFcmToken} from '@/utils/notification/getFcmToken';
// import {sendFcmToken} from '@/apis/notification/sendFcmToken';

/**
 *
 * @param email
 * @param password
 * @returns 성공적으로 요청을 하면 cookie에 refresh token을, body에 access token을 담아서 응답한다.
 */
export const useLogin = () => {
  const result = useMutation({
    mutationFn: ({email, password}: {email: string; password: string}) => {
      return login(email, password);
    },
    onSuccess: async (data: LoginResponse) => {
      const {accessToken, ...userData} = data;
      setAccessToken(accessToken);
      // setRefreshToken(refreshToken);
      setUserData(userData);

      // const permission = await Notification.requestPermission();
      // if (permission === 'granted') {
      //   navigator.serviceWorker
      //     .register('/firebase-messaging-sw.js')
      //     .then(async registration => {
      //       console.log('Service Worker 등록 성공:', registration);

      //       // FCM 토큰 가져오기
      //       const token = await getFcmToken();
      //       await sendFcmToken(token);
      //     })
      //     .catch(error => {
      //       console.log('Service Worker 등록 실패:', error);
      //     });
      //   // FCM 토큰 가져오기
      // } else {
      //   console.log('알림 권한이 없습니다.');
      // }
    },
  });
  return result;
};
