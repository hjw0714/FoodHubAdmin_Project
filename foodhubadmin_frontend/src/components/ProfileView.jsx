import { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import defaultProfile from '../assets/defaultProfile.png';
import '../assets/css/profileView.css';

const ProfileView = () => {
  const navigate = useNavigate();
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);
  const [errorMsg, setErrorMsg] = useState(null);

  const fetchUser = async () => {
    try {
      setLoading(true);
      setErrorMsg(null);
     
      const { data } = await axios.get(`${import.meta.env.VITE_API_URL}/user/profile`, 
        { headers: { Authorization: `Bearer ${localStorage.getItem('token')}` } }
      );
      setUser(data);
      
    } catch (error) {
      if (error.response) {
        if (error.response.status === 401) {
          navigate('/error/401');
        } else if (error.response.status === 500) {
          navigate('/error/500');
        }
      }
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchUser();
  }, []);

  if (loading) return <div className="loading">로딩 중...</div>;
  if (errorMsg) return <div className="error-message">{error}</div>;

  return (
    <div className="profile-view-container">
      <div className="profile-view-section">
        <h2>'{user.nickname}' 님의 프로필 정보</h2>

        <div className="profile-photo-wrapper">
          <img
            src={user.profileUuid ? `${import.meta.env.VITE_API_URL}/images/${user.profileUuid}` : defaultProfile}
            alt="Profile"
            className="profile-photo"
            onError={(e) => (e.target.src = defaultProfile)} // 이미지 로드 실패 시 기본 이미지 사용
          />
        </div>

        <div className="profile-item">
          <label>아이디</label>
          <span>{user.userId}</span>
        </div>

        <div className="profile-item">
          <label>닉네임</label>
          <span>{user.nickname}</span>
        </div>

        <div className="profile-item">
          <label>이메일</label>
          <span>{user.email}</span>
        </div>

        <div className="profile-item">
          <label>전화번호</label>
          <span>{user.tel}</span>
        </div>

        <div className="profile-item">
          <label>성별</label>
          <span>{user.gender === 'M' ? '남성' : user.gender === 'F' ? '여성' : '기타'}</span>
        </div>

        <div className="profile-item">
          <label>생일</label>
          <span>{user.birthday}</span>
        </div>

        <div className="button-group">
          <button className="edit-button" onClick={() => navigate('/edit-profile')}>
            ✏️ 회원정보 수정
          </button>
          <button className="edit-button" onClick={() => navigate('/admin/change-passwd')}>
            🔐 비밀번호 변경
          </button>
          <button className="edit-button" onClick={() => navigate('/delete-user')}>
            ❌ 회원 탈퇴
          </button>
        </div>
      </div>
    </div>
  );
};

export default ProfileView;