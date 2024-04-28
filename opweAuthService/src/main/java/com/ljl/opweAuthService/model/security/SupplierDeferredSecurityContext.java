package com.ljl.opweAuthService.model.security;

import org.springframework.security.core.context.DeferredSecurityContext;
import java.util.function.Supplier;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.core.log.LogMessage;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolderStrategy;

/**
 * @Author Liu Jialin
 * @Date 2024/4/23 15:10
 * @PackageName com.ljl.opweAuthService.model.security
 * @ClassName SupplierDeferredSecurityContext
 * @Description TODO
 * @Version 1.0.0
 */
public final class SupplierDeferredSecurityContext implements DeferredSecurityContext {

    private static final Log logger = LogFactory.getLog(SupplierDeferredSecurityContext.class);

    private final Supplier<SecurityContext> supplier;

    private final SecurityContextHolderStrategy strategy;

    private SecurityContext securityContext;

    private boolean missingContext;

    public SupplierDeferredSecurityContext(Supplier<SecurityContext> supplier, SecurityContextHolderStrategy strategy) {
        this.supplier = supplier;
        this.strategy = strategy;
    }

    @Override
    public SecurityContext get() {
        init();
        return this.securityContext;
    }

    @Override
    public boolean isGenerated() {
        init();
        return this.missingContext;
    }

    private void init() {
        if (this.securityContext != null) {
            return;
        }

        this.securityContext = this.supplier.get();
        this.missingContext = (this.securityContext == null);
        if (this.missingContext) {
            this.securityContext = this.strategy.createEmptyContext();
            if (logger.isTraceEnabled()) {
                logger.trace(LogMessage.format("Created %s", this.securityContext));
            }
        }
    }

}

