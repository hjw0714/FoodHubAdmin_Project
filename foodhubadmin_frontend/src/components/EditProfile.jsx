import axios from "axios";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import defaultProfile from '../assets/defaultProfile.png';
import '../assets/css/editProfile.css';

// 이메일 전화번호
const EditProfile = ({ user, setUser, setViewMode, fetchUser }) => {

  const navigate = useNavigate();
  const [email, setEmail] = useState(user.email);
  const [tel, setTel] = useState(user.tel);
  const [uploadProfile, setUploadProfile] = useState(null);
  const [errorMsg, setErrorMsg] = useState('');
  const [previewImage, setPreviewImage] = useState( // 프로필 사진 미리보기
    user.profileUuid ? `${import.meta.env.VITE_IMAGE_URL}/images/${user.profileUuid}` : defaultProfile
  );

  // 파일 선택 시 미리보기 업데이트
  const handleFileChange = (e) => {
    const file = e.target.files[0];
    setUploadProfile(file);
    if (file) {
      const reader = new FileReader();
      reader.onloadend = () => {
        setPreviewImage(reader.result);
      };
      reader.readAsDataURL(file);
    }
  };

  const handleSubmit = async () => {

    if (!email || !tel) {
      setErrorMsg('모든 작성란을 입력해주세요.');
      return;
    }

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
      setErrorMsg('올바른 이메일 형식을 입력해주세요.');
      return;
    }

    const telRegex = /^\d{3}-\d{3,4}-\d{4}$/;
    if (!telRegex.test(tel)) {
      setErrorMsg('전화번호 형식을 확인해주세요. (예: 010-1234-5678)');
      return;
    }

    const formData = new FormData();

    const buildFormData = {
      userId: user.userId,
      nickname: user.nickname,
      email: email,
      tel: tel
    }

    formData.append(
      "requestDto",
      new Blob([JSON.stringify(buildFormData)],
        { type: 'application/json' })
    );

    if (uploadProfile) {
      formData.append('uploadProfile', uploadProfile)
    }

    try {
      // const { data } = await axios.put(`${import.meta.env.VITE_API_URL}/user/editProfile`, formData, {
      const { data } = await axios.put(`${import.meta.env.VITE_API_URL}/user/editProfile`, formData, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`,
          'Content-Type': 'multipart/form-data'
        }
      });
      setUser(data);
      fetchUser();
      alert('회원 정보가 수정되었습니다.');
      setViewMode('profileView');
    } catch (error) {




      console.error('오류 발생: ' , error);


      
      if (error.response.status === 401) {
        navigate('/error/401');
      } else if (error.response.status === 403) {
        navigate('/error/403');
      } else {
        setErrorMsg('회원정보 변경 중 오류가 발생했습니다.');
        console.error(error);
      }
    }

  };

  const handleCancel = () => {
    if (window.confirm('회원정보 수정을 취소하시겠습니까?')) {
      setViewMode('profileView');
    }
  };

  return (
    <div className="edit-profile-container">
      <div className="edit-profile-section">
        <h2>'{user.nickname}' 님의 회원정보 수정</h2>

        <div className="profile-photo-wrapper">
          <img
            src={previewImage}
            alt="프로필 미리보기"
            className="profile-photo"
            onError={(e) => (e.target.src = defaultProfile)} // 이미지 오류 시 기본 이미지
          />

          <input
            type="file"
            accept="image/*"
            onChange={handleFileChange}
            className="file-input"
          />
        </div>

        <div className="edit-profile-item">
          <label>이메일</label>
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            className="edit-profile-input"
            placeholder="이메일을 입력하세요"
          />
        </div>

        <div className="edit-profile-item">
          <label>전화번호</label>
          <input
            type="tel"
            value={tel}
            onChange={(e) => setTel(e.target.value)}
            className="edit-profile-input"
            placeholder="전화번호를 입력하세요 (예: 010-1234-5678)"
          />
        </div>

        {errorMsg && <div className="error-message">{errorMsg}</div>}

        <div className="button-group">
          <button className="submit-button" onClick={handleSubmit}>
            💾 저장
          </button>
          <button className="cancel-button" onClick={handleCancel}>
            ↩ 취소
          </button>
        </div>
      </div>
    </div>
  );


}

export default EditProfile;