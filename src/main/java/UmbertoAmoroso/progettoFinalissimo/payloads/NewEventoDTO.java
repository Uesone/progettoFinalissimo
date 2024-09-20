package UmbertoAmoroso.progettoFinalissimo.payloads;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

// DTO per la creazione o modifica dell'evento
public record NewEventoDTO(
        @NotEmpty(message = "Il titolo è obbligatorio")
        String titolo,

        @NotEmpty(message = "La descrizione è obbligatoria")
        String descrizione,

        @NotEmpty(message = "La data è obbligatoria")
        String data,

        @NotEmpty(message = "Il luogo è obbligatorio")
        String luogo,

        @Min(value = 1, message = "Deve esserci almeno un posto disponibile")
        int postiDisponibili
) {}
