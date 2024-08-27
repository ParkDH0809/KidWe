import {PatchKidInfo} from '@/types/user/PatchKidInfo';
import dayjs from 'dayjs';
import {atom} from 'recoil';

export const patchKidInfoState = atom<PatchKidInfo>({
  key: 'patchKidInfoState',
  default: {
    dto: {
      id: 0,
      name: '',
      birthday: dayjs().format('YYYY-MM-DD'),
      gender: 'MALE',
      allergies: [],
      banId: 0,
      kindergartenId: 0,
    },
    picture: undefined,
  },
});
