
package com.vidaEsSalud.repository;

import com.vidaEsSalud.domain.Negocio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NegocioRepository extends JpaRepository<Negocio, Integer>{
    Negocio findByUsuarioAndIsVerificadoTrue(String usuario);
    Optional<Negocio> findByVerificacion(String verificacion);
}
