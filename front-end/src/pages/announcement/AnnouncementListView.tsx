import {useNavigate} from 'react-router-dom';
import {useAnnouncementList} from '@/hooks/announcement/useAnnouncementList';
import {containerHeaderClass} from '@/styles/styles';

import {getMemberId, getKindergartenId} from '@/utils/userData';
import {useLoading} from '@/hooks/loading/useLoading';
import {isGuardian} from '@/utils/auth/isGuardian';

import Header from '@/components/organisms/Navigation/Header';
import NoResult from '@/components/atoms/NoResult';
import AnnounceItem from '@/components/molecules/Item/AnnounceItem';
import WriteButton from '@/components/atoms/Button/WriteButton';
import NavigationBar from '@/components/organisms/Navigation/NavigationBar';

const AnnouncementListView = () => {
  const navigate = useNavigate();

  const {data, isLoading} = useAnnouncementList(
    getMemberId()!,
    getKindergartenId()!
  );

  const handleUserItemClick = (announcementId: number) => {
    navigate(`/announcements/${announcementId}`);
  };

  const handleWriteButtonClick = () => {
    navigate('/announcements/write');
  };

  useLoading(isLoading);

  return (
    <div className="relative flex flex-col h-screen max-h-screen">
      <Header title="공지사항" buttonType="close" />
      <div className={`${containerHeaderClass}`}>
        {data?.length === 0 ? (
          <div className="flex items-center justify-center h-full">
            <NoResult text="등록된 글이 없어요" />
          </div>
        ) : (
          data?.map(item => (
            <div
              key={item.announcementId}
              onClick={() => handleUserItemClick(item.announcementId)}
            >
              <AnnounceItem
                title={item.title}
                banName={item.memberBan}
                writer={item.memberName}
                date={item.createdDate}
                commentCount={item.commentCnt}
              />
            </div>
          ))
        )}
      </div>
      {!isGuardian() && <WriteButton onClick={handleWriteButtonClick} />}
      <NavigationBar />
    </div>
  );
};

export default AnnouncementListView;
