package com.example.application.views.contracts;

import com.example.application.data.Contract;
import com.example.application.data.coveredRisksEnum.CoveredRisksEnum;
import com.example.application.data.statusEnum.StatusEnum;
import com.example.application.services.ContractService;
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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

@AnonymousAllowed
@Route(value = "contracts", layout = MainLayout.class)
@RouteAlias(value = "contracts", layout = MainLayout.class)
@Uses(Icon.class)
public class ContractsView extends Div {

    private Grid<Contract> grid;
    private Filters filters;

    private final ContractService contractService;

    public ContractsView(ContractService contractService) {
        this.contractService = contractService;
        setSizeFull();
        addClassNames("contract-view");

        filters = new Filters(() -> refreshGrid());
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

        private final TextField insuranceContractNumber = new TextField("Insurance Contract Number");
        private final TextField insurer = new TextField("Insurer");
        private final ComboBox<StatusEnum> status = new ComboBox<>("Status");
        private final DatePicker startDateOfInsuranceCoverage = new DatePicker("Start Date of Insurance Coverage");
        private final DatePicker endDateOfInsuranceCoverage = new DatePicker("End Date of Insurance Coverage");
        private final TextField policyholder = new TextField("Policyholder");
        private final ComboBox<CoveredRisksEnum> coveredRisks = new ComboBox<>("Covered Risks");

        public Filters(Runnable onSearch) {
            setWidthFull();
            addClassName("filter-layout");
            addClassNames(LumoUtility.Padding.Horizontal.LARGE, LumoUtility.Padding.Vertical.MEDIUM,
                    LumoUtility.BoxSizing.BORDER);

            status.setItems(StatusEnum.values());
            coveredRisks.setItems(CoveredRisksEnum.values());

            HorizontalLayout filterLayout = new HorizontalLayout();
            filterLayout.setSpacing(true);
            filterLayout.setAlignItems(FlexComponent.Alignment.CENTER);

            filterLayout.add(insuranceContractNumber, insurer, status, createDateRangeFilter(), policyholder,
                    coveredRisks);

            Button resetBtn = new Button("Reset");
            resetBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            resetBtn.addClickListener(e -> {
                insuranceContractNumber.clear();
                insurer.clear();
                status.clear();
                startDateOfInsuranceCoverage.clear();
                endDateOfInsuranceCoverage.clear();
                policyholder.clear();
                coveredRisks.clear();
                onSearch.run();
            });

            Button searchBtn = new Button("Search");
            searchBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            searchBtn.addClickListener(e -> onSearch.run());

            Div actions = new Div(resetBtn, searchBtn);
            actions.addClassName(LumoUtility.Gap.SMALL);
            actions.addClassName("actions");

            filterLayout.add(resetBtn, searchBtn);

            add(filterLayout, actions);
        }

        private Component createDateRangeFilter() {
            startDateOfInsuranceCoverage.setPlaceholder("From");
            endDateOfInsuranceCoverage.setPlaceholder("To");
            startDateOfInsuranceCoverage.setAriaLabel("From date");
            endDateOfInsuranceCoverage.setAriaLabel("To date");

            FlexLayout dateRangeComponent = new FlexLayout(startDateOfInsuranceCoverage, new Text(" – "),
                    endDateOfInsuranceCoverage);
            dateRangeComponent.setAlignItems(FlexComponent.Alignment.BASELINE);
            dateRangeComponent.addClassNames(LumoUtility.Gap.XSMALL);

            return dateRangeComponent;
        }

        @Override
        public Predicate toPredicate(Root<Contract> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            List<Predicate> predicates = new ArrayList<>();

            if (!insuranceContractNumber.isEmpty()) {
                String lowerCaseFilter = insuranceContractNumber.getValue().toLowerCase();
                Predicate contractNumberMatch = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("insuranceContractNumber")),
                        lowerCaseFilter + "%");
                predicates.add(contractNumberMatch);
            }
            if (!insurer.isEmpty()) {
                String lowerCaseFilter = insurer.getValue().toLowerCase();
                Predicate insurerMatch = criteriaBuilder.like(criteriaBuilder.lower(root.get("insurer")),
                        lowerCaseFilter + "%");
                predicates.add(insurerMatch);
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
                        Timestamp.valueOf(endDateOfInsuranceCoverage.getValue().atStartOfDay().plusDays(1))));
            }
            if (!policyholder.isEmpty()) {
                String lowerCaseFilter = policyholder.getValue().toLowerCase();
                Predicate policyholderMatch = criteriaBuilder.like(criteriaBuilder.lower(root.get("policyholder")),
                        lowerCaseFilter + "%");
                predicates.add(policyholderMatch);
            }
            if (coveredRisks.getValue() != null) {
                predicates.add(criteriaBuilder.equal(root.get("coveredRisks"), coveredRisks.getValue()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }
    }

    private Component createGrid() {
        grid = new Grid<>(Contract.class, false);
        grid.addColumn("insuranceContractNumber").setAutoWidth(true).setHeader("Insurance Contract Number");
        grid.addColumn("insurer").setAutoWidth(true).setHeader("Insurer");
        grid.addColumn("status").setAutoWidth(true).setHeader("Status");
        grid.addColumn("startDateOfInsuranceCoverage").setAutoWidth(true).setHeader("Start Date of Insurance Coverage");
        grid.addColumn("endDateOfInsuranceCoverage").setAutoWidth(true).setHeader("End Date of Insurance Coverage");
        grid.addColumn("policyholder").setAutoWidth(true).setHeader("Policyholder");
        grid.addColumn("coveredRisks").setAutoWidth(true).setHeader("Covered Risks");

        grid.setItems(query -> contractService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)),
                filters).stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_10);

        grid.addItemClickListener(event -> {
            Contract selectedContract = event.getItem();
            String contractId = String.valueOf(selectedContract.getId());
            getUI().ifPresent(ui -> ui.navigate("contracts/" + contractId));
            System.out.println("contracts  ВЫВОД ЧТОБЫ ВИДНО ЧТО НЕ NULL" + contractId);
        });

        return grid;
    }

    private void refreshGrid() {
        grid.getDataProvider().refreshAll();
    }
}
