package net.myfirst.webapp;

import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class App {

    public static void main(String[] args) {
        staticFiles.location("/public");
      //  staticFiles.location("/templates");
        port(8080);

        final String[] msg = new String[1];

        get("/greet", (request, response) -> "Hi and Welcome");
        get("/greet/:username", (request, response) -> "Hi " + request.params(":username"));
        get("/greet/:username/language/:language", (request, response) -> {
            if (request.params(":language").equals("french")){
                msg[0] = "Bonjour " + request.params(":username");
            } else if(request.params(":language").equals("afrikaans")){
                msg[0] = "Hallo " + request.params(":username");
            } else {
                msg[0] = "Hi " + request.params(":username");
            }
            return msg[0];
        });

        post("/greet", (req, res) -> {
            if (req.queryParams("username").isEmpty()){
                msg[0] = "Hi ";
            } else{
                msg[0]= "Hi " + req.queryParams("username");
            }
            return msg[0];
        });

        get("/hello", (request, response) -> {
            Map<String, Object> userNames = new HashMap<>();
            Map<String, Object> map = new HashMap<>();
            return new HandlebarsTemplateEngine()
                    .render(new ModelAndView(map, "hello.handlebars"));
        });

        post("/hello",(request, response) -> {
            Map<String, Object> map = new HashMap<>();
            Map<String, Object> userNames = new HashMap<>();
            String name = request.queryParams("username");
            StringBuilder build = new StringBuilder();

            build.append("Hi how are you today, ");
            build.append(name);
            // create the greeting message
            String greeting = build.toString();

            // put it in the map which is passed to the template - the value will be merged into the template
            map.put("greeting", greeting);
            userNames.put("users", name);

            return new HandlebarsTemplateEngine()
                    .render(new ModelAndView(map, "hello.handlebars"));
        } );
    }
}
