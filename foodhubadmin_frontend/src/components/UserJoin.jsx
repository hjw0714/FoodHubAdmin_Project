import axios from 'axios';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import {
    LineChart, Line, XAxis, YAxis, Tooltip, CartesianGrid, ResponsiveContainer
  } from 'recharts';
  
  
  
  const UserJoin = () => {
    const [userYearData , setUserYearData] = useState([]);
    const [userMonthData , setUserMonthData] = useState([]);
    const [userDayData , setUserDayData] = useState([]);
    const navigate = useNavigate();
  
    const fetchUser = async() => {
      try {
        const yearRes = await axios.get(`${import.meta.env.VITE_API_URL}/user/yearlyNewUser`, 
          { headers: { Authorization: `Bearer ${localStorage.getItem('token')}` } });
          const formattedYear = yearRes.data.map(item => ({
            ...item,
            year: `${item.year}년`
          }));
          setUserYearData(formattedYear); 
  
        const monthRes = await axios.get(`${import.meta.env.VITE_API_URL}/user/monthlyNewUser`, 
          { headers: { Authorization: `Bearer ${localStorage.getItem('token')}` } });
          const formattedMonth = monthRes.data.map(item => {
            const [year, month] = item.month.split('-');
            return {
              ...item,
              month: `${year}년 ${month.padStart(2, '0')}월`
            };
          });
          setUserMonthData(formattedMonth);
  
        const dayRes = await axios.get(`${import.meta.env.VITE_API_URL}/user/dailyNewUser`, 
          { headers: { Authorization: `Bearer ${localStorage.getItem('token')}` } });
          const formattedDay = dayRes.data.map(item => {
            const parts = item.day.match(/(\d{4})-(\d{1,2})-(\d{1,2})$/); // 마지막 날짜만 추출
            if (!parts) return item;
    
            const [, year, month, day] = parts;
    
            return {
              ...item,
              day: `${year}년 ${month.padStart(2, '0')}월 ${day.padStart(2, '0')}일`
            };
          });
          setUserDayData(formattedDay);
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
      fetchUser();
    } , []);
  
    return (
      <div className="dashboard-section">
        <h3>👤 신규회원 통계</h3>
        <p>년도별, 월별, 일별 회원 수 변화를 한 눈에 확인할 수 있습니다.</p>
  
        <h4 style={{ marginTop: '30px' }}>📅 년도별 회원 가입</h4>
        <ResponsiveContainer width="100%" height={250}>
          <LineChart data={userYearData}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="year" />
            <YAxis allowDecimals={false} />
            <Tooltip />
            <Line type="monotone" dataKey="userCnt" stroke="#8884d8" />
          </LineChart>
        </ResponsiveContainer>
  
        <h4 style={{ marginTop: '30px' }}>📆 월별 회원 가입</h4>
        <ResponsiveContainer width="100%" height={250}>
          <LineChart data={userMonthData}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="month" />
            <YAxis allowDecimals={false} />
            <Tooltip />
            <Line type="monotone" dataKey="userCnt" stroke="#82ca9d" />
          </LineChart>
        </ResponsiveContainer>
  
        <h4 style={{ marginTop: '30px' }}>🗓️ 일별 회원 가입</h4>
        <ResponsiveContainer width="100%" height={250}>
          <LineChart data={userDayData}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="day" />
            <YAxis allowDecimals={false} />
            <Tooltip />
            <Line type="monotone" dataKey="userCnt" stroke="#ffc658" />
          </LineChart>
        </ResponsiveContainer>
      </div>
    );
  };
  
  export default UserJoin;
  