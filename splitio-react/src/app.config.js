export const appConfig = {
    split: {
        authorizationKey: 'b1o9jf27ur07abhpncot1u56lqvj1tph8tkk', // update to real authorization key1
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