import axios from 'axios';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

const initialMembers = [
  { id: 1, nickname: 'user123', email: 'user123@example.com', joinDate: '2023-10-01', membershipType: '일반 회원' },
  { id: 2, nickname: 'goodguy', email: 'goodguy@example.com', joinDate: '2024-01-15', membershipType: '사업자 회원' },
  { id: 3, nickname: 'cookmaster', email: 'cook@example.com', joinDate: '2024-03-21', membershipType: '일반 회원' },
  { id: 4, nickname: 'testuser', email: 'test@naver.com', joinDate: '2025-01-05', membershipType: '일반 회원' },
  { id: 5, nickname: 'admin', email: 'admin@foodhub.com', joinDate: '2023-06-25', membershipType: '사업자 회원' },
  { id: 6, nickname: 'yeongdo95', email: 'yeongdo95@food.com', joinDate: '2024-12-11', membershipType: '일반 회원' },
  { id: 7, nickname: 'seoulman', email: 'seoul@korea.com', joinDate: '2023-02-28', membershipType: '사업자 회원' },
  { id: 8, nickname: 'fastcook', email: 'cook@naver.com', joinDate: '2023-05-14', membershipType: '일반 회원' },
  { id: 9, nickname: 'happymeal', email: 'happy@meal.com', joinDate: '2024-09-12', membershipType: '일반 회원' },
  { id: 10, nickname: 'chickenlover', email: 'chicken@food.com', joinDate: '2025-02-02', membershipType: '사업자 회원' },
  { id: 11, nickname: 'ssgfan', email: 'ssg@korea.com', joinDate: '2023-08-08', membershipType: '일반 회원' },
  { id: 12, nickname: 'superboss', email: 'boss@biz.com', joinDate: '2024-07-30', membershipType: '사업자 회원' },
  { id: 13, nickname: 'noodleking', email: 'noodle@king.com', joinDate: '2024-04-03', membershipType: '일반 회원' },
  { id: 14, nickname: 'bbqfan', email: 'bbq@naver.com', joinDate: '2025-03-17', membershipType: '사업자 회원' },
  { id: 15, nickname: 'ontheroad', email: 'road@driver.com', joinDate: '2024-11-19', membershipType: '일반 회원' },
  { id: 16, nickname: 'busyman', email: 'busy@life.com', joinDate: '2023-07-04', membershipType: '사업자 회원' },
  { id: 17, nickname: 'dreamer', email: 'dream@naver.com', joinDate: '2023-10-10', membershipType: '일반 회원' },
  { id: 18, nickname: 'bizowner', email: 'biz@company.com', joinDate: '2025-01-01', membershipType: '사업자 회원' },
  { id: 19, nickname: 'foodie22', email: 'foodie@naver.com', joinDate: '2023-11-11', membershipType: '일반 회원' },
  { id: 20, nickname: 'gogogo', email: 'go@speed.com', joinDate: '2024-06-06', membershipType: '사업자 회원' },
];

const MemberList = () => {
  const navigate = useNavigate();
  const [members, setMembers] = useState(initialMembers);
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

  // 탈퇴
  const handleSignOut = async(id) => {
    const deleteMem = window.confirm("정말 탈퇴시키시겠습니까?");
    if(deleteMem) {
      try{
        await axios.delete(`${import.meta.env.VITE_API_URL}/user/memberList/delete/${id}`, 
          { headers: { Authorization: `Bearer ${localStorage.getItem('token')}` } });
        alert(id, "님 탈퇴되었습니다.");
        window.location.reload();

      } catch(error) {
        console.log(error.message);
        alert("탈퇴 실패");
      }
    }
  };

  // membershipType Update
  const handleUpdate = async(id, membershipType) => {
    
    try {
      await axios.put(`${import.meta.env.VITE_API_URL}/user/memberList/update/${id}`, {membershipType}, 
          { headers: { Authorization: `Bearer ${localStorage.getItem('token')}` } });
      alert("변경 완료");
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
                <td>{member.deletedAt === null ? (<span> </span>) : (<>{new Date(member.deletedAt).toLocaleDateString()}</>)}</td>
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
                  {member.deletedAt !== null ? (<span>탈퇴 회원</span>) : (
                    <button onClick={() => handleSignOut(member.id)}>탈퇴</button>
                  )}
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
