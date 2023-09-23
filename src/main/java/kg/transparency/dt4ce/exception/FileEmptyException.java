package kg.transparency.dt4ce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class FileEmptyException extends RuntimeException{
    public FileEmptyException(String message){
        super(message);
    }
}
