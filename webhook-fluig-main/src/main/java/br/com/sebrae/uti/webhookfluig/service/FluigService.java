package br.com.sebrae.uti.webhookfluig.service;


import lombok.Getter;
import lombok.Setter;
import okhttp3.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    Logger logger = LogManager.getLogger(FluigService.class);

	public FluigService() {
        this.mediaType = MediaType.parse("application/json; charset=utf-8");
    }

    public boolean paymentStatusEqualTwo (Map<String, String> body) {
        return "2".equals(body.get("payment_status"));
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

        if (matcher.find() && !matcher.group(1).isEmpty()) {
            return matcher.group(1);
        } else {
            throw new IllegalArgumentException("order_number not found in the input string");
        }
    }

    private void moveProcessDataset(String txid, String paymentType) throws IOException {
        Response response = null;
        try {
            String jsonBody = "{\"endpoint\":\"dataset\",\"method\":\"get\",\"params\":\"datasetId=dsPagamentoWebhook&constraintsField=txId&constraintsInitialValue=" + txid + "&constraintsField=processId&constraintsInitialValue="+ paymentType +"\"}";
            RequestBody requestBody = RequestBody.create(jsonBody, mediaType);
	        String datasearchUri = "https://fluighml.rn.sebrae.com.br/fluighub/rest/service/execute/datasearch";
	        Request request = new Request.Builder()
                    .addHeader("Content-Type", "application/json")
                    .url(datasearchUri)
                    .post(requestBody)
                    .build();
            response = client.newCall(request).execute();
            logger.info(response.body().string());
            if (response.code() != 200) {
                logger.error("Erro ao enviar o processo para o dataset");
                throw new IOException("Erro ao enviar o processo para o dataset");
                }
            else {
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

