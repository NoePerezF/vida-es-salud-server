
package com.vidaEsSalud.repository;

import com.vidaEsSalud.domain.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Integer>{
    
}
