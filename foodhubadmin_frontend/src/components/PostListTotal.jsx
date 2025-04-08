import { useEffect, useState } from 'react';
import axios from "axios";
import {
  BarChart, Bar, XAxis, YAxis, Tooltip, CartesianGrid, ResponsiveContainer
} from 'recharts';



const PostListTotal = () => {

  const [postYearData, setPostYearData] = useState();
  const [postMonthData, setPostMonthData] = useState();
  const [postDayData, setPostDayData] = useState();

  const fetchPosts = async () => {
    try {
      const yearRes = await axios.get(`${import.meta.env.VITE_API_URL}/posts/yearlyNewPost`);
      const formattedYear = yearRes.data.map(item => ({
        ...item,
        year: `${item.year}년`
      }));
      setPostYearData(formattedYear);

      const monthRes = await axios.get(`${import.meta.env.VITE_API_URL}/posts/monthlyNewPost`);
      const formattedMonth = monthRes.data.map(item => {
        const [year, month] = item.month.split('-');
        return {
          ...item,
          month: `${year}년 ${month.padStart(2, '0')}월`
        };
      });
      setPostMonthData(formattedMonth);

      const dayRes = await axios.get(`${import.meta.env.VITE_API_URL}/posts/dailyNewPost`);
      const formattedDay = dayRes.data.map(item => {
        const parts = item.day.match(/(\d{4})-(\d{1,2})-(\d{1,2})$/); // 마지막 날짜만 추출
        if (!parts) return item;

        const [, year, month, day] = parts;

        return {
          ...item,
          day: `${year}년 ${month.padStart(2, '0')}월 ${day.padStart(2, '0')}일`
        };
      });
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
      <ResponsiveContainer width="100%" height={250}>
        <BarChart data={postMonthData}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="month" />
          <YAxis allowDecimals={false} />
          <Tooltip />
          <Bar dataKey="postCnt" fill="#43a047" />
        </BarChart>
      </ResponsiveContainer>

      <h4 style={{ marginTop: '30px' }}>🗓️ 일별 게시글 수 (2025년 3월)</h4>
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
