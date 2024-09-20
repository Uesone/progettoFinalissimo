package UmbertoAmoroso.progettoFinalissimo.repositories;

import UmbertoAmoroso.progettoFinalissimo.entities.Evento;
import UmbertoAmoroso.progettoFinalissimo.entities.Prenotazione;
import UmbertoAmoroso.progettoFinalissimo.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {

    // Trova tutte le prenotazioni effettuate da un determinato utente
    List<Prenotazione> findByUtente(Utente utente);

    // Verifica se esiste già una prenotazione per un determinato evento e utente
    boolean existsByEventoAndUtente(Evento evento, Utente utente);

    // Verifica se esiste una prenotazione per una data specifica, escludendo una determinata prenotazione
    boolean existsByUtenteAndEventoAndIdNot(Utente utente, Evento evento, Long prenotazioneId);
}
