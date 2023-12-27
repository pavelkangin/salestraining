package com.st.app;

import com.st.app.dao.ScriptService;
import com.st.app.dto.PagingInfo;
import com.st.app.model.Script;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AppScriptTests {
    @Autowired
    private ScriptService scriptService;

    @Test
    void getAllScriptsSuccessful(){
        Script script1 = new Script();
        script1.setName("Автосалон");
        script1.setDescription("Скрипт для автосалона");
        scriptService.create(script1);

        Script script2 = new Script();
        script2.setName("Агенство недвижимости");
        script2.setDescription("Скрипт для агенства недвижимости");
        scriptService.create(script2);

        Script script3 = new Script();
        script3.setName("Торговая компания");
        script3.setDescription("Скрипт для торговой компании");
        scriptService.create(script3);

        List<Script> all = scriptService.fetchAll();
        all.sort((s1, s2) -> s1.getName().compareToIgnoreCase(s2.getName()));

        PagingInfo pagingInfo = new PagingInfo();
        pagingInfo.setPage(0);
        pagingInfo.setLimit(2);
        Page<Script> scripts = scriptService.fetch(pagingInfo);
        assertEquals(2, scripts.getContent().size());
        assertEquals(all.get(0).getName(), scripts.getContent().get(0).getName());
        assertEquals(all.get(1).getName(), scripts.getContent().get(1).getName());

        scriptService.delete(script1.getId());
        scriptService.delete(script2.getId());
        scriptService.delete(script3.getId());


    }
}
