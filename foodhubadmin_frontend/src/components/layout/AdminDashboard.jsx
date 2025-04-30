import React, { useState } from 'react';
import { Link, Outlet } from 'react-router-dom';
import '../assets/css/adminDashboard.css';

const AdminDashboard = () => {

  const [userStatsOpen, setUserStatsOpen] = useState(false);
  const [postStatsOpen, setPostStatsOpen] = useState(false);
  const [reportListOpen, setReportListOpen] = useState(false);
  const [adminPostOpen, setAdminPostOpen] = useState(false);


  return (
    <div className="admin-wrapper">
      <div className="dashboard-body">
        <aside className="sidebar">
          <nav>
            <Link to="/admin/dashboard">📊 대시보드</Link>

            <div className="dropdown">
              <div
                className="dropdown-toggle"
                onClick={() => setUserStatsOpen(!userStatsOpen)}
              >
                👤 회원 통계 {userStatsOpen ? '▴' : '▾'}
              </div>
              {userStatsOpen && (
                <div className="dropdown-menu">
                  <Link to="/admin/dashboard/userStatus">- 총 회원 수</Link>
                  <Link to="/admin/dashboard/userJoin">- 회원 가입</Link>
                  <Link to="/admin/dashboard/userLeave">- 회원 탈퇴</Link>
                </div>
              )}
            </div>

            <div className="dropdown">
              <div
                className="dropdown-toggle"
                onClick={() => setPostStatsOpen(!postStatsOpen)}
              >
                📝 게시글 통계 {postStatsOpen ? '▴' : '▾'}
              </div>
              {postStatsOpen && (
                <div className="dropdown-menu">
                  <Link to="/admin/dashboard/postListTotal"> - 총 게시글 수 </Link>
                  <Link to="/admin/dashboard/categoryPostList">- 카테고리별 게시글 수 </Link>
                </div>
              )}
            </div>

            <Link to="/admin/dashboard/commentStats">🗒️ 댓글 통계</Link>

            <div className="dropdown">
              <div
                className="dropdown-toggle"
                onClick={() => setReportListOpen(!reportListOpen)}
              >
                🚨 신고 목록 {reportListOpen ? '▴' : '▾'}
              </div>
              {reportListOpen && (
                <div className="dropdown-menu">
                  <Link to="/admin/dashboard/postReport"> - 게시글 신고 목록 </Link>
                  <Link to="/admin/dashboard/commentReport">- 댓글 신고 목록 </Link>
                </div>
              )}
            </div>
            <Link to="/admin/dashboard/memberList">👥 회원 리스트</Link>
            <Link to="/admin/dashboard/visitorStats">👣 방문자 통계</Link>
            <Link to="/admin/dashboard/bannerManager">🖼️ 배너 수정</Link>
            <Link to="/admin/dashboard/adminChat">💬 관리자 채팅</Link>
            <div className="dropdown">
              <div
                className="dropdown-toggle"
                onClick={() => setAdminPostOpen(!adminPostOpen)}
              >
                🛠️ 관리자 게시판 {adminPostOpen ? '▴' : '▾'}
              </div>
              {adminPostOpen && (
                <div className="dropdown-menu">
                  <Link to="/admin/dashboard/createPost">- 관리자 게시글 작성</Link>
                  <Link to="/admin/dashboard/adminPostList">- 관리자 게시글 목록</Link>
                </div>
              )}
            </div>
            <Link
              to="http://localhost/foodhub"
              target="_blank"
              className="external-link"
            >
              <div className="external-link-content">
                <span className="icon">🏠</span>
                <div className="text">
                  <div>FOOD HUB</div>
                  <div className="subtext">메인으로 이동</div>
                </div>
              </div>
            </Link>
          </nav>
        </aside>

        <main className="main-content">
          <div className="content">
            <Outlet /> {/* ← 본문에 상세 페이지 출력 */}
          </div>
        </main>
      </div>
    </div>
  );
};

export default AdminDashboard;
