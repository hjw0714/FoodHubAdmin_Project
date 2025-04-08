import { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import defaultProfile from '../assets/defaultProfile.png';
import '../assets/css/profileView.css';

const ProfileView = () => {
  const navigate = useNavigate();
  const [user, setUser] = useState(null); // 초기값을 null로 변경

  const fetchUser = async () => {
    try {
      // 백엔드가 없으므로 임시로 데트스 데이터 사용
      // 실제 백엔드 연결 시 아래 주석 해제
      /*
      const { data } = await axios.get(`${import.meta.env.VITE_API_URL}/user/profile`, 
        { headers: { Authorization: `Bearer ${localStorage.getItem('token')}` } }
      );
      setUser(data);
      */

      // 데스트 데이터로 시뮬레이션  (백엔드 연결 시 테스트 데이터 삭제)
      const testData = {
        userId: 'testuser',
        nickname: '테스트유저',
        email: 'test@example.com',
        tel: '010-1234-5678',
        gender: '남성',
        birthday: '1990-01-01',
        profileUuid: 'default-profile-uuid',
      };
      setUser(testData);
    } catch (error) {
      // error.response가 있는지 먼저 확인
      if (error.response) {
        if (error.response.status === 401) {
          navigate('/error/401');
        } else if (error.response.status === 500) {
          navigate('/error/500');
        }
      }
    }
  };

  useEffect(() => {
    fetchUser();
  }, []);

  if (!user) return <div>로딩 중...</div>; // user가 null일 때 로딩 표시

  return (
    <div className="profile-view-container">
      <div className="profile-view-section">
        <h2>{user.nickname}님의 프로필 정보</h2>

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
          <span>{user.gender}</span>
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