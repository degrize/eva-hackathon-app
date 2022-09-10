package com.hackathon.eva.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MandataireDelegateurMapperTest {

    private MandataireDelegateurMapper mandataireDelegateurMapper;

    @BeforeEach
    public void setUp() {
        mandataireDelegateurMapper = new MandataireDelegateurMapperImpl();
    }
}
