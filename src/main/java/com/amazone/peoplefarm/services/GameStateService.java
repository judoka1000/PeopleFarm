package com.amazone.peoplefarm.services;

import com.amazone.peoplefarm.model.GameState;
import com.amazone.peoplefarm.model.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GameStateService extends CrudRepository<GameState,Integer>{
}
