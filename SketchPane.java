// Assignment #7, CSE205, Arizona State University 
// Name: Arvin Edouard
// StudentID: 1222200512
// Lecture time: Tuesday Thursday 1:30 to 2:45 
// Description: Creates drawing pad comboboxes and radiovos that allows the user to draw circles, lines, and rectangles

import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;


public class SketchPane extends BorderPane {
	
	//Task 1: Declare all instance variables listed in UML diagram
	private ArrayList<Shape> shapeList;// declares all the variables needed
	private ArrayList<Shape> tempList;
	private Button undoButton;
	private Button eraseButton;
	private Label fillColorLabel;
	private Label strokeColorLabel;
	private Label strokeWidthLabel;
	private ComboBox<String> fillColorCombo;
	private ComboBox<String> strokeColorCombo;
	private ComboBox<String> strokeWidthCombo;
	private RadioButton radioButtonLine;
	private RadioButton radioButtonRectangle;
	private RadioButton radioButtonCircle;
	private Pane sketchCanvas;
	private Color[] colors;
	private String[] strokeWidth;
	private String[] colorLabels;
	private Color currentStrokeColor;
	private Color currentFillColor;
	private int currentStrokeWidth;
	private Line line;
	private Circle circle;
	private Rectangle rectangle;
	private double x1;
	private double y1;
	
	//Task 2: Implement the constructor
	public SketchPane() {// uses constructor instanciate all the instance varbiables
		// Colors, labels, and stroke widths that are available to the user
		colors = new Color[] {Color.BLACK, Color.GREY, Color.YELLOW, Color.GOLD, Color.ORANGE, Color.DARKRED, Color.PURPLE, Color.HOTPINK, Color.TEAL, Color.DEEPSKYBLUE, Color.LIME} ;
        colorLabels = new String[] {"black", "grey", "yellow", "gold", "orange", "dark red", "purple", "hot pink", "teal", "deep sky blue", "lime"};
        fillColorLabel = new Label("Fill Color:");
        strokeColorLabel = new Label("Stroke Color:");
        strokeWidthLabel = new Label("Stroke Width:");
        strokeWidth = new String[] {"1", "3", "5", "7", "9", "11", "13"}; 
		shapeList = new ArrayList<Shape>();
		tempList = new ArrayList<Shape>();  
		fillColorCombo = new ComboBox<String>();
		strokeColorCombo = new ComboBox<>();
		strokeWidthCombo = new ComboBox<>();
		fillColorCombo.getItems().setAll(colorLabels);
		strokeColorCombo.getItems().setAll(colorLabels);
		strokeWidthCombo.getItems().setAll(strokeWidth);
		fillColorCombo.setValue(colorLabels[0]);
		strokeColorCombo.setValue(colorLabels[0]);
		strokeWidthCombo.setValue(strokeWidth[0]);
		fillColorCombo.setOnAction(new ColorHandler());
		strokeColorCombo.setOnAction(new ColorHandler());
		strokeWidthCombo.setOnAction(new WidthHandler());
		radioButtonLine = new RadioButton("Line");
		radioButtonRectangle = new RadioButton("Rectangle");
		radioButtonCircle = new RadioButton("Circle");
		ToggleGroup group = new ToggleGroup();// creates group for radio buttons
		radioButtonLine.setToggleGroup(group);// adds radio buttons to groups
		radioButtonRectangle.setToggleGroup(group);
		radioButtonCircle.setToggleGroup(group);
		radioButtonLine.setSelected(true);// defaults line as selected
		eraseButton = new Button("Erase");
		undoButton = new Button("Undo");
		eraseButton.setOnAction(new ButtonHandler());// ties buttons to handlers
		undoButton.setOnAction(new ButtonHandler());
		sketchCanvas = new Pane();
		sketchCanvas.setStyle("-fx-background-color: white;");// changes backgroud color
		HBox hComboBoxes = new HBox(20);
		hComboBoxes.setMinSize(20, 40);
		hComboBoxes.setAlignment(Pos.CENTER);
		hComboBoxes.setStyle("-fx-background-color: lightgrey;");// changes background color
		HBox buttons = new HBox(20);
		buttons.setMinSize(20, 40);
		buttons.setAlignment(Pos.CENTER);
		buttons.setStyle("-fx-background-color: lightgrey;");// changes backgrounf colo
		hComboBoxes.getChildren().addAll(fillColorLabel, fillColorCombo, strokeWidthLabel, strokeWidthCombo, strokeColorLabel, strokeColorCombo);// adds choldren to hboxes
		buttons.getChildren().addAll(radioButtonLine, radioButtonRectangle, radioButtonCircle, undoButton, eraseButton);
		this.setCenter(sketchCanvas);// adds panes to sketchpane
		this.setTop(hComboBoxes);
		this.setBottom(buttons);
		MouseHandler draw = new MouseHandler();// ties sketchCanvas to mousehandler
		sketchCanvas.setOnMousePressed(draw);
		sketchCanvas.setOnMouseDragged(draw);
		sketchCanvas.setOnMouseReleased(draw);
		fillColorCombo.setOnAction(new ColorHandler());// ties comboboxes to handler
		strokeColorCombo.setOnAction(new ColorHandler());
		strokeWidthCombo.setOnAction(new WidthHandler());
		x1 = 0.0;
		y1 = 0.0;
		currentFillColor = colors[0];
		currentStrokeColor = colors[0];
		currentStrokeWidth = Integer.parseInt(strokeWidth[0]);
    }

