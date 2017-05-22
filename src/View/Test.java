package View;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Test {

    public static void dispensation(){

        Stage window = new Stage();

        GridPane grid3 = new GridPane();
        grid3.setPadding(new Insets(10, 10, 10, 10));
        grid3.setVgap(8);
        grid3.setHgap(10);

        Label værelseLabel = new Label("Værelses Nr.");
        GridPane.setConstraints(værelseLabel,0,0);

        TextField værelseTextfield = new TextField();
        værelseTextfield.setPromptText("Værelses Nr.");
        GridPane.setConstraints(værelseTextfield,1,0);

        Label navnLabel = new Label("Navn");
        GridPane.setConstraints(navnLabel,0,1);

        TextField navnTextfield = new TextField();
        navnTextfield.setPromptText("Navn");
        GridPane.setConstraints(navnTextfield,1,1);

        Label givesDisp = new Label("Gives der dispensation?");
        GridPane.setConstraints(givesDisp,0,3);

        Label jaLabel = new Label("Ja");
        GridPane.setConstraints(jaLabel,0,4);

        CheckBox jaCheckbox = new CheckBox();
        GridPane.setConstraints(jaCheckbox, 0,5);

        Label nejLabel = new Label("Nej");
        GridPane.setConstraints(nejLabel,0,6);

        CheckBox nejCheckbox = new CheckBox();
        GridPane.setConstraints(nejCheckbox,0,7);

        Label dispLabel1 = new Label("Dispensation Start");
        GridPane.setConstraints(dispLabel1,0,8);

        TextField dispTextfield = new TextField();
        dispTextfield.setPromptText("Dispensation start.");
        GridPane.setConstraints(dispTextfield,1,8);

        Label dispLabel2 = new Label("Dispensation Slut");
        GridPane.setConstraints(dispLabel2,0,9);

        TextField dispSlutTextfield = new TextField();
        dispSlutTextfield.setPromptText("Dispensation slut");
        GridPane.setConstraints(dispSlutTextfield,1,9);

        Label begrundelsesLabel = new Label("Begrundelse");
        GridPane.setConstraints(begrundelsesLabel,0,10);

        TextArea begrundelseTA = new TextArea();
        begrundelseTA.setPrefSize(150,100);
        begrundelseTA.setWrapText(true);
        GridPane.setConstraints(begrundelseTA,0,11);

        TextField deadLine1 = new TextField();
        deadLine1.setPromptText("Deadline");
        GridPane.setConstraints(deadLine1,0,12);

        TextField ddMMyyyy1 = new TextField();
        ddMMyyyy1.setPromptText("DD/MM/YYYY");
        GridPane.setConstraints(ddMMyyyy1,1,12);

        TextField deadLine2 = new TextField();
        deadLine2.setPromptText("Deadline");
        GridPane.setConstraints(deadLine2,0,13);

        TextField ddMMyyyy2 = new TextField();
        ddMMyyyy2.setPromptText("DD/MM/YYYY");
        GridPane.setConstraints(ddMMyyyy2,1,13);


        grid3.getChildren().addAll(værelseLabel, værelseTextfield, navnLabel, navnTextfield, givesDisp, dispLabel1, jaLabel, jaCheckbox, nejLabel, nejCheckbox, dispTextfield, dispLabel2, dispSlutTextfield, begrundelsesLabel, begrundelseTA, deadLine1, ddMMyyyy1, deadLine2, ddMMyyyy2);
        Scene scene = new Scene(grid3, 600, 600);

        window.setScene(scene);
        window.setTitle("Dispensation");
        window.show();
    }
}
