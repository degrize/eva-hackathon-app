package com.hackathon.eva.service;

import com.hackathon.eva.domain.MandataireDelegateur;
import com.hackathon.eva.domain.User;
import com.hackathon.eva.repository.MandataireDelegateurRepository;
import com.hackathon.eva.repository.UserRepository;
import com.hackathon.eva.service.dto.ReportDTO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MailingReport {

    private final Logger log = LoggerFactory.getLogger(MailingReport.class);

    @Autowired
    private MailService mailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReportService reportService;

    @Autowired
    private MandataireDelegateurRepository mandataireDelegateurRepository;

    public MailingReport() {}

    /* @Scheduled(cron="00 00 18 * * MON-FRI")
    public void dailyReport() {
        log.info("Start daily report");
        Authority authority = new Authority();
        Set<Authority> authorities = new HashSet<>();
        authority.setName("ROLE_ADMIN");
        authorities.add(authority);

        Set<User> users = userRepository.findByAuthoritiesContainsAndActivated(authorities, true);
        ReportDTO reportDTO = reportService.adminDailyReport("daily");

        mailService.sendReportEmail(users, reportDTO);
    }

    @Scheduled(cron = "00 00 18 * * FRI") // execute every friday at 16:30:00
    public void weeklyReport() throws Exception {
        log.info("Start weekly report");
        Authority authority = new Authority();
        Set<Authority> authorities = new HashSet<>();
        authority.setName("ROLE_ADMIN");
        authorities.add(authority);

        Set<User> users = userRepository.findByAuthoritiesContainsAndActivated(authorities, true);
        ReportDTO reportDTO = reportService.adminDailyReport("weekly");

        mailService.sendReportEmail(users, reportDTO);
    } */

    @Scheduled(cron = "00 30 23 * * MON-FRI")
    public void dailyReport() {
        log.info("Start daily report");
        ReportDTO reportDTO;
        List<String> exclusions = new ArrayList<>();
        exclusions.add("dga-esi@inphb.ci");
        exclusions.add("dg-esi@inphb.ci");

        Set<User> users = new HashSet<>(userRepository.findByLoginNotIn(exclusions));
        for (User user : users) {
            log.info(user.toString());
            reportDTO = reportService.adminDailyReport("year");
            mailService.sendReportEmail(user, reportDTO);
        }
    }

    //@Scheduled(cron = "00 30 23 * * FRI") // execute every friday at 16:30:00
    @Scheduled(cron = "0 0/5 14 * * ?") // every 5 minutes starting at 2:00 PM and ending at 2:55 PM, every day
    public void weeklyReport() {
        log.info("Start weekly report");
        ReportDTO reportDTO;
        List<String> exclusions = new ArrayList<>();
        exclusions.add("dga-esi@inphb.ci");
        exclusions.add("dg-esi@inphb.ci");

        Set<User> users = new HashSet<>(userRepository.findByLoginNotIn(exclusions));
        for (User user : users) {
            log.info(user.toString());
            reportDTO = reportService.adminDailyReport("year");
            mailService.sendReportEmail(user, reportDTO);
        }
    }

    //@Scheduled(cron = "00 30 23 * * FRI") // execute every friday at 16:30:00
    @Scheduled(cron = "0 0/5 14 * * ?") // every 5 minutes starting at 2:00 PM and ending at 2:55 PM, every day
    public void weeklyAlertDepoRapport() {
        log.info("Start weekly report");
        ReportDTO reportDTO;
        List<String> exclusions = new ArrayList<>();

        exclusions.add("dga@inphb.ci");
        exclusions.add("dg@inphb.ci");

        Set<User> users = new HashSet<>(userRepository.findByLoginNotIn(exclusions));

        for (User user : users) {
            MandataireDelegateur existingMandataireDelegateur = mandataireDelegateurRepository.findByJhiUserId(user.getId());
            if (existingMandataireDelegateur.getId() == null) {
                mailService.sendBeveAvantage(user);
            }
        }
    }
}
