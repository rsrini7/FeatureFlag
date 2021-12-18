import React, { useState } from 'react';
import Login from './login/Login';
import SplitAccountListApp from './account/AccountListApp.split';

export default function App() {

  const [login, setLogin] = useState(true);
  const [email, setEmail] = useState('');

  const getEmail = (email) => {
    setEmail(email);
    setLogin(false);
  }

  const logout = () => {
    setLogin(true);
  }

  return (
    <div>
      {login && <Login handleClick={getEmail}/>}
      {!login && <SplitAccountListApp email={email} handleClick={logout}/>}
    </div>
  )

}
