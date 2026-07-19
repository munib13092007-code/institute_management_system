package files;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

public class ViewHostels extends rootNode {
    private Administrator administrator;
    private ObservableList<Institute> institutes;
    private TableView<Hostel> table = new TableView<>();

    private HBox addHostelForm = new HBox(10);
    private Button addHostel = new Button("Add Hostel");
    private TextField nameField           = new TextField();
    private TextField numberOfRoomsField  = new TextField();
    private TextField occupancyField      = new TextField();

    @SuppressWarnings("unchecked")
    public ViewHostels(ObservableList<Institute> institutes) {
        this.institutes = institutes;

        nameField.setPromptText("Hostel Name");
        numberOfRoomsField.setPromptText("No. of Rooms");
        occupancyField.setPromptText("Beds per Room");

        // Name column
        TableColumn<Hostel, String> nameCol = new TableColumn<>("Hostel Name");
        nameCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getName()));
        nameCol.setPrefWidth(200);

        // Rooms
        TableColumn<Hostel, String> roomsCol = new TableColumn<>("Total Rooms");
        roomsCol.setCellValueFactory(d ->
            new SimpleStringProperty(String.valueOf(d.getValue().getNumberOfRooms())));
        roomsCol.setPrefWidth(120);

        // Beds per room
        TableColumn<Hostel, String> bedsCol = new TableColumn<>("Beds/Room");
        bedsCol.setCellValueFactory(d ->
            new SimpleStringProperty(String.valueOf(d.getValue().getOccupancyPerRoom())));
        bedsCol.setPrefWidth(110);

        // Accommodated students
        TableColumn<Hostel, String> accCol = new TableColumn<>("Accommodated");
        accCol.setCellValueFactory(d ->
            new SimpleStringProperty(String.valueOf(d.getValue().getTotalAccomodations())));
        accCol.setPrefWidth(130);

        // Vacancy
        TableColumn<Hostel, String> vacancyCol = new TableColumn<>("Vacancy");
        vacancyCol.setCellValueFactory(d ->
            new SimpleStringProperty(String.valueOf(d.getValue().getAvailableAccomodations())));
        vacancyCol.setPrefWidth(100);

        // Fee
        TableColumn<Hostel, String> feeCol = new TableColumn<>("Fee/Month (PKR)");
        feeCol.setCellValueFactory(d ->
            new SimpleStringProperty(String.format("%.0f", d.getValue().getFee())));
        feeCol.setPrefWidth(140);

        table.getColumns().addAll(nameCol, roomsCol, bedsCol, accCol, vacancyCol, feeCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPrefHeight(400);
        table.setPlaceholder(new Label("No hostels added yet."));

        addContent(table);

        addHostelForm.getChildren().addAll(
            new Label("Name:"), nameField,
            new Label("Rooms:"), numberOfRoomsField,
            new Label("Beds/Room:"), occupancyField,
            addHostel);
        addHostelForm.setAlignment(Pos.CENTER);
        addHostelForm.setPadding(new Insets(10, 0, 0, 0));
        addBottom(new Label("ADD A NEW HOSTEL"));
        addBottom(addHostelForm);

        addHostel.setOnAction(e -> {
            try {
                String hName   = nameField.getText().trim();
                int rooms      = Integer.parseInt(numberOfRoomsField.getText().trim());
                int occupancy  = Integer.parseInt(occupancyField.getText().trim());
                if (hName.isEmpty()) return;
                double fee = administrator.getInstitute().getHostelDivision().getHostelFee();
                administrator.getInstitute().getHostelDivision()
                    .addHostel(new Hostel(hName, rooms, occupancy, fee));
                nameField.clear();
                numberOfRoomsField.clear();
                occupancyField.clear();
                if (institutes != null) DatabaseManager.saveAll(institutes);
                table.refresh();
            } catch (NumberFormatException ignored) {}
        });
    }

    // Legacy no-arg constructor
    public ViewHostels() { this(null); }

    public void setUser(Administrator administrator) {
        this.administrator = administrator;
        table.setItems(administrator.getInstitute().getHostelDivision().getHostels());
    }
}
