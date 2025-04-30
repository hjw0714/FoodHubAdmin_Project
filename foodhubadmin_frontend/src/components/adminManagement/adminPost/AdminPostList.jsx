import axios from "axios";
import { useEffect, useState } from "react"
import { useNavigate } from "react-router-dom";
import Modal from 'react-modal';
Modal.setAppElement('#root');

const AdminPostList = () => {
    const [notice, setNotice] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const navigate = useNavigate();
    const postsPerPage = 10;

    const [isOpen, setIsOpen] = useState(null);
    const [postContent, setPostContent] = useState(null);
    const [openTitle, setOpenTitle] = useState(null);
    const [openNickName, setOpenNickName] = useState(null);

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

    const fetchPostContent = async (postId) => {
        try {
            const { data } = await axios.get(`${import.meta.env.VITE_API_URL}/admin/posts/postContent/${postId}`, {
                headers: { Authorization: `Bearer ${localStorage.getItem('token')}` },
            });
            const content = data.content.replace(/<[^>]*>/g, ''); // html 태그 제거
            setPostContent(content);
            setOpenTitle(data.title);
            setOpenNickName(data.nickname);
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
            setNotice(prev => prev.map(post => post.id === postId ? { ...post, status: 'ACTIVE' } : post));
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
                            <td>
                                <span
                                    style={{ cursor: 'pointer', textDecoration: 'underline' }}
                                    onClick={() => {
                                        fetchPostContent(notice.id);
                                        setIsOpen(true);
                                    }}
                                >
                                    {notice.title}
                                </span>
                            </td>
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

            {isOpen && (
                <Modal
                    isOpen={isOpen}
                    onRequestClose={() => {
                        setIsOpen(false);
                        setPostContent(null);
                        setOpenTitle(null);
                        setOpenNickName(null);
                    }}
                    style={{
                        overlay: {
                            backgroundColor: 'rgba(0, 0, 0, 0.2)',
                        },
                        content: {
                            width: '500px',
                            height: '300px',
                            margin: 'auto',
                            overflowY: 'auto',
                            borderRadius: '10px',
                            backgroundColor: '#F5FBFF',
                            padding: '20px 30px',  // 🔥 더 깔끔하게
                        }
                    }}
                >
                    <h3 align="center">게시글 제목: {openTitle}</h3>
                    <h4 align="right">작성자: {openNickName}</h4>
                    <p style={{ whiteSpace: 'pre-wrap' }}>본문 내용:<br />{postContent}</p>
                    <button
                        style={{ display: 'block', margin: '0 auto', marginTop: '10px' }}
                        onClick={() => {
                            setIsOpen(false);
                            setPostContent(null);
                            setOpenTitle(null);
                            setOpenNickName(null);
                        }}
                    >
                        닫기
                    </button>
                </Modal>
            )
            }

            {/* 페이징 */}
            <div className="pagination">
                <button onClick={() => setCurrentPage(1)} disabled={currentPage === 1}>⏮</button>
                <button onClick={() => setCurrentPage(p => Math.max(p - 1, 1))}>◀</button>
                <span>{currentPage} / {totalPages}</span>
                <button onClick={() => setCurrentPage(p => Math.min(p + 1, totalPages))}>▶</button>
                <button onClick={() => setCurrentPage(totalPages)} disabled={currentPage === totalPages}>⏭</button>
            </div>
        </div >
    );
};

export default AdminPostList;

