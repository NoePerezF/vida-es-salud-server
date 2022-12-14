
package com.vidaEsSalud.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.hash.Hashing;
import com.vidaEsSalud.domain.Cliente;
import com.vidaEsSalud.repository.ClienteRepository;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class ClienteController {
    
    @Autowired
    private ClienteRepository repo;
    
    private ObjectMapper mapper = new ObjectMapper();
    
    @GetMapping("/api/cliente/getclientes")
    public String getClientes() throws JsonProcessingException{
        return(mapper.writeValueAsString(repo.findAll())); 
    }
    
    @PostMapping("/api/cliente/login")
    public ResponseEntity<?> login(@RequestBody Cliente cliente) throws JsonProcessingException{
        Cliente aux = repo.findByUsuario(cliente.getUsuario()); 
        String sha256hex = Hashing.sha256()
                            .hashString(cliente.getContrasena(), StandardCharsets.UTF_8)
                            .toString();
        if(aux != null && aux.getContrasena().compareTo(sha256hex) == 0)
            return new ResponseEntity<Cliente>(aux, HttpStatus.OK);
        return new ResponseEntity<>("Error en usuario o contrasena",HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @PostMapping("/api/cliente/addcliente")
    public ResponseEntity<?> addCliente(@RequestBody Cliente cliente) throws JsonProcessingException{
        String sha256hex = Hashing.sha256()
                            .hashString(cliente.getContrasena(), StandardCharsets.UTF_8)
                            .toString();
        cliente.setContrasena(sha256hex);
        return new ResponseEntity<Cliente>(repo.save(cliente), HttpStatus.OK);
    }
    
    @PostMapping("/api/cliente/updatecliente")
    public String updateCliente(@RequestBody Cliente cliente) throws JsonProcessingException{
        Cliente aux = repo.findById(cliente.getId()).get();
        aux.setNombre(cliente.getNombre());
        aux.setApellido_paterno(cliente.getApellido_paterno());
        aux.setApellido_materno(cliente.getApellido_materno());
        aux.setTelefono(cliente.getTelefono());
        return(mapper.writeValueAsString(repo.save(aux)));
    }
    
}
