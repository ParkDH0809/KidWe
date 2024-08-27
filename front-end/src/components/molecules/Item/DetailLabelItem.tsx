import {getFullImageSource} from '@/utils/getFullImageSource';
import Tag from '@/components/atoms/Tag/Tag';

interface MedicationDetailItemProps {
  title: string;
  content?: string;
  color?: string;
  imageUrl?: string;
}

const DetailLabelItem = ({
  title,
  content,
  color = '#FFEC9E',
  imageUrl,
}: MedicationDetailItemProps) => {
  return (
    <div className="flex items-center space-x-6">
      <Tag text={title} backgroundColor={color} size="medium" />
      {imageUrl ? (
        <img
          src={getFullImageSource(imageUrl)}
          className="object-cover w-32 h-32 rounded-lg"
        />
      ) : (
        <p className="border-b-2" style={{borderColor: color}}>
          {content}
        </p>
      )}
    </div>
  );
};

export default DetailLabelItem;
