package org.reggiemcdonald;

import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reggiemcdonald.api.controller.AppUserController;
import org.reggiemcdonald.api.controller.NumberImageController;
import org.reggiemcdonald.api.controller.ScalingController;
import org.reggiemcdonald.api.model.api.AppUserApiModel;
import org.reggiemcdonald.api.model.api.NumberImageApiModel;
import org.reggiemcdonald.api.model.api.ScaleApiModel;
import org.reggiemcdonald.api.model.request.NumberImagePutRequestModel;
import org.reggiemcdonald.api.model.request.NumberImageRequestModel;
import org.reggiemcdonald.persistence.entity.AppUserEntity;
import org.reggiemcdonald.persistence.entity.NumberImageEntity;
import org.reggiemcdonald.persistence.repo.AppUserRepository;
import org.reggiemcdonald.persistence.repo.NumberImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(properties = {
        // TODO: parameterize this
        "TEST_DB=neuralserv_test_db",
        "POSTGRES_PASSWORD=pgpassword"
})
public class IntegrationTest {
    private final String USER_NAME = "test-user";
    private final String PASSWORD = "test-password";

    @Autowired
    private AppUserController appUserController;

    @Autowired
    private NumberImageController numberImageController;

    @Autowired
    private ScalingController scalingController;

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private NumberImageRepository numberImageRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    @After
    public void cleanup() {
        AppUserEntity entity = userRepository.findByUsername(USER_NAME);
        if (entity != null) {
            userRepository.delete(entity);
        }
    }

    @Before
    public void runBefore() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void testIntegration() throws Exception {
        AppUserApiModel model = new AppUserApiModel(USER_NAME, PASSWORD);
        Gson gson = new Gson();
        mockMvc.perform(post("/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(model)))
                .andExpect(status().isCreated());
        MvcResult result = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(model)))
                .andExpect(status().isOk())
                .andReturn();
        String token = result.getResponse().getContentAsString();
        AppUserEntity entity = userRepository.findByUsername(USER_NAME);
        assertNotNull(entity);

        InputStream is =
                new FileInputStream(new File("src/test/java/org/reggiemcdonald/test.png"));
        result = mockMvc.perform(MockMvcRequestBuilders.multipart("/scale/image-scale")
                .file(new MockMultipartFile("file", is))
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn();
        ScaleApiModel scaleApiModel = gson.fromJson(result.getResponse().getContentAsString(), ScaleApiModel.class);
        double[][] img = scaleApiModel.getImage();
        assertEquals(28,img.length);
        assertEquals(28, img[0].length);

        NumberImageRequestModel niReqModel =
                new NumberImageRequestModel(img, 6);
        result = mockMvc.perform(post("/number")
                .header("authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(niReqModel)))
                .andExpect(status().isOk())
                .andReturn();
        NumberImageApiModel apiModel =
                gson.fromJson(result.getResponse().getContentAsString(), NumberImageApiModel.class);
        assertEquals(5, apiModel.getLabel());
        assertEquals(new Integer(6), apiModel.getExpectedLabel());
        Iterable<NumberImageEntity> numberImageEntities = numberImageRepository.findAll();
        boolean found = false;
        for (NumberImageEntity numberImageEntity : numberImageEntities) {
            found = apiModel.getId() == numberImageEntity.getId();
        }
        assertTrue(found);

        NumberImagePutRequestModel update =
                new NumberImagePutRequestModel(apiModel.getId(), apiModel.getImageWeights(), 5);

        mockMvc.perform(
                put("/number")
                    .header("authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(gson.toJson(update)))
                    .andExpect(status().isOk());

        numberImageEntities = numberImageRepository.findAll();
        found = false;
        for (NumberImageEntity numberImageEntity : numberImageEntities) {
            found = apiModel.getId() == numberImageEntity.getId();
        }
        assertTrue(found);
    }
}
