import { Role } from "./role.model";
import { Disabilities } from "./disabilities.model";
import { UserWarning } from "./user-warning.model";

export interface User {
  firstName: string;
  lastName: string;
  roles: Role[];
  disabilities: Disabilities[];
  age: number;
  warnings: UserWarning[]
}
