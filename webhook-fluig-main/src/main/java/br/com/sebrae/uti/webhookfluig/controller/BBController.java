package br.com.sebrae.uti.webhookfluig.controller;

import br.com.sebrae.uti.webhookfluig.model.BB;
import br.com.sebrae.uti.webhookfluig.service.BBService;
import br.com.sebrae.uti.webhookfluig.service.FluigService;
import br.com.sebrae.uti.webhookfluig.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/bb/notificacao")
public class BBController {
    @Autowired
    BBService bbService;
    @Autowired
    FluigService fluigService;
    @Autowired
    UtilService utilService;

    Logger logger = LogManager.getLogger(BBController.class);

    @PostMapping()
    public ResponseEntity<BB> postNotificacaoBB (HttpServletRequest request, @RequestBody BB bb) {
        utilService.printRequestInfo(request, "BB");
        try{
            String body = bb.toString();
	        String typePayment = "BancoDoBrasil";
	        fluigService.notifyFluig(body, typePayment);
            return new ResponseEntity<>(bb, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
