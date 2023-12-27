package com.st.app.dao;

import com.st.app.dto.PagingInfo;
import com.st.app.model.Script;
import com.st.app.repository.ScriptRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScriptService {


    Logger logger = LoggerFactory.getLogger(ScriptService.class);


    @Autowired
    private ScriptRepository repository;

    public Page<Script> fetch(PagingInfo info){
        PageRequest pageRequest = PageRequest.of(info.getPage(), info.getLimit(), Sort.by(Sort.Order.asc("name")));
        return repository.findAll(pageRequest);
    }
    public List<Script> fetchAll() {
        return repository.findAll();
    }
    public void create (Script script) {
        repository.save(script);
    }
    public void delete (Integer id) {
        repository.deleteById(id);
    }
}
