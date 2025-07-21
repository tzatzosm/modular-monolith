package {{cookiecutter.package}}.hello.domain.model;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record Name(@NonNull String value) { }
