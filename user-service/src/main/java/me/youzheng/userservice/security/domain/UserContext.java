package me.youzheng.userservice.security.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import me.youzheng.userservice.user.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class UserContext implements UserDetails {

    private final User user;

    public UserContext(User user) {
        if (user == null) {
            throw new IllegalArgumentException("UserContext 에 null 은 절대로 넣을 수 없다.");
        }
        this.user = user;
    }

    public boolean canLogin() {
        return isEnabled()
            && isAccountNonLocked()
            && !isAccountNonExpired();
    }

    /**
     * 로그인 확인이 완료된 후 User 객체 내부의 Password 를 삭제한다.
     */
    public void afterAuthenticate() {
        this.user.setPassword(null);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
        return roles;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getLoginId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.user.isLockYn();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return isAccountNonLocked();
    }

}