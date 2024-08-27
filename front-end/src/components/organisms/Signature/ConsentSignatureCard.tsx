import {getFullImageSource} from '@/utils/getFullImageSource';

interface ConsentSignatureCardProps {
  text: string;
  date: string;
  parentName: string;
  signatureUrl: string;
}

const ConsentSignatureCard = ({
  text,
  date,
  parentName,
  signatureUrl,
}: ConsentSignatureCardProps) => {
  const textLines = text.split('\\n');

  return (
    <div className="p-6 space-y-2">
      <div>
        {textLines.map((line, index) => (
          <p key={index} className="text-sm text-gray-150">
            {line}
          </p>
        ))}
      </div>
      <div className="flex flex-row items-start justify-between">
        <p className="text-sm ">
          {date} {parentName}
        </p>
        <img
          className="object-contain w-20"
          src={getFullImageSource(signatureUrl)}
        />
      </div>
    </div>
  );
};

export default ConsentSignatureCard;
