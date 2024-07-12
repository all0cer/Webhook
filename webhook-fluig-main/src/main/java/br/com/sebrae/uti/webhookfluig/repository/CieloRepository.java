package br.com.sebrae.uti.webhookfluig.repository;

import br.com.sebrae.uti.webhookfluig.model.Cielo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CieloRepository extends JpaRepository<Cielo, Long> {
    public Optional<Cielo> findByCheckoutCieloOrderNumber(String checkoutCieloOrderNumber);
    public Optional<Cielo> findByTid(String tid);
}
