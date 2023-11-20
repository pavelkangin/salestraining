package com.st.app.repository;

import com.st.app.model.DialogueEntry;
import com.st.app.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DialogueRepository  extends JpaRepository<DialogueEntry, Integer> {



}
