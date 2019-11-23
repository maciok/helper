import { Role } from "../model/role.model";
import { Disabilities } from "../model/disabilities.model";

export interface UserResponse {
  givenName: string;
  familyName: string;
  picture: string;
  roles: Role[];
  disabilities: Disabilities[];
  age: number;
  warnings: string[];
}
