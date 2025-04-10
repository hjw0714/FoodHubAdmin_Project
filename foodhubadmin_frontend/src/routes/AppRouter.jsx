import { Routes, Route } from "react-router-dom";
import AdminDashboard from "../components/AdminDashboard";
import DashboardHome from "../components/DashboardHome";
import PostReport from "../components/PostReport";
import AdminLogin from "../components/AdminLogin";
import UserJoin from "../components/UserJoin";
import UserLeave from "../components/UserLeave";
import PostListTotal from "../components/PostListTotal";
import CategoryPostList from "../components/CategoryPostList";
import CommentStats from "../components/CommentStats";
import CommentReport from "../components/CommentReport";
import MemberList from "../components/MemberList";
import VisitorStats from "../components/VisitorStats";
import AdminChat from "../components/AdminChat";
import BannerManager from "../components/BannerManager";
import { Forbidden, NotFound, ServerError, Unauthorized } from "../components/ErrorPage";

import ChangePasswd from "../components/ChangePasswd";
import ProfileView from "../components/ProfileView";
import UserStatus from "../components/UserStatus";


const AppRouter = () => {
  return (
    <Routes>
      <Route path="" element={<AdminLogin />} />
      <Route path="/" element={<AdminLogin />} />
      <Route path="/admin/login" element={<AdminLogin />} />
      <Route path="/admin/profile-view" element={<ProfileView />} />
      <Route path="/admin/change-passwd" element={<ChangePasswd />} />

      <Route path="/error/401" element={<Unauthorized />} /> {/* 401 Unauthorized 페이지 */}
      <Route path="/error/403" element={<Forbidden />} />    {/* 403 Unauthorized 페이지 */}
      <Route path="/error/500" element={<ServerError />} />  {/* 500 Server Error 페이지 */}
      <Route path="*" element={<NotFound />} />              {/* 404 Not Found 페이지 */}

      <Route path="/admin/dashboard" element={<AdminDashboard />}>
        <Route index element={<DashboardHome />} />
        <Route path="postReport" element={<PostReport />} />
        <Route path="commentReport" element={<CommentReport />} />
        <Route path="userStatus" element={<UserStatus />} />
        <Route path="userJoin" element={<UserJoin />} />
        <Route path="userLeave" element={<UserLeave />} />
        <Route path="postListTotal" element={<PostListTotal />} />
        <Route path="categoryPostList" element={<CategoryPostList />} />
        <Route path="commentStats" element={<CommentStats />} />
        <Route path="memberList" element={<MemberList />} />
        <Route path="visitorStats" element={<VisitorStats />} />
        <Route path="adminChat" element={<AdminChat />} />
        <Route path="bannerManager" element={<BannerManager />} />

      </Route>
    </Routes>
  );

};

export default AppRouter;
