FROM minio/minio:RELEASE.2023-12-23T07-19-11Z

ADD ./ssl/minio.crt /root/.minio/certs/public.crt
ADD ./ssl/minio.key /root/.minio/certs/private.key

ENTRYPOINT [ "sleep", "60" ]

CMD [ "server", "/minio/data", "--address", ":6000", "--console-address", ":6061" ]
