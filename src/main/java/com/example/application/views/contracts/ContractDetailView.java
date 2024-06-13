package com.example.application.views.contracts;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.application.data.Contract;
import com.example.application.data.Policyholder;
import com.example.application.data.User;
import com.example.application.data.coveredRisksEnum.CoveredRisksEnum;
import com.example.application.data.statusEnum.StatusEnum;
import com.example.application.services.ConfirmationDialog;
import com.example.application.services.ContractService;
import com.example.application.services.PolicyholderService;
import com.example.application.services.UserService;
import com.example.application.views.MainLayout;
import com.example.application.views.requests.RequestsPendingContractView;
import com.example.application.views.requests.RequestsSuccessContractView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.component.dialog.Dialog;

@AnonymousAllowed
@Route(value = "contracts/:id", layout = MainLayout.class)
@Uses(Icon.class)
public class ContractDetailView extends VerticalLayout implements BeforeEnterObserver {

    @Autowired
    ContractService contractService;
    @Autowired
    PolicyholderService policyholderService;
    private Contract contract;
    private Dialog confirmDialog;

    private final TextField insuranceContractNumber = new TextField("Номер договора");
    private final TextField insurer = new TextField("Страховщик");
    private final DatePicker startDateOfInsuranceCoverage = new DatePicker("Дата начала страхования");
    private final DatePicker endDateOfInsuranceCoverage = new DatePicker("Дата окончания страхования");
    private final ComboBox<StatusEnum> status = new ComboBox<>("Статус");
    private final ComboBox<User> supervisingUnderwriter = new ComboBox<>("Курирующий андерайтер");
    private final ComboBox<User> supervising_UOPB_employee = new ComboBox<>("Курирующий УОПБ сотрудник");
    private final ComboBox<User> underwriterOne = new ComboBox<>("Ответственный андерайтер 1");
    private final ComboBox<User> underwriterTwo = new ComboBox<>("Ответственный андерайтер 2");
    ComboBox<Policyholder> policyholder = new ComboBox<>("Страхователь");
    private final ComboBox<String> coveredCountries = new ComboBox<>("Покрытые страны");
    private final ComboBox<CoveredRisksEnum> coveredRisks = new ComboBox<>("Покрытые риски");
    private final TextField insuredSharePolitical = new TextField("Политическое участие Страхователя в убытке");
    private final TextField waitingPeriodPolitical = new TextField("Политический период ожидания");
    private final TextField maxPoliticalCreditPeriod = new TextField("Макcимальный период политического кредита");
    private final TextField insuredShareCommercial = new TextField("Коммерческое участие Страхователя в убытке");
    private final TextField waitingPeriodCommercial = new TextField("Коммерческий период ожидания");
    private final TextField maxCommercialCreditPeriod = new TextField("Макcимальный период коммерческиеого кредита");
    private final TextField clientName = new TextField("Имя клиента");

    private final Button cancel = new Button("Отмена");
    private final Button save = new Button("Сохранить");
    private final Button delete = new Button("Удалить");
    private final BeanValidationBinder<Contract> binder;

    private final UserService userService;

    public ContractDetailView(ContractService contractService, UserService userService,
            PolicyholderService policyholderService) {
        this.contractService = contractService;
        this.userService = userService;
        this.policyholderService = policyholderService;

        Button openSuccessRequestViewButton = acceptedCreditLimits();
        Button openPendingRequestViewButton = pendingCreditLimits();

        HorizontalLayout buttonLayoutForRequestButtons = new HorizontalLayout(openSuccessRequestViewButton,
                openPendingRequestViewButton);
        add(buttonLayoutForRequestButtons);

        FormLayout formLayout = new FormLayout();
        formLayout.setWidth("100%");
        formLayout.add(insuranceContractNumber, insurer, startDateOfInsuranceCoverage,
                endDateOfInsuranceCoverage, status,
                supervisingUnderwriter, supervising_UOPB_employee, underwriterOne, underwriterTwo, policyholder,
                coveredCountries, coveredRisks,
                insuredSharePolitical, waitingPeriodPolitical, maxPoliticalCreditPeriod, insuredShareCommercial,
                waitingPeriodCommercial, maxCommercialCreditPeriod, clientName);

        add(formLayout);
        setWidth("100%");

        binder = new BeanValidationBinder<>(Contract.class);
        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> clearForm());
        save.addClickListener(e -> {
            try {
                if (this.contract == null) {
                    this.contract = new Contract();
                }
                binder.writeBean(this.contract);
                contractService.update(this.contract);
                Notification.show("Данные успешно обновлены");
            } catch (ValidationException validationException) {
                Notification.show("Не удалось обновить данные. Проверьте, что все значения допустимы");
            }
        });

