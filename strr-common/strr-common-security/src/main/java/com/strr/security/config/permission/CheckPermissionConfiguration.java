package com.strr.security.config.permission;

import java.util.function.Supplier;

import io.micrometer.observation.ObservationRegistry;
import org.aopalliance.intercept.MethodInterceptor;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationEventPublisher;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.ObservationAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.util.function.SingletonSupplier;

/**
 * Base {@link Configuration} for enabling Spring Security Method Security.
 *
 * @author Evgeniy Cheban
 * @author Josh Cummings
 * @since 5.6
 * @see com.strr.security.annotation.EnableCheckPermission
 */
@Configuration(proxyBeanMethods = false)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class CheckPermissionConfiguration {
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    static MethodInterceptor checkPermissionAuthorizationMethodInterceptor(
            ObjectProvider<SecurityContextHolderStrategy> strategyProvider,
            ObjectProvider<AuthorizationEventPublisher> eventPublisherProvider,
            ObjectProvider<ObservationRegistry> registryProvider) {
        CheckPermissionAuthorizationManager manager = new CheckPermissionAuthorizationManager();
        CheckPermissionAuthorizationManagerMethodInterceptor checkPermission = CheckPermissionAuthorizationManagerMethodInterceptor
            .checkPermission(manager(manager, registryProvider));
        strategyProvider.ifAvailable(checkPermission::setSecurityContextHolderStrategy);
        eventPublisherProvider.ifAvailable(checkPermission::setAuthorizationEventPublisher);
        return checkPermission;
    }

    static <T> AuthorizationManager<T> manager(AuthorizationManager<T> delegate,
            ObjectProvider<ObservationRegistry> registryProvider) {
        return new DeferringObservationAuthorizationManager<>(registryProvider, delegate);
    }

    private static final class DeferringObservationAuthorizationManager<T> implements AuthorizationManager<T> {

        private final Supplier<AuthorizationManager<T>> delegate;

        private DeferringObservationAuthorizationManager(ObjectProvider<ObservationRegistry> provider,
                                                 AuthorizationManager<T> delegate) {
            this.delegate = SingletonSupplier.of(() -> {
                ObservationRegistry registry = provider.getIfAvailable(() -> ObservationRegistry.NOOP);
                if (registry.isNoop()) {
                    return delegate;
                }
                return new ObservationAuthorizationManager<>(registry, delegate);
            });
        }

        @Override
        public AuthorizationDecision check(Supplier<Authentication> authentication, T object) {
            return this.delegate.get().check(authentication, object);
        }
    }
}
