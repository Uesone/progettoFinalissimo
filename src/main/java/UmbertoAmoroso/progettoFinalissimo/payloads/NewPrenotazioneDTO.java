package UmbertoAmoroso.progettoFinalissimo.payloads;

import jakarta.validation.constraints.NotNull;

// DTO per la creazione di una prenotazione
public record NewPrenotazioneDTO(
        @NotNull(message = "L'ID dell'evento è obbligatorio")
        Long eventoId
) {}
