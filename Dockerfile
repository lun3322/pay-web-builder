FROM vegardit/graalvm-maven:latest-java17 as builder
WORKDIR /app
COPY . .
RUN mvn -Pnative clean install \
    && mvn -Pnative native:compile -pl pay-web --initialize-at-build-time=org.apache.commons.logging.LogFactory

FROM alpine:latest
RUN apk --no-cache add ca-certificates
WORKDIR /root/
COPY --from=builder /app .
CMD ["./app"]
