FROM phusion/baseimage:latest

MAINTAINER Markus Günther <markus.guenther@gmail.com>

ENV DEBIAN_FRONTEND noninteractive
ENV SCALA_VERSION 2.11
ENV KAFKA_VERSION 0.11.0.0
ENV KAFKA_HOME /opt/kafka_"$SCALA_VERSION"-"$KAFKA_VERSION"

# Install Oracle Java 8, some utilities and Kafka
RUN \
  echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | debconf-set-selections && \
  add-apt-repository -y ppa:webupd8team/java && \
  apt-get update && \
  apt-get install -y oracle-java8-installer && \
  apt-get install -y wget supervisor dnsutils curl jq coreutils docker net-tools && \
  rm -rf /var/lib/apt/lists/* && \
  rm -rf /var/cache/oracle-jdk8-installer && \
  apt-get clean && \
  wget -q http://apache.mirrors.spacedump.net/kafka/"$KAFKA_VERSION"/kafka_"$SCALA_VERSION"-"$KAFKA_VERSION".tgz -O /tmp/kafka_"$SCALA_VERSION"-"$KAFKA_VERSION".tgz && \
  tar xfz /tmp/kafka_"$SCALA_VERSION"-"$KAFKA_VERSION".tgz -C /opt && \
  rm /tmp/kafka_"$SCALA_VERSION"-"$KAFKA_VERSION".tgz

VOLUME ["/kafka"]

ADD kafka-start /usr/bin/kafka-start
ADD kafka-create-topics /usr/bin/kafka-create-topics
ADD supervisord.conf /etc/supervisor/conf.d/

EXPOSE 9092

CMD ["/sbin/my_init", "kafka-start"]