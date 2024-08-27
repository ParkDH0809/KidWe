import {useNavigate} from 'react-router-dom';
import BackButton from '@/components/atoms/Button/BackButton';
import CloseButton from '@/components/atoms/Button/CloseButton';

interface HeaderProps {
  title: string;
  buttonType?: 'back' | 'close';
}

const Header = ({title, buttonType}: HeaderProps) => {
  const navigate = useNavigate();

  const handleGoHome = () => {
    navigate('/');
  };

  return (
    <header className="fixed top-0 left-0 right-0 z-20 flex items-center justify-between w-full p-3.5 bg-secondary">
      <div className="flex items-center w-6">
        {buttonType === 'back' && <BackButton />}
        {buttonType === 'close' && <CloseButton onClick={handleGoHome} />}
      </div>
      <h1 className="flex-grow font-semibold text-center">{title}</h1>
      <div className="w-6"></div>
    </header>
  );
};

export default Header;
