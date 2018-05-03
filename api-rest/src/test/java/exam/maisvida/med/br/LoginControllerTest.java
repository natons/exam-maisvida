package exam.maisvida.med.br;

import com.fasterxml.jackson.databind.ObjectMapper;
import exam.maisvida.med.br.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String VALID_USER = "roy";
    private static final String VALID_PASSWORD = "spring";

    @Test
    public void authenticateTest() throws Exception {
        User user = new User("name", VALID_USER, VALID_PASSWORD);
        mvc.perform(post("/login/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    public void authenticateInvalidUserTest() throws Exception {
        User user = new User("name", "invalid", "invalid");
        mvc.perform(post("/login/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void authenticateNoUserTest() throws Exception {
        User user = new User("     ", "    ", null);
        mvc.perform(post("/login/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isNotAcceptable());
    }
}
