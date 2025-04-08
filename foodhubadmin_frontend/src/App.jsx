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
 
 
 export const getMembershipTypeToken = (token) => {
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
 
   useEffect(() => { // 컴포넌트가 마운트될 때 로컬 스토리지에서 토큰을 가져와 로그인 상태를 설정 
     const token = localStorage.getItem('token');
     if (token && !isTokenExpired(token)) {
       setIsLoggedIn(true);
       const extractedMembershipType = getMembershipTypeFromToken(token);
       setMembershipType(extractedMembershipType);
     }
     else {
       localStorage.removeItem('token'); // 만료된 토큰 제거
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
