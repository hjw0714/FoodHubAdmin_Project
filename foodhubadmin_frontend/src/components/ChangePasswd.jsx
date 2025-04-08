import { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import '../assets/css/changePassword.css';

const ChangePasswd = ({ userId }) => {
  const navigate = useNavigate();
  const [currentPasswd, setCurrentPasswd] = useState('');
  const [newPasswd, setNewPasswd] = useState('');
  const [confirmPasswd, setConfirmPasswd] = useState(''); // 오타 수정
  const [errorMsg, setErrorMsg] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault(); // 기본 form 제출 방지

    if (!currentPasswd || !newPasswd || !confirmPasswd) {
      setErrorMsg('모든 작성란을 입력해주세요.');
      return;
    }

    if (newPasswd !== confirmPasswd) {
      setErrorMsg('새 비밀번호와 확인 비밀번호가 일치하지 않습니다.');
      return;
    }

    if (newPasswd.length < 4) {
      setErrorMsg('새 비밀번호는 4자 이상이어야 합니다.');
      return;
    }

    try {

      /*
      // 현재 비밀번호 검증 (누락된 부분 추가)
      const verifyResponse = await axios.put(
        `${import.meta.env.VITE_API_URL}/verify-passwd`,
        { userId, currentPasswd },
        { headers: { Authorization: `Bearer ${localStorage.getItem('token')}` } }
      );

      if (!verifyResponse.data.isValid) {
        setErrorMsg('현재 비밀번호가 일치하지 않습니다.');
        return;
      }

      // 비밀번호 변경 요청
      await axios.put(
        `${import.meta.env.VITE_API_URL}/change-passwd`,
        { userId, newPasswd },
        { headers: { Authorization: `Bearer ${localStorage.getItem('token')}` } }
      );
      */

      alert('비밀번호가 변경되었습니다.');
      navigate('/admin/profile-view');
    } catch (error) {
      if (error.response) {
        if (error.response.status === 401) {
          navigate('/error/401');
        } else if (error.response.status === 403) {
          navigate('/error/403');
        } else {
          setErrorMsg('비밀번호 변경 중 오류가 발생했습니다.');
          console.error(error);
        }
      }
    }
  };

  return (
    <div className="change-password-container">
      <div className="change-password-section">
        <h2>비밀번호 변경</h2>

        <form onSubmit={handleSubmit}>
          <div className="password-item">
            <label>현재 비밀번호</label>
            <input
              type="password"
              name="currentPasswd"
              value={currentPasswd}
              onChange={(e) => setCurrentPasswd(e.target.value)}
              className="password-input"
            />
          </div>

          <div className="password-item">
            <label>새 비밀번호</label>
            <input
              type="password"
              name="newPasswd"
              value={newPasswd}
              onChange={(e) => setNewPasswd(e.target.value)}
              className="password-input"
            />
          </div>

          <div className="password-item">
            <label>비밀번호 확인</label>
            <input
              type="password"
              name="confirmPasswd"
              value={confirmPasswd}
              onChange={(e) => setConfirmPasswd(e.target.value)}
              className="password-input"
            />
          </div>

          {errorMsg && <div className="error-message">{errorMsg}</div>}

          <div className="button-group">
            <button type="submit" className="submit-button">
              🔐 저장
            </button>
            <button
              type="button"
              className="cancel-button"
              onClick={() => navigate('/admin/profile-view')}
            >
              ↩ 취소
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default ChangePasswd;