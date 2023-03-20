
package com.vidaEsSalud.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "negocio")
public class Negocio implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @Column(nullable = false)
    private String nombre;
    private String usuario;
    private String contrasena;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "direccion")
    private Direccion direccion;
    private String horario;
    private String email;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval=true, mappedBy = "negocio")
    private List<Producto> productos;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval=true, mappedBy = "negocio")
    private List<Servicio> servicios;
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,mappedBy = "negocio")
    private List<Citas> citas;
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,mappedBy = "negocio")
    private List<Telefono_negocio> telefonos;
    @Column(nullable = true)
    private boolean isVerificado;
    @Column
    private String verificacion;
}
