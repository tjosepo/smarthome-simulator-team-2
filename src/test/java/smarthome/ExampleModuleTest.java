package smarthome;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.javalin.Javalin;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class ExampleModuleTest {
    private static Javalin app;

    @BeforeAll
    static void BeforeExampleTest() {
        app = Javalin.create().start(7000);
        new ExampleModule(app);
    }

    @AfterAll
    static void AfterExampleTest() {
        app.stop();
    }

    @Test
    public void GET_Example_Test() throws UnirestException {
        HttpResponse<String> response = Unirest.get("http://localhost:7000/example").asString();
        assertEquals(response.getCode(), 200, "Status code must be 200");
        assertEquals(response.getBody(), "This is an example", "Body should be \"This is an example\"");
    }
}