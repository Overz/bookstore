FROM adminer:4.8.1

ADD . /var/www/html/

ENTRYPOINT [ "php", "-S", "[::]:9999", "-t", "/var/www/html" ]
