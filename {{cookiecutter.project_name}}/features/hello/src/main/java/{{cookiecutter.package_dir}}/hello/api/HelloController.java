package {{cookiecutter.package}}.hello.api;

import {{cookiecutter.package}}.hello.application.GetHelloMessageUseCase;
import {{cookiecutter.package}}.hello.domain.model.Message;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/hello")
public class HelloController {

    private final GetHelloMessageUseCase getHelloMessageUseCase;

    @GetMapping
    public ResponseEntity<Message> getHelloMessage() {
        return ResponseEntity.ok(getHelloMessageUseCase.execute());
    }

}

