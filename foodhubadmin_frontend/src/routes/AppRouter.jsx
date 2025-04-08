import { Routes, Route } from "react-router-dom";
import AdminDashboard from "../components/AdminDashboard";
import DashboardHome from "../components/DashboardHome";
import PostReport from "../components/PostReport";

const AppRouter = () => {
  return (
    <Routes>
        <Route path="" element={<AdminDashboard />} />
        <Route path="/admin/dashboard" element={<AdminDashboard />}>
            <Route index element={<DashboardHome />} />
            <Route path="postReport" element={<PostReport />} />
        </Route>
    </Routes>
  );
};

export default AppRouter;
