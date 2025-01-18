ARG KC_VERSION="26.1.0"

FROM keycloak/keycloak:${KC_VERSION} AS builder

WORKDIR /opt/keycloak

COPY . .

RUN /opt/keycloak/bin/kc.sh build \
    --db=postgres \
    --transaction-xa-enabled=true \
    --http-relative-path=/kc \
    --health-enabled=true \
    --metrics-enabled=true

FROM keycloak/keycloak:${KC_VERSION} AS final

WORKDIR /opt/keycloak

COPY --from=builder /opt/keycloak/ /opt/keycloak/
COPY --chown=keycloak:keycloak . .

CMD [ "start", "--optimized", "--import-realm" ]
