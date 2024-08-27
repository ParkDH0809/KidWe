import axios, {AxiosResponse, InternalAxiosRequestConfig} from 'axios';
import {getAccessToken} from '@/apis/login/getAccessToken';
import {
  getAccessToken as getToken,
  setAccessToken as setToken,
} from '@/utils/userAccessToken';
import {ErrorCode} from '@/types/error/ErrorCode';

const axiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: true, // Cross Origin
  timeout: 10000,
});

axiosInstance.interceptors.request.use((config: InternalAxiosRequestConfig) => {
  const accessToken = getToken();

  if (accessToken !== null) {
    config.headers.Authorization = `Bearer ${accessToken}`;
  }

  console.log('api config', config);

  return config;
});

axiosInstance.interceptors.response.use(
  (response: AxiosResponse) => {
    console.log('server response', response);

    return response;
  },
  async error => {
    console.error(error);

    const status = error?.response?.status;
    const {code}: ErrorCode = error?.response?.data || {};
    if (
      (status === 400 && code === 'EXPIRED_TOKEN') ||
      (status === 400 && code === 'TOKEN_MISSING')
    ) {
      console.log('Access Token 재발급 시작');
      try {
        const {accessToken} = await getAccessToken();
        setToken(accessToken);

        console.log('Access Token 재발급 완료');
        return axiosInstance(error.config);
      } catch (error) {
        window.location.replace('/auth/login');
      }
    }
    throw error;
  }
);

export default axiosInstance;
