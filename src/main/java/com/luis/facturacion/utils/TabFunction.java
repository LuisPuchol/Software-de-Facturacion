package com.luis.facturacion.utils;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TabFunction {

    /**
     * Configures tab functionality for all text fields in a form grid.
     * @param formGrid The grid pane containing form elements
     */
    public void configureTabFunction(GridPane formGrid) {
        getAllTextFields(formGrid).forEach(this::configureEnterKeyBehavior);

        /**
         * List<TextField> textFields = getAllTextFields(formGrid);
         *
         * for (TextField textField : textFields) {
         *     configureEnterKeyBehavior(textField);
         * }
         */
    }

    /**
     * Configures circular navigation between a list of nodes.
     * @param navigationOrder The ordered list of nodes to navigate between
     * @param finishPoint The last node in the navigation sequence
     * @param starterPoint The first node in the navigation sequence
     */
    public void configureCircularNavigation(List<Node> navigationOrder, TextField starterPoint, Button finishPoint, Map<Node, Runnable> customActions) {
        for (int i = 0; i < navigationOrder.size(); i++) {
            final Node currentNode = navigationOrder.get(i);
            final Node nextNode = (currentNode == finishPoint)
                    ? starterPoint
                    : navigationOrder.get((i + 1) % navigationOrder.size());
            /**
             * final Node nextNode;
             * if (currentNode == finishPoint) {
             *     nextNode = starterPoint;
             * } else {
             *     nextNode = navigationOrder.get((currentIndex + 1) % navigationOrder.size());
             * }
             */
            configureNodeEnterBehavior(currentNode, nextNode, customActions);
        }

        finishPoint.addEventHandler(ActionEvent.ACTION, event -> starterPoint.requestFocus());
    }

    /**
     * Configures Enter key behavior for a node to navigate to the next node.
     * @param currentNode The current node
     * @param nextNode The node to focus on Enter key press
     */
    private void configureNodeEnterBehavior(Node currentNode, Node nextNode, Map<Node, Runnable> customActions) {
        if (currentNode instanceof TextField || currentNode instanceof DatePicker) {
            currentNode.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    if (customActions.containsKey(currentNode)){
                        customActions.get(currentNode).run();
                    }
                    nextNode.requestFocus();
                    event.consume();
                }
            });
        } else if (currentNode instanceof Button button) {
            button.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    button.fire();
                    event.consume();
                }
            });
        }
    }

    /**
     * Retrieves all text fields from a grid pane.
     * @param gridPane The grid pane to search
     * @return A list of all text fields in the grid pane
     */
    private List<TextField> getAllTextFields(GridPane gridPane) {
        return gridPane.getChildren().stream()
                .filter(node -> node instanceof TextField)
                .map(node -> (TextField) node)
                .collect(Collectors.toList());
        /**
         * List<TextField> textFields = new ArrayList<>();
         *
         * for (Node node : gridPane.getChildren()) {
         *     if (node instanceof TextField) {
         *         textFields.add((TextField) node);
         *     }
         * }
         *
         * return textFields;
         */
    }

    /**
     * Configures Enter key behavior for a text field to simulate Tab key press.
     * @param textField The text field to configure
     */
    private void configureEnterKeyBehavior(TextField textField) {
        textField.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                event.consume();

                KeyEvent tabEvent = new KeyEvent(
                        KeyEvent.KEY_PRESSED,
                        "", "", KeyCode.TAB,
                        false, false, false, false);
            }
        });
    }
}