import {
    BarChart, Bar, XAxis, YAxis, Tooltip, CartesianGrid, ResponsiveContainer
  } from 'recharts';
  import '../../../assets/css/postReport.css'; // 공통 스타일 재사용
import dayjs from 'dayjs';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
  
  const VisitorStats = () => {

    const [visitorYearData, setVisitorYearData] = useState();
    const [visitorMonthData, setVisitorMonthData] = useState();
    const [visitorDayData, setVisitorDayData] = useState();
    const navigate = useNavigate();

    const [monthStartDate, setMonthStartDate] = useState(dayjs().subtract(1, 'year').format('YYYY-MM'));
    const [dayStartDate, setDayStartDate] = useState(dayjs().subtract(1, 'month').format('YYYY-MM-DD'));

    const fetchVisitor = async() => {
      try {
        // 연도별
        const yearData = await axios.get(`${import.meta.env.VITE_API_URL}/admin/visitor/yearlyVisitorLog`,
        { headers: { Authorization: `Bearer ${localStorage.getItem('token')}` } });
        const formattedYear = yearData.data.map(item => ({
          ...item,
          year: `${item.year}년`
        }));
        setVisitorYearData(formattedYear.slice(0, 5));

        // 월별
        const monthData = await axios.get(`${import.meta.env.VITE_API_URL}/admin/visitor/monthlyVisitorLog`, {
        params: { startDate: monthStartDate },
        headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
      });
      const formattedMonth = monthData.data.map(item => {
        const [year, month] = item.month.split("-");
        return {
          ...item,
          rawDate: `${year}-${month.padStart(2, "0")}`,
          month: `${year}년 ${month.padStart(2, '0')}월`
        };
      })
      .sort((a, b) => new Date(a.rawDate) - new Date(b.rawDate));
      setVisitorMonthData(formattedMonth.slice(0, 12));

      // 일별
      const dayData = await axios.get(`${import.meta.env.VITE_API_URL}/admin/visitor/dailyVisitorLog`, {
        params: { startDate: dayStartDate },
        headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
      });
      const formattedDay = dayData.data.map(item => {
        const parts = item.day.match(/(\d{4})-(\d{1,2})-(\d{1,2})$/);
        if(!parts) return item;

        const [, year, month, day] = parts;

        return {
          ...item,
          rawDate: `${year}-${month.padStart(2, '0')}-${day.padStart(2, '0')}`,
          day: `${year}년 ${month.padStart(2, '0')}월 ${day.padStart(2, '0')}일`
        };

      })
      .sort((a, b) => new Date(a.rawDate) - new Date(b.rawDate));
      setVisitorDayData(formattedDay.slice(0, 31))


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
        }

      }

    };

    useEffect(() => {
      fetchVisitor();
    }, []);
  

    return (
      <div className="dashboard-section">
        <h3>👣 방문자 수 통계</h3>
        <p>웹사이트 방문자 수를 연도별, 월별, 일별로 확인할 수 있습니다.</p>
  
        <h4>📅 연도별 방문자 수</h4>
        <ResponsiveContainer width="100%" height={220}>
          <BarChart data={visitorYearData}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="year" />
            <YAxis allowDecimals={false} />
            <Tooltip />
            <Bar dataKey="visitorCnt" fill="#42a5f5" />
          </BarChart>
        </ResponsiveContainer>
  
        <h4 style={{ marginTop: '30px' }}>📆 월별 방문자 수</h4>
        <label>조회 시작일: </label>
        <input type="month" value={monthStartDate} onChange={(e) => setMonthStartDate(e.target.value)} /> {" "}
        <button onClick={fetchVisitor}>조회</button>
        <ResponsiveContainer width="100%" height={220}>
          <BarChart data={visitorMonthData}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="month" interval={2} />
            <YAxis allowDecimals={false} />
            <Tooltip />
            <Bar dataKey="visitorCnt" fill="#66bb6a" />
          </BarChart>
        </ResponsiveContainer>
  
        <h4 style={{ marginTop: '30px' }}>🗓️ 일별 방문자 수</h4>
        <label>조회 시작일: </label>
        <input type="date" value={dayStartDate} onChange={(e) => setDayStartDate(e.target.value)} /> {" "}
        <button onClick={fetchVisitor}>조회</button>
        <ResponsiveContainer width="100%" height={220}>
          <BarChart data={visitorDayData}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="day" interval={4} />
            <YAxis allowDecimals={false} />
            <Tooltip />
            <Bar dataKey="visitorCnt" fill="#ffa726" />
          </BarChart>
        </ResponsiveContainer>
      </div>
    );
  };
  
  export default VisitorStats;
  