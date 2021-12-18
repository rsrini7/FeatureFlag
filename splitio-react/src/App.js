import React, { useState } from 'react';
import Login from './login/Login'
import AccountListApp from './account/AccountListApp';

export default function App() {

  const [login, setLogin] = useState(true);
  const [accountId, setAccountId] = useState('');

  const getAccountId = (accountId) => {
    setAccountId(accountId);
    setLogin(false);
  }

  const logout = () => {
    setLogin(true);
  }

  return (
    <div>
      {login && <Login handleClick={getAccountId}/>}
      {!login && <AccountListApp accountId={accountId} handleClick={logout}/>}
    </div>
  )
}
