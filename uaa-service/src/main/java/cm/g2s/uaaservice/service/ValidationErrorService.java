package cm.g2s.uaaservice.service;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface ValidationErrorService {
    ResponseEntity<?> process(BindingResult result);
}
