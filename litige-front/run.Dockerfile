#https://medium.com/@wkrzywiec/build-and-run-angular-application-in-a-docker-container-b65dbbc50be8

FROM nginx:1.17.1-alpine
COPY docker/certificat/key.pem /etc/nginx/certs/key.pem
COPY docker/certificat/certificate.pem /etc/nginx/certs/certificate.pem
RUN rm /etc/nginx/conf.d/default.conf
COPY docker/nginx.conf /etc/nginx/conf.d/default.conf
COPY dist/litige-front /usr/share/nginx/html
# expose port 4200
# run nginx
CMD ["nginx", "-g", "daemon off;"]