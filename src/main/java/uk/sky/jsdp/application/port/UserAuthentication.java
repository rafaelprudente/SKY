package uk.sky.jsdp.application.port;

import org.springframework.security.core.Authentication;
import uk.sky.jsdp.application.dto.AuthDto;

public interface UserAuthentication {
    AuthDto token(Authentication authentication);
}
