package View;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Date;

/**
 * Created by Herlev Kollegiet Udvikler on 30-05-2017..
 *
 * Janus Olsen og Ender Zorsøker
 */
public class TableViews
{
    public ObservableList<Beboer> getBeboer() {
        ObservableList<Beboer> beboer = FXCollections.observableArrayList();
        return beboer;
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
    public ObservableList<KlageStatus> getKlageStatus(){
        ObservableList<KlageStatus> klageStatus = FXCollections.observableArrayList();
        klageStatus.add(new KlageStatus(403,"Mette Frederiksen",2));
        return klageStatus;
    }
    public ObservableList<Protokol> getProtokol(){
        ObservableList<Protokol> protokol = FXCollections.observableArrayList();
        protokol.add(new Protokol(new Date(2017, 5,15), "Jessica","Mathias","Janus","Peter","Natali"));
        return protokol;
    }
    public ObservableList<VaerelsesUdlejning> getVærelsesUdlejning() {
        ObservableList<VaerelsesUdlejning> værelsesUdlejning = FXCollections.observableArrayList();
        //værelsesUdlejning.add(new VaerelsesUdlejning(404,new java.util.Date(2017,10,29)));
        return værelsesUdlejning;
    }
    public ObservableList<Deadline> getReminder() {
        ObservableList<Deadline> reminder = FXCollections.observableArrayList();// DER SKAL INKLUDERES ET LINJESKIFT EFER XXXX ANTAL ANSLAG
        reminder.add(new Deadline("01/02/2017", "407 skal aflevere\n studiekontrol her"));
        return reminder;
    }
    /**
     * @param beboerListe viser tableview med beboer informationer.
     * */
    public void visBeboerTableView(TableView beboerListe){

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
    public void visStudieKontrolTableView(TableView studieKontrolListe){

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

        TableColumn<Beboer, String> kontrolStatus = new TableColumn<>("KontrolStatus");
        kontrolStatus.setMinWidth(100);
        kontrolStatus.setCellValueFactory(new PropertyValueFactory<>("kontrolStatus"));
        studieKontrolListe.setItems(getBeboer());
        studieKontrolListe.getColumns().addAll(værelseBeboerListe, navnBeboerListe, indflytningsdatoBeboerliste, institutionBeboerListe, påbegyndtUddannelseBeboerListe, uddannelseAfsluttesBeboerListe, uddannelsesRetningBeboerListe, kontrolStatus);

    }

    public void visDispensationsTableView(TableView<Dispensation> dispensationsView)
    {
        TableColumn<Dispensation, Integer> værelseDispensation = new TableColumn<>("Værelse");
        værelseDispensation.setMaxWidth(70);
        værelseDispensation.setCellValueFactory(new PropertyValueFactory<>("værelse"));//Property need to match the class's field names

        TableColumn<Dispensation, String> navnDispensation = new TableColumn<>("Navn");
        navnDispensation.setMaxWidth(200);
        navnDispensation.setCellValueFactory(new PropertyValueFactory<>("navn"));//Property need to match the class's field names

        TableColumn<Dispensation, Date> startDatoDispensation = new TableColumn<>("Dispensation\nstarter d.");
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

        TableColumn<Dispensation, Date> ophørsDatoDispensation = new TableColumn<>("Dispensation\nOphører d.");
        ophørsDatoDispensation.setMaxWidth(100);
        ophørsDatoDispensation.setCellValueFactory(new PropertyValueFactory<>("ophørsDato"));//Property need to match the class's field names

        dispensationsView.setItems(getDispensation());
        dispensationsView.getColumns().addAll(værelseDispensation,navnDispensation,startDatoDispensation,deadline1Dispensation, deadline2Dispensation, deadline3Dispensation, ophørsDatoDispensation);
        dispensationsView.setMaxSize(1080, 400);
    }
    public void visFremLejeTableView(TableView<Fremleje> fremlejeView)
    {
        TableColumn<Fremleje, Integer> værelseFremleje = new TableColumn<>("Værelse");
        værelseFremleje.setMaxWidth(70);
        værelseFremleje.setMinWidth(70);
        værelseFremleje.setCellValueFactory(new PropertyValueFactory<>("værelse"));//Property need to match the class's field names

        TableColumn<Fremleje, String> fremlejetagerFremleje = new TableColumn<>("Fremlejetager");
        fremlejetagerFremleje.setMaxWidth(300);
        fremlejetagerFremleje.setMinWidth(300);
        fremlejetagerFremleje.setCellValueFactory(new PropertyValueFactory<>("fremlejetager"));//Property need to match the class's field names

        TableColumn<Fremleje, Date> startDatoFremleje = new TableColumn<>("Fremlejens\nstart:");
        startDatoFremleje.setMaxWidth(125);
        startDatoFremleje.setMinWidth(125);
        startDatoFremleje.setCellValueFactory(new PropertyValueFactory<>("startDato"));//Property need to match the class's field names

        TableColumn<Fremleje, String> fremlejerFremleje = new TableColumn<>("Fremlejer\nNavn");
        fremlejerFremleje.setMaxWidth(300);
        fremlejerFremleje.setMinWidth(300);
        fremlejerFremleje.setCellValueFactory(new PropertyValueFactory<>("fremlejer"));//Property need to match the class's field names

        TableColumn<Fremleje, Date> slutDatoFremleje = new TableColumn<>("Fremleje\nSlutter");
        slutDatoFremleje.setMaxWidth(125);
        slutDatoFremleje.setMinWidth(125);
        slutDatoFremleje.setCellValueFactory(new PropertyValueFactory<>("slutDato"));//Property need to match the class's field names

        fremlejeView.setItems(getFremleje());
        fremlejeView.getColumns().addAll(værelseFremleje,startDatoFremleje, fremlejetagerFremleje, slutDatoFremleje, fremlejerFremleje);
        fremlejeView.setMaxSize(1080, 400);
    }
    public void visKlageTableView(TableView<KlageStatus> klageStatusTableView)
    {
        TableColumn<KlageStatus, Integer> værelseKlageColumn = new TableColumn<>("Værelse");
        værelseKlageColumn.setMaxWidth(75);
        værelseKlageColumn.setMinWidth(75);
        værelseKlageColumn.setCellValueFactory(new PropertyValueFactory<>("værelse"));//Property need to match the class's field names

        TableColumn<KlageStatus, Integer> navnKlageColumn = new TableColumn<>("Navn");
        navnKlageColumn.setMaxWidth(300);
        navnKlageColumn.setMinWidth(200);
        navnKlageColumn.setCellValueFactory(new PropertyValueFactory<>("navn"));//Property need to match the class's field names

        TableColumn<KlageStatus, Integer> antalKlagerKlageColumn = new TableColumn<>("Antal\nKlager");
        antalKlagerKlageColumn.setMaxWidth(125);
        antalKlagerKlageColumn.setMinWidth(125);
        antalKlagerKlageColumn.setCellValueFactory(new PropertyValueFactory<>("antalKlager"));//Property need to match the class's field names

        klageStatusTableView.setItems(getKlageStatus());
        klageStatusTableView.getColumns().addAll(værelseKlageColumn,navnKlageColumn, antalKlagerKlageColumn);

    }
    public void visProtokolTableView(TableView<Protokol> protokolTableView)
    {
        TableColumn<Protokol, Date> datoProtokolColumn = new TableColumn<>("Dato:");
        datoProtokolColumn.setMaxWidth(125);
        datoProtokolColumn.setMinWidth(125);
        datoProtokolColumn.setCellValueFactory(new PropertyValueFactory<>("dato"));//Property need to match the class's field names

        TableColumn<Protokol, String> andenSalProtokolColumn = new TableColumn<>("2. sal:");
        andenSalProtokolColumn.setMaxWidth(125);
        andenSalProtokolColumn.setMinWidth(125);
        andenSalProtokolColumn.setCellValueFactory(new PropertyValueFactory<>("andenSalTilstedeværelse"));//Property need to match the class's field names

        TableColumn<Protokol, String> tredjeSalProtokolColumn = new TableColumn<>("3. sal:");
        tredjeSalProtokolColumn.setMaxWidth(125);
        tredjeSalProtokolColumn.setMinWidth(125);
        tredjeSalProtokolColumn.setCellValueFactory(new PropertyValueFactory<>("tredjeSalTilstedeværelse"));//Property need to match the class's field names

        TableColumn<Protokol, String> fjerdeSalProtokolColumn = new TableColumn<>("4. sal:");
        fjerdeSalProtokolColumn.setMaxWidth(125);
        fjerdeSalProtokolColumn.setMinWidth(125);
        fjerdeSalProtokolColumn.setCellValueFactory(new PropertyValueFactory<>("fjerdeSalTilstedeværelse"));//Property need to match the class's field names

        TableColumn<Protokol, String> femteSalProtokolColumn = new TableColumn<>("5. sal:");
        femteSalProtokolColumn.setMaxWidth(125);
        femteSalProtokolColumn.setMinWidth(125);
        femteSalProtokolColumn.setCellValueFactory(new PropertyValueFactory<>("femteSalTilstedeværelse"));//Property need to match the class's field names

        TableColumn<Protokol, String> sjetteSalProtokolColumn = new TableColumn<>("6. sal:");
        sjetteSalProtokolColumn.setMaxWidth(125);
        sjetteSalProtokolColumn.setMinWidth(125);
        sjetteSalProtokolColumn.setCellValueFactory(new PropertyValueFactory<>("sjetteSalTilstedeværelse"));//Property need to match the class's field names


        protokolTableView.setMaxSize(750,1000);
        protokolTableView.setItems(getProtokol());
        protokolTableView.getColumns().addAll(datoProtokolColumn, andenSalProtokolColumn, tredjeSalProtokolColumn, fjerdeSalProtokolColumn, femteSalProtokolColumn, sjetteSalProtokolColumn);

    }
    public void visVaerelsesUdlejningTableVew(TableView<VaerelsesUdlejning> udlejningTableView)
    {
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

        udlejningTableView.setMaxSize(430,217);
        udlejningTableView.setItems(getVærelsesUdlejning());
        udlejningTableView.getColumns().addAll(udlejedeVærelser,udlejningsdato1,behandlingsdato,behandlerInitialer);

    }
    public void visLedigeVaerelserTableView(TableView<VaerelsesUdlejning> ledigeVaerelserTableView)
    {
        TableColumn<VaerelsesUdlejning, Integer> ledigeVærelser = new TableColumn<>("Værelse");
        ledigeVærelser.setCellValueFactory(new PropertyValueFactory<>("værelse"));//Property need to match the class's field names

        TableColumn<VaerelsesUdlejning, java.util.Date> udlejningsdato = new TableColumn<>("Overtagelses-\ndato:");
        udlejningsdato.setCellValueFactory(new PropertyValueFactory<>("udlejningsdato"));//Property need to match the class's field names

        ledigeVaerelserTableView.setMaxSize(430, 225);
        ledigeVaerelserTableView.setItems(getVærelsesUdlejning());
        ledigeVaerelserTableView.getColumns().addAll(udlejningsdato, ledigeVærelser);
    }
    public void visDeadlineTableView(TableView<Deadline> deadlineTableView)
    {
        TableColumn<Deadline, String> dato = new TableColumn<>("Dato");
        TableColumn<Deadline, Integer> værelse = new TableColumn<>("Evt.\nVærelse");

        dato.setMinWidth(70);
        dato.setCellValueFactory(new PropertyValueFactory<>("dato"));//Property need to match the class's field names

        TableColumn<Deadline, String> deadline = new TableColumn<>("Deadline");
        deadline.setMinWidth(300);
        deadline.setSortable(false); // Fjerner mulighed for at sortere
        deadline.setCellValueFactory(new PropertyValueFactory<>("deadline"));//Property need to match the class's field names

        deadlineTableView.setItems(getReminder());
        deadlineTableView.getColumns().addAll(dato, værelse, deadline);
    }


}
