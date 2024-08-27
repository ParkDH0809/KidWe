import DateNavigator from '@/components/organisms/Navigation/DateNavigator';
import ScheduleInfo from '@/components/organisms/Schedule/ScheduleInfo';
import CustomCalendar from '@/components/molecules/Calendar/CustomCalendar';
import Header from '@/components/organisms/Navigation/Header';
import NavigationBar from '@/components/organisms/Navigation/NavigationBar';
import {containerNavigatorClass} from '@/styles/styles';
import {Dayjs} from 'dayjs';
import {useGetKindergartenInfo} from '@/hooks/schedule/useGetKindergartenInfo';
import {useLoading} from '@/hooks/loading/useLoading';
import {getKindergartenId} from '@/utils/userData';
import {useNavigate} from 'react-router-dom';
import {useGetDateBySearchParam} from '@/hooks/useGetDateBySearchParam';
import {directorBanState} from '@/recoil/atoms/schedule/directorBan';
import {useSetRecoilState} from 'recoil';
import {ScheduleTeacherMenu} from '@/components/organisms/Schedule/ScheduleTeacherMenu';

const KindergartenSchedule = () => {
  const setBanState = useSetRecoilState(directorBanState);

  const handleBanChange = (value: string) => {
    setBanState(+value);
  };
  const {data, isLoading} = useGetKindergartenInfo(getKindergartenId()!);
  const bans = data?.bans;
  useLoading(isLoading);

  const date = useGetDateBySearchParam();

  const navigate = useNavigate();
  const onChangeDate = (value: Dayjs) => {
    const newDate = value.format('YYYY-MM-DD');
    navigate({
      pathname: '/schedule',
      search: `?date=${newDate}`,
    });
  };

  const handleBeforeMonth = () => {
    navigate({
      pathname: '/schedule',
      search: `?date=${date.subtract(1, 'month').format('YYYY-MM-DD')}`,
    });
  };

  const handleAfterMonth = () => {
    navigate({
      pathname: '/schedule',
      search: `?date=${date.add(1, 'month').format('YYYY-MM-DD')}`,
    });
  };

  return (
    <>
      <Header title="유치원 일정" />
      <DateNavigator
        title={date.format('YY년 MM월')}
        onClickLeft={handleBeforeMonth}
        onClickRight={handleAfterMonth}
      />
      <div
        className={`${containerNavigatorClass} items-center justify-center w-full px-10 space-y-4 overflow-y-auto`}
      >
        <div className="flex items-center justify-between w-full h-16">
          <ScheduleTeacherMenu
            bans={bans}
            date={date}
            onBanChange={handleBanChange}
          />
        </div>
        <div className="flex items-center justify-center flex-grow w-full">
          <div className="flex items-start justify-center max-w-full px-1 pt-10 pb-3 border border-gray-200 rounded-lg aspect-square">
            <CustomCalendar
              defaultDate={date}
              showNavigation={false}
              onChange={onChangeDate}
              activeStartDate={true}
            />
          </div>
        </div>
        <ScheduleInfo date={date} />
      </div>
      <NavigationBar />
    </>
  );
};

export default KindergartenSchedule;
