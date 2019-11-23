export interface UserResponse {
  givenName: string;
  familyName: string;
  picture: string;
  roles: Role[];
  age: number;
  warnings: string[];
}
