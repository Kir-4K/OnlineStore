package by.itacademy.kostusev.service;

import by.itacademy.kostusev.entity.ProductOrder;
import by.itacademy.kostusev.util.ReportText;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SessionScope
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final ReportText reportText;

    public void sendOrderReport(String mail, ProductOrder order, List<ProductOrder> products) throws MailException {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(mail);
        mailMessage.setFrom("potion.onlinestore@gmail.com");
        mailMessage.setSubject("PotionStore: Заказ №" + order.getId().getOrder().getId());
        mailMessage.setText(reportText.getReportText(order) + reportText.getProductInReportText(products));

        javaMailSender.send(mailMessage);
    }
}
