package sample;

import Classes.Excel;
import Classes.Informacion;
import Classes.PDFManager;
import Classes.Transaccion;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {
    @FXML
    Button btoListo, btoSetDirectory, btoGetFile;

    @FXML
    TextField txtPDF, txtExc, txtName;

    @FXML
    Pane pane;

    public void initialize(){

        btoListo.setOnAction(event -> {
            System.out.println("hola");
            btoListo.setDisable(true);
            if(!txtExc.getText().equals("") && !txtPDF.getText().equals("") && !txtName.getText().equals("")){
                try{
                    getTransacciones(txtPDF.getText(),txtExc.getText()+"\\"+txtName.getText()+".xlsx");
                }catch (Exception e ){
                    e.printStackTrace();
                }
            }else
            {
                Platform.runLater(
                        () -> {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error.");
                            alert.setHeaderText("Falta de información.");
                            alert.setContentText("Rellene la información necesaria antes de convertir.");
                            alert.showAndWait().ifPresent(rs -> {
                                if (rs == ButtonType.OK) {
                                    alert.close();
                                }
                            });});
            }
            btoListo.setDisable(false);
        });
        btoGetFile.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(null);
            if(file!=null)txtPDF.setText(file.getAbsolutePath());
        });
        pane.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                if (db.hasFiles()) {
                    event.acceptTransferModes(TransferMode.COPY);
                } else {
                    event.consume();
                }
            }
        });
        pane.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    success = true;
                    String filePath = null;
                    for (File file:db.getFiles()) {
                        filePath = file.getAbsolutePath();
                        txtPDF.setText(filePath);
                    }
                }
                event.setDropCompleted(success);
                event.consume();
            }
        });

        btoSetDirectory.setOnAction(event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(null);
            txtExc.setText(selectedDirectory.getAbsolutePath());
        });
    }

    public void getTransacciones(String pathPDF, String pathExc) throws Exception {
        PDFManager pdfManager = new PDFManager();
        pdfManager.setFilePath(pathPDF);
        try{
            String[] fk = pdfManager.ToText().split("\n");
            ArrayList<Transaccion> transaccions = new ArrayList<>();
            int cont = 0;
            String saldoTotal = "";
            Informacion info = new Informacion();
            String nombre = "";
            Boolean banderaSaldo = false;
            Boolean banderaClave = false;
            Boolean banderaNombre = false;
            Boolean banderaMoneda = false;
            for(String a : fk){
                a=a.replaceAll("\\s+",";");
                String[] fkfk = a.split(";");
                if(fkfk.length>2 && (fkfk[1].equals("FACTURA") || fkfk[1].equals("NOTA") || fkfk[1].equals("PAGO"))) {
                    //ystem.out.println(a);
                    String descr = "";
                    for (int i = 0; i < fkfk.length - 4; i++) {
                        descr += (fkfk[i + 2] + " ");
                    }
                    transaccions.add(new Transaccion(fkfk[0], fkfk[1], descr, fkfk[fkfk.length - 2], fkfk[fkfk.length - 1]));
                }
                else if(cont==2){
                    info.setSaldoTotal(a);
                    System.out.println("se mando este: "+a+" y llego esto: "+info.getSaldoTotal());
                }
                else if(cont>3){
                    if(fkfk[0].equals("SALDO") && !fkfk[1].equals("crédito")){
                        info.setSaldo(fkfk[1]);
                    }else if(fkfk[0].equals("CRÉDITO")){
                        info.setSaldoCreditoRevolutivo(fkfk[2]);
                    }else if(fkfk[0].equals("DERECHO")){
                        info.setDerechoMarca(fkfk[3]);
                    }else if(fkfk[0].equals("TOTAL")){
                        info.setTotal(fkfk[1]);
                    }else if(fkfk[0].equals("DISPONIBLE")){
                        info.setDisponibleCreditoRevolutivo(fkfk[3]);
                    }else if(fkfk[0].equals("Estimado")){
                        info.setMensaje(a.replace(";"," "));
                    }else if(fkfk[0].equals("Saldo")&& fkfk[1].equals("crédito")){
                        banderaSaldo = true;
                    }else if(banderaSaldo){
                        banderaSaldo= false;
                        info.setSaldoCreditoRevolutivo2(fkfk[0]);
                        info.setDerechoMarca2(fkfk[1]);
                    }else if(fkfk[0].equals("Nombre")){
                        banderaClave=true;
                    }else if(banderaClave){
                        banderaClave = false;
                        banderaNombre = true;
                        info.setClaveCliente(fkfk[0]);
                        nombre = fkfk[1]+" "+fkfk[2]+ " ";
                    }else if(banderaNombre){
                        banderaNombre = false;
                        banderaMoneda = true;
                        nombre += a.replace(";"," ");
                        info.setNombre(nombre);
                    }else if(banderaMoneda){
                        banderaMoneda = false;
                        info.setMoneda(fkfk[0]);
                        info.setFechaCorte(fkfk[1]);
                    }
                }
                else{
                    System.out.println(cont +" "+a);
                }
                cont++;
            }
            System.out.println(info.toString());
            try{
            Excel.escribirExcel(pathExc,transaccions,info);
            Platform.runLater(
                    () -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Listo!");
                        alert.setHeaderText("Se convirtió el archivo.");
                        alert.setContentText("El archivo se deberá encontrar en la dirección especificada.");
                        alert.showAndWait().ifPresent(rs -> {
                            if (rs == ButtonType.OK) {
                                alert.close();
                            }
                        });});
            }catch(Exception e){
                Platform.runLater(
                        () -> {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Oops :(");
                            alert.setHeaderText("Ocurrió un error.");
                            alert.setContentText("El archivo no se convirtió correctamente.");
                            alert.showAndWait().ifPresent(rs -> {
                                if (rs == ButtonType.OK) {
                                    alert.close();
                                }
                            });});
            }
        }catch (IOException ioe){
            Platform.runLater(
                    () -> {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("ERROR!");
                        alert.setHeaderText("Ocurrió un error");
                        alert.setContentText("Verifique el archivo PDF cargado.");
                        alert.showAndWait().ifPresent(rs -> {
                            if (rs == ButtonType.OK) {
                                txtPDF.setText("");
                                alert.close();
                            }
                        });});
        }
    }
}
