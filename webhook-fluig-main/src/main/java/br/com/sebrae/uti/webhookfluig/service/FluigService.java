package br.com.sebrae.uti.webhookfluig.service;


import lombok.Getter;
import lombok.Setter;
import okhttp3.*;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class FluigService {
    private static final OkHttpClient client = new OkHttpClient();
    MediaType mediaType;
    @Getter
    @Setter
    private String paymentId;

    Logger logger = Logger.getLogger(FluigService.class);

	public FluigService() {
        this.mediaType = MediaType.parse("application/json; charset=utf-8");
    }

    public boolean paymentStatusEqualTwo (Map<String, String> body) {
        int payment_status = Integer.parseInt(body.get("payment_status"));
        return payment_status == 2;
    }

    public void notifyFluig (String bodyString, String paymentAutority) throws IOException {
        if (paymentAutority.equals("BancoDoBrasil")) {
            setPaymentId(extractTxidFromJson(bodyString));
        }
        else if (paymentAutority.equals("Cielo")) {
                setPaymentId(extractProductIdFromString(bodyString));
        }
        else {
            throw new IllegalArgumentException("Invalid payment autority: " + paymentAutority);
        }

        if (getPaymentId() != null) {
            moveProcessDataset(paymentId, paymentAutority);
        }
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

    private String extractProductIdFromString(String body) {
        if (body == null || body.isEmpty()) {
            throw new IllegalArgumentException("Input body cannot be null or empty");
        }
        String regex = "\"order_number\":\"([^\"]+)\"";
        Pattern pattern = java.util.regex.Pattern.compile(regex);
        Matcher matcher = pattern.matcher(body);

        if (matcher.find()) {
            return matcher.group(1); // Retorna o grupo capturado
        } else {
            throw new IllegalArgumentException("order_number not found in the input string");
        }
    }

    private void moveProcessDataset(String txid, String paymentType) throws IOException {
        Response response = null;
        try {
            String jsonBody = "{\"endpoint\":\"dataset\",\"method\":\"get\",\"params\":\"datasetId=dsPagamentoWebhook&constraintsField=txId&constraintsInitialValue=" + txid + "&constraintsField=processId&constraintsInitialValue="+ paymentType +"\"}";
            RequestBody requestBody = RequestBody.create(mediaType, jsonBody);
	        String datasearchUri = "https://fluighml.rn.sebrae.com.br/fluighub/rest/service/execute/datasearch";
	        Request request = new Request.Builder()
                    .addHeader("Content-Type", "application/json")
                    .url(datasearchUri)
                    .post(requestBody)
                    .build();
            System.out.println(jsonBody);
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                logger.info("Processo enviado para o dataset");
                }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
	        throw new RuntimeException(e);
        }finally {
            if (response != null) {
               response.body().close();
            }
        }
    }

}

