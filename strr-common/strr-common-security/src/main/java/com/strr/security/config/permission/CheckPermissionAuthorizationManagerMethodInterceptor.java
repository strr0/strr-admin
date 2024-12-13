package com.strr.security.config.permission;

import java.lang.annotation.Annotation;
import java.util.function.Supplier;

import com.strr.security.annotation.CheckPermission;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.framework.AopInfrastructureBean;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.Pointcuts;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.core.Ordered;
import org.springframework.core.log.LogMessage;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationEventPublisher;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.method.AuthorizationInterceptorsOrder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.util.Assert;

/**
 * A {@link MethodInterceptor} which uses a {@link AuthorizationManager} to determine if
 * an {@link Authentication} may invoke the given {@link MethodInvocation}
 */
public final class CheckPermissionAuthorizationManagerMethodInterceptor
        implements Ordered, MethodInterceptor, PointcutAdvisor, AopInfrastructureBean {

    private Supplier<SecurityContextHolderStrategy> securityContextHolderStrategy = SecurityContextHolder::getContextHolderStrategy;

    private final Log logger = LogFactory.getLog(this.getClass());

    private final Pointcut pointcut;

    private final AuthorizationManager<MethodInvocation> authorizationManager;

    private int order = AuthorizationInterceptorsOrder.FIRST.getOrder();

    private AuthorizationEventPublisher eventPublisher = CheckPermissionAuthorizationManagerMethodInterceptor::noPublish;

    /**
     * Creates an instance.
     * @param pointcut the {@link Pointcut} to use
     * @param authorizationManager the {@link AuthorizationManager} to use
     */
    public CheckPermissionAuthorizationManagerMethodInterceptor(Pointcut pointcut,
                                                                AuthorizationManager<MethodInvocation> authorizationManager) {
        Assert.notNull(pointcut, "pointcut cannot be null");
        Assert.notNull(authorizationManager, "authorizationManager cannot be null");
        this.pointcut = pointcut;
        this.authorizationManager = authorizationManager;
    }

    /**
     * Creates an interceptor for the {@link CheckPermission} annotation
     * @return the interceptor
     */
    public static CheckPermissionAuthorizationManagerMethodInterceptor checkPermission() {
        return checkPermission(new CheckPermissionAuthorizationManager());
    }

    /**
     * Creates an interceptor for the {@link CheckPermission} annotation
     * @param authorizationManager the {@link CheckPermissionAuthorizationManager} to use
     * @return the interceptor
     */
    public static CheckPermissionAuthorizationManagerMethodInterceptor checkPermission(
            CheckPermissionAuthorizationManager authorizationManager) {
        CheckPermissionAuthorizationManagerMethodInterceptor interceptor = new CheckPermissionAuthorizationManagerMethodInterceptor(
                forAnnotations(CheckPermission.class), authorizationManager);
        interceptor.setOrder(AuthorizationInterceptorsOrder.PRE_AUTHORIZE.getOrder());
        return interceptor;
    }

    /**
     * Creates an interceptor for the {@link CheckPermission} annotation
     * @param authorizationManager the {@link AuthorizationManager} to use
     * @return the interceptor
     * @since 6.0
     */
    public static CheckPermissionAuthorizationManagerMethodInterceptor checkPermission(
            AuthorizationManager<MethodInvocation> authorizationManager) {
        CheckPermissionAuthorizationManagerMethodInterceptor interceptor = new CheckPermissionAuthorizationManagerMethodInterceptor(
                forAnnotations(CheckPermission.class), authorizationManager);
        interceptor.setOrder(AuthorizationInterceptorsOrder.PRE_AUTHORIZE.getOrder());
        return interceptor;
    }

    @SafeVarargs
    private static Pointcut forAnnotations(Class<? extends Annotation>... annotations) {
        ComposablePointcut pointcut = null;
        for (Class<? extends Annotation> annotation : annotations) {
            if (pointcut == null) {
                pointcut = new ComposablePointcut(classOrMethod(annotation));
            }
            else {
                pointcut.union(classOrMethod(annotation));
            }
        }
        return pointcut;
    }

    private static Pointcut classOrMethod(Class<? extends Annotation> annotation) {
        return Pointcuts.union(new AnnotationMatchingPointcut(null, annotation, true),
                new AnnotationMatchingPointcut(annotation, true));
    }

    /**
     * Determine if an {@link Authentication} has access to the {@link MethodInvocation}
     * using the configured {@link AuthorizationManager}.
     * @param mi the {@link MethodInvocation} to check
     * @throws AccessDeniedException if access is not granted
     */
    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        attemptAuthorization(mi);
        return mi.proceed();
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    /**
     * Use this {@link AuthorizationEventPublisher} to publish the
     * {@link AuthorizationManager} result.
     * @param eventPublisher
     * @since 5.7
     */
    public void setAuthorizationEventPublisher(AuthorizationEventPublisher eventPublisher) {
        Assert.notNull(eventPublisher, "eventPublisher cannot be null");
        this.eventPublisher = eventPublisher;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    @Override
    public Advice getAdvice() {
        return this;
    }

    @Override
    public boolean isPerInstance() {
        return true;
    }

    /**
     * Sets the {@link SecurityContextHolderStrategy} to use. The default action is to use
     * the {@link SecurityContextHolderStrategy} stored in {@link SecurityContextHolder}.
     *
     * @since 5.8
     */
    public void setSecurityContextHolderStrategy(SecurityContextHolderStrategy securityContextHolderStrategy) {
        this.securityContextHolderStrategy = () -> securityContextHolderStrategy;
    }

    private void attemptAuthorization(MethodInvocation mi) {
        this.logger.debug(LogMessage.of(() -> "Authorizing method invocation " + mi));
        AuthorizationDecision decision = this.authorizationManager.check(this::getAuthentication, mi);
        this.eventPublisher.publishAuthorizationEvent(this::getAuthentication, mi, decision);
        if (decision != null && !decision.isGranted()) {
            this.logger.debug(LogMessage.of(() -> "Failed to authorize " + mi + " with authorization manager "
                    + this.authorizationManager + " and decision " + decision));
            throw new AccessDeniedException("Access Denied");
        }
        this.logger.debug(LogMessage.of(() -> "Authorized method invocation " + mi));
    }

    private Authentication getAuthentication() {
        Authentication authentication = this.securityContextHolderStrategy.get().getContext().getAuthentication();
        if (authentication == null) {
            throw new AuthenticationCredentialsNotFoundException(
                    "An Authentication object was not found in the SecurityContext");
        }
        return authentication;
    }

    private static <T> void noPublish(Supplier<Authentication> authentication, T object,
            AuthorizationDecision decision) {

    }
}
