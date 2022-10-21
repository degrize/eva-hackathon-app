package com.hackathon.eva.service.impl;

import com.hackathon.eva.domain.MandataireDelegateur;
import com.hackathon.eva.domain.Souscription;
import com.hackathon.eva.domain.Transaction;
import com.hackathon.eva.domain.User;
import com.hackathon.eva.domain.enumeration.EtatCompte;
import com.hackathon.eva.repository.MandataireDelegateurRepository;
import com.hackathon.eva.repository.TransactionRepository;
import com.hackathon.eva.repository.UserRepository;
import com.hackathon.eva.service.MandataireDelegateurService;
import com.hackathon.eva.service.dto.AdminUserDTO;
import com.hackathon.eva.service.dto.MandataireDelegateurDTO;
import com.hackathon.eva.service.mapper.MandataireDelegateurMapper;
import com.hackathon.eva.test.ExcelTemplate;
import com.hackathon.eva.web.rest.AccountResource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.DocumentException;
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
    private TransactionRepository transactionRepository;

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

    @Override
    public ByteArrayInputStream generateExcelFile(long id) throws IOException, InvalidFormatException, DocumentException {
        List<Transaction> transactions = transactionRepository.findTransactionsByReceiverId(id);
        MandataireDelegateur mandataireDelegateur = mandataireDelegateurRepository.findByMandateurId(id);

        Class clazz = ExcelTemplate.class;
        InputStream excelFile = clazz.getResourceAsStream("/templates/excel/transactions-eva.xlsx");
        Workbook workbook = new XSSFWorkbook(OPCPackage.open(excelFile));
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Sheet sheet = workbook.getSheetAt(0);

        // Pays
        sheet.getRow(1).getCell(2).setCellValue("Pays : " + mandataireDelegateur.getPays().toString());
        // Compte
        sheet.getRow(1).getCell(8).setCellValue(mandataireDelegateur.getEtatCompte().toString());

        // ID eva
        sheet.getRow(2).getCell(3).setCellValue("EVA-" + mandataireDelegateur.getId().toString());
        // NOM
        sheet.getRow(3).getCell(3).setCellValue(mandataireDelegateur.getNomDeFamille() + " " + mandataireDelegateur.getPrenom());
        // CONTACT
        sheet.getRow(4).getCell(3).setCellValue(mandataireDelegateur.getNumeroMomo() + " / " + mandataireDelegateur.getContact());

        int rowIndex = 7;
        // le text
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);

        // config du texte

        CellStyle labelStyle = workbook.createCellStyle();
        labelStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        labelStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        labelStyle.setBorderBottom((short) 1);
        labelStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        labelStyle.setBorderLeft((short) 1);
        labelStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        labelStyle.setBorderRight((short) 1);
        labelStyle.setWrapText(true);

        Font fontTitle = workbook.createFont();
        fontTitle.setColor(IndexedColors.BLACK.getIndex());
        fontTitle.setFontName("Arial");
        labelStyle.setFont(fontTitle); // on applique ces valeurs

        int numero = 1;
        for (Transaction transaction : transactions) {
            MandataireDelegateur mandataireDelegateurTransmetteur = mandataireDelegateurRepository.findByMandateurId(
                transaction.getTransmeteurId()
            );
            int cellIndex = 1;
            Row row = sheet.createRow(rowIndex++);
            row.setHeight((short) 550);

            Cell cell1 = row.createCell(cellIndex++);
            cell1.setCellStyle(labelStyle);
            cell1.setCellValue(numero++);

            Cell cell2 = row.createCell(cellIndex++);
            cell2.setCellStyle(labelStyle);
            cell2.setCellValue(transaction.getDateTransaction().toString());

            Cell cell3 = row.createCell(cellIndex++);
            cell3.setCellStyle(labelStyle);
            cell3.setCellValue(
                mandataireDelegateurTransmetteur.getNomDeFamille().toUpperCase() + " " + mandataireDelegateurTransmetteur.getPrenom()
            );

            Cell cell4 = row.createCell(cellIndex++);
            cell4.setCellStyle(labelStyle);
            cell4.setCellValue(transaction.getMontant() + "");

            Cell cell5 = row.createCell(cellIndex++);
            cell5.setCellStyle(labelStyle);
            cell5.setCellValue(transaction.getNumeroMtn());

            Cell cell6 = row.createCell(cellIndex++);
            cell6.setCellStyle(labelStyle);
            cell6.setCellValue(mandataireDelegateurTransmetteur.getEmail() + "");

            Cell cell7 = row.createCell(cellIndex++);
            cell7.setCellStyle(labelStyle);
            cell7.setCellValue(mandataireDelegateur.getPays() + "");

            Cell cell8 = row.createCell(cellIndex++);
            cell8.setCellStyle(labelStyle);
            cell8.setCellValue(mandataireDelegateur.getAdresse() != null ? mandataireDelegateur.getAdresse() : "");

            Cell cell9 = row.createCell(cellIndex++);
            cell9.setCellStyle(labelStyle);
            cell9.setCellValue(mandataireDelegateur.getContact() + "");

            Cell cell10 = row.createCell(cellIndex++);
            cell10.setCellStyle(labelStyle);
            cell10.setCellValue(transaction.getAnnonce().getTitre() + "");

            Cell cell11 = row.createCell(cellIndex++);
            cell11.setCellStyle(labelStyle);
            String premium = "";
            switch (mandataireDelegateur.getEtatCompte()) {
                case PREMIUM:
                    premium = "Oui";
                    break;
                case NORMAL:
                    premium = "Non";
                    break;
                default:
                    premium = "Non";
                    break;
            }
            cell11.setCellValue(premium + "");
        }
        workbook.write(out);
        return new ByteArrayInputStream(out.toByteArray());
    }
}
