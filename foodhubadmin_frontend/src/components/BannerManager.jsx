import { useState } from 'react';
import '../assets/css/bannerManager.css';
import banner1 from '../assets/img/banner-1.jpg';
import banner2 from '../assets/img/banner-2.jpg';
import banner3 from '../assets/img/banner-3.jpg';

const initialBanners = [
    {
      id: 1,
      imageUrl: banner1,
      title: '[이벤트] 신규 가입자 30% 할인 쿠폰 증정!',
      description: '지금 가입하고 할인 혜택을 받아보세요.',
      link: '/event.html',
    },
    {
      id: 2,
      imageUrl: banner2,
      title: '[베스트 글] 이번주 가장 많은 추천을 받은 게시글!',
      description: 'Food Hub 유저들이 추천하는 핫한 주제 TOP 5!',
      link: '/foodhub/post/postDetail?postId=1',
    },
    {
      id: 3,
      imageUrl: banner3,
      title: '[매장 홍보] 특별 할인 진행 중!',
      description: '지금 방문하면 추가 할인 혜택까지!',
      link: '/store-promo.html',
    },
  ];

const BannerManager = () => {
  const [banners, setBanners] = useState(initialBanners);

  const handleChange = (id, key, value) => {
    const updated = banners.map(b =>
      b.id === id ? { ...b, [key]: value } : b
    );
    setBanners(updated);
  };

  const handleImageChange = (id, file) => {
    const reader = new FileReader();
    reader.onloadend = () => {
      const updated = banners.map((b) =>
        b.id === id ? { ...b, imageUrl: reader.result } : b
      );
      setBanners(updated);
    };
    if (file) {
      reader.readAsDataURL(file);
    }
  };

  const handleDelete = (id) => {
    setBanners(banners.filter(b => b.id !== id));
  };

  return (
    <div className="dashboard-section">
      <h3>🖼️ 배너 이미지 관리</h3>
      <p>홈페이지에 표시되는 메인 배너를 수정하거나 삭제할 수 있습니다.</p>

      <div className="banner-list">
        {banners.map(banner => (
          <div key={banner.id} className="banner-item">
            <img src={banner.imageUrl} alt="배너 썸네일" />
            <input
              type="file"
              accept="image/*"
              onChange={(e) => handleImageChange(banner.id, e.target.files[0])}
            />
            <input
              type="text"
              value={banner.title}
              onChange={(e) => handleChange(banner.id, 'title', e.target.value)}
              placeholder="제목"
            />
            <input
              type="text"
              value={banner.description}
              onChange={(e) => handleChange(banner.id, 'description', e.target.value)}
              placeholder="설명"
            />
            <input
              type="text"
              value={banner.link}
              onChange={(e) => handleChange(banner.id, 'link', e.target.value)}
              placeholder="링크"
            />
            <button onClick={() => handleDelete(banner.id)}>삭제</button>
          </div>
        ))}
      </div>

      <button className="add-btn" onClick={() => {
        const newId = Date.now();
        setBanners([...banners, {
          id: newId,
          imageUrl: '', // 기본 없음
          title: '',
          description: '',
          link: '',
        }]);
      }}>+ 새 배너 추가</button>
    </div>
  );
};

export default BannerManager;
