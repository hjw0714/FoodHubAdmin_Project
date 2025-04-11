import axios from "axios";
import { useContext, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import '../assets/css/adminLogin.css';
import { AuthContext, getMembershipTypeFromToken } from "../App";

const AdminLogin = () => {
  const navigate = useNavigate();
    const {setIsLoggedIn , setMembershipType } = useContext(AuthContext); // 로그인 상태 업데이트를 위한 context 사용 (+ 권한)

    const [userId, setUserId] = useState(''); 
    const [passwd, setPasswd] = useState(''); 
    const [failMsg, setFailMsg] = useState('');  

  const handleLogin = async (e) => {
    e.preventDefault();

    if (userId.trim() === '' || passwd.trim() === '') {
      alert('아이디와 비밀번호를 입력하세요.');
      return;
    }

    try {
      const { data } = await axios.post(`${import.meta.env.VITE_API_URL}/auth/logIn`, { userId, passwd });
      localStorage.setItem('token', data);  
      setIsLoggedIn(true); 
      setFailMsg(''); 
      
      const extractedMembershipType = getMembershipTypeFromToken(data);
      setMembershipType(extractedMembershipType); 
      if (extractedMembershipType === 'ADMIN') { 
        navigate("/admin/dashboard");
      }
      else {
        setFailMsg('아이디 또는 비밀번호가 일치하지 않습니다');
      }
    

    } catch (error) {
      console.error(error);
      setFailMsg('아이디 또는 비밀번호가 일치하지 않습니다.');   
    }

  };

  return (
    <div className="admin-login-page">
      <div className="login-box">
        <h2>관리자 로그인</h2>
        <form onSubmit={handleLogin}>
          <input
            type="text"
            className="form-control"
            placeholder="아이디"
            value={userId}
            onChange={(e) => setUserId(e.target.value)}
            required
          />
          <input
            type="password"
            className="form-control"
            placeholder="비밀번호"
            value={passwd}
            onChange={(e) => setPasswd(e.target.value)}
            required
          />
          {failMsg && <p className="error-text">{failMsg}</p>}
          <button type="submit" className="btn btn-primary w-100 mt-3">
            로그인
          </button>
        </form>
      </div>
    </div>
  );
};

export default AdminLogin;
