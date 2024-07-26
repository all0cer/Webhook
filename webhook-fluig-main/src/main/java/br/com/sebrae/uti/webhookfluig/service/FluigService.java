package br.com.sebrae.uti.webhookfluig.service;

import com.squareup.okhttp.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class FluigService {
    OkHttpClient client;
    MediaType mediaType;
    private final String DATASEARCH_URI = "https://fluighml.rn.sebrae.com.br/fluighub/rest/service/execute/datasearch";

    public boolean paymentStatusEqualTwo (Map<String, String> body) {
        int payment_status = Integer.parseInt(body.get("payment_status"));
        return payment_status == 2;
    }

    public Response notifyFluig (String bodyString) {
        String txid = extractTxidFromJson(bodyString);
        getResponseDataSearch(txid);

	    return null;
    }

    private String extractTxidFromJson(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            JSONArray pixArray = jsonObject.getJSONArray("pix");

            if (pixArray.length() > 0) {
                JSONObject pixObject = pixArray.getJSONObject(0);

                return pixObject.getString("txid");
            } else {
                return "Array 'pix' está vazio";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao processar o JSON";
        }
    }

    private void checkValuesInResponseDatasearch(String cleaneredMessage) {
        try {

            JSONObject messageValueString = new JSONObject(cleaneredMessage);

            JSONArray valuesArray = messageValueString.getJSONArray("values");


            if (valuesArray == null || valuesArray.length() == 0) {
                System.out.println("O campo 'values' está vazio.");
            } else {
                System.out.println("O campo 'values' contém dados.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getResponseDataSearch(String txid) {
        try {
            JSONObject requestBodyJson = new JSONObject();
            requestBodyJson.put("endpoint", "dataset");
            requestBodyJson.put("method", "get");
            requestBodyJson.put("params", "datasetId=dsPagamentoWebhook&constraintsField=txId&constraintsInitialValue=" + txid + "&constraintsField=processId&constraintsInitialValue=pix");
            RequestBody requestBody = RequestBody.create(mediaType, txid);
            Request request = new Request.Builder()
                    .url(DATASEARCH_URI)
                    .post(requestBody)
                    .build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();

               extractMessageFromResponse(responseBody);

            } else {
                throw new IOException("Unexpected code " + response);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
	        throw new RuntimeException(e);
        }
    }

    private String extractMessageFromResponse(String responseBody) {
        try {
            org.json.JSONObject jsonResponse = new org.json.JSONObject(responseBody);
            return jsonResponse.getString("message");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Not found message";
    }
}

