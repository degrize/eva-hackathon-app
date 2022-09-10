package com.hackathon.eva.service.mapper;

import com.hackathon.eva.domain.Annonce;
import com.hackathon.eva.domain.Message;
import com.hackathon.eva.service.dto.AnnonceDTO;
import com.hackathon.eva.service.dto.MessageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Message} and its DTO {@link MessageDTO}.
 */
@Mapper(componentModel = "spring")
public interface MessageMapper extends EntityMapper<MessageDTO, Message> {
    @Mapping(target = "annonce", source = "annonce", qualifiedByName = "annonceTitre")
    MessageDTO toDto(Message s);

    @Named("annonceTitre")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "titre", source = "titre")
    AnnonceDTO toDtoAnnonceTitre(Annonce annonce);
}
