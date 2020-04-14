import { Role } from './role';

export class User {
  id: number;
  email: string;
  password: string;
  phone: string;
  roles: Array<Role>;
  firstName: string;
  lastName: string;
}
