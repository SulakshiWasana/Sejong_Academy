package util;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ValidationUtil {

    public static Object validate(LinkedHashMap<TextField,Pattern> map, Button btn) {
        btn.setDisable(true);
        for (TextField textFieldKey : map.keySet()) {
            Pattern patternValue = map.get(textFieldKey);
            if (!patternValue.matcher(textFieldKey.getText()).matches()) {
                if (!textFieldKey.getText().isEmpty()){
                    textFieldKey.getParent().setStyle("-fx-border-color: red");
                }
                return textFieldKey;
            }
            textFieldKey.getParent().setStyle("-fx-border-color: green");
        }
        btn.setDisable(false);
        return true;
    }

    public static Object validateRegistration(LinkedHashMap<TextField,Pattern> map, Button btn) {
        btn.setDisable(true);
        for (TextField textFieldKey : map.keySet()) {
            Pattern patternValue = map.get(textFieldKey);
            if (!patternValue.matcher(textFieldKey.getText()).matches()) {
                if (!textFieldKey.getText().isEmpty()){
                    textFieldKey.setStyle("-fx-border-color: red; -fx-border-radius: 30;-fx-background-radius: 30");
                }
                return textFieldKey;
            }
            textFieldKey.setStyle("-fx-border-color: green;-fx-border-radius: 30;-fx-background-radius: 30");
        }
        btn.setDisable(false);
        return true;
    }
}

