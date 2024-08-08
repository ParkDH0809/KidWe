import axiosInstance from '@/apis/axiosInstance';
import {Token} from '@/types/login/Token';

export const login = async (
  email: string,
  password: string
): Promise<{data: Token; status: number}> => {
  try {
    const response = await axiosInstance.post('/login', {email, password});
    console.log('서버의 응답은!!!:', response);
    return response;
  } catch (error) {
    console.debug(`login 전송 Error!!!: ${error}`);
    throw error;
  }
};
