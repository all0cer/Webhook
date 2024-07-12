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
    private String endToEndId;
    private String txid;
    private String valor;
    private String chave;
    private String horario;
    private String infoPagador;

    @Override
    public String toString() {
        return "{" +
                "\"endToEndId\":\"" + endToEndId + '\"' +
                ", \"txid\":\"" + txid + '\"' +
                ", \"valor\":\"" + valor + '\"' +
                ", \"chave:\"" + chave + '\"' +
                ", \"horario\":\"" + horario + '\"' +
                ", \"infoPagador\":\"" + infoPagador + '\"' +
                '}';
    }
}
