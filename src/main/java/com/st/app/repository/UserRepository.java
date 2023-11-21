package com.st.app.repository;

import com.st.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//        User findByName (String name);
        @Query("select * from users where lower(email) = lower(:email) and password = md5(:password)")
        User findByEmail (String email, String password);


}
