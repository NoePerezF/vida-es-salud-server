
package com.vidaEsSalud.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "estatus_cita")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EstatusCita implements Serializable{
    
    @Id
    private Integer id;
    
    private String nombre;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval=true, mappedBy = "estatus")
    @JsonIgnore
    private List<Citas> citas;
}
