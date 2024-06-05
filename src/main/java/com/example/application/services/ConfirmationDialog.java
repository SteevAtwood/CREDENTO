package com.example.application.services;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.button.ButtonVariant;

public class ConfirmationDialog extends Dialog {

    public ConfirmationDialog(String message, Runnable onConfirm) {
        VerticalLayout layout = new VerticalLayout();
        layout.add(new Text(message));

        Button confirmButton = new Button("Подтвердить", event -> {
            onConfirm.run();
            close();
        });
        confirmButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button cancelButton = new Button("Отмена", event -> close());

        HorizontalLayout buttonLayout = new HorizontalLayout(confirmButton, cancelButton);
        layout.add(buttonLayout);

        add(layout);
    }
}
