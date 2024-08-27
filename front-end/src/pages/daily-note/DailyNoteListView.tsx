import {useEffect, useState} from 'react';
import {useNavigate} from 'react-router-dom';
import dayjs from 'dayjs';

import {useDailyNoteList} from '@/hooks/daily-note/useDailyNoteList';
import {containerNavigatorClass} from '@/styles/styles';
import {RoleItem} from '@/enum/roleItem';
import {getMemberRole, getKidId, getBanId, getMemberId} from '@/utils/userData';
import {sortedByNewest} from '@/utils/sortedByNewest';
import {isDirector} from '@/utils/auth/isDirector';
import {useLoading} from '@/hooks/loading/useLoading';

import Header from '@/components/organisms/Navigation/Header';
import DateNavigator from '@/components/organisms/Navigation/DateNavigator';
import NoResult from '@/components/atoms/NoResult';
import MonthDivider from '@/components/atoms/Divider/MonthDivider';
import ScheduledUserCard from '@/components/molecules/Item/ScheduledUserCard';
import WriteButton from '@/components/atoms/Button/WriteButton';
import NavigationBar from '@/components/organisms/Navigation/NavigationBar';
import DirectorSelectItem from '@/components/organisms/Medication/DirectorSelectItem';

const DailyNoteListView = () => {
  const navigate = useNavigate();
  const [currentMonth, setCurrentMonth] = useState(dayjs().startOf('month'));

  const memberRole = getMemberRole() as RoleItem;

  const [id, setId] = useState<number | null>(null);
  const handleIdChange = (value: number) => {
    setId(value);
  };

  useEffect(() => {
    if (memberRole === RoleItem.Guardian) {
      setId(getKidId());
    } else if (memberRole === RoleItem.Teacher) {
      setId(getBanId());
    }
  }, [memberRole]);

  const {data, isLoading} = useDailyNoteList(
    id,
    getMemberId()!,
    currentMonth.year(),
    currentMonth.month() + 1,
    memberRole
  );
  useLoading(isLoading);

  const handleLeftClick = () => {
    setCurrentMonth(prev => prev.subtract(1, 'month').startOf('month'));
  };

  const handleRightClick = () => {
    setCurrentMonth(prev => prev.add(1, 'month').startOf('month'));
  };

  const handleUserItemClick = (dailyNoteId: number) => {
    navigate(`/daily-notes/${dailyNoteId}`);
  };

  const handleWriteButtonClick = () => {
    navigate('/daily-notes/write');
  };

  const sortedData = sortedByNewest(
    data ?? {dailyNoteListItemResponseDtos: {}}
  );

  return (
    <div className="relative flex flex-col h-screen">
      <Header title="알림장" buttonType="close" />
      <DateNavigator
        title={currentMonth.format('YY년 M월')}
        onClickLeft={handleLeftClick}
        onClickRight={handleRightClick}
      />
      <div className={`${containerNavigatorClass} pt-[6.5rem] flex flex-col`}>
        <div className="w-full px-3">
          <DirectorSelectItem
            memberRole={memberRole}
            onBanChange={handleIdChange}
          />
        </div>
        {(data &&
          Object.keys(data.dailyNoteListItemResponseDtos).length === 0) ||
        !id ? (
          <div className="flex items-center justify-center flex-grow h-full">
            <NoResult text="등록된 알림장이 없어요" />
          </div>
        ) : (
          sortedData &&
          sortedData.map(({day, items}) => (
            <div key={day}>
              <MonthDivider text={`${day}일`} color="gray" />
              {items.map(item => {
                const isFutureTime = dayjs(item.sendTime).isAfter(dayjs());
                return (
                  <div
                    key={item.id}
                    onClick={() => handleUserItemClick(item.id)}
                  >
                    <ScheduledUserCard
                      profile={item.kidPicture}
                      userName={item.kidName}
                      banName={item.banName}
                      writerRole={item.writerRole}
                      isSchedule={isFutureTime}
                      sendTime={dayjs(item.sendTime).format('HH:mm')}
                    />
                  </div>
                );
              })}
            </div>
          ))
        )}
      </div>
      {!isDirector() && <WriteButton onClick={handleWriteButtonClick} />}
      <NavigationBar />
    </div>
  );
};

export default DailyNoteListView;
