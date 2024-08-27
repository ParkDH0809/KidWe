import Select from '@/components/molecules/DropdownButton/Select';
import {RoleItem} from '@/enum/roleItem';
import {useGetKindergartenInfo} from '@/hooks/schedule/useGetKindergartenInfo';
import {getKindergartenId} from '@/utils/userData';

interface DirectorSelectItemProps {
  memberRole: RoleItem;
  onBanChange: (value: number) => void;
}

const DirectorSelectItem = ({
  memberRole,
  onBanChange,
}: DirectorSelectItemProps) => {
  const {data} = useGetKindergartenInfo(getKindergartenId()!);
  const bans = data?.bans;

  const handleBanChange = (value: string) => {
    onBanChange(+value);
  };

  return (
    <>
      {memberRole === RoleItem.Director && (
        <div className="flex justify-end w-full">
          <Select size="medium" label="반선택" onChange={handleBanChange}>
            {bans &&
              bans.map(ban => (
                <Select.Option key={ban.id} text={ban.name} id={`${ban.id}`} />
              ))}
          </Select>
        </div>
      )}
    </>
  );
};

export default DirectorSelectItem;
