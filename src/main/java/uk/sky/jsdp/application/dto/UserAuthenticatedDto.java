package uk.sky.jsdp.application.dto;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uk.sky.jsdp.infrastructure.adapter.out.persistence.entity.RoleEntity;
import uk.sky.jsdp.infrastructure.adapter.out.persistence.entity.UserEntity;

import java.util.Collection;

@Builder
@RequiredArgsConstructor
public class UserAuthenticatedDto implements UserDetails {
    private final UserEntity user;

    @Override
    @NullMarked
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream().map(RoleEntity::getName).map(SimpleGrantedAuthority::new).toList();
    }

    @Override
    public @Nullable String getPassword() {
        return user.getPassword();
    }

    @Override
    @NullMarked
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}
