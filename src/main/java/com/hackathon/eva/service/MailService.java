package com.hackathon.eva.service;

import com.hackathon.eva.domain.Transaction;
import com.hackathon.eva.domain.User;
import com.hackathon.eva.service.dto.AideDTO;
import com.hackathon.eva.service.dto.ReportDTO;
import com.hackathon.eva.service.dto.TransactionDTO;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import tech.jhipster.config.JHipsterProperties;

/**
 * Service for sending emails.
 * <p>
 * We use the {@link Async} annotation to send emails asynchronously.
 */
@Service
public class MailService {

    private final Logger log = LoggerFactory.getLogger(MailService.class);

    private static final String USER = "user";
    private static final String AIDE = "aide";
    private static final String TRANSACTION = "transaction";

    private static final String BASE_URL = "baseUrl";

    private static final String REPORT = "report";

    private final JHipsterProperties jHipsterProperties;

    private final JavaMailSender javaMailSender;

    private final MessageSource messageSource;

    private final SpringTemplateEngine templateEngine;

    public MailService(
        JHipsterProperties jHipsterProperties,
        JavaMailSender javaMailSender,
        MessageSource messageSource,
        SpringTemplateEngine templateEngine
    ) {
        this.jHipsterProperties = jHipsterProperties;
        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug(
            "Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
            isMultipart,
            isHtml,
            to,
            subject,
            content
        );

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.setFrom(jHipsterProperties.getMail().getFrom());
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
            log.debug("Sent email to User '{}'", to);
        } catch (MailException | MessagingException e) {
            log.warn("Email could not be sent to user '{}'", to, e);
        }
    }

    @Async
    public void sendEmailFromTemplate(User user, String templateName, String titleKey) {
        if (user.getEmail() == null) {
            log.debug("Email doesn't exist for user '{}'", user.getLogin());
            return;
        }
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendEmailFromTemplate(TransactionDTO transactionDTO, String templateName, String titleKey) {
        if (transactionDTO.getAnnonce().getMandataireDelegateur().getEmail() == null) {
            log.debug("Email doesn't exist for user '{}'", transactionDTO.getAnnonce().getMandataireDelegateur().getNomDeFamille());
            return;
        }
        Locale locale = Locale.forLanguageTag("fr");
        Context context = new Context(locale);
        context.setVariable(TRANSACTION, transactionDTO);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        sendEmail(transactionDTO.getAnnonce().getMandataireDelegateur().getEmail(), subject, content, false, true);
    }

    @Async
    public void sendAideEmailFromTemplate(AideDTO aide, String templateName, String titleKey) {
        if (aide.getEmail() == null) {
            log.debug("Email doesn't exist for user '{}'", aide.getNom());
            return;
        }
        Locale locale = Locale.forLanguageTag("fr");
        Context context = new Context(locale);
        context.setVariable(AIDE, aide);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        sendEmail("kechiedou.meda18@inphb.ci", subject, content, false, true);
    }

    @Async
    public void sendActivationEmail(User user) {
        log.debug("Sending activation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/activationEmail", "email.activation.title");
    }

    @Async
    public void sendAideEmail(AideDTO aide) {
        log.debug("Sending aide email from '{}'", aide.getEmail());
        sendAideEmailFromTemplate(aide, "mail/aideEmail", "email.aide.title");
    }

    @Async
    public void sendCreationEmail(User user) {
        log.debug("Sending creation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/creationEmail", "email.activation.title");
    }

    @Async
    public void sendPasswordResetMail(User user) {
        log.debug("Sending password reset email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/passwordResetEmail", "email.reset.title");
    }

    @Async
    public void sendEmailFromTemplate(User user, ReportDTO reportDTO, String templateName, String titleKey) {
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        // context.setVariable(USER, user);
        context.setVariable(REPORT, reportDTO);
        // context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendReportEmail(User user, ReportDTO reportDTO) {
        log.info("Daily report send to '{}'", user.getEmail());
        sendEmailFromTemplate(user, reportDTO, "mail/reportAdminEmail", "email.reportAdminEmail.title");
    }

    @Async
    public void sendBeveAvantage(User user) {
        log.info("Daily mail send to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/createDocumentServiceEmail", "email.createDocumentService.title");
    }

    @Async
    public void sendPaiementNotificationEmail(TransactionDTO transaction) {
        log.debug(
            "Sending notification Don email to Mandataire '{}' email : '{}'",
            transaction.getAnnonce().getMandataireDelegateur().getNomDeFamille(),
            transaction.getAnnonce().getMandataireDelegateur().getEmail()
        );
        sendEmailFromTemplate(transaction, "mail/paiementNotificationEmail", "email.donnotification.title");
    }
}
