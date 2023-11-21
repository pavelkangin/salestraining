package com.st.app.dao;

import com.st.app.model.Role;
import com.st.app.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    private RoleRepository repository;
    Logger logger = LoggerFactory.getLogger(RoleService.class);



    public List<Role> findAll(){
        return repository.findAll(Sort.by(Sort.Order.asc("name")));
    }


    public Role getStudent(){
        Optional<Role> role=repository.findById(1);
        return role.orElse(null);
    }

    public Role getManager(){
        Optional<Role> role=repository.findById(2);
        return role.orElse(null);
    }

}
