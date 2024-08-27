export interface KidOfMedication {
  kidPicture: string; // 아이 사진
  medicineName: string; // 약 이름
  symptom: string; // 증상
  type: string; // 종류
  capacity: string; // 용량
  numberOfDoses: string; // 횟수
  medicationExecuteDate: string;
  medicationExecuteTime: string; // 시간
  storageMethod: string; // 보관
  others: string; // 비고
  medicineUrl: string; // 약 사진
  signUrl: string; // 서명 사진
  parentName: string; // 학부모 이름
  signDate: string; // 투약일
}