        delete.addClickListener(e -> {
            if (this.contract != null) {
                confirmDialog = new ConfirmationDialog(
                        "Вы действительно хотите удалить контракт " + this.contract.getInsuranceContractNumber() + "?",
                        this::deleteContract);
                confirmDialog.open();
            }
        });

        HorizontalLayout buttonLayout = new HorizontalLayout(save, cancel, delete);
        add(buttonLayout);
    }

    private Button acceptedCreditLimits() {
        Button button = new Button("Одобренные кредитные лимиты", event -> {
            if (contract != null && contract.getInsuranceContractNumber() != null) {
                String insuranceContractNumber = contract.getInsuranceContractNumber();
                RouteParameters routeParameters = new RouteParameters("insuranceContractNumber",
                        insuranceContractNumber);
                UI.getCurrent().navigate(RequestsSuccessContractView.class, routeParameters);
            } else {
                Notification.show("Регистрационный код компании не найден");
            }
        });
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return button;
    }

    private Button pendingCreditLimits() {
        Button button = new Button("Просмотреть заявки", event -> {
            if (contract != null && contract.getInsuranceContractNumber() != null) {
                String insuranceContractNumber = contract.getInsuranceContractNumber();
                RouteParameters routeParameters = new RouteParameters("insuranceContractNumber",
                        insuranceContractNumber);
                UI.getCurrent().navigate(RequestsPendingContractView.class, routeParameters);
            } else {
                Notification.show("Регистрационный код компании не найден");
            }
        });
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return button;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<String> contractId = event.getRouteParameters().get("id");
        if (contractId.isPresent()) {
            Integer id = Integer.valueOf(contractId.get());
            contract = contractService.findContractById(id);
            if (contract != null) {
                populateForm(contract);
            } else {
                event.forwardTo(ContractsView.class);
            }
        } else {
            event.forwardTo(ContractsView.class);
        }
    }

    private void populateForm(Contract contract) {
        this.contract = contract;

        List<User> mainUnderwriters = userService.getUsersWithRoleMainUnderwriter();
        supervisingUnderwriter.setItems(mainUnderwriters);
        supervisingUnderwriter.setItemLabelGenerator(User::getName);
        supervisingUnderwriter.setValue(contract.getSupervisingUnderwriter());

        List<User> mainUOPBemployees = userService.getUsersWithRoleSupervisingUOPBEmployee();
        supervising_UOPB_employee.setItems(mainUOPBemployees);
        supervising_UOPB_employee.setItemLabelGenerator(User::getName);
        supervising_UOPB_employee.setValue(contract.getSupervising_UOPB_employee());

        List<User> responsibleUnderwriterOne = userService.getUsersWithRoleUnderwriter();
        underwriterOne.setItems(responsibleUnderwriterOne);
        underwriterOne.setItemLabelGenerator(User::getName);
        underwriterOne.setValue(contract.getUnderwriterOne());

        List<User> responsibleUnderwriterTwo = userService.getUsersWithRoleUnderwriter();
        underwriterTwo.setItems(responsibleUnderwriterTwo);
        underwriterTwo.setItemLabelGenerator(User::getName);
        underwriterTwo.setValue(contract.getUnderwriterOne());

        List<Policyholder> policyholders = policyholderService.getAllPolicyholders();
        policyholder.setItems(policyholders);
        policyholder.setItemLabelGenerator(Policyholder::getCompanyName);
        policyholder.setValue(contract.getPolicyholder());

        status.setItems(StatusEnum.values());
        status.setItemLabelGenerator(StatusEnum::getDisplayName);
        coveredCountries.setItems("Россия", "Украина", "Беларусь");
        coveredRisks.setItems(CoveredRisksEnum.values());
        coveredRisks.setItemLabelGenerator(CoveredRisksEnum::getDisplayName);
        binder.readBean(this.contract);
    }

    private void clearForm() {
        populateForm(null);
    }

    private void deleteContract() {
        if (this.contract != null) {
            contractService.deleteContractById(this.contract.getId());
            clearForm();
            Notification.show("Контракт удален");
            UI.getCurrent().navigate(ContractsView.class);
        }
    }

}