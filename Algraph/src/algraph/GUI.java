package algraph;


import java.io.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;


public class GUI extends Application{
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private int action=0;
    private int num_scene =1;
    private String file_name= null;
    private HBox close = new HBox(2);
    private HBox close_and_back = new HBox(2);
    private Button button_back =new Button("<-");
    private Button button_close =new Button("X");
    private  double xOffset = 0;
    private double yOffset = 0;
    private File file;
    private Graph grafo = new Graph();
    private Graph grafo_di_appoggio = new Graph();
    private Johnson j;


    @Override
    public void start(Stage primaryStage) {

        Stage window;
        Button button_start =new Button("Inizia!");
        Button button_upload =new Button("Carica da un file esterno");
        Button button_casual =new Button("Genera casualmente");
        Button button_restart =new Button("Inizia da zero");
        Button button_choose = new Button("Scegli");
        Button button_show = new Button("Visualizza!");
        Button button_modify= new Button("Modifica");
        Button button_export = new Button("Esporta");
        Button button_export_cammini = new Button("Esporta il cammino minimo");
        Button button_johnson = new Button ("Cammino minimo");
        Button button_next = new Button("Avanti");
        Button button_final = new Button("Mostra direttamente il risualtato");
        Button button_conferm = new Button("Conferma modifica");
        Button aggiungi_nodo = new Button("Aggiungi nodo");
        Button elimina_nodo = new Button("Elimina nodo");
        Button aggiungi_arco = new Button("Aggiungi arco");
        Button elimina_arco = new Button("Elimina arco");
        Button peso_arco = new Button("Modifica peso arco");
        Button direzione_arco = new Button("Modifica direzione arco");
        ComboBox<Character> combobox1= new ComboBox<>();
        ComboBox<Character> combobox2= new ComboBox<>();
        ComboBox<Integer> combobox3 = new ComboBox<>();
        Slider slider = new Slider(1,10,10);
        Label label1 =new Label("Benvenuto su Algraph");
        Label label2 =new Label("Crea un nuovo grafo o riprendi da dove ti eri fermato");
        Label label3 = new Label("Ricerca il grafo");
        Label label4= new Label("Prima dicci alcune informazioni");
        Label label5 = new Label ("Crea il tuo grafo");
        VBox vbox2 = new VBox(0);
        VBox vbox3 = new VBox(0);
        HBox hcombobox = new HBox(20);
        VBox vcombobox = new VBox();
        BorderPane layout1= new BorderPane();
        BorderPane layout2= new BorderPane();
        BorderPane layout3= new BorderPane();
        BorderPane layout4= new BorderPane();
        BorderPane layout5= new BorderPane();
        BorderPane layout6= new BorderPane();
        BorderPane layout7= new BorderPane();
        BorderPane layout8= new BorderPane();
        Scene scene1= new Scene(layout1, WIDTH, HEIGHT);
        Scene scene2= new Scene(layout2, WIDTH, HEIGHT);
        Scene scene3= new Scene(layout3, WIDTH, HEIGHT);
        Scene scene4= new Scene(layout4, WIDTH, HEIGHT);
        Scene scene5= new Scene(layout5, WIDTH, HEIGHT);
        Scene scene6= new Scene(layout6, WIDTH, HEIGHT);
        Scene scene7= new Scene(layout7, WIDTH, HEIGHT);
        Scene scene8= new Scene(layout8, WIDTH, HEIGHT);
        BackgroundImage background1= new BackgroundImage(new Image("/img/p1.png",WIDTH, HEIGHT,false,true), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        BackgroundImage background2 = new BackgroundImage(new Image("/img/p2.png",WIDTH, HEIGHT,false,true), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        BackgroundImage background3 = new BackgroundImage(new Image("/img/p3.png",WIDTH, HEIGHT,false,true), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        BackgroundImage background4 = new BackgroundImage(new Image("/img/p4.png",WIDTH, HEIGHT,false,true), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Font.loadFont(GUI.class.getResource("/font/SIMPLIFICA.ttf").toExternalForm(), 30);

        window=primaryStage;

        //BOTTONI
        button_start.setId("title_button");
        button_start.setOnAction(e -> {
            num_scene =2;
            if(!close.getChildren().contains(button_close))
                close.getChildren().add(button_close);
            label2.setId("normal_label");
            if(!(vbox2.getChildren().contains(label2) && vbox2.getChildren().contains(close))) {
                vbox2.getChildren().clear();
                vbox2.getChildren().addAll(close, label2);
            }
            HBox hbox = new HBox(10);
            hbox.getChildren().addAll(button_upload, button_casual, button_restart);
            hbox.setMinWidth(WIDTH);
            hbox.setAlignment(Pos.CENTER);
            layout2.setTop(vbox2);
            layout2.setCenter(hbox);
            layout2.setBackground(new Background(background2));
            scene2.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());
            window.setScene(scene2);
        });

        button_upload.setOnAction(e -> {
            num_scene =3;
            check_closebutton();
            check_vbox(vbox3, label3);
            label3.setId("normal_label");
            scene3.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());
            window.setScene(scene3);
        });

        button_casual.setOnAction(e-> {
            num_scene =4;
            check_closebutton();
            VBox vbox4_1 = new VBox(0);
            label4.setId("normal_label");
            check_vbox(vbox4_1, label4);

            Label l2 = new Label("Quanti nodi deve avere il grafo?");
            l2.setId("middle_label");

            slider.setShowTickLabels(true);
            slider.setShowTickMarks(true);
            slider.setMajorTickUnit(1f);
            slider.setBlockIncrement(1f);
            slider.valueProperty().addListener((obs, oldval, newVal) ->slider.setValue(newVal.intValue()));
            slider.setId("slider");

            VBox vbox4_2 = new VBox(15);
            vbox4_2.getChildren().addAll(l2, slider);
            vbox4_2.setAlignment(Pos.CENTER);
            vbox4_2.setMaxSize(800, 100);
            HBox center = new HBox();
            center.getChildren().add(button_show);
            center.setAlignment(Pos.CENTER);

            layout4.setTop(vbox4_1);
            layout4.setCenter(vbox4_2);
            layout4.setBottom(center);
            layout4.setBackground(new Background(background2));
            scene4.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());
            window.setScene(scene4);
        });

        //BOTTONE CHE CREA UN GRAFO CON UN SOLO NODO
        button_restart.setOnAction(e-> {
            grafo.insertNode();
            button_show.fire();
        });

        //TUTTI I BOTTONI INERENTI ALLA MODIFICA DEL GRAFO
        button_modify.setOnAction(e->{
            num_scene =6;
            check_closebutton();
            Label l1 = new Label("Modifica");
            l1.setId("normal_label");
            l1.setMinSize(WIDTH,0);
            VBox vbox = new VBox(5);
            check_vbox(vbox, l1);
            vbox.setAlignment(Pos.CENTER);

            VBox vbox1 = new VBox(5);
            vbox1.getChildren().addAll(aggiungi_nodo, elimina_nodo, aggiungi_arco, elimina_arco, peso_arco, direzione_arco);
            vbox1.setMaxSize(350, 100);

            combobox1.setVisibleRowCount(8);
            combobox2.setVisibleRowCount(8);
            combobox3.setVisibleRowCount(8);
            combobox3.getItems().addAll(1,2,3,4,5,6,7,8,9,10);

            hcombobox.getChildren().clear();
            hcombobox.setAlignment(Pos.CENTER);
            hcombobox.setMinWidth(WIDTH);

            vcombobox.getChildren().clear();
            vcombobox.getChildren().add(print_collegamenti());

            layout6.setTop(vbox);
            layout6.setLeft(vbox1);
            layout6.setRight(vcombobox);
            layout6.setBackground(new Background(background3));

            scene6.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());
            window.setScene(scene6);
        });

        //BOTTONE AGGIUNGI NODO
        aggiungi_nodo.setOnAction(e->{
            action=1;
            Label label = new Label("Sicuro di voler aggiungere un nuovo nodo?");
            label.setId("middle_label");
            hcombobox.getChildren().clear();
            hcombobox.getChildren().addAll(label, button_conferm);

            layout6.setBottom(hcombobox);
        });

        //BOTTONE ELIMINA NODO
        elimina_nodo.setOnAction(e->{
            action=2;
            Label label = new Label("Scegli il nodo da eliminare: ");
            label.setId("middle_label");
            hcombobox.getChildren().clear();
            hcombobox.getChildren().addAll(label, add_combobox(combobox1), button_conferm);

            layout6.setBottom(hcombobox);
        });

        aggiungi_arco.setOnAction(e->{
            action=3;
            Label l = new Label("Il nuovo arco andrà dal nodo ");
            Label l2 = new Label(" al nodo ");
            Label l3 = new Label(" e avrà peso ");
            l.setId("middle_label");
            l2.setId("middle_label");
            l3.setId("middle_label");
            hcombobox.getChildren().clear();
            hcombobox.getChildren().addAll(l, add_combobox(combobox1), l2, add_combobox(combobox2), l3, combobox3, button_conferm);

            layout6.setBottom(hcombobox);
        });

        //BOTTONO ELIMINA ARCO
        elimina_arco.setOnAction(e->{
            action=4;
            Label l = new Label("Elimina l'arco che va dal nodo ");
            Label l2 = new Label(" al nodo ");
            l.setId("middle_label");
            l2.setId("middle_label");
            hcombobox.getChildren().clear();
            hcombobox.getChildren().addAll(l, add_combobox(combobox1), l2, add_combobox(combobox2), button_conferm);

            layout6.setBottom(hcombobox);
        });

        //BOTTONE MODIFICA PESO ARCO
        peso_arco.setOnAction(e->{
            action=5;
            Label l = new Label("L'arco che va dal nodo ");
            Label l2 = new Label(" al nodo ");
            Label l3 = new Label(" avrà peso ");
            l.setId("middle_label");
            l2.setId("middle_label");
            l3.setId("middle_label");
            hcombobox.getChildren().clear();
            hcombobox.getChildren().addAll(l, add_combobox(combobox1), l2, add_combobox(combobox2), l3, combobox3, button_conferm);

            layout6.setBottom(hcombobox);
        });

        //BOTTONE MODIFICA DIREZIONE ARCO
        direzione_arco.setOnAction(e->{
            action=6;
            Label l = new Label("L'arco che va dal nodo ");
            Label l2 = new Label(" al nodo ");
            Label l3 = new Label(" avrà direzione inversa ");
            l.setId("middle_label");
            l2.setId("middle_label");
            l3.setId("middle_label");
            hcombobox.getChildren().clear();
            hcombobox.getChildren().addAll(l, add_combobox(combobox1), l2, add_combobox(combobox2), l3, button_conferm);

            layout6.setBottom(hcombobox);
        });

        //BOTTONE CHE CONFERMA OGNI MODIFICA
        button_conferm.setOnAction(e->{
            if(action==1){
                //AGGIUNGI NODO
                if(!grafo.insertNode()) {
                    call_warning("Hai raggiunto in numero massimo di nodi");
                    button_modify.fire();
                }
            }
            else if(action==2){
                //ELIMINA NODO

                if(combobox1.getValue()== null || !grafo.deleteNode(combobox1.getValue())){
                    call_warning("Si è verificata una delle seguenti situazioni: \n" +
                            "- E' rimasto un solo nodo nel grafo, non puoi eliminarlo \n" +
                            "- Il campo è vuoto");
                    button_modify.fire();
                }
            }
            else if(action==3) {
                //AGGIUNGI ARCO
                if (combobox1.getValue()== null || combobox2.getValue()== null || combobox3.getValue()== null ||!grafo.insertEdge(combobox1.getValue(), combobox2.getValue(), combobox3.getValue())) {

                    call_warning("Si è verificata una delle seguenti situazioni: \n" +
                            "- Il peso non è accettabile(tra 1 e 10) \n" +
                            "- Il peso non è accettabile(tra 1 e 10) \n" +
                            "- Il nodo di partenza è uguale a quello di arrivo \n" +
                            "- L'arco esiste già");
                    button_modify.fire();
                }
            }
            else if(action==4){
                //ELIMINA ARCO

                if(combobox1.getValue()== null || combobox2.getValue()== null || !grafo.deleteEdge(combobox1.getValue(), combobox2.getValue())){
                    call_warning("Si è verificata una delle seguenti situazioni: \n" +
                            "- Il peso non è accettabile(tra 1 e 10) \n" +
                            "- Il nodo di partenza è uguale a quello di arrivo \n" +
                            "- L'arco non esiste");
                    button_modify.fire();
                }
            }
            else if(action==5){
                //MODIFICA PESO ARCO
                    if(combobox1.getValue()== null || combobox2.getValue()== null || combobox3.getValue()== null ||!grafo.modificaPeso(combobox1.getValue(), combobox2.getValue(), combobox3.getValue())){
                    call_warning("Si è verificata una delle seguenti situazioni: \n" +
                            "- Il peso non è accettabile(tra 1 e 10) \n" +
                            "- Il peso non è accettabile(tra 1 e 10) \n" +
                            "- Il nodo di partenza è uguale a quello di arrivo \n" +
                            "- L'arco non esiste");
                    button_modify.fire();
                    }
            }
            else if(action==6){
                //MODIFICA DIREZIONE ARCO
                if(combobox1.getValue()== null || combobox2.getValue()== null ||!grafo.invertiDirezione(combobox1.getValue(), combobox2.getValue())){
                    call_warning("Si è verificata una delle seguenti situazioni: \n" +
                            "- Il peso non è accettabile(tra 1 e 10) \n" +
                            "- Il nodo di partenza è uguale a quello di arrivo \n" +
                            "- L'arco non esiste");
                    button_modify.fire();
                }
            }
            button_show.fire();
        });

        button_choose.setOnAction(e ->{
            num_scene =3;
            check_closebutton();
            check_vbox(vbox3, label3);

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Cerca un grafo");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("*.txt", "*.txt"));
            file =fileChooser.showOpenDialog(window);

            try {
                if (grafo.fileVuoto(file) ) {
                    call_warning("Non ho trovato nessun file o forse il file che hai caricato è vuoto");
                    button_upload.fire();
                }
                else {
                    if(!file_corretto(file)){
                        call_warning("Abbiamo riscontrato degli errori nel file");
                        button_upload.fire();
                    }
                    else {
                        file_name=file.getName();

                        Label l_name = new Label();
                        l_name.setText("File: " + file_name);
                        l_name.setMinSize(WIDTH, 100);
                        button_choose.setText("Cambia");

                        HBox h1 = new HBox(5);
                        h1.getChildren().addAll(l_name, button_show, button_choose);
                        h1.setAlignment(Pos.CENTER);
                        h1.minWidth(WIDTH);

                        VBox vbox = new VBox(5);
                        vbox.getChildren().addAll(l_name, h1);
                        vbox.setAlignment(Pos.CENTER);

                        layout3.setTop(vbox3);
                        layout3.setCenter(vbox);
                        scene3.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());
                        window.setScene(scene3);
                    }
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        button_export_cammini.setOnAction(e->{
            FileChooser fileChooser = new FileChooser();

            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("txt", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);

            file = fileChooser.showSaveDialog(primaryStage);
            grafo.SaveFile(grafo.esporta_cammino(), file);
        });

        button_close.setOnAction(e -> window.close());
        button_close.setId("button-up");


        //QUANDO SI PREME BOTTONE BACK
        button_back.setOnAction(e -> {
            if(num_scene==6){
                num_scene =5;
                if(!close.getChildren().contains(button_close))
                    close.getChildren().add(button_close);
                scene5.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());
                window.setScene(scene5);
            }
            else {
                num_scene = 2;
                if (!close.getChildren().contains(button_close))
                    close.getChildren().add(button_close);
                scene2.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());
                window.setScene(scene2);
            }
        });
        button_back.setId("button-up");


        //SCHERMATA DI VISUALIZZAZIONE DEL GRAFO (MODIFICA, ESPORTA, JHONSON)
        button_show.setOnAction(e-> {
            if(num_scene==4){
                int n= (int)slider.getValue();
                grafo= new Graph(n);
            }
            if(num_scene==3){
                grafo=grafo_di_appoggio;
            }
            num_scene =5;

            if(!close.getChildren().contains(button_close))
                close.getChildren().add(button_close);

            HBox hbox = new HBox(10);
            hbox.getChildren().addAll(button_modify, button_export, button_johnson);
            hbox.setAlignment(Pos.CENTER_LEFT);
            hbox.setMinWidth(WIDTH);


            layout5.setTop(close);
            layout5.setCenter(print_graph());
            layout5.setLeft(print_collegamenti());
            layout5.setBottom(hbox);
            layout5.setBackground(new Background(background3));
            scene5.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());
            window.setScene(scene5);

        });

        //QUANDO SI CERCA DI ESPORTARE IL GRAFO
        button_export.setOnAction(e-> {
            FileChooser fileChooser = new FileChooser();

            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("txt", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);

            file = fileChooser.showSaveDialog(primaryStage);
            grafo.SaveFile(grafo.esporta(), file);
        });

        button_johnson.setOnAction(e-> {
            num_scene =7;
            if(!grafo.partenzaJohnson()){
                call_warning("Un nodo è isolato");
                button_show.fire();
            }
            else {
                j = new Johnson(grafo);

                if (!close.getChildren().contains(button_close))
                    close.getChildren().add(button_close);

                HBox hbox = new HBox(10);
                hbox.getChildren().addAll(button_next, button_final);
                hbox.setAlignment(Pos.CENTER);

                layout7.setTop(close);
                layout7.setBottom(hbox);
                layout7.setCenter(print_graph());
                layout7.setLeft(print_route(false));
                layout7.setBackground(new Background(background4));
                scene7.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());
                window.setScene(scene7);
            }
        });

