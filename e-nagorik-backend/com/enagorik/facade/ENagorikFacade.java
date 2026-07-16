package com.enagorik.facade;

import com.enagorik.adapter.EmailGatewayAdapter;
import com.enagorik.adapter.NotificationSender;
import com.enagorik.adapter.SmsGatewayAdapter;
import com.enagorik.adapter.ThirdPartyEmailApi;
import com.enagorik.adapter.ThirdPartySmsApi;
import com.enagorik.command.ApproveCommand;
import com.enagorik.command.CommandInvoker;
import com.enagorik.command.RejectCommand;
import com.enagorik.command.RequestCorrectionCommand;
import com.enagorik.command.VerifyCommand;
import com.enagorik.exception.ENagorikException;
import com.enagorik.factory.UserFactory;
import com.enagorik.factory.payment.BkashGatewayFactory;
import com.enagorik.factory.payment.NagadGatewayFactory;
import com.enagorik.factory.payment.PaymentGatewayFactory;
import com.enagorik.factory.payment.PaymentProcessor;
import com.enagorik.model.Application;
import com.enagorik.model.ApplicationStatus;
import com.enagorik.model.Citizen;
import com.enagorik.model.Payment;
import com.enagorik.model.Role;
import com.enagorik.model.Service;
import com.enagorik.model.StaffMember;
import com.enagorik.model.User;
import com.enagorik.repository.ApplicationRepository;
import com.enagorik.repository.UserRepository;
import com.enagorik.service.AuditService;
import com.enagorik.service.NotificationService;
import com.enagorik.service.OtpService;
import com.enagorik.service.SlaService;
import com.enagorik.singleton.IdGenerator;
import com.enagorik.singleton.ServiceCatalog;
import com.enagorik.util.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Facade: the UI (console or any future web layer) talks to this one
 * class instead of wiring up factories, repositories, commands and
 * services itself.
 */
public class ENagorikFacade
{
    private final ApplicationRepository applicationRepository = new ApplicationRepository();
    private final UserRepository userRepository = new UserRepository();
    private final AuditService auditService = new AuditService();
    private final OtpService otpService = new OtpService();
    private final SlaService slaService = new SlaService();
    private final CommandInvoker invoker = new CommandInvoker();
    private final NotificationService notificationService;

    public ENagorikFacade()
    {
        Map<String, NotificationSender> senders = new HashMap<>();
        senders.put("SMS", new SmsGatewayAdapter(new ThirdPartySmsApi()));
        senders.put("EMAIL", new EmailGatewayAdapter(new ThirdPartyEmailApi()));
        this.notificationService = new NotificationService(senders);
    }

    public Citizen registerCitizen(String name, String phone, String email, String password, String nid, String address)
    {
        Validator.requireValidPhone(phone);
        Validator.requireValidNid(nid);

        User user = UserFactory.createUser(nextUserId(), name, phone, email, password, Role.CITIZEN, nid, address);
        userRepository.save(user);
        return (Citizen) user;
    }

    public StaffMember registerStaff(String name, String phone, String email, String password, String designation, String department, Role role)
    {
        User user = UserFactory.createUser(nextUserId(), name, phone, email, password, role, designation, department);
        userRepository.save(user);
        return (StaffMember) user;
    }

    public Application applyForService(Citizen citizen, int serviceId)
    {
        Service service = ServiceCatalog.getInstance().findById(serviceId)
            .orElseThrow(() -> new ENagorikException("Unknown service."));

        String appNo = IdGenerator.getInstance().nextApplicationNo();
        Application application = new Application.Builder(appNo, citizen, service).build();
        applicationRepository.save(application);

        notificationService.notifyCitizen(citizen, "Application " + appNo + " submitted for " + service.getName());
        auditService.log(citizen.getName(), "SUBMIT", appNo);
        return application;
    }

    public void payForApplication(Application application, String provider, String mobileNumber)
    {
        PaymentGatewayFactory factory = "NAGAD".equalsIgnoreCase(provider)
            ? new NagadGatewayFactory()
            : new BkashGatewayFactory();

        PaymentProcessor processor = factory.createProcessor();
        boolean success = processor.charge(application.getService().getBaseFee(), mobileNumber);

        if (!success)
        {
            throw new ENagorikException("Payment could not be processed.");
        }

        Payment payment = new Payment(IdGenerator.getInstance().nextPaymentId(), application.getService().getBaseFee(), provider);
        payment.confirmPayment();
        application.attachPayment(payment);
        application.updateStatus(ApplicationStatus.UNDER_REVIEW, "Payment confirmed via " + provider);
        applicationRepository.save(application);

        auditService.log(application.getCitizen().getName(), "PAYMENT_CONFIRMED", application.getApplicationNo());
    }

    public void verifyApplication(StaffMember officer, Application application)
    {
        invoker.run(new VerifyCommand(application, officer, auditService));
        applicationRepository.save(application);
    }

    public void rejectApplication(StaffMember officer, Application application, String reason)
    {
        invoker.run(new RejectCommand(application, officer, reason, auditService));
        applicationRepository.save(application);
    }

    public void requestCorrection(StaffMember officer, Application application)
    {
        invoker.run(new RequestCorrectionCommand(application, officer, auditService));
        applicationRepository.save(application);
    }

    public String requestMayorOtp(Application application)
    {
        return otpService.generateOtp(application.getApplicationNo());
    }

    public void approveApplication(StaffMember mayor, Application application, String submittedOtp)
    {
        invoker.run(new ApproveCommand(application, mayor, submittedOtp, otpService, auditService));
        applicationRepository.save(application);
        notificationService.notifyCitizen(application.getCitizen(), "Congratulations! " + application.getApplicationNo() + " has been approved.");
    }

    public Optional<Application> trackApplication(String applicationNo)
    {
        return applicationRepository.findById(applicationNo);
    }

    public List<Application> findBreachedSla()
    {
        return slaService.findBreached(applicationRepository.findAll());
    }

    public List<Service> listServices()
    {
        return ServiceCatalog.getInstance().getAllServices();
    }

    public Optional<User> getUserById(int id)
    {
        return userRepository.findById(id);
    }

    private int nextUserId()
    {
        return userRepository.findAll().size() + 1;
    }
}