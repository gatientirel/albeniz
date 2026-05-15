package com.theodo.albeniz.services;

import com.theodo.albeniz.config.ApplicationConfig;
import com.theodo.albeniz.dto.Tune;
import com.theodo.albeniz.mappers.TuneMapper;
import com.theodo.albeniz.model.TuneEntity;
import com.theodo.albeniz.repositories.TuneRepository;

import lombok.RequiredArgsConstructor;

import org.hibernate.query.SortDirection;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Profile("!memory")
@RequiredArgsConstructor
public class InDatabaseLibraryService implements LibraryService {
    private final ApplicationConfig applicationConfig;
    private final TuneRepository tuneRepository;

    private final TuneMapper tuneMapper;

    @Override
    public Collection<Tune> getAll(String query) {
        Sort.Direction direction = applicationConfig.getApi().isAscending() ? Sort.Direction.ASC : Sort.Direction.DESC;
        PageRequest pageRequest = PageRequest.of(0, applicationConfig.getApi().getMaxCollection(),
                Sort.by(direction, "title"));

        if (query != null) {
            List<TuneEntity> tuneEntities = tuneRepository.searchBy(query, pageRequest);
            return tuneEntities.stream().map(tuneMapper::mapEntityToDto).collect(Collectors.toList());
        } else {
            Page<TuneEntity> tuneEntities = tuneRepository.findAll(pageRequest);
            return tuneEntities.stream().map(tuneMapper::mapEntityToDto).collect(Collectors.toList());
        }
    }

    public Collection<Tune> getByAuthor(String author) {
        List<TuneEntity> tuneEntities = tuneRepository.findByAuthor(author);
        return tuneEntities.stream().map(tuneMapper::mapEntityToDto).toList();
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
}
