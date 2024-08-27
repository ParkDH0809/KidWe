import {DailyNoteForm} from '@/types/daily-note/DailyNoteForm';
import {atom} from 'recoil';

export const dailyNoteFormState = atom<DailyNoteForm>({
  key: 'dailyNoteForm',
  default: {
    kidId: undefined,
    sendTime: '',
    content: '',
  },
});
