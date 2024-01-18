FROM postgres:16.1

ADD . /docker-entrypoint-initdb.d/

RUN chmod 600 /docker-entrypoint-initdb.d/ssl/* \
	&& chown -Rv postgres:postgres /docker-entrypoint-initdb.d/ssl \
	&& chown -v postgres:postgres /docker-entrypoint-initdb.d/*.conf \
	&& ls -Rlha /docker-entrypoint-initdb.d/

EXPOSE 5432

CMD [ "postgres", "-c", "config_file=/docker-entrypoint-initdb.d/postgresql.conf" ]
