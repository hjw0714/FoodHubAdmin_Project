import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import '../assets/css/createPost.css';
import axios from "axios";
import MDEditor from "@uiw/react-md-editor";

const AdminPostCreate = () => {
    const navigate = useNavigate();

    const [title, setTitle] = useState('');
    const [content, setContent] = useState('');
    const [categoryId] = useState(0); // 공지사항 고정
    const [subCateId, setSubCateId] = useState(0);
    const [subCateNm, setSubCateNm] = useState('공지');
    const [file, setFile] = useState(null);
    const [userId, setUserId] = useState('');

    const subCategories = [
        { id: 0, name: '공지' },
        { id: 26, name: '이벤트' }
    ];

    // 🔹 유저 정보 불러오기
    const fetchUser = async () => {
        try {
            const { data } = await axios.get(`${import.meta.env.VITE_API_URL}/admin/user/profile`, {
                headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
            });
            setUserId(data.userId); // 백엔드 응답에서 userId만 추출
        } catch (error) {
            if (error.response.status === 401) {
                navigate('/error/401');
            }
            else if (error.response.status === 403) {
                navigate('/error/403');
            }
            else {
                console.error(error);
                navigate('/error/500');
            }
        }
    };

    useEffect(() => {
        fetchUser();
    }, []);

    const handleSubmit = async (e) => {
        e.preventDefault();

        const formData = new FormData();
        formData.append("title", title);
        formData.append("content", content);
        formData.append("categoryId", categoryId);
        formData.append("subCateId", subCateId);
        formData.append("categoryNm", "공지사항");
        formData.append("subCateNm", subCateNm);
        formData.append("userId", userId); // 🔹 동적으로 설정된 userId

        if (file) {
            formData.append("file", file);
        }

        for (const [key, value] of formData.entries()) {
            console.log(`${key}:`, value);
        }

        try {
            await axios.post(`${import.meta.env.VITE_API_URL}/admin/post/create`, formData, {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem("token")}`,
                    "Content-Type": "multipart/form-data"
                }
            });
            alert("게시글이 등록되었습니다.");
            navigate('/admin/dashboard');
        } catch (error) {
            console.error("등록 실패:", error);
            alert("등록 중 오류가 발생했습니다.");
        }
    };

    return (
        <div className="admin-post-create">
            <h2>📢 공지/이벤트 게시글 작성</h2>
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label>제목</label>
                    <input
                        type="text"
                        maxLength={255}
                        value={title}
                        onChange={(e) => setTitle(e.target.value)}
                        required
                    />
                </div>

                <div className="form-group">
                    <label htmlFor="subCategory">말머리</label>
                    <select
                        id="subCategory"
                        className="custom-select"
                        value={subCateId}
                        onChange={(e) => {
                            const selectedId = parseInt(e.target.value);
                            setSubCateId(selectedId);
                            const selectedName = subCategories.find((sc) => sc.id === selectedId)?.name;
                            setSubCateNm(selectedName || '');
                        }}
                        required
                    >
                        {subCategories.map((sub) => (
                            <option key={sub.id} value={sub.id}>
                                {sub.name}
                            </option>
                        ))}
                    </select>
                </div>

                <div className="form-group">
                    <label>내용</label>
                    <div data-color-mode="light">
                        <MDEditor
                            value={content}
                            onChange={setContent}
                            preview="edit"
                            height={300}
                        />
                    </div>
                </div>

                <div className="form-group">
                    <label>첨부파일</label>
                    <input type="file" onChange={(e) => setFile(e.target.files[0])} />
                </div>

                <button type="submit" className="btn btn-primary">등록</button>
            </form>
        </div>
    );
};

export default AdminPostCreate;
