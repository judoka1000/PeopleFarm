package com.amazone.peoplefarm.services;

import com.amazone.peoplefarm.models.Account;
import com.amazone.peoplefarm.models.Button;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ButtonService extends CrudRepository<Button, Integer> {
    public Button findByName(String name);
}