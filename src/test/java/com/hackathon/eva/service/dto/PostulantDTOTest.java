package com.hackathon.eva.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.hackathon.eva.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PostulantDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostulantDTO.class);
        PostulantDTO postulantDTO1 = new PostulantDTO();
        postulantDTO1.setId(1L);
        PostulantDTO postulantDTO2 = new PostulantDTO();
        assertThat(postulantDTO1).isNotEqualTo(postulantDTO2);
        postulantDTO2.setId(postulantDTO1.getId());
        assertThat(postulantDTO1).isEqualTo(postulantDTO2);
        postulantDTO2.setId(2L);
        assertThat(postulantDTO1).isNotEqualTo(postulantDTO2);
        postulantDTO1.setId(null);
        assertThat(postulantDTO1).isNotEqualTo(postulantDTO2);
    }
}
