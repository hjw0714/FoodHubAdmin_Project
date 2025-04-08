import {
    BarChart, Bar, XAxis, YAxis, Tooltip, CartesianGrid, ResponsiveContainer
  } from 'recharts';
  
  const sampleData = {
    year: [
      { name: '2023', count: 3200 },
      { name: '2024', count: 4100 },
      { name: '2025', count: 5000 },
    ],
    month: [
      { name: '1월', count: 300 }, { name: '2월', count: 350 },
      { name: '3월', count: 400 }, { name: '4월', count: 420 },
      { name: '5월', count: 380 }, { name: '6월', count: 390 },
      { name: '7월', count: 420 }, { name: '8월', count: 410 },
      { name: '9월', count: 430 }, { name: '10월', count: 450 },
      { name: '11월', count: 400 }, { name: '12월', count: 390 },
    ],
    day: Array.from({ length: 31 }, (_, i) => ({
      name: `03-${String(i + 1).padStart(2, '0')}`,
      count: Math.floor(Math.random() * 40 + 10), // 10~49개
    })),
  };
  
  const PostListTotal = () => {
    return (
      <div className="dashboard-section">
        <h3>🧱 총 게시글 수</h3>
        <p>년도별, 월별, 일별 게시글 수를 막대그래프로 확인할 수 있습니다.</p>
  
        <h4 style={{ marginTop: '30px' }}>📅 연도별 게시글 수</h4>
        <ResponsiveContainer width="100%" height={250}>
          <BarChart data={sampleData.year}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" />
            <YAxis allowDecimals={false} />
            <Tooltip />
            <Bar dataKey="count" fill="#1976d2" />
          </BarChart>
        </ResponsiveContainer>
  
        <h4 style={{ marginTop: '30px' }}>📆 월별 게시글 수</h4>
        <ResponsiveContainer width="100%" height={250}>
          <BarChart data={sampleData.month}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" />
            <YAxis allowDecimals={false} />
            <Tooltip />
            <Bar dataKey="count" fill="#43a047" />
          </BarChart>
        </ResponsiveContainer>
  
        <h4 style={{ marginTop: '30px' }}>🗓️ 일별 게시글 수 (2025년 3월)</h4>
        <ResponsiveContainer width="100%" height={250}>
          <BarChart data={sampleData.day}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" fontSize={10} />
            <YAxis allowDecimals={false} />
            <Tooltip />
            <Bar dataKey="count" fill="#ffb300" />
          </BarChart>
        </ResponsiveContainer>
      </div>
    );
  };
  
  export default PostListTotal;
  