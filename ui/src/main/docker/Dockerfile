FROM java:8
VOLUME /home/volume/gplucky
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo 'Asia/Shanghai' >/etc/timezone
RUN mkdir /app
ADD ui-1.0-SNAPSHOT.jar /app/app.jar
ADD runboot.sh /app/
RUN bash -c 'touch /app/app.jar'
WORKDIR /app
RUN chmod a+x runboot.sh
CMD /app/runboot.sh