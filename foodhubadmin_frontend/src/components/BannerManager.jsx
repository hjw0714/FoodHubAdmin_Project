import { useEffect, useState } from 'react';
import '../assets/css/bannerManager.css';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const BannerManager = () => {
  const [banners, setBanners] = useState([]);
  const [editingBannerId, setEditingBannerId] = useState(null); // 수정 중인 배너 ID
  const navigate = useNavigate();

  const fetchBanners = async () => {
    try {
      const response = await axios.get(`${import.meta.env.VITE_API_URL}/admin/banner`, {
        headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
      });
      setBanners(response.data);
    } catch (error) {
      if (error.response) {
        if (error.response.status === 401) {
          navigate('/error/401');
        } else if (error.response.status === 500) {
          navigate('/error/500');
        } else if (error.response.status === 404) {
          console.error('404: 해당 API 경로가 존재하지 않습니다.');
        }
      } else {
        console.error('요청 실패:', error.message);
      }
    }
  };

  const handleSave = async (banner) => {
    if (!banner.title || !banner.description || !banner.file) {
      alert("입력되지 않은 항목이 있습니다.");
      return;
    }

    const formData = new FormData();
    const dto = {
      title: banner.title,
      description: banner.description,
      bannerOriginalName: banner.file.name,
      link: banner.link
    };

    formData.append("requestDto", new Blob([JSON.stringify(dto)], { type: "application/json" }));
    formData.append("imageFile", banner.file);

    try {
      await axios.post(`${import.meta.env.VITE_API_URL}/admin/banner/save`, formData, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`,
          'Content-Type': 'multipart/form-data'
        }
      });
      alert("저장 성공!");
      setEditingBannerId(null);
      fetchBanners();
    } catch (error) {
      console.error(error);
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm("정말 삭제하시겠습니까?")) return;
    try {
      await axios.delete(`${import.meta.env.VITE_API_URL}/admin/banner/${id}`, {
        headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
      });
      alert("삭제 성공");
      fetchBanners();
    } catch (error) {
      console.error(error);
      alert("삭제 실패");
    }
  };

  const handleEdit = async (banner) => {
    if (!banner.title || !banner.description) {
      alert("입력되지 않은 항목이 있습니다.");
      return;
    }

    const formData = new FormData();
    const dto = {
      title: banner.title,
      description: banner.description,
      bannerOriginalName: banner.file?.name || banner.bannerOriginalName,
      link: banner.link
    }

    formData.append("requestDto", new Blob([JSON.stringify(dto)], { type: "application/json" }));

    if (banner.file) {
      formData.append("imageFile", banner.file); // 파일이 있으면 파일 추가
    }

    try {
      await axios.patch(`${import.meta.env.VITE_API_URL}/admin/banner/update/${banner.id}`, formData, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`,
          'Content-Type': 'multipart/form-data'
        }
      });
      alert("수정 성공");
      setEditingBannerId(null);
      fetchBanners();
    } catch (error) {
      console.log(error);
      alert("수정 실패");
    }
  };

  const handleChange = (id, key, value) => {
    const updated = banners.map(b => b.id === id ? { ...b, [key]: value } : b);
    setBanners(updated);
  };

  const handleImageChange = (id, file) => {
    const reader = new FileReader();
    reader.onloadend = () => {
      const updated = banners.map(b => b.id === id ? { ...b, imageUrl: reader.result, file } : b);
      setBanners(updated);
    };
    if (file) reader.readAsDataURL(file);
  };

  const handleAddBanner = () => {
    const newId = Date.now();
    setEditingBannerId(newId);
    setBanners([...banners, {
      id: newId,
      title: '',
      description: '',
      link: '',
      imageUrl: '',
      file: null
    }]);
  };

  const handleCancel = (id) => {
    // 새로 추가한 배너라면 삭제 (id가 타임스탬프이므로 길이로 구분)
    if (id.toString().length >= 13) {
      setBanners((prev) => prev.filter((b) => b.id !== id));
    } else {
      setEditingBannerId(null);
      fetchBanners();
    }
  }

  useEffect(() => { fetchBanners(); }, []);

  return (
    <div className="dashboard-section">
      <h3>🖼️ 배너 이미지 관리</h3>
      <p>홈페이지에 표시되는 메인 배너를 수정하거나 삭제할 수 있습니다.</p>

      <div className="banner-list">
        {banners.map((banner) => (
          <div key={banner.id} className="banner-item">
            {banner.bannerUuid && (
              <div
                className="banner-preview"
                style={{
                  backgroundImage: `url(${import.meta.env.VITE_API_URL}/images/${banner.bannerUuid})`
                }}
              ></div>
            )}{banner.bannerOriginalName}
            <input
              type="file"
              accept="image/*"
              disabled={editingBannerId !== banner.id}
              onChange={(e) => handleImageChange(banner.id, e.target.files[0])}
            />
            <input
              type="text"
              value={banner.title}
              disabled={editingBannerId !== banner.id}
              onChange={(e) => handleChange(banner.id, 'title', e.target.value)}
              placeholder="제목"
            />
            <input
              type="text"
              value={banner.description}
              disabled={editingBannerId !== banner.id}
              onChange={(e) => handleChange(banner.id, 'description', e.target.value)}
              placeholder="설명"
            />
            <input
              type="text"
              value={banner.link || ''}
              disabled={editingBannerId !== banner.id}
              onChange={(e) => handleChange(banner.id, 'link', e.target.value)}
              placeholder="링크 URL (예: https://example.com)"
            />
            <div className="button-group">
              {banner.id && banner.id.toString().length < 13 ? (
                editingBannerId === banner.id ? (
                  <>
                    <button className="save-button" onClick={() => handleEdit(banner)}>저장</button>
                    <button className="cancel-button" onClick={() => handleCancel(banner.id)}>취소</button>
                  </>
                ) : (
                  <>
                    <button className="edit-button" onClick={() => setEditingBannerId(banner.id)}>수정</button>
                    <button className="delete-button" onClick={() => handleDelete(banner.id)}>삭제</button>
                  </>
                )
              ) : (
                <>
                  <button className="save-button" onClick={() => handleSave(banner)}>저장</button>
                  <button className="cancel-button" onClick={() => handleCancel(banner.id)}>취소</button>
                </>
              )}
            </div>
          </div>
        ))}
      </div>
      <br />
      <button className="add-btn" onClick={handleAddBanner}>+ 새 배너 추가</button>
    </div>
  );
};

export default BannerManager;