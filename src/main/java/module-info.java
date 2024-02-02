module com.mother.pdfmerger {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires org.apache.pdfbox;

    opens com.mother.pdfmerger to javafx.fxml;
    exports com.mother.pdfmerger;
}