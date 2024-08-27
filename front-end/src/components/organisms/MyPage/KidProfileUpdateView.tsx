import XSmallButton from '@/components/atoms/Button/XSmallButton';
import ImageUploadButton from '@/components/molecules/Button/ImageUploadButton';
import {patchKidPictureState} from '@/recoil/atoms/my-page/kidPicture';
import {kidInfoPicture} from '@/recoil/selectors/my-page/kidInfoPicture';
import {getFullImageSource} from '@/utils/getFullImageSource';
import {useState} from 'react';
import {useRecoilState, useSetRecoilState} from 'recoil';

const KidProfileUpdateView = () => {
  const [kidProfile, setKidProfile] = useRecoilState(kidInfoPicture);
  const setKidProfileFile = useSetRecoilState(patchKidPictureState);
  const [preview, setPreview] = useState<string | null>(null);

  const handleChangePicture = (value: string) => {
    setPreview(value);
  };

  const handleChangeFile = (value: File) => {
    setKidProfileFile(value);
  };

  const handleDeletePicture = () => {
    setKidProfile(undefined);
    setPreview(null);
    setKidProfileFile(null);
  };

  return (
    <div className="flex flex-col w-full">
      <p>자녀 이미지</p>
      <div className="flex flex-col items-center justify-center w-full gap-1">
        <ImageUploadButton
          userPicture={
            preview === null ? getFullImageSource(kidProfile) : preview
          }
          onChangePreview={handleChangePicture}
          onChangeFile={handleChangeFile}
        />
        {kidProfile && (
          <div className="w-fit">
            <XSmallButton label="삭제" onClick={handleDeletePicture} />
          </div>
        )}
      </div>
    </div>
  );
};

export default KidProfileUpdateView;
