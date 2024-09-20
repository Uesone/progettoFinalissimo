package UmbertoAmoroso.progettoFinalissimo.controller;

import UmbertoAmoroso.progettoFinalissimo.payloads.NewUtenteDTO;
import UmbertoAmoroso.progettoFinalissimo.payloads.UserLoginDTO;
import UmbertoAmoroso.progettoFinalissimo.payloads.UserLoginRespDTO;
import UmbertoAmoroso.progettoFinalissimo.services.AuthService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public String registraUtente(@RequestBody @Validated NewUtenteDTO newUtenteDTO) throws BadRequestException {
        authService.registraUtente(newUtenteDTO);
        return "Utente registrato con successo!";
    }

    @PostMapping("/login")
    public UserLoginRespDTO login(@RequestBody UserLoginDTO payload) {
        return new UserLoginRespDTO(authService.login(payload));
    }
}