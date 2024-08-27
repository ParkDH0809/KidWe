import Dropdown from '@/components/atoms/Dropdown/Dropdown';
import SelectButton from '@/components/atoms/Button/SelectButton';
import Option, {OptionProps} from '@/components/atoms/Option/Option';
import React, {isValidElement, useEffect, useRef, useState} from 'react';
import {useClickOutside} from '@/hooks/useClickOutside';

export interface SelectProps {
  label?: string;
  size?: 'small' | 'medium' | 'large';
  onChange?: (value: string) => void;
  children?: React.ReactNode;
}

function getSize(size: string) {
  switch (size) {
    case 'small':
      return 'w-20';
    case 'medium':
      return 'w-40';
    default:
      return 'w-full';
  }
}

function addEventListenerToChild(
  children: React.ReactNode,
  eventListener: ({id, text}: OptionProps) => void
) {
  const optionType = (<Option text="" />).type;
  const childrenArray = React.Children.toArray(children);
  return childrenArray
    .filter(child => isValidElement(child) && child.type === optionType)
    .map(child => {
      if (isValidElement<OptionProps>(child) && child.type === optionType) {
        return React.cloneElement(child, {
          onClick: () => eventListener(child.props),
        });
      }
      return child;
    });
}

const SelectMain = ({
  label = '',
  size = 'large',
  onChange,
  children,
}: SelectProps) => {
  const selectRef = useRef<HTMLDivElement>(null);
  const [isOpen, setIsOpen] = useClickOutside(selectRef);

  const [selectedOption, setSelectedOption] = useState<string | undefined>(
    undefined
  );

  const handleButtonClick = () => {
    setIsOpen(!isOpen);
  };

  const handleOptionClick = ({id, text}: OptionProps) => {
    setSelectedOption(text);
    onChange?.(id !== undefined ? id : text);
    setIsOpen(false);
  };

  const sizeClass = getSize(size);
  const childrenWithEvent = addEventListenerToChild(
    children,
    handleOptionClick
  );

  useEffect(() => {
    setSelectedOption(undefined);
    // console.log(label);
  }, [label]);

  return (
    <div ref={selectRef} className={`${sizeClass} box-border relative z-10`}>
      <SelectButton
        onClick={handleButtonClick}
        label={selectedOption === undefined ? label : selectedOption}
      />
      <div className="absolute w-full top-10 min-w-fit">
        <Dropdown isOpen={isOpen}>{childrenWithEvent}</Dropdown>
      </div>
    </div>
  );
};

export default SelectMain;
