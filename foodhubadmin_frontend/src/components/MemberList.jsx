import axios from 'axios';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';


const MemberList = () => {
  const navigate = useNavigate();
  const [members, setMembers] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');

  const [currentPage, setCurrentPage] = useState(1);
  const membersPerPage = 10;

  const indexOfLast = currentPage * membersPerPage;
  const indexOfFirst = indexOfLast - membersPerPage;

  const filteredMembers = members.filter(
    (m) =>
      m.nickname.toLowerCase().includes(searchTerm.toLowerCase()) ||
      m.email.toLowerCase().includes(searchTerm.toLowerCase())
  );

  const currentMembers = filteredMembers.slice(indexOfFirst, indexOfLast);
  const totalPages = Math.ceil(filteredMembers.length / membersPerPage);

  const handleMembershipChange = (id, newType) => {
    const updated = members.map((m) =>
      m.id === id ? { ...m, membershipType: newType } : m
    );
    setMembers(updated);
  };

  const fetchMember = async () => {
    try {

      const { data } = await axios.get(`${import.meta.env.VITE_API_URL}/admin/user/memberList`,

        { headers: { Authorization: `Bearer ${localStorage.getItem('token')}` } });
      const temp = data.map(user => ({
        ...user,
        membershipType: user.membershipType === "COMMON"
          ? "일반 회원"
          : user.membershipType === "BUSSI"
            ? "사업자 회원"
            : "관리자"
      }));
      setMembers(temp);

    } catch (error) {
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
    fetchMember();
  }, []);

  // 탈퇴
  const handleSignOut = async (id) => {
    const deleteMem = window.confirm(id + "님 정말 탈퇴시키시겠습니까?");
    if (deleteMem) {
      try {
        await axios.delete(`${import.meta.env.VITE_API_URL}/admin/user/memberList/delete/${id}`,
          { headers: { Authorization: `Bearer ${localStorage.getItem('token')}` } });
        alert(id + "님 탈퇴되었습니다.");
        window.location.reload();

      } catch (error) {
        console.log(error.message);
        alert("탈퇴 실패");
      }
    }
  };

  // membershipType Update
  const handleUpdate = async (id, membershipType) => {

    try {
      await axios.put(`${import.meta.env.VITE_API_URL}/admin/user/memberList/update/${id}`, { membershipType },
        { headers: { Authorization: `Bearer ${localStorage.getItem('token')}` } });
      alert("변경 완료");
      window.location.reload();

    } catch (error) {
      console.log(error);
      alert("변경 실패");
    }

  };

  // Change Password
  const [isOpen, setIsOpen] = useState(false);
  const [passwd, setPasswd] = useState("");
  const [confirmPasswd, setConfirmPasswd] = useState("");
  const [errorMsg, setErrorMsg] = useState("");

  const handleSubmit = async(id) => {
    if(!passwd || !confirmPasswd) {
      setErrorMsg("작성란 모두 입력하세요.");
      return;
    }
    if(passwd !== confirmPasswd) {
      setErrorMsg("비밀번호가 일치하지 않습니다.");
      return;
    }
    if(passwd.length < 4) {
      setErrorMsg("비밀번호는 4글자 이상이어야 합니다.");
      return;
    }
    
    try {
      await axios.put(`${import.meta.env.VITE_API_URL}/admin/user/changePasswd`, { userId: id, passwd }, {
        headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
      });
      alert("비밀번호 변경 완료");
      setIsOpen(false);
      window.location.reload();

    } catch(error) {
      console.log(error);
      alert("변경 실패");

    }

  };

  return (
    <div className="dashboard-section">
      <h3>👥 회원 리스트</h3>
      <p>회원 검색 및 멤버십 타입 변경 기능을 제공합니다.</p>

      <input
        type="text"
        placeholder="닉네임 또는 이메일 검색"
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
        style={{ marginBottom: '15px', width: '60%' }}
      />

      <table className="report-table">
        <thead>
          <tr>
            <th>아이디</th>
            <th>닉네임</th>
            <th>이메일</th>
            <th>가입일</th>
            <th>탈퇴일</th>
            <th>멤버십</th>
            <th>멤버십 변경</th>
            <th>비밀번호 변경</th>
            <th>탈퇴</th>
          </tr>
        </thead>
        <tbody>
          {currentMembers.length > 0 ? (
            currentMembers.map((member) => (
              <tr key={member.id}>
                <td>{member.id}</td>
                <td>{member.nickname}</td>
                <td>{member.email}</td>
                <td>{new Date(member.joinAt).toLocaleDateString()}</td>
                <td>{member.deletedAt === null ? (<span> </span>) : (<>{new Date(member.deletedAt).toLocaleDateString()}</>)}</td>
                <td>{member.membershipType}</td>
                <td>
                  {member.deletedAt !== null || member.membershipType === "관리자" ? (
                    <span>변경 불가</span>
                  ) : (
                    <>
                    <select
                    value={member.membershipType}
                    onChange={(e) => handleMembershipChange(member.id, e.target.value)}
                  >
                    <option value="일반 회원">일반 회원</option>
                    <option value="사업자 회원">사업자 회원</option>
                  </select>
                  <button onClick={() => handleUpdate(member.id, member.membershipType)}>변경</button>
                  </>
                  )}
                </td>
                <td>
                  {member.deletedAt !== null ? (<span>변경 불가</span>) : (
                    <>
                    <button onClick={() => setIsOpen(true)}>변경</button>
                    {isOpen && (
                      <div style={{
                        position: "fixed", top: "50%", left: "50%", transform: "translate(-50%, -50%)",
                        background: "white", padding: "20px", borderRadius: "10px"
                    }}>
                        <h3>{member.id} 비밀번호 변경</h3>
                        비밀번호: <input 
                            type="password" 
                            value={passwd} 
                            onChange={(e) => setPasswd(e.target.value)}
                            placeholder="새 비밀번호 입력"
                        /> <br/>
                        비밀번호 재입력: <input type='password' value={confirmPasswd} onChange={(e) => setConfirmPasswd(e.target.value)} placeholder='비밀번호 다시 입력' /> <br/><br/>
                        {errorMsg && <div className="error-message">{errorMsg}</div>}
                        <button onClick={() => handleSubmit(member.id)}>✅ 변경</button> {" "}
                        <button onClick={() => setIsOpen(false)}>❌ 닫기</button>
                    </div>
                    )}
                    </>
                  )}
                </td>
                <td>
                  {member.deletedAt !== null ? (<span>탈퇴 회원</span>) : (
                    <>
                    <button onClick={() => handleSignOut(member.id)}>탈퇴</button>

                    </>
                  )}
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="8">검색 결과가 없습니다.</td>
            </tr>
          )}
        </tbody>

      </table>
      {totalPages > 1 && (
        <div className="pagination" style={{ marginTop: '15px' }}>
          {/* ⏮ 맨 앞으로 */}
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

            if (endPage - startPage < 4) {
              startPage = Math.max(endPage - 4, 1);
            }

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

            if (endPage < totalPages) {
              pageNumbers.push(<span key="end-ellipsis">...</span>);
            }

            return pageNumbers;
          })()}

          {/* ▶ 다음 */}
          <button onClick={() => setCurrentPage(prev => Math.min(prev + 1, totalPages))} disabled={currentPage === totalPages}>
            ▶
          </button>

          {/* ⏭ 맨 끝으로 */}
          <button onClick={() => setCurrentPage(totalPages)} disabled={currentPage === totalPages}>
            ⏭
          </button>
        </div>
      )}
    </div>
  );
};

export default MemberList;
