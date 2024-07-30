package br.com.sebrae.uti.webhookfluig.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;





@Getter
@Setter
@NoArgsConstructor
public class Pix {

    private String txid;

    @Override
    public String toString() {
        return "{" +
                "\"endToEndId\":\"" +  '\"' +
                ", \"txid\":\"" + txid + '\"' +
                ", \"valor\":\"" +   '\"' +
                ", \"chave\":\"" + '\"' +
                ", \"horario\":\"" + '\"' +
                ", \"infoPagador\":\"" +  '\"' +
                '}';
    }
}
