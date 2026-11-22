# legacy-orders

Sample app for practising a migration:

    Phase 0: Java 8  + Spring Boot 2.5.15   (this project, as delivered)
    Phase 1: Java 8  + Spring Boot 2.7.18
    Phase 2: Java 17 + Spring Boot 2.7.18

## Run (Java 8)

    mvn clean verify
    mvn spring-boot:run          # or: mvn package && ./run.sh

Basic auth: admin / admin123

    curl -u admin:admin123 localhost:8080/api/orders
    curl -u admin:admin123 localhost:8080/api/orders/1/xml
    curl -u admin:admin123 localhost:8080/api/orders/1/receipt
    curl -u admin:admin123 localhost:8080/api/orders/1/ref
    curl -u admin:admin123 -H 'Content-Type: application/json' \
         -d '{"customerName":"Test","value":10.5}' localhost:8080/api/orders
    curl localhost:8080/actuator/health
    open http://localhost:8080/swagger-ui/index.html

## Planted problems (answer key: try to find them yourself first)

| # | Where | Breaks in | Symptom | Fix |
|---|-------|-----------|---------|-----|
| 1 | OrderService <-> NotificationService field injection | Phase 1 (Boot 2.6+) | BeanCurrentlyInCreationException | Break the cycle (constructor injection / remove back-reference); `spring.main.allow-circular-references=true` only as a stopgap |
| 2 | SwaggerConfig + springfox 3.0.0 | Phase 1 | NPE in documentationPluginsBootstrapper | `spring.mvc.pathmatch.matching-strategy=ant_path_matcher`, real fix: springdoc-openapi |
| 3 | Order.value column | Phase 1 (H2 2.x) | SQL syntax error creating table `orders` | `@Column(name = "\"value\"")`, rename the column, or `;NON_KEYWORDS=VALUE` in JDBC URL |
| 4 | SecurityConfig extends WebSecurityConfigurerAdapter | Phase 1 (deprecation) | Warning only | Convert to a `SecurityFilterChain` bean |
| 5 | OrderXml (JAXB, javax.xml.bind) | Phase 2 | Compile error / NoClassDefFoundError | Check `mvn dependency:tree`; add jakarta.xml.bind-api + jaxb-runtime (BOM-managed) if missing |
| 6 | OrderService `@PostConstruct` | Phase 2 | package javax.annotation does not exist | Add jakarta.annotation-api (BOM-managed, 1.3.x line) if missing |
| 7 | Base64Util uses sun.misc.BASE64Encoder | Phase 2 | Compile error | `java.util.Base64` |
| 8 | ReportWriter.finalize() | Phase 2 (warnings) | Deprecation / removal warning | AutoCloseable + try-with-resources (+ Cleaner as safety net) |
| 9 | ReportWriter FileWriter charset | Phase 2 | ReportWriterTest fails if default charset is not UTF-8 | `Files.newBufferedWriter(path, UTF_8)` or `-Dfile.encoding=UTF-8` |
| 10 | ReceiptFormatterTest | Phase 2 (CLDR) | Expected "...2026 3:45" but was "...2026, 3:45" | Fix expectation, or better, use an explicit pattern |
| 11 | run.sh / Dockerfile flags | Phase 2 | Unrecognized VM option 'UseConcMarkSweepGC' | Remove CMS/PermSize, use G1 and `-Xlog:gc*` |
| 12 | Lombok / Mockito / JaCoCo | Phase 2 | Only if you pin old versions | Use BOM-managed versions |

## Suggested workflow

1. `git init && git add . && git commit -m "baseline"`, and run `mvn dependency:tree > deps-before.txt`.
2. Phase 1: change parent version to 2.7.18, add `spring-boot-properties-migrator`, fix #1-#4.
3. Commit, then Phase 2: `<java.version>17</java.version>`, JDK 17, fix #5-#11.
4. Commit after each phase so you can `git diff` and explain each change in an interview.
