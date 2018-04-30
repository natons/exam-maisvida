package exam.maisvida.med.br.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class ObjectIncorrectException extends RuntimeException {

    public ObjectIncorrectException(String exception) {
        super(exception);
    }

}
