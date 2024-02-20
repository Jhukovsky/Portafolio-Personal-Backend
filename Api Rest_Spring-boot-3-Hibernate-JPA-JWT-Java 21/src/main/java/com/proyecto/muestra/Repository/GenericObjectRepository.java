package com.proyecto.muestra.Repository;

import com.proyecto.muestra.Class.GenericObject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenericObjectRepository extends JpaRepository<GenericObject,Long> {

    GenericObject findByid(Long id);

}
