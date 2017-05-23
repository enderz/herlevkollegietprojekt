package View;

import Controller.HovedMenuFunktion;
import Model.*;
import Controller.LoginFunktion;

import com.sun.javafx.scene.control.skin.DatePickerSkin;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.Date;

/**
 * Created by Ender on 07-05-2017.
 */
public class Main extends Application
{
    Stage window;
    Scene sceneLogin, sceneStart, sceneStudiekontrol, sceneBeboerListe, sceneFormand;
    DBConnection dbConnection = new DBConnection();
    LoginFunktion loginFunk = new LoginFunktion();
    Connection conn;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    TableView<Beboer> beboerListe;
    final ObservableList<Beboer> beboerData = FXCollections.observableArrayList();

    public static void main(String[] args)
    {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        conn = dbConnection.getDBConnection();
        dbConnection.checkDBConnection();

        window = primaryStage;
        window.setTitle("LOG IND - Herlev Kollegiet");

        GridPane grid = new GridPane();
        grid.setStyle("-fx-background-color: #508090;");
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label nameLabel = new Label("Brugernavn");
        GridPane.setConstraints(nameLabel, 0 , 0);

        TextField nameInput = new TextField();
        nameInput.setPromptText("Brugernavn");
        GridPane.setConstraints(nameInput, 1, 0);

        Label passwordLabel = new Label("Kodeord");
        GridPane.setConstraints(passwordLabel, 0 , 1);

        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("Kodeord");
        GridPane.setConstraints(passwordInput,1 ,1);

        Button loginButton = new Button("Log Ind");
        GridPane.setConstraints(loginButton,1 ,2);
        loginButton.setOnAction((ActionEvent event) -> {
            if(loginFunk.login(conn, nameInput, passwordInput)==true){
               try{
                   startHovedMenu(window);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else{
                loginAlert();
            }
        });

        Button annullerButton  = new Button("Annuller");
        GridPane.setConstraints(annullerButton,1,3);
        annullerButton.setOnAction(e -> {
            dbConnection.closeDBConnection(conn);
            window.close();
        });

        grid.getChildren().addAll(nameLabel, nameInput, passwordLabel, passwordInput, loginButton, annullerButton);
        sceneLogin = new Scene(grid, 500,200);
        sceneLogin.setOnKeyPressed(event -> {
            if(loginFunk.login(conn, nameInput, passwordInput)==true){
                try{
                    startHovedMenu(window);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else{
                loginAlert();
            }
        });

        window.setScene(sceneLogin);
        window.show();
    }
    public void startHovedMenu(Stage primaryStage)
    {
        conn = dbConnection.getDBConnection();
        dbConnection.checkDBConnection();

        window = primaryStage;
        window.setTitle("Herlevkollegiets Indstillingsudvalg");
        window.getIcons().add(new Image("Butterfly1.jpg")); // HER SKAL HEKO LOGO VÆRE

        // Start Menu setup!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //Venstre Del af menuen
        //Buttons
        Button studiekontrolButton = new Button("Studiekontrol");
        studiekontrolButton.getStyleClass().add("button-hovedmenu");
        studiekontrolButton.setOnAction(e -> window.setScene(sceneStudiekontrol));

        beboerListe = new TableView<>();

        Button beboerListeButton = new Button("Beboer-Liste");
        beboerListeButton.getStyleClass().add("button-hovedmenu");
        beboerListeButton.setOnAction((ActionEvent event) -> {
            window.setScene(sceneBeboerListe);
            visAlleBeboer();
        });

        Button formandButton = new Button("Formanden");
        formandButton.getStyleClass().add("button-hovedmenu");
        formandButton.setOnAction(e-> window.setScene(sceneFormand));

        Button indstillingsSkabelonerButton = new Button("Indstillingsskabeloner");
        indstillingsSkabelonerButton.getStyleClass().add("button-hovedmenu");
//        indstillingsSkabelonerButton.setOnAction(e-> window.setScene(sceneSkabeloner));

        Button logoutButton = new Button("Log ud");
        logoutButton.getStyleClass().add("button-logout");
        logoutButton.setOnAction(e ->{
            try {
                logoutAlert();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        //Højre del af menuen
        Label kalenderLabel = new Label("Kalender");
        kalenderLabel.getStyleClass().add("label-hovedmenu");
        //Method displays popupcontent of calendar instead of it being a pop-up.
        DatePickerSkin skin = new DatePickerSkin(new DatePicker(LocalDate.now()));
        Node calendarControl = skin.getPopupContent();
        calendarControl.getStyleClass().add("date-picker");

        Label deadlineLabel = new Label("Deadlines: ");
        deadlineLabel.getStyleClass().add("label-hovedmenu");

        TableColumn<Deadline, String> dato = new TableColumn<>("Dato");
        TableColumn<Deadline, Integer> værelse = new TableColumn<>("Evt.\nVærelse");

        dato.setMinWidth(70);
        dato.setCellValueFactory(new PropertyValueFactory<>("dato"));//Property need to match the class's field names

        TableColumn<Deadline, String> deadline = new TableColumn<>("Deadline");
        deadline.setMinWidth(300);
        deadline.setSortable(false); // Fjerner mulighed for at sortere
        deadline.setCellValueFactory(new PropertyValueFactory<>("deadline"));//Property need to match the class's field names

        TableView deadlinesView = new TableView();
        deadlinesView.setItems(getReminder());
        deadlinesView.getColumns().addAll(dato, værelse, deadline);

        //Buttons tilføjes til HBox for horisontal placering
        Button tilfoejButton = new Button("Tilføj Deadline");
        tilfoejButton.getStyleClass().add("button-tilfoej");
        tilfoejButton.setOnAction(e -> PopUps.tilføjDeadline("Tilføj Deadline", "Lort"));

        Button fjernButton = new Button("Fjern Deadline");
        fjernButton.getStyleClass().add("button-fjern");

        HBox bottomRightHovedMenu = new HBox(20, tilfoejButton, fjernButton);

        Label værelsesudlejningLabel = new Label("Værelser til udlejning:");
        værelsesudlejningLabel.getStyleClass().add("label-hovedmenu");

        TableColumn<VaerelsesUdlejning, Integer> ledigeVærelser = new TableColumn<>("Værelse");
        ledigeVærelser.setCellValueFactory(new PropertyValueFactory<>("værelse"));//Property need to match the class's field names

        TableColumn<VaerelsesUdlejning, java.util.Date> udlejningsdato = new TableColumn<>("Overtagelses-\ndato:");
        udlejningsdato.setCellValueFactory(new PropertyValueFactory<>("udlejningsdato"));//Property need to match the class's field names

        TableView ledigeVærelserTableView = new TableView(); // DEN HER ER UNDER TAB NUMMER 1
        ledigeVærelserTableView.setMaxSize(430, 225);
        ledigeVærelserTableView.setItems(getVærelsesUdlejning());
        ledigeVærelserTableView.getColumns().addAll(udlejningsdato, ledigeVærelser);

        //TAB 2
        TableColumn<VaerelsesUdlejning, Integer> udlejedeVærelser = new TableColumn<>("Værelse");
        udlejedeVærelser.setCellValueFactory(new PropertyValueFactory<>("værelse"));//Property need to match the class's field names

        TableColumn<VaerelsesUdlejning, java.util.Date> udlejningsdato1 = new TableColumn<>("Overtagelses-\ndato:");
        udlejningsdato1.setCellValueFactory(new PropertyValueFactory<>("udlejningsdato"));//Property need to match the class's field names
        udlejningsdato1.setMaxWidth(110);

        TableColumn<VaerelsesUdlejning, java.util.Date> behandlingsdato = new TableColumn<>("Behandlingsdato");
        behandlingsdato.setCellValueFactory(new PropertyValueFactory<>("behandlingsdato"));//Property need to match the class's field names

        TableColumn<VaerelsesUdlejning, Integer> behandlerInitialer = new TableColumn<>("Behandler\nInitialer");
        behandlerInitialer.setSortable(false); // Fjerner mulighed for at sortere
        behandlerInitialer.setCellValueFactory(new PropertyValueFactory<>("behandlerInitialer"));//Property need to match the class's field names

        TableView udlejedeVærelserTableView = new TableView<>();
        udlejedeVærelserTableView.setMaxSize(430,217);
        udlejedeVærelserTableView.setItems(getVærelsesUdlejning());
        udlejedeVærelserTableView.getColumns().addAll(udlejedeVærelser,udlejningsdato1,behandlingsdato,behandlerInitialer);

        Button buttonTilføjLedigtVærelse = new Button("Tilføj Værelse");
        buttonTilføjLedigtVærelse.getStyleClass().add("button-tilfoej");
        Button buttonRedigerLedigtVærelse = new Button("Rediger Værelse");
        buttonRedigerLedigtVærelse.getStyleClass().add("button-fjern");
        HBox  bottomLeftLayoutTab1= new HBox();
        bottomLeftLayoutTab1.getChildren().addAll(buttonTilføjLedigtVærelse, buttonRedigerLedigtVærelse);
        bottomLeftLayoutTab1.setSpacing(20);
        bottomLeftLayoutTab1.setPadding(new Insets(20));

        Tab ledigeVærelserTab = new Tab("Ledige værelser");
        BorderPane tab1LedigtVærelseBorderPane = new BorderPane();
        tab1LedigtVærelseBorderPane.setCenter(ledigeVærelserTableView);
        tab1LedigtVærelseBorderPane.setBottom(bottomLeftLayoutTab1);
        tab1LedigtVærelseBorderPane.setPrefSize(430, 370);
        ledigeVærelserTab.setContent(tab1LedigtVærelseBorderPane);
        ledigeVærelserTab.setClosable(false);

        Tab udlejedeVærelserTab = new Tab("Udlejede\nVærelser");
        udlejedeVærelserTab.setContent(udlejedeVærelserTableView);
        udlejedeVærelserTab.setClosable(false);


        TabPane værelsesudlejningTabpane = new TabPane(ledigeVærelserTab, udlejedeVærelserTab);
        værelsesudlejningTabpane.setMaxSize(430,350);

        VBox allBottomLeftLayout = new VBox(10, værelsesudlejningLabel, værelsesudlejningTabpane);
        allBottomLeftLayout.setPadding(new Insets(10,0,0,0));

        VBox startMenuVenstre = new VBox(20, studiekontrolButton, beboerListeButton, formandButton, indstillingsSkabelonerButton, logoutButton);

        VBox bottomLeftHovedMenu = new VBox(allBottomLeftLayout);
        BorderPane bottomLayoutHovedMenu = new BorderPane();

        bottomLayoutHovedMenu.setLeft(bottomLeftHovedMenu);

        VBox bottomRightLayout = new VBox(deadlineLabel, deadlinesView, bottomRightHovedMenu);
        bottomRightLayout.setPadding(new Insets(10));
        bottomRightLayout.setSpacing(20);
        bottomRightLayout.setPrefSize(430,400);
        bottomLayoutHovedMenu.setRight(bottomRightLayout);
        BorderPane startMenu = new BorderPane();
        startMenu.setLeft(startMenuVenstre);

        VBox startMenuHøjre = new VBox(20,kalenderLabel, calendarControl);
        startMenuHøjre.setPrefSize(410,600); // HER STYRES STØRRELSE af CALENDARCONTROL
        startMenu.setRight(startMenuHøjre);
        startMenu.setBottom(bottomLayoutHovedMenu);


        startMenu.setPadding(new Insets(50));
        sceneStart = new Scene(startMenu, 1000, 900);
        sceneStart.getStylesheets().add("Layout.css");

        //StudiekontrolMenu!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //Buttons
        Button button1 = new Button("Tilbage til Menu");
        button1.setOnAction(e -> window.setScene(sceneStart));

        Button opdaterBeboerButton = new Button("Opdater\nBeboerInfo");
        opdaterBeboerButton.getStyleClass().add("button-opdater-medlem");
        opdaterBeboerButton.setOnAction(e -> PopUps.opdaterBeboerInfo(conn));
        opdaterBeboerButton.setPrefSize(172, 105);

        Button påbegyndStudiekontrolButton = new Button("Påbegynd\nstudiekontrol");
        påbegyndStudiekontrolButton.getStyleClass().add("button-paabegynd-studiekontrol");
        påbegyndStudiekontrolButton.setOnAction(e -> PopUps.påbegyndStudieKontrol("Påbegynd Studiekontrol"));

        Button redigerAfslutStudiekontrolButton = new Button("Rediger/afslut\nStudiekontrol");
        redigerAfslutStudiekontrolButton.getStyleClass().add("button-rediger-afslut-studiekontrol");
        redigerAfslutStudiekontrolButton.setOnAction(e -> PopUps.redigerAfslutStudiekontrol("button-rediger-afslut-studiekontrol"));
        redigerAfslutStudiekontrolButton.setPrefSize(172, 105);

        //menubar and Menu
        Menu menuHelp = new Menu("_Hjælp");
        Menu menuVis = new Menu("_Vis");
        MenuBar menuBar = new MenuBar(menuVis, menuHelp);

        //TableView med beboerinformationer

        TableView<Beboer> studiekontrolBeboerListe = new TableView<>();
        visTableView(studiekontrolBeboerListe);

        //Center Layout of StudiekontrolMenu
        TabPane centerLayout = new TabPane();
        centerLayout.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        Tab tabAlle = new Tab("Igangværende\nStudiekontroller");
        Tab tab1 = new Tab("Januar");
        Tab tab2 = new Tab("Februar");
        Tab tab3 = new Tab("Marts");
        Tab tab4 = new Tab("April");
        Tab tab5 = new Tab("Maj");
        Tab tab6 = new Tab("Juni");
        Tab tab7 = new Tab("Juli");
        Tab tab8 = new Tab("August");
        Tab tab9 = new Tab("September");
        Tab tab10 = new Tab("Oktober");
        Tab tab11 = new Tab("November");
        Tab tab12 = new Tab("December");
        centerLayout.setTabMinWidth(60);
        tabAlle.setContent(studiekontrolBeboerListe);

        tabAlle.getStyleClass().add("tab-studiekontrol");
        centerLayout.getTabs().addAll(tabAlle, tab1, tab2, tab3, tab4, tab5, tab6, tab7, tab8, tab9, tab10, tab11, tab12);

        //Left Layout of StudiekontrolMenu
        VBox leftLayout = new VBox(10, button1);
        leftLayout.setPadding(new Insets(10));


        //Right Layout of StudiekontrolMenu
        VBox rightLayout = new VBox(15, opdaterBeboerButton, påbegyndStudiekontrolButton, redigerAfslutStudiekontrolButton);
        rightLayout.setPadding(new Insets(20));

        //TopLayout of StudiekontrolMenu
        HBox topLayout = new HBox(menuBar);

        //Bottomlayout of StudiekontrolMenu
        HBox bottomLayout = new HBox();
        bottomLayout.setPadding(new Insets(20));

        //Main layout
        BorderPane borderPane = new BorderPane();

        borderPane.setLeft(leftLayout);
        borderPane.setBottom(bottomLayout);
        borderPane.setTop(topLayout);
        borderPane.setRight(rightLayout);
        borderPane.setCenter(centerLayout);

        sceneStudiekontrol = new Scene(borderPane);
        sceneStudiekontrol.getStylesheets().add("Layout.css");


        //BEBOERLISTE MENU
        //Buttons
        Button button2 = new Button("Tilbage til Menu");
        button2.setOnAction(e -> window.setScene(sceneStart));

        Button opretNyBeboerButton = new Button("Opret ny\nbeboer");
        opretNyBeboerButton.getStyleClass().add("button-opdater-medlem");
        opretNyBeboerButton.setMinWidth(150);
        opretNyBeboerButton.setOnAction(e -> PopUps.opretBeboer(conn, beboerListe, beboerData));

        Button opdaterBeboerButton2 = new Button("Opdater \nBeboerinfo");
        opdaterBeboerButton2.getStyleClass().add("button-paabegynd-studiekontrol");
        opdaterBeboerButton2.setOnAction(e -> PopUps.opdaterBeboerInfo(conn));

        //menubar and Menu
        Menu menuHelpBeboerListe = new Menu("_Hjælp");
        Menu menuVisBeboerliste = new Menu("_Vis");
        MenuBar menuBarbeboerListe = new MenuBar(menuVisBeboerliste, menuHelpBeboerListe);

        //TableView med beboerinformationer
        visTableView(beboerListe);

        //Center Layout of BeboerListeMenu
        TabPane centerBeboerlisteLayout = new TabPane();
        centerBeboerlisteLayout.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        centerBeboerlisteLayout.setPrefSize(800, 500);
        Tab tabAlleBeboere = new Tab("Alle beboere");
        Tab tab2Sal = new Tab("2. sal");
        Tab tab3Sal = new Tab("3. sal");
        Tab tab4Sal = new Tab("4. sal");
        Tab tab5Sal = new Tab("5. sal");
        Tab tab6Sal = new Tab("6. sal");

        centerBeboerlisteLayout.setTabMinWidth(60);
        tabAlleBeboere.setContent(visAlleBeboer());
        tab2Sal.setContent(visAndenSal());
        tab3Sal.setContent(visTredjeSal());
        tab4Sal.setContent(visFjerdeSal());
        tab5Sal.setContent(visFemteSal());
        tab6Sal.setContent(visSjetteSal());
        centerBeboerlisteLayout.getTabs().addAll(tabAlleBeboere, tab2Sal, tab3Sal, tab4Sal, tab5Sal, tab6Sal);

        //Left Layout of beboerListeMenu
        VBox leftBeboerListeLayout = new VBox(10, button2);
        leftBeboerListeLayout.setPadding(new Insets(10));


        //Right Layout of beboerlistemenu
        VBox rightBeboerListeLayout = new VBox(15, opretNyBeboerButton, opdaterBeboerButton2);
        rightBeboerListeLayout.setPadding(new Insets(20));

        //TopLayout of BeboerListeMenu
        HBox topBeboerlisteLayout = new HBox(menuBarbeboerListe);

        //Bottomlayout of beboerListeMenu
        HBox bottomBeboerListeLayout = new HBox();
        bottomBeboerListeLayout.setPadding(new Insets(20));

        //Main layout Beboerliste
        BorderPane beboerListeBorderPane = new BorderPane();

        beboerListeBorderPane.setLeft(leftBeboerListeLayout);
        beboerListeBorderPane.setBottom(bottomBeboerListeLayout);
        beboerListeBorderPane.setTop(topBeboerlisteLayout);
        beboerListeBorderPane.setRight(rightBeboerListeLayout);
        beboerListeBorderPane.setCenter(centerBeboerlisteLayout);

        sceneBeboerListe = new Scene(beboerListeBorderPane);
        sceneBeboerListe.getStylesheets().add("Layout.css");

        //FORMAND MENU
        //Venstre side af menuen.
        Button tilbageButton = new Button("Tilbage til Hovedmenu");
        tilbageButton.setOnAction(e -> window.setScene(sceneStart));
        VBox leftLayoutFormand = new VBox(20, tilbageButton);

        //Højre side af menuen.
        Label labeltest = new Label("");
        VBox rightLayoutFormand = new VBox(labeltest); // Skal sørge for højre Pane er der.

        //Centrale del af FormandMenu

        //TabVelkommen
        Label hejLabel = new Label("Hej, I denne Tab skal der være en 'intro' til dem\n der ikke er formand og derfor ikke benytter denne menu normalt");

        Tab velkommenTab = new Tab("Velkommen");
        velkommenTab.setClosable(false);
        velkommenTab.setContent(hejLabel);

        //TabDispensation
        Button redigerDispensationButton = new Button("Rediger i\nDispensation\n (Kun for formanden)");
        redigerDispensationButton.getStyleClass().add("button-rediger-dispensation");

        Button fjernDispensationButton = new Button("Fjern \nDispensation\n(Kun for formanden)");
        fjernDispensationButton.getStyleClass().add("button-afslut-dispensation");

        Button svarPåDispensationButton = new Button("Svar på\nDispensationsansøgning\n(kun for formanden)");
        svarPåDispensationButton.getStyleClass().add("button-svar-paa-dispensation");
        svarPåDispensationButton.setPrefSize(240, 135);

        HBox dispensationTabCenterLayout = new HBox(50, redigerDispensationButton, fjernDispensationButton, svarPåDispensationButton);
        dispensationTabCenterLayout.setPadding(new Insets(40, 30, 20, 120));


        Label dispensationLayoutLabel = new Label("Igangværende og kommende dispensationer: ");
        dispensationLayoutLabel.getStyleClass().add("label-hovedmenu");

        TableColumn<Dispensation, Integer> værelseDispensation = new TableColumn<>("Værelse");
        værelseDispensation.setMaxWidth(70);
        værelseDispensation.setCellValueFactory(new PropertyValueFactory<>("værelse"));//Property need to match the class's field names

        TableColumn<Dispensation, String> navnDispensation = new TableColumn<>("Navn");
        navnDispensation.setMaxWidth(200);
        navnDispensation.setCellValueFactory(new PropertyValueFactory<>("navn"));//Property need to match the class's field names

        TableColumn<Dispensation, java.util.Date> startDatoDispensation = new TableColumn<>("Dispensation\nstarter d.");
        startDatoDispensation.setMaxWidth(100);
        startDatoDispensation.setCellValueFactory(new PropertyValueFactory<>("startDato"));//Property need to match the class's field names

        TableColumn<Dispensation, String> deadline1Dispensation = new TableColumn<>("Deadline 1");
        deadline1Dispensation.setMaxWidth(250);
        deadline1Dispensation.setCellValueFactory(new PropertyValueFactory<>("deadline1"));//Property need to match the class's field names

        TableColumn<Dispensation, String> deadline2Dispensation = new TableColumn<>("Deadline 2");
        deadline2Dispensation.setMaxWidth(250);
        deadline2Dispensation.setCellValueFactory(new PropertyValueFactory<>("deadline2"));//Property need to match the class's field names

        TableColumn<Dispensation, String> deadline3Dispensation = new TableColumn<>("Deadline 3");
        deadline3Dispensation.setMaxWidth(250);
        deadline3Dispensation.setCellValueFactory(new PropertyValueFactory<>("deadline3"));//Property need to match the class's field names

        TableColumn<Dispensation, java.util.Date> ophørsDatoDispensation = new TableColumn<>("Dispensation\nOphører d.");
        ophørsDatoDispensation.setMaxWidth(100);
        ophørsDatoDispensation.setCellValueFactory(new PropertyValueFactory<>("ophørsDato"));//Property need to match the class's field names

        TableView dispensationsView = new TableView();
        dispensationsView.setItems(getDispensation());
        dispensationsView.getColumns().addAll(værelseDispensation,navnDispensation,startDatoDispensation,deadline1Dispensation, deadline2Dispensation, deadline3Dispensation, ophørsDatoDispensation);
        dispensationsView.setMaxSize(1080, 400);

        VBox dispensationsTabMainLayout = new VBox(10, dispensationLayoutLabel, dispensationsView, dispensationTabCenterLayout);
        dispensationsTabMainLayout.setPadding(new Insets(20));

        Tab dispensationTab = new Tab("Dispensation");
        dispensationTab.setClosable(false);
        dispensationTab.setContent(dispensationsTabMainLayout);

        //FremlejeTab på FormandsMenu
        Button redigerFremlejeButton = new Button("Rediger i\nFremleje\n (Kun for formanden)");
        redigerFremlejeButton.getStyleClass().add("button-rediger-dispensation");

        Button fjernFremlejeButton = new Button("Fjern \nDispensation\n(Kun for formanden)");
        fjernFremlejeButton.getStyleClass().add("button-afslut-dispensation");

        Button svarPåFremlejeButton = new Button("Svar på\nFremlejeansøgning\n(kun for formanden)");
        svarPåFremlejeButton.getStyleClass().add("button-svar-paa-dispensation");
        svarPåFremlejeButton.setPrefSize(240, 135);

        HBox fremlejeTabCenterLayout = new HBox(50, redigerFremlejeButton,fjernFremlejeButton, svarPåFremlejeButton);
        fremlejeTabCenterLayout.setPadding(new Insets(40, 30, 20, 120));


        Label fremlejeLayoutLabel = new Label("Igangværende og kommende Fremlejer: ");
        fremlejeLayoutLabel.getStyleClass().add("label-hovedmenu");

        TableColumn<Fremleje, Integer> værelseFremleje = new TableColumn<>("Værelse");
        værelseFremleje.setMaxWidth(70);
        værelseFremleje.setMinWidth(70);
        værelseFremleje.setCellValueFactory(new PropertyValueFactory<>("værelse"));//Property need to match the class's field names

        TableColumn<Dispensation, String> fremlejetagerFremleje = new TableColumn<>("Fremlejetager");
        fremlejetagerFremleje.setMaxWidth(300);
        fremlejetagerFremleje.setMinWidth(300);
        fremlejetagerFremleje.setCellValueFactory(new PropertyValueFactory<>("fremlejetager"));//Property need to match the class's field names

        TableColumn<Fremleje, java.util.Date> startDatoFremleje = new TableColumn<>("Fremlejens\nstart:");
        startDatoFremleje.setMaxWidth(125);
        startDatoFremleje.setMinWidth(125);
        startDatoFremleje.setCellValueFactory(new PropertyValueFactory<>("startDato"));//Property need to match the class's field names

        TableColumn<Fremleje, String> fremlejerFremleje = new TableColumn<>("Fremlejer\nNavn");
        fremlejerFremleje.setMaxWidth(300);
        fremlejerFremleje.setMinWidth(300);
        fremlejerFremleje.setCellValueFactory(new PropertyValueFactory<>("fremlejer"));//Property need to match the class's field names

        TableColumn<Fremleje, java.util.Date> slutDatoFremleje = new TableColumn<>("Fremleje\nSlutter");
        slutDatoFremleje.setMaxWidth(125);
        slutDatoFremleje.setMinWidth(125);
        slutDatoFremleje.setCellValueFactory(new PropertyValueFactory<>("slutDato"));//Property need to match the class's field names

        TableView fremlejeView = new TableView();
        fremlejeView.setItems(getFremleje());
        fremlejeView.getColumns().addAll(værelseFremleje,startDatoFremleje, fremlejetagerFremleje, slutDatoFremleje, fremlejerFremleje);
        fremlejeView.setMaxSize(1080, 400);

        VBox fremlejeTabMainLayout = new VBox(10, fremlejeLayoutLabel, fremlejeView, fremlejeTabCenterLayout);
        fremlejeTabMainLayout.setPadding(new Insets(20));

        Tab fremlejeTab = new Tab("Fremleje");
        fremlejeTab.setClosable(false);
        fremlejeTab.setContent(fremlejeTabMainLayout);

        //KlagerTab på Formanden
        //Layout 1
        Label loginForKlagerLabel = new Label("Log ind for at se klager");
        loginForKlagerLabel.getStyleClass().add("label-login");
        Label brugernavnLabel = new Label("Brugernavn: ");
        TextField textBrugerNavn = new TextField();
        Label passwordLabel = new Label("Password: ");
        TextField textPassword = new TextField();
        Button loginButton = new Button("Se Klager");//Hvis Password og brugernavn passer kan man fortsætte til næste layout

        loginButton.getStyleClass().add("button-login");
        GridPane loginTilKlagerGridPane = new GridPane();

        loginTilKlagerGridPane.add(loginForKlagerLabel,2,1);
        loginTilKlagerGridPane.add(brugernavnLabel, 2,2);
        loginTilKlagerGridPane.add(textBrugerNavn,2,3);
        loginTilKlagerGridPane.add(passwordLabel, 2,5);
        loginTilKlagerGridPane.add(textPassword,2,6);
        loginTilKlagerGridPane.add(loginButton,3,6);
        loginTilKlagerGridPane.setPadding(new Insets(20,30,30,30));
        loginTilKlagerGridPane.setHgap(5);
        loginTilKlagerGridPane.setVgap(15);
        loginTilKlagerGridPane.getStyleClass().add("root-login");
        loginTilKlagerGridPane.setMaxHeight(210);

        Button opretKlageButton = new Button("Opret\nKlage");
        opretKlageButton.getStyleClass().add("button-klage");
        opretKlageButton.setPrefSize(240,120);

        HBox klagerTabMainLayout1 = new HBox(10, loginTilKlagerGridPane, opretKlageButton);
        klagerTabMainLayout1.setPadding(new Insets(20));
        Tab klagerTab = new Tab("Klager");
        klagerTab.setClosable(false);
        klagerTab.setContent(klagerTabMainLayout1);

        //Layout2
        Label klageOverskrift1 = new Label("Klageoversigt:");
        TableView klagerTableView = new TableView();//Navn, værelse, antal klager

        Button opretKlageButton2 = new Button("Opret\nKlage");
        opretKlageButton2.getStyleClass().add("button-klage");
        opretKlageButton2.setPrefSize(240,120);

        VBox leftLayoutKlagerTab2 = new VBox(10, klageOverskrift1, klagerTableView);
        HBox klagerTabMainLayout2 = new HBox(20,leftLayoutKlagerTab2, opretKlageButton2);

        loginButton.setOnAction(e->klagerTab.setContent(klagerTabMainLayout2));//Hører til KlageTab for Formand

        Tab protokolTab = new Tab("Protokol");
        protokolTab.setClosable(false);

        TabPane centerLayoutFormand = new TabPane();
        centerLayoutFormand.getTabs().addAll(velkommenTab, dispensationTab, fremlejeTab, klagerTab, protokolTab);

        BorderPane formandBorderPane = new BorderPane();
        formandBorderPane.setLeft(leftLayoutFormand);
        formandBorderPane.setCenter(centerLayoutFormand);
        formandBorderPane.setRight(rightLayoutFormand);
        formandBorderPane.setPrefSize(1450,900);
        sceneFormand = new Scene(formandBorderPane);
        sceneFormand.getStylesheets().add("Layout.css");

        //Afsluttende - sætter scenen

        window.setScene(sceneStart);
        window.show();
    }
    private void loginAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setTitle("Login Status");
        alert.setContentText("Login Failed. Try again.");
        alert.show();
    }
    private void logoutAlert(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Logud");
        alert.setContentText("Er du sikker på du vil log ud ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            window.setScene(sceneLogin);
        }
        else{
            alert.close();
        }
    }
    public ObservableList<Beboer> getBeboer() {
        ObservableList<Beboer> beboer = FXCollections.observableArrayList();
        return beboer;
    }
    public ObservableList<Deadline> getReminder() {
        ObservableList<Deadline> reminder = FXCollections.observableArrayList();// DER SKAL INKLUDERES ET LINJESKIFT EFER XXXX ANTAL ANSLAG
        reminder.add(new Deadline("01/02/2017", "407 skal aflevere\n studiekontrol her"));
        return reminder;
    }
    public ObservableList<VaerelsesUdlejning> getVærelsesUdlejning() {
        ObservableList<VaerelsesUdlejning> værelsesUdlejning = FXCollections.observableArrayList();
        //værelsesUdlejning.add(new VaerelsesUdlejning(404,new java.util.Date(2017,10,29)));
        return værelsesUdlejning;
    }
    public ObservableList<Dispensation> getDispensation(){
        ObservableList<Dispensation> dispensation = FXCollections.observableArrayList();
        dispensation.add(new Dispensation(401,"Mette",new java.util.Date(2018,10,01),new java.util.Date(2017,10,01), "Beboeren skal 19/01 dokumentere afsluttet studie", "Beboeren skal 20/01 dokumentere at have søgt studie", "Beboeren skal 21/01 her dokumentere optag på nyt studie."));
        return dispensation;
    }
    public ObservableList<Fremleje> getFremleje() {
        ObservableList<Fremleje> fremleje = FXCollections.observableArrayList();
        fremleje.add(new Fremleje(422, "Mette Frederiksen", "Jesper mikkelsen", new java.util.Date(2017, 10, 1), new java.util.Date(2017, 12, 31)));
        return fremleje;
    }

    public TableView visAndenSal()
    {
        TableView<Beboer> beboerListe = new TableView<>();
        final ObservableList<Beboer> beboerData = FXCollections.observableArrayList();

        visTableView(beboerListe);

        beboerData.clear();
        try{
            String sql = "SELECT * FROM Beboer WHERE VaerelseNR BETWEEN 200 AND 300";
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                beboerData.add(new Beboer(
                        resultSet.getInt("VaerelseNr"),
                        resultSet.getString("Navn"),
                        resultSet.getDate("Indflytningsdato"),
                        resultSet.getString("Uddannelsested"),
                        resultSet.getDate("Uddanelsesstart"),
                        resultSet.getDate("Uddannelseafsluttes"),
                        resultSet.getString("Uddannelseretning"),
                        resultSet.getString("Email")
                ));
                beboerListe.setItems(beboerData);
            }
            //preparedStatement.close();
            //resultSet.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return beboerListe;

    }
    public TableView visTredjeSal()
    {
        TableView<Beboer> beboerListe = new TableView<>();
        final ObservableList<Beboer> beboerData = FXCollections.observableArrayList();

        visTableView(beboerListe);

        beboerData.clear();
        try{
            String sql = "SELECT * FROM Beboer WHERE VaerelseNR BETWEEN 300 AND 400";
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                beboerData.add(new Beboer(
                        resultSet.getInt("VaerelseNr"),
                        resultSet.getString("Navn"),
                        resultSet.getDate("Indflytningsdato"),
                        resultSet.getString("Uddannelsested"),
                        resultSet.getDate("Uddanelsesstart"),
                        resultSet.getDate("Uddannelseafsluttes"),
                        resultSet.getString("Uddannelseretning"),
                        resultSet.getString("Email")
                ));
                beboerListe.setItems(beboerData);
            }
            //preparedStatement.close();
            //resultSet.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return beboerListe;

    }
    public TableView visFjerdeSal()
    {
        TableView<Beboer> beboerListe = new TableView<>();
        final ObservableList<Beboer> beboerData = FXCollections.observableArrayList();

        visTableView(beboerListe);

        beboerData.clear();
        try{
            String sql = "SELECT * FROM Beboer WHERE VaerelseNR BETWEEN 400 AND 500";
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                beboerData.add(new Beboer(
                        resultSet.getInt("VaerelseNr"),
                        resultSet.getString("Navn"),
                        resultSet.getDate("Indflytningsdato"),
                        resultSet.getString("Uddannelsested"),
                        resultSet.getDate("Uddanelsesstart"),
                        resultSet.getDate("Uddannelseafsluttes"),
                        resultSet.getString("Uddannelseretning"),
                        resultSet.getString("Email")
                ));
                beboerListe.setItems(beboerData);
            }
            //preparedStatement.close();
            //resultSet.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return beboerListe;

    }
    public TableView visFemteSal()
    {
        TableView<Beboer> beboerListe = new TableView<>();
        final ObservableList<Beboer> beboerData = FXCollections.observableArrayList();

        visTableView(beboerListe);

        beboerData.clear();
        try{
            String sql = "SELECT * FROM Beboer WHERE VaerelseNR BETWEEN 500 AND 600";
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                beboerData.add(new Beboer(
                        resultSet.getInt("VaerelseNr"),
                        resultSet.getString("Navn"),
                        resultSet.getDate("Indflytningsdato"),
                        resultSet.getString("Uddannelsested"),
                        resultSet.getDate("Uddanelsesstart"),
                        resultSet.getDate("Uddannelseafsluttes"),
                        resultSet.getString("Uddannelseretning"),
                        resultSet.getString("Email")
                ));
                beboerListe.setItems(beboerData);
            }
            //preparedStatement.close();
            //resultSet.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return beboerListe;

    }
    public TableView visSjetteSal()
    {
        TableView<Beboer> beboerListe = new TableView<>();
        final ObservableList<Beboer> beboerData = FXCollections.observableArrayList();

        visTableView(beboerListe);

        beboerData.clear();
        try{
            String sql = "SELECT * FROM Beboer WHERE VaerelseNR BETWEEN 600 AND 700";
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                beboerData.add(new Beboer(
                        resultSet.getInt("VaerelseNr"),
                        resultSet.getString("Navn"),
                        resultSet.getDate("Indflytningsdato"),
                        resultSet.getString("Uddannelsested"),
                        resultSet.getDate("Uddanelsesstart"),
                        resultSet.getDate("Uddannelseafsluttes"),
                        resultSet.getString("Uddannelseretning"),
                        resultSet.getString("Email")
                ));
                beboerListe.setItems(beboerData);
            }
            //preparedStatement.close();
            //resultSet.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return beboerListe;

    }
    public TableView visAlleBeboer()
    {
        beboerListe = new TableView<>();
        final ObservableList<Beboer> beboerData = FXCollections.observableArrayList();

        beboerData.clear();
        visTableView(beboerListe);

        try{
            String sql = "SELECT * FROM Beboer";
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                beboerData.add(new Beboer(
                        resultSet.getInt("VaerelseNr"),
                        resultSet.getString("Navn"),
                        resultSet.getDate("Indflytningsdato"),
                        resultSet.getString("Uddannelsested"),
                        resultSet.getDate("Uddanelsesstart"),
                        resultSet.getDate("Uddannelseafsluttes"),
                        resultSet.getString("Uddannelseretning"),
                        resultSet.getString("Email")
                ));
                beboerListe.setItems(beboerData);
            }
            //preparedStatement.close();
            //resultSet.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return beboerListe;

    }
    // Viser tabledview med alle bebeor informationer.
    public void visTableView(TableView beboerListe){

        TableColumn<Beboer, Integer> værelseBeboerListe = new TableColumn<>("Vaerelse");
        værelseBeboerListe.setMinWidth(100);
        værelseBeboerListe.setCellValueFactory(new PropertyValueFactory<>("vaerelseNr"));//Property need to match the class's field names

        TableColumn<Beboer, String> navnBeboerListe = new TableColumn<>("Navn");
        navnBeboerListe.setMinWidth(100);
        navnBeboerListe.setCellValueFactory(new PropertyValueFactory<>("navn"));//Property need to match the class's field names

        TableColumn<Beboer, Date> indflytningsdatoBeboerliste = new TableColumn<>("Indflytningsdato");
        indflytningsdatoBeboerliste.setMinWidth(100);
        indflytningsdatoBeboerliste.setCellValueFactory(new PropertyValueFactory<>("indflytdato"));//Property need to match the class's field names

        TableColumn<Beboer, String> institutionBeboerListe = new TableColumn<>("Uddannelses-\ninstitution");
        institutionBeboerListe.setMinWidth(100);
        institutionBeboerListe.setCellValueFactory(new PropertyValueFactory<>("uddannelsested"));//Property need to match the class's field names

        TableColumn<Beboer, Date> påbegyndtUddannelseBeboerListe = new TableColumn<>("Uddannelse\nPåbegyndt:");
        påbegyndtUddannelseBeboerListe.setMinWidth(100);
        påbegyndtUddannelseBeboerListe.setCellValueFactory(new PropertyValueFactory<>("uddannelsestart"));//Property need to match the class's field names

        TableColumn<Beboer, Date> uddannelseAfsluttesBeboerListe = new TableColumn<>("Uddannelse\nforventes\nafsluttet: ");
        uddannelseAfsluttesBeboerListe.setMinWidth(100);
        uddannelseAfsluttesBeboerListe.setCellValueFactory(new PropertyValueFactory<>("uddannelseafslut"));//Property need to match the class's field names

        TableColumn<Beboer, String> uddannelsesRetningBeboerListe = new TableColumn<>("Uddannelsesretning");
        uddannelsesRetningBeboerListe.setMinWidth(100);
        uddannelsesRetningBeboerListe.setCellValueFactory(new PropertyValueFactory<>("uddannelseretning"));//Property need to match the class's field names

        TableColumn<Beboer, String> emailBeboerListe = new TableColumn<>("Email");
        emailBeboerListe.setMinWidth(100);
        emailBeboerListe.setCellValueFactory(new PropertyValueFactory<>("email"));

        beboerListe.setItems(getBeboer());
        beboerListe.getColumns().addAll(værelseBeboerListe, navnBeboerListe, indflytningsdatoBeboerliste, institutionBeboerListe, påbegyndtUddannelseBeboerListe, uddannelseAfsluttesBeboerListe, uddannelsesRetningBeboerListe, emailBeboerListe);

    }

}

