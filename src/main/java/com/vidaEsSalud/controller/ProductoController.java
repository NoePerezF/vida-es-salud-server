
package com.vidaEsSalud.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vidaEsSalud.domain.Negocio;
import com.vidaEsSalud.domain.Producto;
import com.vidaEsSalud.repository.NegocioRepository;
import com.vidaEsSalud.repository.ProductoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductoController {
    
    @Autowired
    private NegocioRepository repo;
    
    @Autowired
    private ProductoRepository repoProducto;
    
    private ObjectMapper mapper = new ObjectMapper();
    
    @PostMapping("/api/productos/getproductos")
    public String getProductos(@RequestBody Negocio negocio) throws JsonProcessingException{
        negocio = repo.findById(negocio.getId()).get();
        List<Producto> lista = negocio.getProductos();
        return(mapper.writeValueAsString(lista));
    }
    
    @PostMapping("/api/productos/addproducto")
    public String addProducto(@RequestBody Negocio negocio) throws JsonProcessingException{
        Negocio aux = repo.findById(negocio.getId()).get();
        List<Producto> productos = aux.getProductos();
        
        negocio.getProductos().get(0).setNegocio(negocio);
        
        productos.add(negocio.getProductos().get(0));
        aux.setProductos(productos);
        return(mapper.writeValueAsString(repo.save(aux)));
    }
    
    @PostMapping("/api/productos/updateproducto")
    public String updateProducto(@RequestBody Producto producto) throws JsonProcessingException{
        Producto aux = repoProducto.findById(producto.getId()).get();
        producto.setNegocio(aux.getNegocio());
        return(mapper.writeValueAsString(repoProducto.save(producto)));
        
    }
}
