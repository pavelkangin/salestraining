//public class AppDialogueTests {
//}
package com.st.app;

import com.st.app.dao.DialogueService;
import com.st.app.dao.ScriptService;
import com.st.app.dao.UserService;
import com.st.app.dto.DialogueEntryResponse;

import static org.junit.jupiter.api.Assertions.*;

import com.st.app.dto.ScriptInfo;
import com.st.app.model.DialogueEntry;
import com.st.app.model.Script;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class AppDialogueEntryFetchTests {


    @Autowired
    private UserService userService;

    @Autowired
    private DialogueService dialogueService;

    @Autowired
    private ScriptService scriptService;


    Logger logger = LoggerFactory.getLogger(AppApplicationTests.class);


    @Test
    void contextLoads() {

    }
// positive scenarios
    @Test
    //@Disabled
    // get first script's sentence
    void testFirstDialogueEntry(){
        DialogueEntryResponse resp = new DialogueEntryResponse();
        Script script = new Script();
        script.setId(1);
        List<DialogueEntry> entry = dialogueService.fetch(script, 0);
        resp.setEntry(entry.get(0));
        resp.setHasNext(entry.size() > 1);
        assertNotNull(resp.getEntry());
        assertTrue(resp.isHasNext());
        assertNull(resp.getMessage());
    }
    @Test
    //get next script's sentence
    void testNextDialogueEntry() {
        DialogueEntryResponse resp = new DialogueEntryResponse();
        Script script = new Script();
        script.setId(1);
        List<DialogueEntry> entry = dialogueService.fetch(script, 20);
        resp.setEntry(entry.get(0));
        resp.setHasNext(entry.size() > 1);
        assertNotNull(resp.getEntry());
        assertTrue(resp.isHasNext());
        assertNull(resp.getMessage());
    }
    @Test
        //get last script's sentence
    void testLastDialogueEntry() {
        DialogueEntryResponse resp = new DialogueEntryResponse();
        Script script = new Script();
        script.setId(1);
        List<DialogueEntry> entry = dialogueService.fetch(script, 24);
        resp.setEntry(entry.get(0));
        resp.setHasNext(entry.size() > 1);
        assertNotNull(resp.getEntry());
        assertFalse(resp.isHasNext());
        assertNull(resp.getMessage());
    }

// negative scenarios
    @Test
    //  Script Id number doesn't exist
    void testScriptIdNotExist() {
        ScriptInfo scriptInfo = scriptService.validateScriptById(55);
        assertNull(scriptInfo.getScript());
        assertNotNull(scriptInfo.getMessage());
        assertEquals(scriptInfo.getMessage(), "script не найден!");
    }

    @Test
    // wrong Script Id number
    void testWrongScriptId() {
        ScriptInfo scriptInfo = scriptService.validateScriptById(-10);
        assertNull(scriptInfo.getScript());
        assertNotNull(scriptInfo.getMessage());
        assertEquals(scriptInfo.getMessage(), "script не найден!");
    }

}

