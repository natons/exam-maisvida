package exam.maisvida.med.br;

import com.fasterxml.jackson.databind.ObjectMapper;
import exam.maisvida.med.br.model.City;
import exam.maisvida.med.br.model.Region;
import exam.maisvida.med.br.service.RegionService;
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

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RegionControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RegionService regionService;

    private String access_token;

    private static final String CLIENT_ID = "clientapp";
    private static final String CLIENT_SECRET = "123456";

    private static final String VALID_USER = "roy";
    private static final String VALID_PASSWORD = "spring";

    @Before
    public void setUp() throws Exception {
        regionService.deleteAll();
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
    public void getAllTest() throws Exception {
        mvc.perform(get("/region/get/all")
                .header("Authorization", "Bearer " + this.access_token))
                .andExpect(status().isOk());
    }

    @Test
    public void saveRegionTest() throws Exception {
        Region region = simpleCreteRegion();

        mvc.perform(post("/region/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(region))
                .header("Authorization", "Bearer " + this.access_token))
                .andExpect(status().isCreated());
    }

    private Region simpleCreteRegion(){
        return new Region("GO",
                Arrays.asList(new City("Goiânia")));
    }

    private Region saveSimpleCreteRegion(){
        return regionService.regionSave(simpleCreteRegion());
    }

    @Test
    public void saveRegionStateAlreadyExistsTest() throws Exception {
        Region region = saveSimpleCreteRegion();
        region.setId(null);

        mvc.perform(post("/region/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(region))
                .header("Authorization", "Bearer " + this.access_token))
                .andExpect(status().isConflict());
    }

    @Test
    public void updateRegionTest() throws Exception {
        Region region = saveSimpleCreteRegion();
        region.setState("TU");

        mvc.perform(post("/region/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(region))
                .header("Authorization", "Bearer " + this.access_token))
                .andExpect(status().isOk());
    }

    @Test
    public void saveRegionTestInvalid() throws Exception {
        Region region = new Region("         ", null);

        mvc.perform(post("/region/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(region))
                .header("Authorization", "Bearer " + this.access_token))
                .andExpect(status().isNotAcceptable());
    }
}
