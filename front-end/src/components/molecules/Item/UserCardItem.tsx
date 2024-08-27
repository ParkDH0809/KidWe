import ProfileImage from '@/components/atoms/Image/ProfileImage';
import MoreButton from '@/components/molecules/DropdownButton/MoreButton';
import BracketButton from '@/components/atoms/Button/BracketButton';

interface Option {
  text: string;
  onClick: () => void;
}

interface UserCardItemProps {
  profile: string | undefined;
  userName: string;
  banName?: string;
  cardType: 'basic' | 'detail' | 'arrow';
  options?: Option[];
}

const UserCardItem = ({
  profile,
  userName,
  banName,
  cardType,
  options,
}: UserCardItemProps) => {
  const arrowClass = cardType === 'arrow' ? 'rounded-lg' : 'border-b';

  return (
    <div
      className={`flex justify-between items-center py-5 px-8 bg-white ${arrowClass}`}
    >
      <div className="flex items-center space-x-4">
        <ProfileImage src={profile || ''} size="2.9rem" />
        <span className="text-lg font-medium">{userName}</span>
        {banName && <span className="text-sm font-medium">{banName}</span>}
      </div>
      {cardType === 'detail' && (
        <MoreButton>
          {options?.map(option => (
            <MoreButton.Option
              key={option.text}
              text={option.text}
              onClick={option.onClick}
            />
          ))}
        </MoreButton>
      )}
      {cardType === 'arrow' && <BracketButton direction="right" />}
    </div>
  );
};

export default UserCardItem;
