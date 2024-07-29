package br.com.sebrae.uti.webhookfluig.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cielo {

    @Id
    @JsonProperty("checkout_cielo_order_number")
    private String checkoutCieloOrderNumber;
    private BigDecimal amount;
    private String order_number;
    private String created_date;
    private String customer_name;
    private String customer_identity;
    private int payment_method_type;
    private String payment_boletonumber;
    private int payment_status;
    private String tid;
    private boolean test_transaction;
    private String product_id;
    private String product_type;
    private String product_expiration_date;

    @Override
    public String toString() {
        return "{" +
                "\"checkoutCieloOrderNumber\":\"" + checkoutCieloOrderNumber + '\"' +
                ", \"tid\":\"" + tid + '\"' +
                ", \"amount\":\"" + amount + '\"' +
                ", \"payment_status:\"" + payment_status + '\"' +
                ", \"customer_name:\"" + customer_name + '\"' +
                ", \"customer_identity\":\"" + customer_identity + '\"' +
                ", \"test_transaction\":\"" + test_transaction + '\"' +
                ", \"product_id\":\"" + product_id + '\"' +
                ", \"product_type\":\"" + product_type + '\"' +
                ", \"product_expiration_date\":\"" + product_expiration_date + '\"' +
                '}';
    }
}
