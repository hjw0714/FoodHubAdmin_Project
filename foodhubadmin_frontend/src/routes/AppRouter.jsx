import { Routes, Route } from "react-router-dom";
import AdminDashboard from "../components/AdminDashboard";
import DashboardHome from "../components/DashboardHome";
import PostReport from "../components/PostReport";
import AdminLogin from "../components/adminLogin";
import UserStats from "../components/UserStatus";
import UserJoin from "../components/UserJoin";
import UserLeave from "../components/UserLeave";
import PostListTotal from "../components/PostListTotal";
import CategoryPostList from "../components/CategoryPostList";
import CommentStats from "../components/CommentStats";
import PostReportStats from "../components/PostReportStats ";
import CommentReportStats from "../components/CommentReportStats ";
import CommentReport from "../components/CommentReport";
import MemberList from "../components/MemberList";
import VisitorStats from "../components/VisitorStats";
import AdminChat from "../components/AdminChat";
import BannerManager from "../components/BannerManager";

const AppRouter = () => {
  return (
    <Routes>
      <Route path="" element={<AdminLogin />} />
      <Route path="/" element={<AdminLogin />} />
      <Route path="/admin/login" element={<AdminLogin />} />

      <Route path="/admin/dashboard" element={<AdminDashboard />}>
        <Route index element={<DashboardHome />} />
        <Route path="postReport" element={<PostReport />} />
        <Route path="commentReport" element={<CommentReport />} />
        <Route path="userStatus" element={<UserStats />} />
        <Route path="userJoin" element={<UserJoin />} />
        <Route path="userLeave" element={<UserLeave />} />
        <Route path="postListTotal" element={<PostListTotal />}/>
        <Route path="categoryPostList" element={<CategoryPostList />}/>
        <Route path="commentStats" element={<CommentStats />}/>
        <Route path="postReportStats" element={<PostReportStats />}/>
        <Route path="commentReportStats" element={<CommentReportStats />}/>
        <Route path="memberList" element={<MemberList />}/>
        <Route path="visitorStats" element={<VisitorStats />}/>
        <Route path="adminChat" element={<AdminChat />}/>
        <Route path="bannerManager" element={<BannerManager />}/>
      </Route>
    </Routes>
  );
};

export default AppRouter;
