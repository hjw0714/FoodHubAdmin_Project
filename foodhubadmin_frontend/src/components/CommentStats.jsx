import {
    BarChart, Bar, XAxis, YAxis, Tooltip, CartesianGrid, ResponsiveContainer
  } from 'recharts';
  
  // 더미 데이터
  const commentData = {
    year: [
      { name: '2023', count: 2300 },
      { name: '2024', count: 2600 },
      { name: '2025', count: 3000 },
    ],
    month: [
      { name: '1월', count: 200 }, { name: '2월', count: 220 }, { name: '3월', count: 250 },
      { name: '4월', count: 240 }, { name: '5월', count: 230 }, { name: '6월', count: 260 },
      { name: '7월', count: 280 }, { name: '8월', count: 300 }, { name: '9월', count: 310 },
      { name: '10월', count: 290 }, { name: '11월', count: 270 }, { name: '12월', count: 250 },
    ],
    day: Array.from({ length: 31 }, (_, i) => ({
      name: `03-${String(i + 1).padStart(2, '0')}`,
      count: Math.floor(Math.random() * 50 + 10) // 10~59개
    }))
  };
  
  const CommentStats = () => {
    return (
      <div className="dashboard-section">
        <h3>💬 댓글 작성 수 통계</h3>
        <p>댓글 작성량을 연도별, 월별, 일별로 확인할 수 있습니다.</p>
  
        <h4>📅 연도별</h4>
        <ResponsiveContainer width="100%" height={220}>
          <BarChart data={commentData.year}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" />
            <YAxis />
            <Tooltip />
            <Bar dataKey="count" fill="#42a5f5" />
          </BarChart>
        </ResponsiveContainer>
  
        <h4 style={{ marginTop: '30px' }}>📆 월별</h4>
        <ResponsiveContainer width="100%" height={220}>
          <BarChart data={commentData.month}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" />
            <YAxis />
            <Tooltip />
            <Bar dataKey="count" fill="#66bb6a" />
          </BarChart>
        </ResponsiveContainer>
  
        <h4 style={{ marginTop: '30px' }}>🗓️ 일별 (2025년 3월)</h4>
        <ResponsiveContainer width="100%" height={220}>
          <BarChart data={commentData.day}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" interval={2} />
            <YAxis />
            <Tooltip />
            <Bar dataKey="count" fill="#ffa726" />
          </BarChart>
        </ResponsiveContainer>
      </div>
    );
  };
  
  export default CommentStats;
  