package com.strr.security.support;

import com.strr.security.annotation.EnableCheckPermission;
import org.springframework.context.annotation.AutoProxyRegistrar;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;

/**
 * Dynamically determines which imports to include using the {@link EnableCheckPermission}
 * annotation.
 */
public class CheckPermissionSelector implements ImportSelector {
    private static final String[] IMPORTS = new String[] {
            AutoProxyRegistrar.class.getName(),
            CheckPermissionAdvisorRegistrar.class.getName(),
            CheckPermissionConfiguration.class.getName()
    };

    @Override
    public String[] selectImports(@NonNull AnnotationMetadata importMetadata) {
        if (!importMetadata.hasAnnotation(EnableCheckPermission.class.getName())) {
            return new String[0];
        }
        return IMPORTS;
    }
}
