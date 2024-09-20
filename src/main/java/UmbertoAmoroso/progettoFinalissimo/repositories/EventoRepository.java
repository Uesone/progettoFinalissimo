package UmbertoAmoroso.progettoFinalissimo.repositories;

import UmbertoAmoroso.progettoFinalissimo.entities.Evento;
import UmbertoAmoroso.progettoFinalissimo.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    // Trova tutti gli eventi creati da un determinato organizzatore
    List<Evento> findByOrganizzatore(Utente organizzatore);
}
