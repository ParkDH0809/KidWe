import {useEffect, useCallback} from 'react';
import {useParams, useNavigate} from 'react-router-dom';
import {useRecoilState, useResetRecoilState} from 'recoil';

import {useDailyNoteDetail} from '@/hooks/daily-note/useDailyNoteDetail';
import {usePutDailyNote} from '@/hooks/daily-note/usePutDailyNote';
import {dailyNoteEditFormState} from '@/recoil/atoms/daily-note/dailyNoteEditFormState';
import {getMemberId} from '@/utils/userData';
import {containerHeaderClass} from '@/styles/styles';

import Header from '@/components/organisms/Navigation/Header';
import TextEditor from '@/components/organisms/Board/TextEditor';
import ButtonBar from '@/components/organisms/Navigation/ButtonBar';

const DailyNoteEdit = () => {
  const {dailyNoteId} = useParams();
  const navigate = useNavigate();
  const {data} = useDailyNoteDetail(getMemberId()!, dailyNoteId!);
  const {mutate} = usePutDailyNote();
  const [formState, setFormState] = useRecoilState(dailyNoteEditFormState);
  const resetFormState = useResetRecoilState(dailyNoteEditFormState);
  const EMPTY = '<p><br></p>';

  useEffect(() => {
    if (data) {
      setFormState({
        sendTime: data.sendTime || '',
        content: data.content,
      });
    }

    return () => {
      resetFormState();
    };
  }, [data, setFormState, resetFormState]);

  const handleEditorChange = (value: string) => {
    setFormState(prev => ({...prev, content: value}));
  };

  const handleFormSubmit = useCallback(() => {
    mutate(
      {
        memberId: getMemberId()!,
        dailyNoteId: dailyNoteId!,
        body: {
          sendTime: formState.sendTime,
          content: formState.content,
        },
      },
      {
        onSuccess: () => {
          navigate(`/daily-notes/${dailyNoteId}`);
        },
      }
    );
  }, [formState, mutate, navigate, dailyNoteId]);

  return (
    <div className="flex flex-col h-screen">
      <Header title="알림장 수정" buttonType="back" />
      <div className={containerHeaderClass}>
        <TextEditor value={formState.content} onChange={handleEditorChange} />
      </div>
      <ButtonBar
        label="수정하기"
        disabled={formState.content === EMPTY}
        variant={formState.content === EMPTY ? 'negative' : 'positive'}
        onClick={handleFormSubmit}
      />
    </div>
  );
};

export default DailyNoteEdit;
