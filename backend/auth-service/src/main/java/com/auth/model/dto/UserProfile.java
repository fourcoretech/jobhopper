package com.auth.model.dto;

import com.auth.entity.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Data
@Builder
public class UserProfile implements UserDetails {
    private final String username;
    private final String password;
    private final String phone;
    private final String email;
    private final String createTimeStamp;
    private final String role;

    public UserProfile(String username, String password, String phone, String email, String createTimeStamp, String role) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.createTimeStamp = createTimeStamp;
        this.role = role;
    }

    public UserProfile(User user) {
        this(user.getUsername(), user.getPassword(), user.getPhone(), user.getEmail(), user.getCreateTimeStamp(), user.getRole());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
        return Set.of(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getCreateTimeStamp() {
        return createTimeStamp;
    }

    public String getRole() {
        return role;
    }
}
