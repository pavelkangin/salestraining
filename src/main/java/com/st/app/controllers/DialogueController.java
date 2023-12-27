package com.st.app.controllers;

import com.st.app.dao.DialogueService;
import com.st.app.dao.RecognitionService;
import com.st.app.dao.ScriptService;
import com.st.app.dao.SynthService;
import com.st.app.dto.*;
import com.st.app.model.DialogueEntry;
import com.st.app.model.Script;
import com.st.app.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class DialogueController {

    @Autowired
    private DialogueService dialogueService;

    @Autowired
    private SynthService synthService;

    @Autowired
    private RecognitionService recognitionService;

    @Autowired
    private ScriptService scriptService;

    Logger logger = LoggerFactory.getLogger(DialogueController.class);

    @PostMapping("/api/dialogue/fetch")
    public DialogueEntryResponse fetch(@RequestBody DialogueProgressInfo info, HttpSession session) {
        // return next (or first) dialogue entry as text
        DialogueEntryResponse resp = new DialogueEntryResponse();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            logger.info("DC fetch. empty user in session, not authenticated");
            resp.setStatus(-1);
            resp.setMessage("Пользователь не аутентифицирован!");
            return resp;
        }

        ScriptInfo scriptInfo = scriptService.validateScriptById(info.getScriptId());

        if (scriptInfo.getScript() != null) {
            List<DialogueEntry> entry = dialogueService.fetch(scriptInfo.getScript(), info.getSentenceId());
            if (entry.isEmpty()) {
                logger.info("Empty Script, scriptId is " + info.getScriptId() + "sentenceId is " + info.getSentence());
                resp.setStatus(-1);
                resp.setMessage("Can't find sentence");
                return resp;
            } else {
                resp.setEntry(entry.get(0));
                resp.setHasNext(entry.size() > 1);
            }
        }
        else {
            resp.setStatus(-1);
            resp.setMessage(scriptInfo.getMessage());
        }
        return resp;
    }

    @PostMapping("/api/dialogue/synth")
    public void synth(@RequestBody DialogueProgressInfo info, HttpServletRequest request, HttpServletResponse response, HttpSession session){
        //TODO implement calling synth API and stream audio back to the frontend
        //TODO change content type to audio/mp3

    }

    @PostMapping("/api/dialogue/recognize")
    public DefaultResponse recognize(@RequestBody DialogueProgressInfo info, MultipartFile audio, HttpSession session){
        //TODO return successful status if recognition is completed, or special status that recognition was failed
        //TODO update script statistics

        return null;
    }



}
