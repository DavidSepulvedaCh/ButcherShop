package models;

public class BuyRes {
    private String tipoAnimal;
    private String pesoArrobas;
    private String precioArroba;
    private String fechaCompra;
    private String proveedor;

    public BuyRes() {
    }

    public BuyRes(String tipoAnimal, String pesoArrobas, String precioArroba, String fechaCompra, String proveedor) {
        this.tipoAnimal = tipoAnimal;
        this.pesoArrobas = pesoArrobas;
        this.precioArroba = precioArroba;
        this.fechaCompra = fechaCompra;
        this.proveedor = proveedor;
    }

    public String getTipoAnimal() {
        return tipoAnimal;
    }

    public void setTipoAnimal(String tipoAnimal) {
        this.tipoAnimal = tipoAnimal;
    }

    public String getPesoArrobas() {
        return pesoArrobas;
    }

    public void setPesoArrobas(String pesoArrobas) {
        this.pesoArrobas = pesoArrobas;
    }

    public String getPrecioArroba() {
        return precioArroba;
    }

    public void setPrecioArroba(String precioArroba) {
        this.precioArroba = precioArroba;
    }

    public String getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(String fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }
}
