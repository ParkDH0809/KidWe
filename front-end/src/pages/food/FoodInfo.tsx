import DateNavigator from '@/components/organisms/Navigation/DateNavigator';
import WriteButton from '@/components/atoms/Button/WriteButton';
import FoodDateNavigator from '@/components/organisms/Food/FoodDateNavigator';
import Header from '@/components/organisms/Navigation/Header';
import NavigationBar from '@/components/organisms/Navigation/NavigationBar';
import {useNavigate} from 'react-router-dom';
import {containerNavigatorClass} from '@/styles/styles';
import dayjs, {Dayjs} from 'dayjs';
import weekOfYear from 'dayjs/plugin/weekOfYear';
import {useGetDailyFood} from '@/hooks/food/useGetDailyFood';
import {useLoading} from '@/hooks/loading/useLoading';
import {getKindergartenId, getMemberRole} from '@/utils/userData';
import {useGetDateBySearchParam} from '@/hooks/useGetDateBySearchParam';
import {useDeleteDailyFood} from '@/hooks/food/useDeleteDailyFood';
import {toast} from 'react-toastify';
import {useState} from 'react';
import FoodListView from '@/components/organisms/Food/FoodListView';
import FoodModal from '@/components/organisms/Food/FoodModal';
import FoodModifyButton from '@/components/organisms/Food/FoodModifyButton';
import {isGuardian} from '@/utils/auth/isGuardian';

dayjs.extend(weekOfYear);

function getWeekOfMonth(date: Dayjs) {
  const startOfMonth = date.startOf('month');
  const startWeekCount = startOfMonth.week();
  const currentWeekCount = date.week();
  return currentWeekCount - startWeekCount + 1;
}

const FoodInfo = () => {
  const navigate = useNavigate();
  const date = useGetDateBySearchParam();

  const {data: food, isLoading} = useGetDailyFood(getKindergartenId()!, date);
  useLoading(isLoading);

  // 삭제 기능 및 모달
  const [isModalOpen, setIsModalOpen] = useState(false);
  const handleCloseModal = () => {
    setIsModalOpen(false);
  };
  const handleOpenModal = () => {
    setIsModalOpen(true);
  };

  const deleteMutate = useDeleteDailyFood();
  const handleDeleteClick = () => {
    if (food) {
      deleteMutate.mutate(
        {menuId: food.menuId},
        {
          onSuccess: () => {
            toast.info('삭제 완료되었습니다.');
          },
        }
      );
    }
    handleCloseModal();
  };

  const handleUpdateClick = () => {
    navigate({
      pathname: 'write',
      search: `date=${date.format('YYYY-MM-DD')}&update=true`,
    });
  };

  // navigate
  const handleLeftClick = () => {
    navigate({
      pathname: '/foods',
      search: `?date=${date.subtract(1, 'week').format('YYYY-MM-DD')}`,
    });
  };

  const handleRightClick = () => {
    navigate({
      pathname: '/foods',
      search: `?date=${date.add(1, 'week').format('YYYY-MM-DD')}`,
    });
  };

  const handleDateChange = (value: Dayjs) => {
    navigate({
      pathname: '/foods',
      search: `?date=${value.format('YYYY-MM-DD')}`,
    });
  };

  const moveToWrite = () => {
    navigate({
      pathname: 'write',
      search: `date=${date.format('YYYY-MM-DD')}`,
    });
  };

  return (
    <>
      <div
        className={`${containerNavigatorClass} max-h-screen relative flex flex-col items-center justify-center box-border h-screen px-5 overflow-y-auto`}
      >
        <Header title="급식 정보" buttonType="close" />
        <DateNavigator
          title={`${date.format('M월')} ${getWeekOfMonth(date)}주차`}
          onClickLeft={handleLeftClick}
          onClickRight={handleRightClick}
        />

        <div className="flex justify-center gap-2 mb-4 w-fit h-fit">
          <FoodDateNavigator date={date} onClick={handleDateChange} />
        </div>
        <div className="flex justify-end w-full mb-3">
          {!isGuardian() && food && (
            <FoodModifyButton
              onOpenModalClick={handleOpenModal}
              onUpdateClick={handleUpdateClick}
            />
          )}
        </div>
        <div className="flex-grow mb-20 space-y-3 overflow-y-auto scrollbar-hide">
          <FoodListView food={food} />
        </div>
        <NavigationBar />
        {getMemberRole() !== 'ROLE_GUARDIAN' && !food && (
          <WriteButton onClick={() => moveToWrite()} />
        )}
      </div>
      <FoodModal
        isOpen={isModalOpen}
        onClickClose={handleCloseModal}
        onDeleteClick={handleDeleteClick}
      />
    </>
  );
};

export default FoodInfo;
