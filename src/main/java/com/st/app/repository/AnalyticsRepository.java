package com.st.app.repository;

import com.st.app.model.AnalyticsEntry;
import com.st.app.model.DialogueEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalyticsRepository extends JpaRepository<AnalyticsEntry, Integer>  {
}
