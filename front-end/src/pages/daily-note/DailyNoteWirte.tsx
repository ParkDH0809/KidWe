import {useState, useEffect, useCallback} from 'react';
import {useNavigate} from 'react-router-dom';
import {useRecoilState, useResetRecoilState} from 'recoil';
import {toast} from 'react-toastify';

import {useGetKidInfo} from '@/hooks/my-page/useGetKidInfo';
import {dailyNoteFormState} from '@/recoil/atoms/daily-note/dailyNoteFormState';
import {usePostDailyNote} from '@/hooks/daily-note/usePostDailyNote';
import {getMemberId, getKidId, getMemberRole} from '@/utils/userData';
import {getFullImageSource} from '@/utils/getFullImageSource';
import {containerHeaderClass} from '@/styles/styles';

import Header from '@/components/organisms/Navigation/Header';
import ProfileImage from '@/components/atoms/Image/ProfileImage';
import TextEditor from '@/components/organisms/Board/TextEditor';
import MemoChildSelect from '@/components/organisms/Memo/MemoChildSelect';
import ArticleImageList from '@/components/molecules/List/ArticleImageList';
import ImageIcon from '@/assets/icons/pic_line.svg?react';
import ButtonBar from '@/components/organisms/Navigation/ButtonBar';
import ScheduleModal from '@/components/organisms/Modal/ScheduleModal';
import DailyNoteAutoCreateModal from '@/components/organisms/Modal/DailyNoteAutoCreateModal';
import {RoleItem} from '@/enum/roleItem';

const DailyNoteWrite = () => {
  const navigate = useNavigate();
  const kidId = getKidId();
  const {data, isLoading} = useGetKidInfo(kidId!);
  const {mutate} = usePostDailyNote();
  const [formState, setFormState] = useRecoilState(dailyNoteFormState);
  const resetFormState = useResetRecoilState(dailyNoteFormState);
  const [files, setFiles] = useState<File[]>([]);
  const [imagePreviews, setImagePreviews] = useState<string[]>([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isScheduled, setIsScheduled] = useState(false);
  const userRole = getMemberRole();

  useEffect(() => {
    if (!isLoading && userRole === RoleItem.Guardian && kidId) {
      console.log('Data Loaded: ', kidId);
      setFormState(prev => ({...prev, kidId: kidId!}));
    }
  }, [isLoading, data, kidId, userRole, setFormState]);

  useEffect(() => {
    return () => {
      resetFormState();
    };
  }, [resetFormState]);

  const handleEditorChange = (value: string) => {
    setFormState(prev => ({...prev, content: value}));
  };

  const handleAddImage = (selectedFiles: FileList) => {
    const currentFileCount = files.length;

    if (currentFileCount + selectedFiles.length > 10) {
      toast.warn('최대 10장까지 업로드할 수 있습니다.');
      return;
    }

    const newFiles = Array.from(selectedFiles);
    setFiles([...files, ...newFiles]);

    const newImagePreviews = newFiles.map(file => URL.createObjectURL(file));
    setImagePreviews([...imagePreviews, ...newImagePreviews]);
  };

  const handleFormSubmit = useCallback(() => {
    const formData = new FormData();

    formData.append(
      'dailynote',
      new Blob([JSON.stringify(formState)], {type: 'application/json'})
    );

    files.forEach(file => {
      formData.append('images', file);
    });

    mutate({memberId: getMemberId()!, formData});
    navigate('/daily-notes');
  }, [formState, files, mutate, navigate]);

  const options = [
    {
      text: '예약 전송',
      onClick: () => setIsModalOpen(true),
    },
  ];

  const handleFormScheduledSubmit = (
    selectedDate: string,
    selectedTime: string
  ) => {
    setFormState(prev => ({
      ...prev,
      sendTime: `${selectedDate} ${selectedTime}`,
    }));

    setIsScheduled(true);
  };

  useEffect(() => {
    if (isScheduled) {
      handleFormSubmit();
    }
  }, [handleFormSubmit, isScheduled]);

  return (
    <div className="flex flex-col h-screen">
      <Header title="알림장" buttonType="back" />
      <div className={containerHeaderClass}>
        <div className="px-6 py-4">
          {userRole === RoleItem.Teacher && (
            <div className="flex flex-row items-center justify-between">
              <MemoChildSelect type="daily-note" />
              <DailyNoteAutoCreateModal />
            </div>
          )}
          {userRole === RoleItem.Guardian && (
            <div>
              <p className="mb-2">내 아이</p>
              <ProfileImage
                src={getFullImageSource(data?.picture)}
                size="3rem"
              />
            </div>
          )}
        </div>
        <TextEditor value={formState.content} onChange={handleEditorChange} />
        <div className="p-6 mt-10">
          <div className="flex flex-row items-center mb-2">
            <ImageIcon fill="black" width={24} height={25} />
            <p className="ms-2">사진 선택</p>
            <p className="text-xs ms-5">{files.length}/10</p>
          </div>
          <ArticleImageList
            images={imagePreviews}
            isEditable
            onAddImage={handleAddImage}
          />
        </div>
      </div>
      <ButtonBar
        label="전송하기"
        disabled={formState.kidId === undefined}
        variant={formState.kidId === undefined ? 'negative' : 'positive'}
        onClick={handleFormSubmit}
        options={options}
      />
      <ScheduleModal
        isOpen={isModalOpen}
        onClose={() => setIsModalOpen(false)}
        onSubmit={handleFormScheduledSubmit}
      />
    </div>
  );
};

export default DailyNoteWrite;
