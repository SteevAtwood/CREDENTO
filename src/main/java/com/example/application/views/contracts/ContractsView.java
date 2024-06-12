package com.example.application.views.contracts;

import com.example.application.data.Contract;
import com.example.application.data.Policyholder;
import com.example.application.data.coveredRisksEnum.CoveredRisksEnum;
import com.example.application.data.statusEnum.StatusEnum;
import com.example.application.services.ContractService;
import com.example.application.services.PolicyholderService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.persistence.criteria.Predicate;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

@AnonymousAllowed
@Route(value = "contracts", layout = MainLayout.class)
@RouteAlias(value = "contracts", layout = MainLayout.class)
@Uses(Icon.class)
public class ContractsView extends Div {

    private Grid<Contract> grid;
    private Filters filters;

    @Autowired
    ContractService contractService;
    @Autowired
    PolicyholderService policyholderService;

    public ContractsView(ContractService contractService, PolicyholderService policyholderService) {
        this.contractService = contractService;
        this.policyholderService = policyholderService;

        setSizeFull();
        addClassNames("contract-view");

        filters = new Filters(() -> refreshGrid(), policyholderService);
        VerticalLayout layout = new VerticalLayout(createMobileFilters(), filters, createGrid());
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        add(layout);
    }

    private HorizontalLayout createMobileFilters() {
        // Mobile version
        HorizontalLayout mobileFilters = new HorizontalLayout();
        mobileFilters.setWidthFull();
        mobileFilters.addClassNames(LumoUtility.Padding.MEDIUM, LumoUtility.BoxSizing.BORDER,
                LumoUtility.AlignItems.CENTER);
        mobileFilters.addClassName("mobile-filters");

        Icon mobileIcon = new Icon("lumo");
        Span filtersHeading = new Span("Filters");
        mobileFilters.add(mobileIcon, filtersHeading);
        mobileFilters.setFlexGrow(1, filtersHeading);
        mobileFilters.addClickListener(e -> {
            if (filters.getClassNames().contains("visible")) {
                filters.removeClassName("visible");
                mobileIcon.getElement().setAttribute("icon", "lumo:plus");
            } else {
                filters.addClassName("visible");
                mobileIcon.getElement().setAttribute("icon", "lumo:minus");
            }
        });
        return mobileFilters;
    }

    public static class Filters extends Div implements Specification<Contract> {

        @Autowired
        PolicyholderService policyholderService;

        private final TextField contractNumber = new TextField("Номер договора");
        private final ComboBox<Policyholder> policyholder = new ComboBox<>("Страхователь");
        private final ComboBox<StatusEnum> status = new ComboBox<>("Статус");
        private final DatePicker startDateOfInsuranceCoverage = new DatePicker("Дата начала страхования");
        private final DatePicker endDateOfInsuranceCoverage = new DatePicker("Дата окончания страхования");
        private final ComboBox<CoveredRisksEnum> coveredRisks = new ComboBox<>("Покрытые риски");

        public Filters(Runnable onSearch, PolicyholderService policyholderService) {

            this.policyholderService = policyholderService;

            setWidthFull();
            addClassName("filter-layout");
            addClassNames(LumoUtility.Padding.Horizontal.LARGE, LumoUtility.Padding.Vertical.MEDIUM,
                    LumoUtility.BoxSizing.BORDER);

            policyholder.setItems(policyholderService.getAllPolicyholders());
            policyholder.setItemLabelGenerator(Policyholder::getCompanyName);

            status.setItems(StatusEnum.values());
            coveredRisks.setItems(CoveredRisksEnum.values());

            HorizontalLayout filterLayout = new HorizontalLayout();
            filterLayout.setSpacing(true);
            filterLayout.setAlignItems(FlexComponent.Alignment.CENTER);

            filterLayout.add(contractNumber, policyholder, status, createDateRangeFilter(),
                    coveredRisks);

            Button resetBtn = new Button("Очистить");
            resetBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            resetBtn.addClickListener(e -> {
                contractNumber.clear();
                policyholder.clear();
                status.clear();
                startDateOfInsuranceCoverage.clear();
                endDateOfInsuranceCoverage.clear();
                coveredRisks.clear();
                onSearch.run();
            });

            Button searchBtn = new Button("Найти");
            searchBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            searchBtn.addClickListener(e -> onSearch.run());

            Div actions = new Div(resetBtn, searchBtn);
            actions.addClassName(LumoUtility.Gap.SMALL);
            actions.addClassName("actions");

            filterLayout.add(resetBtn, searchBtn);

            add(filterLayout, actions);
        }

