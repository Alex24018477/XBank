package com.alex.xbank.repo;
//Repository работают с Entity

import com.alex.xbank.entity.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<CustomUser, Long> {

    //добавляем нестандартные методы:

//        @Query("SELECT u FROM CustomUser u where u.login = :login")//ищет и возвращает пользователя в базе по логину
//    CustomUser findByLogin(@Param("login") String login);
//
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false " +
            "END FROM CustomUser u WHERE u.login = :login")
    boolean existsByLogin(@Param("login") String login);//ищет пользователя в базе, возвращает true - false

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false " +
            "END FROM CustomUser u WHERE u.email = :email")
    boolean existsByEmail(@Param("email") String email);//ищет пользователя в базе, возвращает true - false

    @Query("SELECT u FROM CustomUser u where u.email = :email")
    CustomUser findByEmail(@Param("email") String email);

    CustomUser findByLastName(@Param("lastName") String lastName);

    CustomUser findByLogin(@Param("Login") String login);


}
