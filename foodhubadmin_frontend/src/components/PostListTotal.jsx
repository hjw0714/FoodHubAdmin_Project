import { useEffect, useState } from 'react';
import axios from "axios";
import {
  BarChart, Bar, XAxis, YAxis, Tooltip, CartesianGrid, ResponsiveContainer
} from 'recharts';
import { useNavigate } from "react-router-dom";
import dayjs from 'dayjs';



const PostListTotal = () => {

  const [postYearData, setPostYearData] = useState();
  const [postMonthData, setPostMonthData] = useState();
  const [postDayData, setPostDayData] = useState();
  const navigate = useNavigate();
  const [monthStartDate, setMonthStartDate] = useState(dayjs().subtract(1, 'year').format('YYYY-MM-DD')); // 날짜 설정용 dayjs 설치 
  const [dayStartDate, setDayStartDate] = useState(dayjs().subtract(1, 'month').format('YYYY-MM-DD'));

  const fetchPosts = async () => {
    try {
      const yearData = await axios.get(`${import.meta.env.VITE_API_URL}/posts/yearlyNewPost`,
        { headers: { Authorization: `Bearer ${localStorage.getItem('token')}` } });
      const formattedYear = yearData.data.map(item => ({
        ...item,
        year: `${item.year}년`
      }));
      setPostYearData(formattedYear);

      const monthData = await axios.get(`${import.meta.env.VITE_API_URL}/posts/monthlyNewPost`, {
        params: { startDate: monthStartDate },
        headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
      });
      const formattedMonth = monthData.data
        .map(item => {
          const [year, month] = item.month.split('-');
          return {
            ...item,
            rawDate: `${year}-${month.padStart(2, '0')}`, // 정렬용
            month: `${year}년 ${month.padStart(2, '0')}월` // 표시용
          };
        })
        .sort((a, b) => new Date(a.rawDate) - new Date(b.rawDate)); // 날짜 정렬

      setPostMonthData(formattedMonth);

      const dayData = await axios.get(`${import.meta.env.VITE_API_URL}/posts/dailyNewPost`, {
        params: { startDate: dayStartDate },
        headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
      });
      const formattedDay = dayData.data
        .map(item => {
          const parts = item.day.match(/(\d{4})-(\d{1,2})-(\d{1,2})$/);
          if (!parts) return item;

          const [, year, month, day] = parts;

          return {
            ...item,
            rawDate: `${year}-${month.padStart(2, '0')}-${day.padStart(2, '0')}`, // 정렬용
            day: `${year}년 ${month.padStart(2, '0')}월 ${day.padStart(2, '0')}일` // 표시용
          };
        })
        .sort((a, b) => new Date(a.rawDate) - new Date(b.rawDate)); // 날짜 정렬

      setPostDayData(formattedDay);

    } catch (error) {
      if (error.response) {
        if (error.response.status === 401) {
          navigate('/error/401');
        } else if (error.response.status === 500) {
          navigate('/error/500');
        }
      }
    }
  };

  useEffect(() => {
    fetchPosts();
  }, []);

  return (
    <div className="dashboard-section">
      <h3>🧱 총 게시글 수</h3>
      <p>년도별, 월별, 일별 게시글 수를 막대그래프로 확인할 수 있습니다.</p>

      <h4 style={{ marginTop: '30px' }}>📅 연도별 게시글 수</h4>
      <ResponsiveContainer width="100%" height={250}>
        <BarChart data={postYearData}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="year" />
          <YAxis allowDecimals={false} />
          <Tooltip />
          <Bar dataKey="postCnt" fill="#1976d2" />
        </BarChart>
      </ResponsiveContainer>

      <h4 style={{ marginTop: '30px' }}>📆 월별 게시글 수</h4>
      <label>조회 시작일: </label>
      <input type="date" value={monthStartDate} onChange={(e) => setMonthStartDate(e.target.value)} />
      <button onClick={fetchPosts}>조회</button>
      <ResponsiveContainer width="100%" height={250}>
        <BarChart data={postMonthData}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="month" />
          <YAxis allowDecimals={false} />
          <Tooltip />
          <Bar dataKey="postCnt" fill="#43a047" />
        </BarChart>
      </ResponsiveContainer>

      <h4 style={{ marginTop: '30px' }}>🗓️ 일별 게시글 수 </h4>
      <label>조회 시작일: </label>
      <input type="date" value={dayStartDate} onChange={(e) => setDayStartDate(e.target.value)} />
      <button onClick={fetchPosts}>조회</button>
      <ResponsiveContainer width="100%" height={250}>
        <BarChart data={postDayData}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="day" fontSize={10} />
          <YAxis allowDecimals={false} />
          <Tooltip />
          <Bar dataKey="postCnt" fill="#ffb300" />
        </BarChart>
      </ResponsiveContainer>
    </div>
  );
};

export default PostListTotal;
