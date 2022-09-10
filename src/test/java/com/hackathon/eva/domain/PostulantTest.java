package com.hackathon.eva.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hackathon.eva.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PostulantTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Postulant.class);
        Postulant postulant1 = new Postulant();
        postulant1.setId(1L);
        Postulant postulant2 = new Postulant();
        postulant2.setId(postulant1.getId());
        assertThat(postulant1).isEqualTo(postulant2);
        postulant2.setId(2L);
        assertThat(postulant1).isNotEqualTo(postulant2);
        postulant1.setId(null);
        assertThat(postulant1).isNotEqualTo(postulant2);
    }
}
