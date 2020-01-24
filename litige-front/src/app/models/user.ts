export enum Role {
  AGENT = 'AGENT',
  ADMIN = 'ADMIN',
  CLAIMANT = 'CLAIMANT'
}

export class User {
  id: number;
  email: string;
  password: string;
  phone: string;
  role: Role;
  firstName: string;
  lastName: string;
}
