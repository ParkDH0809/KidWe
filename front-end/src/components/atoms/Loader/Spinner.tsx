import '@/components/atoms/Loader/spinner.css';

const Spinner = () => {
  return (
    <div className="absolute top-0 bottom-0 left-0 right-0 z-50 flex items-center justify-center w-full h-full modal">
      <div className="spinner"></div>
      <div className="absolute top-0 bottom-0 left-0 right-0 z-40 w-full h-full bg-gray-200 modal opacity-20"></div>
    </div>
  );
};

export default Spinner;
