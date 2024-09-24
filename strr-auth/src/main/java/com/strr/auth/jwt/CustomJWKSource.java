package com.strr.auth.jwt;

import com.nimbusds.jose.KeySourceException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.strr.auth.util.Jwks;

import java.util.List;

public class CustomJWKSource implements JWKSource<SecurityContext> {
    private final JWKSet jwkSet;

    public CustomJWKSource() {
        this.jwkSet = new JWKSet(Jwks.generateRsa());
    }

    @Override
    public List<JWK> get(JWKSelector jwkSelector, SecurityContext securityContext) throws KeySourceException {
        return jwkSelector.select(jwkSet);
    }
}
