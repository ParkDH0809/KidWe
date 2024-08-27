import {KidAllergy} from '@/types/food/KidAllergy';
import {getFullImageSource} from '@/utils/getFullImageSource';

import sun from '@/assets/icons/sun-with-face.svg';
import moon from '@/assets/icons/full-moon-face.svg';
import hotDog from '@/assets/icons/hot-dog.svg';
import Tag from '@/components/atoms/Tag/Tag';
import ProfileImage from '@/components/atoms/Image/ProfileImage';

interface FoodInfomationItemProps {
  variant?: 'lunch' | 'snack' | 'dinner';
  menu?: string;
  allergies?: string[];
  kidAllergies?: KidAllergy[];
  onClick?: React.MouseEventHandler<HTMLDivElement>;
}

function getVariant(variant: string): {
  color: string;
  src: string;
  title: string;
} {
  switch (variant) {
    case 'snack':
      return {color: '#C0E583', src: hotDog, title: '간식'};
    case 'dinner':
      return {color: '#C99FFF', src: moon, title: '석식'};
    default:
      return {color: '#FFC36A', src: sun, title: '중식'};
  }
}

function getColorVariant(variant: string) {
  switch (variant) {
    case 'snack':
      return 'border-[#C0E583] shadow-[#C0E583]';
    case 'dinner':
      return 'border-[#C99FFF] shadow-[#C99FFF]';
    default:
      return 'border-[#FFC36A] shadow-[#FFC36A]';
  }
}

const FoodInfomationItem = ({
  variant = 'lunch',
  menu,
  allergies,
  kidAllergies,
  onClick,
}: FoodInfomationItemProps) => {
  const {color, src, title} = getVariant(variant);
  const colorClass = getColorVariant(variant);

  return (
    <div
      onClick={onClick}
      className={`w-[21rem] ${colorClass} flex items-center justify-between shadow-sm min-h-fit px-5 py-5 box-border rounded-md border text-gray-300`}
    >
      <div className="flex flex-col items-center justify-center h-full gap-2 w-14">
        <img src={src} />
        <Tag
          text={title}
          textColor="white"
          size="small"
          backgroundColor={color}
        />
      </div>
      <div className="flex flex-col justify-center w-56">
        <p className="text-xs">식단</p>
        <p className="mb-3 text-md">{menu}</p>
        {allergies?.length !== 0 && (
          <>
            <p className="text-xs">알러지</p>
            <div className="flex flex-wrap gap-1 mb-3">
              {allergies?.map((allergy, idx) => (
                <Tag key={idx} text={allergy} size="small" />
              ))}
            </div>
          </>
        )}
        {kidAllergies?.length !== 0 && (
          <>
            <p className="text-xs">주의 학생</p>
            <div className="flex flex-wrap gap-1 mb-3">
              {kidAllergies?.map(kid => (
                <div className="flex flex-col items-center justify-center gap-1 w-fit h-fit">
                  <ProfileImage
                    src={getFullImageSource(kid.kidImageUrl)}
                    key={kid.kidName}
                    size="2rem"
                  />
                  <p className="text-xxs">{kid.kidName}</p>
                </div>
              ))}
            </div>
          </>
        )}
      </div>
    </div>
  );
};

export default FoodInfomationItem;
