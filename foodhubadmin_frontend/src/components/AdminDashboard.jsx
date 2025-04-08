import React from 'react';
import { Link, Outlet } from 'react-router-dom';
import '../assets/css/adminDashboard.css';

const AdminDashboard = () => {
  return (
    <div className="admin-wrapper">
      <div className="dashboard-body">
        <aside className="sidebar">
          <nav>
            <Link to="/admin/dashboard">📊 대시보드</Link>
            <Link to="/admin/dashboard/postReport">🚨 신고 목록</Link>
            <Link to="/admin/dashboard/banner">🖼️ 배너 수정</Link>
            <Link to="/admin/dashboard/chat">💬 관리자 채팅</Link>
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
