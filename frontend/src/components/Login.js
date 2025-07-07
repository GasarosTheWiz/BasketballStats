import React, { useState } from 'react';
import axios from 'axios';
import '../App.css';

function Login({ onLogin }) {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [showRegister, setShowRegister] = useState(false);

  //login
  const handleSubmit = (e) => {
    e.preventDefault();
    axios.get(`http://localhost:8080/users/check`, { params: { username, password } })
      .then(res => {
        if (res.data.valid) {
          onLogin(); // call parent onLogin if elements are valid
        } else {
          alert('Invalid.');
        }
      })
      .catch(() => alert('Error connecting to server'));
  };

  //register
  const handleRegister = (e) => {
    e.preventDefault();
    axios.post("http://localhost:8080/users", { username, password })
      .then(() => {
        alert(`User ${username} registered!`);
        setShowRegister(false);
      })
      .catch(err => {
        console.error(err);
        alert('Registration failed');
      });
  };

  return (
    <div className="login-container">
      <video autoPlay loop muted playsInline className="background-video">
        <source src="/assets/background.mp4" type="video/mp4" />
      </video>
      <div className="login-panel">
        <h2>Login</h2>
        <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '15px' }}>
          <input
            type="text"
            placeholder="Username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
          <button type="submit">Login</button>
          <button type="button" onClick={() => setShowRegister(true)}>Register</button>
        </form>
      </div>
      {showRegister && (
        <div className="register-modal">
          <div className="register-content">
            <h3>Register</h3>
            <form onSubmit={handleRegister} style={{ display: 'flex', flexDirection: 'column', gap: '15px' }}>
              <input
                type="text"
                placeholder="New Username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                required
              />
              <input
                type="password"
                placeholder="New Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
              <button type="submit">Register</button>
              <button type="button" onClick={() => setShowRegister(false)}>Cancel</button>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}

export default Login;
