import { useState, useEffect, useRef } from 'react';
import defaultProfile from '../assets/defaultProfile.png';
import '../assets/css/header.css';
import { Link } from 'react-router-dom';

const Header = () => {
  const [dropdownOpen, setDropdownOpen] = useState(false);
  const dropdownRef = useRef(null);

  // 외부 클릭 시 드롭다운 닫기
  useEffect(() => {
    const handleClickOutside = (e) => {
      if (dropdownRef.current && !dropdownRef.current.contains(e.target)) {
        setDropdownOpen(false);
      }
    };
    document.addEventListener('mousedown', handleClickOutside);
    return () => document.removeEventListener('mousedown', handleClickOutside);
  }, []);

  return (
    <header className="global-header">
      <div className="header-left">
        <Link to="/admin/dashboard" className="logo">Food Hub</Link>
      </div>
      <div className="header-right" ref={dropdownRef}>
        <div className="profile-toggle" onClick={() => setDropdownOpen(!dropdownOpen)}>
          <img src={defaultProfile} alt="profile" />
          <span>관리자 ▾</span>
        </div>
        {dropdownOpen && (
          <div className="profile-dropdown">
            <a href="/settings">⚙️ 프로필 설정</a>
            <a href="/logs">📋 활동 로그</a>
            <a href="/logout">🚪 로그아웃</a>
          </div>
        )}
      </div>
    </header>

  );
};

export default Header;
