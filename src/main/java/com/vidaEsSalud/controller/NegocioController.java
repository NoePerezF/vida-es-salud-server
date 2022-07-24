
package com.vidaEsSalud.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.hash.Hashing;
import com.vidaEsSalud.domain.Citas;
import com.vidaEsSalud.domain.Negocio;
import com.vidaEsSalud.repository.NegocioRepository;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NegocioController {
    @Autowired
    private NegocioRepository repo;
    
    private ObjectMapper mapper = new ObjectMapper();
    @GetMapping("/api/negocio/getnegocios")
    public String getNegocios() throws JsonProcessingException{
        return (mapper.writeValueAsString(repo.findAll()));
    }
    
    @PostMapping("/api/negocio/login")
    public String login(@RequestBody Negocio negocio) throws JsonProcessingException{
        Negocio aux = repo.findByUsuario(negocio.getUsuario());
        if(aux != null && aux.getContrasena().compareTo(negocio.getContrasena()) == 0)
            return(mapper.writeValueAsString(aux));
        return("Error en usuario o contrasena");
    }
    
    @PostMapping("/api/negocio/addnegocio")
    public String addNegocio(@RequestBody Negocio negocio) throws JsonProcessingException{
        
        return(mapper.writeValueAsString(repo.save(negocio)));   
    }
    
    @PostMapping("/api/negocio/updatenegocio")
    public String updateNegocio(@RequestBody Negocio negocio) throws JsonProcessingException{
        Negocio aux = repo.findById(negocio.getId()).get();
        aux.setDireccion(negocio.getDireccion());
        aux.setNombre(negocio.getNombre());
        aux.setHorario(negocio.getHorario());
        return(mapper.writeValueAsString(repo.save(aux)));
    }
    
    @PostMapping("/api/negocio/addcitadesdenegocio")
    public String addCitaDesdeNegocio(@RequestBody Negocio negocio) throws JsonProcessingException{
        Citas cita = negocio.getCitas().get(0);
        negocio = repo.findById(negocio.getId()).get();
        cita.getNegocio().setId(negocio.getId());
        negocio.getCitas().add(cita);
        return(mapper.writeValueAsString(repo.save(negocio)));
    }
    
    @GetMapping("/api/negocio/getcitas")
    public String getCitas(@RequestParam int id) throws JsonProcessingException{
        Negocio negocio = repo.findById(id).get();
        return(mapper.writeValueAsString(negocio.getCitas()));
    }
}
