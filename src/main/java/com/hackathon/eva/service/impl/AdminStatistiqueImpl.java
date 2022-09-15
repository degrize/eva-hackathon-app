package com.hackathon.eva.service.impl;

import com.hackathon.eva.repository.*;
import com.hackathon.eva.service.AdminStatistiqueService;
import com.hackathon.eva.service.dto.AdminStatisticsDTO;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminStatistiqueImpl implements AdminStatistiqueService {

    private final Logger log = LoggerFactory.getLogger(AdminStatistiqueImpl.class);

    @Autowired
    private AnnonceRepository annonceRepository;

    @Autowired
    private PostulantRepository postulantRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private MandataireDelegateurRepository mandataireDelegateurRepository;

    public AdminStatistiqueImpl() {}

    @Override
    public Optional<AdminStatisticsDTO> makeStatistique() {
        AdminStatisticsDTO adminStatisticsDTO = new AdminStatisticsDTO();
        adminStatisticsDTO.setNbreAnnonce(annonceRepository.getCountAnnonce());
        adminStatisticsDTO.setNbreAnnonceDemande(postulantRepository.getCountPostulant());
        adminStatisticsDTO.setNbreTransaction(transactionRepository.getCountTransaction());
        adminStatisticsDTO.setNbreCompteNormal(mandataireDelegateurRepository.getCountMandataireDelegateurNormal());
        adminStatisticsDTO.setNbreComptePremium(mandataireDelegateurRepository.getCountMandataireDelegateurPremium());

        return Optional.of(adminStatisticsDTO);
    }
}
