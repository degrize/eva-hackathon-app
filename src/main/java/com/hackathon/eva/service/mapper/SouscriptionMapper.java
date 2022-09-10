package com.hackathon.eva.service.mapper;

import com.hackathon.eva.domain.MandataireDelegateur;
import com.hackathon.eva.domain.Souscription;
import com.hackathon.eva.service.dto.MandataireDelegateurDTO;
import com.hackathon.eva.service.dto.SouscriptionDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Souscription} and its DTO {@link SouscriptionDTO}.
 */
@Mapper(componentModel = "spring")
public interface SouscriptionMapper extends EntityMapper<SouscriptionDTO, Souscription> {
    @Mapping(target = "mandataireDelegateurs", source = "mandataireDelegateurs", qualifiedByName = "mandataireDelegateurNumeroMomoSet")
    SouscriptionDTO toDto(Souscription s);

    @Mapping(target = "removeMandataireDelegateur", ignore = true)
    Souscription toEntity(SouscriptionDTO souscriptionDTO);

    @Named("mandataireDelegateurNumeroMomo")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "numeroMomo", source = "numeroMomo")
    MandataireDelegateurDTO toDtoMandataireDelegateurNumeroMomo(MandataireDelegateur mandataireDelegateur);

    @Named("mandataireDelegateurNumeroMomoSet")
    default Set<MandataireDelegateurDTO> toDtoMandataireDelegateurNumeroMomoSet(Set<MandataireDelegateur> mandataireDelegateur) {
        return mandataireDelegateur.stream().map(this::toDtoMandataireDelegateurNumeroMomo).collect(Collectors.toSet());
    }
}
