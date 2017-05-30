package View;

import Controller.SkabelonController;
import Model.*;
import Controller.LoginFunktion;

import com.sun.javafx.scene.control.skin.DatePickerSkin;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.Date;

/**
 * Created by Herlev Kollegiet Udvikler on 07-05-2017..
 */
public class Main extends Application
{
    Stage window;
    Scene sceneLogin, sceneStart, sceneStudiekontrol, sceneBeboerListe, sceneFormand, sceneIndstillingsskabeloner;
    DBConnection dbConnection = new DBConnection();
    LoginFunktion loginFunk = new LoginFunktion();
    PopUpsMenues popUpsMenues = new PopUpsMenues();
    TableViews tableViews = new TableViews();
    SQL_TableViewData sql_tableViewData  = new SQL_TableViewData();
    SkabelonController skabelonController = new SkabelonController();
    AlertBoxes alertBoxes = new AlertBoxes();
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
        window.getIcons().add(new Image("HeKoLogo.png"));

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
                   Log.insertIntoLog(nameInput.getText()+" logget ind");

                }catch (Exception e){
                    e.printStackTrace();
                }
            }else{
                alertBoxes.loginAlert();
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
                    Log.insertIntoLog(nameInput.getText()+" logget ind");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else{
                alertBoxes.loginAlert();
            }
        });

        window.setScene(sceneLogin);
        window.show();
    }
    // HOVEDMENU START
    public void startHovedMenu(Stage primaryStage)
    {
        conn = dbConnection.getDBConnection();
        dbConnection.checkDBConnection();

        window = primaryStage;
        window.setTitle("Herlevkollegiets Indstillingsudvalg");
        window.getIcons().add(new Image("HeKoLogo.png")); // HER SKAL HEKO LOGO VÆRE

        //VENSTRE DEL AF HOVEDMENUEN
        //StudieKontrol-Button.
        Button studiekontrolButton = new Button("Studiekontrol");
        studiekontrolButton.getStyleClass().add("button-hovedmenu");
        studiekontrolButton.setOnAction(e -> {
            window.setScene(sceneStudiekontrol);
            sql_tableViewData.visStudieKontrolBeboer(conn);
        });
        //Beboer-Liste Button.
        Button beboerListeButton = new Button("Beboer-Liste");
        beboerListeButton.getStyleClass().add("button-hovedmenu");
        beboerListeButton.setOnAction((ActionEvent event) -> {
            window.setScene(sceneBeboerListe);
            sql_tableViewData.visAlleBeboer(conn);
        });
        //Formand Button.
        Button formandButton = new Button("Formanden");
        formandButton.getStyleClass().add("button-hovedmenu");
        formandButton.setOnAction(e-> window.setScene(sceneFormand));

        //Indstllingsskabeloner Button.
        Button indstillingsSkabelonerButton = new Button("Indstillingsskabeloner");
        indstillingsSkabelonerButton.getStyleClass().add("button-hovedmenu");
        indstillingsSkabelonerButton.setOnAction(e-> window.setScene(sceneIndstillingsskabeloner));

        //Logud Button.
        Button logoutButton = new Button("Log ud");
        logoutButton.getStyleClass().add("button-hovedmenu");
        logoutButton.setOnAction(e ->{
            try {
                logoutAlert();
                Log.insertIntoLog("Bruger Logget ud.");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        //HØJRE DEL AF HOVEDMENUEN
        Label kalenderLabel = new Label("Kalender");
        kalenderLabel.getStyleClass().add("label-hovedmenu");
        //Method displays popupcontent of calendar instead of it being a pop-up.
        DatePickerSkin skin = new DatePickerSkin(new DatePicker(LocalDate.now()));
        Node calendarControl = skin.getPopupContent();
        calendarControl.getStyleClass().add("date-picker");

        Label deadlineLabel = new Label("Deadlines: ");
        deadlineLabel.getStyleClass().add("label-hovedmenu");

        TableView<Deadline> deadlineTableView = new TableView<>();
        tableViews.visDeadlineTableView(deadlineTableView);

        //Buttons tilføjes til HBox for horisontal placering
        Button tilfoejButton = new Button("Tilføj Deadline");
        tilfoejButton.getStyleClass().add("button-tilfoej");
        tilfoejButton.setOnAction(e -> popUpsMenues.tilføjDeadline("Tilføj Deadline", "Lort"));

        Button fjernButton = new Button("Fjern Deadline");
        fjernButton.getStyleClass().add("button-fjern");

        HBox bottomRightHovedMenu = new HBox(20, tilfoejButton, fjernButton);

        Label værelsesudlejningLabel = new Label("Værelser til udlejning:");
        værelsesudlejningLabel.getStyleClass().add("label-hovedmenu");

        TableView<VaerelsesUdlejning> vaerelsesUdlejningTableView = new TableView<>();
        tableViews.visLedigeVaerelserTableView(vaerelsesUdlejningTableView);
        //TAB 2
        tableViews.visVaerelsesUdlejningTableVew(vaerelsesUdlejningTableView);

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
        tab1LedigtVærelseBorderPane.setCenter(vaerelsesUdlejningTableView);
        tab1LedigtVærelseBorderPane.setBottom(bottomLeftLayoutTab1);
        tab1LedigtVærelseBorderPane.setPrefSize(430, 370);
        ledigeVærelserTab.setContent(tab1LedigtVærelseBorderPane);
        ledigeVærelserTab.setClosable(false);

        Tab udlejedeVærelserTab = new Tab("Udlejede\nVærelser");
        udlejedeVærelserTab.setContent(vaerelsesUdlejningTableView);
        udlejedeVærelserTab.setClosable(false);

        TabPane værelsesudlejningTabpane = new TabPane(ledigeVærelserTab, udlejedeVærelserTab);
        værelsesudlejningTabpane.setMaxSize(430,350);

        VBox allBottomLeftLayout = new VBox(10, værelsesudlejningLabel, værelsesudlejningTabpane);
        allBottomLeftLayout.setPadding(new Insets(10,0,0,0));

        VBox startMenuVenstre = new VBox(20, studiekontrolButton, beboerListeButton, formandButton, indstillingsSkabelonerButton, logoutButton);

        VBox bottomLeftHovedMenu = new VBox(allBottomLeftLayout);
        BorderPane bottomLayoutHovedMenu = new BorderPane();

        bottomLayoutHovedMenu.setLeft(bottomLeftHovedMenu);

        VBox bottomRightLayout = new VBox(deadlineLabel, deadlineTableView, bottomRightHovedMenu);
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

        //STUDIEKONTROL-MENUEN
        //Tilbage til Menu-Button.
        Button tilbagebutton1 = new Button("Tilbage til Menu");
        tilbagebutton1.setOnAction(e -> window.setScene(sceneStart));

        //Opdater BeboerInfo-Button i Studiekontrol
        Button opdaterBeboerButton = new Button("Opdater\nBeboerInfo");
        opdaterBeboerButton.getStyleClass().add("button-opdater-medlem");
        opdaterBeboerButton.setOnAction(e -> popUpsMenues.opdaterStudieKontrolInfo(conn));
        opdaterBeboerButton.setPrefSize(172, 105);

        //Påbegynd studiekontrol-Button.
        Button påbegyndStudiekontrolButton = new Button("Påbegynd\nstudiekontrol");
        påbegyndStudiekontrolButton.getStyleClass().add("button-paabegynd-studiekontrol");
        påbegyndStudiekontrolButton.setOnAction(e -> {
            popUpsMenues.påbegyndStudieKontrol("Påbegynd Studiekontrol");

            });

        //Rediger/afslut studuikontrol-Button.
        Button redigerAfslutStudiekontrolButton = new Button("Rediger/afslut\nStudiekontrol");
        redigerAfslutStudiekontrolButton.getStyleClass().add("button-rediger-afslut-studiekontrol");
        redigerAfslutStudiekontrolButton.setOnAction(e -> popUpsMenues.redigerAfslutStudiekontrol("button-rediger-afslut-studiekontrol"));
        redigerAfslutStudiekontrolButton.setPrefSize(172, 105);

        //menubar and Menu
        Menu menuHelp = new Menu("_Hjælp");
        Menu menuVis = new Menu("_Vis");
        MenuBar menuBar = new MenuBar(menuVis, menuHelp);

       //Center Layout of StudiekontrolMenu
        TabPane centerLayout = new TabPane();
        centerLayout.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab tabAlle = new Tab("Igangværende\nStudiekontroller");
        Tab tabJan = new Tab("Januar");
        Tab tabFeb = new Tab("Februar");
        Tab tabMart = new Tab("Marts");
        Tab tabApr = new Tab("April");
        Tab tabMaj = new Tab("Maj");
        Tab tabJuni = new Tab("Juni");
        Tab tabJuli = new Tab("Juli");
        Tab tabAug = new Tab("August");
        Tab tabSep = new Tab("September");
        Tab tabOkt = new Tab("Oktober");
        Tab tabNov = new Tab("November");
        Tab tabDec = new Tab("December");

        centerLayout.setTabMinWidth(60);
        tabAlle.setContent(sql_tableViewData.visStudieKontrolBeboer(conn));
        tabJan.setContent(sql_tableViewData.visStudieKontrolMaaned(conn,"Godkendt", "Januar"));
        tabFeb.setContent(sql_tableViewData.visStudieKontrolMaaned(conn,"Godkendt", "Februar"));
        tabMart.setContent(sql_tableViewData.visStudieKontrolMaaned(conn,"Godkendt","Marts"));
        tabApr.setContent(sql_tableViewData.visStudieKontrolMaaned(conn,"Godkendt","April"));
        tabMaj.setContent(sql_tableViewData.visStudieKontrolMaaned(conn,"Godkendt","Maj"));
        tabJuni.setContent(sql_tableViewData.visStudieKontrolMaaned(conn,"Godkendt","Juni"));
        tabJuli.setContent(sql_tableViewData.visStudieKontrolMaaned(conn,"Godkendt","Juli"));
        tabAug.setContent(sql_tableViewData.visStudieKontrolMaaned(conn,"Godkendt","August"));
        tabSep.setContent(sql_tableViewData.visStudieKontrolMaaned(conn,"Godkendt","September"));
        tabOkt.setContent(sql_tableViewData.visStudieKontrolMaaned(conn,"Godkendt","Oktober"));
        tabNov.setContent(sql_tableViewData.visStudieKontrolMaaned(conn,"Godkendt","November"));
        tabDec.setContent(sql_tableViewData.visStudieKontrolMaaned(conn,"Godkendt","December"));

        tabAlle.getStyleClass().add("tab-studiekontrol");
        centerLayout.getTabs().addAll(tabAlle, tabJan, tabFeb, tabMart, tabApr, tabMaj, tabJuni, tabJuli, tabAug, tabSep, tabOkt, tabNov, tabDec);

        //Left Layout of StudiekontrolMenu
        VBox leftLayout = new VBox(10, tilbagebutton1);
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
        //Tilbage til Menu - Button.
        Button tilbageButton2 = new Button("Tilbage til Menu");
        tilbageButton2.setOnAction(e -> window.setScene(sceneStart));

        //Opret ny beboer - Button.
        Button opretNyBeboerButton = new Button("Opret ny\nbeboer");
        opretNyBeboerButton.getStyleClass().add("button-opdater-medlem");
        opretNyBeboerButton.setMinWidth(150);
        opretNyBeboerButton.setOnAction(event -> {
            popUpsMenues.opretBeboer(conn, beboerListe, beboerData);

        });
        //Opdater Beboerinfo - Button i Beboer-Liste Menu.
        Button opdaterBeboerButton2 = new Button("Opdater \nBeboerinfo");
        opdaterBeboerButton2.getStyleClass().add("button-paabegynd-studiekontrol");
        opdaterBeboerButton2.setOnAction(e -> popUpsMenues.opdaterBeboerInfo(conn));

        //menubar and Menu
        Menu menuHelpBeboerListe = new Menu("_Hjælp");
        Menu menuVisBeboerliste = new Menu("_Vis");
        MenuBar menuBarbeboerListe = new MenuBar(menuVisBeboerliste, menuHelpBeboerListe);

        //TableView med beboerinformationer
        beboerListe = new TableView<>();

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
        tabAlleBeboere.setContent(sql_tableViewData.visAlleBeboer(conn));
        //Sortetring efter værelses nummer med given intervaller...
        tab2Sal.setContent(sql_tableViewData.visSal(conn,200,300));
        tab3Sal.setContent(sql_tableViewData.visSal(conn,300,400));
        tab4Sal.setContent(sql_tableViewData.visSal(conn,400,500));
        tab5Sal.setContent(sql_tableViewData.visSal(conn,500,600));
        tab6Sal.setContent(sql_tableViewData.visSal(conn,600,700));
        centerBeboerlisteLayout.getTabs().addAll(tabAlleBeboere, tab2Sal, tab3Sal, tab4Sal, tab5Sal, tab6Sal);

        //Left Layout of beboerListeMenu
        VBox leftBeboerListeLayout = new VBox(10, tilbageButton2);
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
        leftLayoutFormand.setPadding(new Insets(20,0,0,0));

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
        redigerDispensationButton.setOnAction(e-> PopUpsMenues.redigerIDispensation("Rediger i dispensation"));

        Button fjernDispensationButton = new Button("Fjern \nDispensation\n(Kun for formanden)");
        fjernDispensationButton.getStyleClass().add("button-afslut-dispensation");

        Button svarPåDispensationButton = new Button("Svar på\nDispensationsansøgning\n(kun for formanden)");
        svarPåDispensationButton.setOnAction(e-> PopUpsMenues.svarPåDispensation("Svar på Dispensationsansøgning"));
        svarPåDispensationButton.getStyleClass().add("button-svar-paa-dispensation");
        svarPåDispensationButton.setPrefSize(240, 135);

        HBox dispensationTabCenterLayout = new HBox(50, redigerDispensationButton, fjernDispensationButton, svarPåDispensationButton);
        dispensationTabCenterLayout.setPadding(new Insets(40, 30, 20, 120));

        Label dispensationLayoutLabel = new Label("Igangværende og kommende dispensationer: ");
        dispensationLayoutLabel.getStyleClass().add("label-hovedmenu");

        TableView<Dispensation> dispensationsView = new TableView<>();
        tableViews.visDispensationsTableView(dispensationsView);

        VBox dispensationsTabMainLayout = new VBox(10, dispensationLayoutLabel, dispensationsView, dispensationTabCenterLayout);
        dispensationsTabMainLayout.setPadding(new Insets(20));

        Tab dispensationTab = new Tab("Dispensation");
        dispensationTab.setClosable(false);
        dispensationTab.setContent(dispensationsTabMainLayout);


        //FremlejeTab på FormandsMenu
        Button redigerFremlejeButton = new Button("Rediger i\nFremleje\n (Kun for formanden)");
        redigerFremlejeButton.getStyleClass().add("button-rediger-dispensation");
        redigerFremlejeButton.setOnAction(e-> PopUpsMenues.redigerIFremleje("Rediger i Ellers Godkendt fremleje"));

        Button fjernFremlejeButton = new Button("Fjern \nFremleje\n(Kun for formanden)");
        fjernFremlejeButton.getStyleClass().add("button-afslut-dispensation");

        Button svarPåFremlejeButton = new Button("Svar på\nFremlejeansøgning\n(kun for formanden)");
        svarPåFremlejeButton.setOnAction(e-> PopUpsMenues.svarPåFremleje("Svar på Fremlejeansøgning"));
        svarPåFremlejeButton.getStyleClass().add("button-svar-paa-dispensation");
        svarPåFremlejeButton.setPrefSize(240, 135);

        HBox fremlejeTabCenterLayout = new HBox(50, redigerFremlejeButton,fjernFremlejeButton, svarPåFremlejeButton);
        fremlejeTabCenterLayout.setPadding(new Insets(40, 30, 20, 120));

        Label fremlejeLayoutLabel = new Label("Igangværende og kommende Fremlejer: ");
        fremlejeLayoutLabel.getStyleClass().add("label-hovedmenu");

        TableView<Fremleje> fremlejeTableView = new TableView<>();
        tableViews.visFremLejeTableView(fremlejeTableView);

        VBox fremlejeTabMainLayout = new VBox(10, fremlejeLayoutLabel, fremlejeTableView, fremlejeTabCenterLayout);
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
        opretKlageButton.setOnAction(e-> PopUpsMenues.opretKlageOverBeboer("Opret klage over beboer"));
        opretKlageButton.getStyleClass().add("button-klage");
        opretKlageButton.setPrefSize(240,120);
        VBox rightLayoutKlagerTab1 = new VBox(opretKlageButton);
        rightLayoutKlagerTab1.setPadding(new Insets(50));

        HBox klagerTabMainLayout1 = new HBox(10, loginTilKlagerGridPane, rightLayoutKlagerTab1);
        klagerTabMainLayout1.setPadding(new Insets(20));
        Tab klagerTab = new Tab("Klager");
        klagerTab.setClosable(false);
        klagerTab.setContent(klagerTabMainLayout1);

        //Layout2

        Label klageOverskrift1 = new Label("Klageoversigt:");
        klageOverskrift1.getStyleClass().add("label-hovedmenu");

        Button opretKlageButton2 = new Button("Opret\nKlage");
        opretKlageButton2.getStyleClass().add("button-klage");
        opretKlageButton2.setPrefSize(240,120);
        VBox rightLayoutKlagerTab2 = new VBox(opretKlageButton2);
        rightLayoutKlagerTab2.setPadding(new Insets(100,50,50,50));

        TableView<KlageStatus> klageStatusTableView = new TableView<>();
        tableViews.visKlageTableView(klageStatusTableView);

        VBox leftLayoutKlagerTab2 = new VBox(10, klageOverskrift1, klageStatusTableView);
        HBox klagerTabMainLayout2 = new HBox(20,leftLayoutKlagerTab2, rightLayoutKlagerTab2);
        klagerTabMainLayout2.setPadding(new Insets(30));

        loginButton.setOnAction(e->klagerTab.setContent(klagerTabMainLayout2));//Hører til KlageTab for Formand

        //ProtokolTab til formandsMenu
        Label protokolLabel = new Label("Protokol");
        protokolLabel.getStyleClass().add("label-hovedmenu");

        TableView<Protokol> protokolTableView = new TableView<>();
        tableViews.visProtokolTableView(protokolTableView);

        Label dateProtokolLabel = new Label("    Vælg dato:");
        dateProtokolLabel.getStyleClass().add("label-login");
        DatePicker protokolDatePicker = new DatePicker();
        protokolDatePicker.setEditable(false);
        protokolDatePicker.setMaxSize(120,20);
        Label andenSalRepræsentanterLabel = new Label("     2. sal:");
        andenSalRepræsentanterLabel.getStyleClass().add("label-login");
        ChoiceBox andenSalRepræsentanterChoiceBox = new ChoiceBox(FXCollections.observableArrayList("Jessica", ""));//Disse Items skal hentes fra 'brugernavn' - eller skal kunne ændres manuelt
        andenSalRepræsentanterChoiceBox.setMinWidth(110);
        Label tredjeSalRepræsentanterLabel = new Label("     3. sal:");
        tredjeSalRepræsentanterLabel.getStyleClass().add("label-login");
        ChoiceBox tredjeSalRepræsentanterChoiceBox = new ChoiceBox(FXCollections.observableArrayList("Mathias",""));//Disse Items skal hentes fra 'brugernavn' - eller skal kunne ændres manuelt
        tredjeSalRepræsentanterChoiceBox.setMinWidth(110);
        Label fjerdeSalRepræsentanterLabel = new Label("     4. sal:");
        fjerdeSalRepræsentanterLabel.getStyleClass().add("label-login");
        ChoiceBox fjerdeSalRepræsentanterChoiceBox = new ChoiceBox(FXCollections.observableArrayList("Janus",""));//Disse Items skal hentes fra 'brugernavn' - eller skal kunne ændres manuelt
        fjerdeSalRepræsentanterChoiceBox.setMinWidth(110);
        Label femteSalRepræsentanterLabel = new Label("     5. sal:");
        femteSalRepræsentanterLabel.getStyleClass().add("label-login");
        ChoiceBox femteSalRepræsentanterChoiceBox = new ChoiceBox(FXCollections.observableArrayList("Peter",""));//Disse Items skal hentes fra 'brugernavn' - eller skal kunne ændres manuelt
        femteSalRepræsentanterChoiceBox.setMinWidth(110);
        Label sjetteSalRepræsentanterLabel = new Label("     6. sal:");
        sjetteSalRepræsentanterLabel.getStyleClass().add("label-login");

        ChoiceBox sjetteSalRepræsentanterChoiceBox = new ChoiceBox(FXCollections.observableArrayList("Natali",""));//Disse Items skal hentes fra 'brugernavn' - eller skal kunne ændres manuelt
        sjetteSalRepræsentanterChoiceBox.setMinWidth(110);
        Button førProtokolButton = new Button("Tilføj til\nProtokol");
        førProtokolButton.getStyleClass().add("button-tilfoej-til-protokol");
        Button redigerProtokolButton = new Button("Rediger Protokol\n(Kun For formanden)");
        redigerProtokolButton.getStyleClass().add("button-rediger-i-protokol");

        GridPane formandProtokolMainLayout = new GridPane();
        formandProtokolMainLayout.setHgap(15);
        formandProtokolMainLayout.setVgap(5);
        formandProtokolMainLayout.setPadding(new Insets(7.5));
        formandProtokolMainLayout.add(protokolLabel,1,1,12,1);
        formandProtokolMainLayout.add(protokolTableView,1,2,12,10);
        formandProtokolMainLayout.add(dateProtokolLabel,1,14);
        formandProtokolMainLayout.add(protokolDatePicker,1,15);
        formandProtokolMainLayout.add(andenSalRepræsentanterLabel,2,14);
        formandProtokolMainLayout.add(andenSalRepræsentanterChoiceBox,2,15);
        formandProtokolMainLayout.add(tredjeSalRepræsentanterLabel,3,14);
        formandProtokolMainLayout.add(tredjeSalRepræsentanterChoiceBox,3,15);
        formandProtokolMainLayout.add(fjerdeSalRepræsentanterLabel,4,14);
        formandProtokolMainLayout.add(fjerdeSalRepræsentanterChoiceBox,4,15);
        formandProtokolMainLayout.add(femteSalRepræsentanterLabel,5,14);
        formandProtokolMainLayout.add(femteSalRepræsentanterChoiceBox,5,15);
        formandProtokolMainLayout.add(sjetteSalRepræsentanterLabel,6,14);
        formandProtokolMainLayout.add(sjetteSalRepræsentanterChoiceBox,6,15);
        formandProtokolMainLayout.add(førProtokolButton,7,15);
        formandProtokolMainLayout.add(redigerProtokolButton,3,25,3,3);

        Tab protokolTab = new Tab("Protokol");
        protokolTab.setClosable(false);
        protokolTab.setContent(formandProtokolMainLayout);

        TabPane centerLayoutFormand = new TabPane();
        centerLayoutFormand.getTabs().addAll(velkommenTab, dispensationTab, fremlejeTab, klagerTab, protokolTab);


        BorderPane formandBorderPane = new BorderPane();
        formandBorderPane.setLeft(leftLayoutFormand);
        formandBorderPane.setCenter(centerLayoutFormand);
        formandBorderPane.setRight(rightLayoutFormand);
        formandBorderPane.setPrefSize(1450,900);
        sceneFormand = new Scene(formandBorderPane);
        sceneFormand.getStylesheets().add("Layout.css");

        //Scene Indstillingsskabeloner
        Button tilbageTilHovedMenuButton = new Button("Tilbage til Hovedmenu");
        Label indstillingsskabelonerHovedLabel = new Label("Indstillingsskabeloner");
        indstillingsskabelonerHovedLabel.getStyleClass().add("label-hovedmenu");
        tilbageTilHovedMenuButton.setOnAction(e -> window.setScene(sceneStart));
        //1) Klager
        Label klageSkabelonerLabel = new Label("Klager:");
        klageSkabelonerLabel.getStyleClass().add("label-skabeloner");
        Button skabelonKlageForSenDokumentationButton = new Button("Klageskabelon forsent afleveret studiedokumentation");
        skabelonKlageForSenDokumentationButton.getStyleClass().add("button-skabeloner");
        skabelonKlageForSenDokumentationButton.setOnAction(e->{
            skabelonController.openFile("Klage_For_sen_studie_dokumentation.docx");
        });
        Button skabelonKlagerÆndringerButton = new Button("Klageskabelon for ikke at orientere om Studieændringer");
        skabelonKlagerÆndringerButton.getStyleClass().add("button-skabeloner");
        skabelonKlagerÆndringerButton.setOnAction(e->{
            skabelonController.openFile("Klage Studieændringer manglende orientering.docx");
        });
        //2) Fremleje
        Label fremlejeSkabelonLabel = new Label("Fremleje:");
        fremlejeSkabelonLabel.getStyleClass().add("label-skabeloner");
        Button skabelonFremlejerButton = new Button("Fremleje");
        skabelonFremlejerButton.getStyleClass().add("button-skabeloner");
        skabelonFremlejerButton.setOnAction(e->{
           skabelonController.openFile("Fremleje");
        });
        Button typeformularU1991FremlejeButton = new Button("Typeformular(U1991) til fremleje");
        typeformularU1991FremlejeButton.getStyleClass().add("button-skabeloner");
        typeformularU1991FremlejeButton.setOnAction(e->{
            skabelonController.openFile("typeformularU1991.pdf");
        });
        //3) Dispensation
        Label dispensationSkabelonLabel = new Label("Dispensation:");
        dispensationSkabelonLabel.getStyleClass().add("label-skabeloner");
        Button skabelonDispensationButton = new Button("Dispensation");
        skabelonDispensationButton.getStyleClass().add("button-skabeloner");
        skabelonDispensationButton.setOnAction(e->{
            skabelonController.openFile("Dispensation");
        });
        //4) Studiekontrol//Følgeseddel //'påmindelser' //Studiekontrol der kan laves i hånden
        Label studiekontrolSkabelonerLabel = new Label("Studiekontrol");
        studiekontrolSkabelonerLabel.getStyleClass().add("label-skabeloner");
        Button skabelonStudiekontrolBlanketButton = new Button("Studiekontrolsblanket til Udfyldning");
        skabelonStudiekontrolBlanketButton.getStyleClass().add("button-skabeloner");
        skabelonStudiekontrolBlanketButton.setOnAction(e->{
           skabelonController.openFile("Studiekontrolsblanket.docx");
        });
        Button skabelonStudiekontrolFølgeseddelBlanketButton = new Button("Følgeseddel til studiekontrol");
        skabelonStudiekontrolFølgeseddelBlanketButton.getStyleClass().add("button-skabeloner");
        Button skabelonStudiekontrolPåmindelseButton = new Button("Påmindelse om studiekontrol");
        skabelonStudiekontrolPåmindelseButton.getStyleClass().add("button-skabeloner");
        skabelonStudiekontrolPåmindelseButton.setOnAction(e->{
            skabelonController.openFile("Påmindelsesseddel-om-studiekontrol.docx");
        });
        Button skabelonStudiekontrolBlanketTilMapperButton = new Button("Studiekontrolsblanket til Repræsentanternes Mapper");
        skabelonStudiekontrolBlanketTilMapperButton.getStyleClass().add("button-skabeloner");
        Button uoverensstemmelseIOplysningerButton = new Button("Uoverensstemmelse i Oplysninger ifbm. studiekontrol");
        uoverensstemmelseIOplysningerButton.getStyleClass().add("button-skabeloner");

        //5) Opsigelser
        Label opsigelseSkabelonLabel = new Label("Opsigelse af Værelse:");
        opsigelseSkabelonLabel.getStyleClass().add("label-skabeloner");
        Button opsigelsesskabelonButton = new Button("Opsigelsesblanket");
        opsigelsesskabelonButton.getStyleClass().add("button-skabeloner");

        //6)Ansøgningsskema
        Label ansøgningsksemaSkabelonLabel = new Label("Ansøgningsskema");
        ansøgningsksemaSkabelonLabel.getStyleClass().add("label-skabeloner");
        Button ansøgningsskemaSkabelonButton = new Button("Ansøgningsskema");
        ansøgningsskemaSkabelonButton.getStyleClass().add("button-skabeloner");
        ansøgningsskemaSkabelonButton.setOnAction(e->{
            skabelonController.openFile("Ansøgningsskema.docx");
        });

        //7) Brevpapir
        Label brevpapirSkabelonLabel = new Label("Brevpapir:");
        brevpapirSkabelonLabel.getStyleClass().add("label-skabeloner");
        Button brevpapirSkabelonButton = new Button("Brevpapir");
        brevpapirSkabelonButton.getStyleClass().add("button-skabeloner");

        //8)Regler for indstillingen
        Label andetSkabelonerLabel = new Label("Andet:");
        andetSkabelonerLabel.getStyleClass().add("label-skabeloner");
        Button indstillingsreglerButton = new Button("Indstillingsregler");
        indstillingsreglerButton.getStyleClass().add("button-skabeloner");
        indstillingsreglerButton.setOnAction(e->{
            skabelonController.openFile("Indstllingsregler.docx");
        });
        //9)(Links til lovgivning) - andre ting

        GridPane indstillingsskabelonerPane = new GridPane();
        indstillingsskabelonerPane.setPadding(new Insets(20,10,20,10));
        indstillingsskabelonerPane.setVgap(1);
        indstillingsskabelonerPane.add(tilbageTilHovedMenuButton,1,2,1,1);
        indstillingsskabelonerPane.add(indstillingsskabelonerHovedLabel,4,1,4,1);
        indstillingsskabelonerPane.add(klageSkabelonerLabel,4,4,4,1);
        indstillingsskabelonerPane.add(skabelonKlageForSenDokumentationButton,2,6,4,1);
        indstillingsskabelonerPane.add(skabelonKlagerÆndringerButton,2,7,4,1);
        indstillingsskabelonerPane.add(fremlejeSkabelonLabel,4, 9,4,1);
        indstillingsskabelonerPane.add(skabelonFremlejerButton,2,10,4,1);
        indstillingsskabelonerPane.add(typeformularU1991FremlejeButton,2,11,4,1);
        indstillingsskabelonerPane.add(dispensationSkabelonLabel,4,13,4,1);
        indstillingsskabelonerPane.add(skabelonDispensationButton,2,14,4,1);
        indstillingsskabelonerPane.add(studiekontrolSkabelonerLabel,4,16,4,1);
        indstillingsskabelonerPane.add(skabelonStudiekontrolBlanketButton,2,17,4,1);
        indstillingsskabelonerPane.add(skabelonStudiekontrolFølgeseddelBlanketButton,2,18,4,1);
        indstillingsskabelonerPane.add(skabelonStudiekontrolPåmindelseButton,2,19,4,1);
        indstillingsskabelonerPane.add(skabelonStudiekontrolBlanketTilMapperButton,2,20,4,1);
        indstillingsskabelonerPane.add(uoverensstemmelseIOplysningerButton,2,21,4,1);
        indstillingsskabelonerPane.add(opsigelseSkabelonLabel,4,23,4,1);
        indstillingsskabelonerPane.add(opsigelsesskabelonButton,2,24,4,1);
        indstillingsskabelonerPane.add(ansøgningsksemaSkabelonLabel,4,26,4,1);
        indstillingsskabelonerPane.add(ansøgningsskemaSkabelonButton,2,27,4,1);
        indstillingsskabelonerPane.add(brevpapirSkabelonLabel,4,29,4,1);
        indstillingsskabelonerPane.add(brevpapirSkabelonButton,2,30,4,1);
        indstillingsskabelonerPane.add(andetSkabelonerLabel,4,34,4,1);
        indstillingsskabelonerPane.add(indstillingsreglerButton,2,35,4,1);

        sceneIndstillingsskabeloner = new Scene(indstillingsskabelonerPane);
        sceneIndstillingsskabeloner.getStylesheets().add("Layout.css");

        //Afsluttende - sætter scenen

        window.setScene(sceneStart);
        window.show();
    }
    private void logoutAlert(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Logud");
        alert.setContentText("Er du sikker på du vil log ud ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            window.setScene(sceneLogin);
            Log.insertIntoLog("Bruger logget ud af hovedmenuen");
        }
        else{
            alert.close();
        }
    }
}

