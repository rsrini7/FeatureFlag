export const appConfig = {
    split: {
        authorizationKey: '4gsdh8sv4td9t07ce041l9c6jmfbs6f90mos', // update to real authorization key1
        //authorizationKey: 'localhost',
        treatmentName: 'account_filter',
        intlTreatment: 'INTERNATIONAL',
        usaTreatment: 'USA'
    },
    localFeatures:{
            account_filter: {treatment: "INTERNATIONAL"}
    },
    backend: {
        baseUrl: 'http://localhost:8080',
        accountsUri: '/api/v1/accounts'
    }
}