
package com.vidaEsSalud.repository;

import com.vidaEsSalud.domain.Telefono_negocio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Telefono_negocioRepository extends JpaRepository<Telefono_negocio, Integer>{
    
}
