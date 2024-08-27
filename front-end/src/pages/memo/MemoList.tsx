import DateNavigator from '@/components/organisms/Navigation/DateNavigator';
import WriteButton from '@/components/atoms/Button/WriteButton';
import {memo, useEffect, useState} from 'react';
import {useNavigate} from 'react-router-dom';
import {containerNavigatorClass} from '@/styles/styles';
import Header from '@/components/organisms/Navigation/Header';
import NavigationBar from '@/components/organisms/Navigation/NavigationBar';
import type {GetMemo} from '@/types/memo/GetMemo';
import {useGetDailyMemo} from '@/hooks/memo/useGetDailyMemo';
import {useDeleteMemo} from '@/hooks/memo/useDeleteMemo';
import {toast} from 'react-toastify';
import {getMemberId} from '@/utils/userData';
import {useGetDateBySearchParam} from '@/hooks/useGetDateBySearchParam';
import {useLoading} from '@/hooks/loading/useLoading';
import MemoListView from '@/components/organisms/Memo/MemoListView';
import MemoModal from '@/components/organisms/Memo/MemoModal';
import {useResetRecoilState} from 'recoil';
import {memoState} from '@/recoil/atoms/memo/memo';

const MemoList = memo(() => {
  const date = useGetDateBySearchParam();

  // 현재 시간
  const handleLeftClick = () => {
    navigate({
      pathname: '/memos',
      search: `?date=${date.subtract(1, 'day').format('YYYY-MM-DD')}`,
    });
  };

  const handleRightClick = () => {
    navigate({
      pathname: '/memos',
      search: `?date=${date.add(1, 'day').format('YYYY-MM-DD')}`,
    });
  };

  const memoReset = useResetRecoilState(memoState);
  useEffect(() => {
    memoReset();
  }, []);

  // data fetch
  const {data, refetch, isLoading} = useGetDailyMemo(
    getMemberId()!,
    date.format('YYYY'),
    date.format('MM'),
    date.format('DD')
  );
  useLoading(isLoading);

  // modal
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [modalMemo, setModalMemo] = useState<GetMemo>();

  const handleModalClose = () => {
    setIsModalOpen(false);
  };

  const handleModalOpen = (memo: GetMemo) => {
    setModalMemo(memo);
    setIsModalOpen(true);
  };

  // navigate
  const navigate = useNavigate();
  const deleteMutate = useDeleteMemo();

  const moveToUpdate = (id: string | undefined) => {
    if (id !== undefined) {
      navigate({
        pathname: `update/${id}`,
      });
    }
  };

  const moveToWrite = () => {
    navigate({
      pathname: `write`,
      search: `date=${date.format('YYYY-MM-DD')}`,
    });
  };

  const handleDeleteClick = (memoId: string | undefined) => {
    if (memoId !== undefined) {
      deleteMutate.mutate(
        {teacherId: getMemberId()!, memoId},
        {
          onSuccess: () => {
            refetch();
            toast.info('삭제 완료');
          },
        }
      );
    }
    setIsModalOpen(false);
  };

  return (
    <>
      <div
        className={`${containerNavigatorClass} relative flex flex-col h-screen items-center`}
      >
        <Header title="관찰 메모" buttonType="close" />
        <DateNavigator
          title={date.format('M.D (ddd)')}
          onClickLeft={handleLeftClick}
          onClickRight={handleRightClick}
        />
        <div className="z-10 max-h-full px-4 py-4 overflow-y-auto min-w-fit scrollbar-hide">
          <MemoListView data={data} onModalClick={handleModalOpen} />
        </div>
        <WriteButton onClick={moveToWrite} />
        <NavigationBar />
        <MemoModal
          isOpen={isModalOpen}
          modalMemo={modalMemo}
          onCloseClick={handleModalClose}
          onDeleteClick={handleDeleteClick}
          onUpdateClick={moveToUpdate}
        />
        <div className="absolute w-full h-full bg-[#F8F8F8] top-0 bottom-0 left-0 right-0" />
      </div>
    </>
  );
});

export default MemoList;
