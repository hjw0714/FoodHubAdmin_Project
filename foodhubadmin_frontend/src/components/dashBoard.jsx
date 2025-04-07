import React from 'react';
import {
  LineChart, Line, BarChart, Bar,
  XAxis, YAxis, Tooltip, CartesianGrid, ResponsiveContainer
} from 'recharts';
import '../assets/adminDashboard.css';

const sampleData = [
  { name: 'Jan', users: 120, posts: 200, comments: 300, visitors: 500 },
  { name: 'Feb', users: 150, posts: 180, comments: 280, visitors: 450 },
  { name: 'Mar', users: 170, posts: 220, comments: 350, visitors: 550 },
  { name: 'Apr', users: 200, posts: 240, comments: 400, visitors: 600 },
];

const AdminDashboard = () => {

  
  return (
    <div className="admin-wrapper">
      {/* 전체 헤더 */}
      

      {/* 헤더 아래 전체 영역 */}
      <div className="dashboard-body">
        <aside className="sidebar">
          <nav>
            <a href="#">📊 대시보드</a>
            <a href="#">👥 회원 관리</a>
            <a href="#">🚨 신고 목록</a>
            <a href="#">🖼️ 배너 수정</a>
            <a href="#">💬 관리자 채팅</a>
          </nav>
        </aside>

        <main className="main-content">
          <div className="content">
            <div className="dashboard-section">
              <h3>회원 통계</h3>
              <ResponsiveContainer width="100%" height={200}>
                <LineChart data={sampleData}>
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis dataKey="name" />
                  <YAxis />
                  <Tooltip />
                  <Line type="monotone" dataKey="users" stroke="#8884d8" />
                </LineChart>
              </ResponsiveContainer>
            </div>

            <div className="dashboard-section">
              <h3>게시글 및 댓글 통계</h3>
              <ResponsiveContainer width="100%" height={200}>
                <BarChart data={sampleData}>
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis dataKey="name" />
                  <YAxis />
                  <Tooltip />
                  <Bar dataKey="posts" fill="#82ca9d" />
                  <Bar dataKey="comments" fill="#ffc658" />
                </BarChart>
              </ResponsiveContainer>
            </div>

            <div className="dashboard-section">
              <h3>신고 관리</h3>
              <button>게시글 신고 목록</button>
              <button>댓글 신고 목록</button>
            </div>

            <div className="dashboard-section">
              <h3>회원 관리</h3>
              <input type="text" placeholder="회원 검색" />
              <button className="btn">상태 변경</button>
            </div>

            <div className="dashboard-section">
              <h3>배너 이미지 관리</h3>
              <button>배너 이미지 수정</button>
            </div>

            <div className="dashboard-section">
              <h3>방문자 통계</h3>
              <ResponsiveContainer width="100%" height={200}>
                <LineChart data={sampleData}>
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis dataKey="name" />
                  <YAxis />
                  <Tooltip />
                  <Line type="monotone" dataKey="visitors" stroke="#8884d8" />
                </LineChart>
              </ResponsiveContainer>
            </div>

            <div className="dashboard-section">
              <h3>관리자 채팅</h3>
              <button>채팅 시작하기</button>
            </div>
          </div>
        </main>
      </div>
    </div>
  );
};

export default AdminDashboard;
