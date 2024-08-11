import {patchKidInfoState} from '@/recoil/atoms/my-page/kidInfo';
import {selector} from 'recoil';

export const kidInfoGender = selector({
  key: 'kidInfoGender',
  get: ({get}) => {
    const data = get(patchKidInfoState);
    return data.gender;
  },
  set: ({get, set}, newValue) => {
    const data = get(patchKidInfoState);
    set(patchKidInfoState, {...data, gender: newValue as 'MALE' | 'FEMALE'});
  },
});
