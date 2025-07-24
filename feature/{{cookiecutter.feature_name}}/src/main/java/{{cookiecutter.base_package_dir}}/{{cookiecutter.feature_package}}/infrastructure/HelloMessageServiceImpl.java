package {{cookiecutter.package}}.infrastructure;

import org.springframework.stereotype.Service;
import {{cookiecutter.package}}.domain.ports.HelloMessageService;

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
