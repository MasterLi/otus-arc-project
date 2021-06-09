FROM php:7.1-apache

MAINTAINER Synergyteam <ivakhin@synergyteam.msk.ru>

# Set timezone ENV and install tzdata
ENV TZ=Europe/Moscow
ENV APACHE_DOCUMENT_ROOT /var/www/html/public
ENV PHP_INI_DIR /usr/local/etc/php
ARG DEBIAN_FRONTEND=noninteractive

#местонахождение файлов статики
COPY index.php /var/www/html/public/
COPY .htaccess /var/www/html/public/

#установка доп. модулей в PHP
RUN apt-get update \
    && apt-get clean \
    && php -m \
    && a2enmod -q rewrite \
    && a2enmod -q headers \
    && sed -ri -e 's!/var/www/html!'"${APACHE_DOCUMENT_ROOT}"'!g' /etc/apache2/sites-available/*.conf \
    && sed -ri -e 's!/var/www/!'"${APACHE_DOCUMENT_ROOT}"'!g' /etc/apache2/apache2.conf /etc/apache2/conf-available/*.conf \
    && cd /var/www/html/ \
    && echo "log_errors = On" >> ${PHP_INI_DIR}/conf.d/php-log.ini \
    && echo "error_log = /dev/stderr" >> ${PHP_INI_DIR}/conf.d/php-log.ini

#порт
ARG SERVER_PORT=80
ENV SERVER_PORT=$SERVER_PORT

#порт
EXPOSE $SERVER_PORT
