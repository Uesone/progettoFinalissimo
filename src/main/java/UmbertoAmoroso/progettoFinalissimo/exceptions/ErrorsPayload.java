package UmbertoAmoroso.progettoFinalissimo.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
@Getter
@Setter

public class ErrorsPayload {
    @Setter
    private String message;
    private Map<String, String> errors = new HashMap<>();

    public ErrorsPayload(String message) {
        this.message = message;
    }

    public void addError(String field, String errorMessage) {
        this.errors.put(field, errorMessage);
    }
}