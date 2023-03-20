
package com.vidaEsSalud.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "cliente")
public class Cliente implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String nombre;
    private String apellido_paterno;
    private String apellido_materno;
    private String usuario;
    private String contrasena;
    private String email;
    private String telefono;
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,mappedBy = "cliente")
    private List<Citas> citas;
    @Column(nullable = true)
    private boolean isVerificado;
    @Column(nullable = true)
    private String verificacion;
}
