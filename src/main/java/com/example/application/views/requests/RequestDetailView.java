package com.example.application.views.requests;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.application.data.Request;
import com.example.application.data.requestStatusEnum.RequestStatusEnum;
import com.example.application.services.ConfirmationDialog;
import com.example.application.services.RequestService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
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
@Route(value = "requests/:id", layout = MainLayout.class)
@Uses(Icon.class)
public class RequestDetailView extends VerticalLayout implements BeforeEnterObserver {

    @Autowired
    private final RequestService requestService;
    private Request request;
    private Dialog confirmDialog;

    private final TextField insuranceContractNumber = new TextField("Страховой номер");
    private final TextField debitorsCountry = new TextField("Страна дебитора");
    private final TextField registrationCode = new TextField("Регистрационный код");
    private final TextField clAmount = new TextField("Сумма CL");
    private final ComboBox<String> clCurrency = new ComboBox<>("Валюта CL");
    private final TextField clTermsAndConditions = new TextField("Условия CL");
    private final TextField adjustmentPossibility = new TextField("Возможность корректировки");
    private final ComboBox<RequestStatusEnum> status = new ComboBox<>("Стehfwbhjdbrfhjvbdhjbатус");
    private final Button cancel = new Button("Отмена");
    private final Button save = new Button("Сохранить");
    private final Button delete = new Button("Удалить");
    private final BeanValidationBinder<Request> binder;

    public RequestDetailView(RequestService requestService) {
        this.requestService = requestService;

        FormLayout formLayout = new FormLayout();
        formLayout.setWidth("100%");
        formLayout.add(insuranceContractNumber, debitorsCountry, registrationCode, clAmount, clCurrency,
                clTermsAndConditions, adjustmentPossibility, status);

        add(formLayout);
        setWidth("100%");

        binder = new BeanValidationBinder<>(Request.class);
        binder.bindInstanceFields(this);

        clCurrency.setItems("RUB", "USD", "EUR");

        cancel.addClickListener(e -> clearForm());
        save.addClickListener(e -> {
            try {
                if (this.request == null) {
                    this.request = new Request();
                }
                binder.writeBean(this.request);
                requestService.update(this.request);
                Notification.show("Данные успешно обновлены");
            } catch (ValidationException validationException) {
                Notification.show("Не удалось обновить данные. Проверьте, что все значения допустимы");
            }
        });

        delete.addClickListener(e -> {
            if (this.request != null) {
                confirmDialog = new ConfirmationDialog(
                        "Вы действительно хотите удалить запрос " + this.request.getInsuranceContractNumber() + "?",
                        this::deleteRequest);
                confirmDialog.open();
            }
        });

        HorizontalLayout buttonLayout = new HorizontalLayout(save, cancel, delete);
        add(buttonLayout);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<String> requestId = event.getRouteParameters().get("id");
        if (requestId.isPresent()) {
            Integer id = Integer.valueOf(requestId.get());
            request = requestService.findRequestById(id);
            if (request != null) {
                populateForm(request);
            } else {
                event.forwardTo(RequestsView.class);
            }
        } else {
            event.forwardTo(RequestsView.class);
        }
    }

    private void populateForm(Request request) {
        this.request = request;

        status.setItems(RequestStatusEnum.values());
        status.setItemLabelGenerator(RequestStatusEnum::getDisplayName);
        binder.readBean(this.request);
    }

    private void clearForm() {
        populateForm(null);
    }

    private void deleteRequest() {
        if (this.request != null) {
            requestService.deleteRequestById(this.request.getId());
            clearForm();
            Notification.show("Запрос удален");
            UI.getCurrent().navigate(RequestsView.class);
        }
    }
}
