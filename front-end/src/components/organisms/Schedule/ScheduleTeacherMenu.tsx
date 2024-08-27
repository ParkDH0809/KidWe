import Select from '@/components/molecules/DropdownButton/Select';
import {getMemberRole} from '@/utils/userData';
import ScheduleAdd from './ScheduleAdd';
import {Ban} from '@/types/kindergarten/Ban';
import {Dayjs} from 'dayjs';

interface ScheduleTeacherMenuProps {
  bans: Ban[] | undefined;
  date: Dayjs;
  onBanChange: (value: string) => void;
}

export const ScheduleTeacherMenu = ({
  bans,
  date,
  onBanChange,
}: ScheduleTeacherMenuProps) => {
  return (
    <>
      {getMemberRole() !== 'ROLE_GUARDIAN' && (
        <div className="flex items-center justify-between w-full">
          <div>
            {getMemberRole() === 'ROLE_DIRECTOR' && (
              <>
                <Select label="반" size="medium" onChange={onBanChange}>
                  {bans &&
                    bans.map(ban => (
                      <Select.Option
                        key={ban.id}
                        id={`${ban.id}`}
                        text={ban.name}
                      />
                    ))}
                </Select>
              </>
            )}
          </div>
          <ScheduleAdd defaultDate={date} />
        </div>
      )}
    </>
  );
};
