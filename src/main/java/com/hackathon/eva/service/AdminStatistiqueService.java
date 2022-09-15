package com.hackathon.eva.service;

import com.hackathon.eva.service.dto.AdminStatisticsDTO;
import java.util.Optional;

public interface AdminStatistiqueService {
    Optional<AdminStatisticsDTO> makeStatistique();
}
