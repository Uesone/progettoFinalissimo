package UmbertoAmoroso.progettoFinalissimo.services;

import UmbertoAmoroso.progettoFinalissimo.entities.Utente;
import UmbertoAmoroso.progettoFinalissimo.enums.Ruolo;
import UmbertoAmoroso.progettoFinalissimo.exceptions.UnauthorizedException;
import UmbertoAmoroso.progettoFinalissimo.payloads.NewUtenteDTO;
import UmbertoAmoroso.progettoFinalissimo.payloads.UserLoginDTO;
import UmbertoAmoroso.progettoFinalissimo.security.JWTTools;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private PasswordEncoder bcrypt;

    public void registraUtente(NewUtenteDTO newUtenteDTO) throws BadRequestException {
        Utente utente = new Utente();
        utente.setUsername(newUtenteDTO.username());
        utente.setEmail(newUtenteDTO.email());
        utente.setPassword(bcrypt.encode(newUtenteDTO.password()));
        utente.setRuolo(Ruolo.valueOf(newUtenteDTO.ruolo().toUpperCase()));

        utenteService.salvaUtente(utente);
    }

    public String login(UserLoginDTO loginDTO) {
        Utente utente = utenteService.trovaPerEmail(loginDTO.email());

        if (bcrypt.matches(loginDTO.password(), utente.getPassword())) {
            return jwtTools.createToken(utente);
        } else {
            throw new UnauthorizedException("Credenziali non valide!");
        }
    }
}