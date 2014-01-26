FROM ubuntu:12.04

RUN apt-get install -y build-essential
RUN apt-get install -y haproxy
RUN apt-get install -y ruby1.9.1 ruby1.9.1-dev rubygems1.9.1

RUN gem install sinatra synapse

ADD . /app

EXPOSE 4567

CMD ["ruby", "/app/server.rb"]
