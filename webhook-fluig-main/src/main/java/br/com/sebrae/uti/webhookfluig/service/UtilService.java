package br.com.sebrae.uti.webhookfluig.service;

import br.com.sebrae.uti.webhookfluig.model.Cielo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Service
public class UtilService {
    @Autowired
    CieloService cieloService;
    Logger logger = Logger.getLogger(UtilService.class);
    public void printRequestInfo (HttpServletRequest request, String tipo) {
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
        Date resultDate = new Date(System.currentTimeMillis());
        logger.info("Request " + tipo + " - Endpoint " + request.getRequestURI() + " - Hora " + sdf.format(resultDate));
    }

    public Cielo parseBody (Map<String, String> body) {
        return Cielo.builder()
                .checkoutCieloOrderNumber(body.get("checkout_cielo_order_number"))
                .amount(BigDecimal.valueOf(Double.parseDouble(body.get("amount"))))
                .order_number(body.get("order_number"))
                .created_date(body.get("created_date"))
                .customer_name(body.get("customer_name"))
                .customer_identity(body.get("customer_identity"))
                .payment_method_type(Integer.parseInt(body.get("payment_method_type")))
                .payment_boletonumber(body.get("payment_boletonumber"))
                .payment_status(Integer.parseInt(body.get("payment_status")))
                .tid(body.get("tid"))
                .test_transaction(Boolean.parseBoolean(body.get("test_transaction")))
                .product_id(body.get("product_id"))
                .product_type(body.get("product_type"))
                .product_expiration_date(body.get("product_expiration_date"))
                .build();
    }
}
