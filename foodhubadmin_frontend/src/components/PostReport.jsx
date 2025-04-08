
import '../assets/css/postReport.css';

const dummyReports = [
  { id: 1, postTitle: '욕설이 포함된 게시글', reporter: 'user123', reason: '비속어 사용', date: '2025-04-06', status: '처리 대기' },
  { id: 2, postTitle: '광고성 게시글', reporter: 'goodUser99', reason: '상업적 광고', date: '2025-04-05', status: '처리 완료' },
  { id: 3, postTitle: '혐오 표현 포함 게시글', reporter: 'member77', reason: '차별적 발언', date: '2025-04-04', status: '처리 대기' },
  { id: 4, postTitle: '중복된 내용의 게시글', reporter: 'skyblue1', reason: '도배성 글', date: '2025-04-03', status: '처리 대기' },
  { id: 5, postTitle: '무단 도용 이미지 포함', reporter: 'photoFan', reason: '저작권 위반', date: '2025-04-02', status: '처리 완료' },
  { id: 6, postTitle: '비방성 내용 포함', reporter: 'criticMan', reason: '명예훼손 우려', date: '2025-04-01', status: '처리 대기' },
  { id: 7, postTitle: '욕설 및 혐오', reporter: 'mod123', reason: '욕설 포함', date: '2025-03-31', status: '처리 완료' },
  { id: 8, postTitle: '스팸 게시물', reporter: 'spamBot', reason: '스팸 의심', date: '2025-03-30', status: '처리 대기' },
  { id: 9, postTitle: '정치적 선동', reporter: 'newsHunter', reason: '정치 관련 문제', date: '2025-03-29', status: '처리 완료' },
  { id: 10, postTitle: '불쾌한 표현 사용', reporter: 'fairUser', reason: '기분 나쁜 표현', date: '2025-03-28', status: '처리 대기' },
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