import './App.css'
import Header from './layout/Header'
import Footer from './layout/Footer'
import { createContext, useEffect, useState } from 'react'
import AppRouter from './routes/AppRouter';

export const AuthContext = createContext();

const isTokenExpired = (token) => {
  try {
    const payload = JSON.parse(atob(token.split('.')[1])); // Payload 디코딩
    const expiration = payload.exp * 1000; // 초 단위를 밀리초로 변환
    return Date.now() > expiration; // 현재 시간이 만료 시간보다 크면 true
  } catch (error) {
    return true; // 파싱 실패 시 만료된 것으로 간주
  }
};


export const getMembershipTypeFromToken = (token) => {
  try {
    const payload = JSON.parse(atob(token.split('.')[1]));
    return payload.membershipType || null;
  } catch (error) {
    return null;
  }
}


function App() {

  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [membershipType, setMembershipType] = useState(null);

  useEffect(() => {
    const params = new URLSearchParams(window.location.search);
    const tokenFromUrl = params.get('token');

    if (tokenFromUrl) {
      localStorage.setItem('token', tokenFromUrl);
      params.delete('token'); // token 파라미터 제거

      const cleanUrl = window.location.pathname + (params.toString() ? `?${params.toString()}` : '');
      window.history.replaceState({}, '', cleanUrl); // 브라우저 주소 바꿈 (새로고침 없이)

      // 💡 여기서 강제 새로고침하면 모든 컴포넌트가 token 저장 이후에 실행됨
      window.location.reload();
    }

    const token = localStorage.getItem('token');
    if (token && !isTokenExpired(token)) {
      setIsLoggedIn(true);
      setMembershipType(getMembershipTypeFromToken(token));
    } else {
      localStorage.removeItem('token');
      setIsLoggedIn(false);
      setMembershipType(null);
    }
  }, []);


  return (
    <>
      <AuthContext.Provider value={{ isLoggedIn, setIsLoggedIn, membershipType, setMembershipType }}>
        <Header />
        <AppRouter />
        <Footer />
      </AuthContext.Provider>

    </>
  )
}

export default App
