package UmbertoAmoroso.progettoFinalissimo.controller;

import UmbertoAmoroso.progettoFinalissimo.entities.Prenotazione;
import UmbertoAmoroso.progettoFinalissimo.payloads.NewPrenotazioneDTO;
import UmbertoAmoroso.progettoFinalissimo.services.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {

    @Autowired
    private PrenotazioneService prenotazioneService;

    // Gli utenti possono prenotare un posto per un evento
    @PostMapping
    @PreAuthorize("hasAuthority('UTENTE')")
    public Prenotazione creaPrenotazione(@RequestBody @Validated NewPrenotazioneDTO newPrenotazioneDTO) {
        return prenotazioneService.creaPrenotazione(newPrenotazioneDTO);
    }

    // Gli utenti possono visualizzare le proprie prenotazioni
    @GetMapping
    @PreAuthorize("hasAuthority('UTENTE')")
    public List<Prenotazione> trovaPrenotazioniPerUtente() {
        return prenotazioneService.trovaPrenotazioniPerUtente();
    }
}
