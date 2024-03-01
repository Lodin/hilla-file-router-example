package org.vaadin.example;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("another-flow")
public class AnotherFlowView extends VerticalLayout {
    public AnotherFlowView() {
        add("Content of the AnotherFlowView");
    }

}
