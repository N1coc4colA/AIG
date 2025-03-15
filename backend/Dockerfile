# Define the base image
FROM alpine:latest

# Update
RUN apk update

# Add Maven
RUN mkdir /app
RUN mkdir -p /build/maven
WORKDIR /build/maven

# Download Maven
RUN wget https://dlcdn.apache.org/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.tar.gz
RUN tar -zxvf apache-maven-3.9.9-bin.tar.gz

# Install JDK
RUN apk add openjdk17

# Setup for build
COPY . /build
WORKDIR /build

# Build our project, but we also need to add the Maven env vars
RUN M2_HOME=/build/maven/apache-maven-3.9.9 PATH=$PATH:$M2_HOME/bin mvn clean package

# Copy the result
RUN cp ./target/aig-backend.jar /app/aig-backend.jar

# Clean up
RUN rm -rf /build

WORKDIR /app

# Now run it at startup
ENTRYPOINT ["java", "-jar", "/app/aig-backend.jar"]
