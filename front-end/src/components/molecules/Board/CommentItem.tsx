import {getFullImageSource} from '@/utils/getFullImageSource';
import ReplyIcon from '@/assets/icons/reply-enter.svg?react';
import ProfileImage from '@/components/atoms/Image/ProfileImage';
import MoreButton from '@/components/molecules/DropdownButton/MoreButton';
import Tag from '@/components/atoms/Tag/Tag';

interface CommentItemProps {
  profile: string;
  writer: string;
  banName?: string;
  content: string;
  date: string;
  onClick: () => void;
  isReply?: boolean;
  isSelected?: boolean;
  canDelete?: boolean;
  onDelete: () => void;
}

const CommentItem = ({
  profile,
  writer,
  banName,
  content,
  date,
  onClick,
  isReply = false,
  isSelected = false,
  canDelete = false,
  onDelete,
}: CommentItemProps) => {
  return (
    <div
      className={`flex flex-row justify-between rounded-sm p-2 ${
        isSelected ? 'bg-yellow-50' : 'bg-white'
      }`}
    >
      <div className="flex flex-row gap-3">
        {isReply && (
          <div>
            <ReplyIcon width={30} height={30} />
          </div>
        )}
        <ProfileImage src={getFullImageSource(profile)} size="2rem" />
        <div className="flex flex-col">
          <div className="flex flex-row items-end gap-2 mb-0.5">
            <p className="text-sm font-semibold">{writer}</p>
            <p className="text-2xs">{banName}</p>
          </div>
          <p className="mb-2 text-sm">{content}</p>
          <div className="flex flex-row items-center gap-3">
            <Tag
              text="답글"
              size="small"
              backgroundColor="#EAEAEA"
              onClick={onClick}
            />
            <p className="text-2xs">{date}</p>
          </div>
        </div>
      </div>
      {canDelete && (
        <MoreButton align="vertical">
          <MoreButton.Option text="삭제하기" onClick={onDelete} />
        </MoreButton>
      )}
    </div>
  );
};

export default CommentItem;
