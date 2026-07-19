package files;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class ViewFee extends rootNode {
    private Student student;
    private TableView<FeeEntity> table = new TableView<>();
    private Label totalLabel = new Label();

    @SuppressWarnings("unchecked")
    public ViewFee() {
        TableColumn<FeeEntity, String> titleCol = new TableColumn<>("Description");
        titleCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getTitle()));
        titleCol.setPrefWidth(300);

        TableColumn<FeeEntity, String> amountCol = new TableColumn<>("Amount (PKR)");
        amountCol.setCellValueFactory(d ->
            new SimpleStringProperty(String.format("%.0f", d.getValue().getAmount())));
        amountCol.setPrefWidth(160);

        table.getColumns().addAll(titleCol, amountCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPrefHeight(340);
        table.setPlaceholder(new Label("No fee records yet."));

        totalLabel.setStyle("-fx-font-size:15px; -fx-font-weight:bold; -fx-text-fill:#1e293b;");
        totalLabel.setPadding(new Insets(10, 0, 0, 0));

        addContent(table, totalLabel);
    }

    public void setUser(Student student) {
        this.student = student;
        table.setItems(student.getFeeDetails());
        totalLabel.setText("Total Due: PKR " + String.format("%.0f", student.getDueFee()));
    }
}
