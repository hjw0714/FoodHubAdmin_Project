import { useState, useContext } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import '../assets/css/changePassword.css';
import { AuthContext } from '../App';

const ChangePasswd = ({ userId }) => {
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
      setErrorMsg('새 비밀번호와 확인 비밀번호가 일치하지 않습니다.');
      return;
    }

    if (passwd.length < 4) {
      setErrorMsg('새 비밀번호는 4자 이상이어야 합니다.');
      return;
    }

    try {

      console.log('Request Data : ', { userId, passwd });
      await axios.put(`${import.meta.env.VITE_API_URL}/user/changePasswd`, { userId, passwd },
        { headers: { Authorization: `Bearer ${localStorage.getItem('token')}` } }
      );
      alert('비밀번호가 변경되었습니다.');
      navigate('/admin/profile-view');
    } catch (error) {

      if (error.response.status === 401) { 
        navigate('/error/401'); 
      }
      else if (error.response.status === 403) { 
        navigate('/error/403'); 
      }
      else {
        setErrorMsg('비밀번호 변경 중 오류가 발생했습니다.');
        console.error(error);
      }

    }
  };

  return (
    <div className="change-password-container">
      <div className="change-password-section">
        <h2>비밀번호 변경</h2>

        <div className="password-item">
          <label>새 비밀번호</label>
          <input
            type="password"
            name="newPasswd"
            value={passwd}
            onChange={(e) => setPasswd(e.target.value)}
            placeholder='비밀번호를 4자 이상 입력해주세요'
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
            placeholder='비밀번호를 4자 이상 입력해주세요'
            className="password-input"
          />
        </div>

        {errorMsg && <div className="error-message">{errorMsg}</div>}

        <div className="button-group">
          <button onClick={handleSubmit} className="submit-button">
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
      </div>
    </div>
  );
};

// 래퍼 컴포넌트: AuthContext에서 userId를 가져와 props로 전달
const ChangePasswdWrapper = () => {
  const { userId } = useContext(AuthContext);
  return <ChangePasswd userId={userId} />;
};

export default ChangePasswdWrapper;