        button_next.setOnAction(e->{
            if(!j.nextpasso()) {
                button_final.fire();
            }
            else {
                layout7.setCenter(print_graph());
                layout7.setLeft(print_route(false));
            }
        });

        button_final.setOnAction(e->{
            j.shortestPath();
            layout7.setCenter(print_graph());
            layout7.setLeft(print_route(false));
            if(!close.getChildren().contains(button_close))
                close.getChildren().add(button_close);

            HBox hbox = new HBox();
            hbox.getChildren().add(button_export_cammini);
            hbox.setAlignment(Pos.CENTER);


            layout8.setTop(close);
            layout8.setBottom(hbox);
            layout8.setCenter(print_graph());
            layout8.setLeft(print_route(true));
            layout8.setBackground(new Background(background4));
            scene8.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());
            window.setScene(scene8);
        });


        //ETICHETTE
        label1.setId("title");
        label1.setMinSize(WIDTH,350);
        label2.setMinSize(WIDTH,0);
        label3.setMinSize(WIDTH,0);
        label4.setMinSize(WIDTH,0);
        label5.setMinSize(WIDTH,0);

        //BOX
        //close.getChildren().add(button_close);
        close.setMinWidth(WIDTH);
        close.setAlignment(Pos.BASELINE_RIGHT);

        //close_and_back.getChildren().addAll(button_back, button_close);
        close_and_back.setMinWidth(WIDTH);
        close_and_back.setAlignment(Pos.BASELINE_RIGHT);

        //LAYOUT
        layout1.setTop(label1);
        layout1.setCenter(button_start);
        layout1.setBackground(new Background(background1));

        layout3.setTop(vbox3);
        layout3.setCenter(button_choose);
        layout3.setBackground(new Background(background2));

        //SCENE
        scene1.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());
        scene6.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());

        //FUNZIONI PER LA FINESTRA BORDERLESS
        //window.initStyle(StageStyle.UNDECORATED);
        layout1.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        layout1.setOnMouseDragged(event -> {
            window.setX(event.getScreenX() - xOffset);
            window.setY(event.getScreenY() - yOffset);
        });

        //STAGE
        window.setTitle("Algraph - Jhonson");
        window.setScene(scene1);
        window.setResizable(false);
        window.show();


    }

    static void first(String[] args){
        launch(args);
    }

    private void check_closebutton(){
        if(!(close_and_back.getChildren().contains(button_back) && close_and_back.getChildren().contains(button_close))){
            close_and_back.getChildren().clear();
            close_and_back.getChildren().addAll(button_back, button_close);
        }
    }

    private void check_vbox(VBox vbox, Label label){
        if(!(vbox.getChildren().contains(label) && vbox.getChildren().contains(close_and_back))) {
            vbox.getChildren().clear();
            vbox.getChildren().addAll(close_and_back, label);
        }
    }

    private ComboBox<Character> add_combobox(ComboBox<Character> box){
        box.getItems().clear();
        for(char i= 'A'; i<'K'; i++){
            if(grafo.appartiene(i))
                box.getItems().add(i);
        }
        return box;
    }

    //STAMPA UN MESSAGGIO DI WARNING
    private void call_warning(String string){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Errore");
        alert.setHeaderText("Attento, quanlcosa non è andata come previsto!");
        alert.setContentText(string);

        alert.showAndWait();
    }

    //FUNZIONE CHE STAMPA GRAFICAMENTE IL GRAFO
    private HBox print_graph (){
        int n= grafo.size();
        double angle=360/n;
        int size = 560;
        int raggio= size/2;
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.TOP_CENTER);
        hbox.setMinSize(WIDTH, size);
        hbox.getHeight();
        double CENTER_Y = size / 2;
        double CENTER_X = size / 2;
        // Circle c = new Circle(CENTER_X, CENTER_Y, raggio);
        //c.setId("circle");
        Pane pane = new Pane();

        //SETTAGGI POSIZIONI NODI
        int j=0;
        for(char i='A'; i<'K'; i++) {
            if (grafo.appartiene(i)) {
                double angleAlpha = (j * angle) * (Math.PI / 180);
                double pointX1 = CENTER_X + raggio * Math.sin(angleAlpha);
                double pointY1 = CENTER_Y - raggio * Math.cos(angleAlpha);
                grafo.set_x_y(i, pointX1, pointY1);
                j++;
            }
        }

        //CREAZIONE ARCHI
        for(char p='A'; p<'K'; p++)
            if (grafo.appartiene(p)) {
                for (char k = 'A'; k < 'K'; k++)
                    if (grafo.collegato(p, k)) {
                        double x1 = grafo.get_x(p);
                        double y1 = grafo.get_y(p);
                        double x2 = grafo.get_x(k);
                        double y2 = grafo.get_y(k);
                        Line line = new Line(x1, y1, x2, y2);
                        line.setStrokeWidth(2);

                        Text arrow = new Text(String.valueOf('>'));
                        arrow.setId("line-arrow");

                        if ((grafo.getNode(p).isVisitato() && grafo.getNode(k).isVisitato()) && (grafo.getNode(p).getT() == grafo.getNode(k) || grafo.getNode(k).getT() == grafo.getNode(p))) {
                            line.setId("route_line");
                            line.setStroke(Color.DARKRED);
                            arrow.setStroke(Color.DARKRED);
                        } else if (grafo.getNode(p).getT() == grafo.getNode(k) || grafo.getNode(k).getT() == grafo.getNode(p)) {
                            line.setId("adj_line");
                            line.getStrokeDashArray().addAll(25d, 10d);
                        } else {
                            line.setId("normal_line");
                        }

                        double ax = (((grafo.get_x(p) + grafo.get_x(k)) / 2) + grafo.get_x(k)) / 2;
                        double ay = (((grafo.get_y(p) + grafo.get_y(k)) / 2) + grafo.get_y(k)) / 2;
                        arrow.setX(((ax + grafo.get_x(k)) / 2) - 4);
                        arrow.setY(((ay + grafo.get_y(k)) / 2) + 7);

                        double cateto1; //p
                        double cateto2; //v
                        double a = 0;
                        double ipotenusa;
                        if (x1 <= x2 && y1 <= y2) {
                            cateto1 = y2 - y1;
                            cateto2 = x2 - x1;
                            ipotenusa = Math.sqrt(Math.pow(cateto1, 2) + Math.pow(cateto2, 2));
                            a = Math.acos(cateto2 / ipotenusa);
                            a = convertitore(a);
                        } else if (x1 >= x2 && y1 <= y2) {
                            cateto1 = x1 - x2;
                            cateto2 = y2 - y1;
                            ipotenusa = Math.sqrt(Math.pow(cateto1, 2) + Math.pow(cateto2, 2));
                            a = convertitore(Math.asin(cateto1 / ipotenusa)) + 90;
                        } else if (x1 >= x2 && y1 >= y2) {
                            cateto1 = y1 - y2;
                            cateto2 = x1 - x2;
                            ipotenusa = Math.sqrt(Math.pow(cateto1, 2) + Math.pow(cateto2, 2));
                            a = Math.acos(cateto2 / ipotenusa);
                            a = convertitore(a) + 180;
                        } else if (x1 <= x2 && y1 >= y2) {
                            cateto1 = x2 - x1;
                            cateto2 = y1 - y2;
                            ipotenusa = Math.sqrt(Math.pow(cateto1, 2) + Math.pow(cateto2, 2));
                            a = Math.asin(cateto1 / ipotenusa);
                            a = convertitore(a) + 270;
                        }

                        arrow.setRotate(a);

                        Text t = new Text(String.valueOf(grafo.getPeso(p, k)));
                        t.setId("line-text");
                        t.setFill(Color.WHITE);

                        t.setX(ax - 2);
                        t.setY(ay + 4);
                        pane.getChildren().addAll(line, t, arrow);
                    }
            }

        //CREAZIONE NODI
        for(char i='A'; i<'K'; i++) {
            if (grafo.appartiene(i)) {
                Circle p = new Circle(grafo.get_x(i), grafo.get_y(i), 20);
                if(grafo.getNode(i).isVisitato()){
                    p.setId("route-circle");
                }else if(grafo.getNode(i).isB()){
                    p.setId("adj-circle");
                }
                else{
                    p.setId("normal-circle");
                }
                Text l = new Text(String.valueOf(i));
                l.setId("node-text");

                l.setX(grafo.get_x(i) - 2);
                l.setY(grafo.get_y(i) + 4);
                pane.getChildren().addAll(p,l);
            }
        }

        pane.setMaxSize(size, size);
        pane.setId("pane");
        //pane.getChildren().add(c);
        hbox.getChildren().add(pane);
        return hbox;
    }

    //CONVERTE IL GRADO DEGLI ANGOLI IN RADIANTI
    private double convertitore(double n){
        n=n*57.2958;
        return n;
    }

    //MOSTRA SULLA GUI IL GRAFO DEI CAMMINI MINIMI
    private VBox print_route(boolean b){
        VBox v = new VBox();
        v.setMaxWidth(200);
        int peso_totale=0;
        StringBuilder s= new StringBuilder("     Cammino minimo: \n");
        for(char p='A'; p<'K'; p++) {
            if (grafo.appartiene(p)) {
                for (char k = 'A'; k < 'K'; k++) {
                    if (grafo.appartiene(k)) {
                        if ((grafo.getNode(p).isVisitato() && grafo.getNode(k).isVisitato()) && grafo.getNode(p).getT() == grafo.getNode(k)){
                            if(grafo.collegato(p,k)){
                                s.append("     ").append(p).append(" -> ").append(k).append(" peso ").append(grafo.getPeso(p, k)).append("\n");
                                peso_totale+=grafo.getPeso(p,k);
                            }
                            else if(grafo.collegato(k,p)){
                                s.append("     ").append(k).append(" -> ").append(p).append(" peso ").append(grafo.getPeso(k, p)).append("\n");
                                peso_totale+=grafo.getPeso(k,p);
                            }
                        }
                    }
                }
            }
        }
        if(b){
            s.append("Con un peso totale di ").append(peso_totale);
        }
        Label l =new Label(s.toString());
        l.setId("table");
        v.getChildren().add(l);
        return v;
    }

    //MOSTRA SULLA GUI I COLLEGAMENTI UTILIZZATI PER ARRIVARE AL GRAFO DEI CAMMINI MINIMI
    private Label print_collegamenti(){
        StringBuilder s= new StringBuilder("     Collegamenti: \n");
        String q;
        for(char p='A'; p<'K'; p++) {
            if (grafo.appartiene(p)) {
                q="     "+p+":";
                s.append(q);
                for (char k = 'A'; k < 'K'; k++) {
                    if (grafo.collegato(p, k)) {
                        q = " [" + k + "," + String.valueOf(grafo.getPeso(p, k)) + "] ";
                        s.append(q);
                    }
                }
                s.append("\n");
            }
        }
        Label l =new Label(s.toString());
        l.setId("table");
        return l;
    }

    //CONTROLLA CHE IL FILE SIA CORRETTO E CREA IL GRAFO (FA L'IMPORT)
    public boolean file_corretto(File file) throws IOException {
        boolean b = true;

        FileReader filereader = new FileReader(file);
        BufferedReader lettore = new BufferedReader(filereader);
        String riga = lettore.readLine();
        if(riga==null) {
            System.out.println("Errore riga == null");
            b = false;
            return b;
        }
        else {
            while(riga != null){
                if(riga.length()>=4) {
                    if ((riga.charAt(1) == ':' && grafo_di_appoggio.inserisciNodoTXT(riga.charAt(0)) && riga.charAt(riga.length() - 2) == '.' && grafo_di_appoggio.lettere(riga.charAt(0))) != true) {
                        //controlli sui primi due char e gli ultimi due char
                        System.out.println("Errore riga formattazione file");
                        b = false;
                        return b;
                    }
                    riga = lettore.readLine();
                }
                else{
                    System.out.println("Errore spazio");
                    b = false;
                    return b;
                }
            }

            filereader = new FileReader(file);
            lettore = new BufferedReader(filereader);
            riga = lettore.readLine();

            do {
                int i = 2;
                while (riga.charAt(i) != '.' && i < 40){
                    if (riga.charAt(i) < 'A' || riga.charAt(i) > 'J' || riga.charAt(i + 1) < '1' || riga.charAt(i + 1) > '9'){
                        b = false;
                        System.out.println("primo return");
                        return b;
                    }
                    if(riga.charAt(i+1)=='1' && riga.charAt(i+2)=='0') {
                        if(riga.charAt(i+3)!=',') {
                            b = false;
                            System.out.println("secondo return");
                            return b;
                        }
                        grafo_di_appoggio.insertEdge(riga.charAt(0), riga.charAt(i), 10);
                        i = i + 4;
                    }
                    else{
                        if(riga.charAt(i+2)!=',') {
                            b = false;
                            System.out.println("terzo return");
                            return b;
                        }
                        grafo_di_appoggio.insertEdge(riga.charAt(0), riga.charAt(i), (int)riga.charAt(i+1)-48);
                        i=i+3;
                    }
                }
                riga=lettore.readLine();
                grafo_di_appoggio.print();
                System.out.println();
            }while(riga != null);
}
        return b;
    }
}
