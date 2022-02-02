package com.example.application.views;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import org.springframework.boot.autoconfigure.condition.ConditionMessage.ItemsBuilder;

import net.bytebuddy.asm.Advice.OffsetMapping.Target.ForArray;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Route(value = "")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class ScrollerBasic extends VerticalLayout {

  public static final String PERSONAL_TITLE_ID = "personal-title";
  public static final String EMPLOYMENT_TITLE_ID = "employment-title";
  private static final String PAYMENT_TITLE_ID = "payment-title";
  public static double dest = 0.0;

  public ScrollerBasic() {
    setAlignItems(Alignment.CENTER);
    setHeight("600px");
    setMaxWidth("100%");
    setPadding(false);
    setSpacing(false);
    setWidth("360px");
    getStyle().set("border", "1px solid var(--lumo-contrast-20pct)");

    Ticket hamburg = new Ticket("Hamburg",10.0);
    Ticket berlin = new Ticket("Berlin",20.0);
    Ticket bonn = new Ticket("Bonn",30.0);
    final List<Ticket> tickets = new ArrayList<>();
    tickets.add(hamburg);
    tickets.add(berlin);
    tickets.add(bonn);

    // Header
    Header header = new Header();
    header.getStyle()
            .set("align-items", "center")
            .set("border-bottom", "1px solid var(--lumo-contrast-20pct)")
            .set("display", "flex")
            .set("padding", "var(--lumo-space-m)");

    H2 ticketAutomat = new H2("Ticketautomat");
    ticketAutomat.getStyle().set("margin", "0");

    header.add(ticketAutomat);
    add(header);

    // tag::snippet[]
    // Personal information
    H3 personalTitle = new H3("Personal information");
    personalTitle.setId(PERSONAL_TITLE_ID);

    TextField firstName = new TextField("First name");
    firstName.setWidthFull();
    firstName.isRequired();

    TextField lastName = new TextField("Last name");
    lastName.setWidthFull();
    lastName.isRequired();

    Section personalInformation = new Section(personalTitle, firstName, lastName);
    personalInformation.getElement().setAttribute("aria-labelledby", PERSONAL_TITLE_ID);
    // Employment information
    H3 ticketTitle = new H3("Trip information");
    ticketTitle.setId(EMPLOYMENT_TITLE_ID);

    DatePicker destinationDate = new DatePicker("Date");
    destinationDate.setWidthFull();
    destinationDate.isRequired();

    TimePicker timePicker = new TimePicker();
    timePicker.setLabel("Time");
    timePicker.setValue(LocalTime.of(7, 0));
    timePicker.isRequired();

    ComboBox<Ticket> destination = new ComboBox<>("Destination");
    destination.setAllowCustomValue(false);
    destination.setItemLabelGenerator(Ticket::getFullName);
    destination.setItems(tickets);
    destination.setValue(tickets.get(0));
    destination.isRequired();
    destination.setHelperText("Select a destination");

    Section employmentInformation = new Section(ticketTitle, timePicker, destination,destinationDate);
    employmentInformation.getElement().setAttribute("aria-labelledby", EMPLOYMENT_TITLE_ID);
 
    // Payment
    H3 paymentTitle = new H3("Payment");
    personalTitle.setId(PAYMENT_TITLE_ID);

    NumberField price = new NumberField("Price");
    price.setReadOnly(true);
    price.setLabel("Price");
    price.setValue(destination.getValue().preis);
    Div priceSuffix = new Div();
    priceSuffix.setText("€");
    price.setSuffixComponent(priceSuffix);
    destination.addValueChangeListener(event -> {
      price.setValue(destination.getValue().preis);
    });

    NumberField euroField = new NumberField();
    euroField.isRequiredIndicatorVisible();
    euroField.setLabel("Balance");
    euroField.setValue(null);
    Div euroSuffix = new Div();
    euroSuffix.setText("€");
    euroField.setSuffixComponent(euroSuffix);
    euroField.setWidthFull();

    Section payment = new Section(paymentTitle, price, euroField);
    personalInformation.getElement().setAttribute("aria-labelledby", PAYMENT_TITLE_ID);


    Scroller scroller = new Scroller(new Div(personalInformation, employmentInformation,payment));
    scroller.setScrollDirection(Scroller.ScrollDirection.VERTICAL);
    scroller.getStyle()
            .set("border-bottom", "1px solid var(--lumo-contrast-20pct)")
            .set("padding", "var(--lumo-space-m)");
    add(scroller);
    // end::snippet[]

    // Footer
    Button buy = new Button("Buy");
    buy.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    buy.getStyle().set("margin-right", "var(--lumo-space-s)");
    
    // get the entered price on press of buy
    PriceHandler priceHandler = new PriceHandler();
    buy.addClickListener(ClickEvent -> {
        if (priceHandler.compare(price.getValue(), euroField.getValue()) == true){
          System.out.println("payed");
        }
        else {
          System.out.println("not enough balance");
        }
      });

    Button reset = new Button("Reset");
    reset.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
    reset.addClickListener(clickListener -> {
        firstName.clear();
        lastName.clear();
        timePicker.clear();
        destinationDate.clear();
        destination.clear();
        price.clear();
        euroField.clear();
    });

    Footer footer = new Footer(buy, reset);
    footer.getStyle().set("padding", "var(--lumo-space-wide-m)");
    add(footer);
  }
}