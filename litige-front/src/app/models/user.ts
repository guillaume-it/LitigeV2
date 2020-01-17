export enum Role {
  USER = 'USER',
  ADMIN = 'ADMIN',
  USER_MANAGER = 'USER_MANAGER',
  CLAIMANT = 'CLAIMANT'
}

export class User {
  id: number;
  email: string;
  password: string;
  phone: string;
  role: Role;
  firstName: string;
  name: string;
}
