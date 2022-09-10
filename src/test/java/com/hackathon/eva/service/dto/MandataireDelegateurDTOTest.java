package com.hackathon.eva.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.hackathon.eva.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MandataireDelegateurDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MandataireDelegateurDTO.class);
        MandataireDelegateurDTO mandataireDelegateurDTO1 = new MandataireDelegateurDTO();
        mandataireDelegateurDTO1.setId(1L);
        MandataireDelegateurDTO mandataireDelegateurDTO2 = new MandataireDelegateurDTO();
        assertThat(mandataireDelegateurDTO1).isNotEqualTo(mandataireDelegateurDTO2);
        mandataireDelegateurDTO2.setId(mandataireDelegateurDTO1.getId());
        assertThat(mandataireDelegateurDTO1).isEqualTo(mandataireDelegateurDTO2);
        mandataireDelegateurDTO2.setId(2L);
        assertThat(mandataireDelegateurDTO1).isNotEqualTo(mandataireDelegateurDTO2);
        mandataireDelegateurDTO1.setId(null);
        assertThat(mandataireDelegateurDTO1).isNotEqualTo(mandataireDelegateurDTO2);
    }
}
