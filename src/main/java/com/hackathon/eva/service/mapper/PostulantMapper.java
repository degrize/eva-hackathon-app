package com.hackathon.eva.service.mapper;

import com.hackathon.eva.domain.Annonce;
import com.hackathon.eva.domain.MandataireDelegateur;
import com.hackathon.eva.domain.Postulant;
import com.hackathon.eva.service.dto.AnnonceDTO;
import com.hackathon.eva.service.dto.MandataireDelegateurDTO;
import com.hackathon.eva.service.dto.PostulantDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Postulant} and its DTO {@link PostulantDTO}.
 */
@Mapper(componentModel = "spring")
public interface PostulantMapper extends EntityMapper<PostulantDTO, Postulant> {
    @Mapping(target = "annonces", source = "annonces", qualifiedByName = "annonceTitreSet")
    @Mapping(target = "mandataireDelegateur", source = "mandataireDelegateur", qualifiedByName = "mandataireDelegateurId")
    PostulantDTO toDto(Postulant s);

    @Mapping(target = "removeAnnonce", ignore = true)
    Postulant toEntity(PostulantDTO postulantDTO);

    @Named("annonceTitre")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "titre", source = "titre")
    AnnonceDTO toDtoAnnonceTitre(Annonce annonce);

    @Named("annonceTitreSet")
    default Set<AnnonceDTO> toDtoAnnonceTitreSet(Set<Annonce> annonce) {
        return annonce.stream().map(this::toDtoAnnonceTitre).collect(Collectors.toSet());
    }

    @Named("mandataireDelegateurId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MandataireDelegateurDTO toDtoMandataireDelegateurId(MandataireDelegateur mandataireDelegateur);
}
