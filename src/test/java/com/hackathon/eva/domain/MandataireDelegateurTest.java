package com.hackathon.eva.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hackathon.eva.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MandataireDelegateurTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MandataireDelegateur.class);
        MandataireDelegateur mandataireDelegateur1 = new MandataireDelegateur();
        mandataireDelegateur1.setId(1L);
        MandataireDelegateur mandataireDelegateur2 = new MandataireDelegateur();
        mandataireDelegateur2.setId(mandataireDelegateur1.getId());
        assertThat(mandataireDelegateur1).isEqualTo(mandataireDelegateur2);
        mandataireDelegateur2.setId(2L);
        assertThat(mandataireDelegateur1).isNotEqualTo(mandataireDelegateur2);
        mandataireDelegateur1.setId(null);
        assertThat(mandataireDelegateur1).isNotEqualTo(mandataireDelegateur2);
    }
}
