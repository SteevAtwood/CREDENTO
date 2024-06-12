package com.example.application.views.contracts;

import com.example.application.services.ContractService;
import com.example.application.services.CountryService;
import com.example.application.services.PolicyholderService;
import com.example.application.services.UserService;

import java.sql.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.application.data.Country;
import com.example.application.data.DateConversionUtil;
import com.example.application.data.Policyholder;
import com.example.application.data.User;
import com.example.application.data.coveredRisksEnum.CoveredRisksEnum;
import com.example.application.data.statusEnum.StatusEnum;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

import jakarta.annotation.security.RolesAllowed;

@PageTitle("Договоры")
@Route(value = "create-contract", layout = MainLayout.class)
@RouteAlias(value = "create-contract", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@Uses(Icon.class)
public class CreateContractView extends Composite<VerticalLayout> {

    @Autowired
    ContractService contractService;
    @Autowired
    UserService userService;
    @Autowired
    CountryService countryService;
    @Autowired
    PolicyholderService policyholderService;

    public CreateContractView(ContractService contractService, UserService userService, CountryService countryService,
            PolicyholderService policyholderService) {
        this.contractService = contractService;
        this.userService = userService;
        this.countryService = countryService;
        this.policyholderService = policyholderService;

        VerticalLayout layoutColumn2 = new VerticalLayout();
        H3 h3 = new H3();
        FormLayout formLayout2Col = new FormLayout();
        TextField insuranceContractNumber = new TextField();
        ComboBox<String> insurer = new ComboBox<>();
        DatePicker startDateOfInsuranceCoverage = new DatePicker();
        DatePicker endDateOfInsuranceCoverage = new DatePicker();
        ComboBox<StatusEnum> status = new ComboBox<>();

        ComboBox<User> supervisingUnderwriter = new ComboBox<>();
        ComboBox<User> supervisingUOPBEmployee = new ComboBox<>();
        ComboBox<Policyholder> policyholder = new ComboBox<>();
        MultiSelectComboBox<Country> coveredCountries = new MultiSelectComboBox<>("Страны покрытия");
        ComboBox<CoveredRisksEnum> coveredRisks = new ComboBox<>();
        IntegerField insuredSharePolitical = new IntegerField();
        TextField waitingPeriodPolitical = new TextField();
        TextField maxPoliticalCreditPeriod = new TextField();
        IntegerField insuredShareCommercial = new IntegerField();
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
        insurer.setItems("ООО «Кредендо – Ингосстрах Кредитное Страхование»");
        insurer.setValue("ООО «Кредендо – Ингосстрах Кредитное Страхование»");
        status.setItems(StatusEnum.values());
        status.setItemLabelGenerator(StatusEnum::getDisplayName);
        status.setLabel("Статус");
        startDateOfInsuranceCoverage.setLabel("Дата начала страхования");
        endDateOfInsuranceCoverage.setLabel("Дата окончания страхования");
        supervisingUnderwriter.setLabel("Курирующий андерайтер");
        List<User> mainUnderwriters = userService.getUsersWithRoleMainUnderwriter();
        supervisingUnderwriter.setItems(mainUnderwriters);
        supervisingUnderwriter.setItemLabelGenerator(User::getName);

        supervisingUOPBEmployee.setLabel("Курирующий УОПБ сотрудник");
        List<User> mainUOPBemployees = userService.getUsersWithRoleSupervisingUOPBEmployee();
        supervisingUOPBEmployee.setItems(mainUOPBemployees);
        supervisingUOPBEmployee.setItemLabelGenerator(User::getName);

        policyholder.setLabel("Страхователь");
        List<Policyholder> policyholders = policyholderService.getAllPolicyholders();
        policyholder.setItems(policyholders);
        policyholder.setItemLabelGenerator(Policyholder::getCompanyName);

        policyholder.setLabel("Страхователь");
        coveredCountries.setLabel("Страны покрытия");
        coveredCountries.setItems(countryService.getAllCounties());
        coveredCountries.setItemLabelGenerator(Country::getName);

        coveredRisks.setLabel("Риски покрытия");
        coveredRisks.setItems(CoveredRisksEnum.values());
        coveredRisks.setItemLabelGenerator(CoveredRisksEnum::getDisplayName);

        insuredSharePolitical.setLabel("Политическое участие Страхователя в убытке");
        insuredSharePolitical.setValue(0);
        insuredSharePolitical.setStepButtonsVisible(true);
        insuredSharePolitical.setMin(0);
        insuredSharePolitical.setMax(100);
        insuredSharePolitical.setStep(1);
        insuredSharePolitical.setSuffixComponent(new Span("%"));
        insuredSharePolitical.setValueChangeMode(ValueChangeMode.EAGER);
        Registration valueChangeListenerPolitical = insuredSharePolitical.addValueChangeListener(event -> {
            Integer value = event.getValue();
            if (value != null) {
                String valueString = value.toString();
                if (value < 0) {
                    insuredSharePolitical.setValue(0);
                    Notification.show("Значение не может быть меньше 0");
                } else if (valueString.length() > 3) {
                    String newValueString = valueString.substring(0, 3);
                    insuredSharePolitical.setValue(Integer.valueOf(newValueString));
                    Notification.show("Значение не может содержать более 3 символов");
                } else if (value > 100) {
                    insuredSharePolitical.setValue(100);
                    Notification.show("Значение не может быть больше 100");
                }
            }
        });

        addDetachListener(detachEvent -> valueChangeListenerPolitical.remove());

        waitingPeriodPolitical.setLabel("Политический период ожидания");
        maxPoliticalCreditPeriod.setLabel("Макcимальный период политического кредита");
        insuredShareCommercial.setLabel("Коммерческое участие Страхователя в убытке");
        insuredShareCommercial.setValue(0);
        insuredShareCommercial.setStepButtonsVisible(true);
        insuredShareCommercial.setMin(0);
        insuredShareCommercial.setMax(100);
        insuredShareCommercial.setStep(1);
        insuredShareCommercial.setSuffixComponent(new Span("%"));
        insuredShareCommercial.setValueChangeMode(ValueChangeMode.EAGER);
        Registration valueChangeListenerCommercial = insuredShareCommercial.addValueChangeListener(event -> {
            Integer value = event.getValue();
            if (value != null) {
                String valueString = value.toString();
                if (value < 0) {
                    insuredShareCommercial.setValue(0);
                    Notification.show("Значение не может быть меньше 0");
                } else if (valueString.length() > 3) {
                    String newValueString = valueString.substring(0, 3);
                    insuredShareCommercial.setValue(Integer.valueOf(newValueString));
                    Notification.show("Значение не может содержать более 3 символов");
                } else if (value > 100) {
                    insuredShareCommercial.setValue(100);
                    Notification.show("Значение не может быть больше 100");
                }
            }
        });
        addDetachListener(detachEvent -> valueChangeListenerCommercial.remove());

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

        formLayout2Col.add(supervisingUnderwriter);
        formLayout2Col.add(supervisingUOPBEmployee);
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
            User selectedSupervisingUnderwriter = supervisingUnderwriter.getValue();
            User selectedSupervisingUOPBEmployee = supervisingUOPBEmployee.getValue();
            Policyholder selectedPolicyholder = policyholder.getValue();

            Set<String> countryNames = coveredCountries.getValue().stream()
                    .map(Country::getName)
                    .collect(Collectors.toSet());

            contractService.createContract(
                    insuranceContractNumber.getValue(),
                    insurer.getValue(),
                    status.getValue(),
                    DateConversionUtil.toLocalDate(startDate),
                    DateConversionUtil.toLocalDate(endDate),
                    selectedSupervisingUnderwriter,
                    selectedSupervisingUOPBEmployee,
                    selectedPolicyholder,
                    countryNames,
                    coveredRisks.getValue(),
                    insuredSharePolitical.getValue().toString(),
                    Integer.valueOf(waitingPeriodPolitical.getValue()),
                    Integer.valueOf(maxPoliticalCreditPeriod.getValue()),
                    insuredShareCommercial.getValue().toString(),
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
            supervisingUnderwriter.clear();
            supervisingUOPBEmployee.clear();
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
