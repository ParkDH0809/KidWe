import React, {Children, isValidElement} from 'react';
import Button from '@/components/atoms/Button/Button';
import ModalHeader from '@/components/atoms/Modal/ModalHeader';
import ModalBody from '@/components/atoms/Modal/ModalBody';
import ModalBackground from '@/components/atoms/Modal/ModalBackground';

function getModalHeader(children: React.ReactNode) {
  const ModalHeaderType = (<ModalHeader />).type;
  const childrenArray = Children.toArray(children);
  return childrenArray
    .filter(child => isValidElement(child) && child.type === ModalHeaderType)
    .slice(0, 1);
}

function getModalBody(children: React.ReactNode) {
  const ModalBodyType = (<ModalBody />).type;
  const childrenArray = Children.toArray(children);
  return childrenArray
    .filter(child => isValidElement(child) && child.type === ModalBodyType)
    .slice(0, 1);
}

function getModalButton(children: React.ReactNode) {
  const ModalButtonType = (<Button label="" onClick={() => {}} />).type;
  const childrenArray = Children.toArray(children);
  return childrenArray
    .filter(child => isValidElement(child) && child.type === ModalButtonType)
    .slice(0, 2);
}

function getModalBackground(children: React.ReactNode) {
  const ModalBackgroundType = (<ModalBackground />).type;
  const childrenArray = Children.toArray(children);
  return childrenArray
    .filter(
      child => isValidElement(child) && child.type === ModalBackgroundType
    )
    .slice(0, 1);
}

export interface ModalMainProps {
  children?: React.ReactNode;
  isOpen: boolean;
}

const ModalMain = ({children, isOpen}: ModalMainProps) => {
  if (!isOpen) {
    return null;
  }

  const modalHeader = getModalHeader(children);
  const modalBody = getModalBody(children);
  const modalButtons = getModalButton(children);
  const modalBackground = getModalBackground(children);

  return (
    <div className="fixed top-0 bottom-0 left-0 right-0 z-40 flex items-center justify-center w-screen h-screen modal">
      <div className="box-border z-30 flex flex-col items-center justify-center max-h-screen px-10 py-10 bg-white rounded-lg shadow-md min-w-fit min-h-fit">
        {modalHeader}
        {modalBody}
        <div className="flex justify-between w-full px-8 space-x-3">
          {modalButtons}
        </div>
      </div>
      {modalBackground}
    </div>
  );
};

export default ModalMain;