	private class MouseHandler implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent event) {
			// TASK 3: Implement the mouse handler for Circle and Line
			// Rectange Example given!
			if (radioButtonRectangle.isSelected()) {// checks if the rectangle radio button
				//Mouse is pressed
				if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
					x1 = event.getX();
					y1 = event.getY();
					rectangle = new Rectangle();
					rectangle.setX(x1);
					rectangle.setY(y1);
					shapeList.add(rectangle);
					rectangle.setFill(Color.WHITE);
					rectangle.setStroke(Color.BLACK);	
					sketchCanvas.getChildren().add(rectangle);
				}
				//Mouse is dragged
				else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
					if (event.getX() - x1 <0) 
						rectangle.setX(event.getX());
					if (event.getY() - y1 <0) 
						rectangle.setY(event.getY());
					rectangle.setWidth(Math.abs(event.getX() - x1));
					rectangle.setHeight(Math.abs(event.getY() - y1));

				}
				//Mouse is released
				else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
					rectangle.setFill(currentFillColor);
					rectangle.setStroke(currentStrokeColor);
					rectangle.setStrokeWidth(currentStrokeWidth);
				}
			}

			if (radioButtonCircle.isSelected()) {// checks if circle radio button is chosen
				//Mouse is pressed
				if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
					x1 = event.getX();// sets x and y to mouse x and y
					y1 = event.getY();
					circle = new Circle(); //creats circle
					circle.setCenterX(x1); //makes the center the a and y
					circle.setCenterY(y1);
					shapeList.add(circle);// adds circle to shape array
					circle.setFill(Color.WHITE);
					circle.setStroke(Color.BLACK);	
					sketchCanvas.getChildren().add(circle);// adds circle canvas
				}
				//Mouse is dragged
				else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
					circle.setRadius(getDistance(x1, y1, event.getX(), event.getY())); //gets disance of dragged mouse

				}
				//Mouse is released
				else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
					circle.setFill(currentFillColor); // fills the color and sets width
					circle.setStroke(currentStrokeColor);
					circle.setStrokeWidth(currentStrokeWidth);
				}
			}

			if (radioButtonLine.isSelected()) {// checks if line is selected
				//Mouse is pressed
				if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
					x1 = event.getX();// sets x and y with mouse position
					y1 = event.getY();
					line = new Line(x1, y1, x1, y1); // creates line with x and y
					shapeList.add(line);// adds line to shape array
					line.setStroke(Color.BLACK);	
					sketchCanvas.getChildren().add(line); // adds line to canvas
				}
				//Mouse is dragged
				else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
					line.setEndX(event.getX());// when dragged chanfes the end points of line to mouse x and y
					line.setEndY(event.getY());

				}
				//Mouse is released
				else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
					line.setStroke(currentStrokeColor);// sets color and width
					line.setStrokeWidth(currentStrokeWidth);
				}
			}
		}
	}
		
	private class ButtonHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			// TASK 4: Implement the button handler
			if(event.getSource() == undoButton){// chacks if undo is pressed
				if(shapeList.size() > 0){// checks if shpae list has any shapes
					int last = shapeList.size() - 1;
					sketchCanvas.getChildren().remove(shapeList.get(last));
					shapeList.remove(last);
				}else if(shapeList.size() == 0){ // checks is shape list is empty
					for(int i = 0; i < tempList.size(); i++){
						shapeList.add(tempList.get(i));
					}
					tempList.clear();
					sketchCanvas.getChildren().addAll(shapeList);
				}
			}

			if(event.getSource() == eraseButton && shapeList.size() > 0){ // checks if erase of pressed and shape array has any shapes
				tempList.clear();
				for(int i = 0; i < shapeList.size(); i++){
					tempList.add(shapeList.get(i));
				}
				shapeList.clear();
				sketchCanvas.getChildren().clear();
			}
		}
	}

	private class ColorHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			// TASK 5: Implement the color handler
			int fillIndex = fillColorCombo.getSelectionModel().getSelectedIndex();
			currentFillColor = colors[fillIndex];
			int strokeIndex = strokeColorCombo.getSelectionModel().getSelectedIndex();
			currentStrokeColor = colors[strokeIndex];
		}
	}
	
	private class WidthHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event){
			// TASK 6: Implement the stroke width handler
			currentStrokeWidth = Integer.parseInt(strokeWidthCombo.getValue());
		}
	}
	
		
	// Get the Euclidean distance between (x1,y1) and (x2,y2)
    private double getDistance(double x1, double y1, double x2, double y2)  {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

}
