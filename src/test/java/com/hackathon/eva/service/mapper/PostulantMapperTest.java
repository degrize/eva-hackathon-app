package com.hackathon.eva.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PostulantMapperTest {

    private PostulantMapper postulantMapper;

    @BeforeEach
    public void setUp() {
        postulantMapper = new PostulantMapperImpl();
    }
}
