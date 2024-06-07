package com.example.application.views.contracts;

import com.example.application.services.ContractService;
import com.example.application.services.UserService;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.application.data.DateConversionUtil;
import com.example.application.data.User;
import com.example.application.data.coveredRisksEnum.CoveredRisksEnum;
import com.example.application.data.statusEnum.StatusEnum;
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
@Route(value = "create-contract", layout = MainLayout.class)
@RouteAlias(value = "create-contract", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@Uses(Icon.class)
public class CreateContractView extends Composite<VerticalLayout> {

    private final ContractService contractService;
    @Autowired
    private final UserService userService;

    public CreateContractView(ContractService contractService, UserService userService) {
        this.contractService = contractService;
        this.userService = userService;

        VerticalLayout layoutColumn2 = new VerticalLayout();
        H3 h3 = new H3();
        FormLayout formLayout2Col = new FormLayout();
        TextField insuranceContractNumber = new TextField();
        TextField insurer = new TextField();
        DatePicker startDateOfInsuranceCoverage = new DatePicker();
        DatePicker endDateOfInsuranceCoverage = new DatePicker();
        ComboBox<StatusEnum> status = new ComboBox<>();

        ComboBox<String> supervisingUndewriter = new ComboBox<>();
        ComboBox<String> supervising_UOPB_employee = new ComboBox<>();
        TextField policyholder = new TextField();
        ComboBox<String> coveredCountries = new ComboBox<>();
        ComboBox<CoveredRisksEnum> coveredRisks = new ComboBox<>();
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
        insuranceContractNumber.setLabel("Страховой номер");
        insurer.setLabel("Страховщик");
        status.setItems(StatusEnum.values());
        status.setItemLabelGenerator(StatusEnum::getDisplayName);
        status.setLabel("Статус");
        startDateOfInsuranceCoverage.setLabel("Дата начала страхования");
        endDateOfInsuranceCoverage.setLabel("Дата окончания страхования");
        supervisingUndewriter.setLabel("Курирующий андерайтер");
        List<User> mainUnderwriters = userService.getUsersWithRoleMainUndewtirher();
        List<String> mainUnderwriterNames = mainUnderwriters.stream()
                .map(User::getName)
                .collect(Collectors.toList());
        supervisingUndewriter.setItems(mainUnderwriterNames);
        supervising_UOPB_employee.setLabel("Курирующий УОПБ сотрудник");
        List<User> mainUOPBemployees = userService.getUsersWithRoleSupervisingUOPBemployee();
        List<String> mainUOPBemployeesNames = mainUOPBemployees.stream()
                .map(User::getName)
                .collect(Collectors.toList());
        supervising_UOPB_employee.setItems(mainUOPBemployeesNames);
        policyholder.setLabel("Страхователь");
        coveredCountries.setLabel("Страны покрытия");
        coveredCountries.setItems("Россия", "Украина", "Беларусь");

        coveredRisks.setLabel("Покрытые риски");
        coveredRisks.setItems(CoveredRisksEnum.values());
        coveredRisks.setItemLabelGenerator(CoveredRisksEnum::getDisplayName);
        insuredSharePolitical.setLabel("Политическое участие Страхователя в убытке");
        waitingPeriodPolitical.setLabel("Политический период ожидания");
        maxPoliticalCreditPeriod.setLabel("Макcимальный период политического кредита");
        insuredShareCommercial.setLabel("Коммерческое участие Страхователя в убытке");
        waitingPeriodCommercial.setLabel("Коммерческий период ожидания");
        maxCommercialCreditPeriod.setLabel("Макcимальный период коммерческиеого кредита");
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
        formLayout2Col.add(insuranceContractNumber);
        formLayout2Col.add(insurer);
        formLayout2Col.add(startDateOfInsuranceCoverage);
        formLayout2Col.add(endDateOfInsuranceCoverage);
        formLayout2Col.add(status);

        formLayout2Col.add(supervisingUndewriter);
        formLayout2Col.add(supervising_UOPB_employee);
        formLayout2Col.add(policyholder);
        formLayout2Col.add(coveredCountries);
        formLayout2Col.add(coveredRisks);
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

        buttonPrimary.addClickListener(e -> {
            Date startDate = startDateOfInsuranceCoverage.getValue() != null
                    ? Date.valueOf(startDateOfInsuranceCoverage.getValue())
                    : null;
            Date endDate = endDateOfInsuranceCoverage.getValue() != null
                    ? Date.valueOf(endDateOfInsuranceCoverage.getValue())
                    : null;

            contractService.createContract(
                    insuranceContractNumber.getValue(),
                    insurer.getValue(),
                    status.getValue(),
                    DateConversionUtil.toLocalDate(startDate),
                    DateConversionUtil.toLocalDate(endDate),
                    supervisingUndewriter.getValue(),
                    supervising_UOPB_employee.getValue(),
                    policyholder.getValue(),
                    coveredCountries.getValue(),
                    coveredRisks.getValue(),
                    insuredSharePolitical.getValue(),
                    Integer.valueOf(waitingPeriodPolitical.getValue()),
                    Integer.valueOf(maxPoliticalCreditPeriod.getValue()),
                    insuredShareCommercial.getValue(),
                    Integer.valueOf(waitingPeriodCommercial.getValue()),
                    Integer.valueOf(maxCommercialCreditPeriod.getValue()),
                    clientName.getValue());
        });

        buttonSecondary.addClickListener(e -> {
            // Clear all fields or navigate away
            insuranceContractNumber.clear();
            insurer.clear();
            status.clear();
            startDateOfInsuranceCoverage.clear();
            endDateOfInsuranceCoverage.clear();
            supervisingUndewriter.clear();
            supervising_UOPB_employee.clear();
            policyholder.clear();
            coveredCountries.clear();
            coveredRisks.clear();
            insuredSharePolitical.clear();
            waitingPeriodPolitical.clear();
            maxPoliticalCreditPeriod.clear();
            insuredShareCommercial.clear();
            waitingPeriodCommercial.clear();
            maxCommercialCreditPeriod.clear();
            clientName.clear();
        });
    }
}
