package ca.cmpt213.Cmpt_project.controller;

import ca.cmpt213.Cmpt_project.model.Tokimon;
import ca.cmpt213.Cmpt_project.model.TokimonManager;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * TokimonController class models the controller
 * between of the server requests. Data includes
 * the TokimonManager. It supports GET, POST, DELETE
 * operations and returns a response status for each.
 */
@RestController
public class TokimonController {
    TokimonManager manager = new TokimonManager();

    @GetMapping("/api/tokimon/all")
    public ArrayList<Tokimon> getTokimons(HttpServletResponse response) {
        response.setStatus(200);
        System.out.println("Get all tokimons");
        return manager.getTokimons();
    }

    @RequestMapping("/api/tokimon/{id}")
    public Tokimon getTokimonAtId(@PathVariable String id, HttpServletResponse response) {
        Tokimon tempToki = manager.getAtId(id);
        // No such tokimon on the server
        if (tempToki == null) {
            response.setStatus(404);
            System.out.println("No tokimons found at id " + id);
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "The tokimon is not found"
            );
        }
        response.setStatus(200);
        return tempToki;
    }

    @PostMapping("/api/tokimon/add")
    public void addTokimon(@RequestBody Tokimon newToki, HttpServletResponse response) {
        manager.addToki(newToki);
        // Add a new tokimon
        response.setStatus(201);
        System.out.println("New tokimon id: " + newToki.getId() + ", name: " + newToki.getName());

        // tokimon.json should be changed
        try {
            updateJson();
        } catch (JSONException e) {
            System.err.println("Error updating tokimon.json");
        }
    }

    @PostMapping("/api/tokimon/change/{id}")
    public void updateTokimon(@RequestBody Tokimon newToki, @PathVariable String id, HttpServletResponse response) {
        // Update the tokimon at id
        manager.updateSomeTokimon(newToki, id);
        response.setStatus(201);

        // tokimon.json should be changed
        try {
            updateJson();
        } catch (JSONException e) {
            System.err.println("Error updating tokimon.json");
        }
    }

    @DeleteMapping("api/tokimon/{id}")
    public void deleteTokimon (@PathVariable String id, HttpServletResponse response) {
        Tokimon tempToki = manager.getAtId(id);
        // No such tokimon has been found
        if (tempToki == null) {
            response.setStatus(404);
            System.out.println("No tokimons found at id " + id);
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "The tokimon is not found"
            );
        }
        response.setStatus(204);
        manager.deleteTokimon(id);

        // tokimon.json should be changed
        try {
            updateJson();
        } catch (JSONException e) {
            System.err.println("Error updating tokimon.json");
        }
    }

    @PostConstruct
    public void initAll() {
        // Initially 3 tokimons is added
        manager.addToki(new Tokimon("Zuko", 60, 100,
                0, 100,100,0, 80,"Red"));
        manager.addToki(new Tokimon("Aang", 50, 60,
                100, 30,20,60, 100,"Yellow"));
        manager.addToki(new Tokimon("Toph", 70, 40,
                90, 120,40,0, 90,"Green"));

        // Update the json file
        try {
            updateJson();
        } catch (JSONException e) {
            System.err.println("Error updating tokimon.json");
        }
    }

    private void updateJson() throws JSONException {
        JSONArray tokimonsList = new JSONArray();

        for (Tokimon tokimon : manager.getTokimons()) {
            JSONObject object = new JSONObject();

            // Attributes of a single tokimon
            object.put("id", tokimon.getId());
            object.put("name", tokimon.getName());
            object.put("weight", tokimon.getWeight());
            object.put("height", tokimon.getHeight());
            object.put("abilityFly", tokimon.getAbilityFly());
            object.put("abilityFire", tokimon.getAbilityFire());
            object.put("abilityElctrify", tokimon.getAbilityElctrify());
            object.put("abilityFreeze", tokimon.getAbilityFreeze());
            object.put("strength", tokimon.getStrength());
            object.put("color", tokimon.getColor());

            tokimonsList.put(object);
        }

        try (FileWriter fileWriter = new FileWriter("data/tokimon.json")) {
            // Writing onto tokimon.json
            fileWriter.write(tokimonsList.toString());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
