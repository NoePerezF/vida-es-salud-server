
package com.vidaEsSalud.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.hash.Hashing;
import com.vidaEsSalud.domain.Cliente;
import com.vidaEsSalud.repository.ClienteRepository;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClienteController {
    
    @Autowired
    private ClienteRepository repo;
    
    private ObjectMapper mapper = new ObjectMapper();
    
    @GetMapping("/api/cliente/getclientes")
    public String getClientes() throws JsonProcessingException{
        return(mapper.writeValueAsString(repo.findAll())); 
    }
    
    @PostMapping("/api/cliente/login")
    public String login(@RequestBody Cliente cliente) throws JsonProcessingException{
        Cliente aux = repo.findByUsuario(cliente.getUsuario()); 
        if(aux != null && aux.getContrasena().compareTo(cliente.getContrasena()) == 0)
            return(mapper.writeValueAsString(aux));
        return("Error en usuario o contrasena");
    }
    
    @PostMapping("/api/cliente/addcliente")
    public String addCliente(@RequestBody Cliente cliente) throws JsonProcessingException{
        
        return(mapper.writeValueAsString(repo.save(cliente)));
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
