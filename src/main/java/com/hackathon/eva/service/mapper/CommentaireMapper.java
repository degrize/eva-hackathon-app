package com.hackathon.eva.service.mapper;

import com.hackathon.eva.domain.Commentaire;
import com.hackathon.eva.domain.MandataireDelegateur;
import com.hackathon.eva.service.dto.CommentaireDTO;
import com.hackathon.eva.service.dto.MandataireDelegateurDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link Commentaire} and its DTO {@link CommentaireDTO}.
 */
@Mapper(componentModel = "spring")
public interface CommentaireMapper extends EntityMapper<CommentaireDTO, Commentaire> {
    @Mapping(target = "mandataireDelegateur", source = "mandataireDelegateur", qualifiedByName = "mandataireDelegateurId")
    CommentaireDTO toDto(Commentaire s);

    @Named("mandataireDelegateurId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MandataireDelegateurDTO toDtoMandataireDelegateurId(MandataireDelegateur mandataireDelegateur);
}
