FROM golang:1.21-alpine AS builder

RUN apk add --update --no-cache make poppler-utils

WORKDIR /app

ADD . .

RUN go mod downloads && make

FROM alpine:3.9.6

WORKDIR /app

COPY --from=builder /app/bookstore .

CMD [ "/app/bookstore" ]
