import {RoleItem} from '@/enum/roleItem';

export interface Comment {
  id: number;
  picture: string;
  role: RoleItem;
  name: string;
  banName: string;
  content: string;
  createdTime: string;
  canDelete: boolean;
  childs: Child[];
}

export interface Child {
  id: string;
  picture: string;
  role: RoleItem;
  name: string;
  banName: string;
  content: string;
  createdTime: string;
  canDelete: boolean;
}
