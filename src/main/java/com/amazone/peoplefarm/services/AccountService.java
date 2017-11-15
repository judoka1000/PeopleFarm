package com.amazone.peoplefarm.services;

import com.amazone.peoplefarm.models.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface AccountService extends CrudRepository<Account, Integer> {

    public Account findByUsername(String username);
}
