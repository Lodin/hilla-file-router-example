package org.vaadin.example;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H3;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and use @Route
 * annotation to announce it in a URL as a Spring managed bean.
 * <p>
 * A new instance of this class is created for every new user and every browser
 * tab/window.
 * <p>
 * The main view contains a text field for getting the user name and a button
 * that shows a greeting message in a notification.
 */
@Route("flow")
public class FlowView extends VerticalLayout {

    private final Random random = new Random(System.currentTimeMillis());

    private int selectedPersonId;

    /**
     * Construct a new Vaadin view.
     * <p>
     * Build the initial UI state for the user accessing the application.
     *
     * @param service
     *            The message service. Automatically injected Spring managed
     *            bean.
     */
    public FlowView(GreetService service) {

        // Use TextField for standard text input
        TextField textField = new TextField("Your name");

        // Button click listeners can be defined as lambda expressions
        Button button = new Button("Say hello", e -> {
            add(new Paragraph(service.greet(textField.getValue())));
        });

        add(new H3("Flow View"), textField, button);

        AtomicReference<Button> seePersonInfoRef = new AtomicReference<>();

        Button seePersonInfo = new Button();
        seePersonInfo.addClickListener(e -> {
            int tempId = selectedPersonId;
            selectedPersonId = 0;
            seePersonInfoRef.get().setVisible(false);
            UI.getCurrent().navigate("person/" + tempId);
        });
        seePersonInfo.setVisible(false);

        seePersonInfoRef.set(seePersonInfo);

        Button generatePersonId = new Button("Generate Person ID", e -> {
            selectedPersonId = random.ints(1000, 1, 1000).findFirst().getAsInt();
            seePersonInfo.setText("See Person Info with Id = " + selectedPersonId);
            seePersonInfo.setVisible(true);
        });

        add(generatePersonId, seePersonInfo);

        Button navigateToAnotherFlowView = new Button("Navigate to about (Hilla) View", e -> {
            UI.getCurrent().navigate("a/bout");
        });
        Anchor anchor = new Anchor("a/bout", "Navigate to about (Hilla) View");
        add(navigateToAnotherFlowView, anchor);
    }

}
