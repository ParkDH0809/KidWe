import Button from '@/components/atoms/Button/Button';
import Header from '@/components/organisms/Navigation/Header';
import NavigationBar from '@/components/organisms/Navigation/NavigationBar';
import {containerHeaderClass} from '@/styles/styles';
import dayjs from 'dayjs';
import {useNavigate, useSearchParams} from 'react-router-dom';
import {PostFood} from '@/types/food/PostFood';
import {useEffect, useState} from 'react';
import {useWriteDailyFood} from '@/hooks/food/useWriteDailyFood';
import {getKindergartenId} from '@/utils/userData';
import {useGetDateBySearchParam} from '@/hooks/useGetDateBySearchParam';
import {useGetDailyFood} from '@/hooks/food/useGetDailyFood';
import {useLoading} from '@/hooks/loading/useLoading';
import {usePutDailyFood} from '@/hooks/food/usePutDailyFood';
import {toast} from 'react-toastify';
import FoodInfoWriteView from '@/components/organisms/Food/FoodInfoWriteView';
import {loadingState} from '@/recoil/atoms/axios/loading';
import {useSetRecoilState} from 'recoil';

const FoodInfoWrite = () => {
  const setLoading = useSetRecoilState(loadingState); // Batcher Component 오류
  const date = useGetDateBySearchParam().format('YYYY-MM-DD');

  const navigate = useNavigate();
  const [menu, setMenu] = useState<PostFood>({
    lunch: '',
    lunchAllergies: [],
    snack: '',
    snackAllergies: [],
    dinner: '',
    dinnerAllergies: [],
    menuDate: date,
  });

  // querystring에 update가 true일 때 값을 서버로부터 가져와서 세팅
  const [searchParam] = useSearchParams();
  const isUpdate = searchParam.get('update') === 'true';
  const {data: food, isLoading} = useGetDailyFood(
    getKindergartenId()!,
    dayjs(date)
  );
  useLoading(isLoading);

  useEffect(() => {
    if (food && !isUpdate) {
      navigate({
        pathname: '/foods',
        search: `?date=${date}`,
      });
    }
  }, [food, isUpdate, navigate]);

  useEffect(() => {
    if (isUpdate && food !== undefined) {
      const {
        lunch,
        lunchAllergies,
        snack,
        snackAllergies,
        dinner,
        dinnerAllergies,
      } = food;
      setMenu({
        lunch,
        lunchAllergies,
        snack,
        snackAllergies,
        dinner,
        dinnerAllergies,
        menuDate: date,
      });
    }
  }, [food, isUpdate, date]);

  // 등록 및 수정
  const foodWriteMutate = useWriteDailyFood();
  const foodUpdateMutate = usePutDailyFood();

  const onSuccess = () => {
    navigate({
      pathname: '/foods',
      search: `?date=${date}`,
    });
    toast.info('작성이 완료되었습니다.');
  };

  const onSettled = () => {
    setLoading(false);
  };

  const handleButtonClick = () => {
    if (isUpdate) {
      if (food) {
        foodUpdateMutate.mutate(
          {
            menuId: food.menuId,
            body: menu,
          },
          {
            onSuccess,
            onSettled,
          }
        );
      }
    } else {
      foodWriteMutate.mutate(
        {
          kindergartenId: getKindergartenId()!,
          menu,
        },
        {
          onSuccess,
          onSettled,
        }
      );
    }
  };
  useEffect(() => {
    setLoading(foodUpdateMutate.isPending || foodWriteMutate.isPending);
  }, [foodUpdateMutate.isPending, foodWriteMutate.isPending, setLoading]);

  const handleChangeData = (
    allergies: string[],
    type: 'lunch' | 'snack' | 'dinner'
  ) => {
    if (type === 'lunch') {
      setMenu({...menu, lunchAllergies: allergies});
    } else if (type === 'snack') {
      setMenu({...menu, snackAllergies: allergies});
    } else {
      setMenu({...menu, dinnerAllergies: allergies});
    }
  };

  const handleChangeInput = (
    value: string,
    type: 'lunch' | 'snack' | 'dinner'
  ) => {
    setMenu({...menu, [type]: value});
  };

  const dateShow = dayjs(date);
  if (!dateShow.isValid()) {
    throw new Error('invalid date format');
  }

  return (
    <div className={`${containerHeaderClass} flex flex-col h-full`}>
      <Header title="메뉴 정보 등록" buttonType="back" />
      <div className="flex justify-end px-2 py-2 text-xs h-fit min-h-fit min-w-fit">
        <p>{dateShow.format('YYYY-MM-DD (ddd)')}</p>
      </div>
      <div className="flex-grow px-5 py-5 space-y-6 overflow-y-scroll">
        <FoodInfoWriteView
          menu={menu}
          onChangeData={handleChangeData}
          onChangeInput={handleChangeInput}
        />
      </div>
      <div className="px-5 py-6 h-fit min-h-fit min-w-fit">
        <Button
          onClick={handleButtonClick}
          label={isUpdate ? '수정 완료' : '작성 완료'}
          size="large"
        />
      </div>
      <NavigationBar />
    </div>
  );
};

export default FoodInfoWrite;
