
package com.vidaEsSalud.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
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
@Table(name = "telefno_negocio")
public class Telefono_negocio implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String telefono;
    @ManyToOne
    @JoinColumn(name = "negocio")
    @JsonIgnore
    private Negocio negocio;
}
