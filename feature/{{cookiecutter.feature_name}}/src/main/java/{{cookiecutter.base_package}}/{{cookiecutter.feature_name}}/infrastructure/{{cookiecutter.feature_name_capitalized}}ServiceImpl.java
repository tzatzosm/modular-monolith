package {{cookiecutter.base_package}}.{{cookiecutter.feature_name}}.infrastructure;

import {{cookiecutter.base_package}}.{{cookiecutter.feature_name}}.domain.ports.{{cookiecutter.feature_name_capitalized}}Service;
import org.springframework.stereotype.Service;

@Service
public class {{cookiecutter.feature_name_capitalized}}ServiceImpl implements {{cookiecutter.feature_name_capitalized}}Service {
    
    @Override
    public String getMessage() {
        return "Hello from {{cookiecutter.feature_name}}";
    }

}
