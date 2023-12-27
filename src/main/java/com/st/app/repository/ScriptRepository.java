package com.st.app.repository;

import com.st.app.model.Role;
import com.st.app.model.Script;
import com.st.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ScriptRepository extends JpaRepository<Script, Integer> {
    @Query(
            value = "select * from scripts where id=(:scriptId)",
            nativeQuery = true
    )
    Script findScriptById(@Param("scriptId") int scriptId);
}
