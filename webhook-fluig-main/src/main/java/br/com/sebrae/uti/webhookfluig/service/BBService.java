package br.com.sebrae.uti.webhookfluig.service;

import br.com.sebrae.uti.webhookfluig.model.BB;
import br.com.sebrae.uti.webhookfluig.model.Pix;
import br.com.sebrae.uti.webhookfluig.repository.BBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BBService {
    @Autowired
    BBRepository bbRepository;

    public void salvar (Pix pix) {
        bbRepository.save(pix);
    }
}
