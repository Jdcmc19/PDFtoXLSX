package Classes;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Informacion {
    private String claveCliente = "";
    private String nombre = "";
    private String moneda = "";
    private Date fechaCorte;
    private Double saldoTotal = 0.0;
    private Double saldoCreditoRevolutivo = 0.0;
    private Double derechoMarca = 0.0;
    private Double saldo = 0.0;
    private Double saldoCreditoRevolutivo2 = 0.0;
    private Double derechoMarca2 = 0.0;
    private Double total = 0.0;
    private Double disponibleCreditoRevolutivo = 0.0;
    private String mensaje="";

    public Informacion(String claveCliente, String nombre, String moneda, Date fechaCorte, Double saldoTotal, Double saldoCreditoRevolutivo, Double derechoMarca, Double saldo, Double saldoCreditoRevolutivo2, Double derechoMarca2, Double total, Double disponibleCreditoRevolutivo, String mensaje) {
        this.claveCliente = claveCliente;
        this.nombre = nombre;
        this.moneda = moneda;
        this.fechaCorte = fechaCorte;
        this.saldoTotal = saldoTotal;
        this.saldoCreditoRevolutivo = saldoCreditoRevolutivo;
        this.derechoMarca = derechoMarca;
        this.saldo = saldo;
        this.saldoCreditoRevolutivo2 = saldoCreditoRevolutivo2;
        this.derechoMarca2 = derechoMarca2;
        this.total = total;
        this.disponibleCreditoRevolutivo = disponibleCreditoRevolutivo;
        this.mensaje = mensaje;
    }

    public Informacion() {
    }

    public String getClaveCliente(){
        return claveCliente;
    }

    public void setClaveCliente(String claveCliente) {
        this.claveCliente = claveCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public Date getFechaCorte() {
        return fechaCorte;
    }

    public void setFechaCorte(String fechaCorte) {
        DateFormat sourceFormat = new SimpleDateFormat("dd-MM-yy");
        try{
            Date date = sourceFormat.parse(fechaCorte);this.fechaCorte = date;
        }catch (ParseException pe){
            pe.printStackTrace();
            this.fechaCorte = new Date();
        }

    }

    public Double getSaldoTotal() {
        return saldoTotal;
    }

    public void setSaldoTotal(String saldoTotal) {
        try{this.saldoTotal = Integer.parseInt(saldoTotal.substring(0, saldoTotal.length() - 2).replaceAll(",","").replaceAll("\\.",""))/100.0;}
        catch (Exception e){
            e.printStackTrace();
            this.saldoTotal = -1.0;
        }
    }

    public Double getSaldoCreditoRevolutivo() {
        return saldoCreditoRevolutivo;
    }

    public void setSaldoCreditoRevolutivo(String saldoCreditoRevolutivo) {
        try {
            this.saldoCreditoRevolutivo = Integer.parseInt(saldoCreditoRevolutivo.substring(0, saldoCreditoRevolutivo.length() - 1).replaceAll(",", "").replaceAll("\\.", "")) / 100.0;
        }
        catch(Exception e){
            this.saldoCreditoRevolutivo = -1.0;
        }
    }

    public Double getDerechoMarca() {
        return derechoMarca;
    }

    public void setDerechoMarca(String derechoMarca) {
        try {
            this.derechoMarca = Integer.parseInt(derechoMarca.substring(0, derechoMarca.length() - 1).replaceAll(",", "").replaceAll("\\.", "")) / 100.0;
        }
        catch(Exception e){
            this.derechoMarca = -1.0;}
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        try {
            this.saldo = Integer.parseInt(saldo.substring(0, saldo.length() - 1).replaceAll(",", "").replaceAll("\\.", "")) / 100.0;
        }
        catch(Exception e){
            this.saldo = -1.0;}
    }

    public Double getSaldoCreditoRevolutivo2() {
        return saldoCreditoRevolutivo2;
    }

    public void setSaldoCreditoRevolutivo2(String saldoCreditoRevolutivo2) {
        try {
            this.saldoCreditoRevolutivo2 = Integer.parseInt(saldoCreditoRevolutivo2.substring(0, saldoCreditoRevolutivo2.length() - 1).replaceAll(",", "").replaceAll("\\.", "")) / 100.0;
        }
        catch(Exception e){
            this.saldoCreditoRevolutivo2 = -1.0;}
    }

    public Double getDerechoMarca2() {
        return derechoMarca2;
    }

    public void setDerechoMarca2(String derechoMarca2) {
        try {
            this.derechoMarca2 = Integer.parseInt(derechoMarca2.substring(0, derechoMarca2.length() - 1).replaceAll(",", "").replaceAll("\\.", "")) / 100.0;
        }
        catch(Exception e){
            this.derechoMarca2 = 0.0;}
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(String total) {
        try {
            this.total = Integer.parseInt(total.replaceAll(",", "").replaceAll("\\.", "")) / 100.0;
        }
        catch(Exception e){
            this.total = 0.0;}
    }

    public Double getDisponibleCreditoRevolutivo() {
        return disponibleCreditoRevolutivo;
    }

    public void setDisponibleCreditoRevolutivo(String disponibleCreditoRevolutivo) {
        try {
            this.disponibleCreditoRevolutivo = Integer.parseInt(disponibleCreditoRevolutivo.replaceAll("\\(","").replaceAll("\\)","").replaceAll(",", "").replaceAll("\\.", "")) / 100.0;
            System.out.println(this.disponibleCreditoRevolutivo + "Ä    UIIII");
        }
        catch(Exception e){
            this.disponibleCreditoRevolutivo = 0.0;}
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {
        return "Informacion{" +
                "claveCliente='" + claveCliente + '\'' + '\n' +
                ", nombre='" + nombre + '\'' +'\n' +
                ", moneda='" + moneda + '\'' +'\n' +
                ", fechaCorte=" + fechaCorte +'\n' +
                ", saldoTotal=" + saldoTotal +'\n' +
                ", saldoCreditoRevolutivo=" + saldoCreditoRevolutivo +'\n' +
                ", derechoMarca=" + derechoMarca +'\n' +
                ", saldo=" + saldo +'\n' +
                ", saldoCreditoRevolutivo2=" + saldoCreditoRevolutivo2 +'\n' +
                ", derechoMarca2=" + derechoMarca2 +'\n' +
                ", total=" + total +'\n' +
                ", disponibleCreditoRevolutivo=" + disponibleCreditoRevolutivo +'\n' +
                ", mensaje='" + mensaje + '\'' +
                '}';
    }
}
