import MemoTimeSelect from '@/components/organisms/Memo/MemoTimeSelect';
import MemoTagSelect from '@/components/organisms/Memo/MemoTagSelect';
import KindergartenInfomationSelect from '@/components/organisms/Memo/KindergartenInfomationSelect';
import Header from '@/components/organisms/Navigation/Header';
import ButtonBar from '@/components/organisms/Navigation/ButtonBar';
import {containerHeaderClass} from '@/styles/styles';
import {memoState} from '@/recoil/atoms/memo/memo';
import {useEffect, useState} from 'react';
import {useNavigate, useSearchParams} from 'react-router-dom';
import {useWriteDailyMemo} from '@/hooks/memo/useWriteDailyMemo';
import {PostMemo} from '@/types/memo/PostMemo';
import {useRecoilState, useSetRecoilState} from 'recoil';
import dayjs from 'dayjs';
import {getMemberId} from '@/utils/userData';
import {loadingState} from '@/recoil/atoms/axios/loading';
import {useLoading} from '@/hooks/loading/useLoading';

const MemoWrite = () => {
  const navigate = useNavigate();

  const [memo, setMemo] = useRecoilState<PostMemo>(memoState);

  const [serachParams] = useSearchParams();
  const paramDate = serachParams.get('date'); // query로 date가 올바르지 않게 들어올 때 에러 처리 필요
  let currentDate = dayjs().format('YYYY-MM-DD HH:mm');
  if (paramDate !== undefined) {
    currentDate = dayjs(`${paramDate} ${dayjs().format('HH:mm')}`).format(
      'YYYY-MM-DD HH:mm'
    );
  }

  useEffect(() => {
    setMemo({...memo, updatedTime: dayjs(currentDate)});
  }, [currentDate, setMemo]);

  const [isValid, setIsValid] = useState(false);
  useEffect(() => {
    setIsValid(
      memo.content !== '' ||
        memo.kids.length !== 0 ||
        memo.lesson !== '' ||
        memo.tags.length !== 0
    );
  }, [memo]);

  const setLoadingState = useSetRecoilState(loadingState);
  const writeMutate = useWriteDailyMemo();
  const handleClick = () => {
    writeMutate.mutateAsync(
      {teacherId: getMemberId()!, memo},
      {
        onSuccess: () =>
          navigate({
            pathname: '/memos',
            search: `?date=${paramDate}`,
          }),
        onSettled: () => setLoadingState(false),
      }
    );
  };
  useLoading(writeMutate.isPending);

  return (
    <>
      <div className={`${containerHeaderClass} flex flex-col h-full bg-white`}>
        <Header title="관찰 메모 작성" buttonType="back" />
        <div className="flex-grow px-5 py-5 space-y-8 overflow-y-auto">
          <MemoTimeSelect />
          <MemoTagSelect />
          <KindergartenInfomationSelect />
        </div>
        <ButtonBar
          label="메모 작성하기"
          variant={isValid ? 'positive' : 'negative'}
          disabled={!isValid}
          onClick={handleClick}
        />
      </div>
    </>
  );
};

export default MemoWrite;
