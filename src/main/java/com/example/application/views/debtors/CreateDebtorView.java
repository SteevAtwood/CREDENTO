package com.example.application.views.debtors;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.application.data.Contract;
import com.example.application.services.ContractService;
import com.example.application.services.DebtorService;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import jakarta.annotation.security.RolesAllowed;

@PageTitle("Дебиторы")
@Route(value = "create-debtor", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@Uses(Icon.class)
public class CreateDebtorView extends Composite<VerticalLayout> {

    @Autowired
    DebtorService debtorService;
    @Autowired
    ContractService contractService;

    public CreateDebtorView(DebtorService debtorService, ContractService contractService) {
        this.debtorService = debtorService;
        this.contractService = contractService;

        VerticalLayout layoutColumn2 = new VerticalLayout();
        H3 h3 = new H3();
        FormLayout formLayout = new FormLayout();
        ComboBox<Contract> insuranceContractNumber = new ComboBox<>();
        TextField companyName = new TextField();
        TextField address = new TextField();
        TextField informationProviderCode = new TextField();
        TextField companyRegistrationCodes = new TextField();
        TextField okvedCode = new TextField();
        TextField debtorCompanyEmail = new TextField();
        TextField companyStatus = new TextField();
        TextField ownerInformation = new TextField();
        TextField contactPersonDetails = new TextField();

        HorizontalLayout buttonLayout = new HorizontalLayout();
        Button saveButton = new Button("Сохранить");
        Button cancelButton = new Button("Отмена");

        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.START);
        getContent().setAlignItems(Alignment.CENTER);
        layoutColumn2.setWidth("100%");
        layoutColumn2.setMaxWidth("800px");
        layoutColumn2.setHeight("min-content");
        h3.setText("Создание нового дебитора");
        h3.setWidth("100%");
        formLayout.setWidth("100%");

        insuranceContractNumber.setLabel("Номер договора");
        List<Contract> contracts = contractService.getAllContracts();
        insuranceContractNumber.setItems(contracts);
        insuranceContractNumber.setItemLabelGenerator(Contract::getInsuranceContractNumber);

        companyName.setLabel("Название компании");
        address.setLabel("Адрес");
        informationProviderCode.setLabel("Код информационного провайдера");
        companyRegistrationCodes.setLabel("Регистрационные коды компании");
        okvedCode.setLabel("Код ОКВЭД");
        debtorCompanyEmail.setLabel("Email компании дебитора");
        companyStatus.setLabel("Статус компании");
        ownerInformation.setLabel("Информация о владельце");
        contactPersonDetails.setLabel("Данные контактного лица");

        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        formLayout.add(insuranceContractNumber, companyName, address, informationProviderCode, companyRegistrationCodes,
                okvedCode,
                debtorCompanyEmail, companyStatus, ownerInformation, contactPersonDetails);

        buttonLayout.add(saveButton, cancelButton);
        buttonLayout.setSpacing(true);

        getContent().add(layoutColumn2);
        layoutColumn2.add(h3, formLayout, buttonLayout);

        saveButton.addClickListener(e -> {

            Contract selectedInsuranceContractNumber = insuranceContractNumber.getValue();
            debtorService.createDebtor(

                    selectedInsuranceContractNumber,
                    companyName.getValue(),
                    address.getValue(),
                    informationProviderCode.getValue(),
                    companyRegistrationCodes.getValue(),
                    okvedCode.getValue(),
                    debtorCompanyEmail.getValue(),
                    companyStatus.getValue(),
                    ownerInformation.getValue(),
                    contactPersonDetails.getValue());
        });

        cancelButton.addClickListener(e -> clearFields(companyName, address, informationProviderCode,
                companyRegistrationCodes, okvedCode, debtorCompanyEmail, companyStatus, ownerInformation,
                contactPersonDetails));
    }

    private void clearFields(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }
}
