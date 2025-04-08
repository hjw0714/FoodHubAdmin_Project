import {
    BarChart, Bar, XAxis, YAxis, Tooltip, CartesianGrid, ResponsiveContainer
  } from 'recharts';
  import '../assets/css/postReport.css'; // 공통 스타일 재사용
  
  // 더미 데이터
  const visitorData = {
    year: [
      { name: '2023', count: 12000 },
      { name: '2024', count: 15000 },
      { name: '2025', count: 18000 },
    ],
    month: [
      { name: '1월', count: 1200 }, { name: '2월', count: 1400 }, { name: '3월', count: 1350 },
      { name: '4월', count: 1600 }, { name: '5월', count: 1700 }, { name: '6월', count: 1650 },
      { name: '7월', count: 1800 }, { name: '8월', count: 1900 }, { name: '9월', count: 1850 },
      { name: '10월', count: 1750 }, { name: '11월', count: 1600 }, { name: '12월', count: 1500 },
    ],
    day: Array.from({ length: 31 }, (_, i) => ({
      name: `03-${String(i + 1).padStart(2, '0')}`,
      count: Math.floor(Math.random() * 200 + 50), // 50~249명
    }))
  };
  
  const VisitorStats = () => {
    return (
      <div className="dashboard-section">
        <h3>👣 방문자 수 통계</h3>
        <p>웹사이트 방문자 수를 연도별, 월별, 일별로 확인할 수 있습니다.</p>
  
        <h4>📅 연도별</h4>
        <ResponsiveContainer width="100%" height={220}>
          <BarChart data={visitorData.year}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" />
            <YAxis allowDecimals={false} />
            <Tooltip />
            <Bar dataKey="count" fill="#42a5f5" />
          </BarChart>
        </ResponsiveContainer>
  
        <h4 style={{ marginTop: '30px' }}>📆 월별</h4>
        <ResponsiveContainer width="100%" height={220}>
          <BarChart data={visitorData.month}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" />
            <YAxis allowDecimals={false} />
            <Tooltip />
            <Bar dataKey="count" fill="#66bb6a" />
          </BarChart>
        </ResponsiveContainer>
  
        <h4 style={{ marginTop: '30px' }}>🗓️ 일별 (2025년 3월)</h4>
        <ResponsiveContainer width="100%" height={220}>
          <BarChart data={visitorData.day}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" interval={2} />
            <YAxis allowDecimals={false} />
            <Tooltip />
            <Bar dataKey="count" fill="#ffa726" />
          </BarChart>
        </ResponsiveContainer>
      </div>
    );
  };
  
  export default VisitorStats;
  