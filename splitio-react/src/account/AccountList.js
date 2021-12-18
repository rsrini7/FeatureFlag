import React from 'react';
import Table from "react-bootstrap/Table";
import { AccountItem } from './AccountItem.js';

export default function AccountList({ accounts }) {
  const accountList = accounts.map(account => (<AccountItem {...account} key={Math.random()}/>))
  
  return (
    <div>
      <Table striped bordered hover>
        <thead>
          <tr>
            <th>Account No</th>
            <th>Name</th>
            <th>Balance</th>
            <th>Country</th>
          </tr>
        </thead>
        <tbody>
          {accountList}
        </tbody>
      </Table>
    </div>
  )
}
