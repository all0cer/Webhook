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
    private String paymentId;
    private final String DATASEARCH_URI = "https://fluighml.rn.sebrae.com.br/fluighub/rest/service/execute/datasearch";

    public FluigService() {
        this.client = new OkHttpClient();
        this.mediaType = MediaType.parse("application/json; charset=utf-8");
    }

    public boolean paymentStatusEqualTwo (Map<String, String> body) {
        int payment_status = Integer.parseInt(body.get("payment_status"));
        return payment_status == 2;
    }

    public Response notifyFluig (String bodyString, String paymentAutority) {
        if (paymentAutority.equals("BancoDoBrasil")) {
            setPaymentId(extractTxidFromJson(bodyString));
        } else {
            if (paymentAutority.equals("Cielo")) {
                setPaymentId(extractProductIdFromJson(bodyString));
            }
        }

	    moveProcessDataset(paymentId, paymentAutority);

	    return null;
    }

    private String extractTxidFromJson(String jsonString) {
        try {
            System.out.println("Json: " + jsonString);
            JSONObject jsonObject = new JSONObject(jsonString);

            JSONArray pixArray = jsonObject.getJSONArray("pix");

            if (!pixArray.isEmpty()) {
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

    private String extractProductIdFromJson(String jsonString) {
        try {
            System.out.println("Json: " + jsonString);
            JSONObject jsonObject = new JSONObject(jsonString);

            // Extrair o valor do campo "product_id"
            if (jsonObject.has("product_id")) {
                return jsonObject.getString("product_id");
            } else {
                return "Campo 'product_id' não encontrado";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao processar o JSON";
        }
    }

    private void moveProcessDataset(String txid, String paymentType) {
        try {
            String jsonBody = "{\"endpoint\":\"dataset\",\"method\":\"get\",\"params\":\"datasetId=dsPagamentoWebhook&constraintsField=txId&constraintsInitialValue=" + txid + "&constraintsField=processId&constraintsInitialValue="+ paymentType +"\"}";
            System.out.println("RequestBodyJson: " + jsonBody);
            RequestBody requestBody = RequestBody.create(mediaType, jsonBody);
            Request request = new Request.Builder()
                    .addHeader("Content-Type", "application/json")
                    .url(DATASEARCH_URI)
                    .post(requestBody)
                    .build();
            System.out.println("Request: " + request);
            Response response = client.newCall(request).execute();
            System.out.println("Response: " + response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
	        throw new RuntimeException(e);
        }
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
}

