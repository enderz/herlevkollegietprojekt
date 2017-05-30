package View; /**
 * Created by Janus on 07-02-2017.
 */
import Controller.HovedMenuFunktion;
import Model.Beboer;
import Model.Log;
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
        scene.getStylesheets().add("PopupsLayout.css");

        window.setScene(scene);
        window.showAndWait();
    }

    /**
     *
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
        TextField textStatus = new TextField();
        TextField textSlutMaaned = new TextField();
        TextField textIndflytMaaned = new TextField();

        Button buttonFortryd = new Button("Fortryd");
        buttonFortryd.setOnAction(e -> window.close());
        buttonFortryd.setPadding(new Insets(20));

        Button buttonOpretBeboer = new Button("Opret Beboer");
        buttonOpretBeboer.setOnAction(e -> window.close());
        buttonOpretBeboer.setPadding(new Insets(20));
        buttonOpretBeboer.setOnAction((ActionEvent e) -> {
            try{
                String sqlInsert = "INSERT INTO Beboer (VaerelseNr, Navn, Indflytningsdato, Uddannelsested, Uddanelsesstart, Uddannelseafsluttes, Uddannelseretning, Email, KontrolStatus, SlutStudieMaaned, IndflytningsMaaned) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                preparedStatement = conn.prepareStatement(sqlInsert);
                preparedStatement.setString(1, textVærelse.getText());
                preparedStatement.setString(2, textNavn.getText());
                preparedStatement.setString(3, textIndflytning.getText());
                preparedStatement.setString(4, textUddannelsesInstitution.getText());
                preparedStatement.setString(5, textUddannelsePåbegyndt.getText());
                preparedStatement.setString(6, textUddannelseForventesAfsluttet.getText());
                preparedStatement.setString(7, textUddannelsesRetning.getText());
                preparedStatement.setString(8, textEmail.getText());
                preparedStatement.setString(9, textStatus.getText());
                preparedStatement.setString(10, textSlutMaaned.getText());
                preparedStatement.setString(11, textIndflytMaaned.getText());

                preparedStatement.execute();
                preparedStatement.close();
                try{
                    java.util.Date dateindflyt = new SimpleDateFormat("yyyy-MM-dd").parse(textIndflytning.getText());
                    java.util.Date uddanelsestart = new SimpleDateFormat("yyyy-MM-dd").parse(textUddannelsePåbegyndt.getText());
                    java.util.Date uddannelseslut = new SimpleDateFormat("yyyy-MM-dd").parse(textUddannelseForventesAfsluttet.getText());

                    beboerData.add(new Beboer(Integer.parseInt(textVærelse.getText()),textNavn.getText(), dateindflyt, textUddannelsesInstitution.getText(), uddanelsestart ,uddannelseslut, textUddannelsesRetning.getText(), textEmail.getText(),textStatus.getText(),textSlutMaaned.getText(),textIndflytMaaned.getText()));
                    beboerListe.getColumns().get(0).setVisible(false);
                    beboerListe.getColumns().get(0).setVisible(true);

                }catch (Exception exc){
                    exc.printStackTrace();
                }
                beboerOkAlert();                    //hovedMenuFunktion.opdatereBeboerListe();

                try{
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
        //Log.insertIntoLog("Beboer oprettet");
        alert.show();
    }
    public void beboerOpdateretOKAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Beboer Opdatering Info");
        alert.setContentText("Beboer er opdateret korrekt.");
        //Log.insertIntoLog("Beboer Opdateret");
        alert.show();
    }
    /**
     * @param conn opretter database connection
     * */

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
        TextField textStatus = new TextField();
        TextField textSlutMaaned = new TextField();
        TextField textIndflytMaaned = new TextField();

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

    public void opdaterStudieKontrolInfo(Connection conn) {
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
        Label labelStatus = new Label("Kontrol Status:");
        Label labelSlutMaaned = new Label("Slut Studie Maaned:");
        Label labelIndflytMaaned = new Label("Indflytningsmåned:");

        TextField textVærelse = new TextField();
        TextField textNavn = new TextField();
        TextField textIndflytning = new TextField();
        TextField textUddannelsesInstitution = new TextField();
        TextField textUddannelsePåbegyndt = new TextField();
        TextField textUddannelseForventesAfsluttet = new TextField();
        TextField textUddannelsesRetning = new TextField();
        TextField textStatus = new TextField();
        TextField textSlutMaaned = new TextField();
        TextField textIndflytMaaned = new TextField();

        Label labelImporterBeboerInfo = new Label("Indtast Værelsesnummer");
        TextField textFindVærelse = new TextField();
        textFindVærelse.getStyleClass().add("text-field-hent-beboer-info");

        Button hentBeboerInfoButton = new Button("Hent\nBeboer\nInfo");
        hentBeboerInfoButton.getStyleClass().add("button-hent-beboer-info");
        hentBeboerInfoButton.setMinSize(120, 120);
        hentBeboerInfoButton.setMaxSize(120, 120);
        hentBeboerInfoButton.setOnAction(e -> {
            try{
                String currentSql = "SELECT VaerelseNr, Navn, Indflytningsdato, Uddannelsested, Uddanelsesstart, Uddannelseafsluttes, Uddannelseretning, KontrolStatus,SlutStudieMaaned, IndflytningsMaaned FROM Beboer WHERE VaerelseNr="+textFindVærelse.getText();
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
                    textStatus.setText(resultSet.getString("KontrolStatus"));
                    textSlutMaaned.setText(resultSet.getString("SlutStudieMaaned"));
                    textIndflytMaaned.setText(resultSet.getString("IndflytningsMaaned"));
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
                String updateSql1 = "UPDATE Beboer SET VaerelseNr = ?, Navn = ?, Indflytningsdato = ?, Uddannelsested = ?, Uddanelsesstart = ?," +
                        "Uddannelseafsluttes = ?, Uddannelseretning = ?, KontrolStatus = ?, SlutStudieMaaned = ?, IndflytningsMaaned=? WHERE VaerelseNr="+textFindVærelse.getText();

                preparedStatement = conn.prepareStatement(updateSql1);
                preparedStatement.setString(1, textVærelse.getText());
                preparedStatement.setString(2, textNavn.getText());
                preparedStatement.setString(3, textIndflytning.getText());
                preparedStatement.setString(4, textUddannelsesInstitution.getText());
                preparedStatement.setString(5, textUddannelsePåbegyndt.getText());
                preparedStatement.setString(6, textUddannelseForventesAfsluttet.getText());
                preparedStatement.setString(7, textUddannelsesRetning.getText());
                preparedStatement.setString(8, textStatus.getText());
                preparedStatement.setString(9, textSlutMaaned.getText());
                preparedStatement.setString(10,textIndflytMaaned.getText());

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
        VBox leftLayout = new VBox(14, labelVærelse, labelNavn, labelIndflytning, labelUddannelsesInstitution,labelUddannelsePåbegyndt, labelUddannelseForventesAfsluttet, labelUddannelsesRetning, labelStatus, labelSlutMaaned,labelIndflytMaaned, buttonFortryd);
        VBox rightLayout = new VBox(10, textVærelse, textNavn, textIndflytning, textUddannelsesInstitution, textUddannelsePåbegyndt, textUddannelseForventesAfsluttet,textUddannelsesRetning, textStatus,textSlutMaaned,textIndflytMaaned, buttonOpdaterBeboer);

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

    public static void påbegyndStudieKontrol(String title) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label vælgMånedDerUdføresStudiekontrolForLabel = new Label("Vælg indflytningsmåned/måned for afslutning\naf studier der skal udføres studiekontrol for:");
        ChoiceBox hvilkenMånedDerPåbegyndesStudiekontrolForChoiceBox = new ChoiceBox(FXCollections.observableArrayList(
                "Januar", "Februar", "Marts", "April", "Maj", "Juni", "Juli", "August", "September", "Oktober", "November", "December")
        );

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
        påbegyndStudieKontrolButton.setOnAction(event -> {

        });

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
    public static void redigerAfslutStudiekontrol(String title){
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
        //layout.setCenter(separator2);

        Scene scene = new Scene(layout);
        scene.getStylesheets().add("PopupsLayout.css");
        window.setScene(scene);
        window.showAndWait();

    }
    public static void svarPåDispensation(String title) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        //LAYOUT TIL GODKENDT FREMLEJE
        Label overskriftDispensationLabel1 = new Label("Brug denne side hvis du ønsker at give dispensation til en beboer.\nHvis du ønsker at give afslag på dispensationsansøgning så skift i toppen");



        Label ansøgerensNavnLabel = new Label("Ansøgerens navn:");
        TextField ansøgernavnText = new TextField();

        Label værelsesnummerLabel = new Label("Ansøgerens værelsesnummer:");
        TextField værelsesnummerTextField = new TextField();

        Label dispensationStartDatoLabel = new Label("Startdato for Dispensation:");
        DatePicker startdatoForDispensationDatePicker = new DatePicker();
        startdatoForDispensationDatePicker.setEditable(false);

        Label slutdatoForDispensationLabel = new Label("Slutdato for dispensation:");
        DatePicker slutdatoForDispensationDatePicker = new DatePicker();
        slutdatoForDispensationDatePicker.setEditable(false);

        Label årsagTilGodkendtDispensationLabel = new Label("Årsag til godkendt fremleje");
        TextArea årsagTilGodkendtDispensationTextArea = new TextArea();
        årsagTilGodkendtDispensationTextArea.setWrapText(true);
        årsagTilGodkendtDispensationTextArea.setMaxSize(250,75);

        Label deadlinesLabelOverskrift = new Label("Tilfør eventuelle deadlines/frister:");
        Label deadlinesLabel1 = new Label("1. Deadline og dato:");
        TextArea deadline1 = new TextArea();
        deadline1.setWrapText(true);
        deadline1.setMaxSize(250,75);
        DatePicker deadline1DatePicker = new DatePicker();
        deadline1DatePicker.setEditable(false);

        Label deadlinesLabel2 = new Label("2. Deadline og dato:");
        TextArea deadline2 = new TextArea();
        deadline2.setWrapText(true);
        deadline2.setMaxSize(250,75);
        DatePicker deadline2DatePicker = new DatePicker();
        deadline1DatePicker.setEditable(false);

        Label deadlinesLabel3 = new Label("3. Deadline og dato:");
        TextArea deadline3 = new TextArea();
        deadline3.setWrapText(true);
        deadline3.setMaxSize(250,75);
        DatePicker deadline3DatePicker = new DatePicker();
        deadline1DatePicker.setEditable(false);

        Label formandsNavnLabel = new Label("Formandens fulde navn:");
        TextField formandsNavnTextField = new TextField();

        Button fortrydButton = new Button("Fortryd");
        fortrydButton.setMinWidth(70);

        Button opretDispensationButton = new Button("Opret Dispensation");
        opretDispensationButton.getStyleClass().add("button-green");

        GridPane layoutGodkendtDisp = new GridPane();
        layoutGodkendtDisp.setVgap(10);
        layoutGodkendtDisp.setHgap(10);
        layoutGodkendtDisp.setPadding(new Insets(0, 0, 10, 0));
        layoutGodkendtDisp.add(overskriftDispensationLabel1, 2, 1,5,1);
        layoutGodkendtDisp.add(ansøgerensNavnLabel, 3, 3,1,1);
        layoutGodkendtDisp.add(ansøgernavnText, 4, 3,1,1);
        layoutGodkendtDisp.add(værelsesnummerLabel, 3, 5);
        layoutGodkendtDisp.add(værelsesnummerTextField, 4, 5, 1, 1);
        layoutGodkendtDisp.add(dispensationStartDatoLabel, 3, 7);
        layoutGodkendtDisp.add(startdatoForDispensationDatePicker, 4, 7,1,1);
        layoutGodkendtDisp.add(slutdatoForDispensationLabel, 3, 9, 1, 1);
        layoutGodkendtDisp.add(slutdatoForDispensationDatePicker, 4, 9,1,1);
        layoutGodkendtDisp.add(årsagTilGodkendtDispensationLabel, 3, 11);
        layoutGodkendtDisp.add(årsagTilGodkendtDispensationTextArea, 4, 11);
        layoutGodkendtDisp.add(deadlinesLabelOverskrift, 3, 13,3,1);
        layoutGodkendtDisp.add(deadlinesLabel1, 3, 14);
        layoutGodkendtDisp.add(deadline1, 4, 14);
        layoutGodkendtDisp.add(deadline1DatePicker, 5, 14);
        layoutGodkendtDisp.add(deadlinesLabel2, 3, 16);
        layoutGodkendtDisp.add(deadline2, 4, 16);
        layoutGodkendtDisp.add(deadline2DatePicker, 5, 16);
        layoutGodkendtDisp.add(deadlinesLabel3, 3, 18);
        layoutGodkendtDisp.add(deadline3, 4, 18);
        layoutGodkendtDisp.add(deadline3DatePicker, 5, 18);
        layoutGodkendtDisp.add(formandsNavnLabel,3,19);
        layoutGodkendtDisp.add(formandsNavnTextField, 4,19);
        layoutGodkendtDisp.add(fortrydButton,1,21,2,1);
        layoutGodkendtDisp.add(opretDispensationButton,5,21);

        Tab godkendDispensationTab = new Tab("Lav Dispensation");
        godkendDispensationTab.setContent(layoutGodkendtDisp);
        godkendDispensationTab.setClosable(false);

        //AFSLAG PÅ DISPENSATION layout

        Label overskriftDispensationLabel2 = new Label("Brug denne side hvis du ønsker at give afslag på dispensationansøgning til en beboer.\nHvis du ønsker at tildele dispensation på dispensationsansøgning så skift i toppen.");

        Label ansøgerensNavnLabel2 = new Label("Ansøgerens navn:");
        TextField ansøgernavnText2 = new TextField();

        Label værelsesnummerLabel2 = new Label("Ansøgerens værelsesnummer:");
        TextField værelsesnummerTextField2 = new TextField();

        Label årsagTilAfslagDispensationLabel = new Label("Årsag til godkendt Dispensation");
        TextArea årsagTilAfslagDispensationTextArea = new TextArea();
        årsagTilAfslagDispensationTextArea.setWrapText(true);
        årsagTilAfslagDispensationTextArea.setMaxSize(250,75);

        Label formandsNavnLabel2 = new Label("Indtast formandens fulde navn:");
        TextField formandsNavnTextField2 = new TextField();

        Button fortrydButton2 = new Button("Fortryd");
        fortrydButton.setMinWidth(70);
        fortrydButton2.setOnAction(e-> window.close());

        Button givAfslagDispensationButton = new Button("Opret afslag på\nDispensationsansøgning");
        givAfslagDispensationButton.getStyleClass().add("button-green");

        GridPane layoutAfslagPåDisp = new GridPane();
        layoutAfslagPåDisp.setVgap(10);
        layoutAfslagPåDisp.setHgap(10);
        layoutAfslagPåDisp.setPadding(new Insets(0, 0, 10, 0));
        layoutAfslagPåDisp.add(overskriftDispensationLabel2, 2, 1,5,1);
        layoutAfslagPåDisp.add(ansøgerensNavnLabel2, 3, 3,2,1);
        layoutAfslagPåDisp.add(ansøgernavnText2, 4, 3,1,1);
        layoutAfslagPåDisp.add(værelsesnummerLabel2, 3, 5);
        layoutAfslagPåDisp.add(værelsesnummerTextField2, 4, 5, 1, 1);

        layoutAfslagPåDisp.add(årsagTilAfslagDispensationLabel, 3, 7);
        layoutAfslagPåDisp.add(årsagTilAfslagDispensationTextArea, 4, 7);
        layoutAfslagPåDisp.add(formandsNavnLabel2,3,9);
        layoutAfslagPåDisp.add(formandsNavnTextField2, 4,9);
        layoutAfslagPåDisp.add(fortrydButton2,1,11,2,1);
        layoutAfslagPåDisp.add(givAfslagDispensationButton,5,11);

        Tab givAfslagPåDispensationtab = new Tab("Lav afslag på Dispensation");
        givAfslagPåDispensationtab.setClosable(false);
        givAfslagPåDispensationtab.setContent(layoutAfslagPåDisp);


        TabPane layout1 = new TabPane();
        layout1.getTabs().addAll(godkendDispensationTab, givAfslagPåDispensationtab);
        BorderPane layout = new BorderPane();
        layout.setCenter(layout1);


        Scene scene = new Scene(layout);
        scene.getStylesheets().add("PopupsLayout.css");
        window.setScene(scene);
        window.showAndWait();

    }
    public static void redigerIDispensation(String title) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        //LAYOUT TIL GODKENDT FREMLEJE
        Label overskriftDispensationLabel1 = new Label("Rediger i dispensation");
        overskriftDispensationLabel1.getStyleClass().add("label-overskrift");

        Label hentDispensationInfoLabel = new Label("Indtast værelsesnummer\n(eller navn)");
        TextField hentDispensationInfoTextField = new TextField();
        hentDispensationInfoTextField.setMaxWidth(75);
        Button hentDispensationInfoButton = new Button("Hent\nDispensationsdetaljer");
        hentDispensationInfoButton.getStyleClass().add("button-hent-beboer-info");

        Separator separator = new Separator();

        Label ansøgerensNavnLabel = new Label("Ansøgerens navn:");
        TextField ansøgernavnText = new TextField();

        Label værelsesnummerLabel = new Label("Ansøgerens værelsesnummer:");
        TextField værelsesnummerTextField = new TextField();

        Label dispensationStartDatoLabel = new Label("Startdato for Dispensation:");
        DatePicker startdatoForDispensationDatePicker = new DatePicker();
        startdatoForDispensationDatePicker.setEditable(false);

        Label slutdatoForDispensationLabel = new Label("Slutdato for dispensation:");
        DatePicker slutdatoForDispensationDatePicker = new DatePicker();
        slutdatoForDispensationDatePicker.setEditable(false);

        Label årsagTilGodkendtDispensationLabel = new Label("Årsag til godkendt dispensation");
        TextArea årsagTilGodkendtDispensationTextArea = new TextArea();
        årsagTilGodkendtDispensationTextArea.setWrapText(true);
        årsagTilGodkendtDispensationTextArea.setMaxSize(250,75);

        Label deadlinesLabelOverskrift = new Label("Tilfør/Rediger eventuelle deadlines/frister:");
        Label deadlinesLabel1 = new Label("1. Deadline og dato:");
        TextArea deadline1 = new TextArea();
        deadline1.setWrapText(true);
        deadline1.setMaxSize(250,75);
        DatePicker deadline1DatePicker = new DatePicker();
        deadline1DatePicker.setEditable(false);

        Label deadlinesLabel2 = new Label("2. Deadline og dato:");
        TextArea deadline2 = new TextArea();
        deadline2.setWrapText(true);
        deadline2.setMaxSize(250,75);
        DatePicker deadline2DatePicker = new DatePicker();
        deadline1DatePicker.setEditable(false);

        Label deadlinesLabel3 = new Label("3. Deadline og dato:");
        TextArea deadline3 = new TextArea();
        deadline3.setWrapText(true);
        deadline3.setMaxSize(250,75);
        DatePicker deadline3DatePicker = new DatePicker();
        deadline1DatePicker.setEditable(false);

        Button fortrydButton = new Button("Fortryd");
        fortrydButton.setMinWidth(70);
        fortrydButton.cancelButtonProperty();

        Button redigerDispensationButton = new Button("Godkend og rediger\nDispensation");
        redigerDispensationButton.getStyleClass().add("button-green");

        GridPane layoutredigerIDisp = new GridPane();
        layoutredigerIDisp.setVgap(10);
        layoutredigerIDisp.setHgap(10);
        layoutredigerIDisp.setPadding(new Insets(0, 0, 10, 0));
        layoutredigerIDisp.add(overskriftDispensationLabel1, 2, 5,5,1);
        layoutredigerIDisp.add(hentDispensationInfoButton,2,1,2,3);
        layoutredigerIDisp.add(hentDispensationInfoLabel,4,1,2,1);
        layoutredigerIDisp.add(hentDispensationInfoTextField,4,2,2,1);
        layoutredigerIDisp.add(separator,2,4,4,1);
        layoutredigerIDisp.add(ansøgerensNavnLabel, 3, 6,1,1);
        layoutredigerIDisp.add(ansøgernavnText, 4, 6,1,1);
        layoutredigerIDisp.add(værelsesnummerLabel, 3, 8);
        layoutredigerIDisp.add(værelsesnummerTextField, 4, 8, 1, 1);
        layoutredigerIDisp.add(dispensationStartDatoLabel, 3, 10);
        layoutredigerIDisp.add(startdatoForDispensationDatePicker, 4, 10,1,1);
        layoutredigerIDisp.add(slutdatoForDispensationLabel, 3, 12, 1, 1);
        layoutredigerIDisp.add(slutdatoForDispensationDatePicker, 4, 12,1,1);
        layoutredigerIDisp.add(årsagTilGodkendtDispensationLabel, 3, 14);
        layoutredigerIDisp.add(årsagTilGodkendtDispensationTextArea, 4, 14);
        layoutredigerIDisp.add(deadlinesLabelOverskrift, 3, 15,3,1);
        layoutredigerIDisp.add(deadlinesLabel1, 3, 16);
        layoutredigerIDisp.add(deadline1, 4, 16);
        layoutredigerIDisp.add(deadline1DatePicker, 5, 16);
        layoutredigerIDisp.add(deadlinesLabel2, 3, 19);
        layoutredigerIDisp.add(deadline2, 4, 19);
        layoutredigerIDisp.add(deadline2DatePicker, 5, 19);
        layoutredigerIDisp.add(deadlinesLabel3, 3, 21);
        layoutredigerIDisp.add(deadline3, 4, 21);
        layoutredigerIDisp.add(deadline3DatePicker, 5, 21);
        layoutredigerIDisp.add(fortrydButton,1,24,2,1);
        layoutredigerIDisp.add(redigerDispensationButton,5,24);


        BorderPane layout = new BorderPane();
        layout.setCenter(layoutredigerIDisp);


        Scene scene = new Scene(layout);
        scene.getStylesheets().add("PopupsLayout.css");
        window.setScene(scene);
        window.showAndWait();

    }
    public static void svarPåFremleje(String title) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);


        //LAYOUT TIL GODKENDT FREMLEJE
        Label overskriftFremlejeLabel1 = new Label("Brug denne side hvis du ønsker at tildele indstillingens accept i forbindelse med fremleje.\nHvis du ønsker at Indstillingen modsætter sig fremlejet, så skift i toppen");

        Label ansøgerensNavnLabel = new Label("Ansøgerens navn:");
        TextField ansøgernavnText = new TextField();

        Label værelsesnummerLabel = new Label("Ansøgerens værelsesnummer:");
        TextField værelsesnummerTextField = new TextField();

        Label fremlejeStartDatoLabel = new Label("Startdato for Fremlejen:");
        DatePicker startdatoForFremlejeDatePicker = new DatePicker();
        startdatoForFremlejeDatePicker.setEditable(false);

        Label slutdatoForFremlejenLabel = new Label("Slutdato for Fremlejen:");
        DatePicker slutdatoForFremlejeDatePicker = new DatePicker();
        slutdatoForFremlejeDatePicker.setEditable(false);

        Label årsagTilGodkendtFremlejeLabel = new Label("Årsag til godkendt fremleje");
        TextArea årsagTilGodkendtFremlejeTextArea = new TextArea();
        årsagTilGodkendtFremlejeTextArea.setWrapText(true);
        årsagTilGodkendtFremlejeTextArea.setMaxSize(250,75);

        Label formandsNavnLabel = new Label("Formandens fulde navn:");
        TextField formandsNavnTextField = new TextField();

        Button fortrydButton = new Button("Fortryd");
        fortrydButton.setMinWidth(70);
        fortrydButton.setOnAction(e->window.close());

        Button opretFremlejeButton = new Button("Godkend og opret Fremleje");
        opretFremlejeButton.getStyleClass().add("button-green");

        GridPane layoutGodkendtFremleje = new GridPane();
        layoutGodkendtFremleje.setVgap(10);
        layoutGodkendtFremleje.setHgap(10);
        layoutGodkendtFremleje.setPadding(new Insets(0, 0, 10, 0));
        layoutGodkendtFremleje.add(overskriftFremlejeLabel1, 2, 1,5,1);
        layoutGodkendtFremleje.add(ansøgerensNavnLabel, 3, 3,1,1);
        layoutGodkendtFremleje.add(ansøgernavnText, 4, 3,1,1);
        layoutGodkendtFremleje.add(værelsesnummerLabel, 3, 5);
        layoutGodkendtFremleje.add(værelsesnummerTextField, 4, 5, 1, 1);
        layoutGodkendtFremleje.add(fremlejeStartDatoLabel, 3, 7);
        layoutGodkendtFremleje.add(startdatoForFremlejeDatePicker, 4, 7,1,1);
        layoutGodkendtFremleje.add(slutdatoForFremlejenLabel, 3, 9, 1, 1);
        layoutGodkendtFremleje.add(slutdatoForFremlejeDatePicker, 4, 9,1,1);
        layoutGodkendtFremleje.add(årsagTilGodkendtFremlejeLabel, 3, 11);
        layoutGodkendtFremleje.add(årsagTilGodkendtFremlejeTextArea, 4, 11);
        layoutGodkendtFremleje.add(formandsNavnLabel,3,13);
        layoutGodkendtFremleje.add(formandsNavnTextField, 4,13);
        layoutGodkendtFremleje.add(fortrydButton,1,15,2,1);
        layoutGodkendtFremleje.add(opretFremlejeButton,5,15);

        Tab godkendFremlejeTab = new Tab("Lav Dispensation");
        godkendFremlejeTab.setContent(layoutGodkendtFremleje);
        godkendFremlejeTab.setClosable(false);

        //AFSLAG PÅ DISPENSATION layout

        Label overskriftDispensationLabel2 = new Label("Brug denne side hvis du ønsker at give afslag på dispensationansøgning til en beboer.\nHvis du ønsker at tildele dispensation på dispensationsansøgning så skift i toppen.");

        Label ansøgerensNavnLabel2 = new Label("Ansøgerens navn:");
        TextField ansøgernavnText2 = new TextField();

        Label værelsesnummerLabel2 = new Label("Ansøgerens værelsesnummer:");
        TextField værelsesnummerTextField2 = new TextField();

        Label årsagTilAfslagFremlejeLabel = new Label("Årsag(er) til vi modsætter os fremleje");
        TextArea årsagTilAfslagfremlejeTextArea = new TextArea();
        årsagTilAfslagfremlejeTextArea.setWrapText(true);
        årsagTilAfslagfremlejeTextArea.setMaxSize(250,75);

        Label formandsNavnLabel2 = new Label("Indtast formandens fulde navn:");
        TextField formandsNavnTextField2 = new TextField();

        Button fortrydButton2 = new Button("Fortryd");
        fortrydButton.setMinWidth(70);
        fortrydButton2.setOnAction(e->window.close());

        Button givAfslagFremlejeButton = new Button("Opret afslag på\nFremlejeansøgning");
        givAfslagFremlejeButton.getStyleClass().add("button-green");

        GridPane layoutAfslagPåFremleje = new GridPane();
        layoutAfslagPåFremleje.setVgap(10);
        layoutAfslagPåFremleje.setHgap(10);
        layoutAfslagPåFremleje.setPadding(new Insets(0, 0, 10, 0));
        layoutAfslagPåFremleje.add(overskriftDispensationLabel2, 2, 1,5,1);
        layoutAfslagPåFremleje.add(ansøgerensNavnLabel2, 3, 3,2,1);
        layoutAfslagPåFremleje.add(ansøgernavnText2, 4, 3,1,1);
        layoutAfslagPåFremleje.add(værelsesnummerLabel2, 3, 5);
        layoutAfslagPåFremleje.add(værelsesnummerTextField2, 4, 5, 1, 1);

        layoutAfslagPåFremleje.add(årsagTilAfslagFremlejeLabel, 3, 7);
        layoutAfslagPåFremleje.add(årsagTilAfslagfremlejeTextArea, 4, 7);
        layoutAfslagPåFremleje.add(formandsNavnLabel2,3,9);
        layoutAfslagPåFremleje.add(formandsNavnTextField2, 4,9);
        layoutAfslagPåFremleje.add(fortrydButton2,1,11,2,1);
        layoutAfslagPåFremleje.add(givAfslagFremlejeButton,5,11);

        Tab givAfslagPåFremlejeTab = new Tab("Lav afslag på Dispensation");
        givAfslagPåFremlejeTab.setClosable(false);
        givAfslagPåFremlejeTab.setContent(layoutAfslagPåFremleje);

        TabPane layout1 = new TabPane();
        layout1.getTabs().addAll(godkendFremlejeTab, givAfslagPåFremlejeTab);
        BorderPane layout = new BorderPane();
        layout.setCenter(layout1);

        Scene scene = new Scene(layout);
        scene.getStylesheets().add("PopupsLayout.css");
        window.setScene(scene);
        window.showAndWait();

    }
    public static void redigerIFremleje(String title)
    {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        //LAYOUT TIL GODKENDT FREMLEJE
        Label overskriftFremlejeLabel1 = new Label("Rediger i Fremleje:");
        overskriftFremlejeLabel1.getStyleClass().add("label-overskrift");

        Label hentFremlejeInfoLabel = new Label("Indtast værelsesnummer\n(eller navn)");
        TextField hentFremlejeInfoTextField = new TextField();
        hentFremlejeInfoTextField.setMaxWidth(75);
        Button hentFremlejeInfoButton = new Button("Hent\nFremlejedetaljer");
        hentFremlejeInfoButton.getStyleClass().add("button-hent-beboer-info");

        Separator separator = new Separator();
        separator.setPadding(new Insets(20,10,20,10));

        Label ansøgerensNavnLabel = new Label("Ansøgerens navn:");
        TextField ansøgernavnText = new TextField();

        Label værelsesnummerLabel = new Label("Ansøgerens værelsesnummer:");
        TextField værelsesnummerTextField = new TextField();

        Label fremlejeStartDatoLabel = new Label("Startdato for Fremlejen:");
        DatePicker startdatoForFremlejeDatePicker = new DatePicker();
        startdatoForFremlejeDatePicker.setEditable(false);

        Label slutdatoForFremlejenLabel = new Label("Slutdato for Fremlejen:");
        DatePicker slutdatoForFremlejeDatePicker = new DatePicker();
        slutdatoForFremlejeDatePicker.setEditable(false);

        Label årsagTilGodkendtFremlejeLabel = new Label("Årsag til godkendt fremleje");
        TextArea årsagTilGodkendtFremlejeTextArea = new TextArea();
        årsagTilGodkendtFremlejeTextArea.setWrapText(true);
        årsagTilGodkendtFremlejeTextArea.setMaxSize(250,75);

        Button fortrydButton = new Button("Fortryd");
        fortrydButton.setMinWidth(70);

        Button redigerFremlejeButton = new Button("Godkend og\nrediger Fremleje");
        redigerFremlejeButton.getStyleClass().add("button-green");

        GridPane layoutGodkendtFremleje = new GridPane();
        layoutGodkendtFremleje.setVgap(10);
        layoutGodkendtFremleje.setHgap(10);
        layoutGodkendtFremleje.setPadding(new Insets(20, 20, 10, 20));
        layoutGodkendtFremleje.add(overskriftFremlejeLabel1, 2, 5,5,1);
        layoutGodkendtFremleje.add(hentFremlejeInfoButton,2,1,2,3);
        layoutGodkendtFremleje.add(hentFremlejeInfoLabel,4,1,2,1);
        layoutGodkendtFremleje.add(hentFremlejeInfoTextField,4,2,2,1);
        layoutGodkendtFremleje.add(separator,2,4,4,1);
        layoutGodkendtFremleje.add(ansøgerensNavnLabel, 3, 7,1,1);
        layoutGodkendtFremleje.add(ansøgernavnText, 4, 7,1,1);
        layoutGodkendtFremleje.add(værelsesnummerLabel, 3, 9);
        layoutGodkendtFremleje.add(værelsesnummerTextField, 4, 9, 1, 1);
        layoutGodkendtFremleje.add(fremlejeStartDatoLabel, 3, 11);
        layoutGodkendtFremleje.add(startdatoForFremlejeDatePicker, 4, 11,1,1);
        layoutGodkendtFremleje.add(slutdatoForFremlejenLabel, 3, 13, 1, 1);
        layoutGodkendtFremleje.add(slutdatoForFremlejeDatePicker, 4, 13,1,1);
        layoutGodkendtFremleje.add(årsagTilGodkendtFremlejeLabel, 3, 15);
        layoutGodkendtFremleje.add(årsagTilGodkendtFremlejeTextArea, 4, 15);
        layoutGodkendtFremleje.add(fortrydButton,1,17,2,1);
        layoutGodkendtFremleje.add(redigerFremlejeButton,5,17);


        BorderPane layout = new BorderPane();
        layout.setCenter(layoutGodkendtFremleje);

        Scene scene = new Scene(layout);
        scene.getStylesheets().add("PopupsLayout.css");
        window.setScene(scene);
        window.showAndWait();

    }
    public static void opretKlageOverBeboer(String title) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        window.setMinHeight(700);

        //LAYOUT TIL KLAGE OVER FOR SENT AFLEVERET STUDIEDOKUMENTATION
        Label overskriftKlageLabel1 = new Label("Brug denne side hvis klagen du er ved at lave \n" +
                "omhandler at beboeren afleverer sin studiedokumentation til indstillingen for sent.\n" +
                "Hvis klagen du er ved at oprette omhandler \"uoverensstemmelse i studieoplysninger\",\n" +
                "så skift i toppen af vinduet.\n" +
                "Omhandler klagen noget andet er der ingen skabelon der kan udfyldes. Denne må laves fra\n" +
                "bunden. Se evt. under \"Indstillingsskabeloner\"");

        Label beboerensNavnLabel = new Label("Beboerens navn:");
        TextField beboernavnText = new TextField();

        Label værelsesnummerLabel = new Label("Beboerens værelsesnummer:");
        TextField værelsesnummerTextField = new TextField();

        Label studiekontrolStartDatoLabel = new Label("Dato for påbegyndt studiekontrol:");
        DatePicker startdatoForStudiekontrolDatePicker = new DatePicker();
        startdatoForStudiekontrolDatePicker.setEditable(false);

        Label påmindelseOmStudiekontrolLabel = new Label("Slutdato for dispensation:");
        DatePicker påmindelseOmStudiekontrolDatePicker = new DatePicker();
        påmindelseOmStudiekontrolDatePicker.setEditable(false);

        Label slutdatoForStudiekontrolLabel = new Label("Slutdato for dispensation:");
        DatePicker slutdatoForStudiekontrolDatePicker = new DatePicker();
        slutdatoForStudiekontrolDatePicker.setEditable(false);

        Label formandsNavnLabel = new Label("Formandens fulde navn:");
        TextField formandsNavnTextField = new TextField();

        Button fortrydButton = new Button("Fortryd");
        fortrydButton.setMinWidth(70);

        Button opretKlageButton = new Button("Opret Klage");
        opretKlageButton.getStyleClass().add("button-green");

        GridPane layoutOpretKlage = new GridPane();
        layoutOpretKlage.setVgap(10);
        layoutOpretKlage.setHgap(10);
        layoutOpretKlage.setPadding(new Insets(0, 0, 10, 0));
        layoutOpretKlage.add(overskriftKlageLabel1, 2, 1,5,1);
        layoutOpretKlage.add(beboerensNavnLabel, 3, 3,1,1);
        layoutOpretKlage.add(beboernavnText, 4, 3,1,1);
        layoutOpretKlage.add(værelsesnummerLabel, 3, 5);
        layoutOpretKlage.add(værelsesnummerTextField, 4, 5, 1, 1);
        layoutOpretKlage.add(studiekontrolStartDatoLabel, 3, 7);
        layoutOpretKlage.add(startdatoForStudiekontrolDatePicker, 4, 7,1,1);
        layoutOpretKlage.add(påmindelseOmStudiekontrolLabel, 3, 9);
        layoutOpretKlage.add(påmindelseOmStudiekontrolDatePicker, 4, 9,1,1);
        layoutOpretKlage.add(slutdatoForStudiekontrolLabel, 3, 11, 1, 1);
        layoutOpretKlage.add(slutdatoForStudiekontrolDatePicker, 4, 11,1,1);
        layoutOpretKlage.add(formandsNavnLabel,3,13);
        layoutOpretKlage.add(formandsNavnTextField, 4,13);
        layoutOpretKlage.add(fortrydButton,1,15,2,1);
        layoutOpretKlage.add(opretKlageButton,5,15);

        Tab klagOverForSenStudiedokumentationTab = new Tab("Klag over for\n sent afleveret\nstudiedokumentation");
        klagOverForSenStudiedokumentationTab.setContent(layoutOpretKlage);
        klagOverForSenStudiedokumentationTab.setClosable(false);

        //AFSLAG PÅ KLAGE UOVERENSSTEMMENDE OPLYSNINGER MODTAGET layout

        Label overskriftKlageLabel2 = new Label("Brug denne side hvis klagen du er ved at lave omhandler at en beboer\n" +
                "ikke har orienteret Indstillingen om ændringer i forbindelse med studie.\n" +
                "Hvis Klagen istedet omhandler for \"sent afleveret studiedokumentation\"\n" +
                "så skift i toppen af vinduet.\n" +
                "Omhandler klagen noget andet er der ingen skabelon der kan udfyldes.\n" +
                "Denne må laves fra bunden. Se evt. under \"Indstillingsskabeloner\"");

        Label beboerensNavnLabel2 = new Label("Beboerens navn:");
        TextField beboernavnText2 = new TextField();

        Label værelsesnummerLabel2 = new Label("Beboerens værelsesnummer:");
        TextField værelsesnummerTextField2 = new TextField();

        Label årsagTilKlageLabel = new Label("Årsag til klagen.\n" +
                "Hvad er Indstillingen ikke\n " +
                "blevet orienteret om:");
        TextArea årsagTilKlageTextArea = new TextArea();
        årsagTilKlageTextArea.setWrapText(true);
        årsagTilKlageTextArea.setMaxSize(250,75);

        Label formandsNavnLabel2 = new Label("Indtast formandens fulde navn:");
        TextField formandsNavnTextField2 = new TextField();

        Button fortrydButton2 = new Button("Fortryd");
        fortrydButton2.setMinWidth(70);
        fortrydButton2.setOnAction(e->window.close());

        Button opretKlageButton2 = new Button("Opret Klage");
        opretKlageButton2.getStyleClass().add("button-green");

        GridPane layoutKlageOverForSenAflevering = new GridPane();
        layoutKlageOverForSenAflevering.setVgap(10);
        layoutKlageOverForSenAflevering.setHgap(10);
        layoutKlageOverForSenAflevering.setPadding(new Insets(0, 0, 10, 0));
        layoutKlageOverForSenAflevering.add(overskriftKlageLabel2, 2, 1,5,1);
        layoutKlageOverForSenAflevering.add(beboerensNavnLabel2, 3, 3,2,1);
        layoutKlageOverForSenAflevering.add(beboernavnText2, 4, 3,1,1);
        layoutKlageOverForSenAflevering.add(værelsesnummerLabel2, 3, 5);
        layoutKlageOverForSenAflevering.add(værelsesnummerTextField2, 4, 5, 1, 1);
        layoutKlageOverForSenAflevering.add(årsagTilKlageLabel, 3, 7);
        layoutKlageOverForSenAflevering.add(årsagTilKlageTextArea, 4, 7);
        layoutKlageOverForSenAflevering.add(formandsNavnLabel2,3,9);
        layoutKlageOverForSenAflevering.add(formandsNavnTextField2, 4,9);
        layoutKlageOverForSenAflevering.add(fortrydButton2,1,11,2,1);
        layoutKlageOverForSenAflevering.add(opretKlageButton2,5,11);

        Tab klageOverUoverensstemmelseIOplysningerTab = new Tab("Klag over \"Uoverensstemmelser\"\ni oplysninger");
        klageOverUoverensstemmelseIOplysningerTab.setClosable(false);
        klageOverUoverensstemmelseIOplysningerTab.setContent(layoutKlageOverForSenAflevering);

        TabPane layout1 = new TabPane();
        layout1.getTabs().addAll(klagOverForSenStudiedokumentationTab, klageOverUoverensstemmelseIOplysningerTab);
        BorderPane layout = new BorderPane();
        layout.setCenter(layout1);

        Scene scene = new Scene(layout);
        scene.getStylesheets().add("PopupsLayout.css");
        window.setScene(scene);
        window.showAndWait();
    }
}