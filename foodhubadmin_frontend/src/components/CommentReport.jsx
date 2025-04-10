import { useState } from 'react';
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
  const [commentReports, setCommentReports] = useState(dummyCommentReports);
  const [currentPage, setCurrentPage] = useState(1);
  const reportsPerPage = 5; // 필요에 따라 조절 가능

  const indexOfLast = currentPage * reportsPerPage;
  const indexOfFirst = indexOfLast - reportsPerPage;
  const currentReports = commentReports.slice(indexOfFirst, indexOfLast);
  const totalPages = Math.ceil(commentReports.length / reportsPerPage);

  const handleStatusChange = (id) => {
    setCommentReports((prev) =>
      prev.map((report) =>
        report.id === id ? { ...report, status: '처리 완료' } : report
      )
    );
  };

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
          {currentReports.map((report, index) => (
            <tr key={report.id}>
              <td>{indexOfFirst + index + 1}</td>
              <td>{report.comment}</td>
              <td>{report.reporter}</td>
              <td>{report.reason}</td>
              <td>{report.date}</td>
              <td>{report.status}</td>
              <td>
                {report.status === '처리 대기' ? (
                  <button className="action-button" onClick={() => handleStatusChange(report.id)}>
                    처리 완료
                  </button>
                ) : (
                  <span style={{ color: 'gray' }}>완료됨</span>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {totalPages > 1 && (
        <div className="pagination">
          {/* ◀ 이전 버튼 */}
          <button
            onClick={() => setCurrentPage((prev) => Math.max(prev - 1, 1))}
            disabled={currentPage === 1}
          >
            ◀
          </button>

          {/* 동적으로 페이지 번호 5개 보여주기 */}
          {Array.from({ length: totalPages }, (_, i) => i + 1)
            .filter((page) => {
              // 현재 페이지 기준 ±2 페이지만 보여줌
              return (
                page >= Math.max(currentPage - 2, 1) &&
                page <= Math.min(currentPage + 2, totalPages)
              );
            })
            .map((page) => (
              <button
                key={page}
                onClick={() => setCurrentPage(page)}
                className={currentPage === page ? 'active-page' : ''}
              >
                {page}
              </button>
            ))}

          {/* ▶ 다음 버튼 */}
          <button
            onClick={() => setCurrentPage((prev) => Math.min(prev + 1, totalPages))}
            disabled={currentPage === totalPages}
          >
            ▶
          </button>
        </div>
      )}
    </div>
  );
};

export default CommentReport;