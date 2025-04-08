import {
    LineChart, Line, XAxis, YAxis, Tooltip, CartesianGrid, ResponsiveContainer
  } from 'recharts';
  
  const sampleData = {
    year: [
      { name: '2023', count: 1500 },
      { name: '2024', count: 1400 },
      { name: '2025', count: 2400 },
    ],
    month: [
      { name: '1월', count: 100 }, { name: '2월', count: 120 },
      { name: '3월', count: 140 }, { name: '4월', count: 160 },
      { name: '5월', count: 180 }, { name: '6월', count: 200 },
      { name: '7월', count: 190 }, { name: '8월', count: 170 },
      { name: '9월', count: 160 }, { name: '10월', count: 150 },
      { name: '11월', count: 140 }, { name: '12월', count: 130 },
    ],
    day: Array.from({ length: 31 }, (_, i) => {
      const day = i + 1;
      return {
        name: `03-${day < 10 ? `0${day}` : day}`,
        count: Math.floor(Math.random() * 30 + 10) // 10~39 사이 난수
      };
    }),
  };
  
  const UserStats = () => {
    return (
      <div className="dashboard-section">
        <h3>👤 총 회원 수 통계</h3>
        <p>년도별, 월별, 일별 회원 수 변화를 한 눈에 확인할 수 있습니다.</p>
  
        <h4 style={{ marginTop: '30px' }}>📅 연도별 회원 수</h4>
        <ResponsiveContainer width="100%" height={250}>
          <LineChart data={sampleData.year}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" />
            <YAxis allowDecimals={false} />
            <Tooltip />
            <Line type="monotone" dataKey="count" stroke="#8884d8" />
          </LineChart>
        </ResponsiveContainer>
  
        <h4 style={{ marginTop: '30px' }}>📆 월별 회원 수</h4>
        <ResponsiveContainer width="100%" height={250}>
          <LineChart data={sampleData.month}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" />
            <YAxis allowDecimals={false} />
            <Tooltip />
            <Line type="monotone" dataKey="count" stroke="#82ca9d" />
          </LineChart>
        </ResponsiveContainer>
  
        <h4 style={{ marginTop: '30px' }}>🗓️ 일별 회원 수 (2025년 3월)</h4>
        <ResponsiveContainer width="100%" height={250}>
          <LineChart data={sampleData.day}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" />
            <YAxis allowDecimals={false} />
            <Tooltip />
            <Line type="monotone" dataKey="count" stroke="#ffc658" />
          </LineChart>
        </ResponsiveContainer>
      </div>
    );
  };
  
  export default UserStats;
  