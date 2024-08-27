import React from 'react';

interface TagProps {
  text: string;
  backgroundColor?: string;
  textColor?: 'black' | 'white';
  size?: 'small' | 'medium' | 'large';
  onClick?: (value: string) => void;
}

const Tag = ({
  text,
  backgroundColor = '#FFF1A7',
  textColor = 'black',
  size = 'medium',
  onClick,
}: TagProps) => {
  const colorClass = textColor === 'black' ? 'text-black' : 'text-white';
  // small은 커스텀해서 사용하기
  let sizeClass: string = '';

  if (size === 'small') {
    sizeClass = 'text-xs px-3 py-1 h-6';
  } else if (size === 'medium') {
    sizeClass = 'py-1 px-3';
  } else if (size === 'large') {
    sizeClass = 'py-2 px-4';
  }

  const handleClick = () => {
    onClick?.(text);
  };

  return (
    <div
      className={`${colorClass} ${sizeClass} rounded-full text-center flex items-center`}
      style={{backgroundColor}}
      onClick={handleClick}
    >
      <p className="font-medium whitespace-nowrap">{text}</p>
    </div>
  );
};

export default Tag;
