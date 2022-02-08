package challenge.albo.developer.controller;

import challenge.albo.developer.models.Character;
import challenge.albo.developer.models.Heroe;
import challenge.albo.developer.services.CharacterServiceImp;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin (origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/test.albo.mx/marvel")
public class CharacterController {

    @Autowired
    private CharacterServiceImp characterService;

    @GetMapping("/characters")
    public ResponseEntity<?> index(){

        List<Character> listCharacter = new ArrayList<Character>();
        Map<String, Object> response = new HashMap<>();

        try{
            listCharacter = characterService.findAll();
        }catch (DataAccessException e){
            response.put("message", e.getMostSpecificCause());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        if(listCharacter == null){
            response.put("message", "No hay datos en la DB.");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        List<JSONObject> entities = new ArrayList<JSONObject>();
        for (Character cha : listCharacter) {
            JSONObject entity = new JSONObject();

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            entity.put("last_sync", "Fecha de la última sincronización en " + dateFormat.format(cha.getLastSync()));
            entity.put("Comics", cha.getComic().split(","));
            entity.put("character", cha.getName());
            if(cha.getHeroe() == Heroe.capamerica.getValue()){
                entity.put("heroe", "capamerica");
            }else{
                entity.put("heroe", "ironman");
            }

            entities.add(entity);
        }

        return new ResponseEntity<Object>(entities, HttpStatus.OK);
    }

    @GetMapping("/characters/{heroe}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> show(@PathVariable String heroe){

        Heroe heroeObj = Heroe.valueOf(heroe);
        List<Character> listCharacter = new ArrayList<Character>();
        Map<String, Object> response = new HashMap<>();

        try{
            listCharacter = characterService.findAllByHeroe(heroeObj.getValue());
        }catch (DataAccessException e){
            response.put("message", e.getMostSpecificCause());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        if(listCharacter == null){
            response.put("message", "El Heroe: ".concat(heroe).concat(" no existe en la DB.") );
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        List<JSONObject> entities = new ArrayList<JSONObject>();
        for (Character cha : listCharacter) {
            JSONObject entity = new JSONObject();

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            entity.put("last_sync", "Fecha de la última sincronización en " + dateFormat.format(cha.getLastSync()));
            entity.put("Comics", cha.getComic().split(","));
            entity.put("character", cha.getName());

            entities.add(entity);
        }

        return new ResponseEntity<Object>(entities, HttpStatus.OK);
    }

}
