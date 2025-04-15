import React, { useEffect, useState } from 'react';
import axios from "axios";
import {
  BarChart, Bar, LineChart, Line,
  XAxis, YAxis, Tooltip, CartesianGrid, ResponsiveContainer
} from 'recharts';
import '../assets/css/adminDashboard.css';
import { useNavigate } from 'react-router-dom';

const months = ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'];

const data = months.map((month, i) => ({
  name: month,
  visitors: [988, 1183, 828, 928, 856, 1022, 1130, 1195, 1230, 1301, 1255, 1344][i]
}));


const DashboardHome = () => {

  const navigate = useNavigate();
  const [userData, setUserData] = useState([]);
  const [postMonthData, setPostMonthData] = useState([]);
  const [commentsMonthData, setCommentsMonthData] = useState([]);
  const [reportMonthData, setReportMonthData] = useState([]);
  

  const fetchHome = async() => {

    try {

      // 신규 가입
      const userJoin = await axios.get(`${import.meta.env.VITE_API_URL}/admin/user/newUser`, 
        { headers: { Authorization: `Bearer ${localStorage.getItem('token')}` } });
        const formattedUserJoin = userJoin.data.map(item => {
          const [year, month] = item.month.split('-');
          return {
            ...item,
            month: `${year}년 ${month.padStart(2, '0')}월`
          };
        });

        // 탈퇴
        const userLeave = await axios.get(`${import.meta.env.VITE_API_URL}/admin/user/deleteUser`, 
          { headers: { Authorization: `Bearer ${localStorage.getItem('token')}` } });
          const formattedUserLeave = userLeave.data.map(item => {
            const [year, month] = item.month.split('-');
            return {
              ...item,
              month: `${year}년 ${month.padStart(2, '0')}월`
            };
          });

          const allMonths = [...new Set([
            ...formattedUserJoin.map(item => item.month),
            ...formattedUserLeave.map(item => item.month)
        ])].sort();

        const mergedData = allMonths.map(month => {
          const joinItem = formattedUserJoin.find(item => item.month === month) || { userCnt: 0 };
          const leaveItem = formattedUserLeave.find(item => item.month === month) || { userCnt: 0 };
      
          return {
              month, // X축 값
              userJoins: joinItem.userCnt, // 가입자 수
              userLeaves: leaveItem.userCnt // 탈퇴자 수
          };
        });
        setUserData(mergedData);

        // 방문자 수

        // 게시글
        const postList = await axios.get(`${import.meta.env.VITE_API_URL}/admin/posts/totalPost`, {
          headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
        });
        const formattedPost = postList.data
          .map(item => {
            const [year, month] = item.month.split('-');
            return {
              ...item,
              rawDate: `${year}-${month.padStart(2, '0')}`, // 정렬용
              month: `${year}년 ${month.padStart(2, '0')}월` // 표시용
            };
          })
          .sort((a, b) => new Date(a.rawDate) - new Date(b.rawDate)); // 날짜 정렬
  
          setPostMonthData(formattedPost.slice(0, 12));

        // 댓글
        const comments = await axios.get(`${import.meta.env.VITE_API_URL}/admin/comments/totalComments`,
          { headers: { Authorization: `Bearer ${localStorage.getItem('token')}` } 
          });
        const formattedComments = comments.data
        .map(item => {
          const [year, month] = item.month.split("-");
          return {
            ...item,
            rawData: `${year}-${month.padStart(2, "0")}`,
            month: `${year}년 ${month.padStart(2, "0")}월`
          };
        })
        .sort((a, b) => new Date(a.rawData) - new Date(b.rawData));
        setCommentsMonthData(formattedComments.slice(0, 12));

        // 게시글 신고
        const postReport = await axios.get(`${import.meta.env.VITE_API_URL}/admin/post-report/chart`, 
          { headers: { Authorization: `Bearer ${localStorage.getItem('token')}` } });
          const formattedPostReport = postReport.data.map(item => {
            const [year, month] = item.month.split('-');
            return {
              ...item,
              month: `${year}년 ${month.padStart(2, '0')}월`
            };
          });

        // 댓글 신고
        const commentReport = await axios.get(`${import.meta.env.VITE_API_URL}/admin/comment-report/chart`, 
          { headers: { Authorization: `Bearer ${localStorage.getItem('token')}` } });
          const formattedCommentReport = commentReport.data.map(item => {
            const [year, month] = item.month.split('-');
            return {
              ...item,
              month: `${year}년 ${month.padStart(2, '0')}월`
            };
          });

          const allReportMonths = [...new Set([
            ...formattedPostReport.map(item => item.month),
            ...formattedCommentReport.map(item => item.month)
        ])].sort();

        const mergedReport = allReportMonths.map(month => {
          const postItem = formattedUserJoin.find(item => item.month === month) || { reportCnt: 0 };
          const commentItem = formattedUserLeave.find(item => item.month === month) || { reportCnt: 0 };
      
          return {
              month, // X축 값
              postReports: postItem.reportCnt,
              commentReports: commentItem.reportCnt
          };
      });
        setReportMonthData(mergedReport);

    } catch(error) {
      if(error.response) {
        if(error.response.status === 401) {
          navigate("/error/401");
        } 
        else if(error.response.data === 403) {
          navigate("/error/403");
        }
        else if(error.response.data === 500) {
          navigate("/error/500");
        }
        else {
          console.log(error);
        }
      }

    }

  };

  useEffect(() => {
    fetchHome();
  }, []);

  return (
    <>
      <div className="dashboard-section">
        <h3>👤 총 회원 통계</h3>
        <ResponsiveContainer width="100%" height={250}>
          <LineChart data={userData}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="month" />
            <YAxis domain={["auto", "auto"]} />
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
          <BarChart data={postMonthData}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="month" />
            <YAxis />
            <Tooltip />
            <Bar dataKey="postCnt" fill="#66bb6a" name="게시글 수" />
          </BarChart>
        </ResponsiveContainer>
      </div>

      <div className="dashboard-section">
        <h3>💬 댓글 통계</h3>
        <ResponsiveContainer width="100%" height={250}>
          <BarChart data={commentsMonthData}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="month" />
            <YAxis />
            <Tooltip />
            <Bar dataKey="commentsCnt" fill="#ffb74d" name="댓글 수" />
          </BarChart>
        </ResponsiveContainer>
      </div>

      <div className="dashboard-section">
        <h3>🚨 신고 통계 (게시글/댓글)</h3>
        <ResponsiveContainer width="100%" height={250}>
          <BarChart data={reportMonthData}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="month" />
            <YAxis domain={["auto", "auto"]} />
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
