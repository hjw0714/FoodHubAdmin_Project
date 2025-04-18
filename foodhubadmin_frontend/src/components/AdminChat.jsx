import { useEffect, useRef, useState } from 'react';
import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';
import '../assets/css/adminChat.css';
import axios from 'axios';

const AdminChat = () => {
  const [userList, setUserList] = useState([]);
  const [selectedUser, setSelectedUser] = useState(null);
  const [selectedUserId, setSelectedUserId] = useState(null);
  const [roomId, setRoomId] = useState(null);
  const [messages, setMessages] = useState([]);
  const [input, setInput] = useState('');
  const stompClientRef = useRef(null);
  const messageEndRef = useRef(null);
  const [nicknameInput, setNicknameInput] = useState('');



  const adminId = sessionStorage.getItem('userId');
  const adminNickname = sessionStorage.getItem('nickname');
  const accessToken = localStorage.getItem('token');

  const createPrivateChatRoom = async () => {
    const nickname = nicknameInput.trim();
  
    if (!nickname) {
      alert("닉네임을 입력해주세요.");
      return;
    }
  
    try {
      const response = await axios.post(
        'http://localhost/foodhub/admin/chat/private/create',
        { nickname },
        {
          headers: {
            Authorization: `Bearer ${accessToken}`
          },
          withCredentials: true
        }
      );
  
      const newRoom = response.data;
  
      if (!newRoom || !newRoom.roomId) {
        throw new Error("채팅방 생성 실패: 응답 형식 오류");
      }
  
      // ✅ 선택된 유저 및 채팅방 정보 설정
      setSelectedUser(newRoom.otherUserNickname); // 백엔드에서 닉네임 포함 시
      setSelectedUserId(newRoom.otherUserId);     // 포함되어야 함
      setRoomId(newRoom.roomId);
      setMessages([]);
  
      // ✅ 기존 채팅방 메시지 불러오기 (혹은 비어있을 수 있음)
      const messagesResponse = await axios.get(
        `http://localhost/foodhub/admin/chat/private/messages/${newRoom.roomId}`,
        {
          headers: {
            Authorization: `Bearer ${accessToken}`
          },
          withCredentials: true
        }
      );
  
      setMessages(
        messagesResponse.data.map((m) => ({
          from: m.senderId === adminId ? 'admin' : 'you',
          text: m.chatContent,
        }))
      );
  
      connectToPrivateRoom(newRoom.roomId);
      setNicknameInput('');
      await fetchChatRoom();
  
    } catch (err) {
      console.error("❌ 채팅방 생성 실패:", err);
      alert(err.response?.data?.message || "채팅방 생성 중 오류가 발생했습니다.");
    }
  };

  
  const fetchChatRoom = async () => {
    try {
      console.log("🟢 AdminChat 컴포넌트 로딩됨");

      const response = await axios.get('http://localhost/foodhub/admin/chat/private/list', {
        headers: {
          Authorization: `Bearer ${accessToken}`
        },
        withCredentials: true
      });

      const contentType = response.headers['content-type'];
      if (!contentType || !contentType.includes('application/json')) {
        throw new Error("JSON 응답이 아닙니다.");
      }

      console.log("📦 채팅방 목록:", response.data);
      setUserList(response.data);
    } catch (error) {
      console.error('⚠️ 채팅방 목록 요청 실패:', error);
      alert("채팅방 목록을 불러오지 못했습니다. 로그인이 되어 있는지 확인해주세요.");
    }
  };

  const handleUserClick = async (user) => {
    try {
      console.log("✅ user 클릭됨:", user);
      console.log("➡️ otherUserId:", user.otherUserId);
      setSelectedUser(user.otherUserNickname);
      setSelectedUserId(user.otherUserId);
      setRoomId(user.roomId);

      const response = await axios.get(`http://localhost/foodhub/admin/chat/private/messages/${user.roomId}`, {
        headers: {
          Authorization: `Bearer ${accessToken}`
        },
        withCredentials: true
      });

      const contentType = response.headers['content-type'];
      if (!contentType || !contentType.includes('application/json')) {
        throw new Error("JSON 응답이 아닙니다.");
      }

      const data = response.data;

      if (!Array.isArray(data)) {
        throw new Error("서버 응답이 배열이 아닙니다.");
      }

      setMessages(
        data.map((m) => ({
          from: m.senderId === adminId ? 'admin' : 'you',
          text: m.chatContent,
        }))
      );

      connectToPrivateRoom(user.roomId);

    } catch (error) {
      console.error("❌ 채팅 메시지 불러오기 실패:", error);
      alert("메시지를 불러오지 못했습니다.");
    }
  };

  const connectToPrivateRoom = (roomId) => {
    // ✅ 기존 연결이 있다면 해제
    if (stompClientRef.current) {
      console.log("🔁 기존 WebSocket 연결 해제");
      stompClientRef.current.deactivate();
      stompClientRef.current = null;
    }

    const socket = new SockJS('http://localhost:80/ws');
    const client = new Client({
      webSocketFactory: () => socket,
      onConnect: () => {
        console.log("✅ WebSocket 연결됨");
        client.subscribe(`/topic/private.${roomId}`, (payload) => {
          const message = JSON.parse(payload.body);
           // ✅ 시스템 메시지인 경우 alert 처리
  if (message.type === 'LEAVE' && message.sender === 'SYSTEM') {
    alert(message.content); // 예: "홍길동 님이 채팅방에서 나갔습니다."
    return;
  }
          setMessages(prev => {
            const isDuplicate = prev.some(
              m => m.text === message.content && m.from === (message.sender === adminId ? 'admin' : 'you')
            );
            if (isDuplicate) return prev;
            return [...prev, {
              from: message.sender === adminId ? 'admin' : 'you',
              text: message.content
            }];
          });
        });
      },
      onStompError: (frame) => {
        console.error('🔴 STOMP 오류:', frame);
      }
    });

    client.activate();
    stompClientRef.current = client;
  };

  const sendMessage = async () => {
    if (!input.trim()) return;
  
    const sender = sessionStorage.getItem('userId');             // 서버에서 기대하는 sender 필드
    const senderNickname = sessionStorage.getItem('nickname');   // 서버에서 사용하는 닉네임
    const receiver = selectedUserId;                             // 서버에서 기대하는 receiver 필드
  
    console.log("💬 현재 sender:", sender);
    console.log("💬 현재 receiver:", receiver);
  
    if (!sender || !senderNickname) {
      alert("로그인 정보가 없습니다. 다시 로그인 해주세요.");
      return;
    }
  
    if (!receiver) {
      alert("채팅할 상대를 먼저 선택해주세요.");
      return;
    }
  
    const message = {
      sender,               // ✅ 필드명 수정
      receiver,             // ✅ 필드명 수정
      senderNickname,       // ✅ 서버에서 닉네임 별도 필드로 받음
      content: input,
      type: 'CHAT'
    };
  
    console.log("📤 최종 전송 메시지:", message);
  
    if (stompClientRef.current && stompClientRef.current.connected) {
      stompClientRef.current.publish({
        destination: `/app/chat.private.${roomId}`,
        body: JSON.stringify(message)
      });
  
      setMessages([...messages, { from: 'admin', text: input }]);
      setInput('');
    } else {
      alert("서버와의 WebSocket 연결이 끊겼습니다.");
    }
  };

  const scrollToBottom = () => {
    if (messageEndRef.current) {
      messageEndRef.current.scrollIntoView({
        behavior: 'auto',  // ✅ 부드럽게가 아닌 즉시 이동
        block: 'nearest',  // ✅ 화면 중앙이나 상단으로 튀는 걸 방지
        inline: 'nearest'  // ✅ 좌우도 자동 정렬 방지
      });
    }
  };

  

  const leaveChatRoom = async () => {
  if (!roomId) return;

  const confirmLeave = window.confirm("정말 이 채팅방에서 나가시겠습니까?");
  if (!confirmLeave) return;

  try {
    // 1. WebSocket 연결 해제
    if (stompClientRef.current) {
      stompClientRef.current.deactivate();
      stompClientRef.current = null;
      console.log("🔌 WebSocket 연결 해제됨");
    }

    // 2. 서버에 나가기 요청 (POST 요청으로 구현)
    await axios.post(`http://localhost/foodhub/admin/chat/private/delete/${roomId}`, {}, {
      headers: {
        Authorization: `Bearer ${accessToken}`
      },
      withCredentials: true
    });

    // 3. 상태 초기화
    setRoomId(null);
    setSelectedUser(null);
    setSelectedUserId(null);
    setMessages([]);

    // 4. 채팅방 목록 갱신
    await fetchChatRoom();

  } catch (error) {
    console.error("❌ 채팅방 나가기 실패:", error);
    alert("채팅방을 나가는 데 실패했습니다.");
  }
};

  
  useEffect(() => {
    fetchChatRoom();
  },[]);

  useEffect(() => {
    scrollToBottom();
  }, [messages]);

  return (
    <div className="chat-container">
      <aside className="chat-users">
        <h4>👥 사용자 목록</h4>
          {/* ✅ 닉네임 검색/생성 */}
          <div className="create-chat-box">
            <input
              type="text"
              placeholder="닉네임으로 사용자 검색"
              value={nicknameInput}
              onChange={(e) => setNicknameInput(e.target.value)}
            />
            <button onClick={createPrivateChatRoom}>채팅 생성</button>
          </div>
        <ul>
          {userList.map((user) => (
            <li
              key={user.roomId}
              className={user.otherUserNickname === selectedUser ? 'active' : ''}
              onClick={() => handleUserClick(user)}
            >
              {user.otherUserNickname}
            </li>
          ))}
        </ul>
      </aside>

      <section className="chat-main">
      <header className="chat-header">
  <div className="chat-title-area">
    <h4>{selectedUser ? `${selectedUser} 님과의 채팅` : '채팅 상대를 선택하세요'}</h4>
    {selectedUser && (
      <button className="leave-chat-btn" onClick={leaveChatRoom}>채팅방 나가기</button>
    )}
  </div>
</header>


        <div className="chat-messages">
          {messages.map((msg, i) => (
            <div
              key={i}
              className={`chat-message ${msg.from === 'admin' ? 'me' : 'you'}`}
            >
              <div className="sender">{msg.from === 'admin' ? '관리자' : selectedUser}</div>
              <div className="text">{msg.text}</div>
            </div>
          ))}
          <div ref={messageEndRef} />
        </div>

        <div className="chat-input">
          <input
            type="text"
            placeholder="메시지를 입력하세요..."
            value={input}
            onChange={(e) => setInput(e.target.value)}
            onKeyDown={(e) => e.key === 'Enter' && sendMessage()}
          />
          <button onClick={sendMessage}>전송</button>
        </div>
      </section>
    </div>
  );
};

export default AdminChat;
