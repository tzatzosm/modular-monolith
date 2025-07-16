package {{cookiecutter.package}}.app.hello

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

import {{cookiecutter.package}}.app.AppBaseSpecification
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc

class HelloSpecification extends AppBaseSpecification {

  @Autowired private MockMvc mvc

  def 'hello endpoint returns greeting message'() {
    when:
    def responseBody = mvc.perform(get('/hello'))
        .andExpect(status().isOk())
        .andReturn()
        .response
        .contentAsString

      then:
      responseBody == '{"value":"Hello, World!"}'
  }

}