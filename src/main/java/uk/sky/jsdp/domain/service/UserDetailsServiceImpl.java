package uk.sky.jsdp.domain.service;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uk.sky.jsdp.application.port.UserPersistence;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserPersistence userPersistence;

    @Override
    @NullMarked
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userPersistence.findByUsername(username).map(u -> (UserDetails) u).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
