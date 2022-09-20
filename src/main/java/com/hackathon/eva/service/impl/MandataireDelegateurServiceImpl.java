package com.hackathon.eva.service.impl;

import com.hackathon.eva.domain.MandataireDelegateur;
import com.hackathon.eva.domain.Souscription;
import com.hackathon.eva.domain.User;
import com.hackathon.eva.domain.enumeration.EtatCompte;
import com.hackathon.eva.repository.MandataireDelegateurRepository;
import com.hackathon.eva.repository.UserRepository;
import com.hackathon.eva.service.MandataireDelegateurService;
import com.hackathon.eva.service.dto.AdminUserDTO;
import com.hackathon.eva.service.dto.MandataireDelegateurDTO;
import com.hackathon.eva.service.mapper.MandataireDelegateurMapper;
import com.hackathon.eva.web.rest.AccountResource;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MandataireDelegateur}.
 */
@Service
@Transactional
public class MandataireDelegateurServiceImpl implements MandataireDelegateurService {

    private final Logger log = LoggerFactory.getLogger(MandataireDelegateurServiceImpl.class);

    private final MandataireDelegateurRepository mandataireDelegateurRepository;

    private final MandataireDelegateurMapper mandataireDelegateurMapper;

    @Autowired
    private AccountResource accountResource;

    @Autowired
    private UserRepository userRepository;

    public MandataireDelegateurServiceImpl(
        MandataireDelegateurRepository mandataireDelegateurRepository,
        MandataireDelegateurMapper mandataireDelegateurMapper
    ) {
        this.mandataireDelegateurRepository = mandataireDelegateurRepository;
        this.mandataireDelegateurMapper = mandataireDelegateurMapper;
    }

    @Override
    public MandataireDelegateurDTO save(MandataireDelegateurDTO mandataireDelegateurDTO) {
        log.debug("Request to save MandataireDelegateur : {}", mandataireDelegateurDTO);
        MandataireDelegateur mandataireDelegateur = mandataireDelegateurMapper.toEntity(mandataireDelegateurDTO);

        AdminUserDTO adminUserDTO = new AdminUserDTO();
        adminUserDTO = accountResource.getAccountUser();
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(adminUserDTO.getEmail());
        mandataireDelegateur.setUser(existingUser.get());

        mandataireDelegateur.setEtatCompte(EtatCompte.NORMAL);

        mandataireDelegateur = mandataireDelegateurRepository.save(mandataireDelegateur);
        return mandataireDelegateurMapper.toDto(mandataireDelegateur);
    }

    @Override
    public MandataireDelegateurDTO update(MandataireDelegateurDTO mandataireDelegateurDTO) {
        log.debug("Request to update MandataireDelegateur : {}", mandataireDelegateurDTO);
        MandataireDelegateur mandataireDelegateur = mandataireDelegateurMapper.toEntity(mandataireDelegateurDTO);

        AdminUserDTO adminUserDTO = new AdminUserDTO();
        adminUserDTO = accountResource.getAccountUser();
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(adminUserDTO.getEmail());

        mandataireDelegateur.setUser(existingUser.get());
        mandataireDelegateur = mandataireDelegateurRepository.save(mandataireDelegateur);
        return mandataireDelegateurMapper.toDto(mandataireDelegateur);
    }

    @Override
    public Optional<MandataireDelegateurDTO> partialUpdate(MandataireDelegateurDTO mandataireDelegateurDTO) {
        log.debug("Request to partially update MandataireDelegateur : {}", mandataireDelegateurDTO);

        return mandataireDelegateurRepository
            .findById(mandataireDelegateurDTO.getId())
            .map(existingMandataireDelegateur -> {
                mandataireDelegateurMapper.partialUpdate(existingMandataireDelegateur, mandataireDelegateurDTO);

                return existingMandataireDelegateur;
            })
            .map(mandataireDelegateurRepository::save)
            .map(mandataireDelegateurMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MandataireDelegateurDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MandataireDelegateurs");
        return mandataireDelegateurRepository.findAll(pageable).map(mandataireDelegateurMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MandataireDelegateurDTO> findOne(Long id) {
        log.debug("Request to get MandataireDelegateur : {}", id);
        return mandataireDelegateurRepository.findById(id).map(mandataireDelegateurMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MandataireDelegateur : {}", id);
        mandataireDelegateurRepository.deleteById(id);
    }

    @Override
    public MandataireDelegateur findUser(Long id) {
        MandataireDelegateur existingMandataireDelegateur = mandataireDelegateurRepository.findByJhiUserId(id);

        return existingMandataireDelegateur;
    }

    @Override
    public List<MandataireDelegateur> findAllByNomPrenom(String nomprenom) {
        return mandataireDelegateurRepository.findMandataireDelegateurByLikeNomDeFamilleAndPrenom(nomprenom);
    }
}
