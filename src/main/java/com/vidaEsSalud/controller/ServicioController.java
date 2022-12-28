
package com.vidaEsSalud.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vidaEsSalud.domain.Citas;
import com.vidaEsSalud.domain.Cliente;
import com.vidaEsSalud.domain.Negocio;
import com.vidaEsSalud.domain.Servicio;
import com.vidaEsSalud.repository.CitasRepository;
import com.vidaEsSalud.repository.ClienteRepository;
import com.vidaEsSalud.repository.NegocioRepository;
import com.vidaEsSalud.repository.ServicioRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin("*")
public class ServicioController {
    @Autowired
    private NegocioRepository repo;
    
    @Autowired
    private ServicioRepository repoServicio;

    @Autowired
    private ClienteRepository repoCliente;

    @Autowired
    private CitasRepository repoCitas;
    
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
    
     @PostMapping("/api/servicios/addcita")
    public String addCitaDesdeNegocio(@RequestBody Servicio servicio) throws JsonProcessingException{
        System.out.println(servicio.getId());
        System.out.println(servicio.getCitas().size());
        Citas cita = servicio.getCitas().get(0);
        servicio = repoServicio.findById(servicio.getId()).get();
        cita.setNegocio(servicio.getNegocio());
        cita.setServicio(servicio);
        servicio.getCitas().add(cita);
        return(mapper.writeValueAsString(repoServicio.save(servicio)));
    }

    @PostMapping("/api/servicios/addcitacliente")
    public String addCitaCliente(@RequestBody Citas cita) throws JsonProcessingException {
        Optional<Servicio> opServicio =  repoServicio.findById(cita.getServicio().getId());
        Optional<Cliente> opCliente = repoCliente.findById(cita.getCliente().getId());
        if(opServicio.isEmpty() || opCliente.isEmpty()) {
            return("Error no existe el servicio o el cliente");
        }
        cita.setNegocio(opServicio.get().getNegocio());
        return(mapper.writeValueAsString(repoCitas.save(cita)));
    }
}
