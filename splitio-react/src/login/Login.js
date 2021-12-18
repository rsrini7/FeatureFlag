import React, { useState } from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import "./Login.css";

export default function Login(props) {
    const [accountId, setAccountId] = useState("");
    const [password, setPassword] = useState("");

    function validateForm() {
        return accountId.length > 0 && password.length > 0;
    }

    function handleSubmit(event) {
        event.preventDefault();
        props.handleClick(accountId);
    }

    return (
        <div className="Login">
            <Form onSubmit={handleSubmit}>
                <Form.Group size="lg" controlId="accountId">
                    <Form.Label>Account ID</Form.Label>
                    <Form.Control
                        autoFocus
                        type="text"
                        value={accountId}
                        onChange={(e) => setAccountId(e.target.value)}
                    />
                </Form.Group>
                <Form.Group size="lg" controlId="password">
                    <Form.Label>Password</Form.Label>
                    <Form.Control
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </Form.Group>
                <Button block size="lg" type="submit" disabled={!validateForm()}>
                    Login
                </Button>
            </Form>
        </div>
    );
}