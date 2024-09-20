package UmbertoAmoroso.progettoFinalissimo.exceptions;

import org.apache.coyote.BadRequestException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Gestione delle eccezioni di validazione
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsPayload handleValidationExceptions(MethodArgumentNotValidException ex) {
        ErrorsPayload errors = new ErrorsPayload("Errore di validazione");
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.addError(error.getField(), error.getDefaultMessage())
        );
        return errors;
    }

    // Gestione delle eccezioni per entità non trovate
    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorsPayload handleNotFoundException(ChangeSetPersister.NotFoundException ex) {
        return new ErrorsPayload(ex.getMessage());
    }

    // Gestione delle eccezioni di autenticazione non autorizzata
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorsPayload handleUnauthorizedException(UnauthorizedException ex) {
        return new ErrorsPayload(ex.getMessage());
    }

    // Gestione delle eccezioni di accesso negato (es. per ruoli non autorizzati)
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorsPayload handleAccessDeniedException(AccessDeniedException ex) {
        return new ErrorsPayload("Accesso negato: Non hai i permessi necessari per accedere a questa risorsa.");
    }

    // Gestione delle eccezioni per BadRequest
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsPayload handleBadRequestException(BadRequestException ex) {
        return new ErrorsPayload(ex.getMessage());
    }

    // Gestione delle eccezioni generiche
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorsPayload handleGenericException(Exception ex) {
        return new ErrorsPayload("Errore interno del server: " + ex.getMessage());
    }
}
