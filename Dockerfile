FROM ubuntu:latest
LABEL authors="baptiste"

ENTRYPOINT ["top", "-b"]
