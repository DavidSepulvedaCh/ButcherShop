package models;

public class InsumoModel {
    private String id;
    private String nombreInsumo;
    private String precioInsumo;
    private String cantidadInsumo;
    private String fechaInsumo;
    private String proveedorInsumo;
    private String descripcionInsumo;

    public InsumoModel() {
    }

    public InsumoModel(String id, String nombreInsumo, String precioInsumo, String cantidadInsumo, String fechaInsumo, String proveedorInsumo, String descripcionInsumo) {
        this.id = id;
        this.nombreInsumo = nombreInsumo;
        this.precioInsumo = precioInsumo;
        this.cantidadInsumo = cantidadInsumo;
        this.fechaInsumo = fechaInsumo;
        this.proveedorInsumo = proveedorInsumo;
        this.descripcionInsumo = descripcionInsumo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreInsumo() {
        return nombreInsumo;
    }

    public void setNombreInsumo(String nombreInsumo) {
        this.nombreInsumo = nombreInsumo;
    }

    public String getPrecioInsumo() {
        return precioInsumo;
    }

    public void setPrecioInsumo(String precioInsumo) {
        this.precioInsumo = precioInsumo;
    }

    public String getCantidadInsumo() {
        return cantidadInsumo;
    }

    public void setCantidadInsumo(String cantidadInsumo) {
        this.cantidadInsumo = cantidadInsumo;
    }

    public String getFechaInsumo() {
        return fechaInsumo;
    }

    public void setFechaInsumo(String fechaInsumo) {
        this.fechaInsumo = fechaInsumo;
    }

    public String getProveedorInsumo() {
        return proveedorInsumo;
    }

    public void setProveedorInsumo(String proveedorInsumo) {
        this.proveedorInsumo = proveedorInsumo;
    }

    public String getDescripcionInsumo() {
        return descripcionInsumo;
    }

    public void setDescripcionInsumo(String descripcionInsumo) {
        this.descripcionInsumo = descripcionInsumo;
    }
}
