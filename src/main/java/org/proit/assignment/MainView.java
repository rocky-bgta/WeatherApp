package org.proit.assignment;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.proit.assignment.model.LocationDetailsModel;
import org.proit.assignment.model.WeatherApiResponseModel;
import org.proit.assignment.service.LocationService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Route("")
public class MainView extends VerticalLayout {

    private Grid<LocationDetailsModel> grid;
    private List<LocationDetailsModel> allLocationDetails;
    private List<LocationDetailsModel> filteredLocationDetails;
    private int currentPage = 0;
    private final int pageSize = 5;
    private TextField filterTextField;
    
    private final LocationService locationService;

    public MainView(LocationService locationService) {
        // Initialize the list of all persons
        allLocationDetails = new ArrayList<>();
//        for (int i = 1; i <= 50; i++) {
//            allPersons.add(new Person("Firstname" + i, "Lastname" + i, "Address" + i));
//        }

        // Initialize filteredPersons with allPersons initially
        //filteredPersons = new ArrayList<>(allPersons);


       WeatherApiResponseModel weatherApiResponseModel = locationService.getLocationsByCityName("Dhaka");
       allLocationDetails = new ArrayList<>(weatherApiResponseModel.getResults());
       filteredLocationDetails = new ArrayList<>(weatherApiResponseModel.getResults());



        // Create a Grid instance
        grid = new Grid<>(LocationDetailsModel.class);
        grid.setColumns("name");

        // Add a column with a button
        grid.addColumn(new ComponentRenderer<>(filteredLocationDetails -> {
            Button button = new Button("Action", e -> {
                // Handle button click
                Div message = new Div();
                message.setText("Button clicked for: " + filteredLocationDetails.getName());
                add(message);
            });
            return button;
        })).setHeader("Actions");

        // Create a text field for filtering
        filterTextField = new TextField();
        filterTextField.setPlaceholder("Filter by Location Name");
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
        this.locationService = locationService;
    }

    private void showPreviousPage() {
        if (currentPage > 0) {
            currentPage--;
            updateGrid();
        }
    }

    private void showNextPage() {
        if ((currentPage + 1) * pageSize < filteredLocationDetails.size()) {
            currentPage++;
            updateGrid();
        }
    }

    private void updateGrid() {
        int start = currentPage * pageSize;
        int end = Math.min(start + pageSize, filteredLocationDetails.size());
        grid.setItems(filteredLocationDetails.subList(start, end));
    }

    private void filterGrid() {
        String filter = filterTextField.getValue().trim().toLowerCase();
        filteredLocationDetails = allLocationDetails.stream()
                .filter(location -> location.getName().toLowerCase().contains(filter))
                .collect(Collectors.toList());
        currentPage = 0; // Reset to the first page
        updateGrid();
    }
}
