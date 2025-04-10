
import { useEffect, useState } from 'react';
import '../assets/css/postReport.css';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';


const PostReport = () => {

  const [postReportData, setPostReportData] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const reportsPerPage = 10;
  const navigate = useNavigate();

  const fetchReports = async () => {

    try {
      const reportResponse = await axios.get(`${import.meta.env.VITE_API_URL}/post-report`, {
        headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
      });

      setPostReportData(reportResponse.data);

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
  };

  const handleStatusChange = async (id, newStatus) => {
    try {
      await axios.patch(`${import.meta.env.VITE_API_URL}/post-report/${id}/status`, // 일부 데이터만 변경하는 것이라 patch  사용했음
        { status: newStatus },
        {
          headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
        });

      setPostReportData((prev) => prev.map((report) =>
        report.id === id ? { ...report, postReportStatus: newStatus } : report)
      );
    } catch (error) {
      console.log('상태변경 실패', error);
    }
  }

  const handleDeletePost = async (postId) => {
    try {
      await axios.patch(`${import.meta.env.VITE_API_URL}/post-report/${postId}/delete`, null, {
        headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
      });
      setPostReportData((prev) => prev.map((report) =>
        report.postId === postId ? { ...report, postStatus: 'DELETED' } : report
      ));
    } catch (error) {
      console.log("게시글 삭제 실패", error);
    }
  }

  useEffect(() => {
    fetchReports();
  }, []);

  const indexOfLast = currentPage * reportsPerPage;
  const indexOfFirst = indexOfLast - reportsPerPage;
  const currentReports = postReportData.slice(indexOfFirst, indexOfLast);
  const totalPages = Math.ceil(postReportData.length / reportsPerPage);

  return (
    <div className="dashboard-section">
      <h3>📋 게시글 신고 목록</h3>
      <p>신고된 게시글을 확인하고 처리할 수 있는 영역입니다.</p>

      <table className="report-table">
        <thead>
          <tr>
            <th>#</th>
            <th>게시글 아이디</th>
            <th>게시글 제목</th>
            <th>신고자</th>
            <th>신고 사유</th>
            <th>신고 날짜</th>
            <th>처리 상태</th>
            <th>조치</th>
            <th>게시글 삭제</th>
          </tr>
        </thead>
        <tbody>
          {currentReports.map((postReportData, index) => (
            <tr key={postReportData.id}>
              <td>{indexOfFirst + index + 1}</td>
              <td>{postReportData.postId}</td>
              <td>{postReportData.postTitle}</td>
              <td>{postReportData.userId}</td>
              <td>{postReportData.content}</td>
              <td>{new Date(postReportData.createdAt).toLocaleDateString()}</td>
              <td>{formatStatus(postReportData.postReportStatus)}</td>
              <td>
                <select
                  value={postReportData.postReportStatus}
                  onChange={(e) => handleStatusChange(postReportData.id, e.target.value)}
                >
                  <option value="PENDING">처리 대기</option>
                  <option value="REVIEWED">검토됨</option>
                  <option value="RESOLVED">처리 완료</option>
                </select>
              </td>
              <td>
                {postReportData.postStatus === 'DELETED' ? (
                  <span style={{ color: 'gray' }}>삭제 완료</span>
                ) : (
                  <button className="action-button" onClick={() => handleDeletePost(postReportData.postId)}>
                    게시글 삭제
                  </button>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {/* 페이지네이션 UI */}
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

export default PostReport;