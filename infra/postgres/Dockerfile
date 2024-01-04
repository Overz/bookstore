FROM postgres:16.1

ADD . /docker-entrypoint-initdb.d/

RUN openssl req -new -text -passout pass:abcd -subj /CN=localhost -out server.req -keyout privkey.pem \
	&& openssl rsa -in privkey.pem -passin pass:abcd -out server.key \
	&& openssl req -x509 -in server.req -text -key server.key -out server.crt \
	&& mv server.key server.crt /docker-entrypoint-initdb.d/ \
  && chmod -Rv 700 /docker-entrypoint-initdb.d \
	&& chown -Rv postgres:postgres /docker-entrypoint-initdb.d \
	&& ls -lha /docker-entrypoint-initdb.d/

EXPOSE 5432

CMD [ "postgres", "-c", "config_file=/docker-entrypoint-initdb.d/postgresql.conf" ]
