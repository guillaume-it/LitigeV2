export enum ScreenState {
  EDIT = 'edit',
  DETAIL = 'detail',
  CREATE = 'create'
}
export enum LoadingState {
  LOADING = 'loading',
  LOADED = 'loaded'
}

export class State {
  screenState: ScreenState;
  loadingState = LoadingState.LOADED;
}
