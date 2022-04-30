
package com.vidaEsSalud.repository;

import com.vidaEsSalud.domain.Colonia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColoniaRepository extends JpaRepository<Colonia, Integer>{
    
}
