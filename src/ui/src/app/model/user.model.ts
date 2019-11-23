import { Role } from "./role.model";
import { Disabilities } from "./disabilities.model";

export interface User {
  firstName: string;
  lastName: string;
  roles: Role[];
  disabilities: Disabilities[];
  age: number;
}
