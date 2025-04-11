import { useEffect, useState } from 'react';
import '../assets/css/postReport.css';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';



const CommentReport = () => {
  const [commentReports, setCommentReports] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const navigate = useNavigate();
  const reportsPerPage = 10; // 필요에 따라 조절 가능

  const indexOfLast = currentPage * reportsPerPage;
  const indexOfFirst = indexOfLast - reportsPerPage;
  const currentReports = commentReports.slice(indexOfFirst, indexOfLast);
  const totalPages = Math.ceil(commentReports.length / reportsPerPage);

  const fetchReports = async () => {

    try {
      const reportResponse = await axios.get(`${import.meta.env.VITE_API_URL}/admin/comment-report`, {
        headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
      })

      setCommentReports(reportResponse.data);

    } catch (error) {
      if (error.response) {
        if (error.response.status === 401) {
          navigate('/error/401');
        } else if (error.response.status === 500) {
          navigate('/error/500');
        } else if (error.response.status === 404) {
          console.error('404: 해당 API 경로가 존재하지 않습니다.');
        }
      } else {
        console.error('요청 실패:', error.message);
      }
    }
  }

  const formatStatus = (status) => {
    switch (status) {
      case 'PENDING':
        return '처리 대기';
      case 'REVIEWED':
        return '검토됨';
      case 'RESOLVED':
        return '처리 완료';
      default:
        return status;
    }
  }

  const handleStatusChange = async (id, newStatus) => {
    try {
      await axios.patch(`${import.meta.env.VITE_API_URL}/admin/comment-report/${id}/status`,
        { status: newStatus },
        {
          headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
        });

      setCommentReports((prev) => prev.map((report) =>
        report.id === id ? { ...report, commentReportStatus: newStatus } : report)
      );
    } catch (error) {
      console.log('상태변경 실패', error);
    }
  }

  const handleDeleteComment = async (commentId) => {
    try {
      await axios.patch(`${import.meta.env.VITE_API_URL}/admin/comment-report/${commentId}/delete`, null, {
        headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
      });
      setCommentReports((prev) => prev.map((report) =>
        report.commentId === commentId ? { ...report, commentStatus: 'DELETED' } : report
      ));
    } catch (error) {
      console.log("댓글글 삭제 실패", error);
    }
  }

  useEffect(() => {
    fetchReports();
  }, []);

  return (
    <div className="dashboard-section">
      <h3>💬 댓글 신고 목록</h3>
      <p>신고된 댓글을 확인하고 처리할 수 있는 영역입니다.</p>

      <table className="report-table">
        <thead>
          <tr>
            <th>#</th>
            <th>댓글 아이디</th>
            <th>댓글 내용</th>
            <th>신고자</th>
            <th>신고 사유</th>
            <th>신고 날짜</th>
            <th>처리 상태</th>
            <th>조치</th>
            <th>댓글 삭제</th>
          </tr>
        </thead>
        <tbody>
          {currentReports.map((commentReports, index) => (
            <tr key={commentReports.id}>
              <td>{indexOfFirst + index + 1}</td>
              <td>{commentReports.commentId}</td>
              <td>{commentReports.commentContent}</td>
              <td>{commentReports.userId}</td>
              <td>{commentReports.content}</td>
              <td>{new Date(commentReports.createdAt).toLocaleDateString()}</td>
              <td>{formatStatus(commentReports.commentReportStatus)}</td>
              <td>
                <select
                  value={commentReports.commentReportStatus}
                  onChange={(e) => handleStatusChange(commentReports.id, e.target.value)}
                >
                  <option value="PENDING">처리 대기</option>
                  <option value="REVIEWED">검토됨</option>
                  <option value="RESOLVED">처리 완료</option>
                </select>
              </td>
              <td>
                {commentReports.commentStatus === 'DELETED' ? (
                  <span style={{ color: 'gray' }}>삭제 완료</span>
                ) : (
                  <button className="action-button" onClick={() => handleDeleteComment(commentReports.commentId)}>
                    댓글 삭제
                  </button>
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