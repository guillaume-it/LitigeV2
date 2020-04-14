import { Sort } from './sort';

export interface Pageable<T> {
  page: number;
  limit: number;
  size: number;
  sort: Sort;
}
