package br.com.sebrae.uti.webhookfluig.controller;

import br.com.sebrae.uti.webhookfluig.model.Cielo;
import br.com.sebrae.uti.webhookfluig.service.CieloService;
import br.com.sebrae.uti.webhookfluig.service.FluigService;
import br.com.sebrae.uti.webhookfluig.service.UtilService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/cielo/notificacao")
public class CieloController {

    @Autowired
    CieloService cieloService;
    @Autowired
    FluigService fluigService;
    @Autowired
    UtilService utilService;


    Logger logger = Logger.getLogger(CieloController.class);

    @PostMapping()
    public ResponseEntity<Cielo> postNotificacaoCielo (HttpServletRequest request, @RequestParam Map<String, String> body){
        utilService.printRequestInfo(request, "CIELO");
        try{
            System.out.println("Corpo da requisição: " + body);
            System.out.println("requisição: " + request);
            Cielo cielo = utilService.parseBody(body);
            logger.info(cielo.toString());
            String typePayment = "Cielo";
            if(fluigService.paymentStatusEqualTwo(body))
                fluigService.notifyFluig(cielo.toString(), typePayment);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch(Exception e) {
            logger.warn("Não foi possível notificar o fluig. Erro: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
