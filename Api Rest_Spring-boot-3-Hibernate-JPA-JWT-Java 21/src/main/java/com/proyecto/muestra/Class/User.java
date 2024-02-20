package com.proyecto.muestra.Class;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@EqualsAndHashCode(of = "id")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String userName;

    private String password;

    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, targetEntity = GenericObject.class, fetch = FetchType.EAGER)
    private List<GenericObject> genericObjects;

    public void setGenericObject(GenericObject genericObject){
        genericObjects.add(genericObject);
    }
}
