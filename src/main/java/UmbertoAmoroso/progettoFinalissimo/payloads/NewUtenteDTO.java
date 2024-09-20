package UmbertoAmoroso.progettoFinalissimo.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record NewUtenteDTO(
        @NotEmpty(message = "Il nome utente è obbligatorio")
        @Size(min = 3, max = 20, message = "Il nome utente deve essere compreso tra 3 e 20 caratteri")
        String username,

        @NotEmpty(message = "Email obbligatoria")
        @Email(message = "L'email deve essere valida")
        String email,

        @NotEmpty(message = "La password è obbligatoria")
        @Size(min = 6, message = "La password deve essere di almeno 6 caratteri")
        String password,

        @NotEmpty(message = "Il ruolo è obbligatorio")
        String ruolo // Questo campo sarà "UTENTE" o "ORGANIZZATORE"
) {}