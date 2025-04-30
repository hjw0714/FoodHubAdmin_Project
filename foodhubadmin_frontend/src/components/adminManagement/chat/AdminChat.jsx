import { useEffect, useRef, useState } from 'react';
import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';
import '../../../assets/css/adminChat.css';
import axios from 'axios';

const decodeJwtPayload = (token) => {
  try {
    const base64Payload = token.split('.')[1];
    const decodedPayload = JSON.parse(atob(base64Payload));
    return decodedPayload;
  } catch (error) {
    console.error('❌ JWT 디코딩 실패:', error);
    return null;
  }
};

const AdminChat = () => {
  const [userList, setUserList] = useState([]);
  const [selectedUser, setSelectedUser] = useState(null);
  const [selectedUserId, setSelectedUserId] = useState(null);
  const [roomId, setRoomId] = useState(null);
  const [messages, setMessages] = useState([]);
  const [input, setInput] = useState('');
  const [nicknameInput, setNicknameInput] = useState('');
  const stompClientRef = useRef(null);
  const messageEndRef = useRef(null);

  const accessToken = localStorage.getItem('token');
  const decoded = decodeJwtPayload(accessToken);
  const adminId = decoded?.sub;
  const adminNickname = decodeURIComponent(decoded?.nickname);

  const createPrivateChatRoom = async () => {
    const nickname = nicknameInput.trim();
    if (!nickname) return alert("닉네임을 입력해주세요.");

    try {
      const response = await axios.post(
        'http://3.39.94.251/foodhub/admin/chat/private/create',
        { nickname },
        {
          headers: { Authorization: `Bearer ${accessToken}` },
          withCredentials: true
        }
      );

      const newRoom = response.data;
      if (!newRoom?.roomId) throw new Error("채팅방 생성 실패");

      setSelectedUser(newRoom.otherUserNickname);
      setSelectedUserId(newRoom.otherUserId);
      setRoomId(newRoom.roomId);
      setMessages([]);

      const messagesResponse = await axios.get(
        `http://3.39.94.251/foodhub/admin/chat/private/messages/${newRoom.roomId}`,
        {
          headers: { Authorization: `Bearer ${accessToken}` },
          withCredentials: true
        }
      );

      setMessages(
        messagesResponse.data.map(m => ({
          from: m.senderId === adminId ? 'admin' : 'you',
          text: m.chatContent,
          senderNickname: m.sender
        }))
      );

      connectToPrivateRoom(newRoom.roomId);
      setNicknameInput('');
      await fetchChatRoom();

    } catch (err) {
      console.error("❌ 채팅방 생성 실패:", err);
      alert(err.response?.data?.message || "오류 발생");
    }
  };

  const fetchChatRoom = async () => {
    try {
      const response = await axios.get('http://3.39.94.251/foodhub/admin/chat/private/list', {
        headers: { Authorization: `Bearer ${accessToken}` },
        withCredentials: true
      });

      setUserList(response.data);
    } catch (error) {
      console.error('⚠️ 채팅방 목록 요청 실패:', error);
      alert("채팅방 목록을 불러오지 못했습니다.");
    }
  };

  const handleUserClick = async (user) => {
    try {
      setSelectedUser(user.otherUserNickname);
      setSelectedUserId(user.otherUserId);
      setRoomId(user.roomId);

      const response = await axios.get(`http://3.39.94.251/foodhub/admin/chat/private/messages/${user.roomId}`, {
        headers: { Authorization: `Bearer ${accessToken}` },
        withCredentials: true
      });

      const data = response.data;
      if (!Array.isArray(data)) throw new Error("응답 형식 오류");

      setMessages(
        data.map(m => ({
          from: m.senderId === adminId ? 'admin' : 'you',
          text: m.chatContent,
          senderNickname: m.sender
        }))
      );

      connectToPrivateRoom(user.roomId);
    } catch (error) {
      console.error("❌ 채팅 메시지 불러오기 실패:", error);
      alert("메시지를 불러오지 못했습니다.");
    }
  };

  const connectToPrivateRoom = (roomId) => {
    if (stompClientRef.current) {
      stompClientRef.current.deactivate();
      stompClientRef.current = null;
    }

    const socket = new SockJS('http://3.39.94.251/ws');
    const client = new Client({
      webSocketFactory: () => socket,
      onConnect: () => {
        client.subscribe(`/topic/private.${roomId}`, (payload) => {
          const message = JSON.parse(payload.body);
          if (message.type === 'LEAVE' && message.sender === 'SYSTEM') {
            alert(message.content);
            return;
          }

          setMessages(prev => [
            ...prev,
            {
              from: message.sender === adminId ? 'admin' : 'you',
              text: message.content,
              senderNickname: message.senderNickname
            }
          ]);
        });
      },
      onStompError: (frame) => console.error('🔴 STOMP 오류:', frame)
    });

    client.activate();
    stompClientRef.current = client;
  };

  const sendMessage = async () => {
    if (!input.trim()) return;

    const sender = adminId;
    const senderNickname = adminNickname;
    const receiver = selectedUserId;

    if (!sender || !senderNickname || !receiver) {
      alert("로그인 또는 대상 정보가 없습니다.");
      return;
    }

    const message = {
      sender,
      receiver,
      senderNickname,
      content: input,
      type: 'CHAT'
    };

    if (stompClientRef.current && stompClientRef.current.connected) {
      stompClientRef.current.publish({
        destination: `/app/chat.private.${roomId}`,
        body: JSON.stringify(message)
      });
      setInput('');
    } else {
      alert("서버와의 WebSocket 연결이 끊겼습니다.");
    }
  };

  const leaveChatRoom = async () => {
    if (!roomId) return;
    const confirmLeave = window.confirm("정말 이 채팅방에서 나가시겠습니까?");
    if (!confirmLeave) return;

    try {
      if (stompClientRef.current) {
        stompClientRef.current.deactivate();
        stompClientRef.current = null;
      }

      await axios.post(`http://3.39.94.251/admin/chat/private/delete/${roomId}`, {}, {
        headers: { Authorization: `Bearer ${accessToken}` },
        withCredentials: true
      });

      setRoomId(null);
      setSelectedUser(null);
      setSelectedUserId(null);
      setMessages([]);
      await fetchChatRoom();
    } catch (error) {
      console.error("❌ 채팅방 나가기 실패:", error);
      alert("채팅방 나가기 실패");
    }
  };

  const scrollToBottom = () => {
    if (messageEndRef.current) {
      messageEndRef.current.scrollIntoView({
        behavior: 'auto',
        block: 'nearest',
        inline: 'nearest'
      });
    }
  };

  useEffect(() => {
    fetchChatRoom();
  }, []);

  useEffect(() => {
    scrollToBottom();
  }, [messages]);

  return (
    <div className="chat-container">
      <aside className="chat-users">
        <h4>👥 사용자 목록</h4>
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
            {selectedUser && <button className="leave-chat-btn" onClick={leaveChatRoom}>채팅방 나가기</button>}
          </div>
        </header>

        <div className="chat-messages">
          {messages.map((msg, i) => (
            <div key={i} className={`chat-message ${msg.from === 'admin' ? 'me' : 'you'}`}>
              <div className="sender">{msg.senderNickname || (msg.from === 'admin' ? adminNickname : selectedUser)}</div>
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
