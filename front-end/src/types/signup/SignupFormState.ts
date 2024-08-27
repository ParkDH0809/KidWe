export interface SignupFormState {
  dto: {
    name: string;
    tel: string;
    email: string;
    password: string;
    role: string;
  };
  // picture: string | ArrayBuffer | null;
  picture: string | undefined;
}
