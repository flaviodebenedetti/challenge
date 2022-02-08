package challenge.albo.developer.controller;

import challenge.albo.developer.models.Colaborator;
import challenge.albo.developer.models.Heroe;
import challenge.albo.developer.services.ColaboratorServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin (origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/test.albo.mx/marvel")
public class ColaboratorController {

    @Autowired
    private ColaboratorServiceImp colaboradorService;

    @GetMapping("/colaborators")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> index(){

        List<Colaborator> listColaborator = new ArrayList<Colaborator>();
        Map<String, Object> response = new HashMap<>();

        try{
            listColaborator = colaboradorService.findAll();
        }catch (DataAccessException e){
            response.put("message", e.getMostSpecificCause());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        if(listColaborator == null){
            response.put("message", "No hay datos en la DB.");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        List<JSONObject> entities = new ArrayList<JSONObject>();
        for (Colaborator col : listColaborator) {
            JSONObject entity = new JSONObject();

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            entity.put("last_sync", "Fecha de la última sincronización en " + dateFormat.format(col.getLastSync()));
            entity.put("editors", col.getEditors().split(","));
            entity.put("writers", col.getWriters().split(","));
            entity.put("colorists", col.getColorists().split(","));
            if(col.getHeroe() == Heroe.capamerica.getValue()){
                entity.put("heroe", "capamerica");
            }else{
                entity.put("heroe", "ironman");
            }
            entities.add(entity);
        }

        return new ResponseEntity<Object>(entities, HttpStatus.OK);
    }

    @GetMapping("/colaborators/page/{page}")
    @ResponseStatus(HttpStatus.OK)
    public Page<Colaborator> index(@PathVariable Integer page){

        Pageable pegeable = PageRequest.of(page, 5);
        return colaboradorService.findAll(pegeable);
    }

    @GetMapping("/colaborators/{id}/{heroe}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> show(@PathVariable Long id, @PathVariable String heroe){

        Heroe heroeObj = Heroe.valueOf(heroe);
        Colaborator colaborator = null;
        Map<String, Object> response = new HashMap<>();

        try{
            colaborator = colaboradorService.findById(id);
        }catch (DataAccessException e){
            response.put("message", e.getMostSpecificCause());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        if(colaborator == null){
            response.put("message", "El ID: ".concat(id.toString()).concat(" no existe en la DB.") );
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Colaborator>(colaborator, HttpStatus.OK);
    }

    @GetMapping("/colaborators/{heroe}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getTeam(@PathVariable String heroe){

        Heroe heroeObj = Heroe.valueOf(heroe);
        List<Colaborator> listColaborator = new ArrayList<Colaborator>();
        Map<String, Object> response = new HashMap<>();

        try{
            listColaborator = colaboradorService.findAllByHeroe(heroeObj.getValue());
        }catch (DataAccessException e){
            response.put("message", e.getMostSpecificCause());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        if(listColaborator == null){
            response.put("message", "El Heroe: ".concat(heroe).concat(" no existe en la DB.") );
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        List<JSONObject> entities = new ArrayList<JSONObject>();
        for (Colaborator col : listColaborator) {
            JSONObject entity = new JSONObject();

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            entity.put("last_sync", "Fecha de la última sincronización en " + dateFormat.format(col.getLastSync()));
            entity.put("editors", col.getEditors().split(","));
            entity.put("writers", col.getWriters().split(","));
            entity.put("colorists", col.getColorists().split(","));
            entities.add(entity);
        }

        return new ResponseEntity<Object>(entities, HttpStatus.OK);
    }

    /**
     * @Valid intersecta el request validando con la anotaciones del Entity y si tiene errores los retorno
     */
    @PostMapping("/colaborators")
    public ResponseEntity<?> create(@Valid @RequestBody Colaborator colaborator, BindingResult result){

        Colaborator colaboratorSave = null;
        Map<String, Object> response = new HashMap<>();

        if(result.hasErrors()){
            List<String> errors = new ArrayList<>();

            for(FieldError err: result.getFieldErrors()){
                errors.add("El campo '" + err.getField() + "' " + err.getDefaultMessage());
            }

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try{
            colaboratorSave = colaboradorService.save(colaborator);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar el insert en la DB.");
            response.put("error", e.getMostSpecificCause());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "Los colaboradores fueron agregados correctamente.");
        response.put("colaborator", colaborator);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/colaborators/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Colaborator colaborator, BindingResult result){

        Colaborator colaboratorActual = null;
        Colaborator colaboratorUpdate = null;
        Map<String, Object> response = new HashMap<>();

        if(result.hasErrors()){
            List<String> errors = new ArrayList<>();

            for(FieldError err: result.getFieldErrors()){
                errors.add("El campo '" + err.getField() + "' " + err.getDefaultMessage());
            }

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try{
            colaboratorActual = colaboradorService.findById(colaborator.getId());
        }catch (DataAccessException e){
            response.put("message", e.getMostSpecificCause());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        if(colaboratorActual == null){
            response.put("message", "Error: no se pudo editar, el ID: ".concat(colaborator.getId().toString()).concat(" no existe en la DB."));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try{
            colaboratorActual.setColorists(colaborator.getColorists());
            colaboratorActual.setEditors(colaborator.getEditors());
            colaboratorActual.setWriters(colaborator.getWriters());
            colaboratorActual.setHeroe(colaborator.getHeroe());

            colaboratorUpdate = colaboradorService.save(colaboratorActual);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar el insert en la DB.");
            response.put("error", e.getMostSpecificCause());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "Los colaboradores se actualizaron correctamente.");
        response.put("colaborator", colaboratorUpdate);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/colaborators/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){

        Map<String, Object> response = new HashMap<>();

        try{
            colaboradorService.deleteById(id);
        }catch (DataAccessException e){
            response.put("message", "Error al realizar el delete en la DB.");
            response.put("error", e.getMostSpecificCause());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "Los colaboradores se eliminaron correctamente.");

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

}
