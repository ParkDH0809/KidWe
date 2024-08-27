import {useEffect, useCallback, ChangeEvent} from 'react';
import {useNavigate, useParams} from 'react-router-dom';
import {useRecoilState, useResetRecoilState} from 'recoil';

import {announcementFormState} from '@/recoil/atoms/announcement/announcementFormState';
import {usePutAnnouncement} from '@/hooks/announcement/usePutAnnouncement';
import {useAnnouncementDetail} from '@/hooks/announcement/useAnnouncementDetail';
import {getMemberId} from '@/utils/userData';
import {containerHeaderClass} from '@/styles/styles';

import Header from '@/components/organisms/Navigation/Header';
import TitleInput from '@/components/atoms/Input/TitleInput';
import TextEditor from '@/components/organisms/Board/TextEditor';
import ButtonBar from '@/components/organisms/Navigation/ButtonBar';

const AnnouncementEdit = () => {
  const navigate = useNavigate();
  const {announcementId} = useParams<{announcementId: string}>();
  const {mutate: updateAnnouncement} = usePutAnnouncement();
  const [formState, setFormState] = useRecoilState(announcementFormState);
  const resetFormState = useResetRecoilState(announcementFormState);
  const memberId = getMemberId();

  const {data: announcementDetail} = useAnnouncementDetail(
    announcementId!,
    memberId!
  );

  useEffect(() => {
    if (announcementDetail) {
      setFormState({
        title: announcementDetail.post.title,
        content: announcementDetail.post.content,
      });
    }
  }, [announcementDetail, setFormState]);

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

  const handleFormSubmit = useCallback(() => {
    const updateData = {
      title: formState.title,
      content: formState.content,
    };

    updateAnnouncement({
      announcementId: announcementId!,
      body: updateData,
    });
    navigate(`/announcements/${announcementId}`);
  }, [formState, updateAnnouncement, navigate, announcementId]);

  return (
    <div className="flex flex-col h-screen">
      <Header title="공지사항 수정" buttonType="back" />
      <div className={containerHeaderClass}>
        <TitleInput
          value={formState.title}
          placeholder="제목"
          onChange={handleTitleChange}
        />
        <TextEditor value={formState.content} onChange={handleEditorChange} />
      </div>
      <ButtonBar
        label="수정하기"
        disabled={false}
        onClick={handleFormSubmit}
        options={[]}
      />
    </div>
  );
};

export default AnnouncementEdit;
