package UmbertoAmoroso.progettoFinalissimo.services;

import UmbertoAmoroso.progettoFinalissimo.entities.Evento;
import UmbertoAmoroso.progettoFinalissimo.entities.Prenotazione;
import UmbertoAmoroso.progettoFinalissimo.entities.Utente;
import UmbertoAmoroso.progettoFinalissimo.exceptions.BadRequestExceptions;
import UmbertoAmoroso.progettoFinalissimo.exceptions.NotFoundException;
import UmbertoAmoroso.progettoFinalissimo.payloads.NewPrenotazioneDTO;
import UmbertoAmoroso.progettoFinalissimo.repositories.EventoRepository;
import UmbertoAmoroso.progettoFinalissimo.repositories.PrenotazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UtenteService utenteService;

    public Prenotazione creaPrenotazione(NewPrenotazioneDTO newPrenotazioneDTO) {
        Utente utente = utenteService.getCurrentUtente();
        Evento evento = eventoRepository.findById(newPrenotazioneDTO.eventoId())
                .orElseThrow(() -> new NotFoundException("Evento non trovato con ID: " + newPrenotazioneDTO.eventoId()));

        if (evento.getPostiDisponibili() <= 0) {
            throw new BadRequestExceptions("Non ci sono più posti disponibili per questo evento");
        }

        if (prenotazioneRepository.existsByEventoAndUtente(evento, utente)) {
            throw new BadRequestExceptions("Hai già prenotato un posto per questo evento");
        }

        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setUtente(utente);
        prenotazione.setEvento(evento);
        prenotazione.setDataPrenotazione(LocalDateTime.now());

        evento.setPostiDisponibili(evento.getPostiDisponibili() - 1);
        eventoRepository.save(evento);

        return prenotazioneRepository.save(prenotazione);
    }

    public List<Prenotazione> trovaPrenotazioniPerUtente() {
        Utente utente = utenteService.getCurrentUtente();
        return prenotazioneRepository.findByUtente(utente);
    }
}
