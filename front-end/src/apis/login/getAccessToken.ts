import axios from 'axios';
// import {getRefreshToken} from '@/utils/userRefreshToken';

export const getAccessToken = async (): Promise<{accessToken: string}> => {
  const result = await axios.post(
    `${import.meta.env.VITE_API_URL}/refresh`,
    {},
    {
      headers: {
        // Authorization: `Bearer ${getRefreshToken()}`,
        'Content-Type': 'application/json',
      },
      timeout: 3000,
    }
  );
  return result.data;
};
