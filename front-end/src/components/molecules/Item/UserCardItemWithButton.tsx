import {getFullImageSource} from '@/utils/getFullImageSource';
import ProfileImage from '@/components/atoms/Image/ProfileImage';
import XSmallButton from '@/components/atoms/Button/XSmallButton';

interface UserCardItemProps {
  profile?: string;
  userName: string;
  banName?: string;
  onClickNegative?: () => void;
  onClickPositive?: () => void;
  negativeLabel?: string;
  positiveLabel?: string;
}

const UserCardItem = ({
  profile,
  userName,
  banName,
  onClickNegative = () => {},
  onClickPositive = () => {},
  negativeLabel,
  positiveLabel,
}: UserCardItemProps) => {
  return (
    <div
      className={`flex justify-between w-full items-center py-5 px-8 bg-white border-b`}
    >
      <div className="flex items-center space-x-3">
        <ProfileImage src={getFullImageSource(profile)} size="2.9rem" />
        <span className="text-lg font-medium">{userName}</span>
        {banName && <span className="text-sm font-medium">{banName}</span>}
      </div>
      <div className="flex items-center gap-3">
        {negativeLabel && (
          <XSmallButton
            variant="negative"
            label={negativeLabel}
            onClick={onClickNegative}
          />
        )}
        {positiveLabel && (
          <XSmallButton
            variant="positive"
            label={positiveLabel}
            onClick={onClickPositive}
          />
        )}
      </div>
    </div>
  );
};

export default UserCardItem;
