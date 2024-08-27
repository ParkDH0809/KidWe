import {useState} from 'react';

import Button from '@/components/atoms/Button/Button';
import MoreButton from '@/components/molecules/DropdownButton/MoreButton';
import TempStorageModal from '@/components/organisms/Modal/TempStorageModal '; // 임시 보관함 모달 import

interface Option {
  text: string;
  onClick: () => void;
}

interface ButtonBarProps {
  label: string;
  variant?: 'positive' | 'negative';
  disabled: boolean;
  onClick: React.MouseEventHandler<HTMLButtonElement>;
  options?: Option[];
}

const ButtonBar = ({
  label,
  variant = 'positive',
  disabled,
  onClick,
  options,
}: ButtonBarProps) => {
  const [isTempStorageModalOpen, setIsTempStorageModalOpen] = useState(false);

  const handleTempStorageSelect = (selectedItem: string) => {
    console.log('선택된 임시 보관함 항목:', selectedItem);
  };

  return (
    <nav className="box-border fixed bottom-0 flex flex-row items-center w-full py-4 text-base bg-white border-t px-7">
      {options && (
        <div className="flex items-center justify-center w-12 h-10 bg-gray-100 rounded-sm me-2">
          <MoreButton position="right" isUp>
            {options?.map(option => (
              <MoreButton.Option
                key={option.text}
                text={option.text}
                onClick={option.onClick}
              />
            ))}
          </MoreButton>
        </div>
      )}
      <Button
        label={label}
        round="small"
        size="large"
        variant={variant}
        disabled={disabled}
        onClick={onClick}
      />
      <TempStorageModal
        isOpen={isTempStorageModalOpen}
        onClose={() => setIsTempStorageModalOpen(false)}
        onSelect={handleTempStorageSelect}
      />
    </nav>
  );
};

export default ButtonBar;
