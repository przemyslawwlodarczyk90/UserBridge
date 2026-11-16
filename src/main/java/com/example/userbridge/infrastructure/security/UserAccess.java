package com.example.userbridge.infrastructure.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Ownership checks used in {@code @PreAuthorize} expressions, e.g.
 * {@code @PreAuthorize("@userAccess.isSelfOrAdmin(#id)")}.
 */
@Component("userAccess")
public class UserAccess {

    public boolean isSelfOrAdmin(Long userId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return false;
        }
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()));
        if (isAdmin) {
            return true;
        }
        return auth.getPrincipal() instanceof UserDetailsImpl details
                && details.getUser().getId() != null
                && details.getUser().getId().equals(userId);
    }
}
