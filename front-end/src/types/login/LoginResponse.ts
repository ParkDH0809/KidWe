// import {RefreshToken} from '@/types/login/RefreshToken';
import {AccessToken} from '@/types/login/AccessToken';

export interface LoginResponse extends AccessToken {
  memberId: number;
  memberEmail: string;
  memberRole: 'ROLE_DIRECTOR' | 'ROLE_GUARDIAN' | 'ROLE_TEACHER';
  memberStatus: 'NOTHING' | 'DECLINE' | 'PENDING' | 'ACCEPT';
  kindergartenId: number | null;
  banId: number | null;
  kidId: number;
}
