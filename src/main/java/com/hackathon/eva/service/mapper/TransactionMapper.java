package com.hackathon.eva.service.mapper;

import com.hackathon.eva.domain.Annonce;
import com.hackathon.eva.domain.Postulant;
import com.hackathon.eva.domain.Transaction;
import com.hackathon.eva.service.dto.AnnonceDTO;
import com.hackathon.eva.service.dto.PostulantDTO;
import com.hackathon.eva.service.dto.TransactionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Transaction} and its DTO {@link TransactionDTO}.
 */
@Mapper(componentModel = "spring")
public interface TransactionMapper extends EntityMapper<TransactionDTO, Transaction> {
    @Mapping(target = "annonce", source = "annonce", qualifiedByName = "annonceTitre")
    @Mapping(target = "postulant", source = "postulant", qualifiedByName = "postulantId")
    TransactionDTO toDto(Transaction s);

    @Named("annonceTitre")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "titre", source = "titre")
    AnnonceDTO toDtoAnnonceTitre(Annonce annonce);

    @Named("postulantId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PostulantDTO toDtoPostulantId(Postulant postulant);
}
