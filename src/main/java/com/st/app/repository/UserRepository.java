package com.st.app.repository;

import com.st.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(
            value = "select * from users where trim(lower(email))=trim(lower(:email))",
            nativeQuery = true
    )
    User findByEMail(@Param("email") String email);
    @Query(
            value = "select * from users where trim(lower(email))=trim(lower(:email)) and password=md5(:pass)",
            nativeQuery = true
    )
    User findByEMailAndPassword(@Param("email") String email,@Param("pass") String pass);


    @Query(
            value = "select md5(:pass)",
            nativeQuery = true
    )
    String encodePass(String pass);

    @Modifying
    @Query(
            value = "insert into users (email,name,pass,role_id) values (:email,:name,md5(:pass),:role)",
            nativeQuery = true
    )
    void register(@Param("email") String email,@Param("name") String name,@Param("pass") String pass,@Param("role") Integer role);

}
