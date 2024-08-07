package com.bandomatteo.Prototipo1.mappers.impl;

import com.bandomatteo.Prototipo1.domain.dto.BookDto;
import com.bandomatteo.Prototipo1.domain.entities.BookEntity;
import com.bandomatteo.Prototipo1.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BookMapper implements Mapper<BookEntity, BookDto> {

    private ModelMapper modelMapper;

    public BookMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public BookDto mapto(BookEntity bookEntity) {
        return modelMapper.map(bookEntity, BookDto.class);
    }

    @Override
    public BookEntity mapfrom(BookDto bookDto) {
        return modelMapper.map(bookDto, BookEntity.class);
    }
}