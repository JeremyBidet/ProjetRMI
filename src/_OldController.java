import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("deprecation")
public class _OldController {

    @FXML
    private TableView<IVehicle> rentTableView;
    @FXML
    private TableColumn<IVehicle, String> modelRentColumn;
    @FXML
    private TableColumn<IVehicle, Integer> yearRentColumn;
    @FXML
    private TableColumn<IVehicle, Integer> yearOfServiceRentColumn;
    @FXML
    private TableColumn<IVehicle, Long> ratingRentColumn;
    @FXML
    private TableColumn<IVehicle, String> conditionRentColumn;
    @FXML
    private TableColumn<IVehicle, Long> priceRentColumn;
    @FXML
    private TableView<IVehicle> buyTableView;
    @FXML
    private TableColumn<IVehicle, String> modelBuyColumn;
    @FXML
    private TableColumn<IVehicle, Integer> yearBuyColumn;
    @FXML
    private TableColumn<IVehicle, Integer> yearOfServiceBuyColumn;
    @FXML
    private TableColumn<IVehicle, Long> ratingBuyColumn;
    @FXML
    private TableColumn<IVehicle, String> conditionBuyColumn;
    @FXML
    private TableColumn<IVehicle, Long> priceBuyColumn;
    @FXML
    private SplitPane splitPane;
    @FXML
    private TabPane tabPane;


    public void initialize() {
        // Locking divider
        tabPane.maxWidthProperty().bind(splitPane.widthProperty().multiply(0.65));
        tabPane.minWidthProperty().bind(splitPane.widthProperty().multiply(0.65));

        // Setting up rent table
        modelRentColumn.setCellValueFactory(new PropertyValueFactory<IVehicle, String>("model"));
        modelRentColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
        yearRentColumn.setCellValueFactory(new PropertyValueFactory<IVehicle, Integer>("year"));
        yearRentColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
        yearOfServiceRentColumn.setCellValueFactory(new PropertyValueFactory<IVehicle, Integer>("yearOfService"));
        yearOfServiceRentColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
        ratingRentColumn.setCellValueFactory(new PropertyValueFactory<IVehicle, Long>("rating"));
        ratingRentColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
        conditionRentColumn.setCellValueFactory(new PropertyValueFactory<IVehicle, String>("condition"));
        conditionRentColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
        priceRentColumn.setCellValueFactory(new PropertyValueFactory<IVehicle, Long>("price"));
        priceRentColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
        //Insert Button
        TableColumn actionRentColumn = new TableColumn<>("");
        actionRentColumn.setSortable(false);

        // Setting up rent table
        modelBuyColumn.setCellValueFactory(new PropertyValueFactory<IVehicle, String>("model"));
        modelBuyColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
        yearBuyColumn.setCellValueFactory(new PropertyValueFactory<IVehicle, Integer>("year"));
        yearBuyColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
        yearOfServiceBuyColumn.setCellValueFactory(new PropertyValueFactory<IVehicle, Integer>("yearOfService"));
        yearOfServiceBuyColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
        ratingBuyColumn.setCellValueFactory(new PropertyValueFactory<IVehicle, Long>("rating"));
        ratingBuyColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
        conditionBuyColumn.setCellValueFactory(new PropertyValueFactory<IVehicle, String>("condition"));
        conditionBuyColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
        priceBuyColumn.setCellValueFactory(new PropertyValueFactory<IVehicle, Long>("price"));
        priceBuyColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
        //Insert Button
        TableColumn actionBuyColumn = new TableColumn<>("");
        actionBuyColumn.setSortable(false);

        actionRentColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<IVehicle, Boolean>,
                        ObservableValue<Boolean>>() {

                    @Override
                    public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<IVehicle, Boolean> p) {
                        return new SimpleBooleanProperty(p.getValue() != null);
                    }
                });

        actionBuyColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<IVehicle, Boolean>,
                        ObservableValue<Boolean>>() {

                    @Override
                    public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<IVehicle, Boolean> p) {
                        return new SimpleBooleanProperty(p.getValue() != null);
                    }
                });

        actionRentColumn.setCellFactory(
                new Callback<TableColumn<IVehicle, Boolean>, TableCell<IVehicle, Boolean>>() {

                    @Override
                    public TableCell<IVehicle, Boolean> call(TableColumn<IVehicle, Boolean> p) {
                        return new ButtonCell();
                    }

                });
        actionBuyColumn.setCellFactory(
                new Callback<TableColumn<IVehicle, Boolean>, TableCell<IVehicle, Boolean>>() {

                    @Override
                    public TableCell<IVehicle, Boolean> call(TableColumn<IVehicle, Boolean> p) {
                        return new ButtonCell();
                    }

                });
        actionRentColumn.setStyle("-fx-alignment: CENTER;");
        actionBuyColumn.setStyle("-fx-alignment: CENTER;");
        rentTableView.getColumns().add(actionRentColumn);
        buyTableView.getColumns().add(actionBuyColumn);
