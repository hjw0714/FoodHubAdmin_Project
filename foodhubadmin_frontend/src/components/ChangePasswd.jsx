import { useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';
import '../assets/css/changePassword.css';

const ChangePasswd = ({ userId, setViewMode }) => {
  const navigate = useNavigate();
  const [passwd, setPasswd] = useState('');
  const [confirmPasswd, setConfirmPasswd] = useState('');
  const [errorMsg, setErrorMsg] = useState('');

  const handleSubmit = async () => {

    if (!passwd || !confirmPasswd) {
      setErrorMsg('모든 작성란을 입력해주세요.');
      return;
    }

    if (passwd !== confirmPasswd) {
      setErrorMsg('비밀번호가 일치하지 않습니다.');
      return;
    }

    if (passwd.length < 4) {
      setErrorMsg('비밀번호는 4자 이상이어야 합니다.');
      return;
    }

    try {
      await axios.put(`${import.meta.env.VITE_API_URL}/admin/user/changePasswd`, { userId, passwd }, {
        headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
      });

      alert('비밀번호가 변경되었습니다.');
      setViewMode('profileView');
    } catch (error) {
      if (error.response.status === 401) {
        navigate('/error/401');
      } else if (error.response.status === 403) {
        navigate('/error/403');
      } else {
        setErrorMsg('비밀번호 변경 중 오류가 발생했습니다.');
        console.error(error);
      }
    }
  };

  const handleCancel = () => {
    confirm('비밀번호 변경을 취소하시겠습니까?');
    setViewMode('profileView');
  };

  return (
    <div className="change-password-container">
      <div className="change-password-section">
        <h2>비밀번호 변경</h2>
        <div className="password-item">
          <label>새 비밀번호</label>
          <input
            type="password"
            value={passwd}
            onChange={(e) => setPasswd(e.target.value)}
            placeholder='비밀번호를 입력하세요.'
            className="password-input"
          />
        </div>

        <div className="password-item">
          <label>비밀번호 확인</label>
          <input
            type="password"
            value={confirmPasswd}
            onChange={(e) => setConfirmPasswd(e.target.value)}
            placeholder='비밀번호를 한 번 더 입력하세요.'
            className="password-input"
          />
        </div>

        {errorMsg && <div className="error-message">{errorMsg}</div>}

        <div className="button-group">
          <button type="button" className="submit-button" onClick={handleSubmit}>
            🔐 저장
          </button>
          <button
            type="button"
            className="cancel-button"
            onClick={handleCancel}
          >
            ↩ 취소
          </button>
        </div>

      </div>
    </div>
  );
};

export default ChangePasswd;