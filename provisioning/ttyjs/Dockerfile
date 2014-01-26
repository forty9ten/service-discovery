FROM ubuntu

ADD install /install
RUN /install
EXPOSE 7777
CMD ["/node_modules/.bin/tty.js", "-p", "7777"]
