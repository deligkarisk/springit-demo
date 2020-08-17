package com.kosmas.springit.converter;

import com.kosmas.springit.domain.Link;
import com.kosmas.springit.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LinkByIdConverter implements Converter<String, Link> {

    private LinkRepository linkRepository;

    @Autowired
    public LinkByIdConverter(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @Override
    public Link convert(String id) {
        return linkRepository.getOne(Long.parseLong(id));
    }

}

