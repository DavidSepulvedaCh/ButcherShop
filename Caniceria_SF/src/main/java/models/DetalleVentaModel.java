    package models;

    public class DetalleVentaModel {
        private String codigoProducto;
        private String producto;
        private String cantidad;
        private String precio;
        private String idVenta;
        private String totalVenta;
        private String vendedor;
        private String total;
        private String id;
        private String fecha;

        public DetalleVentaModel() { }

        public DetalleVentaModel(String idVenta, String codigoProducto, String producto, String cantidad, String precio, String totalVenta, String vendedor, String total, String id, String fecha) {
            this.codigoProducto = codigoProducto;
            this.producto = producto;
            this.cantidad = cantidad;
            this.precio = precio;
            this.idVenta = idVenta;
            this.totalVenta = totalVenta;
            this.vendedor = vendedor;
            this.total = total;
            this.id = id;
            this.fecha = fecha;
        }

        public String getCodigoProducto() {
            return codigoProducto;
        }

        public void setCodigoProducto(String codigoProducto) {
            this.codigoProducto = codigoProducto;
        }

        public String getProducto() {
            return producto;
        }

        public void setProducto(String producto) {
            this.producto = producto;
        }

        public String getCantidad() {
            return cantidad;
        }

        public void setCantidad(String cantidad) {
            this.cantidad = cantidad;
        }

        public String getPrecio() {
            return precio;
        }

        public void setPrecio(String precio) {
            this.precio = precio;
        }

        public String getIdVenta() {
            return idVenta;
        }

        public void setIdVenta(String idVenta) {
            this.idVenta = idVenta;
        }

        public String getTotalVenta() {
            return totalVenta;
        }

        public void setTotalVenta(String totalVenta) {
            this.totalVenta = totalVenta;
        }

        public String getVendedor() {
            return vendedor;
        }

        public void setVendedor(String vendedor) {
            this.vendedor = vendedor;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFecha() {
            return fecha;
        }

        public void setFecha(String fecha) {
            this.fecha = fecha;
        }
    }
