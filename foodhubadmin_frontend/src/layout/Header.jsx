import { useState, useEffect, useRef, useContext } from 'react';
import defaultProfile from '../assets/defaultProfile.png';
import '../assets/css/header.css';
import { AuthContext } from '../App';
import { Link, replace, useNavigate } from 'react-router-dom';


const Header = () => {
  const navigate = useNavigate();
  const [dropdownOpen, setDropdownOpen] = useState(false);
  const dropdownRef = useRef(null);
  const { membershipType , setMembershipType , isLoggedIn , setIsLoggedIn } = useContext(AuthContext);
  

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

  const handleLogout = () => {
    localStorage.removeItem("token");
    setIsLoggedIn(false);
    setMembershipType(null);
    navigate("/" , {replace:true});
  };

  return (
    <header className="global-header">

      <div className="header-left">
        <Link to="/admin/dashboard" className="logo">Food Hub</Link>
      </div>

      <div className="header-right" ref={dropdownRef}>
        <div className="profile-toggle" onClick={() => setDropdownOpen(!dropdownOpen)}>
          <img src={defaultProfile} alt="profile" />
          <span>관리자 ▾</span>
      {isLoggedIn && (
        membershipType === "ADMIN" && (
        <div className="header-right" ref={dropdownRef}>
          <div className="profile-toggle" onClick={() => setDropdownOpen(!dropdownOpen)}>
            <img src={defaultProfile} alt="profile" />
            <span>관리자 ▾</span>
          </div>
          {dropdownOpen && (
            <div className="profile-dropdown">
              <a><span>⚙️ 프로필 설정</span></a>
              <a><span>📋 활동 로그</span></a>
              <a><span onClick={handleLogout}>🚪 로그아웃</span></a>
            </div>
          )}
        </div>
        )
      )}
    </header>
  );
};

export default Header;