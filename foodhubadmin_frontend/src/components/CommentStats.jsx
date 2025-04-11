import { useEffect, useState } from 'react';
import axios from "axios";
import {
  BarChart, Bar, XAxis, YAxis, Tooltip, CartesianGrid, ResponsiveContainer
} from 'recharts';
import { useNavigate } from "react-router-dom";
import dayjs from 'dayjs';
  
  const CommentStats = () => {
    
    const [commentsYearData, setCommentsYearData] = useState();
    const [commentsMonthData, setCommentsMonthData] = useState();
    const[commentsDayData, setCommentsDayData] = useState();
    const navigate = useNavigate();
    const [monthStartDate, setMonthStartDate] = useState(dayjs().subtract(1, 'year').format('YYYY-MM-DD')); // 날짜 설정용 dayjs 설치 
    const [dayStartDate, setDayStartDate] = useState(dayjs().subtract(1, 'month').format('YYYY-MM-DD'));

    /*
    const [years, setYears] = useState([]);
    const [months, setMonths] = useState([]);

    const [searchYear, setSearchYear] = useState("");
    const [searchMonth, setSearchMonth] = useState("");
    */
    const fetchComments = async() => {
      try {
        
        // 연도별
        const yearData = await axios.get(`${import.meta.env.VITE_API_URL}/admin/comments/yearlyTotalComments`,
          { headers: { Authorization: `Bearer ${localStorage.getItem('token')}` } });
        const formattedYear = yearData.data.map(item => ({
          ...item,
          year: `${item.year}년`
        }));
        setCommentsYearData(formattedYear.slice(0, 5));
        
        // 월별
        const monthData = await axios.get(`${import.meta.env.VITE_API_URL}/admin/comments/monthlyTotalComments`,
          { params : {startDate: monthStartDate}, 
            headers: { Authorization: `Bearer ${localStorage.getItem('token')}` } 
          });
        const formattedMonth = monthData.data
        .map(item => {
          const [year, month] = item.month.split("-");
          return {
            ...item,
            rawData: `${year}-${month.padStart(2, "0")}`,
            month: `${year}년 ${month.padStart(2, "0")}월`
          };
        })
        .sort((a, b) => new Date(a.rawData) - new Date(b.rawData));
        setCommentsMonthData(formattedMonth.slice(0, 12));
        //setYears(yearData.data.map(item => item.year));

        // 일별
        const dayData = await axios.get(`${import.meta.env.VITE_API_URL}/admin/comments/dailyTotalComments`,
          {params: { startDate: dayStartDate },
          headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
        });
        const formattedDay = dayData.data.map(item => {
          const parts = item.day.match(/(\d{4})-(\d{1,2})-(\d{1,2})$/);
          if (!parts) return item;

          const [, year, month, day] = parts;

          return {
            ...item,
            rawDate: `${year}-${month.padStart(2, '0')}-${day.padStart(2, '0')}`, // 정렬용
            day: `${year}년 ${month.padStart(2, '0')}월 ${day.padStart(2, '0')}일` // 표시용
          };
        })
        .sort((a, b) => new Date(a.rawData) - new Date(b.rawData));
        setCommentsDayData(formattedDay.slice(0, 31));
        //setMonths(monthData.data.map(item => item.month));

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
       {/* 
        <label>조회할 연도 </label>
        <select onChange={(e) => setSearchYear(e.target.value)} value={searchYear}>
          {years.map((year) => (
            <option key={year} value={year}>
              {year}
            </option>
          ))}
        </select> {" "}
        <button>조회</button>*/}
      <label>조회 시작일: </label>
      <input type="date" value={monthStartDate} onChange={(e) => setMonthStartDate(e.target.value)} />
      <button onClick={fetchComments}>조회</button>
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
        {/*<label>조회할 월 </label>
        <select onChange={(e) => setSearchMonth(e.target.value)} value={searchMonth}>
          {months.map((month) => (
            <option key={month} value={month}>
              {month}
            </option>
          ))}
        </select> {" "}
        <button>조회</button>*/}
        <label>조회 시작일: </label>
      <input type="date" value={dayStartDate} onChange={(e) => setDayStartDate(e.target.value)} />
      <button onClick={fetchComments}>조회</button>
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
  