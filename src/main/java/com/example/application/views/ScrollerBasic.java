package com.example.application.views;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Route(value = "")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class ScrollerBasic extends VerticalLayout {

  //Title ID's
  public static final String PERSONAL_TITLE_ID = "personal-title";
  public static final String TRIP_INFO_TITLE_ID = "trip-info-title";
  private static final String PAYMENT_TITLE_ID = "payment-title";

  public String firstNameVar;
  public String lastNameVar ;
  public String emailFieldVar;
  public String destinationDateVar ;
  public String timePickerVar;
  public String destinationVar;

  public ScrollerBasic() {
    setAlignItems(Alignment.CENTER);
    /* Removed to center
    setHeight("600px");
    setPadding(false);
    setSpacing(false);
    setWidth("360px");
    */
    setMaxWidth("100%");
    getStyle().set("border", "1px solid var(--lumo-contrast-20pct)");

    //The Tickets made off destination & price
    final List<Ticket> tickets = new ArrayList<>();
    tickets.add(new Ticket("Hamburg",10.0));
    tickets.add(new Ticket("Berlin",20.0));
    tickets.add(new Ticket("Bonn",30.0));

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
      //First Name for the ticket
    TextField firstName = new TextField("First name");
    firstName.setWidthFull();
    firstName.setRequired(true); 
    firstName.setErrorMessage("This field is required");
    firstName.addValueChangeListener(event -> {
      firstNameVar = firstName.getValue().toString();
    });
      //Last Name for the ticket
    TextField lastName = new TextField("Last name");
    lastName.setWidthFull();
    lastName.setRequired(true); 
    lastName.setErrorMessage("This field is required");
    lastName.addValueChangeListener(event -> {
      lastNameVar = lastName.getValue().toString();
    });
    EmailField emailField = new EmailField();
    emailField.setLabel("Email address");
    emailField.setRequiredIndicatorVisible(true);
    emailField.getElement().setAttribute("name", "email");
    emailField.setPlaceholder("username@example.com");
    emailField.setErrorMessage("Please enter a valid example.com email address");
    emailField.setClearButtonVisible(true);
    emailField.addValueChangeListener(event -> {
      emailFieldVar = emailField.getValue().toString();
    });
      //Build the section composing of ^
    Section personalInformation = new Section(personalTitle, firstName, lastName, emailField);
    personalInformation.getElement().setAttribute("aria-labelledby", PERSONAL_TITLE_ID);
    // Employment information
    H3 ticketTitle = new H3("Trip information");
    ticketTitle.setId(TRIP_INFO_TITLE_ID);
      //Pick date of the trip
    DatePicker destinationDate = new DatePicker("Date");
    destinationDate.setWidthFull();
    destinationDate.setRequired(true); 
    destinationDate.setErrorMessage("This field is required");
    destinationDate.addValueChangeListener(event -> {
      destinationDateVar = destinationDate.getValue().toString();
    });
      //Pick time of the trip
    TimePicker timePicker = new TimePicker();
    timePicker.setLabel("Time");
    timePicker.setValue(LocalTime.of(7, 0));
    timePicker.setRequired(true); 
    timePicker.setErrorMessage("This field is required");
    timePicker.addValueChangeListener(event -> {
      timePickerVar = timePicker.getValue().toString();
    });
      //select the destination - sets price
    ComboBox<Ticket> destination = new ComboBox<>("Destination");
    destination.setWidthFull();
    destination.setAllowCustomValue(false);
    destination.setItemLabelGenerator(Ticket::getFullName);
    destination.setItems(tickets);
    destination.setValue(tickets.get(0));
    destination.setRequired(true); 
    destination.setErrorMessage("This field is required");
    destination.setHelperText("Select a destination");

    Section employmentInformation = new Section(ticketTitle, timePicker, destination,destinationDate);
    employmentInformation.getElement().setAttribute("aria-labelledby", TRIP_INFO_TITLE_ID);
 
    // Payment
    H3 paymentTitle = new H3("Payment");
    personalTitle.setId(PAYMENT_TITLE_ID);
      //show the price of the ticket
    NumberField price = new NumberField("Price");
    price.setReadOnly(true);
    price.setLabel("Price");
    price.setValue(destination.getValue().preis);
    Div priceSuffix = new Div();
    priceSuffix.setText("€");
    price.setSuffixComponent(priceSuffix);
    destination.addValueChangeListener(event -> {
      price.setValue(destination.getValue().preis);
      destinationVar = destination.getValue()._name;
    });

    //the balance of the buyer
    NumberField euroField = new NumberField();
    euroField.setLabel("Balance");
    euroField.setValue(null);
    Div euroSuffix = new Div();
    euroSuffix.setText("€");
    euroField.setSuffixComponent(euroSuffix);
    euroField.setWidthFull();

    Section payment = new Section(paymentTitle, price, euroField);
    personalInformation.getElement().setAttribute("aria-labelledby", PAYMENT_TITLE_ID);

    //builds the whole component with the sections
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
    buy.addClickListener(ClickEvent -> {
      if(!firstName.isEmpty() && 
         !lastName.isEmpty() && 
         !emailField.isEmpty() &&
         !timePicker.isEmpty() &&
         !destinationDate.isEmpty()){
        System.out.println(firstNameVar + " " + lastNameVar);
        System.out.println("At: " + timePickerVar);
        System.out.println("On the: " + destinationDateVar);
        System.out.println("To: " + destinationVar);
      }
      else{
        Notification notification = new Notification();
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

        Div text = new Div(new Text("Fill in all fields!"));

        Button closeButton = new Button(new Icon("lumo", "cross"));
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        closeButton.getElement().setAttribute("aria-label", "Close");
        closeButton.addClickListener(event -> {
          notification.close();
        });

        HorizontalLayout layout = new HorizontalLayout(text, closeButton);
        layout.setAlignItems(Alignment.CENTER);

        notification.add(layout);
        notification.open();
      }
    });

    Button reset = new Button("Reset");
    reset.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
    reset.addClickListener(clickListener -> {
        firstName.clear();
        lastName.clear();
        timePicker.clear();
        destinationDate.clear();
        emailField.clear();
        euroField.clear();
    });

    Footer footer = new Footer(buy, reset);
    footer.getStyle().set("padding", "var(--lumo-space-wide-m)");
    add(footer);
  }
}