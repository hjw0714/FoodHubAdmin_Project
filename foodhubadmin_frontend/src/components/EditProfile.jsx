import { useContext, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../App';
import '../assets/css/editProfile.css';

const EditProfile = ({ user, setUser, fetchUser }) => {
  
  const navigate = useNavigate();
  const { userId } = useContext(AuthContext);
  const [email, setEmail] = useState(user.email || '');
  const [tel, setTel] = useState(user.tel || '');
  const [uploadProfile, setUploadProfile] = useState(null);
  const [previewUrl, setPreviewUrl] = useState(
    user.profileUUID ? `${import.meta.env.VITE_API_URL}/images/${user.profileUUID}` : ''
  );
  const [errorMsg, setErrorMsg] = useState('');

  // 파일 선택 시 미리보기 업데이트
  const handleFileChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      setUploadProfile(file);
      setPreviewUrl(URL.createObjectURL(file));
    }
  };

  const handleUpdate = async () => {
    if (!userId) {
      setErrorMsg('사용자 ID가 필요합니다.');
      navigate('/admin/profile-view');
      return;
    }

    if (!email || !tel) {
      setErrorMsg('이메일과 전화번호를 모두 입력해주세요.');
      return;
    }

    const formData = new FormData();
    const buildFormData = {
      userId: user.userId,
      email: email,
      tel: tel,
    };

    formData.append('requestDto', new Blob([JSON.stringify(buildFormData)], { type: 'application/json' }));
    if (uploadProfile) {
      formData.append('uploadProfile', uploadProfile); // 프로필 사진 파일 추가
    }

    try {
      const { data } = await axios.put(`${import.meta.env.VITE_API_URL}/user/updateProfile`, formData, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`,
          'Content-Type': 'multipart/form-data',
        },
      });
      setUser(data); // 업데이트된 사용자 정보로 상태 갱신
      fetchUser();   // 부모 컴포넌트에서 사용자 정보 새로고침
      alert('프로필이 성공적으로 수정되었습니다.');
      navigate('/admin/profile-view');
    } catch (error) {
      console.error('Update error:', error.response ? error.response.data : error.message);
      if (error.response?.status === 401) {
        navigate('/error/401');
      } else if (error.response?.status === 403) {
        navigate('/error/403');
      } else {
        setErrorMsg('프로필 수정 중 오류가 발생했습니다.');
      }
    }
  };

  return (
    <div className="edit-profile-container">
      <div className="edit-profile-section">
        <h2>회원정보 수정</h2>

        <div className="profile-photo-wrapper">
          <label>프로필 사진</label>
          <img
            src={previewUrl || '/defaultProfile.png'} // 기본 이미지 경로 조정 필요
            alt="Profile Preview"
            className="profile-photo-preview"
            onError={(e) => (e.target.src = '/defaultProfile.png')}
          />
          <input
            type="file"
            accept="image/*"
            onChange={handleFileChange}
            className="file-input"
          />
        </div>

        <div className="profile-item">
          <label>이메일</label>
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            placeholder="이메일을 입력하세요"
            className="profile-input"
          />
        </div>

        <div className="profile-item">
          <label>전화번호</label>
          <input
            type="tel"
            value={tel}
            onChange={(e) => setTel(e.target.value)}
            placeholder="전화번호를 입력하세요"
            className="profile-input"
          />
        </div>

        {errorMsg && <div className="error-message">{errorMsg}</div>}

        <div className="button-group">
          <button onClick={handleUpdate} className="submit-button">
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

export default EditProfile;