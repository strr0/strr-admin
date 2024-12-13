package com.strr.security.support;

import org.springframework.aop.Advisor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

class CheckPermissionAdvisorRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        registerAsAdvisor("checkPermissionAuthorization", registry);
    }

    private void registerAsAdvisor(String prefix, BeanDefinitionRegistry registry) {
        String advisorName = prefix + "Advisor";
        if (registry.containsBeanDefinition(advisorName)) {
            return;
        }
        String interceptorName = prefix + "MethodInterceptor";
        if (!registry.containsBeanDefinition(interceptorName)) {
            return;
        }
        BeanDefinition definition = registry.getBeanDefinition(interceptorName);
        if (!(definition instanceof RootBeanDefinition)) {
            return;
        }
        RootBeanDefinition advisor = new RootBeanDefinition((RootBeanDefinition) definition);
        advisor.setTargetType(Advisor.class);
        registry.registerBeanDefinition(prefix + "Advisor", advisor);
    }

}
