package kr.allcll.backend.support.sse;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import kr.allcll.backend.config.AdminConfigStorage;
import kr.allcll.backend.domain.seat.SeatService;
import kr.allcll.backend.domain.seat.SeatStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "app.scheduling.enabled=false") // 테스트에서 스케줄러 비활성화
class SseServiceTest {

    private static final Logger log = LoggerFactory.getLogger(SseServiceTest.class);

    @Autowired
    private SseService sseService;

    @Autowired
    private AdminConfigStorage adminConfigStorage;

    @Autowired
    private SeatStorage seatStorage;

    @MockitoBean
    private SeatService seatService;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("커넥션이 허용되지 않을 경우 좌석 정보를 전달할 수 없다.")
    void sendNonMajorSeatInfoExceptionTest() {
        // given
        adminConfigStorage.connectionClose();

        // when && then
        RestAssured.given()
            .when()
            .get("/api/connect")
            .then()
            .statusCode(400);
    }

    @DisplayName("SSE를 연결하고, 최초 메시지를 받는다.")
    @Test
    void sseConnectionTest() {
        // given
        adminConfigStorage.connectionOpen();

        // when
        Response response = RestAssured.given()
            .accept("text/event-stream")
            .when()
            .get("/api/connect")
            .then()
            .statusCode(200)
            .extract()
            .response();

        // then
        SseTestHelper.assertResponseContainsMessage(response, "success");
    }

    @DisplayName("동시에 여러 클라이언트와 SSE 연결을 유지하고, 모두에게 메시지를 전달한다.")
    @Test
    void ssePropagationTest() {
        // given
        adminConfigStorage.connectionOpen();
        Response response1 = RestAssured.given()
            .accept("text/event-stream")
            .when()
            .get("/api/connect")
            .then()
            .statusCode(200)
            .extract()
            .response();

        Response response2 = RestAssured.given()
            .accept("text/event-stream")
            .when()
            .get("/api/connect")
            .then()
            .statusCode(200)
            .extract()
            .response();

        // when
        sseService.propagate("message", "Hello, SSE!");

        // then
        String expected = "retry:1000\n"
            + "event:connection\n"
            + "data:success\n"
            + "\n"
            + "retry:1000\n"
            + "event:message\n"
            + "data:\"Hello, SSE!\"\n"
            + "\n";
        SseTestHelper.assertResponseIsEqualsToMessage(response1, expected);
        SseTestHelper.assertResponseIsEqualsToMessage(response2, expected);
    }

    private static class SseTestHelper {

        private static final long TIMEOUT = 1000;

        public static void assertResponseIsEqualsToMessage(Response response, String message) {
            String body = response.getBody().asString();
            try (BufferedReader reader = new BufferedReader(new StringReader(body))) {
                String eventReceived = readResponse(reader);
                assertThat(eventReceived).isEqualTo(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public static void assertResponseContainsMessage(Response response, String... messages) {
            String body = response.getBody().asString();
            try (BufferedReader reader = new BufferedReader(new StringReader(body))) {
                String eventReceived = readResponse(reader);
                assertThat(eventReceived).contains(messages);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private static String readResponse(BufferedReader reader) throws IOException {
            StringBuilder lines = new StringBuilder();
            String line;
            long startTime = System.currentTimeMillis();
            while ((line = reader.readLine()) != null) {
                log.info("Received: {}", line);
                lines.append(line).append("\n");
                if (System.currentTimeMillis() - startTime > TIMEOUT) {
                    break;
                }
            }
            return lines.toString();
        }
    }
}
