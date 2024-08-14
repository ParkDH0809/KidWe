import noProfile from '@/assets/no-profile.png';

export const getFullImageSource = (img: string | undefined) => {
  if (img) {
    return `${import.meta.env.VITE_IMG_URL}/${img}`;
  } else {
    return noProfile;
  }
};
