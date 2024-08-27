import Header from '@/components/organisms/Navigation/Header';
import NavigationBar from '@/components/organisms/Navigation/NavigationBar';
import {containerNavigatorClass} from '@/styles/styles';
import {useGetKindergartenInfo} from '@/hooks/schedule/useGetKindergartenInfo';
import {getKindergartenId} from '@/utils/userData';
import {useLoading} from '@/hooks/loading/useLoading';
import Icon from '@/assets/icons/phone-call-fill.svg?react';

const KindergartenManagement = () => {
  const {data, isLoading} = useGetKindergartenInfo(getKindergartenId()!);
  console.log(data, 'data는 어떨까');
  useLoading(isLoading);
  return (
    <div
      className={`${containerNavigatorClass} flex flex-col items-center h-screen w-full max-w-screen`}
    >
      <Header title="유치원 설정" buttonType="back" />
      <div className="flex flex-col items-center content-center justify-center p-10 space-y-4">
        <p className="text-4xl">{data?.name}</p>
        <p>{data?.address}</p>
        <p>{data?.addressDetail}</p>
        <div className="flex space-x-2">
          <p> {data?.tel}</p>

          <a
            href={`tel:${data?.tel}`}
            className="bg-[#30D240] rounded-full w-8 h-8 flex items-center justify-center me-5"
          >
            <Icon />
          </a>
        </div>
      </div>
      <NavigationBar />
    </div>
  );
};

export default KindergartenManagement;
