import {RoleItem} from '@/enum/roleItem';
import {getFullImageSource} from '@/utils/getFullImageSource';
import {isDirector} from '@/utils/auth/isDirector';
import {isTeacher} from '@/utils/auth/isTeacher';
import {isGuardian} from '@/utils/auth/isGuardian';

import kindergartenImage from '@/assets/image/kindergarten.png';
import ScheduledIcon from '@/assets/icons/time-line.svg?react';
import SendIcon from '@/assets/icons/send-plane-line.svg?react';
import ProfileImage from '@/components/atoms/Image/ProfileImage';
import ArrowIcon from '@/assets/icons/arrow-fat.svg?react';

interface ScheduledUserCardProps {
  profile?: string;
  userName: string;
  banName?: string;
  writerRole: RoleItem;
  sendTime?: string;
  isSchedule?: boolean;
}

const ScheduledUserCard = ({
  profile,
  userName,
  banName,
  writerRole,
  sendTime,
  isSchedule = false,
}: ScheduledUserCardProps) => {
  const isReceived = (() => {
    if (isTeacher() || isDirector()) {
      if (writerRole === RoleItem.Teacher) {
        return false;
      } else if (writerRole === RoleItem.Guardian) {
        return true;
      }
    } else if (isGuardian()) {
      if (writerRole === RoleItem.Teacher) {
        return true;
      } else if (writerRole === RoleItem.Guardian) {
        return false;
      }
    }
    return false;
  })();

  const scheduledClass = isSchedule ? 'bg-[#F7F7F7]' : 'bg-white';
  const arrowFillColor = isReceived ? '#2F5DFF' : '#FF2F2F';
  const arrowRotation = isReceived ? '' : 'rotate-180';

  return (
    <div
      className={`flex justify-between items-center py-5 px-8 border-b ${scheduledClass}`}
    >
      <div className="flex items-center space-x-4">
        {writerRole === RoleItem.Guardian ? (
          <div className="relative">
            <ProfileImage src={getFullImageSource(profile)} size="2.75rem" />
            <ArrowIcon
              width="20"
              height="17"
              fill={arrowFillColor}
              className={`${arrowRotation} absolute transform translate-x-1/2 translate-y-1/2 bottom-2 right-1`}
            />
          </div>
        ) : (
          <div className="relative">
            <img className="w-11" src={kindergartenImage} />
            <ArrowIcon
              width="20"
              height="17"
              fill={arrowFillColor}
              className={`${arrowRotation} absolute transform translate-x-1/2 translate-y-1/2 bottom-2 right-1`}
            />
          </div>
        )}
        <span className="text-lg font-medium">{userName}</span>
        {banName && <span className="text-sm font-medium">{banName}</span>}
      </div>
      <div className="flex flex-col items-end">
        {isSchedule ? (
          <ScheduledIcon fill="#8A8A8A" width={22} height={22} />
        ) : (
          <SendIcon fill="#8A8A8A" width={22} height={22} />
        )}
        <p className="text-xs">{sendTime}</p>
      </div>
    </div>
  );
};

export default ScheduledUserCard;
