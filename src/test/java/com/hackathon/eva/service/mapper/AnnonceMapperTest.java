package com.hackathon.eva.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AnnonceMapperTest {

    private AnnonceMapper annonceMapper;

    @BeforeEach
    public void setUp() {
        annonceMapper = new AnnonceMapperImpl();
    }
}
