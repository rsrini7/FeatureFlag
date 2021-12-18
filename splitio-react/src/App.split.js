import React, { useState } from 'react';
import Login from './login/Login';
import SplitAccountListApp from './account/AccountListApp.split';

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
      {!login && <SplitAccountListApp accountId={accountId} handleClick={logout}/>}
    </div>
  )

}
