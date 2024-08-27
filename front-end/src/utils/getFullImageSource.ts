import noProfile from '@/assets/image/no-profile.png';

export const getFullImageSource = (img: string | undefined) => {
  if (img) {
    return `${import.meta.env.VITE_IMG_URL}/${img}`;
  } else {
    return noProfile;
  }
};

export const getFullThumbnailImageSource = (img: string | undefined) => {
  if (img) {
    return `${import.meta.env.VITE_THUMBNAIL_IMG_URL}${img}`;
  } else {
    return noProfile;
  }
};
