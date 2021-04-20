FROM hseeberger/scala-sbt:8u222_1.3.5_2.13.1
EXPOSE 9000 9000
WORKDIR /mill
ADD . /mill
CMD sbt run