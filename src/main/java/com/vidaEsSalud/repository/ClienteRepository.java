
package com.vidaEsSalud.repository;

import com.vidaEsSalud.domain.Cliente;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
   Cliente findByUsuarioAndIsVerificadoTrue(String usuario);
   Optional<Cliente> findByVerificacion(String verificacion);
}
