FROM nginx:1.22.1

RUN mkdir /usr/share/nginx/html/admin-ui

ENV SERVER_PORT=8000

EXPOSE ${SERVER_PORT}

ADD admin-ui.conf /etc/nginx/conf.d/
ADD dist /usr/share/nginx/html/admin-ui
