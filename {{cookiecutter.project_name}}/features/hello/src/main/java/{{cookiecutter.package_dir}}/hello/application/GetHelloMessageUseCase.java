package {{cookiecutter.package}}.hello.application;

import {{cookiecutter.package}}.hello.domain.model.Message;
import {{cookiecutter.package}}.hello.domain.ports.HelloMessageService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GetHelloMessageUseCase {

    private final HelloMessageService helloMessageService;

    public Message execute() {
        return Message.builder().value(helloMessageService.getHelloMessage()).build();
    }
    
}
