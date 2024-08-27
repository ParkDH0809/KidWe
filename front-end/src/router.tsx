import {createBrowserRouter, redirect} from 'react-router-dom';
import Home from '@/pages/Home';

import MyPage from '@/pages/MyPage';
import MyPageUpdate from '@/pages/my-page/MyPageUpdate';
import KidUpdate from '@/pages/my-page/KidUpdate';

import KindergartenSchedule from '@/pages/schedule/KindergartenSchedule';
import ScheduleView from '@/pages/schedule/ScheduleView';

import DailyNoteListView from '@/pages/daily-note/DailyNoteListView';
import DailyNoteDetail from '@/pages/daily-note/DailyNoteDetail';
import DailyNoteWrite from '@/pages/daily-note/DailyNoteWirte';
import DailyNoteEdit from '@/pages/daily-note/DailyNoteEdit';

import AnnouncementListView from '@/pages/announcement/AnnouncementListView';
import AnnouncementDetail from '@/pages/announcement/AnnouncementDetail';
import AnnouncementWrite from '@/pages/announcement/AnnouncementWrite';
import AnnouncementEdit from '@/pages/announcement/AnnouncementEdit';

import MedicationListView from '@/pages/medication/MedicationListView';
import MedicationDetail from '@/pages/medication/MedicationDetail';
import MedicationWrite from '@/pages/medication/MedicationWrite';

import LeaveConsentListView from '@/pages/leave-consent/LeaveConsentListView';
import LeaveConsentDetail from '@/pages/leave-consent/LeaveConsentDetail';
import LeaveConsentnWrite from '@/pages/leave-consent/LeaveConsentWrite';

import SignUp from '@/pages/SignUp';
import LoginMain from '@/pages/login/LoginMain';

import MemoView from '@/pages/memo/MemoView';
import MemoList from '@/pages/memo/MemoList';
import MemoWrite from '@/pages/memo/MemoWrite';
import MemoUpdate from '@/pages/memo/MemoUpdate';

import AttendanceView from '@/pages/attendance/AttendanceView';
import AttendanceManagement from '@/pages/attendance/AttendanceManagement';

import FoodView from '@/pages/food/FoodView';
import FoodInfo from '@/pages/food/FoodInfo';
import FoodInfoWrite from '@/pages/food/FoodInfoWrite';

import NotFound from '@/pages/NotFound';

import {getUserData} from '@/utils/userData';
import ManagementList from '@/pages/kindergarten-management/managementList';
import BanManagement from '@/pages/kindergarten-management/banManagement';
import TeacherManagement from '@/pages/kindergarten-management/teacherManagement';
import KindergartenManagement from '@/pages/kindergarten-management/kindergartenManagement';
import ChildManagement from '@/pages/kindergarten-management/childManagement';
import {isGuardian} from './utils/auth/isGuardian';
import {isTeacher} from './utils/auth/isTeacher';
import {isDirector} from './utils/auth/isDirector';

const requireAuth = () => {
  if (getUserData() === null) {
    return redirect('/auth/login');
  }
  return null;
};

const onlyDirectorAndTeacher = () => {
  if (isGuardian()) {
    return redirect('/');
  }
  return null;
};

const onlyGuardian = () => {
  if (!isGuardian()) {
    return redirect('/');
  }
  return null;
};

// const onlyTeacher = () => {
//   if (!isTeacher()) {
//     return redirect('/');
//   }
//   return null;
// };

const onlyDirector = () => {
  if (!isDirector()) {
    return redirect('/');
  }
  return null;
};

export const router = createBrowserRouter([
  {
    path: '/auth',
    children: [
      {
        path: 'signup/*',
        element: <SignUp />,
      },
      {
        path: 'login',
        element: <LoginMain />,
      },
    ],
  },
  {
    path: '/',
    element: <Home />,
    loader: requireAuth,
  },
  {
    path: '/schedule',
    element: <ScheduleView />,
    loader: requireAuth,
    children: [
      {
        path: '',
        element: <KindergartenSchedule />,
      },
    ],
  },
  {
    path: '/my-page',
    loader: requireAuth,
    children: [
      {
        path: '',
        element: <MyPage />,
      },
      {
        path: 'update',
        element: <MyPageUpdate />,
      },
      {
        path: 'kid/update',
        element: <KidUpdate />,
      },
    ],
  },
  {
    path: '/daily-notes',
    loader: requireAuth,
    children: [
      {
        path: '',
        element: <DailyNoteListView />,
      },
      {
        path: ':dailyNoteId',
        element: <DailyNoteDetail />,
      },
      {
        path: 'write',
        element: <DailyNoteWrite />,
      },
      {
        path: ':dailyNoteId/edit',
        element: <DailyNoteEdit />,
      },
    ],
  },
  {
    path: '/announcements',
    loader: requireAuth,
    children: [
      {
        path: '',
        element: <AnnouncementListView />,
      },
      {
        path: ':announcementId',
        element: <AnnouncementDetail />,
      },
      {
        path: 'write',
        element: <AnnouncementWrite />,
      },
      {
        path: ':announcementId/edit',
        element: <AnnouncementEdit />,
      },
    ],
  },
  {
    path: '/attendances',
    loader: onlyDirectorAndTeacher,
    element: <AttendanceView />,
    children: [
      {
        path: '',
        element: <AttendanceManagement />,
      },
    ],
  },
  {
    path: '/medications',
    loader: requireAuth,
    children: [
      {
        path: '',
        element: <MedicationListView />,
      },
      {
        path: ':medicationId',
        element: <MedicationDetail />,
      },
      {
        path: 'write',
        element: <MedicationWrite />,
        loader: onlyGuardian,
      },
    ],
  },
  {
    path: '/leave-consents',
    loader: requireAuth,
    children: [
      {
        path: '',
        element: <LeaveConsentListView />,
      },
      {
        path: ':leaveConsentId',
        element: <LeaveConsentDetail />,
      },
      {
        path: 'write',
        element: <LeaveConsentnWrite />,
        loader: onlyGuardian,
      },
    ],
  },
  {
    path: '/memos',
    loader: async () => {
      if (!isTeacher()) {
        return redirect('/');
      }

      // try {
      //   const result = await getAccessToken();
      //   const {accessToken} = result;
      //   setAccessToken(accessToken);
      // } catch (error) {
      //   deleteAccessToken();
      //   deleteRefreshToken();
      //   deleteUserData();
      //   return redirect('/auth/login');
      // }
      return null;
    },
    element: <MemoView />,
    children: [
      {
        path: '',
        element: <MemoList />,
      },
      {
        path: 'write',
        element: <MemoWrite />,
      },
      {
        path: 'update/:memoId',
        element: <MemoUpdate />,
      },
    ],
  },
  {
    path: '/kindergarten',
    loader: onlyDirectorAndTeacher,
    children: [
      {path: '', element: <ManagementList />},
      {path: 'ban', element: <BanManagement />},
      {
        path: 'setting',
        element: <KindergartenManagement />,
      },
      {path: 'child', element: <ChildManagement />},
      {path: 'teacher', element: <TeacherManagement />, loader: onlyDirector},
    ],
  },
  {
    path: '/foods',
    loader: requireAuth,
    element: <FoodView />,
    children: [
      {
        path: '',
        element: <FoodInfo />,
      },
      {
        path: 'write',
        element: <FoodInfoWrite />,
        loader: onlyDirectorAndTeacher,
      },
    ],
  },
  {
    path: '*',
    element: <NotFound />,
  },
]);
