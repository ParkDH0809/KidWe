import {useParams} from 'react-router-dom';

import {Comment, Child} from '@/types/article/Comment';
import {ROLE_NAMES} from '@/constants/roleNames';
import {useDeleteDailyNoteComment} from '@/hooks/daily-note/useDeleteDailyNoteComment';
import {getMemberId} from '@/utils/userData';
import {RoleItem} from '@/enum/roleItem';

import CommentCount from '@/components/atoms/Comment/CommentCount';
import CommentItem from '@/components/molecules/Board/CommentItem';

interface CommentSectionProps {
  commentCount: number;
  comments: Comment[];
  onReplyClick: (commentId: number) => void;
  selectedCommentId: number;
}

const CommentSection = ({
  commentCount,
  comments,
  onReplyClick,
  selectedCommentId,
}: CommentSectionProps) => {
  const {dailyNoteId} = useParams();
  const deleteMutation = useDeleteDailyNoteComment();

  const handleDeleteComment = (commentId: number) => {
    deleteMutation.mutate({
      memberId: getMemberId()!,
      dailyNoteId: Number(dailyNoteId),
      dailyNoteCommentId: commentId,
    });
  };

  const writerNameByRole = (comment: Comment | Child) => {
    if (comment.role === RoleItem.Director) {
      return '유치원 원장님';
    } else if (comment.role === RoleItem.Teacher) {
      return `${comment.banName} ${ROLE_NAMES[comment.role]}`;
    } else if (comment.role === RoleItem.Guardian) {
      return `${comment.name} ${ROLE_NAMES[comment.role]}`;
    }
    return;
  };

  return (
    <div className="px-4 pt-3 mb-2 space-y-2">
      <div className="m-2 text-sm">
        <CommentCount count={commentCount} />
      </div>
      {comments.map(comment => (
        <div key={comment.id}>
          <CommentItem
            profile={comment.picture}
            writer={writerNameByRole(comment) || ''}
            banName={comment.role === RoleItem.Guardian ? comment.banName : ''}
            content={comment.content}
            date={comment.createdTime}
            onClick={() => onReplyClick(comment.id)}
            isSelected={comment.id === selectedCommentId}
            canDelete={comment.canDelete}
            onDelete={() => handleDeleteComment(comment.id)}
          />
          {comment.childs.length > 0 && (
            <div className="pl-3 mt-4 space-y-4">
              {comment.childs.map(reply => (
                <CommentItem
                  key={reply.id}
                  profile={reply.picture}
                  writer={writerNameByRole(reply) || ''}
                  banName={
                    reply.role === RoleItem.Guardian ? reply.banName : ''
                  }
                  content={reply.content}
                  date={reply.createdTime}
                  onClick={() => onReplyClick(comment.id)}
                  isReply
                  canDelete={reply.canDelete}
                  onDelete={() => handleDeleteComment(Number(reply.id))}
                />
              ))}
            </div>
          )}
        </div>
      ))}
    </div>
  );
};

export default CommentSection;
