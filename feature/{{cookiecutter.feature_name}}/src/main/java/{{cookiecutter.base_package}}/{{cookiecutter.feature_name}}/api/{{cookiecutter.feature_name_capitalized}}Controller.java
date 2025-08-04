package {{cookiecutter.base_package}}.{{cookiecutter.feature_name}}.api;

import {{cookiecutter.base_package}}.{{cookiecutter.feature_name}}.domain.ports.{{cookiecutter.feature_name_capitalized}}Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/{{cookiecutter.feature_name}}")
public class {{cookiecutter.feature_name_capitalized}}Controller {

    private final {{cookiecutter.feature_name_capitalized}}Service {{cookiecutter.feature_name}}Service;

    @GetMapping
    public ResponseEntity<String> hello{{cookiecutter.feature_name_capitalized}}() {
        return ResponseEntity.ok({{cookiecutter.feature_name}}Service.getMessage());
    }

}

