package za.ac.cput.exception;

import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void notFound_maps404() {
        ResponseEntity<Map<String, Object>> response =
                handler.handleNotFound(new ResourceNotFoundException("Car", 5));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).containsEntry("status", 404);
        assertThat(response.getBody().get("message").toString()).contains("Car").contains("5");
    }

    /** A real method whose parameter backs a real MethodParameter (no concrete-class mock). */
    @SuppressWarnings("unused")
    private void sample(String arg) {
    }

    @Test
    void validation_maps400_withFieldErrors() throws Exception {
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(
                new FieldError("carRequest", "vinNumber", "vinNumber is required"),
                new FieldError("carRequest", "modelId", "modelId is required")));
        MethodParameter parameter = new MethodParameter(
                getClass().getDeclaredMethod("sample", String.class), 0);
        MethodArgumentNotValidException ex =
                new MethodArgumentNotValidException(parameter, bindingResult);

        ResponseEntity<Map<String, Object>> response = handler.handleValidation(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        @SuppressWarnings("unchecked")
        Map<String, String> errors = (Map<String, String>) response.getBody().get("errors");
        assertThat(errors)
                .containsEntry("vinNumber", "vinNumber is required")
                .containsEntry("modelId", "modelId is required");
    }

    @Test
    void unreadable_maps400() {
        ResponseEntity<Map<String, Object>> response =
                handler.handleUnreadable(new HttpMessageNotReadableException("bad json"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void generic_maps500() {
        ResponseEntity<Map<String, Object>> response =
                handler.handleGeneric(new RuntimeException("boom"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        // internal details are not leaked to the client
        assertThat(response.getBody().get("message").toString()).doesNotContain("boom");
    }
}
