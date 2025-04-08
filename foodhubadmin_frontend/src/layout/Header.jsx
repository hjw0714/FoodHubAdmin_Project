import { useState, useEffect, useRef, useContext } from 'react';
import defaultProfile from '../assets/defaultProfile.png';

import '../assets/header.css';
import { AuthContext } from '../App';
import { useNavigate } from 'react-router-dom';

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

  // 로그아웃
  const {setMembershipType, setIsLoggedIn} = useContext(AuthContext);
  const navigate = useNavigate();
  const handleLogout = () => {
    localStorage.removeItem("token");
    setIsLoggedIn(false);
    setMembershipType(null);
    navigate("/login");
  };

  return (
    <header className="global-header">

        <div className="header-left">
            <div className="logo">Food Hub</div>
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
                <span onClick={handleLogout}>🚪 로그아웃</span>
            </div>
            )}

    </header>

  );
};

export default Header;
