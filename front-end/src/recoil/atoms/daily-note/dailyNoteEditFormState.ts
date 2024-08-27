import {DailyNoteEditForm} from '@/types/daily-note/DailyNoteEditForm';
import {atom} from 'recoil';

export const dailyNoteEditFormState = atom<DailyNoteEditForm>({
  key: 'dailyNoteEditForm',
  default: {
    sendTime: '',
    content: '',
  },
});
