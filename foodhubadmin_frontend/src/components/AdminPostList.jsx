import axios from "axios";
import { useEffect, useState } from "react"
import { useNavigate } from "react-router-dom";

const AdminPostList = () => {
    const [notice, setNotice] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const navigate = useNavigate();
    const postsPerPage = 10;

    const fetchPost = async () => {
        try {
            const response = await axios.get(`${import.meta.env.VITE_API_URL}/admin/post/notice`, {
                headers: { Authorization: `Bearer ${localStorage.getItem('token')}` },
            });
            const sorted = response.data.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt));
            setNotice(sorted);

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

    const handleDelete = async (postId) => {
        try {
            await axios.patch(`${import.meta.env.VITE_API_URL}/admin/post/${postId}/delete`, null, {
                headers: { Authorization: `Bearer ${localStorage.getItem('token')}` },
            });
            setNotice(prev => prev.map(post => post.id === postId ? { ...post, status: 'DELETED' } : post));
        } catch (e) {
            console.error("삭제 실패", e);
        }
    };

    const handleRestore = async (postId) => {
        try {
            await axios.patch(`${import.meta.env.VITE_API_URL}/admin/post/${postId}/restore`, null, {
                headers: { Authorization: `Bearer ${localStorage.getItem('token')}` },
            });
            setNotices(prev => prev.map(post => post.id === postId ? { ...post, status: 'ACTIVE' } : post));
        } catch (e) {
            console.error("복구 실패", e);
        }
    };

    useEffect(() => {
        fetchPost();
    }, []);

    const indexOfLast = currentPage * postsPerPage;
    const indexOfFirst = indexOfLast - postsPerPage;
    const currentPosts = notice.slice(indexOfFirst, indexOfLast);
    const totalPages = Math.ceil(notice.length / postsPerPage);
    return (
        <div className="dashboard-section">
            <h3>📢 공지사항 게시글 목록</h3>
            <table className="report-table">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>게시글 제목</th>
                        <th>작성자</th>
                        <th>등록일</th>
                        <th>상태</th>
                        <th>조치</th>
                    </tr>
                </thead>
                <tbody>
                    {currentPosts.map((notice, index) => (
                        <tr key={notice.id}>
                            <td>{indexOfFirst + index + 1}</td>
                            <td>{notice.title}</td>
                            <td>{notice.nickname}</td>
                            <td>{new Date(notice.createdAt).toLocaleDateString()}</td>
                            <td>{notice.status === 'ACTIVE' ? '활성' : '삭제됨'}</td>
                            <td>
                                {notice.status === 'DELETED' ? (
                                    <button onClick={() => handleRestore(notice.id)}>복구</button>
                                ) : (
                                    <button onClick={() => handleDelete(notice.id)}>삭제</button>
                                )}
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>

            {/* 페이징 */}
            <div className="pagination">
                <button onClick={() => setCurrentPage(1)} disabled={currentPage === 1}>⏮</button>
                <button onClick={() => setCurrentPage(p => Math.max(p - 1, 1))}>◀</button>
                <span>{currentPage} / {totalPages}</span>
                <button onClick={() => setCurrentPage(p => Math.min(p + 1, totalPages))}>▶</button>
                <button onClick={() => setCurrentPage(totalPages)} disabled={currentPage === totalPages}>⏭</button>
            </div>
        </div>
    );
};

export default AdminPostList;

