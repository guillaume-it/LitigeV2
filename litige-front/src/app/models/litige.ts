import { AbstractResource } from '../services/store-service';

export class Litige implements AbstractResource {
  id: number;
  creation: Date;
  objet: string;
  localite: string;
  requerant: string;
}
