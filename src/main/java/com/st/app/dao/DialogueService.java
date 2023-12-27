package com.st.app.dao;

import com.st.app.controllers.ScriptsController;
import com.st.app.dto.DialogueEntryResponse;
import com.st.app.dto.PagingInfo;
import com.st.app.model.DialogueEntry;
import com.st.app.model.Script;
import com.st.app.repository.DialogueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DialogueService {

    @Autowired
    private DialogueRepository repository;

    Logger logger = LoggerFactory.getLogger(DialogueService.class);

    public List<DialogueEntry> fetch(Script script, int sentenceId){
        //implement fetching dialogue entries depending on started script and sentence info
        if (sentenceId == 0) {
            return repository.findFirstDialogueEntry(script.getId());
        } else {
            return repository.findDialogueEntry(script.getId(),sentenceId);
        }
    }
}
