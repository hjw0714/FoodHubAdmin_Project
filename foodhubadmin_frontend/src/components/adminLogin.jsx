import React, { useState } from 'react';
import '../assets/css/adminLogin.css';

const AdminLogin = () => {
  const [userId, setUserId] = useState('');
  const [passwd, setPasswd] = useState('');
  const [error, setError] = useState('');

  const handleLogin = async (e) => {
    e.preventDefault();

    // 예시: 관리자 계정 확인
    if (userId === 'admin' && passwd === 'admin123') {
      window.location.href = '/admin/dashboard'; // 실제 경로로 수정
    } else {
      setError('아이디와 비밀번호를 확인하세요.');
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
          {error && <div className="login-error">{error}</div>}
          <button type="submit" className="btn btn-primary w-100 mt-3">
            로그인
          </button>
        </form>
      </div>
    </div>
  );
};

export default AdminLogin;
