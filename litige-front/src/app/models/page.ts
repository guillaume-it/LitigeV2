export interface Page<T> {
  content: T[];
  empty: boolean;
  first: boolean;
  last: boolean;
  number: number;
  numberOfElements: number;
  pageable: {
    offset: number;
    pageNumber: number;
    pageSize: number;
    paged: boolean;
    sort: Sort;
  };
  sort: Sort;
  totalElements: number;
  totalPages: number;
}

interface Sort {
  sorted: boolean;
  unsorted: boolean;
  empty: boolean;
}
