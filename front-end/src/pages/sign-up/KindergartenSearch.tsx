import React, {useState, useEffect} from 'react';
import InputForm from '@/components/molecules/InputForm/InputForm';
import NoResult from '@/components/atoms/NoResult';
import KindergartenItem from '@/components/molecules/Item/KindergartenItem';
import {useNavigate} from 'react-router-dom';
// import {useMutation} from '@tanstack/react-query';
// import {Signup} from '@/recoil/atoms/signup/Signup';
import {useRecoilState} from 'recoil';
// import SelectMain from '@/components/molecules/DropdownButton/SelectMain';
import Select from '@/components/molecules/DropdownButton/Select';
import {CityOptions} from '@/constants/city';
import {DistrictOptions} from '@/constants/district';
import type {District} from '@/constants/district';
import {getKindergartenSearch} from '@/apis/signup/getKindergartenSearch';
import Spinner from '@/components/atoms/Loader/Spinner';
import type {GetKindergarten} from '@/types/kindergarten/GetKindergarten';
import {SignupTeacherState} from '@/recoil/atoms/signup/signupTeacher';
import {SignupGuardianState} from '@/recoil/atoms/signup/signupGuardian';
import {getMemberRole} from '@/utils/userData';
import {toast, ToastContainer} from 'react-toastify';
import {getMemberId} from '@/utils/userData';

const KindergartenSearch: React.FC = () => {
  const navigate = useNavigate();
  const [signupTeacher, setSignupTeacher] = useRecoilState(SignupTeacherState);
  const [signupGuardian, setSignupGuardian] =
    useRecoilState(SignupGuardianState);
  const [selectedCity, setSelectedCity] = useState<string | null>(null);
  const [selectedDistricts, setSelectedDistricts] = useState<District[]>([]);
  const [selectedDistrict, setSelectedDistrict] = useState<string | null>(null);
  const [inputValue, setInputValue] = useState('');
  const [searchResult, setSearchResult] = useState('');
  const [data, setData] = useState<GetKindergarten[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null | unknown>(null);
  // const [role, setrole] = useState('ROLE_GUARDIAN');
  const [role, setrole] = useState(getMemberRole());
  const [isNoResult, setIsNoResult] = useState(true);

  const handleKindergartenSearchButtonClick = (kindergartenId: number) => {
    // 버튼 누를 때의 유치원 정보를 recoil에 담아주기!
    // recoil에는 kindergartenId, banId만 넘겨주면 됨
    if (role === 'ROLE_TEACHER') {
      setSignupTeacher(prevState => ({
        ...prevState,
        kindergartenId,
        memberId: getMemberId()!,
      }));
    } else if (role === 'ROLE_GUARDIAN') {
      setSignupGuardian(prevState => ({
        ...prevState,
        dto: {
          ...prevState.dto,
          kindergartenId,
          memberId: getMemberId()!,
        },
      }));
    }

    navigate('/auth/signup/kindergarten/ban');
  };
  const handleCityChange = (value: string) => {
    const city = CityOptions.find(city => city.value === value);
    if (city) {
      setSelectedCity(city.label);
      setSelectedDistrict(''); // 도시 변경 시, 선택된 구 초기화
      setSelectedDistricts(DistrictOptions[city.value] || []);
    }
  };
  const handleDistrictChange = (value: string) => {
    const district = selectedDistricts.find(
      district => district.value === value
    );
    if (district) {
      setSelectedDistrict(district.label);
    }
  };

  const handleSearch = async () => {
    setIsNoResult(false);
    setSearchResult(inputValue);
    console.log(searchResult);
    setIsLoading(true);
    setError(null);
    console.log(error);
    try {
      const response = await getKindergartenSearch({
        sido: selectedCity,
        sigungu: selectedDistrict,
        search: inputValue,
      });
      setData(response);
    } catch (error) {
      setError(error);
    } finally {
      setIsLoading(false);
    }
  };
  const handleFocus = () => {
    if (inputValue === '') {
      setInputValue('');
    }
  };
  const handleKeyDown = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === 'Enter') {
      handleSearch();
    }
  };

  useEffect(() => {
    if (role !== 'ROLE_TEACHER' && role !== 'ROLE_GUARDIAN') {
      toast.error('당신은 원장님입니다.', {
        onClose: () => navigate('/auth/login'),
      });
    }
  }, [role, navigate]);

  return (
    <div className="flex flex-col items-center w-full h-full max-h-full px-5 py-6 space-y-8">
      <ToastContainer
        position="top-center"
        autoClose={1000}
        hideProgressBar
        pauseOnFocusLoss
        limit={1}
      />
      <p className="text-lg">원 위치를 검색해주세요</p>
      <div className="flex items-center space-x-8">
        {/* 첫 dropdown은 시 */}
        <Select size="medium" label="시" onChange={handleCityChange}>
          {CityOptions.map(city => (
            <Select.Option key={city.id} text={city.label} id={city.value} />
          ))}
        </Select>
        {/* 두 번째 dropdown은 군구 */}
        <Select size="medium" label="군구" onChange={handleDistrictChange}>
          {selectedDistricts.map(district => (
            <Select.Option
              key={district.id}
              text={district.label}
              id={district.value}
            />
          ))}
        </Select>
      </div>
      <InputForm
        inputValue={inputValue}
        buttonLabel="검색"
        placeholder="원 검색"
        onClick={handleSearch}
        setValue={setInputValue}
        onFocus={handleFocus}
        onKeyDown={handleKeyDown}
      ></InputForm>
      <div className="flex w-full border rounded-lg h-[472px]">
        {isNoResult ? (
          <div className="flex items-center justify-center w-full ">
            <NoResult text="유치원을 검색해주세요" />
          </div>
        ) : (
          <div className="flex items-center justify-center w-full mx-4 mt-8 max-h-96">
            {isLoading && <Spinner />}
            {data.length !== 0 ? (
              <div className="w-full overflow-y-scroll h-[400px]">
                {data.map(item => (
                  <KindergartenItem
                    key={item.id}
                    name={item.name}
                    address={item.address}
                    onClick={() => handleKindergartenSearchButtonClick(item.id)}
                  />
                ))}
              </div>
            ) : (
              <NoResult text="유치원 검색 결과가 없습니다." />
            )}
          </div>
        )}
      </div>
    </div>
  );
};

export default KindergartenSearch;
