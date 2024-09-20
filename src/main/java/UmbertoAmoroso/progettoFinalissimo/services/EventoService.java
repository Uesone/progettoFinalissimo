package UmbertoAmoroso.progettoFinalissimo.services;

import UmbertoAmoroso.progettoFinalissimo.entities.Evento;
import UmbertoAmoroso.progettoFinalissimo.entities.Utente;
import UmbertoAmoroso.progettoFinalissimo.exceptions.NotFoundException;
import UmbertoAmoroso.progettoFinalissimo.payloads.NewEventoDTO;
import UmbertoAmoroso.progettoFinalissimo.repositories.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UtenteService utenteService;

    public Evento creaEvento(NewEventoDTO newEventoDTO) {
        Utente organizzatore = utenteService.getCurrentUtente();
        Evento evento = new Evento();
        evento.setTitolo(newEventoDTO.titolo());
        evento.setDescrizione(newEventoDTO.descrizione());
        evento.setData(LocalDateTime.parse(newEventoDTO.data()));
        evento.setLuogo(newEventoDTO.luogo());
        evento.setPostiDisponibili(newEventoDTO.postiDisponibili());
        evento.setOrganizzatore(organizzatore);

        return eventoRepository.save(evento);
    }

    public List<Evento> trovaTutti() {
        return eventoRepository.findAll();
    }

    public Evento aggiornaEvento(Long eventoId, NewEventoDTO newEventoDTO) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new NotFoundException("Evento non trovato con ID: " + eventoId));

        evento.setTitolo(newEventoDTO.titolo());
        evento.setDescrizione(newEventoDTO.descrizione());
        evento.setData(LocalDateTime.parse(newEventoDTO.data()));
        evento.setLuogo(newEventoDTO.luogo());
        evento.setPostiDisponibili(newEventoDTO.postiDisponibili());

        return eventoRepository.save(evento);
    }

    public void cancellaEvento(Long eventoId) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new NotFoundException("Evento non trovato con ID: " + eventoId));
        eventoRepository.delete(evento);
    }
}
