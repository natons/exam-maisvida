package exam.maisvida.med.br;


import exam.maisvida.med.br.service.RoleService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private RoleService roleService;

    private String access_token;

    private static final String CLIENT_ID = "clientapp";
    private static final String CLIENT_SECRET = "123456";

    private static final String VALID_USER = "roy";
    private static final String VALID_PASSWORD = "spring";

    @Before
    public void setUp() throws Exception {
        access_token = getAccessToken(VALID_USER, VALID_PASSWORD);
    }

    private String getAccessToken(String username, String password) throws Exception {
        String authorization = "Basic "
                + new String(Base64Utils.encode(String.format("%s:%s", CLIENT_ID, CLIENT_SECRET).getBytes()));

        String content = mvc
                .perform(
                        post("/oauth/token")
                                .header("Authorization", authorization)
                                .contentType(
                                        MediaType.APPLICATION_FORM_URLENCODED)
                                .param("username", username)
                                .param("password", password)
                                .param("grant_type", "password")
                                .param("scope", "read write")
                                .param("client_id", CLIENT_ID)
                                .param("client_secret", CLIENT_SECRET))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.access_token", is(notNullValue())))
                .andExpect(jsonPath("$.token_type", is(equalTo("bearer"))))
                .andExpect(jsonPath("$.refresh_token", is(notNullValue())))
                .andExpect(jsonPath("$.expires_in", is(greaterThan(4000))))
                .andExpect(jsonPath("$.scope", is(equalTo("read write"))))
                .andReturn().getResponse().getContentAsString();

        return content.substring(17, 53);
    }

    @Test
    public void userSaveTest() throws Exception{
        String user = "{\n" +
                "  \"name\": \"teste\",\n" +
                "  \"login\": \"teste\",\n" +
                "  \"password\": \"teste\",\n" +
                "  \"roles\": [{\n" +
                "    \"name\": \"ROLE_TEST\"\n" +
                "  }, {\n" +
                "    \"name\": \"ROLE_TEST2\"\n" +
                "  }]\n" +
                "}";

        mvc.perform(post("/user/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(user)
                .header("Authorization", "Bearer " + this.access_token))
                .andExpect(status().isCreated());
    }
}
