package com.example.application.views.contracts;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.application.data.Contract;
import com.example.application.data.User;
import com.example.application.data.coveredRisksEnum.CoveredRisksEnum;
import com.example.application.data.statusEnum.StatusEnum;
import com.example.application.services.ConfirmationDialog;
import com.example.application.services.ContractService;
import com.example.application.services.UserService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
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
    private final ContractService contractService;
    private Contract contract;
    private Dialog confirmDialog;

    private final TextField insuranceContractNumber = new TextField("Страховой номер");
    private final TextField insurer = new TextField("Страховщик");
    private final DatePicker startDateOfInsuranceCoverage = new DatePicker("Дата начала страхования");
    private final DatePicker endDateOfInsuranceCoverage = new DatePicker("Дата окончания страхования");
    private final ComboBox<StatusEnum> status = new ComboBox<>("Статус");
    private final ComboBox<User> supervisingUnderwriter = new ComboBox<>("Курирующий андерайтер");
    private final ComboBox<User> supervising_UOPB_employee = new ComboBox<>("Курирующий УОПБ сотрудник");
    private final TextField policyholder = new TextField("Страхователь");
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

    public ContractDetailView(ContractService contractService, UserService userService) {
        this.contractService = contractService;
        this.userService = userService;

        FormLayout formLayout = new FormLayout();
        formLayout.setWidth("100%");
        formLayout.add(insuranceContractNumber, insurer, startDateOfInsuranceCoverage,
                endDateOfInsuranceCoverage, status,
                supervisingUnderwriter, supervising_UOPB_employee, policyholder, coveredCountries, coveredRisks,
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