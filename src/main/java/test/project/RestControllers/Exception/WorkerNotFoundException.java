package test.project.RestControllers.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by i.saenko on 29.09.2018.
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Worker Not Found")
public class WorkerNotFoundException extends Exception{

    private static final long serialVersionUID = 1L;

    public WorkerNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
