export interface PatchKidInfo {
  dto: {
    id: number;
    name: string;
    birthday: string; // YYYY-MM-DD
    gender: 'MALE' | 'FEMALE';
    allergies: string[];
    banId: number;
    kindergartenId: number;
  };
  picture: undefined | string;
}
