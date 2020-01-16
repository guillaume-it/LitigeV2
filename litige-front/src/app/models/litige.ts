import { AbstractResource } from '../services/store.service';
import { User } from './user';

export class Litige implements AbstractResource {
  id: number;
  creation: Date;
  objet: string;
  message: string;
  agent: User;
  requerant: User;
}
