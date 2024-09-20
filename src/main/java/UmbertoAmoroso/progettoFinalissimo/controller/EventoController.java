package UmbertoAmoroso.progettoFinalissimo.controller;

import UmbertoAmoroso.progettoFinalissimo.entities.Evento;
import UmbertoAmoroso.progettoFinalissimo.payloads.NewEventoDTO;
import UmbertoAmoroso.progettoFinalissimo.services.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventi")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    // Gli organizzatori possono creare eventi
    @PostMapping
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    public Evento creaEvento(@RequestBody @Validated NewEventoDTO newEventoDTO) {
        return eventoService.creaEvento(newEventoDTO);
    }

    // Gli utenti possono visualizzare tutti gli eventi
    @GetMapping
    public List<Evento> trovaTuttiEventi() {
        return eventoService.trovaTutti();
    }

    // Gli organizzatori possono modificare i propri eventi
    @PutMapping("/{eventoId}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    public Evento aggiornaEvento(@PathVariable Long eventoId, @RequestBody @Validated NewEventoDTO newEventoDTO) {
        return eventoService.aggiornaEvento(eventoId, newEventoDTO);
    }

    // Gli organizzatori possono eliminare i propri eventi
    @DeleteMapping("/{eventoId}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancellaEvento(@PathVariable Long eventoId) {
        eventoService.cancellaEvento(eventoId);
    }
}
