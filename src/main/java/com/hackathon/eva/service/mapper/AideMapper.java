package com.hackathon.eva.service.mapper;

import com.hackathon.eva.domain.Aide;
import com.hackathon.eva.service.dto.AideDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Aide} and its DTO {@link AideDTO}.
 */
@Mapper(componentModel = "spring")
public interface AideMapper extends EntityMapper<AideDTO, Aide> {}
