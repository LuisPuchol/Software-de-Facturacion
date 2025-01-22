package com.luis.facturacion.model;

import com.luis.facturacion.controller.LoginController;
import com.luis.facturacion.controller.MainMenuController;

public class Model {
    public Model(LoginController loginController) {
        System.out.println("Model created");
    }

    public Model(MainMenuController mainMenuController) {
        System.out.println("Model created");
    }

}
