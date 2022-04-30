
package com.vidaEsSalud.repository;

import com.vidaEsSalud.domain.Codigo_postal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Codigo_postalRepository extends JpaRepository<Codigo_postal, Integer>{
    
}
