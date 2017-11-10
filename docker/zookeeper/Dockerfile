FROM phusion/baseimage:latest

MAINTAINER Markus Guenther <markus.guenther@gmail.com>

ENV ZK_VERSION 3.4.8-1
ENV ZK_HOME /usr/share/zookeeper

RUN apt-get update && \
    apt-get install -y zookeeper=$ZK_VERSION supervisor dnsutils && \
    rm -rf /var/lib/apt/lists/* && \
    apt-get clean && \
    apt-cache policy zookeeper

VOLUME ["/zookeeper"]

ADD supervisord.conf /etc/supervisor/conf.d/

EXPOSE 2181 2888 3888

CMD ["/sbin/my_init", "supervisord"]