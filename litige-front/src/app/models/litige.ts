import { User } from './user';
import { FileInformation } from './file-information';

export class Litige {
  id: number;
  creation: Date;
  objet: string;
  message: string;
  agent: User;
  requerant: User;
  fileInformations: Array<FileInformation>;
}
