package com.example.application.views.debtors;

import com.example.application.data.Debtors;
import com.example.application.data.statusEnum.StatusEnum;
import com.example.application.services.DebtorService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
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

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

@AnonymousAllowed
@Route(value = "debtors", layout = MainLayout.class)
@RouteAlias(value = "debtors", layout = MainLayout.class)
@Uses(Icon.class)
public class DebtorsView extends Div {

    private final Grid<Debtors> grid;
    private final DebtorService debtorService;
    private final Filters filters;

    public DebtorsView(DebtorService debtorService) {
        this.debtorService = debtorService;
        this.grid = createGrid();
        this.filters = new Filters(this::refreshGrid);

        Button openRequestViewButton = createOpenRequestViewButton();
        VerticalLayout layout = new VerticalLayout(filters, grid, openRequestViewButton);
        layout.setSizeFull();
        add(layout);
    }

    private Button createOpenRequestViewButton() {
        Button button = new Button("Открыть заявки", event -> {
            getUI().ifPresent(ui -> ui.navigate("requests"));
        });
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return button;
    }

    public static class Filters extends Div implements Specification<Debtors> {

        private final TextField companyName = new TextField("Название компании");
        private final TextField address = new TextField("Адрес");
        private final TextField informationProviderCode = new TextField("Код информационного провайдера");
        private final TextField companyRegistrationCodes = new TextField("Регистрационные коды компании");
        private final TextField okvedCode = new TextField("Код ОКВЭД");
        private final ComboBox<StatusEnum> status = new ComboBox<>("Статус компании");

        public Filters(Runnable onSearch) {
            setWidthFull();
            addClassName("filter-layout");
            addClassNames(LumoUtility.Padding.Horizontal.LARGE, LumoUtility.Padding.Vertical.MEDIUM,
                    LumoUtility.BoxSizing.BORDER);

            status.setItems(StatusEnum.values());

            HorizontalLayout filterLayout = new HorizontalLayout();
            filterLayout.setSpacing(true);
            filterLayout.setAlignItems(FlexComponent.Alignment.CENTER);

            filterLayout.add(companyName, address, informationProviderCode, companyRegistrationCodes, okvedCode,
                    status);

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
            companyName.clear();
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

            if (!companyName.isEmpty()) {
                String lowerCaseFilter = companyName.getValue().toLowerCase();
                Predicate nameMatch = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("companyName")),
                        lowerCaseFilter + "%");
                predicates.add(nameMatch);
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
        grid.addColumn("companyName").setAutoWidth(true).setHeader("Название компании");
        grid.addColumn("address").setAutoWidth(true).setHeader("Адрес");
        grid.addColumn("informationProviderCode").setAutoWidth(true).setHeader("Код информационного провайдера");
        grid.addColumn("companyRegistrationCodes").setAutoWidth(true).setHeader("Регистрационные коды компании");
        grid.addColumn("okvedCode").setAutoWidth(true).setHeader("Код ОКВЭД");
        grid.addColumn("companyStatus").setAutoWidth(true).setHeader("Статус компании");

        grid.setItems(query -> debtorService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)),
                filters).stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_10);

        grid.addItemClickListener(event -> {
            Debtors selectedDebtor = event.getItem();
            String debtorId = String.valueOf(selectedDebtor.getId());
            getUI().ifPresent(ui -> ui.navigate("debtors/" + debtorId));
            System.out.println("debtors  ВЫВОД ЧТОБЫ ВИДНО ЧТО НЕ NULL" + debtorId);
        });

        return grid;
    }

    private void refreshGrid() {
        grid.getDataProvider().refreshAll();
    }
}
