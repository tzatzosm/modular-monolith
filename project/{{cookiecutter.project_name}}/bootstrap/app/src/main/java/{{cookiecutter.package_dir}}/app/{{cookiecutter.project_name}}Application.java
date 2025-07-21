package {{cookiecutter.package}}.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "{{cookiecutter.package}}")
@SpringBootApplication
public class {{cookiecutter.project_name}}Application {

    public static void main(String[] args) {
        SpringApplication.run({{cookiecutter.project_name}}Application.class, args);
    }

}
