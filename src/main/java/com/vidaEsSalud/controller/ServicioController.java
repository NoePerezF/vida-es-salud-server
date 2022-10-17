
package com.vidaEsSalud.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vidaEsSalud.domain.Negocio;
import com.vidaEsSalud.domain.Servicio;
import com.vidaEsSalud.repository.NegocioRepository;
import com.vidaEsSalud.repository.ServicioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServicioController {
    @Autowired
    private NegocioRepository repo;
    
    @Autowired
    private ServicioRepository repoServicio;
    
    private ObjectMapper mapper = new ObjectMapper();
    
    @PostMapping("/api/servicios/getservicios")
    public String getProductos(@RequestBody Negocio negocio) throws JsonProcessingException{
        negocio = repo.findById(negocio.getId()).get();
        List<Servicio> lista = negocio.getServicios();
        return(mapper.writeValueAsString(lista));
    }
    
    @PostMapping("/api/servicios/addservicio")
    public String addProducto(@RequestBody Negocio negocio) throws JsonProcessingException{
        Negocio aux = repo.findById(negocio.getId()).get();
        List<Servicio> servicios = aux.getServicios();
        
        negocio.getServicios().get(0).setNegocio(negocio);
        
        servicios.add(negocio.getServicios().get(0));
        aux.setServicios(servicios);
        return(mapper.writeValueAsString(repo.save(aux)));
    }
    
    @PostMapping("/api/servicios/updateservicio")
    public String updateProducto(@RequestBody Servicio servicio) throws JsonProcessingException{
        Servicio aux = repoServicio.findById(servicio.getId()).get();
        servicio.setNegocio(aux.getNegocio());
        return(mapper.writeValueAsString(repoServicio.save(servicio)));
        
    }
    
    @GetMapping("/api/servicios/getallservicios")
    public ResponseEntity<List<Servicio>> getAllServicios(){
        return new ResponseEntity<>(repoServicio.findAll(),HttpStatus.OK);
    }
}
