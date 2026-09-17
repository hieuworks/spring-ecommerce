package com.project.ecommerce.common.exception;

import com.project.ecommerce.user.exception.UserRegistrationFailedException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GlobalExceptionHandlerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new TestController())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void returnsFieldErrorsForInvalidRequest() throws Exception {
        mockMvc.perform(post("/test/validation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\",\"email\":\"not-an-email\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_FAILED"))
                .andExpect(jsonPath("$.fieldErrors.name").value("Name is required."))
                .andExpect(jsonPath("$.fieldErrors.email").value("Email must be valid."))
                .andExpect(jsonPath("$.path").value("/test/validation"));
    }

    @Test
    void returnsConflictForUniqueConstraintViolation() throws Exception {
        mockMvc.perform(post("/test/duplicate"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("RESOURCE_ALREADY_EXISTS"))
                .andExpect(jsonPath("$.message").value(
                        "A resource with the same unique value already exists."
                ));
    }

    @Test
    void hidesInternalDetailsForUnexpectedException() throws Exception {
        mockMvc.perform(post("/test/unexpected"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value("INTERNAL_SERVER_ERROR"))
                .andExpect(jsonPath("$.message").value("An unexpected error occurred."));
    }

    @Test
    void returnsRegistrationFailureWithoutLeakingItsCause() throws Exception {
        mockMvc.perform(post("/test/registration"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value("USER_REGISTRATION_FAILED"))
                .andExpect(jsonPath("$.message").value("Unable to register the user at this time."));
    }

    @Test
    void preservesStatusForIntentionalWebExceptions() throws Exception {
        mockMvc.perform(post("/test/not-found"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("HTTP_404"))
                .andExpect(jsonPath("$.message").value("The requested user was not found."));
    }

    @RestController
    private static class TestController {

        @PostMapping("/test/validation")
        void validate(@Valid @RequestBody TestRequest request) {
        }

        @PostMapping("/test/duplicate")
        void duplicate() {
            SQLException sqlException = new SQLException("sensitive database detail", "23505");
            throw new DataIntegrityViolationException("sensitive database detail", sqlException);
        }

        @PostMapping("/test/unexpected")
        void unexpected() {
            throw new IllegalStateException("sensitive internal detail");
        }

        @PostMapping("/test/registration")
        void registration() {
            throw new UserRegistrationFailedException(
                    new SQLException("sensitive database detail")
            );
        }

        @PostMapping("/test/not-found")
        void notFound() {
            throw new ResponseStatusException(
                    org.springframework.http.HttpStatus.NOT_FOUND,
                    "The requested user was not found."
            );
        }
    }

    private record TestRequest(
            @NotBlank(message = "Name is required.") String name,
            @Email(message = "Email must be valid.") String email
    ) {
    }
}
