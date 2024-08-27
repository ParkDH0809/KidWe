import Header from '@/components/organisms/Navigation/Header';
import NavigationBar from '@/components/organisms/Navigation/NavigationBar';
import {containerNavigatorClass} from '@/styles/styles';
import React, {useEffect, useState} from 'react';

import Button from '@/components/atoms/Button/Button';
import BanCardItem from '@/components/molecules/Item/BanCardItem';
import {GetKindergartenDetail} from '@/types/kindergarten/GetKindergartenDetail';
import {getKindergartenDetail} from '@/apis/kindergarten/getKindergartenDetail';
import {getKindergartenId} from '@/utils/userData';
import NoResult from '@/components/atoms/NoResult';
import Modal from '@/components/organisms/Modal/Modal';
import ModalPortal from '@/components/organisms/Modal/ModalPortal';
import LabelInput from '@/components/atoms/Input/LabelInput';
import {postBan} from '@/apis/kindergarten/postBan';
import {putBan} from '@/apis/kindergarten/putBan';
import {toast} from 'react-toastify';

const BanManagement = () => {
  const [kindergartenData, setKindergartenData] = useState<
    GetKindergartenDetail[] | null
  >(null);
  const kindergartenId = getKindergartenId();
  const [isAddModalOpen, setIsAddModalOpen] = useState(false);
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [banName, setBanName] = useState('');
  const [banIdToEdit, setBanIdToEdit] = useState<number | null>(null);
  const handleAddModalOpen = () => {
    setBanName('');
    setIsAddModalOpen(true);
  };
  const handleAddModalClose = () => {
    setIsAddModalOpen(false);
  };

  const handleEditModalOpen = (banId: number, banName: string) => {
    setBanIdToEdit(banId);
    setBanName(banName);
    setIsEditModalOpen(true);
  };

  const handleEditModalClose = () => {
    setIsEditModalOpen(false);
  };
  const handleAddBan = async () => {
    if (banName.trim() === '') {
      toast.error('반 이름을 입력해주세요');
      return;
    }
    try {
      if (kindergartenId !== null) {
        await postBan({kindergartenId, name: banName});
        setIsAddModalOpen(false);
        setBanName(''); // 입력 필드를 초기화
        // 유치원 데이터를 새로 가져와 업데이트
        const updatedData = await getKindergartenDetail(kindergartenId!);
        setKindergartenData(updatedData);
      }
    } catch (error) {
      toast.error('반 등록에 실패했습니다.');
    }
  };

  const handleUpdateBan = async () => {
    if (banName.trim() === '') {
      toast.error('반 이름을 입력해주세요.');
      return;
    }
    if (banIdToEdit === null) return;

    try {
      await putBan({id: banIdToEdit, name: banName});
      setIsEditModalOpen(false);

      const updatedData = await getKindergartenDetail(kindergartenId!);
      setKindergartenData(updatedData);
    } catch (error) {
      toast.error('반 이름 수정에 실패하였습니다.');
    }
  };

  useEffect(() => {
    const fetchKindergartenData = async () => {
      try {
        if (kindergartenId !== null) {
          const data = await getKindergartenDetail(kindergartenId);
          console.log('받은 데이터', data); // 데이터가 배열인지 확인
          setKindergartenData(data);
        }
      } catch (error) {
        console.error('유치원 정보를 가져오는 중 에러 발생:', error);
      }
    };
    fetchKindergartenData();
  }, [kindergartenId]);

  return (
    <div
      className={`${containerNavigatorClass} flex flex-col h-screen w-full max-w-full`}
    >
      <Header title="반 관리" buttonType="back" />
      <div>
        {kindergartenData?.length == 0 ? (
          <NoResult text="유치원 결과가 없습니다." />
        ) : (
          kindergartenData?.map(ban => {
            const options = [
              {
                text: '반 이름 수정',
                onClick: () => {
                  handleEditModalOpen(ban.id, ban.name);
                },
              },
              {
                text: '반 삭제',
                onClick: () => {
                  if (ban.kidCount === 0 && ban.teacherCount === 0) {
                    console.log(`${ban.name} 반 삭제`);
                    // 반 삭제 로직 추가
                  } else {
                    toast.error(
                      '이 반은 원생 또는 교사가 있어 삭제할 수 없습니다.'
                    );
                  }
                },
                disabled: ban.kidCount !== 0 || ban.teacherCount !== 0,
              },
            ];
            return (
              <BanCardItem
                key={ban.id}
                banName={ban.name}
                cardType="detail"
                kidCount={ban.kidCount}
                teacherCount={ban.teacherCount}
                options={options}
              />
            );
          })
        )}
      </div>
      <div
        className={
          'w-full flex justify-center items-center absolute bottom-32 left-1/2 transform -translate-x-1/2'
        }
      >
        <Button label="+ 반 추가하기" onClick={handleAddModalOpen} />
      </div>
      <NavigationBar />
      <ModalPortal>
        <Modal isOpen={isAddModalOpen}>
          <Modal.Header title="반 추가하기" />
          <Modal.Body>
            <LabelInput
              value={banName}
              label="반 이름을 추가해주세요"
              onChange={e => setBanName(e.target.value)}
            />
          </Modal.Body>
          <Modal.BottomButton
            onClick={handleAddBan}
            label="반 등록"
            variant="positive"
            size="large"
            round="full"
          ></Modal.BottomButton>
          <Modal.Background onClick={handleAddModalClose}></Modal.Background>
        </Modal>
        <Modal isOpen={isEditModalOpen}>
          <Modal.Header title="반 수정하기" />
          <Modal.Body>
            <LabelInput
              value={banName}
              label="반 이름을 수정해주세요"
              onChange={e => setBanName(e.target.value)}
            />
          </Modal.Body>
          <Modal.BottomButton
            onClick={handleUpdateBan}
            label="반 수정"
            variant="positive"
            size="large"
            round="full"
          ></Modal.BottomButton>
          <Modal.Background onClick={handleEditModalClose}></Modal.Background>
        </Modal>
      </ModalPortal>
    </div>
  );
};

export default BanManagement;
{
  /* <BanCardItem
          cardType="detail"
          options={[
            {
              text: '반 수정',
              onClick: () => {
                console.log('반 수정');
              },
            },
            {
              text: '반 삭제',
              onClick: () => {
                console.log('반 삭제');
                console.log('이건 반에 원생, 교사가 없을 때만 표시됨');
              },
            },
          ]}
          banName="치타반"
          kidCount={123}
          teacherCount={12}
        /> */
}
