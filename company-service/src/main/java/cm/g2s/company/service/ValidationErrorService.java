package cm.g2s.company.service;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface ValidationErrorService {
    ResponseEntity<?> process(BindingResult result);
}
