import defaultProfile from '../assets/defaultProfile.png';
import '../assets/css/profileView.css';
import { useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';
import axios from 'axios';
import ChangePasswd from './ChangePasswd';
import EditProfile from './EditProfile';

const ProfileView = () => {

  const navigate = useNavigate();
  const [viewMode, setViewMode] = useState('profileView');
  const [user, setUser] = useState(null);


  const fetchUser = async () => {
    try {
      const { data } = await axios.get(`${import.meta.env.VITE_API_URL}/user/profile`, {
        headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
      });
      setUser(data);
    } catch (error) {
      if (error.response.status === 401) {
        navigate('/error/401');
      }
      else if (error.response.status === 403) {
        navigate('/error/403');
      }
      else {
        // navigate('/error/500');
        console.error(error);
      }
    }
  };

  useEffect(() => {
    fetchUser();
  }, []);

  if (!user) return <div>로딩중 .....</div>


  return (
    <div className="profile-view-container">
      {viewMode === 'profileView' && (
      <>
        <div className="profile-view-section">
          <h2>'{user.nickname}' 님의 프로필 정보</h2>

          <div className="profile-photo-wrapper">
            <img
              // src={user.profileUuid ? `${import.meta.env.VITE_API_URL}/images/${user.profileUuid}` : defaultProfile}
              src={user.profileUuid ? `${import.meta.env.VITE_IMAGE_URL}/images/${user.profileUuid}` : defaultProfile}
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
            <button className="edit-button" onClick={() => setViewMode('update')}>
              ✏️ 회원정보 수정
            </button>
            <button className="edit-button" onClick={() => setViewMode('passwd')}>
              🔐 비밀번호 변경
            </button>
            {/* <button className="edit-button" onClick={() => setViewMode('delete')}>
            ❌ 회원 탈퇴
          </button> */}
          </div>
        </div>
      </>
      )}
      {viewMode === 'update' && <EditProfile user={user} setUser={setUser} setViewMode={setViewMode} fetchUser={fetchUser} />}
      {viewMode === 'passwd' && <ChangePasswd userId={user.userId} setViewMode={setViewMode} />}
    </div>
  );
};

export default ProfileView;