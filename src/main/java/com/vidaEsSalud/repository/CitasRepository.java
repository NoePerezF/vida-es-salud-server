
package com.vidaEsSalud.repository;

import com.vidaEsSalud.domain.Citas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitasRepository  extends JpaRepository<Citas, Integer>{
    
}
