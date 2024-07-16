package br.com.sebrae.uti.webhookfluig.controller;

import br.com.sebrae.uti.webhookfluig.model.BB;
import br.com.sebrae.uti.webhookfluig.service.BBService;
import br.com.sebrae.uti.webhookfluig.service.FluigService;
import br.com.sebrae.uti.webhookfluig.service.UtilService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

    String endpointFluig = "https://testehook.onrender.com/bb/notificacao/teste";

    Logger logger = Logger.getLogger(BBController.class);

    @PostMapping()
    public ResponseEntity<BB> postNotificacaoBB (HttpServletRequest request, @RequestBody BB bb) {
        utilService.printRequestInfo(request, "BB");
        try{
            String body = bb.toString();
            fluigService.notifyFluig(endpointFluig, body);
            logger.info(body);
            bbService.salvar(bb.getPix().get(0));
            return new ResponseEntity<>(bb, HttpStatus.OK);
        } catch(Exception e) {
            logger.warn("Não foi possível notificar o fluig. Erro: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint criado apenas para simular a chamada ao fluig para poder testar o resto da aplicação
    @PostMapping("/teste")
    public void teste(){}

}
