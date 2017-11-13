package com.amazone.peoplefarm.services;

import com.amazone.peoplefarm.models.GameState;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameStateService extends CrudRepository<GameState,Integer>{
}
