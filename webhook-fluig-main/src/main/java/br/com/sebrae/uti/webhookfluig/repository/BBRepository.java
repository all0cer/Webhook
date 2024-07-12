package br.com.sebrae.uti.webhookfluig.repository;

import br.com.sebrae.uti.webhookfluig.model.Pix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BBRepository extends JpaRepository<Pix, Long> {
}
