package me.kjs.url_shorter.url;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@Transactional
@Testcontainers
@ContextConfiguration(initializers = ShortUrlIntegratedTest.ContainerPropertyInitialize.class)
@DisplayName("URL 통합 테스트")
class ShortUrlIntegratedTest {

    @Container
    static protected GenericContainer rabbitMQContainer = new GenericContainer("rabbitmq:3-management")
            .withExposedPorts(5672, 15672);
    @Container
    static protected GenericContainer redisContainer = new GenericContainer("redis")
            .withExposedPorts(6379);
    @Autowired
    protected ObjectMapper objectMapper;
    protected MockMvc mockMvc;
    @Autowired
    private WebApplicationContext ctx;
    @BeforeEach
    @Profile("default")
    public void setup() {

        mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .alwaysDo(print())
                .build();

    }
    static class ContainerPropertyInitialize implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            Integer rabbitMQMappedPort = rabbitMQContainer.getMappedPort(5672);
            Integer redisMappedPort = redisContainer.getMappedPort(6379);
            String rabbitMQIpAddress = rabbitMQContainer.getContainerIpAddress();
            String redisIpAddress = rabbitMQContainer.getContainerIpAddress();
            TestPropertyValues testPropertyValues = TestPropertyValues.of("spring.rabbitmq.port=" + rabbitMQMappedPort,
                    "spring.rabbitmq.host=" + rabbitMQIpAddress,
                    "spring.redis.port="+redisMappedPort,
                    "spring.redis.host="+redisIpAddress);
            testPropertyValues.applyTo(applicationContext);
        }
    }

    @Test
    @DisplayName("url 생성 테스트")
    void createShortUrlTest() throws Exception {
        ShortUrlForm.Request.Creator creator = ShortUrlForm.Request.Creator.builder()
                .resource("/JsKim4")
                .host("github.com")
                .protocol(Protocol.HTTPS)
                .port(0)
                .build();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/url")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(creator)))
                .andExpect(jsonPath("shortResource").isNotEmpty())
                .andExpect(jsonPath("fullUrl").value("https://github.com/JsKim4"))
                .andReturn();
        MockHttpServletResponse firstResponse = mvcResult.getResponse();
        ShortUrlForm.Response.FindOne firstResult = objectMapper.readValue(firstResponse.getContentAsString(), ShortUrlForm.Response.FindOne.class);

        MvcResult mvcResult2 = mockMvc.perform(MockMvcRequestBuilders.post("/api/url")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(creator)))
                .andExpect(jsonPath("shortResource").isNotEmpty())
                .andExpect(jsonPath("fullUrl").value("https://github.com/JsKim4"))
                .andReturn();
        MockHttpServletResponse secondResponse = mvcResult2.getResponse();
        ShortUrlForm.Response.FindOne secondResult = objectMapper.readValue(secondResponse.getContentAsString(), ShortUrlForm.Response.FindOne.class);
        assertEquals(firstResult.getShortResource(),secondResult.getShortResource());
    }
    
    @Test
    @DisplayName("url 조회 테스트")
    void findOneTest() throws Exception {
        ShortUrlForm.Request.Creator creator = ShortUrlForm.Request.Creator.builder()
                .resource("/JsKim4")
                .host("github.com")
                .protocol(Protocol.HTTPS)
                .port(0)
                .build();
        String fullUrl = "https://github.com/JsKim4";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/url")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(creator)))
                .andExpect(jsonPath("shortResource").isNotEmpty())
                .andExpect(jsonPath("fullUrl").value(fullUrl))
                .andReturn();
        MockHttpServletResponse firstResponse = mvcResult.getResponse();
        ShortUrlForm.Response.FindOne firstResult = objectMapper.readValue(firstResponse.getContentAsString(), ShortUrlForm.Response.FindOne.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/url/{shortResource}",firstResult.getShortResource()))
                .andExpect(jsonPath("fullUrl").value(fullUrl));
    }

    

}