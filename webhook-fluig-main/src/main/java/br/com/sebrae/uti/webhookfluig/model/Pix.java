package br.com.sebrae.uti.webhookfluig.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Pix {
    @Id
    private String txid;

    @Override
    public String toString() {
        return "{" +
                "\"endToEndId\":\"" +  '\"' +
                ", \"txid\":\"" + txid + '\"' +
                ", \"valor\":\"" +   '\"' +
                ", \"chave:\"" +   '\"' +
                ", \"horario\":\"" + '\"' +
                ", \"infoPagador\":\"" +  '\"' +
                '}';
    }
}
