import {useState, useEffect, useCallback, ChangeEvent} from 'react';
import {useNavigate} from 'react-router-dom';
import {useRecoilState, useResetRecoilState} from 'recoil';
import {toast} from 'react-toastify';

import {announcementFormState} from '@/recoil/atoms/announcement/announcementFormState';
import {usePostAnnouncement} from '@/hooks/announcement/usePostAnnouncement';
import {usePostTempStorage} from '@/hooks/announcement/usePostTempStorage';
import {useGetTempStorageDetail} from '@/hooks/announcement/useGetTempStorageDetail';
import {getMemberId} from '@/utils/userData';
import type {VoteInfo} from '@/types/announcement/VoteInfo';
import {containerHeaderClass} from '@/styles/styles';

import Header from '@/components/organisms/Navigation/Header';
import TitleInput from '@/components/atoms/Input/TitleInput';
import TextEditor from '@/components/organisms/Board/TextEditor';
import ArticleImageList from '@/components/molecules/List/ArticleImageList';
import ImageIcon from '@/assets/icons/pic_line.svg?react';
import ButtonBar from '@/components/organisms/Navigation/ButtonBar';
import VoteModal from '@/components/organisms/Modal/VoteModal';
import TempStorageModal from '@/components/organisms/Modal/TempStorageModal ';

const AnnouncementWrite = () => {
  const navigate = useNavigate();
  const {mutate: postAnnouncement} = usePostAnnouncement();
  const {mutate: postTempStorage} = usePostTempStorage();
  const [formState, setFormState] = useRecoilState(announcementFormState);
  const resetFormState = useResetRecoilState(announcementFormState);
  const [files, setFiles] = useState<File[]>([]);
  const [imagePreviews, setImagePreviews] = useState<string[]>([]);
  const [isTempStorageModalOpen, setIsTempStorageModalOpen] = useState(false);
  const [selectedAnnouncementId, setSelectedAnnouncementId] = useState<
    string | null
  >(null);
  const memberId = getMemberId();

  const {data: tempStorageData} = useGetTempStorageDetail(
    selectedAnnouncementId || ''
  );

  useEffect(() => {
    if (tempStorageData) {
      setFormState(prev => ({
        ...prev,
        title: tempStorageData.title,
        content: tempStorageData.content,
      }));
      toast.success('임시 저장된 항목을 불러왔습니다.');
    }
  }, [tempStorageData, setFormState]);

  useEffect(() => {
    return () => {
      resetFormState();
    };
  }, [resetFormState]);

  const handleTitleChange = (e: ChangeEvent<HTMLInputElement>) => {
    setFormState(prev => ({...prev, title: e.target.value}));
  };

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

  const handleVoteChange = (vote: VoteInfo | undefined) => {
    setFormState(prev => ({...prev, vote}));
  };

  const handleFormSubmit = useCallback(() => {
    const formData = new FormData();

    formData.append(
      'dto',
      new Blob([JSON.stringify(formState)], {type: 'application/json'})
    );

    files.forEach(file => {
      formData.append('images', file);
    });

    if (formState.vote) {
      formData.append(
        'vote',
        new Blob([JSON.stringify(formState.vote)], {type: 'application/json'})
      );
    }

    postAnnouncement({memberId: memberId!, formData});
    navigate('/announcements');
  }, [formState, files, postAnnouncement, navigate, memberId]);

  const handleTempStorageSelect = (announcementId: string) => {
    setSelectedAnnouncementId(announcementId);
  };

  const handleTempStorageSave = () => {
    if (formState.vote) {
      toast.error('투표는 임시 저장할 수 없어요.');
      return;
    }

    const tempStorageData = {
      title: formState.title,
      content: formState.content,
    };

    postTempStorage({
      memberId: memberId!,
      data: tempStorageData,
    });

    toast.success('임시 저장이 완료되었습니다.');
  };

  const options = [
    {
      text: '임시 저장',
      onClick: handleTempStorageSave,
      disabled: !!formState.vote,
    },
    {
      text: '임시 보관함',
      onClick: () => setIsTempStorageModalOpen(true),
    },
  ];

  return (
    <div className="flex flex-col h-screen">
      <Header title="공지사항" buttonType="back" />
      <div className={containerHeaderClass}>
        <TitleInput
          value={formState.title}
          placeholder="제목"
          onChange={handleTitleChange}
        />
        <TextEditor value={formState.content} onChange={handleEditorChange} />
        <div className="p-6 mt-12">
          <div className="flex flex-row items-center mb-2">
            <ImageIcon width={20} height={21} />
            <p className="ms-2">사진 선택</p>
            <p className="text-xs ms-5">{files.length}/10</p>
          </div>
          <ArticleImageList
            images={imagePreviews}
            isEditable
            onAddImage={handleAddImage}
          />
        </div>
        <div className="px-6 pb-10">
          {/* <VoteModal onVoteChange={handleVoteChange} /> */}
        </div>
      </div>
      <ButtonBar
        label="전송하기"
        disabled={false}
        onClick={handleFormSubmit}
        options={options}
      />
      <TempStorageModal
        isOpen={isTempStorageModalOpen}
        onClose={() => setIsTempStorageModalOpen(false)}
        onSelect={handleTempStorageSelect}
      />
    </div>
  );
};

export default AnnouncementWrite;
