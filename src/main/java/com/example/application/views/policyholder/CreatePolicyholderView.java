package com.example.application.views.policyholder;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.application.services.PolicyholderService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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

@PageTitle("Страхователь")
@Route(value = "create-policyholder", layout = MainLayout.class)
@RouteAlias(value = "create-policyholder", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@Uses(Icon.class)
public class CreatePolicyholderView extends Composite<VerticalLayout> {

    @Autowired
    private final PolicyholderService policyholderService;

    public CreatePolicyholderView(PolicyholderService policyholderService) {
        this.policyholderService = policyholderService;

        VerticalLayout layoutColumn2 = new VerticalLayout();
        H3 h3 = new H3();
        FormLayout formLayout = new FormLayout();
        TextField companyName = new TextField();
        TextField address = new TextField();
        TextField informationProviderCode = new TextField();
        TextField companyRegistrationCodes = new TextField();
        TextField okvedCode = new TextField();
        TextField policyholderCompanyEmail = new TextField();
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

        companyName.setLabel("Название компании");
        address.setLabel("Адрес");
        informationProviderCode.setLabel("Код информационного провайдера");
        companyRegistrationCodes.setLabel("Регистрационные коды компании");
        okvedCode.setLabel("Код ОКВЭД");
        policyholderCompanyEmail.setLabel("Email компании дебитора");
        companyStatus.setLabel("Статус компании");
        ownerInformation.setLabel("Информация о владельце");
        contactPersonDetails.setLabel("Данные контактного лица");

        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        formLayout.add(companyName, address, informationProviderCode, companyRegistrationCodes, okvedCode,
                policyholderCompanyEmail, companyStatus, ownerInformation, contactPersonDetails);

        buttonLayout.add(saveButton, cancelButton);
        buttonLayout.setSpacing(true);

        getContent().add(layoutColumn2);
        layoutColumn2.add(h3, formLayout, buttonLayout);

        saveButton.addClickListener(e -> {
            policyholderService.createPolicyholder(
                    companyName.getValue(),
                    address.getValue(),
                    informationProviderCode.getValue(),
                    companyRegistrationCodes.getValue(),
                    okvedCode.getValue(),
                    policyholderCompanyEmail.getValue(),
                    companyStatus.getValue(),
                    ownerInformation.getValue(),
                    contactPersonDetails.getValue());
        });

        cancelButton.addClickListener(e -> clearFields(companyName, address, informationProviderCode,
                companyRegistrationCodes, okvedCode, policyholderCompanyEmail, companyStatus, ownerInformation,
                contactPersonDetails));
    }

    private void clearFields(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }
}
