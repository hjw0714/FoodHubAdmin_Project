import { useEffect, useState } from 'react';
import '../assets/css/bannerManager.css';
import axios from 'axios';

const BannerManager = () => {
  const [banners, setBanners] = useState([]);
  const [editingBannerId, setEditingBannerId] = useState(null); // 수정 중인 배너 ID

  const fetchBanners = async () => {
    try {
      const response = await axios.get(`${import.meta.env.VITE_API_URL}/admin/banner`, {
        headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
      });
      setBanners(response.data);
    } catch (error) {
      console.error(error);
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
      bannerOriginalName: banner.file.name
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
      imageUrl: '',
      file: null
    }]);
  };

  useEffect(() => { fetchBanners(); }, []);

  return (
    <div className="dashboard-section">
      <h3>🖼️ 배너 이미지 관리</h3>
      <p>홈페이지에 표시되는 메인 배너를 수정하거나 삭제할 수 있습니다.</p>

      <div className="banner-list">
        {banners.map((banner) => (
          <div key={banner.id} className="banner-item">
            {banner.bannerUuid && (
              <img
                src={`${import.meta.env.VITE_API_URL}/images/${banner.bannerUuid}`}
                alt="배너 이미지"
                style={{ width: '100%', maxWidth: '300px' }}
              />
            )}
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
            <div className="button-group">
              {banner.id && banner.id.toString().length < 13 ? (
                <>
                  {editingBannerId === banner.id ? (
                    <button className="save-button" onClick={() => handleSave(banner)}>저장</button>
                  ) : (
                    <button className="gray-button" onClick={() => setEditingBannerId(banner.id)}>수정</button>
                  )}
                  <button className="delete-button" onClick={() => handleDelete(banner.id)}>삭제</button>
                </>
              ) : (
                <button className="save-button" onClick={() => handleSave(banner)}>저장</button>
              )}
            </div>
          </div>
        ))}
      </div>

      <button className="add-btn" onClick={handleAddBanner}>+ 새 배너 추가</button>
    </div>
  );
};

export default BannerManager;