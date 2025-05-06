module codesource.tp1code {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens codesource.tp1code to javafx.fxml;
    exports codesource.tp1code;
    exports codesource.tp1code.controllers;
    opens codesource.tp1code.controllers to javafx.fxml;
    exports codesource.tp1code.utils;
    opens codesource.tp1code.utils to javafx.fxml;
}