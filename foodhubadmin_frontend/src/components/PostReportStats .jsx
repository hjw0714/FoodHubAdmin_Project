import {
    BarChart, Bar, XAxis, YAxis, Tooltip, CartesianGrid, ResponsiveContainer
  } from 'recharts';
  import '../assets/css/postReport.css';
  
  const postReportStats = {
    year: [
      { name: '2023', count: 150 },
      { name: '2024', count: 180 },
      { name: '2025', count: 120 },
    ],
    month: [
      { name: '1월', count: 20 }, { name: '2월', count: 25 }, { name: '3월', count: 15 },
      { name: '4월', count: 18 }, { name: '5월', count: 20 }, { name: '6월', count: 22 },
      { name: '7월', count: 25 }, { name: '8월', count: 30 }, { name: '9월', count: 28 },
      { name: '10월', count: 23 }, { name: '11월', count: 19 }, { name: '12월', count: 17 },
    ],
    day: Array.from({ length: 31 }, (_, i) => ({
      name: `03-${String(i + 1).padStart(2, '0')}`,
      count: Math.floor(Math.random() * 4 + 1),
    })),
  };
  
  const PostReportStats = () => {
    return (
      <div className="dashboard-section">
        <h3>📝 게시글 신고 수 통계</h3>
        <p>게시글 신고 발생 건수를 연도, 월, 일 단위로 확인할 수 있습니다.</p>
  
        <h4>📅 연도별</h4>
        <ResponsiveContainer width="100%" height={220}>
          <BarChart data={postReportStats.year}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" />
            <YAxis />
            <Tooltip />
            <Bar dataKey="count" fill="#ef5350" />
          </BarChart>
        </ResponsiveContainer>
  
        <h4 style={{ marginTop: '30px' }}>📆 월별</h4>
        <ResponsiveContainer width="100%" height={220}>
          <BarChart data={postReportStats.month}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" />
            <YAxis />
            <Tooltip />
            <Bar dataKey="count" fill="#ec407a" />
          </BarChart>
        </ResponsiveContainer>
  
        <h4 style={{ marginTop: '30px' }}>🗓️ 일별 (2025년 3월)</h4>
        <ResponsiveContainer width="100%" height={220}>
          <BarChart data={postReportStats.day}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" interval={2} />
            <YAxis />
            <Tooltip />
            <Bar dataKey="count" fill="#ab47bc" />
          </BarChart>
        </ResponsiveContainer>
      </div>
    );
  };
  
  export default PostReportStats;
  