package br.com.sebrae.uti.webhookfluig.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BB {
    private List<Pix> pix;

    @Override
    public String toString() {
        return "{" +
                "\"pix\":" + pix +
                '}';
    }
}
