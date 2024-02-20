package com.proyecto.muestra.Class;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "objects")
@EqualsAndHashCode(of = "id")
public class GenericObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private boolean active;

    private int amount;

    private float amountTotal;

    //@Column(unique = true)
    private long identification;

    private String description;

    @Setter
    private char type;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}
