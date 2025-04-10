import axios from 'axios';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

const MemberList = () => {
  const navigate = useNavigate();
  const [members, setMembers] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');

  const handleMembershipChange = (id, newType) => {
    const updated = members.map((m) =>
      m.id === id ? { ...m, membershipType: newType } : m
    );
    setMembers(updated);
  };

  const filteredMembers = members.filter(
    (m) =>
      m.nickname.toLowerCase().includes(searchTerm.toLowerCase()) ||
      m.email.toLowerCase().includes(searchTerm.toLowerCase())
  );
  
  const fetchMember = async() => {
    try {
      const {data} = await axios.get(`${import.meta.env.VITE_API_URL}/user/memberList`,
        { headers: { Authorization: `Bearer ${localStorage.getItem('token')}` } });
      const temp = data.map(user => ({
        ...user,
        membershipType : user.membershipType === "COMMON"
          ? "일반 회원"
          : user.membershipType === "BUSSI"
          ? "사업자 회원"
          : "관리자"
      }));
      setMembers(temp);
      
    } catch(error) {
      if(error.response) {
        if(error.response.status === 401) {
          navigate("/error/401");
        }
        else if (error.response.status === 500) {
          navigate("/error/500")
        }
        else if(error.response.status === 403) {
          navigate("/error/403");
        }
        else {
          console.log(error);
        }
      }
    }

  };

  useEffect(() =>  {
    fetchMember();
  }, []);

  const handleSignOut = async(id) => {
    const deleteMem = window.confirm("정말 탈퇴시키시겠습니까?");
    if(deleteMem) {
      try{
        await axios.delete(`${import.meta.env.VITE_API_URL}/user/memberList/${id}`);
        alert("탈퇴되었습니다.");
        window.location.reload();

      } catch(error) {
        console.log(error.message);
        alert("탈퇴 실패");
      }
    }
  };

  // membershipType Update 미완성
  const handleUpdate = async(id, membershipType) => {
    if(membershipType === "일반 회원") {
      membershipType = "COMMON";
    }
    else if (membershipType === "사업자 회원") {
      membershipType = "BUSSI";
    }
    console.log(id, membershipType);
    
    try {
      await axios.put(`${import.meta.env.VITE_API_URL}/user/memberList/${id}`, {membershipType : membershipType});
      alert("변경 완료");
      navigate.location.reload();

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
            <th>멤버십</th>
            <th>변경</th>
            <th>탈퇴</th>
          </tr>
        </thead>
        <tbody>
          {filteredMembers.length > 0 ? (
            filteredMembers.map((member) => (
              <tr key={member.id}>
                <td>{member.id}</td>
                <td>{member.nickname}</td>
                <td>{member.email}</td>
                <td>{new Date(member.joinAt).toLocaleDateString()}</td>
                <td>{member.membershipType}</td>
                <td>
                  <select
                    value={member.membershipType}
                    onChange={(e) => handleMembershipChange(member.id, e.target.value)}
                  >
                    <option value="일반 회원">일반 회원</option>
                    <option value="사업자 회원">사업자 회원</option>
                  </select>  {" "}
                  <button onClick={() => handleUpdate(member.id, member.membershipType)}>변경</button>
                </td>
                <td>
                  <button onClick={() => handleSignOut(member.id)}>탈퇴</button>
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="6">검색 결과가 없습니다.</td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
};

export default MemberList;
