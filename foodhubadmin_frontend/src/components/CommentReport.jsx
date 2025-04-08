import '../assets/css/postReport.css';

const dummyCommentReports = [
    { id: 1, comment: '이게 말이야 방구야?', reporter: 'cleanUser', reason: '욕설 포함', date: '2025-04-06', status: '처리 대기' },
    { id: 2, comment: '광고 댓글입니다. 링크 포함', reporter: 'user88', reason: '스팸 광고', date: '2025-04-05', status: '처리 완료' },
    { id: 3, comment: '멍청하네', reporter: 'truthSeeker', reason: '모욕적 언사', date: '2025-04-04', status: '처리 대기' },
    { id: 4, comment: '욕설이 심합니다', reporter: 'adminTest', reason: '비속어 다수 포함', date: '2025-04-03', status: '처리 완료' },
    { id: 5, comment: 'ㅋㅋㅋㅋㅋㅋㅋ 병맛이네', reporter: 'viewer22', reason: '비하성 표현', date: '2025-04-02', status: '처리 대기' },
    { id: 6, comment: '진짜 역겹다', reporter: 'fairMind', reason: '혐오 표현', date: '2025-04-01', status: '처리 대기' },
    { id: 7, comment: '신고합니다. 무례한 댓글', reporter: 'user001', reason: '무례한 표현', date: '2025-03-31', status: '처리 완료' },
    { id: 8, comment: '이건 그냥 선 넘었지', reporter: 'goodPeople', reason: '도 넘은 비난', date: '2025-03-30', status: '처리 대기' },
    { id: 9, comment: '쟤 진짜 이상함ㅋㅋ', reporter: 'watcher', reason: '조롱성 댓글', date: '2025-03-29', status: '처리 대기' },
    { id: 10, comment: '야 넌 답이 없다', reporter: 'oldman', reason: '인신공격', date: '2025-03-28', status: '처리 완료' },
  ];

const CommentReport = () => {
  return (
    <div className="dashboard-section">
      <h3>💬 댓글 신고 목록</h3>
      <p>신고된 댓글을 확인하고 처리할 수 있는 영역입니다.</p>

      <table className="report-table">
        <thead>
          <tr>
            <th>#</th>
            <th>댓글 내용</th>
            <th>신고자</th>
            <th>신고 사유</th>
            <th>신고 날짜</th>
            <th>처리 상태</th>
            <th>조치</th>
          </tr>
        </thead>
        <tbody>
          {dummyCommentReports.map((report) => (
            <tr key={report.id}>
              <td>{report.id}</td>
              <td>{report.comment}</td>
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

export default CommentReport;
