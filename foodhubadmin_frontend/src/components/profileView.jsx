import { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import defaultProfile from '../assets/defaultProfile.png';
import '../assets/css/profileView.css';

const ProfileView = () => {
  const navigate = useNavigate();
  const [user, setUser] = useState({});

  const fetchUser = async () => {
    try {
      const { data } = await axios.get(`${import.meta.env.VITE_API_URL}/user/myInfo`, {
        headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
      });
      setUser({
        ...data,
        profilePhoto: data.profileUuid
          ? `${import.meta.env.VITE_API_URL}/images/${data.profileUuid}`
          : defaultProfile,
        birthday: data.birthday ? new Date(data.birthday).toLocaleDateString() : ''
      });
    } catch (error) {
      if (error.response?.status === 403) {
        navigate('/error/403');
      } else if (error.response?.status === 401) {
        navigate('/error/401');
      } else {
        console.error(error);
      }
    }
  };

  useEffect(() => {
    fetchUser();
  }, []);

  if (!user) return <div>로딩 중...</div>;

  return (
    <div className="profile-view-container">
      <div className="profile-view-section">
        <h2>{user.userId}님의 프로필 정보</h2>

        <div className="profile-photo-wrapper">
          <img src={user.profilePhoto} alt="Profile" className="profile-photo" />
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
          <span>{user.phone}</span>
        </div>

        <div className="profile-item">
          <label>성별</label>
          <span>{user.gender}</span>
        </div>

        <div className="profile-item">
          <label>생일</label>
          <span>{user.birthday}</span>
        </div>

        <div className="button-group">
          <button className="edit-button" onClick={() => navigate('/edit-user')}>
            ✏️ 회원정보 수정
          </button>
          <button className="edit-button" onClick={() => navigate('/change-password')}>
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