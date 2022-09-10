package com.hackathon.eva.service.mapper;

import com.hackathon.eva.domain.MandataireDelegateur;
import com.hackathon.eva.service.dto.MandataireDelegateurDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MandataireDelegateur} and its DTO {@link MandataireDelegateurDTO}.
 */
@Mapper(componentModel = "spring")
public interface MandataireDelegateurMapper extends EntityMapper<MandataireDelegateurDTO, MandataireDelegateur> {}
