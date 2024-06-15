package com.example.application.views.requests;

import com.example.application.data.Debtors;
import com.example.application.data.Request;
import com.example.application.data.requestStatusEnum.RequestStatusEnum;
import com.example.application.services.DebtorService;
import com.example.application.services.RequestService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
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
@Route(value = "accepted-requests/:debtorId", layout = MainLayout.class)
@Uses(Icon.class)
public class RequestsSuccessView extends Div implements BeforeEnterObserver {

    private Grid<Request> grid;
    private Filters filters;
    @Autowired
    RequestService requestService;
    @Autowired
    DebtorService debtorService;
    private Integer debtorId;

    public RequestsSuccessView(RequestService requestService, DebtorService debtorService) {
        this.requestService = requestService;
        this.debtorService = debtorService;

        setSizeFull();
        addClassNames("requests-view");

        filters = new Filters(() -> refreshGrid(), debtorService);
        VerticalLayout layout = new VerticalLayout(createMobileFilters(), filters, createGrid());
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        add(layout);

    }

    private HorizontalLayout createMobileFilters() {
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

    public static class Filters extends Div implements Specification<Request> {

        @Autowired
        DebtorService debtorService;

        private final ComboBox<Debtors> debtor = new ComboBox<>("Дебитор");
        private final TextField contractNumber = new TextField("Номер договора");
        private final TextField debitorsCountry = new TextField("Страна дебитора");
        private final TextField registrationCode = new TextField("Регистрационный код");
        private final TextField clAmount = new TextField("Сумма");
        private final TextField clTermsAndConditions = new TextField("Условия");
        private final ComboBox<RequestStatusEnum> status = new ComboBox<>("Статус");
        private final TextField adjustmentPossibility = new TextField("Возможность корректировки");
        private final ComboBox<String> clCurrency = new ComboBox<>("Валюта CL");

        public Filters(Runnable onSearch, DebtorService debtorService) {
            this.debtorService = debtorService;

            setWidthFull();
            addClassName("filter-layout");
            addClassNames(LumoUtility.Padding.Horizontal.LARGE, LumoUtility.Padding.Vertical.MEDIUM,
                    LumoUtility.BoxSizing.BORDER);

            debtor.setItems(debtorService.getDebtors());
            debtor.setItemLabelGenerator(Debtors::getCompanyName);

            status.setItems(RequestStatusEnum.values());

            clCurrency.setItems("RUB", "USD", "EUR");

            HorizontalLayout filterLayout = new HorizontalLayout();
            filterLayout.setSpacing(true);
            filterLayout.setAlignItems(FlexComponent.Alignment.CENTER);

            filterLayout.add(debtor, contractNumber, debitorsCountry, registrationCode, clAmount, clCurrency,
                    clTermsAndConditions, status, adjustmentPossibility);

            Button resetBtn = new Button("Очистить");
            resetBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            resetBtn.addClickListener(e -> {
                debtor.clear();
                contractNumber.clear();
                debitorsCountry.clear();
                registrationCode.clear();
                clAmount.clear();
                clCurrency.clear();
                clTermsAndConditions.clear();
                status.clear();
                adjustmentPossibility.clear();
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

        @Override
        public Predicate toPredicate(Root<Request> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            List<Predicate> predicates = new ArrayList<>();

            if (debtor.getValue() != null) {
                predicates.add(criteriaBuilder.equal(root.get("debtor"), debtor.getValue()));
            }
            if (!contractNumber.isEmpty()) {
                String lowerCaseFilter = contractNumber.getValue().toLowerCase();
                Predicate contractNumberMatch = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("insuranceContractNumber")),
                        lowerCaseFilter + "%");
                predicates.add(contractNumberMatch);
            }
            if (!debitorsCountry.isEmpty()) {
                String lowerCaseFilter = debitorsCountry.getValue().toLowerCase();
                Predicate debitorsCountryMatch = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("debitorsCountry")),
                        lowerCaseFilter + "%");
                predicates.add(debitorsCountryMatch);
            }
            if (!registrationCode.isEmpty()) {
                String lowerCaseFilter = registrationCode.getValue().toLowerCase();
                Predicate registrationCodeMatch = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("registrationCode")),
                        lowerCaseFilter + "%");
                predicates.add(registrationCodeMatch);
            }
            if (!clAmount.isEmpty()) {
                String lowerCaseFilter = clAmount.getValue().toLowerCase();
                Predicate clAmountMatch = criteriaBuilder.like(criteriaBuilder.lower(root.get("clAmount")),
                        lowerCaseFilter + "%");
                predicates.add(clAmountMatch);
            }
            if (!clCurrency.isEmpty()) {
                String lowerCaseFilter = clCurrency.getValue().toLowerCase();
                Predicate clCurrencyMatch = criteriaBuilder.like(criteriaBuilder.lower(root.get("clCurrency")),
                        lowerCaseFilter + "%");
                predicates.add(clCurrencyMatch);
            }
            if (!clTermsAndConditions.isEmpty()) {
                String lowerCaseFilter = clTermsAndConditions.getValue().toLowerCase();
                Predicate clTermsAndConditionsMatch = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("clTermsAndConditions")),
                        lowerCaseFilter + "%");
                predicates.add(clTermsAndConditionsMatch);
            }
            if (status.getValue() != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status.getValue()));
            }
            if (!adjustmentPossibility.isEmpty()) {
                String lowerCaseFilter = adjustmentPossibility.getValue().toLowerCase();
                Predicate adjustmentPossibilityMatch = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("adjustmentPossibility")),
                        lowerCaseFilter + "%");
                predicates.add(adjustmentPossibilityMatch);
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }
    }

    private Component createGrid() {
        grid = new Grid<>(Request.class, false);
        grid.addColumn(request -> request.getDebtor().getCompanyName())
                .setAutoWidth(true)
                .setHeader("Дебитор");
        grid.addColumn(request -> request.getInsuranceContractNumber().getInsuranceContractNumber())
                .setAutoWidth(true)
                .setHeader("Номер договора");
        grid.addColumn("debitorsCountry").setAutoWidth(true).setHeader("Страна дебитора");
        grid.addColumn("registrationCode").setAutoWidth(true).setHeader("Регистрационный код");
        grid.addColumn("clAmount").setAutoWidth(true).setHeader("Сумма");
        grid.addColumn("clCurrency").setAutoWidth(true).setHeader("Валюта");
        grid.addColumn("clTermsAndConditions").setAutoWidth(true).setHeader("Условия");
        grid.addColumn("status").setAutoWidth(true).setHeader("Статус");
        grid.addColumn("adjustmentPossibility").setAutoWidth(true).setHeader("Возможность корректировки");

        grid.setItems(query -> requestService.getAcceptedRequestsByDebtorId(debtorId,
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_10);

        grid.addItemClickListener(event -> {
            Request selectedRequest = event.getItem();
            String requestId = String.valueOf(selectedRequest.getId());
            getUI().ifPresent(ui -> ui.navigate("requests/" + requestId));
        });

        return grid;
    }

    private void refreshGrid() {
        grid.getDataProvider().refreshAll();
    }

    // @Override
    // public void beforeEnter(BeforeEnterEvent event) {
    // debtorId =
    // Integer.valueOf(event.getRouteParameters().get("debtorId").orElse("-1"));
    // if (debtorId == -1) {
    // Notification.show("Идентификатор дебитора не найден");
    // } else {
    // refreshGrid();
    // }
    // }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String debtorIdString = event.getRouteParameters().get("debtorId").orElse(null);
        if (debtorIdString != null && !debtorIdString.isEmpty()) {
            debtorId = Integer.valueOf(debtorIdString);
            refreshGrid();
        } else {
            Notification.show("Идентификатор дебитора не найден");
        }
    }

}
