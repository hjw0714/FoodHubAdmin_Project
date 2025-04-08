import React, { useState } from 'react';
import axios from "axios";
import {
  LineChart, Line, BarChart, Bar,
  XAxis, YAxis, Tooltip, CartesianGrid, ResponsiveContainer
} from 'recharts';
import '../assets/css/adminDashboard.css';
import { useNavigate } from 'react-router-dom';

const sampleData = [
  { name: 'Jan', users: 120, posts: 200, comments: 300, visitors: 500 },
  { name: 'Feb', users: 150, posts: 180, comments: 280, visitors: 450 },
  { name: 'Mar', users: 170, posts: 220, comments: 350, visitors: 550 },
  { name: 'Apr', users: 200, posts: 240, comments: 400, visitors: 600 },
];


const DashboardHome = () => {

  const navigate = useNavigate();
  const [yearlyNewPostCnt, setYearlyNewPostCnt] = useState([]);
  const [monthlyNewPostCnt, setMonthlyNewPostCnt] = useState([]);
  const [dailyNewPostCnt, setDailyNewPostCnt] = useState([]);

  const fetchPostData = async(endpoint) => {
    try {
      const {data} = await axios.get(`${import.meta.env.VITE_API_URL}/posts/`)
    } catch(error) {
      console.log();
    }
  };

  return (
    <>
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
            <h3>월별 새 게시글 및 댓글 통계</h3>
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
    
    </>
  );
};

export default DashboardHome;
