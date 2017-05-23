package View; /**
 * Created by Janus on 07-02-2017.
 */
import Controller.HovedMenuFunktion;
import Model.Beboer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

import java.sql.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class PopUps
{
    PreparedStatement preparedStatement;
    Connection conn;
    ResultSet resultSet;
    HovedMenuFunktion hovedMenuFunktion = new HovedMenuFunktion();

    public void tilføjDeadline(String title, String message)
    {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label("Deadline:");
        Label label2 = new Label("Vælg dato:");

        DatePicker datePicker = new DatePicker();
        datePicker.setEditable(false);

        TextField textField = new TextField();

        Button tilfoejButton = new Button("Føj til listen");
        tilfoejButton.setOnAction(e -> window.close());
        //tilfoejButton.setPadding(new Insets(20));

        Button fortrydButton = new Button("Fortryd");
        fortrydButton.setOnAction(e -> window.close());
        //fortrydButton.setPadding(new Insets(20));//Ændrer på Knappens størrelse


        GridPane layout = new GridPane();
//        layout.getChildren().addAll(label, closeButton);
        layout.add(label2, 2, 1);
        layout.add(label, 4, 1);
        layout.add(datePicker, 2, 3);
        layout.add(textField, 4, 3);
        layout.add(tilfoejButton, 4, 5);
        layout.add(fortrydButton, 2, 5);


        //layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(40, 20, 50, 20));
        layout.setHgap(10);
        layout.setVgap(10);
        Scene scene = new Scene(layout);
        scene.getStylesheets().add("popUps.css");

        window.setScene(scene);
        window.showAndWait();
    }

    /**
     * Created by Janus on 07-02-2017.
     */

    public void opretBeboer(Connection conn, TableView<Beboer> beboerListe, ObservableList<Beboer> beboerData) {

        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Opret Ny Beboer");
        window.setMinWidth(250);

        Label labelVærelse = new Label("Værelse:");
        Label labelNavn = new Label("Navn:");
        Label labelIndflytning = new Label("Indlfytning:");
        Label labelUddannelsesInstitution = new Label("Uddannelsesinstitution:");
        Label labelUddannelsesRetning = new Label("Uddannelsesretning:");
        Label labelUddannelsePåbegyndt = new Label("Uddannelse påbegyndt:");
        Label labelUddannelseForventesAfsluttet = new Label("Uddannelse forventes afsluttet:");
        Label labelEmail = new Label("Email:");

        TextField textVærelse = new TextField();
        TextField textNavn = new TextField();
        TextField textIndflytning = new TextField();
        TextField textUddannelsesInstitution = new TextField();
        TextField textUddannelsePåbegyndt = new TextField();
        TextField textUddannelseForventesAfsluttet = new TextField();
        TextField textUddannelsesRetning = new TextField();
        TextField textEmail = new TextField();

        Button buttonFortryd = new Button("Fortryd");
        buttonFortryd.setOnAction(e -> window.close());
        buttonFortryd.setPadding(new Insets(20));

        Button buttonOpretBeboer = new Button("Opret Beboer");
        buttonOpretBeboer.setOnAction(e -> window.close());
        buttonOpretBeboer.setPadding(new Insets(20));
        buttonOpretBeboer.setOnAction((ActionEvent e) -> {
            try{
                String sqlInsert = "INSERT INTO Beboer (VaerelseNr, Navn, Indflytningsdato, Uddannelsested, Uddanelsesstart, Uddannelseafsluttes, Uddannelseretning, Email) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                preparedStatement = conn.prepareStatement(sqlInsert);
                preparedStatement.setString(1, textVærelse.getText());
                preparedStatement.setString(2, textNavn.getText());
                preparedStatement.setString(3, textIndflytning.getText());
                preparedStatement.setString(4, textUddannelsesInstitution.getText());
                preparedStatement.setString(5, textUddannelsePåbegyndt.getText());
                preparedStatement.setString(6, textUddannelseForventesAfsluttet.getText());
                preparedStatement.setString(7, textUddannelsesRetning.getText());
                preparedStatement.setString(8, textEmail.getText());

                preparedStatement.execute();
                preparedStatement.close();
                try{
                    java.util.Date dateindflyt = new SimpleDateFormat("yyyy-MM-dd").parse(textIndflytning.getText());
                    java.util.Date uddanelsestart = new SimpleDateFormat("yyyy-MM-dd").parse(textUddannelsePåbegyndt.getText());
                    java.util.Date uddannelseslut = new SimpleDateFormat("yyyy-MM-dd").parse(textUddannelseForventesAfsluttet.getText());

                    beboerData.add(new Beboer(Integer.parseInt(textVærelse.getText()),textNavn.getText(), dateindflyt, textUddannelsesInstitution.getText(), uddanelsestart ,uddannelseslut, textUddannelsesRetning.getText(), textEmail.getText()));
                    beboerListe.getColumns().get(0).setVisible(false);
                    beboerListe.getColumns().get(0).setVisible(true);

                }catch (Exception exc){
                    exc.printStackTrace();
                }
                beboerOkAlert();
                try{
                    //hovedMenuFunktion.opdatereBeboerListe(beboerData);
                    System.out.println("tableview skal opdateres samtidig her i GUI !!!");
                }catch (NullPointerException ex){
                    ex.printStackTrace();
                    System.out.println(ex);
                }
                window.close();

            }catch(SQLException ex){
                ex.printStackTrace();
            }
        });

        VBox leftLayout = new VBox(14, labelVærelse, labelNavn, labelIndflytning, labelUddannelsesInstitution, labelUddannelsePåbegyndt, labelUddannelseForventesAfsluttet, labelUddannelsesRetning, labelEmail, buttonFortryd);
        VBox rightLayout = new VBox(10, textVærelse, textNavn, textIndflytning, textUddannelsesInstitution, textUddannelsePåbegyndt, textUddannelseForventesAfsluttet, textUddannelsesRetning, textEmail, buttonOpretBeboer);

        BorderPane layout = new BorderPane();
        layout.setLeft(leftLayout);
        layout.setRight(rightLayout);

        Scene scene = new Scene(layout);
        scene.getStylesheets().add("PopupsLayout.css");
        window.setScene(scene);
        window.showAndWait();

    }
    public void beboerOkAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Beboer Info");
        alert.setContentText("Beboer oprettet korrekt.");
        alert.show();
    }
    public void beboerOpdateretOKAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Beboer Opdatering Info");
        alert.setContentText("Beboer er opdateret korrekt.");
        alert.show();
    }

    public void opdaterBeboerInfo(Connection conn) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Opdater aktuelle beboer");
        window.setMinWidth(250);

        Label labelVærelse = new Label("Værelse:");
        Label labelNavn = new Label("Navn:");
        Label labelIndflytning = new Label("Indlfytning:");
        Label labelUddannelsesInstitution = new Label("Uddannelsesinstitution:");
        Label labelUddannelsesRetning = new Label("Uddannelsesretning:");
        Label labelUddannelsePåbegyndt = new Label("Uddannelse påbegyndt:");
        Label labelUddannelseForventesAfsluttet = new Label("Uddannelse forventes afsluttet:");
        Label labelEmail = new Label("Email:");

        TextField textVærelse = new TextField();
        TextField textNavn = new TextField();
        TextField textIndflytning = new TextField();
        TextField textUddannelsesInstitution = new TextField();
        TextField textUddannelsePåbegyndt = new TextField();
        TextField textUddannelseForventesAfsluttet = new TextField();
        TextField textUddannelsesRetning = new TextField();
        TextField textEmail = new TextField();

        Label labelImporterBeboerInfo = new Label("Indtast Værelsesnummer");
        TextField textFindVærelse = new TextField();
        textFindVærelse.getStyleClass().add("text-field-hent-beboer-info");

        Button hentBeboerInfoButton = new Button("Hent\nBeboer\nInfo");
        hentBeboerInfoButton.getStyleClass().add("button-hent-beboer-info");
        hentBeboerInfoButton.setMinSize(120, 120);
        hentBeboerInfoButton.setMaxSize(120, 120);
        hentBeboerInfoButton.setOnAction(e -> {
            try{
                String currentSql = "SELECT * FROM Beboer WHERE VaerelseNr ="+textFindVærelse.getText();
                preparedStatement = conn.prepareStatement(currentSql);
                resultSet = preparedStatement.executeQuery();

                while(resultSet.next()){
                    textVærelse.setText(resultSet.getString("VaerelseNr"));
                    textNavn.setText(resultSet.getString("Navn"));
                    textIndflytning.setText(resultSet.getString("Indflytningsdato"));
                    textUddannelsesInstitution.setText(resultSet.getString("Uddannelsested"));
                    textUddannelsePåbegyndt.setText(resultSet.getString("Uddanelsesstart"));
                    textUddannelseForventesAfsluttet.setText(resultSet.getString("Uddannelseafsluttes"));
                    textUddannelsesRetning.setText(resultSet.getString("Uddannelseretning"));
                    textEmail.setText(resultSet.getString("Email"));
                }
                preparedStatement.close();
                resultSet.close();

            }catch (SQLException ex){
                ex.printStackTrace();
            }
        });
        Separator separator = new Separator();
        separator.setValignment(VPos.CENTER);
        separator.setPrefSize(450, 40);

        Button buttonFortryd = new Button("Fortryd");
        buttonFortryd.setOnAction(e -> window.close());
        buttonFortryd.setPadding(new Insets(20));

        Button buttonOpdaterBeboer = new Button("Opdater Beboer");
        buttonOpdaterBeboer.setPadding(new Insets(20));
        buttonOpdaterBeboer.setOnAction(e -> {
            try{
                String updateSql = "UPDATE Beboer SET VaerelseNr = ?, Navn = ?, Indflytningsdato = ?, Uddannelsested = ?, Uddanelsesstart = ?," +
                            "Uddannelseafsluttes = ?, Uddannelseretning = ?, Email = ? WHERE VaerelseNr="+textFindVærelse.getText();

                preparedStatement = conn.prepareStatement(updateSql);
                preparedStatement.setString(1, textVærelse.getText());
                preparedStatement.setString(2, textNavn.getText());
                preparedStatement.setString(3, textIndflytning.getText());
                preparedStatement.setString(4, textUddannelsesInstitution.getText());
                preparedStatement.setString(5, textUddannelsePåbegyndt.getText());
                preparedStatement.setString(6, textUddannelseForventesAfsluttet.getText());
                preparedStatement.setString(7, textUddannelsesRetning.getText());
                preparedStatement.setString(8, textEmail.getText());

                preparedStatement.executeUpdate();
                preparedStatement.close();
                beboerOpdateretOKAlert();
                window.close();
            }catch (SQLException sqle){
                sqle.printStackTrace();
            }

        });

        VBox topLeftLayout = new VBox(10, hentBeboerInfoButton);
        VBox topRightLayout = new VBox(10, labelImporterBeboerInfo, textFindVærelse);
        HBox topBottomLayout = new HBox(20, separator);

        BorderPane topLayout = new BorderPane();
        topLayout.setLeft(topLeftLayout);
        topLayout.setRight(topRightLayout);
        topLayout.setBottom(topBottomLayout);

        topLayout.setPadding(new Insets(30));
        VBox leftLayout = new VBox(14, labelVærelse, labelNavn, labelIndflytning, labelUddannelsesInstitution,labelUddannelsePåbegyndt, labelUddannelseForventesAfsluttet, labelUddannelsesRetning, labelEmail, buttonFortryd);
        VBox rightLayout = new VBox(10, textVærelse, textNavn, textIndflytning, textUddannelsesInstitution, textUddannelsePåbegyndt, textUddannelseForventesAfsluttet,textUddannelsesRetning, textEmail, buttonOpdaterBeboer);

        BorderPane layout = new BorderPane();
        layout.setLeft(leftLayout);
        layout.setRight(rightLayout);
        layout.setTop(topLayout);

        Scene scene = new Scene(layout);
        scene.getStylesheets().add("PopupsLayout.css");
        //scene.setOnKeyPressed(event -> {
        //            });
        window.setScene(scene);
        window.showAndWait();
    }

    public void påbegyndStudieKontrol(String title) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label vælgMånedDerUdføresStudiekontrolForLabel = new Label("Vælg indflytningsmåned/måned for afslutning\naf studier der skal udføres studiekontrol for:");
        ChoiceBox hvilkenMånedDerPåbegyndesStudiekontrolForChoiceBox = new ChoiceBox(FXCollections.observableArrayList(
                "Januar", "Februar", "Marts", "April", "Maj", "Juni", "Juli", "August", "September", "Oktober", "November", "December"));

        Label studiekontrolStartDatoLabel = new Label("Studiekontrol påbegyndes d.:");

        DatePicker studiekontrolStartDatoDatePicker = new DatePicker();
        studiekontrolStartDatoDatePicker.setEditable(false);

        Label påmindelseDeadlinesLabel = new Label("Påmindelse om studiekontrol afsendes d.");
        DatePicker påmindelseDeadlineDatePicker = new DatePicker();
        påmindelseDeadlineDatePicker.setEditable(false);

        Label sidsteFristForAfleveringLabel = new Label("Endelig afleveringsfrist d.:");
        DatePicker sidsteFristForAfleveringDatePicker = new DatePicker();//Content skal på en eller anden måde findes
        sidsteFristForAfleveringDatePicker.setEditable(false);

        Separator separator = new Separator();
        Separator separator2 = new Separator();

        Button påbegyndStudieKontrolButton = new Button("Påbegynd\nstudiekontrol");
        påbegyndStudieKontrolButton.getStyleClass().add("button-paabegynd-studiekontrol");

        GridPane topLayout = new GridPane();
        topLayout.setVgap(20);
        topLayout.setHgap(10);
        topLayout.setPadding(new Insets(0, 0, 10, 0));
        topLayout.add(vælgMånedDerUdføresStudiekontrolForLabel, 1, 1);
        topLayout.add(hvilkenMånedDerPåbegyndesStudiekontrolForChoiceBox, 1, 2);
        topLayout.add(studiekontrolStartDatoLabel, 1, 4);
        topLayout.add(studiekontrolStartDatoDatePicker, 1, 5);
        topLayout.add(separator, 1, 6, 2, 1);
        topLayout.add(påmindelseDeadlinesLabel, 1, 7);
        topLayout.add(påmindelseDeadlineDatePicker, 1, 8);
        topLayout.add(separator2, 1, 9, 2, 1);
        topLayout.add(sidsteFristForAfleveringLabel, 1, 10);
        topLayout.add(sidsteFristForAfleveringDatePicker, 1, 11);
        topLayout.add(påbegyndStudieKontrolButton, 3, 11);

        BorderPane layout = new BorderPane();
        layout.setTop(topLayout);

        Scene scene = new Scene(layout);
        scene.getStylesheets().add("PopupsLayout.css");
        window.setScene(scene);
        window.showAndWait();
    }
    public void redigerAfslutStudiekontrol(String title)
    {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        //TopLayout
        Label igangværendeStudiekontrollerLabel = new Label("Igangværende studiekontroller");
        TableView igangværendeStudiekontrollerTableView = new TableView();// Skal have columns: Måned - StartDato - Slutdato - Antal der ikke har afleveret.
        igangværendeStudiekontrollerTableView.setPrefHeight(200);
        Separator separator1 = new Separator();
        separator1.setPrefSize(300,30);
        //Left Layout

        Label månedsLabelLeft = new Label("Vælg måned:");
        ChoiceBox igangværendeStudiekontrollerChoicceBoxLeft = new ChoiceBox(FXCollections.observableArrayList(
                "Januar", "Februar", "Marts","April","Maj","Juni","Juli","August","September","Oktober","November", "December")
        );
        Label værelsesLabel = new Label("Værelse(r):");
        ChoiceBox værelsesChoicceBox = new ChoiceBox(FXCollections.observableArrayList(
                "201", "202", "203","...","Alle")//Kun alle de værelser der ikke har afleveret i en given måned skal vises her
        );

        Button udsætStudiekontrolButton = new Button("Udsæt\nStudiekontrol");
        udsætStudiekontrolButton.getStyleClass().add("button-udsaet-studiekontrol");

        //Center Layout
        Separator separator2 = new Separator();
        separator2.setOrientation(Orientation.VERTICAL);

        //Right Layout
        Label månedsLabelRight = new Label("Vælg måned:");
        ChoiceBox igangværendeStudiekontrollerChoicceBoxRight = new ChoiceBox(FXCollections.observableArrayList(
                "Januar", "Februar", "Marts","April","Maj","Juni","Juli","August","September","Oktober","November", "December")
        );
        Button afslutStudiekontrolButton = new Button("Afslut\nStudiekontrol");
        afslutStudiekontrolButton.getStyleClass().add("button-afslut-studiekontrol");

        VBox topLayout = new VBox(10,igangværendeStudiekontrollerLabel, igangværendeStudiekontrollerTableView, separator1);
        GridPane leftLayout = new GridPane();
        leftLayout.setPadding(new Insets(30));
        leftLayout.setVgap(10);
        leftLayout.setHgap(10);
        leftLayout.add(månedsLabelLeft,1,1);
        leftLayout.add(igangværendeStudiekontrollerChoicceBoxLeft,1,2);
        leftLayout.add(værelsesLabel, 1,3);
        leftLayout.add(værelsesChoicceBox, 1,4);
        leftLayout.add(udsætStudiekontrolButton,1,6);
        leftLayout.add(separator2,3,1, 1, 6);
        leftLayout.add(månedsLabelRight,5,1);
        leftLayout.add(igangværendeStudiekontrollerChoicceBoxRight,5,2);
        leftLayout.add(afslutStudiekontrolButton, 5,6);

        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(20));
        layout.setTop(topLayout);
        layout.setLeft(leftLayout);

        Scene scene = new Scene(layout);
        scene.getStylesheets().add("PopupsLayout.css");
        window.setScene(scene);
        window.showAndWait();
    }
}