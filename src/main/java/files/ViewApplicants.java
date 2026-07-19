package files;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

public class ViewApplicants extends rootNode {
    private Administrator administrator;
    private ObservableList<Applicant> applicants;
    private ObservableList<Institute> institutes;
    private TableView<Applicant> table = new TableView<>();

    @SuppressWarnings("unchecked")
    public ViewApplicants(ObservableList<Institute> institutes) {
        this.institutes = institutes;

        // Name column
        TableColumn<Applicant, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getName()));
        nameCol.setPrefWidth(180);

        // Merit position
        TableColumn<Applicant, String> meritCol = new TableColumn<>("Merit Position");
        meritCol.setCellValueFactory(d ->
            new SimpleStringProperty("#" + d.getValue().getMeritPosition()));
        meritCol.setPrefWidth(130);

        // Percentage
        TableColumn<Applicant, String> pctCol = new TableColumn<>("Percentage");
        pctCol.setCellValueFactory(d ->
            new SimpleStringProperty(String.format("%.1f%%", d.getValue().getlastGradePercentage())));
        pctCol.setPrefWidth(120);

        // Status column
        TableColumn<Applicant, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(d ->
            new SimpleStringProperty(d.getValue().getAdmissionStatus() ? "Admitted" : "Pending"));
        statusCol.setPrefWidth(100);

        // Admit action column
        TableColumn<Applicant, Void> admitCol = new TableColumn<>("Action");
        admitCol.setPrefWidth(120);
        admitCol.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Admit");
            {
                btn.setOnAction(e -> {
                    Applicant ap = getTableView().getItems().get(getIndex());
                    if (!ap.getAdmissionStatus() && administrator != null) {
                        String domain = administrator.getInstitute().getName()
                            .toLowerCase().replaceAll("\\s+", "");
                        String allotId = ap.getName().toLowerCase().replaceAll("\\s+", "")
                            + (administrator.getInstitute().getEducationDivision().getStudents().size() + 1)
                            + "@" + domain;
                        administrator.admitApplicant(ap, allotId);
                        if (institutes != null) DatabaseManager.saveAll(institutes);
                        table.refresh();
                    }
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Applicant ap = getTableView().getItems().get(getIndex());
                    setGraphic(ap.getAdmissionStatus() ? null : btn);
                }
            }
        });

        table.getColumns().addAll(nameCol, meritCol, pctCol, statusCol, admitCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPrefHeight(400);
        table.setPlaceholder(new Label("No applicants yet."));

        addContent(table);
    }

    // Legacy no-arg constructor
    public ViewApplicants() { this(null); }

    public void setUser(Administrator administrator) {
        this.administrator = administrator;
        applicants = administrator.getInstitute().getEducationDivision().getApplicants();
        table.setItems(applicants);
    }
}