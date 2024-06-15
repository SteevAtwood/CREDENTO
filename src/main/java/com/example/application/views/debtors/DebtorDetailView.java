package com.example.application.views.debtors;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.application.data.Debtors;
import com.example.application.services.ConfirmationDialog;
import com.example.application.services.DebtorService;
import com.example.application.views.MainLayout;
import com.example.application.views.requests.RequestsPendingView;
import com.example.application.views.requests.RequestsSuccessView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.component.dialog.Dialog;

@AnonymousAllowed
@Route(value = "debtors/:id", layout = MainLayout.class)
@Uses(Icon.class)
public class DebtorDetailView extends VerticalLayout implements BeforeEnterObserver {

    @Autowired
    private final DebtorService debtorService;
    private Debtors debtor;
    private Dialog confirmDialog;

    private final TextField companyName = new TextField("Название компании");
    private final TextField address = new TextField("Адрес");
    private final TextField informationProviderCode = new TextField("Код информационного провайдера");
    private final TextField companyRegistrationCodes = new TextField("Регистрационные коды компании");
    private final TextField okvedCode = new TextField("Код ОКВЭД");
    // private final ComboBox<StatusEnum> status = new ComboBox<>("Статус
    // компании");

    private final Button cancel = new Button("Отмена");
    private final Button save = new Button("Сохранить");
    private final Button delete = new Button("Удалить");
    private final BeanValidationBinder<Debtors> binder;

    public DebtorDetailView(DebtorService debtorService) {
        this.debtorService = debtorService;

        Button openSuccessRequestViewButton = acceptedCreditLimits();
        Button openPendingRequestViewButton = pendingCreditLimits();

        HorizontalLayout buttonLayoutForRequestButtons = new HorizontalLayout(openSuccessRequestViewButton,
                openPendingRequestViewButton);
        add(buttonLayoutForRequestButtons);

        FormLayout formLayout = new FormLayout();
        formLayout.setWidth("100%");
        formLayout.add(companyName, address, informationProviderCode, companyRegistrationCodes, okvedCode);

        add(formLayout);
        setWidth("100%");

        binder = new BeanValidationBinder<>(Debtors.class);
        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> clearForm());
        save.addClickListener(e -> {
            try {
                if (this.debtor == null) {
                    this.debtor = new Debtors();
                }
                binder.writeBean(this.debtor);
                debtorService.update(this.debtor);
                Notification.show("Данные успешно обновлены");
            } catch (ValidationException validationException) {
                Notification.show("Не удалось обновить данные. Проверьте, что все значения допустимы");
            }
        });

        delete.addClickListener(e -> {
            if (this.debtor != null) {
                confirmDialog = new ConfirmationDialog(
                        "Вы действительно хотите удалить должника " + this.debtor.getCompanyName() + "?",
                        this::deleteDebtor);
                confirmDialog.open();
            }
        });

        HorizontalLayout buttonLayout = new HorizontalLayout(save, cancel, delete);
        add(buttonLayout);
    }

    private Button acceptedCreditLimits() {
        Button button = new Button("Рассмотренные кредитные лимиты", event -> {
            if (debtor != null && debtor.getId() != null) {
                String debtorId = String.valueOf(debtor.getId());
                RouteParameters routeParameters = new RouteParameters("debtorId", debtorId);
                UI.getCurrent().navigate(RequestsSuccessView.class, routeParameters);
            } else {
                Notification.show("Идентификатор дебитора не найден");
            }
        });
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return button;
    }

    private Button pendingCreditLimits() {
        Button button = new Button("Просмотреть заявки", event -> {
            if (debtor != null && debtor.getId() != null) {
                String debtorId = String.valueOf(debtor.getId());
                RouteParameters routeParameters = new RouteParameters("debtorId", debtorId);
                UI.getCurrent().navigate(RequestsPendingView.class, routeParameters);
            } else {
                Notification.show("Идентификатор дебитора не найден");
            }
        });
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return button;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        System.out.println("beforeEnter called");
        Optional<String> debtorId = event.getRouteParameters().get("id");
        if (debtorId.isPresent()) {
            Integer id = Integer.valueOf(debtorId.get());
            debtor = debtorService.findDebtorById(id);
            if (debtor != null) {
                populateForm(debtor);
            } else {
                event.forwardTo(DebtorsView.class);
            }
        } else {
            event.forwardTo(DebtorsView.class);
        }
    }

    private void populateForm(Debtors debtor) {
        this.debtor = debtor;

        // status.setItems(StatusEnum.values());
        // status.setItemLabelGenerator(StatusEnum::getDisplayName);
        binder.readBean(this.debtor);
    }

    private void clearForm() {
        populateForm(null);
    }

    private void deleteDebtor() {
        if (this.debtor != null) {
            debtorService.deleteDebtorById(this.debtor.getId());
            clearForm();
            Notification.show("Должник удален");
            UI.getCurrent().navigate(DebtorsView.class);
        }
    }
}
