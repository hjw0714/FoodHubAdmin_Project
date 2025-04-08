import React, { useState } from 'react';
import { Link, Outlet } from 'react-router-dom';
import '../assets/css/adminDashboard.css';

const AdminDashboard = () => {

  const [userStatsOpen , setUserStatsOpen] = useState(false);
  const [postStatsOpen , setPostStatsOpen] = useState(false);
  const [reportStatsOpen , setReportStatsOpen] = useState(false);
  const [reportListOpen , setReportListOpen] = useState(false);

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
                onClick={() => setReportStatsOpen(!reportStatsOpen)}
              >
                🚨 신고 통계 {reportStatsOpen ? '▴' : '▾'}
              </div>
              {reportStatsOpen && (
                <div className="dropdown-menu">
                  <Link to="/admin/dashboard/postReportStats"> - 게시글 신고 수 </Link>
                  <Link to="/admin/dashboard/commentReportStats">- 댓글 신고 수 </Link>
                </div>
              )}
            </div>

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
