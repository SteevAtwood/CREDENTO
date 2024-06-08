package com.example.application.views.requests;

import com.example.application.data.requestStatusEnum.RequestStatusEnum;
import com.example.application.services.RequestService;

import java.math.BigDecimal;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import jakarta.annotation.security.RolesAllowed;

@PageTitle("Заявки")
@Route(value = "create-request", layout = MainLayout.class)
@RouteAlias(value = "create-request", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@Uses(Icon.class)
public class CreateRequestView extends Composite<VerticalLayout> {

    private final RequestService requestService;

    public CreateRequestView(RequestService requestService) {
        this.requestService = requestService;

        VerticalLayout layoutColumn2 = new VerticalLayout();
        H3 h3 = new H3();
        FormLayout formLayout2Col = new FormLayout();
        TextField insuranceContractNumber = new TextField();
        TextField debitorsCountry = new TextField();
        TextField registrationCode = new TextField();
        TextField clAmount = new TextField();
        ComboBox<String> clCurrency = new ComboBox<>();
        TextField clTermsAndConditions = new TextField();
        TextField adjustmentPossibility = new TextField();
        ComboBox<RequestStatusEnum> status = new ComboBox<>();

        HorizontalLayout layoutRow = new HorizontalLayout();
        Button buttonPrimary = new Button();
        Button buttonSecondary = new Button();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.START);
        getContent().setAlignItems(Alignment.CENTER);
        layoutColumn2.setWidth("100%");
        layoutColumn2.setMaxWidth("800px");
        layoutColumn2.setHeight("min-content");
        h3.setText("Создание новой заявки");
        h3.setWidth("100%");
        formLayout2Col.setWidth("100%");
        insuranceContractNumber.setLabel("Страховой номер");
        debitorsCountry.setLabel("Страна дебитора");
        registrationCode.setLabel("Регистрационный код");
        clAmount.setLabel("Сумма CL");
        clCurrency.setLabel("Валюта CL");
        clCurrency.setItems("RUB", "USD", "EUR");
        clTermsAndConditions.setLabel("Условия CL");
        status.setItems(RequestStatusEnum.values());
        status.setItemLabelGenerator(RequestStatusEnum::getDisplayName);
        status.setLabel("Статус");
        adjustmentPossibility.setLabel("Возможность корректировки");

        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        buttonPrimary.setText("Сохранить");
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonSecondary.setText("Отмена");
        buttonSecondary.setWidth("min-content");

        getContent().add(layoutColumn2);
        layoutColumn2.add(h3);
        layoutColumn2.add(formLayout2Col);
        formLayout2Col.add(insuranceContractNumber);
        formLayout2Col.add(debitorsCountry);
        formLayout2Col.add(registrationCode);
        formLayout2Col.add(clAmount);
        formLayout2Col.add(clCurrency);
        formLayout2Col.add(clTermsAndConditions);
        formLayout2Col.add(adjustmentPossibility);
        formLayout2Col.add(status);

        layoutColumn2.add(layoutRow);
        layoutRow.add(buttonPrimary);
        layoutRow.add(buttonSecondary);

        buttonPrimary.addClickListener(e -> {
            BigDecimal amount = new BigDecimal(clAmount.getValue());
            requestService.createRequest(
                    insuranceContractNumber.getValue(),
                    debitorsCountry.getValue(),
                    registrationCode.getValue(),
                    amount,
                    clCurrency.getValue(),
                    clTermsAndConditions.getValue(),
                    adjustmentPossibility.getValue());
            status.getValue();
        });

        buttonSecondary.addClickListener(e -> {
            // Clear all fields or navigate away
            insuranceContractNumber.clear();
            debitorsCountry.clear();
            registrationCode.clear();
            clAmount.clear();
            clCurrency.clear();
            clTermsAndConditions.clear();
            adjustmentPossibility.clear();
            status.clear();
        });
    }
}
