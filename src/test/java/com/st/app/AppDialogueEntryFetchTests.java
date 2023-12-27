//public class AppDialogueTests {
//}
package com.st.app;

import com.st.app.dao.DialogueService;
import com.st.app.dao.UserService;
import com.st.app.dto.DialogueEntryResponse;

import static org.junit.jupiter.api.Assertions.*;

import com.st.app.model.Script;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class AppDialogueEntryFetchTests {


    @Autowired
    private UserService userService;

    @Autowired
    private DialogueService dialogueService;

    Logger logger = LoggerFactory.getLogger(AppApplicationTests.class);


    @Test
    void contextLoads() {

    }

    @Test
    @Disabled
    void testFirstDialogueEntry(){
        DialogueEntryResponse resp = new DialogueEntryResponse();
        Script script = new Script();
        script.setId(1);
        dialogueService.fetch(script, 1);

        assertNotNull(resp.getEntry());
        assertNull(resp.getMessage());

    }

}

