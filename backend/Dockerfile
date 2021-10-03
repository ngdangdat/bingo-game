FROM eed3si9n/sbt:jdk8-alpine AS builder

WORKDIR /tmp

ADD app app
ADD conf conf
ADD public public
ADD project project
ADD build.sbt build.sbt

RUN sbt dist

FROM adoptopenjdk/openjdk8:alpine-slim

COPY --from=builder /tmp/target/universal/bingo-game-0.1.zip /tmp/

WORKDIR /tmp

RUN unzip /tmp/bingo-game-0.1.zip

WORKDIR /usr/local/bingoapp/

RUN mv /tmp/bingo-game-0.1/* /usr/local/bingoapp
RUN rm /tmp/bingo-game-0.1.zip

RUN apk add --update bash && rm -rf /var/cache/apk/*

# CMD ["sh","sleep", "1000000000"]
ENTRYPOINT ["sh", "-c", "/usr/local/bingoapp/bin/bingo-game"]
