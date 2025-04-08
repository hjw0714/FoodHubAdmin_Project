import {
    BarChart, Bar, XAxis, YAxis, Tooltip, CartesianGrid, ResponsiveContainer
  } from 'recharts';

const commentReportStats = {
    year: [
      { name: '2023', count: 90 },
      { name: '2024', count: 110 },
      { name: '2025', count: 95 },
    ],
    month: [
      { name: '1월', count: 10 }, { name: '2월', count: 12 }, { name: '3월', count: 15 },
      { name: '4월', count: 14 }, { name: '5월', count: 13 }, { name: '6월', count: 16 },
      { name: '7월', count: 17 }, { name: '8월', count: 18 }, { name: '9월', count: 19 },
      { name: '10월', count: 14 }, { name: '11월', count: 12 }, { name: '12월', count: 11 },
    ],
    day: Array.from({ length: 31 }, (_, i) => ({
      name: `03-${String(i + 1).padStart(2, '0')}`,
      count: Math.floor(Math.random() * 3 + 1),
    })),
  };
  
  const CommentReportStats = () => {
    return (
      <div className="dashboard-section">
        <h3>🗯️ 댓글 신고 수 통계</h3>
        <p>댓글에 대한 신고 건수를 연도, 월, 일 기준으로 시각화합니다.</p>
  
        <h4>📅 연도별</h4>
        <ResponsiveContainer width="100%" height={220}>
          <BarChart data={commentReportStats.year}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" />
            <YAxis />
            <Tooltip />
            <Bar dataKey="count" fill="#42a5f5" />
          </BarChart>
        </ResponsiveContainer>
  
        <h4 style={{ marginTop: '30px' }}>📆 월별</h4>
        <ResponsiveContainer width="100%" height={220}>
          <BarChart data={commentReportStats.month}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" />
            <YAxis />
            <Tooltip />
            <Bar dataKey="count" fill="#26c6da" />
          </BarChart>
        </ResponsiveContainer>
  
        <h4 style={{ marginTop: '30px' }}>🗓️ 일별 (2025년 3월)</h4>
        <ResponsiveContainer width="100%" height={220}>
          <BarChart data={commentReportStats.day}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" interval={2} />
            <YAxis />
            <Tooltip />
            <Bar dataKey="count" fill="#66bb6a" />
          </BarChart>
        </ResponsiveContainer>
      </div>
    );
  };
  
  export default CommentReportStats;
  