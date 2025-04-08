
import '../assets/css/postReport.css';

const dummyReports = [
  {
    id: 1,
    postTitle: '욕설이 포함된 게시글',
    reporter: 'user123',
    reason: '비속어 사용',
    date: '2025-04-06',
    status: '처리 대기',
  },
  {
    id: 2,
    postTitle: '광고성 게시글',
    reporter: 'goodUser99',
    reason: '상업적 광고',
    date: '2025-04-05',
    status: '처리 완료',
  },
  {
    id: 3,
    postTitle: '혐오 표현 포함 게시글',
    reporter: 'member77',
    reason: '차별적 발언',
    date: '2025-04-04',
    status: '처리 대기',
  },
];

const PostReport = () => {
  return (
    <div className="dashboard-section">
      <h3>📋 게시글 신고 목록</h3>
      <p>신고된 게시글을 확인하고 처리할 수 있는 영역입니다.</p>

      <table className="report-table">
        <thead>
          <tr>
            <th>#</th>
            <th>게시글 제목</th>
            <th>신고자</th>
            <th>신고 사유</th>
            <th>신고 날짜</th>
            <th>처리 상태</th>
            <th>조치</th>
          </tr>
        </thead>
        <tbody>
          {dummyReports.map((report) => (
            <tr key={report.id}>
              <td>{report.id}</td>
              <td>{report.postTitle}</td>
              <td>{report.reporter}</td>
              <td>{report.reason}</td>
              <td>{report.date}</td>
              <td>{report.status}</td>
              <td>
                {report.status === '처리 대기' ? (
                  <button className="action-button">처리 완료</button>
                ) : (
                  <span style={{ color: 'gray' }}>완료됨</span>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default PostReport;