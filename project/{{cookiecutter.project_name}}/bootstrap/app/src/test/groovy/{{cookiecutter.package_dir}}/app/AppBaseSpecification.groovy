package {{cookiecutter.package}}.app

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.env.Environment
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.testcontainers.containers.PostgreSQLContainer
import spock.lang.Specification

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class AppBaseSpecification extends Specification {

    @Autowired
    private MockMvc mvc

    @Autowired
    private Environment environment

    private static postgres = new PostgreSQLContainer("postgres:16-alpine")

    static {
        postgres.start()
    }

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    def 'contextLoads'() {
        expect:
        mvc != null
    }

    def 'database is running'() {
        expect:
        postgres.isRunning()
        postgres.jdbcUrl == environment.getProperty('spring.datasource.url')
        postgres.username == environment.getProperty('spring.datasource.username')
        postgres.password == environment.getProperty('spring.datasource.password')
    }

}