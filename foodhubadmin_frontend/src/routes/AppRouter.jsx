import { Routes, Route } from "react-router-dom";
import AdminDashboard from "../components/AdminDashboard";
import DashboardHome from "../components/DashboardHome";
import PostReport from "../components/PostReport";
import AdminLogin from "../components/adminLogin";

const AppRouter = () => {
  return (
    <Routes>
        <Route path="" element={<AdminLogin />} />
        <Route path="/" element={<AdminLogin />} />
        <Route path="/admin/dashboard" element={<AdminDashboard />}>
            <Route index element={<DashboardHome />} />
            <Route path="postReport" element={<PostReport />} />
        </Route>
        <Route path="/admin/login" element={<AdminLogin/>}/>
    </Routes>
  );
};

export default AppRouter;
