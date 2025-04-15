import { useEffect, useState } from 'react';
import {
    BarChart, Bar, XAxis, YAxis, Tooltip, CartesianGrid, ResponsiveContainer
  } from 'recharts';
import '../assets/css/postReport.css'; // 스타일 공통 재사용
import { useNavigate } from "react-router-dom";
import axios from 'axios';
import dayjs from 'dayjs';

const CategoryPostList = () => {
    const [postYearData, setPostYearData] = useState();
    const [postMonthData, setPostMonthData] = useState();
    const [postDayData, setPostDayData] = useState();
    const navigate = useNavigate();
    const [monthStartDate, setMonthStartDate] = useState(dayjs().subtract(1, 'year').format('YYYY-MM')); // 날짜 설정용 dayjs 설치 
    const [dayStartDate, setDayStartDate] = useState(dayjs().subtract(1, 'month').format('YYYY-MM-DD'));


    const [selectedCategory, setSelectedCategory] = useState('5'); // 기본 카테고리 ID
    const categoryData = {
        5 : {name: '공지사항'},
        6 : {name: '외식업정보'},
        7 : {name: '자유'},
        8 : {name: '알바공고'},
        9 : {name: '질문'},
        10 : {name: '중고장비거래'},
        11 : {name: '매장홍보'},
        12 : {name: '협력업체'},
    };

  const fetchPosts = async () => {
    try {
      const yearData = await axios.get(`${import.meta.env.VITE_API_URL}/admin/posts/yearlyCategoryPost`,
        {    params: { categoryId: selectedCategory }, 
            headers: { Authorization: `Bearer ${localStorage.getItem('token')}` } });
      const formattedYear = yearData.data.map(item => ({
        ...item,
        year: `${item.year}년`
      }));
      setPostYearData(formattedYear.slice(0, 5));

      const monthData = await axios.get(`${import.meta.env.VITE_API_URL}/admin/posts/monthlyCategoryPost`, {
        params: { 
            startDate: monthStartDate , 
            categoryId: selectedCategory
        },
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

      setPostMonthData(formattedMonth.slice(0, 12));

      const dayData = await axios.get(`${import.meta.env.VITE_API_URL}/admin/posts/dailyCategoryPost`, {
        params: { 
            startDate: dayStartDate ,
            categoryId: selectedCategory
        },
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

      setPostDayData(formattedDay.slice(0, 31));
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
      }, [selectedCategory]);

    return (
        <div className="dashboard-section">
            <h3>📊 카테고리별 게시글 수</h3>
            <p>년도, 월, 일 기준으로 카테고리별 게시글 수 통계를 확인할 수 있습니다.</p>

            <select value={selectedCategory} onChange={(e) => setSelectedCategory(e.target.value)} style={{ margin: '20px 0' }}>
                {Object.entries(categoryData).map(([id, cat]) => (
                    <option key={id} value={id}>
                        {cat.name}
                    </option>
                ))}
            </select>

            <h4>📅 연도별</h4>
            <ResponsiveContainer width="100%" height={220}>
                <BarChart data={postYearData}>
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="year" interval={0} />
                    <YAxis allowDecimals={false} />
                    <Tooltip />
                    <Bar dataKey="postCnt" fill="#8884d8" />
                </BarChart>
            </ResponsiveContainer>

            <h4>📆 월별</h4>
            <label>조회 시작일: </label>
            <input type="month" value={monthStartDate} onChange={(e) => setMonthStartDate(e.target.value)} /> {" "}
            <button onClick={fetchPosts}>조회</button>
            <ResponsiveContainer width="100%" height={220}>
                <BarChart data={postMonthData}>
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="month" interval={0} />
                    <YAxis allowDecimals={false} />
                    <Tooltip />
                    <Bar dataKey="postCnt" fill="#82ca9d" />
                </BarChart>
            </ResponsiveContainer>

            <h4>🗓️ 일별</h4>
            <label>조회 시작일: </label>
            <input type="date" value={dayStartDate} onChange={(e) => setDayStartDate(e.target.value)} /> {" "}
            <button onClick={fetchPosts}>조회</button>
            <ResponsiveContainer width="100%" height={220}>
                <BarChart data={postDayData}>
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="day" interval={0} />
                    <YAxis allowDecimals={false} />
                    <Tooltip />
                    <Bar dataKey="postCnt" fill="#ffc658" />
                </BarChart>
            </ResponsiveContainer>
        </div>
    );
};

export default CategoryPostList;
