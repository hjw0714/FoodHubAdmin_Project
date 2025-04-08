import {
    LineChart, Line, XAxis, YAxis, Tooltip, CartesianGrid, ResponsiveContainer
  } from 'recharts';
  
  const sampleData = {
    year: [
      { name: '2023', count: 800 },
      { name: '2024', count: 950 },
      { name: '2025', count: 1100 },
    ],
    month: [
      { name: '1월', count: 100 }, { name: '2월', count: 120 },
      { name: '3월', count: 130 }, { name: '4월', count: 140 },
      { name: '5월', count: 150 }, { name: '6월', count: 160 },
      { name: '7월', count: 170 }, { name: '8월', count: 180 },
      { name: '9월', count: 190 }, { name: '10월', count: 200 },
      { name: '11월', count: 180 }, { name: '12월', count: 160 },
    ],
    day: Array.from({ length: 31 }, (_, i) => ({
      name: `03-${String(i + 1).padStart(2, '0')}`,
      count: Math.floor(Math.random() * 20 + 5),
    })),
  };
  
  const UserJoin = () => {
    return (
      <div className="dashboard-section">
        <h3>📈 회원 가입 통계</h3>
        <p>회원 가입 수를 년도/월/일 기준으로 확인할 수 있습니다.</p>
  
        <h4 style={{ marginTop: '30px' }}>📅 연도별 회원 가입</h4>
        <ResponsiveContainer width="100%" height={250}>
          <LineChart data={sampleData.year}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" />
            <YAxis />
            <Tooltip />
            <Line type="monotone" dataKey="count" stroke="#4caf50" />
          </LineChart>
        </ResponsiveContainer>
  
        <h4 style={{ marginTop: '30px' }}>📆 월별 회원 가입</h4>
        <ResponsiveContainer width="100%" height={250}>
          <LineChart data={sampleData.month}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" />
            <YAxis />
            <Tooltip />
            <Line type="monotone" dataKey="count" stroke="#2196f3" />
          </LineChart>
        </ResponsiveContainer>
  
        <h4 style={{ marginTop: '30px' }}>🗓️ 일별 회원 가입 (2025년 3월)</h4>
        <ResponsiveContainer width="100%" height={250}>
          <LineChart data={sampleData.day}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" />
            <YAxis />
            <Tooltip />
            <Line type="monotone" dataKey="count" stroke="#9c27b0" />
          </LineChart>
        </ResponsiveContainer>
      </div>
    );
  };
  
  export default UserJoin;
  