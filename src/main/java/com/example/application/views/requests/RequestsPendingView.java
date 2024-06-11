package com.example.application.views.requests;

import com.example.application.data.Debtors;
import com.example.application.data.Request;
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

import java.math.BigDecimal;
import java.util.*;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

@AnonymousAllowed
@Route(value = "pending-requests/:registrationCode", layout = MainLayout.class)
@Uses(Icon.class)
public class RequestsPendingView extends Div implements BeforeEnterObserver {

    private Grid<Request> grid;
    private Filters filters;
    private final RequestService requestService;
    private String registrationCode;

    public RequestsPendingView(RequestService requestService) {
        this.requestService = requestService;
        setSizeFull();
        addClassNames("requests-view");

        filters = new Filters(() -> refreshGrid());
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

        private final TextField insuranceContractNumber = new TextField("Страховой номер");
        private final TextField debitorsCountry = new TextField("Страна дебитора");
        private final TextField registrationCode = new TextField("Регистрационный код");
        private final TextField clAmount = new TextField("Сумма CL");
        private final ComboBox<String> clCurrency = new ComboBox<>("Валюта CL");
        private final ComboBox<Debtors> debtor = new ComboBox<>("Дебитор");

        public Filters(Runnable onSearch) {
            setWidthFull();
            addClassName("filter-layout");
            addClassNames(LumoUtility.Padding.Horizontal.LARGE, LumoUtility.Padding.Vertical.MEDIUM,
                    LumoUtility.BoxSizing.BORDER);

            clCurrency.setItems("RUB", "USD", "EUR");

            HorizontalLayout filterLayout = new HorizontalLayout();
            filterLayout.setSpacing(true);
            filterLayout.setAlignItems(FlexComponent.Alignment.CENTER);

            filterLayout.add(insuranceContractNumber, debitorsCountry, registrationCode, clAmount, clCurrency);

            Button resetBtn = new Button("Очистить");
            resetBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            resetBtn.addClickListener(e -> {
                insuranceContractNumber.clear();
                debitorsCountry.clear();
                registrationCode.clear();
                clAmount.clear();
                clCurrency.clear();
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

            if (!insuranceContractNumber.isEmpty()) {
                String lowerCaseFilter = insuranceContractNumber.getValue().toLowerCase();
                Predicate insuranceContractNumberMatch = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("insuranceContractNumber")),
                        lowerCaseFilter + "%");
                predicates.add(insuranceContractNumberMatch);
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
                BigDecimal amount = new BigDecimal(clAmount.getValue());
                Predicate clAmountMatch = criteriaBuilder.equal(root.get("clAmount"), amount);
                predicates.add(clAmountMatch);
            }
            if (clCurrency.getValue() != null) {
                Predicate clCurrencyMatch = criteriaBuilder.equal(root.get("clCurrency"), clCurrency.getValue());
                predicates.add(clCurrencyMatch);
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }
    }

    private Component createGrid() {
        grid = new Grid<>(Request.class, false);
        grid.addColumn("insuranceContractNumber").setAutoWidth(true).setHeader("Страховой номер");
        grid.addColumn("debitorsCountry").setAutoWidth(true).setHeader("Страна дебитора");
        grid.addColumn("registrationCode").setAutoWidth(true).setHeader("Регистрационный код");
        grid.addColumn("clAmount").setAutoWidth(true).setHeader("Сумма CL");
        grid.addColumn("clCurrency").setAutoWidth(true).setHeader("Валюта CL");
        grid.addColumn("clTermsAndConditions").setAutoWidth(true).setHeader("Условия CL");
        grid.addColumn("adjustmentPossibility").setAutoWidth(true).setHeader("Возможность корректировки");

        grid.setItems(query -> requestService.getPendingRequestsByRegistrationCode(registrationCode,
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

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        registrationCode = event.getRouteParameters().get("registrationCode").orElse("");
        if (registrationCode.isEmpty()) {
            Notification.show("Регистрационный код не найден");
        } else {
            refreshGrid();
        }
    }
}
