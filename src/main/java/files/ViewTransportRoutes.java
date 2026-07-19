package files;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

public class ViewTransportRoutes extends rootNode {
    private Administrator administrator;
    private ObservableList<Institute> institutes;
    private TableView<Route> table = new TableView<>();

    private HBox addRouteForm = new HBox(10);
    private Button addRoute = new Button("Add Route");
    private TextField driverField   = new TextField();
    private TextField busField      = new TextField();
    private TextField seatsField    = new TextField();
    private TextField stopsField    = new TextField();

    @SuppressWarnings("unchecked")
    public ViewTransportRoutes(ObservableList<Institute> institutes) {
        this.institutes = institutes;

        driverField.setPromptText("Driver Name");
        busField.setPromptText("Bus Number");
        seatsField.setPromptText("Seats");
        stopsField.setPromptText("Stops (e.g. A -> B -> C)");
        stopsField.setPrefWidth(200);

        // Driver column
        TableColumn<Route, String> driverCol = new TableColumn<>("Driver");
        driverCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getDriver()));
        driverCol.setPrefWidth(140);

        // Bus number
        TableColumn<Route, String> busCol = new TableColumn<>("Bus No.");
        busCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getBusNumber()));
        busCol.setPrefWidth(100);

        // Total seats
        TableColumn<Route, String> totalSeatsCol = new TableColumn<>("Total Seats");
        totalSeatsCol.setCellValueFactory(d ->
            new SimpleStringProperty(String.valueOf(d.getValue().getTotalSeats())));
        totalSeatsCol.setPrefWidth(100);

        // Available seats
        TableColumn<Route, String> availCol = new TableColumn<>("Available");
        availCol.setCellValueFactory(d ->
            new SimpleStringProperty(String.valueOf(d.getValue().getAvailableSeats())));
        availCol.setPrefWidth(100);

        // Route stops
        TableColumn<Route, String> stopsCol = new TableColumn<>("Route / Stops");
        stopsCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getStopsString()));
        stopsCol.setPrefWidth(300);

        table.getColumns().addAll(driverCol, busCol, totalSeatsCol, availCol, stopsCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPrefHeight(400);
        table.setPlaceholder(new Label("No transport routes added yet."));

        addContent(table);

        addRouteForm.getChildren().addAll(
            new Label("Driver:"), driverField,
            new Label("Bus:"), busField,
            new Label("Seats:"), seatsField,
            new Label("Route:"), stopsField,
            addRoute);
        addRouteForm.setAlignment(Pos.CENTER);
        addRouteForm.setPadding(new Insets(10, 0, 0, 0));
        addBottom(new Label("ADD A NEW TRANSPORT ROUTE"));
        addBottom(addRouteForm);

        addRoute.setOnAction(e -> {
            try {
                String driver = driverField.getText().trim();
                String bus    = busField.getText().trim();
                int seats     = Integer.parseInt(seatsField.getText().trim());
                String stops  = stopsField.getText().trim();
                if (driver.isEmpty() || bus.isEmpty() || stops.isEmpty()) return;
                administrator.getInstitute().getTransportDivision()
                    .addRoute(new Route(driver, bus, seats, stops));
                driverField.clear(); busField.clear(); seatsField.clear(); stopsField.clear();
                if (institutes != null) DatabaseManager.saveAll(institutes);
                table.refresh();
            } catch (NumberFormatException ignored) {}
        });
    }

    // Legacy no-arg constructor
    public ViewTransportRoutes() { this(null); }

    public void setUser(Administrator administrator) {
        this.administrator = administrator;
        table.setItems(administrator.getInstitute().getTransportDivision().getRoutes());
    }
}