import React from 'react';
import {
  BarChart, Bar, LineChart, Line,
  XAxis, YAxis, Tooltip, CartesianGrid, ResponsiveContainer
} from 'recharts';
import '../assets/css/adminDashboard.css';

const months = ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'];

const data = months.map((month, i) => ({
  name: month,
  totalUsers: [217, 237, 249, 193, 206, 181, 207, 174, 175, 191, 214, 235][i],
  userJoins: [61, 75, 80, 60, 93, 78, 85, 66, 72, 88, 70, 81][i],
  userLeaves: [12, 22, 11, 30, 12, 18, 20, 15, 14, 19, 16, 17][i],
  posts: [317, 312, 330, 359, 364, 382, 399, 377, 366, 388, 405, 420][i],
  comments: [527, 411, 619, 487, 442, 500, 533, 478, 466, 509, 540, 580][i],
  postReports: [6, 9, 10, 10, 10, 12, 11, 13, 12, 14, 15, 16][i],
  commentReports: [12, 10, 7, 11, 11, 9, 13, 12, 11, 13, 12, 14][i],
  visitors: [988, 1183, 828, 928, 856, 1022, 1130, 1195, 1230, 1301, 1255, 1344][i]
}));

const DashboardHome = () => {
  return (
    <>
      <div className="dashboard-section">
        <h3>👤 회원 통계 (가입/탈퇴)</h3>
        <ResponsiveContainer width="100%" height={250}>
          <LineChart data={data}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" />
            <YAxis />
            <Tooltip />
            <Line type="monotone" dataKey="userJoins" stroke="#42a5f5" name="회원 가입" />
            <Line type="monotone" dataKey="userLeaves" stroke="#ef5350" name="회원 탈퇴" />
          </LineChart>
        </ResponsiveContainer>
      </div>

      <div className="dashboard-section">
        <h3>👣 방문자 수 통계</h3>
        <ResponsiveContainer width="100%" height={250}>
          <LineChart data={data}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" />
            <YAxis />
            <Tooltip />
            <Line type="monotone" dataKey="visitors" stroke="#7e57c2" name="방문자 수" />
          </LineChart>
        </ResponsiveContainer>
      </div>

      <div className="dashboard-section">
        <h3>📝 게시글 통계</h3>
        <ResponsiveContainer width="100%" height={250}>
          <BarChart data={data}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" />
            <YAxis />
            <Tooltip />
            <Bar dataKey="posts" fill="#66bb6a" name="게시글 수" />
          </BarChart>
        </ResponsiveContainer>
      </div>

      <div className="dashboard-section">
        <h3>💬 댓글 통계</h3>
        <ResponsiveContainer width="100%" height={250}>
          <BarChart data={data}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" />
            <YAxis />
            <Tooltip />
            <Bar dataKey="comments" fill="#ffb74d" name="댓글 수" />
          </BarChart>
        </ResponsiveContainer>
      </div>

      <div className="dashboard-section">
        <h3>🚨 신고 통계 (게시글/댓글)</h3>
        <ResponsiveContainer width="100%" height={250}>
          <BarChart data={data}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" />
            <YAxis />
            <Tooltip />
            <Bar dataKey="postReports" fill="#ab47bc" name="게시글 신고" />
            <Bar dataKey="commentReports" fill="#26c6da" name="댓글 신고" />
          </BarChart>
        </ResponsiveContainer>
      </div>
    </>
  );
};

export default DashboardHome;
