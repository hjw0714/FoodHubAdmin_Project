import { Route, Routes } from "react-router-dom"
import AdminDashboard from "../components/dashBoard"
import AdminLogin from "../components/adminLogin"

const AppRouter = () => {
    return(
        <Routes>
            <Route path="" element={<AdminDashboard />}/>
            <Route path="/login" element={<AdminLogin />}/>
        </Routes>

    );
    
}

export default AppRouter;