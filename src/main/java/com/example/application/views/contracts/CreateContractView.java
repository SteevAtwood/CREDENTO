package com.example.application.views.contracts;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
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

@PageTitle("Договоры")
@Route(value = "/create-contract", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@Uses(Icon.class)
public class CreateContractView extends Composite<VerticalLayout> {

    public CreateContractView() {
        VerticalLayout layoutColumn2 = new VerticalLayout();
        H3 h3 = new H3();
        FormLayout formLayout2Col = new FormLayout();
        TextField contractNumber = new TextField();
        TextField insurer = new TextField();
        ComboBox<String> status = new ComboBox<>();
        DatePicker startDateOfInsuranceCoverage = new DatePicker();
        DatePicker endDateOfInsuranceCoverage = new DatePicker();
        TextField supervisingUndewriter = new TextField();
        TextField supervisingUOPBEmployee = new TextField();
        TextField policyholder = new TextField();
        ComboBox<String> coveredCountries = new ComboBox<>();
        TextField insuredSharePolitical = new TextField();
        TextField waitingPeriodPolitical = new TextField();
        TextField maxPoliticalCreditPeriod = new TextField();
        TextField insuredShareCommercial = new TextField();
        TextField waitingPeriodCommercial = new TextField();
        TextField maxCommercialCreditPeriod = new TextField();
        TextField clientName = new TextField();

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
        h3.setText("Создание нового договора");
        h3.setWidth("100%");
        formLayout2Col.setWidth("100%");
        contractNumber.setLabel("Страховой номер");
        insurer.setLabel("Страховщик");
        status.setItems("В работе", "Выполнено", "Отменено");
        status.setLabel("Статус");
        startDateOfInsuranceCoverage.setLabel("Дата начала страхования");
        endDateOfInsuranceCoverage.setLabel("Дата окончания страхования");
        supervisingUndewriter.setLabel("Курирующий андерайтер");
        supervisingUOPBEmployee.setLabel("Курирующий УОПБ сотрудник");
        policyholder.setLabel("Страхователь");
        coveredCountries.setItems("В работе", "Выполнено", "Отменено");
        coveredCountries.setLabel("Покрытые страны");
        insuredSharePolitical.setLabel("собственное участие Страхователя в убытке, политическое");
        waitingPeriodPolitical.setLabel("период ожидания, политический");
        maxPoliticalCreditPeriod.setLabel("макcимальный период политического кредита");
        insuredShareCommercial.setLabel("собственное участие Страхователя в убытке, коммерческое");
        waitingPeriodCommercial.setLabel("период ожидания, коммерческий");
        maxCommercialCreditPeriod.setLabel("макcимальный период коммерческого кредита");
        clientName.setLabel("Имя клиента");

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
        formLayout2Col.add(contractNumber);
        formLayout2Col.add(insurer);
        formLayout2Col.add(status);
        formLayout2Col.add(startDateOfInsuranceCoverage);
        formLayout2Col.add(endDateOfInsuranceCoverage);
        formLayout2Col.add(supervisingUndewriter);
        formLayout2Col.add(supervisingUOPBEmployee);
        formLayout2Col.add(policyholder);
        formLayout2Col.add(coveredCountries);
        formLayout2Col.add(insuredSharePolitical);
        formLayout2Col.add(waitingPeriodPolitical);
        formLayout2Col.add(maxPoliticalCreditPeriod);
        formLayout2Col.add(insuredShareCommercial);
        formLayout2Col.add(waitingPeriodCommercial);
        formLayout2Col.add(maxCommercialCreditPeriod);
        formLayout2Col.add(clientName);

        layoutColumn2.add(layoutRow);
        layoutRow.add(buttonPrimary);
        layoutRow.add(buttonSecondary);
    }
}