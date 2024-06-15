package com.example.application.views.debtors;

import com.example.application.data.Debtors;
import com.example.application.data.Policyholder;
import com.example.application.data.statusEnum.StatusEnum;
import com.example.application.services.ContractService;
import com.example.application.services.DebtorService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@AnonymousAllowed
@Route(value = "debtors", layout = MainLayout.class)
@RouteAlias(value = "debtors", layout = MainLayout.class)
@Uses(Icon.class)
public class DebtorsView extends Div {

    private final Grid<Debtors> grid;
    @Autowired
    DebtorService debtorService;
    @Autowired
    ContractService contractService;
    private final Filters filters;

    public DebtorsView(DebtorService debtorService, ContractService contractService) {
        this.debtorService = debtorService;
        this.contractService = contractService;
        this.grid = createGrid();
        this.filters = new Filters(this::refreshGrid, debtorService, contractService);

        setSizeFull();
        addClassNames("request-view");

        VerticalLayout layout = new VerticalLayout(createMobileFilters(), filters, grid);
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

    public static class Filters extends Div implements Specification<Debtors> {

        @Autowired
        DebtorService debtorService;
        @Autowired
        ContractService contractService;

        private final ComboBox<Policyholder> policyholder = new ComboBox<>("Страхователь");
        private final TextField companyName = new TextField("Название компании");
        private final TextField policyholderCompanyName = new TextField("Страхователь");
        private final TextField address = new TextField("Адрес");
        private final TextField informationProviderCode = new TextField("Код информационного провайдера");
        private final TextField companyRegistrationCodes = new TextField("Регистрационные коды компании");
        private final TextField okvedCode = new TextField("Код ОКВЭД");
        private final ComboBox<StatusEnum> status = new ComboBox<>("Статус компании");

        public Filters(Runnable onSearch, DebtorService debtorService, ContractService contractService) {
            this.debtorService = debtorService;
            this.contractService = contractService;
            setWidthFull();
            addClassName("filter-layout");
            addClassNames(LumoUtility.Padding.Horizontal.LARGE, LumoUtility.Padding.Vertical.MEDIUM,
                    LumoUtility.BoxSizing.BORDER);

            // Assuming you have a method to get all policyholders
            // policyholder.setItems(debtorService.getAllPolicyholders());
            // policyholder.setItemLabelGenerator(Policyholder::getName);

            status.setItems(StatusEnum.values());

            HorizontalLayout filterLayout = new HorizontalLayout();
            filterLayout.setSpacing(true);
            filterLayout.setAlignItems(FlexComponent.Alignment.CENTER);

            filterLayout.add(policyholder, companyName, address, informationProviderCode, companyRegistrationCodes,
                    okvedCode, status);

            Button resetBtn = new Button("Очистить");
            resetBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            resetBtn.addClickListener(e -> {
                clearFields();
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

        private void clearFields() {
            policyholder.clear();
            companyName.clear();
            policyholderCompanyName.clear();
            address.clear();
            informationProviderCode.clear();
            companyRegistrationCodes.clear();
            okvedCode.clear();
            status.clear();
        }

        @Override
        public Specification<Debtors> and(Specification<Debtors> other) {
            return null;
        }

        @Override
        public Specification<Debtors> or(Specification<Debtors> other) {
            return null;
        }

        @Override
        public Predicate toPredicate(Root<Debtors> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            List<Predicate> predicates = new ArrayList<>();

            if (policyholder.getValue() != null) {
                predicates.add(criteriaBuilder.equal(root.get("policyholder"), policyholder.getValue()));
            }
            if (!companyName.isEmpty()) {
                String lowerCaseFilter = companyName.getValue().toLowerCase();
                Predicate nameMatch = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("companyName")),
                        lowerCaseFilter + "%");
                predicates.add(nameMatch);
            }
            if (!policyholderCompanyName.isEmpty()) {
                String lowerCaseFilter = policyholderCompanyName.getValue().toLowerCase();
                Predicate policyholderMatch = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("policyholderCompanyName")),
                        lowerCaseFilter + "%");
                predicates.add(policyholderMatch);
            }
            if (!address.isEmpty()) {
                String lowerCaseFilter = address.getValue().toLowerCase();
                Predicate addressMatch = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("address")),
                        lowerCaseFilter + "%");
                predicates.add(addressMatch);
            }
            if (!informationProviderCode.isEmpty()) {
                String lowerCaseFilter = informationProviderCode.getValue().toLowerCase();
                Predicate codeMatch = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("informationProviderCode")),
                        lowerCaseFilter + "%");
                predicates.add(codeMatch);
            }
            if (!companyRegistrationCodes.isEmpty()) {
                String lowerCaseFilter = companyRegistrationCodes.getValue().toLowerCase();
                Predicate regCodeMatch = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("companyRegistrationCodes")),
                        lowerCaseFilter + "%");
                predicates.add(regCodeMatch);
            }
            if (!okvedCode.isEmpty()) {
                String lowerCaseFilter = okvedCode.getValue().toLowerCase();
                Predicate okvedMatch = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("okvedCode")),
                        lowerCaseFilter + "%");
                predicates.add(okvedMatch);
            }
            if (status.getValue() != null) {
                predicates.add(criteriaBuilder.equal(root.get("companyStatus"), status.getValue()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }
    }

    private Grid<Debtors> createGrid() {
        Grid<Debtors> grid = new Grid<>(Debtors.class, false);
        // grid.addColumn(debtors ->
        // debtors.getInsuranceContract().getPolicyholder().getName()) // Adjust based
        // on your method
        // .setAutoWidth(true)
        // .setHeader("Страхователь");

        grid.addColumn("companyName").setAutoWidth(true).setHeader("Название компании");
        grid.addColumn("address").setAutoWidth(true).setHeader("Адрес");
        grid.addColumn("informationProviderCode").setAutoWidth(true).setHeader("Код информационного провайдера");
        grid.addColumn("companyRegistrationCodes").setAutoWidth(true).setHeader("Регистрационные коды компании");
        grid.addColumn("okvedCode").setAutoWidth(true).setHeader("Код ОКВЭД");
        grid.addColumn("companyStatus").setAutoWidth(true).setHeader("Статус компании");

        grid.setItems(query -> debtorService.list(
                PageRequest.of(query.getPage(), query.getPageSize()), filters).stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_10);

        grid.addItemClickListener(event -> {
            Debtors selectedDebtor = event.getItem();
            String debtorId = String.valueOf(selectedDebtor.getId());
            getUI().ifPresent(ui -> ui.navigate("debtors/" + debtorId));
            System.out.println("Выбранный дебитор: " + debtorId);
        });

        return grid;
    }

    // List<Debtors> filteredDebtors = debtorService
    // .getDebtorsByPolicyholderCompanyName(filters.policyholderCompanyName.getValue());
    // grid.setItems(filteredDebtors);

    private void refreshGrid() {
        grid.getDataProvider().refreshAll();
    }
}
