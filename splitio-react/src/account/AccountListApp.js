import React from 'react';
import { accounts } from './accounts'
import AccountList from './AccountList'
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import './AccountList.css';

export default function AccountListApp(props) {

    function handleSubmit(event) {
        event.preventDefault();
        props.handleClick();
    }

    return (
        <div className="AccountList">
            <h2>Hello {props.email}</h2>
            <AccountList accounts={accounts} />
            <Form onSubmit={handleSubmit}>
                <Button block size="lg" type="submit">Logout</Button>
            </Form>
        </div>
    )
}
