package sample;

import Classes.Excel;
import Classes.Informacion;
import Classes.PDFManager;
import Classes.Transaccion;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static File changeExtension(File f, String newExtension) {
        int i = f.getName().lastIndexOf('.');
        String name = f.getName().substring(0, i);
        return new File(f.getParent() + "/" + name + newExtension);
    }

    public static void main(String[] args) throws IOException,Exception {
        //launch(args);
        PDFManager pdfManager = new PDFManager();
        pdfManager.setFilePath("C:\\Users\\iworth\\iCloudDrive\\Desktop\\FCR10.pdf");
       //System.out.println(pdfManager.ToText());
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
        /*for(Transaccion c : transaccions){
            System.out.println(c.toString());
        }*/
       // Excel.escribirExcel("C:\\Users\\iworth\\iCloudDrive\\Desktop\\hola.xlsx",transaccions);
        System.out.println("ya");
    }
}