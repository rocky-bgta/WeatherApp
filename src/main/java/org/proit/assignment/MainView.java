package org.proit.assignment;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

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
@Route
public class MainView extends VerticalLayout {

    /**
     * Construct a new Vaadin view.
     * <p>
     * Build the initial UI state for the user accessing the application.
     *
     * @param service
     *            The message service. Automatically injected Spring managed
     *            bean.
     */
    public MainView(@Autowired GreetService service) {

        // Create a Grid instance
        Grid<Person> grid = new Grid<>(Person.class);

        // Set items for the Grid
        grid.setItems(
                new Person("John", "Doe", "123 Main St"),
                new Person("Jane", "Smith", "456 Maple Ave"),
                new Person("Sam", "Johnson", "789 Oak Dr")
        );

        // Configure the columns
        grid.setColumns("firstname", "lastname", "address");

        // Add a column with a button
        Grid.Column<Person> buttonColumn = grid.addColumn(new ComponentRenderer<>(person -> {
            Button button = new Button("Action", e -> {
                // Handle button click
                Span message = new Span("Button clicked for: " + person.getFirstname() + " " + person.getLastname());
                add(message);
            });
            return button;
        })).setHeader("Actions");

        // Add the Grid to the layout
        add(grid);
    }

    // Person class to represent each row
    public static class Person {
        private String firstname;
        private String lastname;
        private String address;

        public Person(String firstname, String lastname, String address) {
            this.firstname = firstname;
            this.lastname = lastname;
            this.address = address;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

}
