import { User } from './user';

export class Litige {
  id: number;
  creation: Date;
  objet: string;
  message: string;
  agent: User;
  requerant: User;
}
