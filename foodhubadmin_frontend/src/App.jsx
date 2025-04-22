import './App.css'
import Header from './layout/Header'
import Footer from './layout/Footer'
import { createContext, useEffect, useState } from 'react'
import AppRouter from './routes/AppRouter'
import { useLocation, useNavigate } from 'react-router-dom'

export const AuthContext = createContext();

const isTokenExpired = (token) => {
  try {
    const payload = JSON.parse(atob(token.split('.')[1]));
    const expiration = payload.exp * 1000;
    return Date.now() > expiration;
  } catch (error) {
    return true;
  }
};

export const getMembershipTypeFromToken = (token) => {
  try {
    const payload = JSON.parse(atob(token.split('.')[1]));
    return payload.membershipType || null;
  } catch (error) {
    return null;
  }
};

function App() {
  const location = useLocation();
  const navigate = useNavigate();

  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [membershipType, setMembershipType] = useState(null);
  const [initDone, setInitDone] = useState(false); // ✅ 초기화 완료 여부

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const tokenFromUrl = params.get('token');

    if (tokenFromUrl) {
      localStorage.setItem('token', tokenFromUrl);
      navigate('/admin/dashboard', { replace: true }); // ?token 제거
    }

    const token = localStorage.getItem('token');
    console.log("token:", token);

    if (token && !isTokenExpired(token)) {
      setIsLoggedIn(true);
      setMembershipType(getMembershipTypeFromToken(token));
    } else {
      localStorage.removeItem('token');
      setIsLoggedIn(false);
      setMembershipType(null);
    }

    setInitDone(true); // ✅ 여기서 init 완료 표시
  }, [location]);

  if (!initDone) return null; // ✅ 초기화 안 됐으면 아무것도 렌더링 안 함

  return (
    <div className="app-wrapper">
      <AuthContext.Provider value={{ isLoggedIn, setIsLoggedIn, membershipType, setMembershipType }}>
        <Header />
        <AppRouter />
        <Footer />
      </AuthContext.Provider>
    </div>
  );
}

export default App;
