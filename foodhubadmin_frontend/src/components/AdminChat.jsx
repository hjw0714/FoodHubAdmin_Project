import { useEffect, useRef, useState } from 'react';
import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';
import '../assets/css/adminChat.css';

const AdminChat = () => {
  const [userList, setUserList] = useState([]);
  const [selectedUser, setSelectedUser] = useState(null);
  const [roomId, setRoomId] = useState(null);
  const [messages, setMessages] = useState([]);
  const [input, setInput] = useState('');
  const stompClientRef = useRef(null);

  // 로그인된 관리자의 userId와 nickname을 세션에서 가져옴
  const adminId = sessionStorage.getItem('userId');
  const adminNickname = sessionStorage.getItem('nickname');

  useEffect(() => {
    fetch('/foodhub/chat/private/list', { credentials: 'include' })
      .then(res => res.json())
      .then(data => setUserList(data))
      .catch(err => console.error(err));
  }, []);

  const handleUserClick = (user) => {
    setSelectedUser(user.otherUserNickname);
    setRoomId(user.roomId);

    fetch(`/foodhub/chat/private/messages/${user.roomId}`)
      .then(res => res.json())
      .then(data => {
        setMessages(data.map(m => ({
          from: m.senderId === adminId ? 'admin' : 'you',
          text: m.chatContent,
        })));
      });

    connectToPrivateRoom(user.roomId);
  };

  const connectToPrivateRoom = (roomId) => {
    const socket = new SockJS('/ws');
    const client = new Client({
      webSocketFactory: () => socket,
      onConnect: () => {
        client.subscribe(`/topic/private.${roomId}`, (payload) => {
          const message = JSON.parse(payload.body);
          setMessages(prev => [...prev, {
            from: message.sender === adminId ? 'admin' : 'you',
            text: message.content
          }]);
        });
      },
    });
    client.activate();
    stompClientRef.current = client;
  };

  const sendMessage = () => {
    if (!input.trim() || !stompClientRef.current) return;

    const message = {
      sender: adminId,
      senderNickname: adminNickname,
      content: input,
      type: 'CHAT'
    };

    stompClientRef.current.publish({
      destination: `/app/chat.private.${roomId}`,
      body: JSON.stringify(message)
    });

    setMessages([...messages, { from: 'admin', text: input }]);
    setInput('');
  };

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
          <h4>{selectedUser} 님과의 채팅</h4>
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
