import React, { useState, useEffect } from 'react';
import { SplitFactory, SplitTreatments } from '@splitsoftware/splitio-react';
import { appConfig } from '../app.config';
import AccountList from './AccountList'
import axios from 'axios';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import './AccountList.css';

export default function SplitAccountListApp(props) {

    // Filter that accepts only USA accounts
    const usaAccountsFilter = (account) => account.country === appConfig.split.usaTreatment;

    // Filter that accepts all accounts
    const allAccountsFilter = () => true;

    function handleSubmit(event) {
        event.preventDefault();
        props.handleClick();
    }

    const splitFactoryConfig = {
        core: {
            authorizationKey: appConfig.split.authorizationKey,
            key: props.email,
        }
    }

    const [useAllFilter, setUseAllFilter] = useState(false);
    const [accounts, setAccounts] = useState([]);

    useEffect(() => {
        getAccounts(props.email);
    }, []);

    const getAccounts = async () => {
        const response = await axios.get(
            appConfig.backend.baseUrl + appConfig.backend.accountsUri + props.email
        );
        await setAccounts(response.data.accounts);
    };

    return (
        <SplitFactory config={splitFactoryConfig} updateOnSdkUpdate={true} >
            <SplitTreatments /* names: list of features to evaluate */ names={[appConfig.split.treatmentName]} >{
                ({ isReady, treatments }) => {
                    if (isReady) {
                        // once the SDK is ready, `treatments` contains valid values of the evaluated list of features

                        let treatment = treatments[appConfig.split.treatmentName].treatment;
                        console.log(`treatment: ${treatment}`);

                        let filter;
                        if (treatment === appConfig.split.intlTreatment && useAllFilter) {
                            filter = allAccountsFilter;
                        } else {
                            filter = usaAccountsFilter;
                            setUseAllFilter(false);
                        }

                        const filteredAccounts = accounts.filter(filter);
                        return (
                            <div className="AccountList">
                                <h2>Hello {props.email}</h2>
                                {treatment === appConfig.split.intlTreatment &&
                                    <div>
                                        <label><input
                                            type="checkbox"
                                            checked={useAllFilter}
                                            onChange={() => { setUseAllFilter(!useAllFilter) }}
                                        /> Show International Accounts</label>
                                    </div>
                                }
                                <AccountList accounts={filteredAccounts} />
                                <Form onSubmit={handleSubmit}>
                                    <Button block size="lg" type="submit">
                                        Logout
                                    </Button>
                                </Form>
                            </div>
                        );
                    }

                    return (<div>Loading SDK...</div>);
                }
            }</SplitTreatments>
        </SplitFactory>
    )
}
