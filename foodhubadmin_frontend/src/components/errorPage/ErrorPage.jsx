import { useNavigate } from 'react-router-dom';
import '../../assets/css/errorPage.css';

const ErrorPage = ({ code, message, emoji }) => {
   const navigate = useNavigate();

   const handleBack = () => navigate(-1);
   const handleHome = () => navigate('/admin/dashboard');
   const handleLogin = () => navigate("/");

   return (
      <div className="error-wrapper">
         <div className="error-container">
            <div className="error-illustration">{emoji}</div>
            <h1 className="error-code">{code}</h1>
            <p className="error-message">{message}</p>
            <div className="error-actions">
               {code === "401" || code === "403" ? (
                  <button onClick={handleLogin}>🔒 로그인</button>
               ) : (
                  <>
                     <button onClick={handleBack}>🔙 뒤로가기</button>
                     <button onClick={handleHome}>🏠 홈으로</button>
                  </>
               )}
            </div>
         </div>
      </div>
   );
};


export const NotFound = () => (
   <ErrorPage code="404" message="페이지를 찾을 수 없습니다." emoji="😕" />
);

export const ServerError = () => (
   <ErrorPage code="500" message="서버에 문제가 발생했습니다." emoji="💥" />
);

export const Unauthorized = () => (
   <ErrorPage code="401" message="로그인이 필요합니다." emoji="🔒" />
);

export const Forbidden = () => (
   <ErrorPage code="403" message="권한이 부족합니다." emoji="🔒" />
);