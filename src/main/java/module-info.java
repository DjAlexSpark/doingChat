module com.djalexspark.doingchat {
    requires javafx.controls;
    requires javafx.fxml;

        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;

    opens com.djalexspark.doingchat to javafx.fxml;
    exports com.djalexspark.doingchat;
}