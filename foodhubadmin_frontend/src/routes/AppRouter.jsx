import { Routes, Route } from "react-router-dom";
import AdminDashboard from "../components/layout/AdminDashboard.jsx";
import DashboardHome from "../components/chart/DashboardHome.jsx";
import PostReport from "../components/report/PostReport.jsx";
import AdminLogin from "../components/adminUser/AdminLogin.jsx";
import UserJoin from "../components/chart/user/UserJoin.jsx";
import UserLeave from "../components/chart/user/UserLeave.jsx";
import PostListTotal from "../components/chart/post/PostListTotal.jsx";
import CategoryPostList from "../components/chart/post/CategoryPostList.jsx";
import CommentStats from "../components/chart/comment/CommentStats.jsx";
import CommentReport from "../components/report/CommentReport.jsx";
import MemberList from "../components/memberList/MemberList.jsx";
import VisitorStats from "../components/chart/visitor/VisitorStats.jsx";
import AdminChat from "../components/adminManagement/chat/AdminChat.jsx";
import BannerManager from "../components/adminManagement/bannerEdit/BannerManager.jsx";
import { Forbidden, NotFound, ServerError, Unauthorized } from "../components/errorPage/ErrorPage.jsx";

import ProfileView from "../components/adminUser/ProfileView.jsx";
import UserStatus from "../components/chart/user/UserStatus.jsx";
import AdminPostCreate from "../components/adminManagement/adminPost/AdminPostCreate.jsx";
import AdminPostList from "../components/adminManagement/adminPost/AdminPostList.jsx";
  

const AppRouter = () => {
  return (
    <Routes>
      <Route path="" element={<AdminLogin />} />
      <Route path="/" element={<AdminLogin />} />
      <Route path="/admin/login" element={<AdminLogin />} />
      <Route path="/admin/profile-view" element={<ProfileView />} />

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
        <Route path="createPost" element={<AdminPostCreate/>} />
        <Route path="adminPostList" element={<AdminPostList/>} />
      </Route>
    </Routes>
  );

};

export default AppRouter;
