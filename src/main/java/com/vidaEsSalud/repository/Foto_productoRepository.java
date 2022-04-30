
package com.vidaEsSalud.repository;

import com.vidaEsSalud.domain.Foto_producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Foto_productoRepository extends JpaRepository<Foto_producto, Integer>{
    
}
