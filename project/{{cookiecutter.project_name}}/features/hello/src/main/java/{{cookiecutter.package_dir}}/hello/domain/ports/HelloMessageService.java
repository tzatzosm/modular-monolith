package {{cookiecutter.package}}.hello.domain.ports;

public interface HelloMessageService {
    String getHelloMessage();

    String getHelloMessage(String name);
}
