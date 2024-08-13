import {useState} from 'react';
import {useNavigate} from 'react-router-dom';
import dayjs from 'dayjs';
import {groupByDate} from '@/utils/groupByDate';
import {useMedicationList} from '@/hooks/medication/useMedicationList';
import type {MedicationItem} from '@/types/medication/MedicationItem';
import {RoleItem} from '@/enum/roleItem';
import {containerNavigatorClass} from '@/styles/styles';
import Spinner from '@/components/atoms/Loader/Spinner';
import Header from '@/components/organisms/Navigation/Header';
import DateNavigator from '@/components/organisms/Navigation/DateNavigator';
import NoResult from '@/components/atoms/NoResult';
import MonthDivider from '@/components/atoms/Divider/MonthDivider';
import UserCardItem from '@/components/molecules/Item/UserCardItem';
import WriteButton from '@/components/atoms/Button/WriteButton';
import NavigationBar from '@/components/organisms/Navigation/NavigationBar';
import {getMemberRole, getKidId, getBanId} from '@/utils/userData';

const MedicationListView = () => {
  const [currentMonth, setCurrentMonth] = useState(dayjs().startOf('month'));
  const navigate = useNavigate();

  const memberRole = getMemberRole() as RoleItem;
  let id: number | null = 0;
  if (memberRole === RoleItem.Guardian) {
    id = getKidId();
  } else if (memberRole === RoleItem.Teacher) {
    id = getBanId();
  } else if (memberRole === RoleItem.Director) {
    // 선택한 반 id로 변경
    id = 1;
  }
  const {data, isLoading} = useMedicationList(
    id!,
    currentMonth.year(),
    currentMonth.month() + 1,
    memberRole
  );

  const handleLeftClick = () => {
    setCurrentMonth(prev => prev.subtract(1, 'month').startOf('month'));
  };

  const handleRightClick = () => {
    setCurrentMonth(prev => prev.add(1, 'month').startOf('month'));
  };

  const handleUserItemClick = (medicationId: number, item: MedicationItem) => {
    navigate(`/medications/${medicationId}`, {
      state: {
        kidName: item.kidName,
        banName: item.banName,
      },
    });
  };

  const handleWriteButtonClick = () => {
    navigate('/medications/write');
  };

  const groupedData = groupByDate(data ?? []);

  return (
    <div className="flex flex-col h-screen">
      {isLoading && <Spinner />}
      <Header title="투약의뢰서" buttonType="close" />
      <DateNavigator
        title={currentMonth.format('YY년 M월')}
        onClickLeft={handleLeftClick}
        onClickRight={handleRightClick}
      />
      <div className={`${containerNavigatorClass} pt-[6.5rem]`}>
        {data && data.length === 0 ? (
          <div className="flex items-center justify-center h-full">
            <NoResult text="등록된 의뢰서가 없어요" />
          </div>
        ) : (
          Object.keys(groupedData).map(date => (
            <div key={date}>
              <MonthDivider text={`${dayjs(date).date()}일`} color="gray" />
              {groupedData[date].map(item => (
                <div
                  key={item.medicationId}
                  onClick={() => handleUserItemClick(item.medicationId, item)}
                >
                  <UserCardItem
                    profile={item.profileImage || ''}
                    userName={item.kidName}
                    banName={item.banName}
                    cardType="basic"
                  />
                </div>
              ))}
            </div>
          ))
        )}
      </div>
      <WriteButton onClick={handleWriteButtonClick} />
      <NavigationBar />
    </div>
  );
};

export default MedicationListView;
