package com.hackathon.eva.service;

import com.hackathon.eva.service.dto.ReportDTO;

public interface ReportService {
    ReportDTO adminDailyReport(String period);
}
