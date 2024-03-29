
package com.vidaEsSalud.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.hash.Hashing;
import com.vidaEsSalud.domain.Citas;
import com.vidaEsSalud.domain.Direccion;
import com.vidaEsSalud.domain.Negocio;
import com.vidaEsSalud.repository.DireccionRepository;
import com.vidaEsSalud.repository.NegocioRepository;
import com.vidaEsSalud.repository.ParametroRepository;
import com.vidaEsSalud.service.MailServie;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin("*")
public class NegocioController {
    @Autowired
    private NegocioRepository repo;

    @Autowired
    private DireccionRepository repoDireccion;

    @Autowired
    private MailServie mailServie;

    @Autowired
    private ParametroRepository parametroRepository;
    
    private ObjectMapper mapper = new ObjectMapper();
    @GetMapping("/api/negocio/getnegocios")
    public String getNegocios() throws JsonProcessingException{
        return (mapper.writeValueAsString(repo.findAll()));
    }
    
    @PostMapping("/api/negocio/login")
    public ResponseEntity<?> login(@RequestBody Negocio negocio) throws JsonProcessingException{
        Negocio aux = repo.findByUsuarioAndIsVerificadoTrue(negocio.getUsuario());
        String sha256hex = Hashing.sha256()
                            .hashString(negocio.getContrasena(), StandardCharsets.UTF_8)
                            .toString();
        if(aux != null && aux.getContrasena().compareTo(sha256hex) == 0)
            return(new ResponseEntity<>(aux,HttpStatus.OK));
        return(new ResponseEntity<>("Error en usuario o contrasena",HttpStatus.BAD_REQUEST));
    }
    
    @PostMapping("/api/negocio/addnegocio")
    public ResponseEntity<?> addNegocio(@RequestBody Negocio negocio) throws JsonProcessingException{
        if(repo.findByUsuario(negocio.getUsuario()).isPresent()){
            return new ResponseEntity<>("Ya existe un usuario registrado con ese nombre de usuario",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(repo.findByEmail(negocio.getEmail()).isPresent()){
            return new ResponseEntity<>("Ya existe un usuario registrado con ese correo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String sha256hex = Hashing.sha256()
                            .hashString(negocio.getContrasena(), StandardCharsets.UTF_8)
                            .toString();
                            negocio.setContrasena(sha256hex);
                            negocio.setIsVerificado(true);
        String verificacion = RandomStringUtils.randomAlphanumeric(64);
        while(repo.findByVerificacion(verificacion).isPresent()){
            verificacion = RandomStringUtils.randomAlphanumeric(64);
        }
        negocio.setVerificacion(verificacion);
        mailServie.sendEmail("VES", negocio.getEmail(),"Verificacion email", "Enlace de verificacion: " + parametroRepository.findByClave("DOMAIN_NAME").getValor()+"/verificacion/"+verificacion, null);
        return new ResponseEntity<Negocio>(repo.save(negocio), HttpStatus.OK);
    }
    
    @PostMapping("/api/negocio/updatenegocio")
    public String updateNegocio(@RequestBody Negocio negocio) throws JsonProcessingException{
        Negocio aux = repo.findById(negocio.getId()).get();
        if(aux.getDireccion() == null) {
            aux.setDireccion(new Direccion());
        }
        aux.getDireccion().setCalle(negocio.getDireccion().getCalle());
        aux.getDireccion().setNumero(negocio.getDireccion().getNumero());
        aux.getDireccion().setNumero_interio(negocio.getDireccion().getNumero_interio());
        repoDireccion.save(aux.getDireccion());
        aux.setNombre(negocio.getNombre());
        aux.setHorario(negocio.getHorario());
        return(mapper.writeValueAsString(repo.save(aux)));
    }
    
    @PostMapping("/api/negocio/addcitadesdenegocio")
    public String addCitaDesdeNegocio(@RequestBody Negocio negocio) throws JsonProcessingException{
        Citas cita = negocio.getCitas().get(0);
        negocio = repo.findById(negocio.getId()).get();
        cita.setNegocio(new Negocio());
        cita.getNegocio().setId(negocio.getId());
        negocio.getCitas().add(cita);
        return(mapper.writeValueAsString(repo.save(negocio)));
    }
    
    @GetMapping("/api/negocio/getcitas")
    public String getCitas(@RequestParam int id) throws JsonProcessingException{
        Negocio negocio = repo.findById(id).get();
        return(mapper.writeValueAsString(negocio.getCitas()));
    }
    
    @GetMapping("/api/negocio/getcitaspordia")
    public String getCitasPorDia(@RequestParam int id,@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date dia) throws JsonProcessingException{
        Negocio negocio = repo.findById(id).get();
        List<Citas> citasAll = negocio.getCitas();
        if(citasAll.isEmpty()){
            return(mapper.writeValueAsString(citasAll));
        }
        List<Citas> citasDia = new ArrayList<>();
        for(Citas cita : citasAll){
            if(cita.getFecha().toLocalDateTime().getDayOfYear() == (new Timestamp(dia.getTime())).toLocalDateTime().getDayOfYear()){
               citasDia.add(cita);
            }
        }
        return(mapper.writeValueAsString(citasDia));
    }
    @GetMapping("/api/negocio/getcitaspormes")
    public String getCitasPorMes(@RequestParam int id,@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date dia) throws JsonProcessingException{
        Negocio negocio = repo.findById(id).get();
        List<Citas> citasAll = negocio.getCitas();
        if(citasAll.isEmpty()){
            return(mapper.writeValueAsString(citasAll));
        }
        List<Citas> citasDia = new ArrayList<>();
        for(Citas cita : citasAll){
            if(cita.getFecha().toLocalDateTime().getMonthValue() == (new Timestamp(dia.getTime())).toLocalDateTime().getMonthValue()){
               citasDia.add(cita);
            }
        }
        return(mapper.writeValueAsString(citasDia));
    }

    @GetMapping("/api/negocio/gerhora")
    public String getHora() {
        LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("UTC-6"));
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return(dateTime.format(df));
    }

    @GetMapping("/api/negocio/getnegocio/{id}")
    public ResponseEntity<?> getNegocio(@PathVariable("id") int id) throws JsonProcessingException{
        Optional<Negocio> negocio = repo.findByIdAndIsVerificadoTrue(id);
        if(negocio.isPresent())
            return (new ResponseEntity<>(negocio.get(),HttpStatus.OK)); 
        return (new ResponseEntity<>("{mensaje: 'No se encontro el negocio'}",HttpStatus.NOT_FOUND));
  
    }
}
