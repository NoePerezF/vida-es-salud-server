
package com.vidaEsSalud.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import javax.annotation.Nullable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "citas")
public class Citas implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int Id;
    
    private Timestamp fecha;
    
    @ManyToOne
    @JoinColumn(name = "cliente")
    @JsonIgnore
    @Nullable
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(name = "negocio" )
    @JsonIgnore
    private Negocio negocio;
    
    @ManyToOne
    @JoinColumn(name = "servicio" )
    @JsonIgnore
    private Servicio servicio;
    
    @Nullable
    private String nombre;
    
    @ManyToOne
    @JoinColumn(name = "estatus")
    @JsonIgnore
    private EstatusCita estatus;
    
}
