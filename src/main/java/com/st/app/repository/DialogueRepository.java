package com.st.app.repository;

import com.st.app.model.DialogueEntry;
import com.st.app.model.Role;
import com.st.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DialogueRepository  extends JpaRepository<DialogueEntry, Integer> {

    @Query(
            value = "select d.* from dialogues d inner join scripts s " +
                    "on s.id=d.script_id where d.script_id=(:scriptId) and d.id>(:sentenceId) order by id ASC limit 2",
            nativeQuery = true
    )
    List<DialogueEntry> findDialogueEntry(@Param("scriptId") int scriptId, @Param("sentenceId") int sentenceId);

    @Query(
            value = "select d.* from dialogues d inner join scripts s " +
                    "on s.id=d.script_id where d.script_id=(:scriptId) order by id ASC limit 2",
            nativeQuery = true
    )
    List<DialogueEntry> findFirstDialogueEntry(@Param("scriptId") int scriptId);

}
