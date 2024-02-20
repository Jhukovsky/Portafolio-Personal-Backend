package com.proyecto.muestra.Repository;

import com.proyecto.muestra.Class.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByuserName(String userName);

    User findByid(long id);

}