        private Component createDateRangeFilter() {
            startDateOfInsuranceCoverage.setPlaceholder("От");
            endDateOfInsuranceCoverage.setPlaceholder("До");
            startDateOfInsuranceCoverage.setAriaLabel("От даты");
            endDateOfInsuranceCoverage.setAriaLabel("До даты");

            FlexLayout dateRangeComponent = new FlexLayout(startDateOfInsuranceCoverage, new Text(" – "),
                    endDateOfInsuranceCoverage);
            dateRangeComponent.setAlignItems(FlexComponent.Alignment.BASELINE);
            dateRangeComponent.addClassNames(LumoUtility.Gap.XSMALL);

            return dateRangeComponent;
        }

        @Override
        public Predicate toPredicate(Root<Contract> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            List<Predicate> predicates = new ArrayList<>();

            if (!contractNumber.isEmpty()) {
                String lowerCaseFilter = contractNumber.getValue().toLowerCase();
                Predicate contractNumberMatch = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("insuranceContractNumber")),
                        lowerCaseFilter + "%");
                predicates.add(contractNumberMatch);
            }
            if (policyholder.getValue() != null) {
                predicates.add(criteriaBuilder.equal(root.get("policyholder"), policyholder.getValue()));
            }
            if (status.getValue() != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status.getValue()));
            }
            if (startDateOfInsuranceCoverage.getValue() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("startDateOfInsuranceCoverage"),
                        criteriaBuilder.literal(startDateOfInsuranceCoverage.getValue())));
            }
            if (endDateOfInsuranceCoverage.getValue() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("endDateOfInsuranceCoverage"),
                        java.sql.Date.valueOf(endDateOfInsuranceCoverage.getValue().plusDays(1))));
            }
            // if (!policyholder.isEmpty()) {
            // String lowerCaseFilter = policyholder.getValue().toLowerCase();
            // Predicate policyholderMatch =
            // criteriaBuilder.like(criteriaBuilder.lower(root.get("policyholder")),
            // lowerCaseFilter + "%");
            // predicates.add(policyholderMatch);
            // }
            if (coveredRisks.getValue() != null) {
                predicates.add(criteriaBuilder.equal(root.get("coveredRisks"), coveredRisks.getValue()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }
    }

    private Component createGrid() {
        grid = new Grid<>(Contract.class, false);
        grid.addColumn("insuranceContractNumber").setAutoWidth(true).setHeader("Номер договора");
        grid.addColumn(contract -> contract.getPolicyholder().getCompanyName())
                .setAutoWidth(true)
                .setHeader("Страхователь");
        grid.addColumn("status").setAutoWidth(true).setHeader("Статус");
        grid.addColumn("startDateOfInsuranceCoverage").setAutoWidth(true).setHeader("Дата начала страхования");
        grid.addColumn("endDateOfInsuranceCoverage").setAutoWidth(true).setHeader("Дата окончания страхования");
        grid.addColumn("coveredRisks").setAutoWidth(true).setHeader("Покрытые риски");

        grid.setItems(query -> contractService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)),
                filters).stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_10);

        grid.addItemClickListener(event -> {
            Contract selectedContract = event.getItem();
            String contractId = String.valueOf(selectedContract.getId());
            getUI().ifPresent(ui -> ui.navigate("contracts/" + contractId));
        });

        return grid;
    }

    private void refreshGrid() {
        grid.getDataProvider().refreshAll();
    }
}
