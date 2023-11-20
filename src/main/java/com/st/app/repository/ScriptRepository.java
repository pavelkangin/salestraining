package com.st.app.repository;

import com.st.app.model.Role;
import com.st.app.model.Script;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScriptRepository extends JpaRepository<Script, Integer> {
}
