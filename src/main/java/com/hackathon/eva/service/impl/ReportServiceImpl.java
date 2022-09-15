package com.hackathon.eva.service.impl;

import com.hackathon.eva.repository.AnnonceRepository;
import com.hackathon.eva.repository.MandataireDelegateurRepository;
import com.hackathon.eva.repository.PostulantRepository;
import com.hackathon.eva.repository.TransactionRepository;
import com.hackathon.eva.service.ReportService;
import com.hackathon.eva.service.dto.AdminStatisticsDTO;
import com.hackathon.eva.service.dto.ReportDTO;
import com.hackathon.eva.utils.DateUtils;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReportServiceImpl implements ReportService {

    private final Logger log = LoggerFactory.getLogger(ReportServiceImpl.class);

    @Autowired
    private AnnonceRepository annonceRepository;

    @Autowired
    private PostulantRepository postulantRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private MandataireDelegateurRepository mandataireDelegateurRepository;

    public ReportServiceImpl() {}

    @Override
    public ReportDTO adminDailyReport(String period) {
        ReportDTO reportDTO = new ReportDTO();

        //ZoneId zoneId = ZoneId.of("Africa/Abidjan");
        LocalDate today = LocalDate.now();
        LocalDate startDate;
        LocalDate endDate;

        String dateCourante = null; // DateUtils.dateToString(startDate, "dd/MM/yyyy");

        switch (period) {
            case "daily":
                startDate = LocalDate.from(today);
                endDate = LocalDate.from(today.plusDays(1));

                dateCourante = DateUtils.dateToString(startDate, "dd/MM/yyyy");
                break;
            case "weekly": // du 22/07/2020 00:00:00 au 29/07/2020 00:00:00
                startDate = LocalDate.from(today.minusDays(6)); // du 23/07/2020 00:00:00 au 30/07/2020 00:00:00
                endDate = startDate.plusDays(7);

                dateCourante =
                    "du " +
                    DateUtils.dateToString(startDate, "dd/MM/yyyy") +
                    " au " +
                    DateUtils.dateToString(endDate.minusDays(1), "dd/MM/yyyy");
                break;
            case "year": // du 22/07/2020 00:00:00 au 22/07/2021 00:00:00
                startDate = LocalDate.from(today.minusDays(364)); // du 23/07/2020 00:00:00 au 30/07/2020 00:00:00
                endDate = startDate.plusDays(365);

                dateCourante =
                    "du " +
                    DateUtils.dateToString(startDate, "dd/MM/yyyy") +
                    " au " +
                    DateUtils.dateToString(endDate.minusDays(1), "dd/MM/yyyy");
                break;
            default:
                startDate = null;
                endDate = null;
                break;
        }

        reportDTO.setDateCourante(dateCourante);

        AdminStatisticsDTO adminStatisticsDTO = new AdminStatisticsDTO();
        adminStatisticsDTO.setNbreAnnonce(annonceRepository.getCountAnnonce());
        adminStatisticsDTO.setNbreAnnonceDemande(postulantRepository.getCountPostulant());
        adminStatisticsDTO.setNbreTransaction(transactionRepository.getCountTransaction());
        adminStatisticsDTO.setNbreCompteNormal(mandataireDelegateurRepository.getCountMandataireDelegateurNormal());
        adminStatisticsDTO.setNbreComptePremium(mandataireDelegateurRepository.getCountMandataireDelegateurPremium());

        int totalAnnonce = annonceRepository.getCountAnnonceParPeriode(startDate, endDate);
        reportDTO.setTotalAnnonce(totalAnnonce);
        int totalAnnonceDemande = postulantRepository.getCountPostulantParPeriode(startDate, endDate);
        reportDTO.setTotalAnnonceDemande(totalAnnonceDemande);
        int totalComptepremium = mandataireDelegateurRepository.getCountMandataireDelegateurPremiumPeriode(startDate, endDate);
        reportDTO.setTotalComptepremium(totalComptepremium);

        int totalTransaction = transactionRepository.getCountTransactionParPeriode(startDate, endDate);
        reportDTO.setTotalTransaction(totalTransaction);

        return reportDTO;
    }
}
