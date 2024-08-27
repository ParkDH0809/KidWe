import {Routes, Route} from 'react-router-dom';
import {containerHeaderClass} from '@/styles/styles';
import RoleSelect from '@/pages/sign-up/RoleSelect';
import RegisterInfo from '@/pages/sign-up/RegisterInfo';
import RegisterKindergarten from '@/pages/sign-up/RegisterKindergarten';
import RegisterComplete from '@/pages/sign-up/RegisterComplete';
import KindergartenSearch from '@/pages/sign-up/KindergartenSearch';
import KindergartenBan from '@/pages/sign-up/KindergartenBan';
import KindergartenChild from '@/pages/sign-up/KindergartenChild';
import Pending from '@/pages/sign-up/Pending';
const SignUp: React.FC = () => {
  // const {headerTitle} = useHeader();

  return (
    <div className="h-full max-h-full">
      <div className={containerHeaderClass}>
        <Routes>
          <Route path="/role" element={<RoleSelect />}></Route>
          <Route path="/info" element={<RegisterInfo />}></Route>
          <Route
            path="/kindergarten/register"
            element={<RegisterKindergarten />}
          ></Route>
          <Route
            path="kindergarten/search"
            element={<KindergartenSearch />}
          ></Route>
          <Route path="/kindergarten/ban" element={<KindergartenBan />}></Route>
          <Route
            path="/kindergarten/child"
            element={<KindergartenChild />}
          ></Route>
          <Route path="/complete" element={<RegisterComplete />}></Route>
          <Route path="/pending" element={<Pending />}></Route>
        </Routes>
      </div>
    </div>
  );
};

export default SignUp;
