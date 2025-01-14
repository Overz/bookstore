# Postgres

[//]: # (sudo chown 70:70 server.key)

[//]: # (sudo chmod 600 server.key)

## Certificado Raiz (Root Certificate)

- O que é: O certificado raiz é o certificado de autoridade de certificação (CA) de nível mais alto em uma hierarquia de
	certificados SSL/TLS.
- Para que serve: Ele é usado para assinar outros certificados, incluindo certificados intermediários e de folha (leaf).
- Como criar: Geralmente, os certificados raiz são criados pelas próprias autoridades de certificação. Eles podem ser
	auto-assinados ou assinados por outra autoridade de certificação confiável.

```shell
openssl req -new -x509 -days 365 -nodes -out root.crt -keyout root.key -subj "/CN=${HOSTNAME:-"localhost"}"
```

---

## Certificado de Folha (Leaf Certificate)

- O que é: O certificado de folha é o certificado que contém as informações de identidade específicas do servidor ou
	cliente.
- Para que serve: Ele é apresentado pelo servidor ou cliente durante o processo de negociação SSL/TLS para verificar sua
	identidade.
- Como criar: Os certificados de folha são geralmente gerados para servidores web, aplicativos, dispositivos e usuários.

Eles podem ser auto-assinados (para fins de teste ou ambientes fechados) ou assinados por uma autoridade de certificação
confiável (para uso em produção).

```shell
openssl req -new -nodes -out leaf.csr -keyout leaf.key -subj "/CN=${HOSTNAME:-"localhost"}"
openssl x509 -req -in leaf.csr -CA intermediate.crt -CAkey intermediate.key -CAcreateserial -out leaf.crt -days 365
```

---

## Certificado Intermediário (Intermediate Certificate)

- O que é: O certificado intermediário fica entre o certificado raiz e o certificado de folha na cadeia de certificados
	SSL/TLS.
- Para que serve: Ele é usado para criar uma cadeia de confiança entre o certificado de folha e o certificado raiz.
- Como criar: Os certificados intermediários são normalmente criados e assinados pela própria autoridade de
	certificação, com base no certificado raiz. Eles podem ser usados para fins de gerenciamento de chaves e para garantir
	que o certificado raiz permaneça seguro.

```shell
openssl req -new -nodes -out intermediate.csr -keyout intermediate.key -subj "/CN=${HOSTNAME:-"localhost"}-$(uuidgen)"
openssl x509 -req -in intermediate.csr -CA root.crt -CAkey root.key -CAcreateserial -out intermediate.crt -days 365
```

---

## Quando Usar:

- Certificado Raiz: Usado para assinar certificados intermediários e estabelecer a confiança na cadeia de certificados.
- Certificado de Folha: Usado para identificar especificamente um servidor, aplicativo, dispositivo ou usuário durante
	uma conexão SSL/TLS.
- Certificado Intermediário: Usado para construir uma cadeia de confiança entre o certificado de folha e o certificado
	raiz.

Em resumo, o certificado raiz é a raiz de confiança, o certificado de folha é específico para o servidor ou cliente, e
os certificados intermediários ajudam a construir uma cadeia de confiança entre eles. O uso e a criação de cada um
dependem do contexto e dos requisitos específicos do sistema ou aplicativo.
