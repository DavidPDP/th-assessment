FROM tomcat:8.5.70-jdk11-corretto

LABEL COMPANY="FonYou"
LABEL MAINTAINER="johan.ballesteros@outlook.com"
LABEL APPLICATION="Employees (Payroll) App" 

# Install Wait For
RUN curl -LJO https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh
RUN chmod +x wait-for-it.sh

#Data & Config - Persistent Mount Point
COPY ./target/core-1.0.0.war /usr/local/tomcat/webapps
