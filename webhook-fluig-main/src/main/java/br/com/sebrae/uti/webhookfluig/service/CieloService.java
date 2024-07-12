package br.com.sebrae.uti.webhookfluig.service;

import br.com.sebrae.uti.webhookfluig.model.Cielo;
import br.com.sebrae.uti.webhookfluig.repository.CieloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CieloService {

    @Autowired
    CieloRepository cieloRepository;

    public void salvar (Cielo cielo) {
        cieloRepository.save(cielo);
    }

    public Cielo getCieloByCheckoutOrderNumber (String checkoutCieloOrderNumber) {
        return cieloRepository
                .findByCheckoutCieloOrderNumber(checkoutCieloOrderNumber)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Notificacao Cielo não encontrada por checkout_order_number"));
    }

    public Cielo getCieloByTid (String tid) {
        return cieloRepository
                .findByTid(tid)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Notificacao Cielo não encontrada por tid"));
    }
}
