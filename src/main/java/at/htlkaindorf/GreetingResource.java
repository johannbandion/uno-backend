package at.htlkaindorf;

import at.htlkaindorf.beans.Fruit;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        Fruit fruit = new Fruit("Halllo", "Ballo");

        ObjectMapper mapper = new ObjectMapper();
        String fruitString = null;
        try {
            fruitString = mapper.writeValueAsString(fruit);
        } catch (JsonProcessingException e) {
            System.out.println("Failed mapping");
        }

        System.out.printf(fruitString);

        return "Hello RESTEasy";
    }
}