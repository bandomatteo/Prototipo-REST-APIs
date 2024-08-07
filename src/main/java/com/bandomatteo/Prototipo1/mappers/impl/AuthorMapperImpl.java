package com.bandomatteo.Prototipo1.mappers.impl;

import com.bandomatteo.Prototipo1.domain.dto.AuthorDto;
import com.bandomatteo.Prototipo1.domain.entities.AuthorEntity;
import com.bandomatteo.Prototipo1.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapperImpl implements Mapper<AuthorEntity, AuthorDto> {

    private ModelMapper modelMapper;

    public AuthorMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AuthorDto mapto(AuthorEntity authorEntity) {
        return modelMapper.map (authorEntity, AuthorDto.class);
    }

    @Override
    public AuthorEntity mapfrom(AuthorDto authorDto) {
        return modelMapper.map(authorDto, AuthorEntity.class);
    }
}
