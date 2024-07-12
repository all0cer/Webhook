package br.com.sebrae.uti.webhookfluig.service;

import com.squareup.okhttp.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class FluigService {
    OkHttpClient client;
    MediaType mediaType;
    RequestBody body;
    Request request;
    Response response;

    public boolean paymentStatusEqualTwo (Map<String, String> body) {
        int payment_status = Integer.parseInt(body.get("payment_status"));
        return payment_status == 2;
    }

    public Response notifyFluig (String url, String bodyString) {
        client = new OkHttpClient();
        mediaType = MediaType.parse("application/json");
        body = RequestBody.create(mediaType, bodyString);
        request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
                {
                    try {
                        response = client.newCall(request).execute();
                        System.out.println(response);
                        if (response.body() != null)
                            response.body().close();
                        return response;
                    } catch (IOException e) {
                        throw new RuntimeException("Error: " + e.getMessage(), e);
                    }
                }
    }
}
