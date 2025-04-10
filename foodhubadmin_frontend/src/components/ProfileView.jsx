import { useContext, useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import '../assets/css/profileView.css';
import defaultProfile from '../assets/defaultProfile.png';

const ProfileView = () => {
  const navigate = useNavigate();
  // const { setUserId } = useContext(AuthContext);
  const [user, setUser] = useState({}); // 초기값을 null로 변경

  const fetchUser = async () => {
    try {

      const { data } = await axios.get(`${import.meta.env.VITE_API_URL}/user/profile`,
        { headers: { Authorization: `Bearer ${localStorage.getItem('token')}` } }
      );




      console.log(`http://localhost:8080/api/images/${user.profileUuid}`);
      console.log('User data:', data);
      setUser(data);
      
    } catch (error) {
      console.error('Fetch error:', error.response ? error.response.data : error.message);
      if (error.response.status === 401) {
        navigate('/error/401');
      } else if (error.response.status === 500) {
        // navigate('/error/500');
        console.error(error);
      }
    }
  };

  useEffect(() => {
    fetchUser();
  }, []);

  if (!user) return <h1>로딩 중...</h1>; // user가 null일 때 로딩 표시

  return (
    <div className="profile-view-container">
      <div className="profile-view-section">
        <h2>'{user.nickname}'님의 프로필 정보</h2>

        <div className="profile-photo-wrapper">
          
          <img
            //src={user.profileUUID ? `${import.meta.env.VITE_API_URL}/images/${user.profileUuid}` : defaultProfile}
            src={`http://localhost:8080/api/images/${user.profileUuid}`}
            alt="Profile"
            className="profile-photo"
            //onError={(e) => (e.target.src = defaultProfile)}  // 이미지 로드 실패 시 기본 이미지 사용
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
          <span>{user.gender}</span>
        </div>

        <div className="profile-item">
          <label>생일</label>
          <span>{user.birthday}</span>
        </div>

        <div className="button-group">
          <button className="edit-button" onClick={() => navigate('/admin/edit-profile')}>
            ✏️ 회원정보 수정
          </button>
          <button className="edit-button" onClick={() => navigate('/admin/change-passwd', { state: { userId: user.userId } })} >
            🔐 비밀번호 변경
          </button>
          {/* <button className="edit-button" onClick={() => navigate('/delete-user')}>
            ❌ 회원 탈퇴
          </button> */}
        </div>
      </div>
    </div>
  );
};

export default ProfileView;