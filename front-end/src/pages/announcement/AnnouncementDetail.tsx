import {useState} from 'react';
import {useParams} from 'react-router-dom';

import {useAnnouncementDetail} from '@/hooks/announcement/useAnnouncementDetail';
import {usePostAnnouncementComment} from '@/hooks/announcement/usePostAnnouncementComment';
import {usePostAnnouncementReply} from '@/hooks/announcement/usePostAnnouncementReply';
import {getMemberId} from '@/utils/userData';
import {RoleItem} from '@/enum/roleItem';
import {ROLE_NAMES} from '@/constants/roleNames';
import {containerHeaderClass} from '@/styles/styles';

import Header from '@/components/organisms/Navigation/Header';
import Author from '@/components/molecules/Board/Author';
import ArticleTitle from '@/components/molecules/Board/ArticleTitle';
import ArticleSection from '@/components/organisms/Board/ArticleSection';
import CommentSection from '@/components/organisms/Board/CommentSection';
import InputBar from '@/components/organisms/Navigation/InputBar';
import {useLoading} from '@/hooks/loading/useLoading';

const AnnounementDetail = () => {
  const {announcementId} = useParams();
  const {data, isLoading} = useAnnouncementDetail(
    announcementId!,
    getMemberId()!
  );
  useLoading(isLoading);

  const postCommentMutation = usePostAnnouncementComment();
  const postReplyMutation = usePostAnnouncementReply();
  const [parentCommentId, setParentCommentId] = useState<number>(0);

  const handleCommentSubmit = (content: string) => {
    console.log(parentCommentId);
    if (parentCommentId === 0) {
      postCommentMutation.mutate({
        announcementId: announcementId!,
        memberId: getMemberId()!,
        content,
      });
    } else {
      postReplyMutation.mutate({
        announcementCommentId: parentCommentId,
        memberId: getMemberId()!,
        content,
      });

      setParentCommentId(0);
    }
  };

  const handleReplyClick = (commentId: number) => {
    setParentCommentId(commentId);
  };

  const writerName = (() => {
    if (data?.role === RoleItem.Director) {
      return '유치원 원장님';
    }

    const banName = data?.banName || '';
    const roleName = ROLE_NAMES[data?.role as RoleItem] || '';

    return `${banName} ${roleName}`.trim();
  })();

  return (
    <div className="flex flex-col h-screen">
      <Header title="공지사항" buttonType="back" />
      <div className={containerHeaderClass}>
        <Author
          profile={data?.picture ?? ''}
          writer={writerName}
          date={data?.post.createdDateTime || ''}
          isEdit={data?.canDelete}
          isAnnouncement
        />
        <ArticleTitle title={data?.post.title || ''} banName={data?.banName} />
        <ArticleSection
          content={data?.post.content || ''}
          images={data?.images || []}
        />
        <CommentSection
          commentCount={data?.commentCount || 0}
          comments={data?.comments || []}
          onReplyClick={handleReplyClick}
          selectedCommentId={parentCommentId}
        />
      </div>
      <InputBar onSubmit={handleCommentSubmit} />
    </div>
  );
};

export default AnnounementDetail;
