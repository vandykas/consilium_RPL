package org.unpar.project.aspect;

import jakarta.servlet.http.HttpSession;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.unpar.project.model.Pengguna;

import java.util.Arrays;

@Aspect
@Component
public class AuthorizationAspect {
    @Autowired
    HttpSession session;

    @Before("@annotation(requiredRole)")
    public void checkAuthorization(RequiredRole requiredRole) {
        if (notLogin()) {
            throw new SecurityException("You are not logged in");
        }

        String[] roles = requiredRole.value();
        String currentRole = ((Pengguna) session.getAttribute("pengguna")).getRole();

        if (dontHavePermission(currentRole, roles)) {
            throw new SecurityException("You don't have permission");
        }
    }

    private boolean notLogin() {
        return session.getAttribute("pengguna") == null;
    }

    private boolean dontHavePermission(String currentRole, String[] roles) {
        return !Arrays.asList(roles).contains(currentRole);
    }
}
