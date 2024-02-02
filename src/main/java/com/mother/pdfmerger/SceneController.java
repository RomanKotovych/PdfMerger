package com.mother.pdfmerger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SceneController {
    PdfMerger pdfMerger = new PdfMerger();
    private List<String> pdfList = new ArrayList<>();

    @FXML
    private VBox dragAndDropBox;

    @FXML
    private TextField nameField;

    @FXML
    private TextField outputPathField;

    @FXML
    void chooseFiles(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите файлы PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(new Stage());

        if (selectedFiles != null) {
            for (File file : selectedFiles) {
                pdfList.add(file.getAbsolutePath());
            }
        }
    }

    @FXML
    void clearFiles(ActionEvent event) {
        pdfList.clear();
    }


    @FXML
    void browseDirectory(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Выберите директорию");

        File selectedDirectory = directoryChooser.showDialog(new Stage());

        outputPathField.setText(selectedDirectory.getAbsolutePath());
    }

    @FXML
    void onDragOver(DragEvent event) {
        if (event.getGestureSource() != dragAndDropBox &&
                event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY);
        }
        event.consume();
    }

    @FXML
    void onFileDropped(DragEvent event) {
        List<File> droppedFiles = event.getDragboard().getFiles();
        for (File file : droppedFiles) {
            pdfList.add(file.getAbsolutePath());
        }
    }

@FXML
void merge(ActionEvent event) {
    try {
        pdfMerger.merge(outputPathField.getText(), nameField.getText(), pdfList.toArray(new String[0]));
    } catch (Exception e) {
        showError(e.getMessage(), e);
    } finally {
        pdfList.clear();
    }
}

private void showError(String message, Exception ex) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText(message);

    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    ex.printStackTrace(pw);
    String exceptionText = sw.toString();

    Label label = new Label("Exception stacktrace was: ");

    TextArea textArea = new TextArea(exceptionText);
    textArea.setEditable(false);
    textArea.setWrapText(true);

    textArea.setMaxWidth(Double.MAX_VALUE);
    textArea.setMaxHeight(Double.MAX_VALUE);
    GridPane.setVgrow(textArea, Priority.ALWAYS);
    GridPane.setHgrow(textArea, Priority.ALWAYS);

    GridPane expContent = new GridPane();
    expContent.setMaxWidth(Double.MAX_VALUE);
    expContent.add(label, 0, 0);
    expContent.add(textArea, 0, 1);
    alert.getDialogPane().setExpandableContent(expContent);

    alert.showAndWait();
}
}
