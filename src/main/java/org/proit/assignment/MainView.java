package org.proit.assignment;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.data.value.ValueChangeMode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Route("")
public class MainView extends VerticalLayout {

    private Grid<Person> grid;
    private List<Person> allPersons;
    private List<Person> filteredPersons;
    private int currentPage = 0;
    private final int pageSize = 10;
    private TextField filterTextField;

    public MainView() {
        // Initialize the list of all persons
        allPersons = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            allPersons.add(new Person("Firstname" + i, "Lastname" + i, "Address" + i));
        }

        // Initialize filteredPersons with allPersons initially
        filteredPersons = new ArrayList<>(allPersons);

        // Create a Grid instance
        grid = new Grid<>(Person.class);
        grid.setColumns("firstname", "lastname", "address");

        // Add a column with a button
        grid.addColumn(new ComponentRenderer<>(person -> {
            Button button = new Button("Action", e -> {
                // Handle button click
                Div message = new Div();
                message.setText("Button clicked for: " + person.getFirstname() + " " + person.getLastname());
                add(message);
            });
            return button;
        })).setHeader("Actions");

        // Create a text field for filtering
        filterTextField = new TextField();
        filterTextField.setPlaceholder("Filter by Firstname");
        filterTextField.setValueChangeMode(ValueChangeMode.LAZY);
        filterTextField.addValueChangeListener(e -> filterGrid());

        // Add pagination buttons
        Button previousButton = new Button("Previous", e -> showPreviousPage());
        Button nextButton = new Button("Next", e -> showNextPage());
        HorizontalLayout paginationButtons = new HorizontalLayout(previousButton, nextButton);

        // Add the filter text field, Grid, and pagination buttons to the layout
        add(filterTextField, grid, paginationButtons);

        // Display the first page
        updateGrid();
    }

    private void showPreviousPage() {
        if (currentPage > 0) {
            currentPage--;
            updateGrid();
        }
    }

    private void showNextPage() {
        if ((currentPage + 1) * pageSize < filteredPersons.size()) {
            currentPage++;
            updateGrid();
        }
    }

    private void updateGrid() {
        int start = currentPage * pageSize;
        int end = Math.min(start + pageSize, filteredPersons.size());
        grid.setItems(filteredPersons.subList(start, end));
    }

    private void filterGrid() {
        String filter = filterTextField.getValue().trim().toLowerCase();
        filteredPersons = allPersons.stream()
                .filter(person -> person.getFirstname().toLowerCase().contains(filter))
                .collect(Collectors.toList());
        currentPage = 0; // Reset to the first page
        updateGrid();
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
