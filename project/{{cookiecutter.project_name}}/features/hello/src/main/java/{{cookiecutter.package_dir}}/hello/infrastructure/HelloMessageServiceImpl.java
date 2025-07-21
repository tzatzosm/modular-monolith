package {{cookiecutter.package}}.hello.infrastructure;

import org.springframework.stereotype.Service;
import {{cookiecutter.package}}.hello.domain.ports.HelloMessageService;

@Service
public class HelloMessageServiceImpl implements HelloMessageService {

    @Override
    public String getHelloMessage() {
        return "Hello, World!";
    }

    @Override
    public String getHelloMessage(String name) {
        return "Hello, " + name + "!";
    }

}
