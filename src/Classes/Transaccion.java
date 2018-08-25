package Classes;

public class Transaccion {
    private String fecha;
    private String tipoDocumento;
    private String descripcion;
    private String credito;
    private String debito;
    private String saldo;

    public Transaccion(String fecha, String tipoDocumento, String descripcion, String creditoDebito, String saldo) {
        this.fecha = fecha;
        this.tipoDocumento = tipoDocumento;
        this.descripcion = descripcion;
        if(tipoDocumento.equals("FACTURA")){
            this.credito = creditoDebito;
            this.debito="";
        }
        else{
            this.debito = creditoDebito;
            this.credito = "";
        }
        this.saldo = saldo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getCredito() {
        return Integer.parseInt(credito.replaceAll(",","").replaceAll("\\.",""))/100.0;
    }

    public void setCredito(String credito) {
        this.credito = credito;
    }

    public Double getDebito() {
        return Integer.parseInt(debito.replaceAll(",","").replaceAll("\\.",""))/100.0;
    }

    public void setDebito(String debito) {
        this.debito = debito;
    }

    public Double getSaldo() {
        return Integer.parseInt(saldo.substring(0, saldo.length() - 1).replaceAll(",","").replaceAll("\\.",""))/100.0;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "Transaccion{" +
                "fecha='" + fecha + '\'' +
                ", tipoDocumento='" + tipoDocumento + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", credito='" + credito + '\'' +
                ", debito='" + debito + '\'' +
                ", saldo='" + saldo + '\'' +
                '}';
    }
}
