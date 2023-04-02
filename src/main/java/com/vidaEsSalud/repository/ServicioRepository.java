
package com.vidaEsSalud.repository;

import com.vidaEsSalud.domain.Servicio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Integer>{

    List<Servicio> findByNegocioId(Integer negocioId);
    
}
