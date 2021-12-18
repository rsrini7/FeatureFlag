package com.example.demo;

import com.nimbusds.jose.shaded.json.JSONArray;
import com.nimbusds.jose.shaded.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.split.client.SplitClient;
import io.split.client.api.SplitResult;
import split.com.google.gson.Gson;

@RestController
public class AccountController {

    Logger logger = LoggerFactory.getLogger(DemoController.class);

    SplitClient splitClient;

    Gson gson;

    public AccountController(SplitClient splitClient) {
        this.splitClient = splitClient;
        this.gson = new Gson();
    }

    // treatment name pulled from application.properties
    @Value("#{ @environment['split.api.treatement-name-ui'] }")
    private String treatmentName;

    @GetMapping("/api/v1/accounts/{accountId}")
    public ResponseEntity<JSONObject> getAccountaccounts(@PathVariable String accountId) {

        SplitResult result = splitClient.getTreatmentWithConfig(accountId, treatmentName);
        String treatment = result.treatment();
        JSONArray jsonArray = new JSONArray();
        JSONObject returnJsonObject = new JSONObject();

        logger.info("treatment: {}", treatment);

        if (treatment != null) {

            if (treatment.equals("INTERNATIONAL")) {
                jsonArray.add(getUSAAccount());
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("accountNo", "InternationalAcc001");
                jsonObject.put("name", "Srini");
                jsonObject.put("bal", 700);
                jsonObject.put("country", "INDIA");
                jsonArray.add(jsonObject);

            } else if (treatment.equals("USA")) {
                jsonArray.add(getUSAAccount());

            } else {
                throw new IllegalStateException("Invalid treatment: " + treatment);
            }
        }

        returnJsonObject.put("accounts", jsonArray.toString());

        return ResponseEntity.ok(returnJsonObject);
    }

    private JSONObject getUSAAccount() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("accountNo", "USAAcc001");
        jsonObject.put("name", "Name1");
        jsonObject.put("bal", 300);
        jsonObject.put("country", "USA");
        return jsonObject;
    }
}
