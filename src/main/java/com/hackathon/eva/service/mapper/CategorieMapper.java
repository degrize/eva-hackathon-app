package com.hackathon.eva.service.mapper;

import com.hackathon.eva.domain.Categorie;
import com.hackathon.eva.service.dto.AideDTO;
import com.hackathon.eva.service.dto.CategorieDTO;
import java.util.Optional;
import org.mapstruct.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Mapper for the entity {@link Categorie} and its DTO {@link CategorieDTO}.
 */
@Mapper(componentModel = "spring")
public interface CategorieMapper extends EntityMapper<CategorieDTO, Categorie> {}
