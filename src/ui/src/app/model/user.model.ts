import {UserWarning} from "./user-warning.model";

export interface User {
  firstName: string;
  lastName: string;
  roles: Role[];
  disabilities: [];
  age: number;
  warnings: UserWarning[]
}
