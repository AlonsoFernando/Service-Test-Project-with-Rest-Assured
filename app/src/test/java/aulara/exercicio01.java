package aulara;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class exercicio01 {

    @BeforeClass
    public static void preCondition() {
        baseURI = "http://localhost";
        port = 3000;
    }

    @Test
    public String exercicio01() {
        //Letra A
        when()
                .get("/usuarios")
                .then()
                .statusCode(HttpStatus.SC_OK);

        //Letra B
        String ex1_userID = given()
                .body("{\n" +
                        "  \"nome\": \"Exercicio 01\",\n" +
                        "  \"email\": \"exercicio01@qa.com.br\",\n" +
                        "  \"password\": \"teste\",\n" +
                        "  \"administrador\": \"true\"\n" +
                        "}")
                .contentType(ContentType.JSON)
        .when()
                .post("usuarios")
        .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("message", is("Cadastro realizado com sucesso"))
                .extract().path("_id");

        //Letra C
        given()
                .pathParam("_id", ex1_userID)
        .when()
                .get("/usuarios/{_id}")
        .then()
                .statusCode(HttpStatus.SC_OK)
                .body("nome", is("Exercicio 01"))
                .body("email", is("exercicio01@qa.com.br"))
                .body("password", is("teste"))
                .body("administrador", is("true"));

        //Letra D
        given()
                .pathParam("_id", ex1_userID)
        .when()
                .delete("/usuarios/{_id}")
        .then()
                .statusCode(HttpStatus.SC_OK)
                .body("message", is("Registro excluído com sucesso"));
        return ex1_userID;
    }
}
