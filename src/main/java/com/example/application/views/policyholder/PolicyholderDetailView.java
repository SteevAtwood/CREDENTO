package com.example.application.views.policyholder;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.application.data.Policyholder;
import com.example.application.services.ConfirmationDialog;
import com.example.application.services.PolicyholderService;
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
@Route(value = "policyholder/:id", layout = MainLayout.class)
@Uses(Icon.class)
public class PolicyholderDetailView extends VerticalLayout implements BeforeEnterObserver {

    @Autowired
    private final PolicyholderService policyholderService;
    private Policyholder policyholder;
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
    private final BeanValidationBinder<Policyholder> binder;

    public PolicyholderDetailView(PolicyholderService policyholderService) {
        this.policyholderService = policyholderService;

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

        binder = new BeanValidationBinder<>(Policyholder.class);
        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> clearForm());
        save.addClickListener(e -> {
            try {
                if (this.policyholder == null) {
                    this.policyholder = new Policyholder();
                }
                binder.writeBean(this.policyholder);
                policyholderService.update(this.policyholder);
                Notification.show("Данные успешно обновлены");
            } catch (ValidationException validationException) {
                Notification.show("Не удалось обновить данные. Проверьте, что все значения допустимы");
            }
        });

        delete.addClickListener(e -> {
            if (this.policyholder != null) {
                confirmDialog = new ConfirmationDialog(
                        "Вы действительно хотите удалить должника " + this.policyholder.getCompanyName() + "?",
                        this::deletePolicyholder);
                confirmDialog.open();
            }
        });

        HorizontalLayout buttonLayout = new HorizontalLayout(save, cancel, delete);
        add(buttonLayout);
    }

    private Button acceptedCreditLimits() {
        Button button = new Button("Одобренные кредитные лимиты", event -> {
            if (policyholder != null && policyholder.getCompanyRegistrationCodes() != null) {
                String registrationCode = policyholder.getCompanyRegistrationCodes();
                RouteParameters routeParameters = new RouteParameters("registrationCode", registrationCode);
                UI.getCurrent().navigate(RequestsSuccessView.class, routeParameters);
            } else {
                Notification.show("Регистрационный код компании не найден");
            }
        });
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return button;
    }

    private Button pendingCreditLimits() {
        Button button = new Button("Просмотреть заявки", event -> {
            if (policyholder != null && policyholder.getId() != null) {
                String policyholderId = String.valueOf(policyholder.getId());
                RouteParameters routeParameters = new RouteParameters("policyholderId", policyholderId);
                UI.getCurrent().navigate(RequestsPendingView.class, routeParameters);
            } else {
                Notification.show("Идентификатор страхователя не найден");
            }
        });
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return button;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        System.out.println("beforeEnter called");
        Optional<String> policyholderId = event.getRouteParameters().get("id");
        if (policyholderId.isPresent()) {
            Integer id = Integer.valueOf(policyholderId.get());
            policyholder = policyholderService.findPolicyholderById(id);
            if (policyholder != null) {
                populateForm(policyholder);
            } else {
                event.forwardTo(PolicyholderView.class);
            }
        } else {
            event.forwardTo(PolicyholderView.class);
        }
    }

    private void populateForm(Policyholder policyholder) {
        this.policyholder = policyholder;

        // status.setItems(StatusEnum.values());
        // status.setItemLabelGenerator(StatusEnum::getDisplayName);
        binder.readBean(this.policyholder);
    }

    private void clearForm() {
        populateForm(null);
    }

    private void deletePolicyholder() {
        if (this.policyholder != null) {
            policyholderService.deletePolicyholderById(this.policyholder.getId());
            clearForm();
            Notification.show("Должник удален");
            UI.getCurrent().navigate(PolicyholderView.class);
        }
    }
}
