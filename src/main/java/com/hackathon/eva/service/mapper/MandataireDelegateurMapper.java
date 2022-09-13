package com.hackathon.eva.service.mapper;

import com.hackathon.eva.domain.Annonce;
import com.hackathon.eva.domain.MandataireDelegateur;
import com.hackathon.eva.domain.User;
import com.hackathon.eva.service.dto.AnnonceDTO;
import com.hackathon.eva.service.dto.MandataireDelegateurDTO;
import com.hackathon.eva.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MandataireDelegateur} and its DTO {@link MandataireDelegateurDTO}.
 */
@Mapper(componentModel = "spring")
public interface MandataireDelegateurMapper extends EntityMapper<MandataireDelegateurDTO, MandataireDelegateur> {
    @Mapping(target = "user", source = "user", qualifiedByName = "jhiUserId")
    MandataireDelegateurDTO toDto(MandataireDelegateur s);

    @Named("jhiUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
