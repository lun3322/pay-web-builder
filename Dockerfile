FROM vegardit/graalvm-maven:latest-java17 as builder
WORKDIR /app
COPY . .
RUN mvn -Pnative clean install \
    && mvn -Pnative native:compile -pl pay-web

FROM alpine:latest
RUN apk --no-cache add ca-certificates
WORKDIR /app
COPY --from=builder /app/pay-web/target .
CMD ["./pay-web"]
