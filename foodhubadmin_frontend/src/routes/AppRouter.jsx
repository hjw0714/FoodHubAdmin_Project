import { Routes, Route } from "react-router-dom";
import AdminDashboard from "../components/AdminDashboard";
import DashboardHome from "../components/DashboardHome";
import PostReport from "../components/PostReport";
import AdminLogin from "../components/adminLogin";
import { Forbidden, NotFound, ServerError, Unauthorized } from "../components/ErrorPage";
import ProfileView from "../components/profileView";

const AppRouter = () => {
    return (
        <Routes>
            <Route path="" element={<AdminLogin />} />
            <Route path="/" element={<AdminLogin />} />
            <Route path="/admin/dashboard" element={<AdminDashboard />}>
                <Route index element={<DashboardHome />} />
                <Route path="postReport" element={<PostReport />} />
            </Route>
            <Route path="/admin/login" element={<AdminLogin />} />
            <Route path="/admin/profile-view" element={<ProfileView />} />


            <Route path="/error/401" element={<Unauthorized />} /> {/* 401 Unauthorized 페이지 */}
            <Route path="/error/403" element={<Forbidden />} />    {/* 403 Unauthorized 페이지 */}
            <Route path="/error/500" element={<ServerError />} />  {/* 500 Server Error 페이지 */}
            <Route path="*" element={<NotFound />} />              {/* 404 Not Found 페이지 */}
        </Routes>
    );
};

export default AppRouter;
