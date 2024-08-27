import {MENU_ITEMS} from '@/constants/home/menuItems';

interface MenuItem {
  id: number;
  img: string;
  text: string;
  to: string;
}

const directorMenus = [1, 2, 3, 5, 6, 7, 11];
const teacherMenus = [1, 2, 3, 4, 5, 6, 7, 11];
const guardianMenus = [1, 2, 5, 6, 7];

export const getMenuItem = (
  role: 'ROLE_DIRECTOR' | 'ROLE_TEACHER' | 'ROLE_GUARDIAN'
) => {
  if (role === 'ROLE_DIRECTOR') {
    return MENU_ITEMS.filter((menu: MenuItem) =>
      directorMenus.includes(menu.id)
    );
  } else if (role === 'ROLE_TEACHER') {
    return MENU_ITEMS.filter((menu: MenuItem) =>
      teacherMenus.includes(menu.id)
    );
  } else {
    return MENU_ITEMS.filter((menu: MenuItem) =>
      guardianMenus.includes(menu.id)
    );
  }
};
