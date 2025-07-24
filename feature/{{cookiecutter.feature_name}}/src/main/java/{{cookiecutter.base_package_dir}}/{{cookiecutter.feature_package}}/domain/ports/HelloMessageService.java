package {{cookiecutter.package}}.domain.ports;

public interface HelloMessageService {
    String getHelloMessage();

    String getHelloMessage(String name);
}
