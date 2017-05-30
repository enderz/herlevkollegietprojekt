package View;

import Model.Beboer;
import Model.Dispensation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Date;

/**
 * Created by Ender on 30-05-2017.
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

}
