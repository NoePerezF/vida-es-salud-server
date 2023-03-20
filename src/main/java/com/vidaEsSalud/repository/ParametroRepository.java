package com.vidaEsSalud.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.vidaEsSalud.domain.Parametro;

public interface ParametroRepository extends JpaRepository<Parametro,Integer>{

    Parametro findByClave(String clave);
    
}
