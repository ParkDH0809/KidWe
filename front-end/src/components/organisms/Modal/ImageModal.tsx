import {useState} from 'react';
import {Swiper, SwiperSlide} from 'swiper/react';
import 'swiper/css';

import {getFullImageSource} from '@/utils/getFullImageSource';

import CloseButton from '@/components/atoms/Button/CloseButton';
import DownloadButton from '@/components/atoms/Button/DownloadButton';

interface ImageModalProps {
  images: string[];
  selectedImage: number;
  onClose: () => void;
}

const ImageModal = ({images, selectedImage, onClose}: ImageModalProps) => {
  const [currentIndex, setCurrentIndex] = useState(selectedImage);

  return (
    <div className="fixed inset-0 z-40 flex items-center justify-center bg-black modal">
      <div className="absolute top-0 z-40 flex items-center justify-between w-full px-5 py-3 bg-black bg-opacity-50">
        <CloseButton color="white" onClick={onClose} />
        <span className="text-xl text-white">
          {currentIndex + 1}/{images.length}
        </span>
        <DownloadButton imageUrl={getFullImageSource(images[currentIndex])} />
      </div>
      <div className="absolute inset-0 w-full h-full">
        <Swiper
          spaceBetween={0}
          slidesPerView={1}
          initialSlide={selectedImage}
          style={{
            height: '100vh',
          }}
          onSlideChange={swiper => setCurrentIndex(swiper.activeIndex)}
        >
          {images.map((src, index) => (
            <SwiperSlide key={index}>
              <img
                src={getFullImageSource(src)}
                alt={`Image ${index + 1}`}
                className="object-contain w-full h-full"
              />
            </SwiperSlide>
          ))}
        </Swiper>
      </div>
    </div>
  );
};

export default ImageModal;
