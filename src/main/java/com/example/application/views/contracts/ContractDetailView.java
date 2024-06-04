package com.example.application.views.contracts;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.application.data.Contract;
import com.example.application.data.coveredRisksEnum.CoveredRisksEnum;
import com.example.application.data.statusEnum.StatusEnum;
import com.example.application.services.ContractService;
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

@AnonymousAllowed
@Route(value = "contracts/:id", layout = MainLayout.class)
@Uses(Icon.class)
public class ContractDetailView extends VerticalLayout implements BeforeEnterObserver {

    @Autowired
    private final ContractService contractService;
    private Contract contract;

    private final TextField contractNumber = new TextField("Insurance Contract Number");
    private final TextField insurer = new TextField("Insurer");
    // private final ComboBox<StatusEnum> status = new ComboBox<>();
    // private final DatePicker startDateOfInsuranceCoverage = new DatePicker();
    // private final DatePicker endDateOfInsuranceCoverage = new DatePicker();
    private final TextField supervisingUndewriter = new TextField("Supervising Underwriter");
    private final TextField supervising_UOPB_employee = new TextField("Supervising UOPB Employee");
    private final TextField policyholder = new TextField("Policyholder");
    // private final ComboBox<String> coveredCountries = new ComboBox<>();
    // private final ComboBox<CoveredRisksEnum> coveredRisks = new ComboBox<>();
    private final TextField insuredSharePolitical = new TextField("Insured Share Political");
    private final TextField waitingPeriodPolitical = new TextField("Waiting Period Political");
    private final TextField maxPoliticalCreditPeriod = new TextField("Max Political Credit Period");
    private final TextField insuredShareCommercial = new TextField("Insured Share Commercial");
    private final TextField waitingPeriodCommercial = new TextField("Waiting Period Commercial");
    private final TextField maxCommercialCreditPeriod = new TextField("Max Commercial Credit Period");
    private final TextField clientName = new TextField("Client Name");

    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");
    private final BeanValidationBinder<Contract> binder;

    public ContractDetailView(ContractService contractService) {
        this.contractService = contractService;

        FormLayout formLayout = new FormLayout();
        formLayout.setWidth("100%");
        formLayout.add(contractNumber, insurer,
                supervisingUndewriter, supervising_UOPB_employee, policyholder,
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
                clearForm();
                Notification.show("Data updated");
                UI.getCurrent().navigate(ContractDetailView.class);
            } catch (ValidationException validationException) {
                Notification.show("Failed to update the data. Check again that all values are valid");
            }
        });

        HorizontalLayout buttonLayout = new HorizontalLayout(save, cancel);
        add(buttonLayout);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        System.out.println("beforeEnter called");
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
        binder.readBean(this.contract);
    }

    private void clearForm() {
        populateForm(null);
    }

}