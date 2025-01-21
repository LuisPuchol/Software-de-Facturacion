package com.luis.facturacion.view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class HandlersMenu {
    public static Menu createArchivosMenu() {
        Menu menu = new Menu("Archivos");
        menu.getItems().addAll(
                new MenuItem("Artículos"),
                new MenuItem("Clientes"),
                new MenuItem("Configuración IVA")
        );
        return menu;
    }

    public static Menu createEmitirAlbaranMenu() {
        Menu menu = new Menu("Emitir Albarán");
        menu.getItems().addAll(
                new MenuItem("Emitir Albarán"),
                new MenuItem("Listado")
        );
        return menu;
    }

    public static Menu createFacturacionMenu() {
        Menu menu = new Menu("Facturación");
        menu.getItems().addAll(
                new MenuItem("Listado a Facturar"),
                new MenuItem("Libro Ingreso")
        );
        return menu;
    }

    public static Menu createResumenMenu() {
        Menu menu = new Menu("Resumen");
        menu.getItems().addAll(
                new MenuItem("Listado resumen clientes"),
                new MenuItem("Trazabilidad")
        );
        return menu;
    }

}
