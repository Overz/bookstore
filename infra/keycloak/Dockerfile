FROM keycloak/keycloak:23.0.5 AS builder

WORKDIR /opt/keycloak

RUN /opt/keycloak/bin/kc.sh build

FROM keycloak/keycloak:23.0.5 AS final

WORKDIR /opt/keycloak

COPY --from=builder /opt/keycloak/ /opt/keycloak/
ADD --chown=keycloak:keycloak . .

CMD [ "start", "--import-realm" ]
