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

  const adminId = sessionStorage.getItem('userId');
  const adminNickname = sessionStorage.getItem('nickname');
  const accessToken = localStorage.getItem('token');

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
  

  useEffect(() => {
    fetchChatRoom();
  }, []);

  return (
    <div className="chat-container">
      <aside className="chat-users">
        <h4>👥 사용자 목록</h4>
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
          <h4>{selectedUser ? `${selectedUser} 님과의 채팅` : '채팅 상대를 선택하세요'}</h4>
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
