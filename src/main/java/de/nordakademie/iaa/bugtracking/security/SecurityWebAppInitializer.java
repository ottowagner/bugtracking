package de.nordakademie.iaa.bugtracking.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * necessary for Spring security configuration
 */
public class SecurityWebAppInitializer extends AbstractSecurityWebApplicationInitializer {
}
