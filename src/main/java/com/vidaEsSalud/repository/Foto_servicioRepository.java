
package com.vidaEsSalud.repository;

import com.vidaEsSalud.domain.Foto_servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Foto_servicioRepository extends JpaRepository<Foto_servicio, Integer>{
    
}
