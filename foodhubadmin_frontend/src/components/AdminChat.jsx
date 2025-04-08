import { useState } from 'react';
import '../assets/css/adminChat.css';

const dummyUsers = ['user1', 'user2', 'user3'];
const dummyMessages = [
  { from: 'user1', text: '안녕하세요!' },
  { from: 'admin', text: '안녕하세요. 무엇을 도와드릴까요?' },
  { from: 'user1', text: '문의가 있어서요.' },
];

const AdminChat = () => {
  const [selectedUser, setSelectedUser] = useState('user1');
  const [messages, setMessages] = useState(dummyMessages);
  const [input, setInput] = useState('');

  const sendMessage = () => {
    if (input.trim()) {
      setMessages([...messages, { from: 'admin', text: input }]);
      setInput('');
    }
  };

  return (
    <div className="chat-container">
      {/* 좌측: 유저 목록 */}
      <aside className="chat-users">
        <h4>👥 사용자 목록</h4>
        <ul>
          {dummyUsers.map((user) => (
            <li
              key={user}
              className={user === selectedUser ? 'active' : ''}
              onClick={() => setSelectedUser(user)}
            >
              {user}
            </li>
          ))}
        </ul>
      </aside>

      {/* 우측: 채팅 내용 */}
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
              <div className="sender">{msg.from === 'admin' ? '관리자' : msg.from}</div>
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