/*
        buyTableView.getItems().add(new IVehicle("1004", 2007));


        //tableview.getItems().add(new Client("Ramos1", 1));
        // tableview.getItems().add(new Client("Amos2", 2));
        rentTableView.getItems().add(new IVehicle("304", 2007));
        rentTableView.getItems().add(new IVehicle("306", 2000));
        rentTableView.getItems().add(new IVehicle("307", 2005));
        rentTableView.getItems().add(new IVehicle("308", 2003));
        rentTableView.getItems().add(new IVehicle("309", 2001));
        rentTableView.getItems().add(new IVehicle("203", 2002));
        rentTableView.getItems().add(new IVehicle("204", 2010));
        rentTableView.getItems().add(new IVehicle("207", 2015));
*/

        try {
            String codebase = "file:////home/phm/Téléchargements/cassecouilles/RMI-Server/out/production/RMI-Server/";
            System.out.println(codebase);
            System.setProperty("java.rmi.server.codebase", codebase);
            System.setProperty("java.security.policy", "security.policy");
            System.setSecurityManager(new RMISecurityManager());
            Park service = (Park) Naming.lookup("rmi://localhost:1099/ParkService");

            HashMap<Long, IVehicle> map = service.getPark();


            for (Map.Entry<Long, IVehicle> entry : map.entrySet()) {
                long randomLong = entry.getKey();
                IVehicle vehicle = entry.getValue();
                System.out.println(vehicle);
                if (vehicle.isSellable()) {
                    buyTableView.getItems().add(vehicle);
                } else {
                    rentTableView.getItems().add(vehicle);
                }
                System.out.println("Key : " + randomLong + " Vehicle model : " + vehicle.getModel() + "Vehicle year : " + vehicle.getYear());
            }
            //service.addClient(new Client("JetestLeServer",3000));
            List<_MainClient> clients = service.getClients();
            for (_MainClient client : clients) {
                System.out.println(client.toString());
            }

            System.out.println(service.getVec());

			/* Ici c'est l'invocation des webServices prout */
            Bank bank = new BankServiceLocator().getBank();
            System.out.println(bank.checkID(1));
            System.out.println(bank.checkAmount(1, 10000));
        } catch (Exception e) {
            System.out.println("Problem :  " + e);
        }
    }

    private class ButtonCell extends TableCell<IVehicle, Boolean> {
        final Button cellButton = new Button("Ajouter");

        ButtonCell() {

            cellButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    if (tabPane.getSelectionModel().getSelectedIndex() == 0) {
                        System.out.println(rentTableView.getItems().get(getTableRow().getIndex()));
                    } else {
                        System.out.println(buyTableView.getItems().get(getTableRow().getIndex()));
                    }
                }
            });
        }

        //Display button if the row is not empty
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                setGraphic(cellButton);
            }
        }
    }
}