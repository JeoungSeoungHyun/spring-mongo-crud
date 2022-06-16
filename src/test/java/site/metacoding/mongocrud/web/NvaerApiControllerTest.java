package site.metacoding.mongocrud.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import site.metacoding.mongocrud.domain.Naver;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) // 통합테스트
public class NvaerApiControllerTest {

    @Autowired // DI (IoC 컨테이너에 있기 때문에 가능하다)
    private static TestRestTemplate rt;

    // 다른 메서드에서도 header를 사용하기 위해 전역적으로 생성
    private static HttpHeaders headers;

    @BeforeAll // 모든애가 실행되기 전에 실행
    // @BeforeEach // 실행되기 전마다 실행?
    public static void init() {
        headers = new HttpHeaders();
        // 스트링으로 하면 실수를 할 수 있기 때문에 미디어타입으로 만들어놓았다
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void save_테스트() throws JsonProcessingException {
        System.out.println("test1");

        // given => 가짜데이터를 만드는것(만들기)
        Naver naver = new Naver();
        naver.setTitle("스프링1강");
        naver.setCompany("재밌어요");

        ObjectMapper om = new ObjectMapper();
        String content = om.writeValueAsString(naver);

        HttpEntity<String> httpEntity = new HttpEntity<>(content, headers);

        // 모든메서드
        // when => 이걸 실행할거다 이걸 할 때(실행)
        ResponseEntity<String> response = rt.exchange("/navers", HttpMethod.POST, httpEntity, String.class);

        // then => 어떤 결과를?(검증)
        // System.out.println("===================================================");
        // System.out.println(response.getBody());
        // System.out.println(response.getHeaders());
        // System.out.println(response.getStatusCode());
        // System.out.println(response.getStatusCode().is2xxSuccessful());
        // System.out.println("===================================================");
        // assertTrue(response.getStatusCode().is2xxSuccessful());
        // assertFalse(response.getStatusCode().is2xxSuccessful());
        DocumentContext dc = JsonPath.parse(response.getBody());
        System.out.println(dc.jsonString());
        String title = dc.read("$.title");
        System.out.println(title);
        // 공백일 수도 있으니 직접 확인한다?
        assertEquals("스프링1강", title);
    }

    @Test
    public void findAll_테스트() {
        System.out.println("test2");
        // given

        // when

        // body가 없으니 header도 불필요
        ResponseEntity<String> response = rt.exchange("/navers", HttpMethod.GET, null, String.class);

        // then
        // System.out.println(response.getBody());
        // DocumentContext dc = JsonPath.parse(response.getBody());
        // System.out.println(dc.jsonString());
        // String title = dc.read("$.[0].title");
        // System.out.println(title);
        // 공백일 수도 있으니 직접 확인한다?
        // 공백일 수도 있으니 직접 확인한다? => 미친짓 상태코드만 확인하면 된다. 배포시 데이터가 없을수있으니
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    // // rt가 null인지 테스트하기 위해
    // @Test
    // public void rtIsNull() {
    // // assertNull(rt); // null이 아니면 true
    // assertNotNull(rt); // null이 아니면 true
    // }
}
