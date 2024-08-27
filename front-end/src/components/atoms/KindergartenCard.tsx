import Icon from '@/assets/image/sunflower.png';

interface KindergartenCardProps {
  kindergartenName: string;
}

const KindergartenCard = ({kindergartenName}: KindergartenCardProps) => {
  return (
    <div className="flex items-center space-x-2">
      <img className="w-7" src={Icon} alt="Icon" />
      <span className="text-xl font-semibold">{kindergartenName}</span>
    </div>
  );
};

export default KindergartenCard;
