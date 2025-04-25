
import { useEffect, useRef, useState } from 'react';
import '../assets/css/postReport.css';
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';
import Modal from 'react-modal';
import ReactModal from 'react-modal';


const PostReport = () => {

  const [postReportData, setPostReportData] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [statusFilter , setStatusFilter] = useState('ALL'); // 처리 상태별 필터를 걸기 위한 변수
  const reportsPerPage = 10;
  const navigate = useNavigate();

  const fetchReports = async () => {

    try {
      const reportResponse = await axios.get(`${import.meta.env.VITE_API_URL}/admin/post-report`, {
        headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
      });

      const sortedData = reportResponse.data.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt));
      setPostReportData(sortedData);

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
      await axios.patch(`${import.meta.env.VITE_API_URL}/admin/post-report/${id}/status`, // 일부 데이터만 변경하는 것이라 patch  사용했음
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
      await axios.patch(`${import.meta.env.VITE_API_URL}/admin/post-report/${postId}/delete`, null, {
        headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
      });
      setPostReportData((prev) => prev.map((report) =>
        report.postId === postId ? { ...report, postStatus: 'DELETED' } : report
      ));
    } catch (error) {
      console.log("게시글 삭제 실패", error);
    }
  }

  const handleRestorePost = async (postId) => {
    try {
      await axios.patch(`${import.meta.env.VITE_API_URL}/admin/post-report/${postId}/restore`, null, {
        headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
      });
      setPostReportData((prev) => prev.map((report) =>
        report.postId === postId ? { ...report, postStatus: 'ACTIVE' } : report
      ));
    } catch (error) {
      console.log("게시글 복구 실패", error);
    }
  }

  // 게시글 신고 팝업창
  const [isOpen, setIsOpen] = useState(false);
  const [openId, setOpenId] = useState(null);
  const [postContent, setPostContent]= useState(null);
  const [nickname, setNickname] = useState(null);

  const fetchPost = async(id) => {
    try {
      const {data} = await axios.get(`${import.meta.env.VITE_API_URL}/admin/posts/postContent/${id}`,
        { headers: { Authorization: `Bearer ${localStorage.getItem('token')}` } });
      const content = data.content.replace(/<[^>]*>/g, '');
      setPostContent(content);
      setNickname(data.nickname);

    } catch(error) {

      if (error.response) {
        if (error.response.status === 401) {
          navigate("/error/401");
        }
        else if (error.response.status === 500) {
          navigate("/error/500")
        }
        else if (error.response.status === 403) {
          navigate("/error/403");
        }
        else {
          console.log(error);
        }
      }

    }

  };


  useEffect(() => {
    fetchReports();
  }, []);

  const filteredReports = postReportData.filter((report) => statusFilter === 'ALL' ? true : report.postReportStatus === statusFilter);
  const indexOfLast = currentPage * reportsPerPage;
  const indexOfFirst = indexOfLast - reportsPerPage;
  const currentReports = filteredReports.slice(indexOfFirst, indexOfLast);
  const totalPages = Math.ceil(filteredReports.length / reportsPerPage);

  return (
    <div className="dashboard-section">
      <h3>📋 게시글 신고 목록</h3>
      <p>신고된 게시글을 확인하고 처리할 수 있는 영역입니다.</p>

      {/* 상태별 필터 걸기 */}
      <div style={{ marginBottom: '15px' }}>
        <label>처리 상태 필터: </label>
        <select value={statusFilter} onChange={(e) => {
          setCurrentPage(1); // 필터 변경 시 첫 페이지로
          setStatusFilter(e.target.value);
        }}>
          <option value="ALL">전체</option>
          <option value="PENDING">처리 대기</option>
          <option value="REVIEWED">검토됨</option>
          <option value="RESOLVED">처리 완료</option>
        </select>
      </div>

      <table className="report-table">
        <thead>
          <tr>
            <th>No.</th>
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
              <td>
                <Link onClick={() => {
                  setOpenId(postReportData.postId); 
                  fetchPost(postReportData.postId); 
                  setIsOpen(true);
                }}>
                  {postReportData.postTitle}
                </Link>
                {isOpen && openId === postReportData.postId && (
                  <>
                    <Modal 
                      isOpen={isOpen}
                      onRequestClose={() => setIsOpen(false)}
                      style={{
                        overlay: {backgroundColor: "rgba(0, 0, 0, 0.2)"},
                        content: { width: '500px', height: '300px', margin: 'auto', overflowY : 'auto', borderRadius: '10px', backgroundColor: "#F5FBFF" }
                    }}>
                        <h3 align="center">게시글 제목 : {postReportData.postTitle}</h3>
                        <h4 align="right">게시글 작성자 : {nickname}</h4>
                        <span style={{wordWrap: 'break-word'}}>게시글 내용 : <br/>{postContent}</span><br/><br/>
                        <button style={{display: 'block', margin: '0 auto'}} onClick={() => {setIsOpen(false); setPostContent(null); setOpenId(null);}}>🚫닫기</button>
                    </Modal>
                  </>
                )}
              </td>
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
                  <button className="action-button restore" onClick={() => handleRestorePost(postReportData.postId)}>
                    게시글 복구
                  </button>
                ) : (
                  <button className="action-button delete" onClick={() => handleDeletePost(postReportData.postId)}>
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
          {/* ⏮ 맨 앞으로 이동 */}
          <button onClick={() => setCurrentPage(1)} disabled={currentPage === 1}>
            ⏮
          </button>

          {/* ◀ 이전 */}
          <button onClick={() => setCurrentPage(prev => Math.max(prev - 1, 1))} disabled={currentPage === 1}>
            ◀
          </button>

          {/* 페이지 번호들 */}
          {(() => {
            const pageNumbers = [];
            let startPage = Math.max(currentPage - 2, 1);
            let endPage = Math.min(startPage + 4, totalPages);

            // 페이지 범위 보정
            if (endPage - startPage < 4) {
              startPage = Math.max(endPage - 4, 1);
            }

            // ... 앞
            if (startPage > 1) {
              pageNumbers.push(<span key="start-ellipsis">...</span>);
            }

            for (let i = startPage; i <= endPage; i++) {
              pageNumbers.push(
                <button
                  key={i}
                  onClick={() => setCurrentPage(i)}
                  className={currentPage === i ? 'active-page' : ''}
                >
                  {i}
                </button>
              );
            }

            // ... 뒤
            if (endPage < totalPages) {
              pageNumbers.push(<span key="end-ellipsis">...</span>);
            }

            return pageNumbers;
          })()}

          {/* ▶ 다음 */}
          <button onClick={() => setCurrentPage(prev => Math.min(prev + 1, totalPages))} disabled={currentPage === totalPages}>
            ▶
          </button>

          {/* ⏭ 맨 끝으로 이동 */}
          <button onClick={() => setCurrentPage(totalPages)} disabled={currentPage === totalPages}>
            ⏭
          </button>
        </div>
      )}

    </div>
  );
};

export default PostReport;