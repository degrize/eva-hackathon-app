package com.hackathon.eva.service.mapper;

import com.hackathon.eva.domain.Annonce;
import com.hackathon.eva.domain.Categorie;
import com.hackathon.eva.domain.MandataireDelegateur;
import com.hackathon.eva.service.dto.AnnonceDTO;
import com.hackathon.eva.service.dto.CategorieDTO;
import com.hackathon.eva.service.dto.MandataireDelegateurDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Annonce} and its DTO {@link AnnonceDTO}.
 */
@Mapper(componentModel = "spring")
public interface AnnonceMapper extends EntityMapper<AnnonceDTO, Annonce> {
    @Mapping(target = "categories", source = "categories", qualifiedByName = "categorieNomSet")
    @Mapping(target = "mandataireDelegateur", source = "mandataireDelegateur", qualifiedByName = "mandataireDelegateurId")
    AnnonceDTO toDto(Annonce s);

    @Mapping(target = "removeCategorie", ignore = true)
    Annonce toEntity(AnnonceDTO annonceDTO);

    @Named("categorieNom")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nom", source = "nom")
    CategorieDTO toDtoCategorieNom(Categorie categorie);

    @Named("categorieNomSet")
    default Set<CategorieDTO> toDtoCategorieNomSet(Set<Categorie> categorie) {
        return categorie.stream().map(this::toDtoCategorieNom).collect(Collectors.toSet());
    }

    @Named("mandataireDelegateurId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MandataireDelegateurDTO toDtoMandataireDelegateurId(MandataireDelegateur mandataireDelegateur);
}
