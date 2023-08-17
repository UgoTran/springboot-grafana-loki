# Project Spring boot push log to loki(grafana)

- using logback config & push log
- docker-compose: run grafana + loki
- loki doc: https://grafana.com/docs/loki/latest/logql/log_queries/
- java 17 + spring boot 3.1
- spring boot app: 8080
- grafana: 3000
- loki: 3100
- steps build & view log:
    - maven build: mvn clean install
    - java -jar target/sb-log.jar
    - enter grafana: http://localhost:3000/dashboards
    - add datasource loki
    - enter panel select label {my-app-log} > query inspector > run query