
package com.vidaEsSalud.repository;

import com.vidaEsSalud.domain.Negocio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NegocioRepository extends JpaRepository<Negocio, Integer>{
    Negocio findByUsuario(String usuario);
}
