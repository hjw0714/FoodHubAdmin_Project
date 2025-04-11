import axios from 'axios';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import {
    BarChart, Bar, XAxis, YAxis, Tooltip, CartesianGrid, ResponsiveContainer
  } from 'recharts';
  
  const CommentStats = () => {
    const navigate = useNavigate();
    const [commentsYearData, setCommentsYearData] = useState();
    const [commentsMonthData, setCommentsMonthData] = useState();
    const[commentsDayData, setCommentsDayData] = useState();

    const fetchComments = async() => {
      try {
        
        // 연도별
        const yearData = await axios.get(`${import.meta.env.VITE_API_URL}/admin/comments/yearlyNewComments`,
          { headers: { Authorization: `Bearer ${localStorage.getItem('token')}` } });
        const formattedYear = yearData.data.map(item => ({
          ...item,
          year: `${item.year}년`
        }));
        setCommentsYearData(formattedYear);
        
        // 월별
        const monthData = await axios.get(`${import.meta.env.VITE_API_URL}/admin/comments/monthlyNewComments`,
          { headers: { Authorization: `Bearer ${localStorage.getItem('token')}` } });
        const formattedMonth = monthData.data.map(item => {
          const [year, month] = item.month.split("-");
          return {
            ...item,
            rawData: `${year}-${month.padStart(2, "0")}`,
            month: `${year}년 ${month.padStart(2, "0")}월`
          };
        })
        .sort((a, b) => new Date(a.rawData) - new Date(b.rawData));
        setCommentsMonthData(formattedMonth);

        // 일별
        const dayData = await axios.get(`${import.meta.env.VITE_API_URL}/admin/comments/dailyNewComments`,
          { headers: { Authorization: `Bearer ${localStorage.getItem('token')}` } });
        const formattedDay = dayData.data.map(item => {
          const temp = item.day.match(/(\d{4})-(\d{1,2})-(\d{1,2})$/);
          if(!temp) {
            return item;
          }
          const [, year, month, day] = temp;

          return {
            ...item,
            rawData: `${year}-${month.padStart(2, "0")}-${day.padStart(2, "0")}`,
            day: `${year}년 ${month.padStart(2, "0")}월 ${day.padStart(2, "0")}일`
          };

        })
        .sort((a, b) => new Date(a.rawData) - new Date(b.rawData));
        setCommentsDayData(formattedDay);
        

      } catch(error) {
        if(error.response) {
          if(error.response.status === 401) {
            navigate("/error/401");
          }
          else if(error.response.status === 403) {
            navigate("/error/403");
          }
          else if(error.response.status === 500) {
            navigate("/error/500");
          }
          else {
            console.log(error);
          }
        }
      }

    };

    useEffect(() => {
      fetchComments();
    }, []);

    return (
      <div className="dashboard-section">
        <h3>💬 댓글 작성 수 통계</h3>
        <p>댓글 작성량을 연도별, 월별, 일별로 확인할 수 있습니다.</p>
  
        <h4>📅 연도별</h4>
        <ResponsiveContainer width="100%" height={220}>
          <BarChart data={commentsYearData}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="year" />
            <YAxis />
            <Tooltip />
            <Bar dataKey="commentsCnt" fill="#42a5f5" />
          </BarChart>
        </ResponsiveContainer>
  
        <h4 style={{ marginTop: '30px' }}>📆 월별</h4>
        <ResponsiveContainer width="100%" height={220}>
          <BarChart data={commentsMonthData}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="month" />
            <YAxis />
            <Tooltip />
            <Bar dataKey="commentsCnt" fill="#66bb6a" />
          </BarChart>
        </ResponsiveContainer>
  
        <h4 style={{ marginTop: '30px' }}>🗓️ 일별</h4>
        <ResponsiveContainer width="100%" height={220}>
          <BarChart data={commentsDayData}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="day" interval={2} />
            <YAxis />
            <Tooltip />
            <Bar dataKey="commentsCnt" fill="#ffa726" />
          </BarChart>
        </ResponsiveContainer>
      </div>
    );
  };
  
  export default CommentStats;
  