package me.youzheng.userservice.config.jpa;

import java.util.Optional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null || context.getAuthentication() == null || "anonymousUser".equals(
            context.getAuthentication().getName())) {
            return Optional.of("SYSTEM");
        }
        return Optional.of(context.getAuthentication().getName());
    }

}