import {
    LineChart, Line, XAxis, YAxis, Tooltip, CartesianGrid, ResponsiveContainer
  } from 'recharts';
  
  const sampleData = {
    year: [
      { name: '2023', count: 200 },
      { name: '2024', count: 220 },
      { name: '2025', count: 190 },
    ],
    month: [
      { name: '1월', count: 20 }, { name: '2월', count: 25 },
      { name: '3월', count: 18 }, { name: '4월', count: 22 },
      { name: '5월', count: 24 }, { name: '6월', count: 30 },
      { name: '7월', count: 28 }, { name: '8월', count: 26 },
      { name: '9월', count: 22 }, { name: '10월', count: 20 },
      { name: '11월', count: 19 }, { name: '12월', count: 18 },
    ],
    day: Array.from({ length: 31 }, (_, i) => ({
      name: `03-${String(i + 1).padStart(2, '0')}`,
      count: Math.floor(Math.random() * 10 + 1),
    })),
  };
  
  const UserLeave = () => {
    return (
      <div className="dashboard-section">
        <h3>📉 회원 탈퇴 통계</h3>
        <p>회원 탈퇴 수를 년도/월/일 기준으로 확인할 수 있습니다.</p>
  
        <h4 style={{ marginTop: '30px' }}>📅 연도별 회원 탈퇴</h4>
        <ResponsiveContainer width="100%" height={250}>
          <LineChart data={sampleData.year}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" />
            <YAxis />
            <Tooltip />
            <Line type="monotone" dataKey="count" stroke="#f44336" />
          </LineChart>
        </ResponsiveContainer>
  
        <h4 style={{ marginTop: '30px' }}>📆 월별 회원 탈퇴</h4>
        <ResponsiveContainer width="100%" height={250}>
          <LineChart data={sampleData.month}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" />
            <YAxis />
            <Tooltip />
            <Line type="monotone" dataKey="count" stroke="#ff9800" />
          </LineChart>
        </ResponsiveContainer>
  
        <h4 style={{ marginTop: '30px' }}>🗓️ 일별 회원 탈퇴 (2025년 3월)</h4>
        <ResponsiveContainer width="100%" height={250}>
          <LineChart data={sampleData.day}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" />
            <YAxis />
            <Tooltip />
            <Line type="monotone" dataKey="count" stroke="#795548" />
          </LineChart>
        </ResponsiveContainer>
      </div>
    );
  };
  
  export default UserLeave;
  