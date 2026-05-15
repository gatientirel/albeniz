package com.theodo.albeniz.services;

import com.theodo.albeniz.config.ApplicationConfig;
import com.theodo.albeniz.dto.Tune;
import com.theodo.albeniz.mappers.TuneMapper;
import com.theodo.albeniz.model.TuneEntity;
import com.theodo.albeniz.repositories.TuneRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.StreamSupport;

@Service
@Profile("!memory")
@RequiredArgsConstructor
public class InDatabaseLibraryService implements LibraryService {
    private final TuneRepository tuneRepository;

    private final TuneMapper tuneMapper;

    private final ApplicationConfig applicationConfig;

    @Override
    public Collection<Tune> getAll(String query) {
        return StreamSupport.stream(tuneRepository.findAll().spliterator(), false)
                .filter(entity -> query == null || entity.getTitle().toLowerCase().contains(query.toLowerCase()))
                .map(tuneMapper::mapEntityToDto)
                .sorted(getComparator(applicationConfig.getApi().isAscending()))
                .limit(applicationConfig.getApi().getMaxCollection())
                .toList();
    }

    @Override
    public Tune getOne(UUID id) {
        Optional<TuneEntity> entity = tuneRepository.findById(id);
        if (entity.isEmpty()) {
            return null;
        }
        return tuneMapper.mapEntityToDto(entity.get());
    }

    @Override
    public UUID addTune(Tune tune) {
        TuneEntity newEntity = tuneRepository.save(tuneMapper.mapDtoToEntity(tune));
        return newEntity.getId();
    }

    @Override
    public boolean removeTune(UUID id) {
        if (tuneRepository.existsById(id)) {
            tuneRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean isExist(UUID id) {
        return tuneRepository.existsById(id);
    }

    @Override
    public boolean modifyTune(Tune tune) {
        if (tuneRepository.existsById(tune.getId())) {
            tuneRepository.save(tuneMapper.mapDtoToEntity(tune));
            return true;
        }
        return false;
    }

    private Comparator<? super Tune> getComparator(boolean asc) {
        return asc ? Comparator.comparing(Tune::getTitle) : Comparator.comparing(Tune::getTitle).reversed();
    }
}
