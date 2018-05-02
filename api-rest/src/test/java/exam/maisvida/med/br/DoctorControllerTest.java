package exam.maisvida.med.br;

import com.fasterxml.jackson.databind.ObjectMapper;
import exam.maisvida.med.br.model.Doctor;
import exam.maisvida.med.br.service.CityService;
import exam.maisvida.med.br.service.DoctorService;
import exam.maisvida.med.br.service.RegionService;
import exam.maisvida.med.br.service.SpecialtyService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DoctorControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private SpecialtyService specialtyService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private DoctorService doctorService;

    private String access_token;

    @Before
    public void setUp() throws Exception {
        doctorService.deleteAll();
        access_token = getAccessToken("roy", "spring");
    }

    private String getAccessToken(String username, String password) throws Exception {
        String authorization = "Basic "
                + new String(Base64Utils.encode("clientapp:123456".getBytes()));
        String contentType = MediaType.APPLICATION_JSON + ";charset=UTF-8";

        // @formatter:off
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
                                .param("client_id", "clientapp")
                                .param("client_secret", "123456"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.access_token", is(notNullValue())))
                .andExpect(jsonPath("$.token_type", is(equalTo("bearer"))))
                .andExpect(jsonPath("$.refresh_token", is(notNullValue())))
                .andExpect(jsonPath("$.expires_in", is(greaterThan(4000))))
                .andExpect(jsonPath("$.scope", is(equalTo("read write"))))
                .andReturn().getResponse().getContentAsString();

        // @formatter:on

        return content.substring(17, 53);
    }

    @Test
    public void getAllTest() throws Exception {
        mvc.perform(get("/doctor/get/all")
                .header("Authorization", "Bearer " + this.access_token))
                .andExpect(status().isOk());
    }

    @Test
    public void saveDoctorTest() throws  Exception {
        Doctor doctor =
                new Doctor("Teste", "Teste",
                        "teste@test.com",
                        true,
                        Doctor.STATUS_AVAILABLE,
                        specialtyService.specialtyAll().get(0),
                        regionService.regionAll().get(0),
                        regionService.regionAll().get(0).getCities().get(0));

        mvc.perform(post("/doctor/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(doctor))
                .header("Authorization", "Bearer " + this.access_token))
                .andExpect(status().isCreated());
    }

    @Test
    public void saveDoctorTestInvalid() throws  Exception {
        Doctor doctor =
                new Doctor("     ", "Teste",
                        "teste2@test.com",
                        true,
                        Doctor.STATUS_AVAILABLE,
                        specialtyService.specialtyAll().get(0),
                        regionService.regionAll().get(0),
                        regionService.regionAll().get(0).getCities().get(0));

        mvc.perform(post("/doctor/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(doctor))
                .header("Authorization", "Bearer " + this.access_token))
                .andExpect(status().isNotAcceptable());
    }
}
