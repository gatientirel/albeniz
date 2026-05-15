package com.theodo.albeniz.database.repositories;

import org.springframework.data.repository.CrudRepository;

import com.theodo.albeniz.database.entities.TuneEntity;

import java.util.UUID;

public interface TuneRepository extends CrudRepository<TuneEntity, UUID> {
}