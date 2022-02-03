package com.example.application.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("dialog-basic")
public class DialogBasic extends Div {

    public DialogBasic(Float cashback, String mail_adr) {
        Dialog dialog = new Dialog();
        dialog.getElement().setAttribute("aria-label", "Create new employee");

        VerticalLayout dialogLayout = createDialogLayout(dialog, cashback, mail_adr);
        dialog.add(dialogLayout);

        dialog.open();
        add(dialog);
    }

    private static VerticalLayout createDialogLayout(Dialog dialog, Float returnCash, String adr) {
        H2 headline = new H2("Confirm purchase");
        headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
                .set("font-size", "1.5em").set("font-weight", "bold");

        // return cash and user info
        H5 cashReturn = new H5("Cashback: " + returnCash.toString() + "€");
        H5 emailInfo = new H5("Your ticket will be send via email! (make sure to check spam)");
        
        VerticalLayout fieldLayout = new VerticalLayout(cashReturn);
        fieldLayout.setSpacing(false);
        fieldLayout.setPadding(false);
        fieldLayout.setAlignItems(FlexComponent.Alignment.STRETCH);

        VerticalLayout infoLayout = new VerticalLayout(emailInfo);

        // CALL EMAIL FUNCTION ON BUY
        Button cancelButton = new Button("Cancel", e -> dialog.close());
        Button saveButton = new Button("Buy", e -> {
                SendMail yo = new SendMail();
                yo.send(adr);
                dialog.close();
        });

        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton,
                saveButton);
        buttonLayout
                .setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        VerticalLayout dialogLayout = new VerticalLayout(headline, fieldLayout, infoLayout,
                buttonLayout);
        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "300px").set("max-width", "100%");

        return dialogLayout;
    }

}
