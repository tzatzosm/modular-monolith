package {{cookiecutter.package}}.application;

import {{cookiecutter.package}}.domain.model.Message;
import {{cookiecutter.package}}.domain.model.Name;
import {{cookiecutter.package}}.domain.ports.HelloMessageService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GetHelloMessageByNameUseCase {

    private final HelloMessageService helloMessageService;

    public Message execute(Name name) {
        String messageValue = helloMessageService.getHelloMessage(name.value());
        return Message.builder().value(messageValue).build();
    }
    
}
