/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copladb;

import java.awt.Dimension;
import java.time.LocalDate;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import java.util.ArrayList;
import java.util.List;

import copladb.DAO.detalleVentaDAO;
import copladb.DTO.detalle_venta;
import copladb.DAO.clienteDAO;
import copladb.DTO.cliente;
import copladb.DAO.vendedorDAO;
import copladb.DTO.vendedor;
import copladb.DAO.cobradorDAO;
import copladb.DTO.cobrador;
import copladb.DAO.inventarioDAO;
import copladb.DTO.inventario;
import copladb.DAO.pagosProyectadosDAO;
import copladb.DTO.pagosProyectados;
import copladb.DAO.pagosRealizadosDAO;
import copladb.DTO.pagosRealizados;
import copladb.DTO.tarjetasAsigPorSemanaConPagosDTO;
import copladb.DTO.tarjetaPorSemana;
import copladb.DAO.tarjetaDAO;
import copladb.DTO.tarjeta;
import copladb.DAO.tarjetaAsignadasDAO;
import copladb.DTO.tarjetaAsignadas;
import copladb.DAO.gastoDAO;
import copladb.DTO.gasto;
import copladb.DTO.usuario;
import copladb.DAO.usuarioDAO;
import copladb.DTO.accesos;
import copladb.DAO.accesosDAO;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.function.Predicate;
import javafx.geometry.HPos;
import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Modality;
/**
 *
 * @author i7
 */


public class COPLADB extends Application {
    VBox vbAreaTrabajo= new VBox();
    double anchoPantalla;
    double altoPantalla;
    
    tarjetaDAO tarDAO = new tarjetaDAO();
    tarjeta tarDTO = new tarjeta();
    tarjetaAsignadasDAO tarAsigDAO = new tarjetaAsignadasDAO();
    tarjetaAsignadas tarAsigDTO = new tarjetaAsignadas();
    clienteDAO cliDAO = new clienteDAO();
    cliente cliDTO = new cliente();
    vendedorDAO venDAO = new vendedorDAO();
    vendedor venDTO = new vendedor();
    cobradorDAO cobraDAO = new cobradorDAO();
    cobrador cobraDTO = new cobrador();
    inventarioDAO invDAO = new inventarioDAO();
    inventario invDTO = new inventario();
    gastoDAO gasDAO = new gastoDAO();
    gasto gasDTO = new gasto();
    detalleVentaDAO detVentaDAO = new detalleVentaDAO();
    detalle_venta detVentaDTO = new detalle_venta();
    pagosProyectadosDAO pagProyDAO = new pagosProyectadosDAO();
    pagosRealizadosDAO pagReaDAO = new pagosRealizadosDAO();
    usuarioDAO usuDAO = new usuarioDAO();
    usuario usua = new usuario();
    accesosDAO acceDAO = new accesosDAO();
    accesos miAcce = new accesos();
    accesos actAcce = new accesos();
    
    List<tarjeta> lstTarjetas = new ArrayList<>();
    List<tarjetaAsignadas> lstTarjetasAsignadas = new ArrayList<>();
    List<inventario> lstInventario = new ArrayList<>();
    List<detalle_venta> lstDetVenta= new ArrayList<>();
    ObservableList<pagosProyectados> lstobPagProyectados = FXCollections.observableArrayList();
    ObservableList<pagosRealizados> lstobPagRealizados = FXCollections.observableArrayList();
    ObservableList<tarjetasAsigPorSemanaConPagosDTO> lstTarjetasAsigPorSemanaConPagos = FXCollections.observableArrayList();
    ObservableList<tarjetaPorSemana> lstTarjetasPorSemanaConEnganches = FXCollections.observableArrayList();
    List<cliente> lstCliente = new ArrayList<>();
    List<vendedor> lstVendedor = new ArrayList<>();
    List<String> lstWhere = new ArrayList<>();
    ObservableList<usuario> lstUsuarios = FXCollections.observableArrayList();
    
    float GranTotal = 0.0f;
    int ContNumPagosProyectados = 0;
    int ContNumPagosRealizado = 0;
    Stage primaryStageLocal;

    Boolean nueva_tarjetas, modificar_consultar_Tarjeta, eliminar_tarjeta,
    asignar_tarjeta, estado_cobranza, agregar_cliente, modificar_consultar_cliente,
    eliminar_cliente,lista_negra_cliente, agregar_vendedor, modificar_consultar_vendedor, 
    eliminar_vendedor, agregar_cobrador, modificar_consultar_cobrador, eliminar_cobrador,
    estado_corte_cobrador, agregar_producto, modificar_consultar_producto, eliminar_producto,
    estadisticas_producto, agregar_gasto, modificar_consultar_gasto, eliminar_gasto, acumulado_caja,
    nuevo_usuario, modificar_usuario, eliminar_usuario, acceso_usuarios, gestion_bonos;
    
    float totalCobrado =0;
    float comision = 0;
    float PorComision = (float) 0.12;
    
    //Opciones del menu Tarjetas
    MenuItem miNuevaTarjeta = new MenuItem("Nueva Tarjeta");
    MenuItem miModificarTarjeta= new MenuItem("Modificar/Consultar Tarjeta");
    MenuItem miEliminarTarjeta= new MenuItem("Eliminar Tarjeta");
    MenuItem miAsignarTarjetas =new MenuItem("Asignar Tarjetas"); //asigna tarjetas a cobradores
    //Opciones del menu cobranza
    MenuItem miEstadoTarjetas = new MenuItem("Estado Cobranza");
    //Opciones del menu Clientes
    MenuItem miRegistrarClientes = new MenuItem("Agregar Cliente");
    MenuItem miModificarClientes = new MenuItem("Modificar/Consultar Cliente");
    MenuItem miEliminarClientes = new MenuItem("Eliminar Cliente");
    MenuItem miListaNegraClientes = new MenuItem("Lista Negra de Clientes");
    //Opciones del menu Vendedor
    MenuItem miAgregarVendedor = new MenuItem("Agregar Vendedor");
    MenuItem miModificarVendedor = new MenuItem("Modificar/Consultar Vendedor");
    MenuItem miEliminarVendedor = new MenuItem("Eliminar Vendedor");
    MenuItem miEstadoVendedor = new MenuItem("Estado/Corte Vendedor");
    
    //Opciones del menu Cobrador
    MenuItem miAgregarCobrador = new MenuItem("Agregar Cobrador");
    MenuItem miModificarCobrador = new MenuItem("Modificar/Consultar Cobrador");
    MenuItem miEliminarCobrador = new MenuItem("Eliminar Cobrador");
    MenuItem miEstadoCobrador = new MenuItem("Estado/Corte Cobrador");
    //Opciones del menu Inventario
    MenuItem miAgregarProducto = new MenuItem("Agregar Producto");
    MenuItem miModificarProducto = new MenuItem("Modificar/Consultar Producto");
    MenuItem miEliminarProducto = new MenuItem("Eliminar Producto");
    MenuItem miEstadisticasProductos = new MenuItem("Estadisticas de Productos");
    //Opciones del menu Gastos
    MenuItem miAgregarGastos = new MenuItem("Agregar Gasto");
    MenuItem miModificarGastos = new MenuItem("Modificar/Consultar Gasto");
    MenuItem miEliminarGastos = new MenuItem("Eliminar Gasto");
    MenuItem miAcumuladoCaja = new MenuItem("Acumulado en caja");
    //Opciones del menu Herramientas
    //MenuItem miGestionarUsuarios = new MenuItem("Gestionar Usuarios");
    MenuItem miNuevoUsuario = new MenuItem("Nuevo Usuario");
    MenuItem miModificarUsuario = new MenuItem("Modificar Usuario");
    MenuItem miEliminarUsuario = new MenuItem("Eliminar Usuario");
    MenuItem miAccesosUsuario = new MenuItem("Accesos a Usuario");
    MenuItem miGestionBonos = new MenuItem("Gestion Bonos");
    MenuItem miSalir = new MenuItem("Salir del Sistema");
    @Override
    public void start(Stage primaryStage) {
        primaryStageLocal = primaryStage;
        
        //Opciones del menu Tarjetas
        
        miModificarTarjeta.setOnAction((event) -> {
           
            if(vbAreaTrabajo.getChildren().isEmpty()){
                vbAreaTrabajo.getChildren().add(vModificarConsultarTarjeta());
            }else{
                vbAreaTrabajo.getChildren().clear();
                vbAreaTrabajo.getChildren().add(vModificarConsultarTarjeta());
            }
        });
        
        miNuevaTarjeta.setOnAction((event) -> {
            if(vbAreaTrabajo.getChildren().isEmpty()){
                vbAreaTrabajo.getChildren().add(vNuevaTarjeta());
            }else{
                vbAreaTrabajo.getChildren().clear();
                vbAreaTrabajo.getChildren().add(vNuevaTarjeta());
            }
        });
        
        miEliminarTarjeta.setOnAction((event) -> {
            if(vbAreaTrabajo.getChildren().isEmpty()){
                vbAreaTrabajo.getChildren().add(vEliminarTarjeta());
            }else{
                vbAreaTrabajo.getChildren().clear();
                vbAreaTrabajo.getChildren().add(vEliminarTarjeta());
            }
        });
        
        miAsignarTarjetas.setOnAction((event) -> {
            if(vbAreaTrabajo.getChildren().isEmpty()){
                vbAreaTrabajo.getChildren().add(vAsignarTarjeta());
            }else{
                vbAreaTrabajo.getChildren().clear();
                vbAreaTrabajo.getChildren().add(vAsignarTarjeta());
            }
        });

        //Opciones del menu Clientes
        miRegistrarClientes.setOnAction((event) -> {
            if(vbAreaTrabajo.getChildren().isEmpty()){
                vbAreaTrabajo.getChildren().add(vRegistrarCliente());
            }else{
                vbAreaTrabajo.getChildren().clear();
                vbAreaTrabajo.getChildren().add(vRegistrarCliente());
            }
        });
        
        miModificarClientes.setOnAction((event) -> {
            if(vbAreaTrabajo.getChildren().isEmpty()){
                vbAreaTrabajo.getChildren().add(vModificarCliente());
            }else{
                vbAreaTrabajo.getChildren().clear();
                vbAreaTrabajo.getChildren().add(vModificarCliente());
            }
        });
        
        miEliminarClientes.setOnAction((event) -> {
            if(vbAreaTrabajo.getChildren().isEmpty()){
                vbAreaTrabajo.getChildren().add(vEliminarCliente());
            }else{
                vbAreaTrabajo.getChildren().clear();
                vbAreaTrabajo.getChildren().add(vEliminarCliente());
            }
        });
        
        miListaNegraClientes.setOnAction((event) -> {
            if(vbAreaTrabajo.getChildren().isEmpty()){
                vbAreaTrabajo.getChildren().add(vListaNegraCliente());
            }else{
                vbAreaTrabajo.getChildren().clear();
                vbAreaTrabajo.getChildren().add(vListaNegraCliente());
            }
        });
        
        
        //Opciones del menu Vendedor
        miAgregarVendedor.setOnAction((event) -> {
            if(vbAreaTrabajo.getChildren().isEmpty()){
                vbAreaTrabajo.getChildren().add(vRegistrarVendedor());
            }else{
                vbAreaTrabajo.getChildren().clear();
                vbAreaTrabajo.getChildren().add(vRegistrarVendedor());
            }
        });
        
        miModificarVendedor.setOnAction((event) -> {
            if(vbAreaTrabajo.getChildren().isEmpty()){
                vbAreaTrabajo.getChildren().add(vModificarVendedor());
            }else{
                vbAreaTrabajo.getChildren().clear();
                vbAreaTrabajo.getChildren().add(vModificarVendedor());
            }
        });
        
        miEliminarVendedor.setOnAction((event) -> {
            if(vbAreaTrabajo.getChildren().isEmpty()){
                vbAreaTrabajo.getChildren().add(vEliminarVendedor());
            }else{
                vbAreaTrabajo.getChildren().clear();
                vbAreaTrabajo.getChildren().add(vEliminarVendedor());
            }
        });
        
        miEstadoVendedor.setOnAction((event) -> {
            if(vbAreaTrabajo.getChildren().isEmpty()){
                vbAreaTrabajo.getChildren().add(vEstadoVendedores());
            }else{
                vbAreaTrabajo.getChildren().clear();
                vbAreaTrabajo.getChildren().add(vEstadoVendedores());
            }
        });
        
        //Opciones del menu Cobrador
        miAgregarCobrador.setOnAction((event) -> {
            if(vbAreaTrabajo.getChildren().isEmpty()){
                vbAreaTrabajo.getChildren().add(vRegistrarCobrador());
            }else{
                vbAreaTrabajo.getChildren().clear();
                vbAreaTrabajo.getChildren().add(vRegistrarCobrador());
            }
        });        

        miModificarCobrador.setOnAction((event) -> {
            if(vbAreaTrabajo.getChildren().isEmpty()){
                vbAreaTrabajo.getChildren().add(vModificarCobrador());
            }else{
                vbAreaTrabajo.getChildren().clear();
                vbAreaTrabajo.getChildren().add(vModificarCobrador());
            }
        });        
        
        miEliminarCobrador.setOnAction((event) -> {
            if(vbAreaTrabajo.getChildren().isEmpty()){
                vbAreaTrabajo.getChildren().add(vEliminarCobrador());
            }else{
                vbAreaTrabajo.getChildren().clear();
                vbAreaTrabajo.getChildren().add(vEliminarCobrador());
            }
        });
        
        miEstadoCobrador.setOnAction((event) -> {
            if(vbAreaTrabajo.getChildren().isEmpty()){
                vbAreaTrabajo.getChildren().add(vEstadoCobradores());
            }else{
                vbAreaTrabajo.getChildren().clear();
                vbAreaTrabajo.getChildren().add(vEstadoCobradores());
            }
        });

        //Opciones del menu Inventario
        miAgregarProducto.setOnAction((event) -> {
            if(vbAreaTrabajo.getChildren().isEmpty()){
                vbAreaTrabajo.getChildren().add(vRegistrarProducto());
            }else{
                vbAreaTrabajo.getChildren().clear();
                vbAreaTrabajo.getChildren().add(vRegistrarProducto());
            }
        });
        
        miModificarProducto.setOnAction((event) -> {
            if(vbAreaTrabajo.getChildren().isEmpty()){
                vbAreaTrabajo.getChildren().add(vModificarProducto());
            }else{
                vbAreaTrabajo.getChildren().clear();
                vbAreaTrabajo.getChildren().add(vModificarProducto());
            }
        });
        
        miEliminarProducto.setOnAction((event) -> {
            if(vbAreaTrabajo.getChildren().isEmpty()){
                vbAreaTrabajo.getChildren().add(vEliminarProducto());
            }else{
                vbAreaTrabajo.getChildren().clear();
                vbAreaTrabajo.getChildren().add(vEliminarProducto());
            }
        });
        
        
        //Opciones del menu Gastos
        miAgregarGastos.setOnAction((event) -> {
            if(vbAreaTrabajo.getChildren().isEmpty()){
                vbAreaTrabajo.getChildren().add(vRegistrarGastos());
            }else{
                vbAreaTrabajo.getChildren().clear();
                vbAreaTrabajo.getChildren().add(vRegistrarGastos());
            }
        });
        
        miModificarGastos.setOnAction((event) -> {
            if(vbAreaTrabajo.getChildren().isEmpty()){
                vbAreaTrabajo.getChildren().add(vModificarGastos());
            }else{
                vbAreaTrabajo.getChildren().clear();
                vbAreaTrabajo.getChildren().add(vModificarGastos());
            }
        });
        
        miEliminarGastos.setOnAction((event) -> {
            if(vbAreaTrabajo.getChildren().isEmpty()){
                vbAreaTrabajo.getChildren().add(vEliminarGastos());
            }else{
                vbAreaTrabajo.getChildren().clear();
                vbAreaTrabajo.getChildren().add(vEliminarGastos());
            }
        });        
        

        //Opciones del menu Herramientas
        miNuevoUsuario.setOnAction((event) -> {
              if (vbAreaTrabajo.getChildren().size() <= 0){ 
               vbAreaTrabajo.getChildren().addAll(vistaNuevoUsuario());
              }
              else{
               removerVistas();
               vbAreaTrabajo.getChildren().addAll(vistaNuevoUsuario());
              }
        });
        
        miModificarUsuario.setOnAction((event) -> {
              if (vbAreaTrabajo.getChildren().size() <= 0){ 
               vbAreaTrabajo.getChildren().addAll(vistaModificarUsuario());
              }
              else{
               removerVistas();
               vbAreaTrabajo.getChildren().addAll(vistaModificarUsuario());
              }
        });
        
        miEliminarUsuario.setOnAction((event) -> {
              if (vbAreaTrabajo.getChildren().size() <= 0){ 
               vbAreaTrabajo.getChildren().addAll(vistaEliminarUsuario());
              }
              else{
               removerVistas();
               vbAreaTrabajo.getChildren().addAll(vistaEliminarUsuario());
              }
        });

        miAccesosUsuario.setOnAction((event) -> {
            if (vbAreaTrabajo.getChildren().size() <= 0){ 
               vbAreaTrabajo.getChildren().addAll(vistaPermisosUsuario());
            }
            else{
               removerVistas();
               vbAreaTrabajo.getChildren().addAll(vistaPermisosUsuario());
              }
        });
        
        Menu submenuUsuarios = new Menu("Usuarios");
        submenuUsuarios.getItems().addAll(miNuevoUsuario, miModificarUsuario, miEliminarUsuario, miAccesosUsuario);        

        miSalir.setOnAction(((event) -> {
                primaryStage.close();
          }));
        //Submenus
        Menu smTarjetas = new Menu("Tarjetas");
        smTarjetas.getItems().addAll(miNuevaTarjeta, miModificarTarjeta, miEliminarTarjeta, miAsignarTarjetas);
        
        //MenuPrincipal
        Menu mCobranza = new Menu("Cobranza");
        mCobranza.getItems().addAll(smTarjetas, miEstadoTarjetas);
        Menu mClientes = new Menu("Clientes");
        mClientes.getItems().addAll(miRegistrarClientes, miModificarClientes, miEliminarClientes, miListaNegraClientes);
        Menu mVendedores = new Menu("Vendedores");
        mVendedores.getItems().addAll(miAgregarVendedor, miModificarVendedor, miEliminarVendedor, miEstadoVendedor);
        Menu mCobradores = new Menu("Cobradores");
        mCobradores.getItems().addAll(miAgregarCobrador, miModificarCobrador, miEliminarCobrador, miEstadoCobrador);
        Menu mInventario = new Menu("Inventario");
        mInventario.getItems().addAll(miAgregarProducto, miModificarProducto, miEliminarProducto, miEstadisticasProductos);
        Menu mGastos = new Menu("Gastos");
        mGastos.getItems().addAll(miAgregarGastos, miModificarGastos, miEliminarGastos, miAcumuladoCaja);
        Menu mHerramientas = new Menu("Herramientas");
        mHerramientas.getItems().addAll(submenuUsuarios, miGestionBonos, miSalir);
      
        MenuBar mbMain = new MenuBar(mCobranza, mClientes, mVendedores, mCobradores, mInventario, mGastos, mHerramientas);
        
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        anchoPantalla= screenSize.getWidth();
        altoPantalla= screenSize.getHeight();
        
        VBox vbMain = new VBox();
        vbMain.setPadding(new Insets(5,5,5,5));
        vbAreaTrabajo.setPrefSize(anchoPantalla, altoPantalla);
        
       // vbAreaTrabajo.setPrefSize(1200, 800);

        vbMain.getChildren().addAll(mbMain, vbAreaTrabajo);
        
        StackPane root = new StackPane();
        root.getChildren().add(vbMain);
        String stylesheet = getClass().getResource("Style.css").toExternalForm();
        
        Scene scene = new Scene(root, anchoPantalla, altoPantalla);
        scene.getStylesheets().add(stylesheet);        
        //Image ico = new Image("../copladb/recursos/iconPeso.png");
        Image ico = new Image(getClass().getResourceAsStream("recursos/IconSF.png"));
        primaryStage.getIcons().add(ico); 
        //primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("recursos/iconPeso.png")));
        primaryStage.setTitle("--Sistema Cobranza--");
        primaryStage.setScene(scene);
        //primaryStage.show();
        loginEmpresa();
    }
    public void obtieneAccesos(){
        //Alert aviso = new Alert(Alert.AlertType.WARNING);
        //aviso.setContentText("accesos obtenidos");
        
        System.out.println("Usuario: "+usua.getNombre());
        
        lstWhere.clear();
        lstWhere.add("id_usuario ="+usua.getId_usuario());
        actAcce = acceDAO.consultaAccesos(lstWhere).get(0);
        
        nueva_tarjetas=false;
        modificar_consultar_Tarjeta=false;
        eliminar_tarjeta=false;
        asignar_tarjeta=false;
        estado_cobranza=false;
        agregar_cliente=false;
        modificar_consultar_cliente=false;
        eliminar_cliente=false;    
        lista_negra_cliente=false;
        agregar_vendedor=false;
        modificar_consultar_vendedor=false;  
        eliminar_vendedor=false;
        agregar_cobrador=false;
        modificar_consultar_cobrador=false;  
        eliminar_cobrador=false;
        estado_corte_cobrador=false;
        agregar_producto=false;  
        modificar_consultar_producto=false;  
        eliminar_producto=false;
        estadisticas_producto=false;  
        agregar_gasto=false;  
        modificar_consultar_gasto=false;
        eliminar_gasto=false;
        acumulado_caja=false;
        nuevo_usuario=false;
        modificar_usuario=false;
        eliminar_usuario=false; 
        acceso_usuarios=false;
        gestion_bonos=false;
        
        nueva_tarjetas = actAcce.getNueva_tarjetas();
        modificar_consultar_Tarjeta = actAcce.getModificar_consultar_Tarjeta();
        eliminar_tarjeta = actAcce.getEliminar_tarjeta();
        asignar_tarjeta = actAcce.getAsignar_tarjeta();
        estado_cobranza = actAcce.getEstado_cobranza();
        agregar_cliente = actAcce.getAgregar_cliente();
        modificar_consultar_cliente = actAcce.getModificar_consultar_cliente();
        eliminar_cliente = actAcce.getEliminar_cliente();
        lista_negra_cliente = actAcce.getLista_negra_cliente();
        agregar_vendedor = actAcce.getAgregar_vendedor();
        modificar_consultar_vendedor = actAcce.getModificar_consultar_vendedor();
        eliminar_vendedor = actAcce.getEliminar_vendedor();
        agregar_cobrador = actAcce.getAgregar_cobrador();
        modificar_consultar_cobrador = actAcce.getModificar_consultar_cobrador();
        eliminar_cobrador = actAcce.getEliminar_cobrador();
        estado_corte_cobrador = actAcce.getEstado_corte_cobrador();
        agregar_producto = actAcce.getAgregar_producto();
        modificar_consultar_producto = actAcce.getModificar_consultar_producto();
        eliminar_producto = actAcce.getEliminar_producto();
        estadisticas_producto = actAcce.getEstadisticas_producto();
        agregar_gasto = actAcce.getAgregar_gasto();
        modificar_consultar_gasto = actAcce.getModificar_consultar_gasto();
        eliminar_gasto = actAcce.getEliminar_gasto();
        acumulado_caja = actAcce.getAcumulado_caja();
        nuevo_usuario = actAcce.getNuevo_usuario();
        modificar_usuario = actAcce.getModificar_usuario();
        eliminar_usuario = actAcce.getEliminar_usuario();
        acceso_usuarios = actAcce.getAcceso_usuarios();
        gestion_bonos = actAcce.getGestion_bonos();


        nueva_tarjetas=!nueva_tarjetas;
        miNuevaTarjeta.setDisable(nueva_tarjetas);
	modificar_consultar_Tarjeta=!modificar_consultar_Tarjeta;
        miModificarTarjeta.setDisable(modificar_consultar_Tarjeta);
	eliminar_tarjeta=!eliminar_tarjeta;
        miEliminarTarjeta.setDisable(eliminar_tarjeta);
        asignar_tarjeta=!asignar_tarjeta;
        miAsignarTarjetas.setDisable(asignar_tarjeta);
        estado_cobranza=!estado_cobranza;
        miEstadoTarjetas.setDisable(estado_cobranza);
        agregar_cliente=!agregar_cliente;
        miRegistrarClientes.setDisable(agregar_cliente);
        modificar_consultar_cliente=!modificar_consultar_cliente;
        miModificarClientes.setDisable(modificar_consultar_cliente);
        eliminar_cliente=!eliminar_cliente;
        miEliminarClientes.setDisable(eliminar_cliente);        
        lista_negra_cliente=!lista_negra_cliente;
        miListaNegraClientes.setDisable(lista_negra_cliente);
        agregar_vendedor=!agregar_vendedor;
        miAgregarVendedor.setDisable(agregar_vendedor);
        modificar_consultar_vendedor=!modificar_consultar_vendedor;
        miModificarVendedor.setDisable(modificar_consultar_vendedor);
        eliminar_vendedor=!eliminar_vendedor;
        miEliminarVendedor.setDisable(eliminar_vendedor);
        agregar_cobrador=!agregar_cobrador;
        miAgregarCobrador.setDisable(agregar_cobrador);
        modificar_consultar_cobrador=!modificar_consultar_cobrador;
        miModificarCobrador.setDisable(modificar_consultar_cobrador);        
        eliminar_cobrador=!eliminar_cobrador;        
        miEliminarCobrador.setDisable(eliminar_cobrador);
        estado_corte_cobrador=!estado_corte_cobrador;
        miEstadoCobrador.setDisable(estado_corte_cobrador);
        miEstadoVendedor.setDisable(estado_corte_cobrador);
        agregar_producto=!agregar_producto;
        miAgregarProducto.setDisable(agregar_producto);
        modificar_consultar_producto=!modificar_consultar_producto;
        miModificarProducto.setDisable(modificar_consultar_producto);
        eliminar_producto=!eliminar_producto;
        miEliminarProducto.disableProperty().set(eliminar_producto);
        estadisticas_producto=!estadisticas_producto;
        miEstadisticasProductos.disableProperty().set(estadisticas_producto);
        agregar_gasto=!agregar_gasto;
        miAgregarGastos.disableProperty().setValue(agregar_gasto);
        modificar_consultar_gasto=!modificar_consultar_gasto;
        miModificarGastos.setDisable(modificar_consultar_gasto);
        eliminar_gasto=!eliminar_gasto;
        miEliminarGastos.setDisable(eliminar_gasto);
        acumulado_caja=!acumulado_caja;
        miAcumuladoCaja.setDisable(acumulado_caja);
        nuevo_usuario=!nuevo_usuario;
        miNuevoUsuario.setDisable(nuevo_usuario);
        modificar_usuario=!modificar_usuario;
        miModificarUsuario.setDisable(modificar_usuario);
        eliminar_usuario=!eliminar_usuario;
        miEliminarUsuario.setDisable(eliminar_usuario);
        acceso_usuarios=!acceso_usuarios;
        miAccesosUsuario.setDisable(acceso_usuarios);
        gestion_bonos=!gestion_bonos;
        miGestionBonos.setDisable(gestion_bonos);

        
/*        nomUsuario=usua.getUsuario();
        infoLogin.setPadding(new Insets(5, 5, 5, 5));
        infoLogin.setHgap(10);
        infoLogin.setVgap(1);
        infoLogin.setAlignment(Pos.BOTTOM_RIGHT);
   
        LocalDate today = LocalDate.now();
        String hoy = today.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
        LocalTime time = LocalTime.now(); 
        String hora = time.format(DateTimeFormatter.ofPattern("hh:mm"));
        Label lbEmpresa  = new Label("Empresa: "+Empresa.get(EmpresaPos).toString()+" Usuario: "+nomUsuario);
	Label lbFecha  = new Label("Usted ingreso el "+hoy.toString()+" a las "+hora.toString());
        infoLogin.add(lbEmpresa,0,0);        
        infoLogin.add(lbFecha, 0, 1); */       
    }     
    public void loginEmpresa(){
        Stage loginStage = new Stage();
        loginStage.getIcons().add(new Image(getClass().getResourceAsStream("recursos/IconSF.png")));
        loginStage.setTitle("COPLADB. Login");
        
        VBox vbPpal = new VBox();
        vbPpal.setSpacing(10);
        vbPpal.setAlignment(Pos.CENTER);
        
        Label lbTituloVista = new Label("ACCESO");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);

        Label lbUsuario = new Label("Usuario:");
        Label lbContrasena = new Label("Contraseña:");        
        TextField tfUsuario = new TextField();
        tfUsuario.setText(""); //tfUsuario.setText("SuperUser");
        tfUsuario.setPrefWidth(180);
        tfUsuario.setAlignment(Pos.CENTER);
        PasswordField tfContrasena = new PasswordField();
        tfContrasena.setPrefWidth(180);
        tfContrasena.setAlignment(Pos.CENTER);        
        tfContrasena.setPromptText("********");
        tfContrasena.setText("");//tfContrasena.setText("Admin");
        
        GridPane gpUsuario = new GridPane();
        gpUsuario.setPadding(new Insets(5, 5, 5, 5));
        gpUsuario.setVgap(10);
        gpUsuario.setHgap(10);
        gpUsuario.setAlignment(Pos.CENTER);
        
        gpUsuario.add(lbUsuario,0,1);
        gpUsuario.add(tfUsuario,1,1);
        gpUsuario.add(lbContrasena, 0, 2);
        gpUsuario.add(tfContrasena, 1, 2);
        
        Button btnIngresar = new Button("Ingresar");
        btnIngresar.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
            Alert aviso = new Alert(Alert.AlertType.WARNING);
            if (tfUsuario.getText().isEmpty() || tfContrasena.getText().isEmpty()){
                aviso.setContentText("Campo(s) vacío(s)");
                Optional<ButtonType> action = aviso.showAndWait();
                Boolean vUsuarioCorrecto = false;
            }
            else{
                usuarioDAO usloginDAO = new usuarioDAO();
                usuario uslogin = new usuario();
                uslogin = usloginDAO.consultaUnUsuario(tfUsuario.getText());
                usua = uslogin;
                if (tfContrasena.getText().equals(uslogin.getClaveAcceso())){
                        
                        aviso.setAlertType(Alert.AlertType.INFORMATION);
                        aviso.setContentText("Acceso Permitido");
                        //Optional<ButtonType> action = aviso.showAndWait();
                        loginStage.close();
                        obtieneAccesos();
                        primaryStageLocal.show();
                        //aviso.setContentText("Acceso no permitido");
                        Optional<ButtonType> action = aviso.showAndWait();
                }
                else{
                    aviso.setContentText("Usuario y/o Contraseña Incorrectos");
                    Optional<ButtonType> action = aviso.showAndWait();
                }
            }
            }
        });
        
        Button btnSalir = new Button("   Salir   ");
        btnSalir.setOnAction((ActionEvent e)->{
            loginStage.close();
        });
        
        HBox hbBotones = new HBox();
        hbBotones.setSpacing(10);
        hbBotones.getChildren().addAll(btnIngresar, btnSalir);
        hbBotones.setAlignment(Pos.CENTER);
        
        vbPpal.getChildren().addAll(lbTituloVista, gpUsuario, hbBotones);
        
        StackPane rootSelectClientes = new StackPane();
        rootSelectClientes.getChildren().addAll(vbPpal);
        Scene scene = new Scene(rootSelectClientes,300,200);
        loginStage.setScene(scene);
        loginStage.setIconified(false);
        loginStage.initModality(Modality.WINDOW_MODAL);
        loginStage.show();
        
    }    
    //Modulos de Tarjeta
    private void removerVistas(){
       if (vbAreaTrabajo.getChildren().size()>0){
          vbAreaTrabajo.getChildren().remove(0);
       }
    }
    private VBox vNuevaTarjeta(){
        ContNumPagosProyectados = 0;
        ContNumPagosRealizado = 0;
        Label lbTitulo = new Label("N U E V A   T A R J E T A");
        lbTitulo.getStyleClass().add("titulo-vista");
        lbTitulo.setAlignment(Pos.CENTER);
         
        //Componentes busqueda de productos
        Label lbTipoBusqueda = new Label("Buscar por: ");
        
        ToggleGroup tgBusquedas = new ToggleGroup();
        
        RadioButton rbTodos = new RadioButton("Todos");
        RadioButton rbCodigo = new RadioButton("Codigo");
        RadioButton rbDescripcion = new RadioButton("Descripción");
        RadioButton rbCategoria = new RadioButton("Categoria");
        
        rbTodos.setSelected(true);
        
        rbTodos.setToggleGroup(tgBusquedas);
        rbCodigo.setToggleGroup(tgBusquedas);
        rbDescripcion.setToggleGroup(tgBusquedas);
        rbCategoria.setToggleGroup(tgBusquedas);
        
        TextField tfBuscarCodigo = new TextField();
        TextField tfBuscarDescripcion = new TextField();
        tfBuscarDescripcion.setPrefWidth(250);
        ComboBox cbBuscarCategoria = new ComboBox();
        cbBuscarCategoria.setPrefWidth(180);
        
        Button btnSeleccionar = new Button("Seleccionar");

        //Componentes busqueda de Clientes
        Label lbTipoBusquedaCli = new Label("Buscar Cliente por: ");
        
        ToggleGroup tgBusquedasCli = new ToggleGroup();
        
        RadioButton rbTodosCli = new RadioButton("Todos los Clientes");
        RadioButton rbidCli = new RadioButton("Id Cliente");
        RadioButton rbNombreCli = new RadioButton("Nombre Cliente");
        
        rbTodosCli.setSelected(true);
        
        rbTodosCli.setToggleGroup(tgBusquedasCli);
        rbidCli.setToggleGroup(tgBusquedasCli);
        rbNombreCli.setToggleGroup(tgBusquedasCli);
        
        TextField tfBuscarIdCliente = new TextField();
        tfBuscarIdCliente.setPrefWidth(80);
        TextField tfBuscarNombreCliente = new TextField();
        tfBuscarNombreCliente.setPrefWidth(200);
        
        Button btnSeleccionarCliente = new Button("Seleccionar Cliente");
         
        //Datos Tarjeta
        Label lbDatosTarjeta = new Label("Datos de Tarjeta");
        Label lbFolio = new Label("Folio");
        Label lbFecha = new Label("Fecha");
        Label lbPrecio = new Label("Precio");
        Label lbTipoPrecio = new Label("Tipo Precio");
        Label lbEnganche = new Label("Enganche");
        Label lbPendEnganche = new Label("Pend. Eng.");
        lbPendEnganche.setPrefHeight(55);
        Label lbSaldo = new Label("Saldo");
        Label lbPagos = new Label("Pagos");
        Label lbDiaCobro = new Label("DiasCobro");
        Label lbTipoPago = new Label("Tipo Pagos***");
        Label lbRegion = new Label("Region");
        Label lbVendedor = new Label("Vendedor: ");
        
        TextField tfFolio = new TextField();
        tfFolio.setPrefWidth(80);
        tfFolio.setMaxWidth(80);
        tfFolio.setMinWidth(80);
        DatePicker dpFecha = new DatePicker(LocalDate.now());
        dpFecha.setPrefWidth(120);
        TextField tfPrecio = new TextField();
        tfPrecio.setPrefWidth(80);
        tfPrecio.setMinWidth(80);
        tfPrecio.setMaxWidth(80);
        ObservableList<String> lstTipoPrecio = FXCollections.observableArrayList("Contado","Credi-Contado", "Credito");
        ComboBox cbTipoPrecio = new ComboBox(lstTipoPrecio);
        TextField tfEnganche = new TextField();
        tfEnganche.setPrefWidth(80);
        tfEnganche.setMinWidth(80);
        tfEnganche.setMaxWidth(80);


        TextField tfPendEnganche = new TextField();
        tfPendEnganche.setPrefWidth(80);
        tfPendEnganche.setMinWidth(80);
        tfPendEnganche.setMaxWidth(80);
        TextField tfSaldo = new TextField();
        tfSaldo.setPrefWidth(80);
        tfSaldo.setMinWidth(80);
        tfSaldo.setMaxWidth(80);
        TextField tfPagos = new TextField();
        tfPagos.setPrefWidth(80);
        tfPagos.setMinWidth(80);
        tfPagos.setMaxWidth(80);
        
        
        ObservableList<String> lstDiasCobro = FXCollections.observableArrayList(
                "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo");
        ComboBox cbDiasCobro = new ComboBox(lstDiasCobro);
        ObservableList<String> lstTipoPago = FXCollections.observableArrayList(
                "Semanal", "Quincenal", "Mensual");
        ComboBox cbTipoPago = new ComboBox(lstTipoPago);
//        ObservableList<String> lstRegion = FXCollections.observableArrayList(
//                "Soledad de Doblado", "Manlio Fabio Altamirano", "Mata de Agua", "Rincon de Barradas", "Loma de los Carmona");
        ObservableList<String> lstRegion = FXCollections.observableArrayList(tarDAO.consultarDistinctRegion());
        ComboBox cbRegion = new ComboBox(lstRegion);
        cbRegion.setEditable(true);
        cbRegion.setPrefWidth(150);
        ObservableList<String> lstVendedores = FXCollections.observableArrayList(venDAO.ListarVendedores());
        ComboBox cbVendedor = new ComboBox();
        cbVendedor.setPrefWidth(250);
        cbVendedor.setMinWidth(250);
        cbVendedor.setMaxWidth(250);
        cbVendedor.setItems(lstVendedores);
        
        Button btnRegistrarTarjeta = new Button("Registrar \n Tarjeta");
        btnRegistrarTarjeta.setPrefSize(95, 85);
        
        tfEnganche.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                tfSaldo.setText((String.valueOf(Float.parseFloat(tfPrecio.getText())-Float.parseFloat(tfEnganche.getText()))));
                } 
            }
        });
        
        //Datos Cliente
        Label lbDatosCliente = new Label("Datos Cliente");
        Label lbIdCliente = new Label("id: ");
        Label lbNombreCliente = new Label("Nombre: ");
        Label lbDireccion = new Label("Direccion: ");
        Label lbTelefono = new Label("Telefono: ");
        
        TextField tfIdCliente = new TextField();
        tfIdCliente.setPrefWidth(180);
        TextField tfNombreCliente = new TextField();
        TextField tfDireccion = new TextField();
        TextField tfTelefono = new TextField();
        
        Button btnAgregarCliente =new Button("Agregar Cliente");
        btnAgregarCliente.setOnAction((event) -> {
            int result = cliDAO.insertarCliente(tfNombreCliente.getText(), tfDireccion.getText(), tfTelefono.getText(), "Nuevo");
            if (result != -1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Confirmación");
                alert.setContentText("Cliente Registrado con el Id #"+result);
                tfIdCliente.setText(String.valueOf(result));
                alert.showAndWait();

            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Este Cliente ya esta registrado"+result);
            
            }
        });
        
        TableView tvClientes = new TableView();
        tvClientes.setPrefSize(500, 300);
        
        TableColumn<inventario, String> idClienteColumna = new TableColumn<>("id Cliente");
        idClienteColumna.setMinWidth(60);
        idClienteColumna.setCellValueFactory(new PropertyValueFactory<>("IdCliente")); 
        
        TableColumn<inventario, String> nombreColumna = new TableColumn<>("Nombre");
        nombreColumna.setMinWidth(60);
        nombreColumna.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        
        TableColumn<inventario, String> direccionColumna = new TableColumn<>("Direccion");
        direccionColumna.setMinWidth(60);
        direccionColumna.setCellValueFactory(new PropertyValueFactory<>("Direccion"));
        
        TableColumn<inventario, String> telefonoColumna = new TableColumn<>("Telefono");
        telefonoColumna.setMinWidth(60);
        telefonoColumna.setCellValueFactory(new PropertyValueFactory<>("Telefono"));
        
        TableColumn<inventario, String> tipoClienteColumna = new TableColumn<>("Tipo CLiente");
        tipoClienteColumna.setMinWidth(60);
        tipoClienteColumna.setCellValueFactory(new PropertyValueFactory<>("TipoCLiente"));
        
        tvClientes.getColumns().addAll(idClienteColumna, nombreColumna, direccionColumna, telefonoColumna, tipoClienteColumna);
        lstWhere.clear();
        lstWhere.add("idCliente is not null");
        tvClientes.setItems(FXCollections.observableArrayList(cliDAO.consultarClientes(lstWhere)));
        tvClientes.setOnMouseClicked((event) -> {
            cliDTO = (cliente) tvClientes.getSelectionModel().getSelectedItem();
            tfIdCliente.setText(String.valueOf(cliDTO.getIdCliente()));
            tfNombreCliente.setText(cliDTO.getNombre());
            tfDireccion.setText(cliDTO.getDireccion());
            tfTelefono.setText(cliDTO.getTelefono());
        });
        
        btnSeleccionarCliente.setOnAction((event) -> {
            if (rbTodosCli.isSelected()){
                lstWhere.clear();
                lstWhere.add("idCliente is not null");
                tvClientes.setItems(FXCollections.observableArrayList(cliDAO.consultarClientes(lstWhere)));
            }
            if (rbidCli.isSelected()){
                if(!tvClientes.getItems().isEmpty()) tvClientes.getItems().clear();
                lstWhere.clear();
                lstWhere.add("idCliente ="+tfBuscarIdCliente.getText());
                tvClientes.setItems(FXCollections.observableArrayList(cliDAO.consultarClientes(lstWhere)));
            }
            if (rbNombreCli.isSelected()){
                if(!tvClientes.getItems().isEmpty()) tvClientes.getItems().clear();
                lstWhere.clear();
                lstWhere.add("Nombre like '%"+tfBuscarNombreCliente.getText()+"%' ");
                tvClientes.setItems(FXCollections.observableArrayList(cliDAO.consultarClientes(lstWhere)));
            }
        });
        //Datos Producto
        Label lbDatosProducto = new Label("Datos Productos");
        Label lbCodigoProducto = new Label("Codigo: ");
        Label lbDescripcionProducto = new Label("Descripcion:");
        Label lbCantidad = new Label("Cantidad: ");
        Label lbCostoUnitario = new Label("Costo Unitario: ");
        Label lbCostoTotal = new Label("Gran Total: $ 0.00");
        lbCostoTotal.getStyleClass().add("Gran-Total");
        lbCostoTotal.setAlignment(Pos.TOP_RIGHT);
        lbCostoTotal.setPrefWidth(620);
        
        ToggleGroup tgRadioPrecios = new ToggleGroup();
        RadioButton rbPrecioContado = new RadioButton("Precio Contado");
        rbPrecioContado.setSelected(true);
        rbPrecioContado.setToggleGroup(tgRadioPrecios);
        RadioButton rbPrecioCrediContado = new RadioButton("Precio Credi-Contado");
        rbPrecioCrediContado.setToggleGroup(tgRadioPrecios);
        RadioButton rbPrecioCredito = new RadioButton("Precio Credito");
        rbPrecioCredito.setToggleGroup(tgRadioPrecios);
        
        
        TextField tfCodigoProducto = new TextField();
        tfCodigoProducto.setPrefWidth(180);
        TextField tfDescripcionProducto = new TextField();
        TextField tfCostoUnitarioProducto = new TextField();
        TextField tfCantidadProducto = new TextField();
        TextField tfContado = new TextField();
        TextField tfCrediContado = new TextField();
        TextField tfCredito = new TextField();
        
        TableView tvProductos = new TableView();
        tvProductos.setPrefSize(500, 300);
        
        TableColumn<inventario, String> idProductoColumna = new TableColumn<>("idProducto");
        idProductoColumna.setMinWidth(60);
        idProductoColumna.setCellValueFactory(new PropertyValueFactory<>("Id Producto "));        
        
        TableColumn<inventario, String> codigoColumna = new TableColumn<>("Codigo Producto");
        codigoColumna.setMinWidth(120);
        codigoColumna.setCellValueFactory(new PropertyValueFactory<>("CodigoProducto"));
        
        TableColumn<inventario, Float> existenciaColumna = new TableColumn<>("Existencia");
        existenciaColumna.setMinWidth(80);
        existenciaColumna.setCellValueFactory(new PropertyValueFactory<>("Existencia"));

        TableColumn<inventario, Float> descripcionColumna = new TableColumn<>("Descripcion");
        descripcionColumna.setMinWidth(180);
        descripcionColumna.setCellValueFactory(new PropertyValueFactory<>("Descripcion"));

        TableColumn<inventario, Float> unidadMedidaColumna = new TableColumn<>("U. Med.");
        unidadMedidaColumna.setMinWidth(60);
        unidadMedidaColumna.setCellValueFactory(new PropertyValueFactory<>("UnidadMedida"));

        TableColumn<inventario, Float> precioContadoColumna = new TableColumn<>("Precio Contado");
        precioContadoColumna.setMinWidth(80);
        precioContadoColumna.setCellValueFactory(new PropertyValueFactory<>("PrecioContado"));

        TableColumn<inventario, Float> crediContadoColumna = new TableColumn<>("P. Credi-Contado");
        crediContadoColumna.setMinWidth(80);
        crediContadoColumna.setCellValueFactory(new PropertyValueFactory<>("Precio_crediContado"));

        TableColumn<inventario, Float> creditoColumna = new TableColumn<>("Precio Credito");
        creditoColumna.setMinWidth(80);
        creditoColumna.setCellValueFactory(new PropertyValueFactory<>("Precio_credito"));

        TableColumn<inventario, Float> proveedorColumna = new TableColumn<>("Proveedor");
        proveedorColumna.setMinWidth(80);
        proveedorColumna.setCellValueFactory(new PropertyValueFactory<>("Proveedor"));

        TableColumn<inventario, Float> categoriaColumna = new TableColumn<>("id Categoria");
        categoriaColumna.setMinWidth(80);
        categoriaColumna.setCellValueFactory(new PropertyValueFactory<>("idCategoria"));
        
        tvProductos.getColumns().setAll(codigoColumna, existenciaColumna, descripcionColumna, unidadMedidaColumna, precioContadoColumna,
                crediContadoColumna, creditoColumna, proveedorColumna, categoriaColumna);
        tvProductos.setOnMouseClicked((event) -> {
            invDTO = (inventario)tvProductos.getSelectionModel().getSelectedItem();
            tfCodigoProducto.setText(invDTO.getCodigoProducto());
            tfDescripcionProducto.setText(invDTO.getDescripcion());
            tfContado.setText(String.valueOf(invDTO.getPrecioContado()));
            tfCrediContado.setText(String.valueOf(invDTO.getPrecio_crediContado()));
            tfCredito.setText(String.valueOf(invDTO.getPrecio_credito()));
            if(rbPrecioContado.isSelected()){
                tfCostoUnitarioProducto.setText(tfContado.getText());
            }
        });
        
        rbPrecioContado.setOnMouseClicked((event) -> {
            tfCostoUnitarioProducto.setText(String.valueOf(tfContado.getText()));
        });
        rbPrecioCrediContado.setOnMouseClicked((event) -> {
            tfCostoUnitarioProducto.setText(String.valueOf(tfCrediContado.getText()));
        });
        rbPrecioCredito.setOnMouseClicked((event) -> {
            tfCostoUnitarioProducto.setText(String.valueOf(tfCredito.getText()));
        });
        
        lstWhere.clear();
        lstWhere.add("idProducto is not null");
        //lstInventario = invDAO.consultarInventario(lstWhere);
        tvProductos.setItems(FXCollections.observableArrayList(invDAO.consultarInventario(lstWhere)));
        
        //Datos Pagos Proyectados
        Label lbPagosProyectados = new Label("Pagos Proyectados:");
        Label lbFechaPago = new Label("Fecha Pago:");
        Label lbMonto = new Label("Monto:");
        Label lbEstado = new Label("Estado:");
        
        DatePicker dpFechaPago = new DatePicker();
        TextField tfMonto = new TextField();
        ComboBox cbEstado = new ComboBox(FXCollections.observableArrayList("Activo","Pagado","Parcialidad"));
        
        Button btnAgregarPagoProyectado = new Button("A");
        btnAgregarPagoProyectado.setId("btnIconoAdd");
        Button btnModificarPagoProyectado = new Button("M");
        btnModificarPagoProyectado.setId("btnIconoUpdate");
        Button btnEliminarPagoProyectado = new Button("E");
        btnEliminarPagoProyectado.setId("btnIconoRemove");
        
        TableView tvPagosProyectados = new TableView();
        tvPagosProyectados.setPrefSize(500, 300);
        
        TableColumn<pagosProyectados, String> fechaPagoColumna = new TableColumn<>("Fecha");
        fechaPagoColumna.setMinWidth(60);
        fechaPagoColumna.setCellValueFactory(new PropertyValueFactory<>("fechaPago"));        
        
        TableColumn<pagosProyectados, String> montoPagoColumna = new TableColumn<>("Monto");
        montoPagoColumna.setMinWidth(60);
        montoPagoColumna.setCellValueFactory(new PropertyValueFactory<>("monto"));        
        
        TableColumn<pagosProyectados, String> estadoColumna = new TableColumn<>("Estado");
        estadoColumna.setMinWidth(60);
        estadoColumna.setCellValueFactory(new PropertyValueFactory<>("estado"));        
        
        tvPagosProyectados.getColumns().setAll(fechaPagoColumna, montoPagoColumna, estadoColumna);
        
        btnAgregarPagoProyectado.setOnMouseClicked((event) -> {
            if (dpFechaPago.getValue()!=null && tfMonto.getText()!= null && cbEstado.getValue()!= null){
                pagosProyectados pagProy = new pagosProyectados(); 
                pagProy.setNumPago(ContNumPagosProyectados);
                pagProy.setFechaPago(dpFechaPago.getValue().toString());
                pagProy.setEstado(cbEstado.getValue().toString());
                pagProy.setMonto(Float.parseFloat(tfMonto.getText()));
                lstobPagProyectados.add(pagProy);
                tvPagosProyectados.setItems(lstobPagProyectados);
                dpFechaPago.setValue(null);
                tfMonto.setText(null);
                cbEstado.setValue(null);
            }
        });
        btnEliminarPagoProyectado.setOnMouseClicked((event) -> {
            //if (dpFechaPago.getValue()!=null && tfMonto.getText()!= null && cbEstado.getValue()!= null){
                System.out.println("Poscion a borrar: "+tvPagosProyectados.getSelectionModel().getSelectedIndex());
                lstobPagProyectados.remove(tvPagosProyectados.getSelectionModel().getSelectedIndex());
                tvPagosProyectados.setItems(lstobPagProyectados);
                dpFechaPago.setValue(null);
                tfMonto.setText(null);
                cbEstado.setValue(null);
            //}
        });
        btnModificarPagoProyectado.setOnMouseClicked((event) -> {
            if (dpFechaPago.getValue()!=null && tfMonto.getText()!= null && cbEstado.getValue()!= null){
                lstobPagProyectados.get(tvPagosProyectados.getSelectionModel().getSelectedIndex()).setFechaPago(dpFechaPago.getValue().toString());
                lstobPagProyectados.get(tvPagosProyectados.getSelectionModel().getSelectedIndex()).setMonto(Float.parseFloat(tfMonto.getText()));
                lstobPagProyectados.get(tvPagosProyectados.getSelectionModel().getSelectedIndex()).setEstado(cbEstado.getValue().toString());
                tvPagosProyectados.setItems(lstobPagProyectados);
                dpFechaPago.setValue(null);
                tfMonto.setText(null);
                cbEstado.setValue(null);
            }
        });
        
        tvPagosProyectados.setOnMouseClicked((event) -> {
            pagosProyectados pagProy = (pagosProyectados) tvPagosProyectados.getSelectionModel().getSelectedItem();
            dpFechaPago.setValue(LocalDate.parse(pagProy.getFechaPago()));
            tfMonto.setText(String.valueOf(pagProy.getMonto()));
            cbEstado.setValue(pagProy.getEstado());
            
        });
        
        //Datos Pagos Realizados
        Label lbPagosRealizados = new Label("Pagos Realizados:");
        Label lbFechaPagoR = new Label("Fecha Pago:");
        Label lbMontoR = new Label("Monto:");
        Label lbTipoR = new Label("Tipo:");
        
        DatePicker dpFechaPagoR = new DatePicker();
        TextField tfMontoR = new TextField();
        ComboBox cbTipoR = new ComboBox(FXCollections.observableArrayList("Enganche Pend.","Abono","Parcialidad"));
        
        Button btnAgregarPagoRealizado = new Button("A");
        btnAgregarPagoRealizado.setId("btnIconoAdd");
        Button btnModificarPagoRealizado = new Button("M");
        btnModificarPagoRealizado.setId("btnIconoUpdate");
        Button btnEliminarPagoRealizado = new Button("E");
        btnEliminarPagoRealizado.setId("btnIconoRemove");
        
        TableView tvPagosRealizado = new TableView();
        tvPagosRealizado.setPrefSize(500, 300);
        
        TableColumn<pagosRealizados, String> fechaPagoRealizadoColumna = new TableColumn<>("Fecha");
        fechaPagoRealizadoColumna.setMinWidth(60);
        fechaPagoRealizadoColumna.setCellValueFactory(new PropertyValueFactory<>("Fecha"));        
        
        TableColumn<pagosRealizados, String> montoPagoRealizadoColumna = new TableColumn<>("monto");
        montoPagoRealizadoColumna.setMinWidth(60);
        montoPagoRealizadoColumna.setCellValueFactory(new PropertyValueFactory<>("Monto"));        
        
        TableColumn<pagosRealizados, String> tipoColumna = new TableColumn<>("Tipo");
        tipoColumna.setMinWidth(60);
        tipoColumna.setCellValueFactory(new PropertyValueFactory<>("Tipo"));

        tvPagosRealizado.getColumns().setAll(fechaPagoRealizadoColumna, montoPagoRealizadoColumna, tipoColumna);
        
        btnAgregarPagoRealizado.setOnMouseClicked((event) -> {
            if (dpFechaPagoR.getValue()!=null && tfMontoR.getText()!= null && cbTipoR.getValue()!= null){
                pagosRealizados pagRealizado = new pagosRealizados(); 
                pagRealizado.setNumPago(ContNumPagosRealizado);
                pagRealizado.setFecha(dpFechaPagoR.getValue().toString());
                pagRealizado.setTipo(cbTipoR.getValue().toString());
                pagRealizado.setMonto(Float.parseFloat(tfMontoR.getText()));
                lstobPagRealizados.add(pagRealizado);
                tvPagosRealizado.setItems(lstobPagRealizados);
                dpFechaPagoR.setValue(null);
                tfMontoR.setText(null);
                cbTipoR.setValue(null);
                float saldoTemp = 0.00f;
                for(pagosRealizados p : lstobPagRealizados){
                    if(p.getTipo()=="Abono"){
                        saldoTemp = saldoTemp + p.getMonto();
                    }
                }
                float saldoTmp = Float.parseFloat(tfSaldo.getText())-saldoTemp;
                tfSaldo.setText(String.valueOf(saldoTmp));
            }
        });
        btnEliminarPagoRealizado.setOnMouseClicked((event) -> {
                lstobPagRealizados.remove(tvPagosRealizado.getSelectionModel().getSelectedIndex());
                tvPagosRealizado.setItems(lstobPagRealizados);
                dpFechaPagoR.setValue(null);
                tfMontoR.setText(null);
                cbTipoR.setValue(null);
        });
        btnModificarPagoRealizado.setOnMouseClicked((event) -> {
            if (dpFechaPagoR.getValue()!=null && tfMontoR.getText()!= null && cbTipoR.getValue()!= null){
                int indexSeleccionado =tvPagosRealizado.getSelectionModel().getSelectedIndex();
                tvPagosRealizado.getSelectionModel().clearSelection();
                lstobPagRealizados.get(indexSeleccionado).setFecha(dpFechaPagoR.getValue().toString());
                lstobPagRealizados.get(indexSeleccionado).setMonto(Float.parseFloat(tfMontoR.getText()));
                lstobPagRealizados.get(indexSeleccionado).setTipo(cbTipoR.getValue().toString());
                tvPagosRealizado.setItems(lstobPagRealizados);
                dpFechaPagoR.setValue(null);
                tfMontoR.setText(null);
                cbTipoR.setValue(null);
            }
        });
        
        tvPagosRealizado.setOnMouseClicked((event) -> {
            pagosRealizados pagRealizado = (pagosRealizados) tvPagosRealizado.getSelectionModel().getSelectedItem();
            dpFechaPagoR.setValue(LocalDate.parse(pagRealizado.getFecha()));
            tfMontoR.setText(String.valueOf(pagRealizado.getMonto()));
            cbTipoR.setValue(pagRealizado.getTipo());
            
        });        
        
        
        GridPane gpPagosProyectados = new GridPane();
        gpPagosProyectados.setVgap(10);
        gpPagosProyectados.setHgap(10);
        gpPagosProyectados.setPadding(new Insets(5,5,5,5));
        
        gpPagosProyectados.add(lbPagosProyectados, 0, 0);
        gpPagosProyectados.add(lbFechaPago, 0, 1);
        gpPagosProyectados.add(dpFechaPago, 1, 1);
        gpPagosProyectados.add(lbMonto, 0, 2);
        gpPagosProyectados.add(tfMonto, 1, 2);
        gpPagosProyectados.add(lbEstado, 0, 3);
        gpPagosProyectados.add(cbEstado, 1, 3);
        gpPagosProyectados.add(btnAgregarPagoProyectado, 2, 3);
        gpPagosProyectados.add(btnModificarPagoProyectado, 3, 3);
        gpPagosProyectados.add(btnEliminarPagoProyectado, 4, 3);
        
        GridPane gpPagosRealizados = new GridPane();
        gpPagosRealizados.setVgap(10);
        gpPagosRealizados.setHgap(10);
        gpPagosRealizados.setPadding(new Insets(5,5,5,5));

        gpPagosRealizados.add(lbPagosRealizados,0,0);
        gpPagosRealizados.add(lbFechaPagoR,0,1);
        gpPagosRealizados.add(dpFechaPagoR,1,1);
        gpPagosRealizados.add(lbMontoR,0,2);
        gpPagosRealizados.add(tfMontoR,1,2);
        gpPagosRealizados.add(lbTipoR,0,3);
        gpPagosRealizados.add(cbTipoR,1,3);
        gpPagosRealizados.add(btnAgregarPagoRealizado, 2, 3);
        gpPagosRealizados.add(btnModificarPagoRealizado, 3, 3);
        gpPagosRealizados.add(btnEliminarPagoRealizado, 4, 3);
        
        VBox vbPagosProyectados = new VBox();
        vbPagosProyectados.setPadding(new Insets(5,5,5,5));
        vbPagosProyectados.setSpacing(5);
        vbPagosProyectados.getChildren().addAll(gpPagosProyectados, tvPagosProyectados);

        VBox vbPagosRealizados = new VBox();
        vbPagosRealizados.setPadding(new Insets(5,5,5,5));
        vbPagosRealizados.setSpacing(5);
        vbPagosRealizados.getChildren().addAll(gpPagosRealizados, tvPagosRealizado);

        HBox hbPagos = new HBox(vbPagosProyectados, vbPagosRealizados);
        hbPagos.setPadding(new Insets(5,5,5,5));
        hbPagos.setSpacing(5);
        
        btnSeleccionar.setOnMouseClicked((event) -> {
            if(rbTodos.isSelected()){
                if(!tvProductos.getItems().isEmpty()) tvProductos.getItems().clear();
                lstWhere.clear();
                lstWhere.add("idProducto is not null");
                //lstInventario = invDAO.consultarInventario(lstWhere);
                tvProductos.setItems(FXCollections.observableArrayList(invDAO.consultarInventario(lstWhere)));
            }
            if (rbCodigo.isSelected()){
                if(!tvProductos.getItems().isEmpty()) tvProductos.getItems().clear();
                lstWhere.clear();
                lstWhere.add("CodigoProducto ='"+tfBuscarCodigo.getText()+"' ");
                tvProductos.setItems(FXCollections.observableArrayList(invDAO.consultarInventario(lstWhere)));
            }
            if (rbDescripcion.isSelected()){
                if(!tvProductos.getItems().isEmpty()) tvProductos.getItems().clear();
                lstWhere.clear();
                lstWhere.add("Descripcion like '%"+tfBuscarDescripcion.getText()+"%' ");
                tvProductos.setItems(FXCollections.observableArrayList(invDAO.consultarInventario(lstWhere)));
            }
        });
            
        TableView tvProductosSelccionados = new TableView();
        tvProductosSelccionados.setPrefSize(635, 200);
        tvProductosSelccionados.setMinSize(635, 200);
        tvProductosSelccionados.setMaxSize(635, 200);
        
        MenuItem miEliminarDetVenta = new MenuItem("Eliminar");
        ContextMenu cmTabVentas = new ContextMenu();
        cmTabVentas.getItems().add(miEliminarDetVenta);        
        
        //TableColumn<detalle_venta, String> idProdSelecColumna = new TableColumn<>("id detalle venta");
        //idProdSelecColumna.setMinWidth(60);
        //idProdSelecColumna.setCellValueFactory(new PropertyValueFactory<>("Id Producto "));        
        
        TableColumn<detalle_venta, String> codigoProdSelecColumna = new TableColumn<>("Codigo Producto");
        codigoProdSelecColumna.setMinWidth(120);
        codigoProdSelecColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_prod"));
        
        TableColumn<detalle_venta, Float> cantidadColumna = new TableColumn<>("Cantidad");
        cantidadColumna.setMinWidth(80);
        cantidadColumna.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        TableColumn<detalle_venta, Float> descProdSelecColumna = new TableColumn<>("Descripcion");
        descProdSelecColumna.setMinWidth(280);
        descProdSelecColumna.setCellValueFactory(new PropertyValueFactory<>("descprod"));

        TableColumn<detalle_venta, Float> precProdSelecColumna = new TableColumn<>("Precio Venta");
        precProdSelecColumna.setMinWidth(60);
        precProdSelecColumna.setCellValueFactory(new PropertyValueFactory<>("precio_venta"));

        TableColumn<detalle_venta, Float> subTotalColumna = new TableColumn<>("sub-Total");
        subTotalColumna.setMinWidth(80);
        subTotalColumna.setCellValueFactory(new PropertyValueFactory<>("subTotal"));

        tvProductosSelccionados.getColumns().setAll(codigoProdSelecColumna, descProdSelecColumna, cantidadColumna, 
                precProdSelecColumna, subTotalColumna);
        
        tvProductosSelccionados.setItems(FXCollections.observableArrayList(lstDetVenta));
        
        tvProductosSelccionados.setContextMenu(cmTabVentas);
        miEliminarDetVenta.setOnAction((event) -> {
            //detVentaDTO = new detalle_venta();
            detVentaDTO = (detalle_venta) tvProductosSelccionados.getSelectionModel().getSelectedItem();
            lstWhere.clear();
            lstWhere.add("codigoProducto ='"+detVentaDTO.getCodigo_prod()+"'");
            invDTO = invDAO.consultarInventario(lstWhere).get(0);
            int nvaExistencia = invDTO.getExistencia() + detVentaDTO.getCantidad();
            invDAO.modificarExistenciaProducto(invDTO.getCodigoProducto(), nvaExistencia);
            
            tvProductosSelccionados.getItems().remove(tvProductosSelccionados.getSelectionModel().getSelectedIndex());
            lstDetVenta = tvProductosSelccionados.getSelectionModel().getSelectedItems();
            
           
            // Calculo Gran Total de la venta
            if (GranTotal>0) GranTotal = 0;
            ObservableList<detalle_venta> lstObDetVentTemp = tvProductosSelccionados.getItems();
            for (detalle_venta d :lstObDetVentTemp){
               GranTotal = GranTotal + d.getSubTotal();
            }
            
            tfPrecio.setText(String.valueOf(GranTotal));
            lbCostoTotal.setText("Gran Total: $ "+String.valueOf(GranTotal));            
        });        
        
        btnRegistrarTarjeta.setOnAction((event) -> {
            int idVend =0;
            if (cbVendedor.getValue()!= null){
                lstWhere.clear();
                lstWhere.add("Nombre = '"+cbVendedor.getValue().toString()+"'");
                lstVendedor = venDAO.consultarVendedor(lstWhere);
                idVend =lstVendedor.get(0).getIdVendedor();
            }
            
            int idRegTarjeta= tarDAO.insertarTarjeta(tfFolio.getText(), Integer.parseInt(tfIdCliente.getText()) , Float.parseFloat(tfPrecio.getText()), Float.parseFloat(tfEnganche.getText()), 
                    idVend, "S0", cbTipoPago.getValue().toString(), cbRegion.getValue().toString(), cbDiasCobro.getValue().toString(), 
                    Float.parseFloat(tfPendEnganche.getText()), Float.parseFloat(tfSaldo.getText()), dpFecha.getValue().toString(), Float.parseFloat(tfPagos.getText()), cbTipoPrecio.getValue().toString());
            
            if (idRegTarjeta != -1) {
                ObservableList<detalle_venta> lstObDetVentTemp = tvProductosSelccionados.getItems();
                for (detalle_venta d : lstObDetVentTemp) {
                    detVentaDAO.insertarDetalleVenta(d.getDescprod(), d.getCantidad(), d.getPrecio_venta(), idRegTarjeta, d.getCodigo_prod());
                }
                if(!lstobPagProyectados.isEmpty()){
                    for (pagosProyectados pagProy:lstobPagProyectados){
                        pagProyDAO.insertarPagoProyectado(pagProy.getFechaPago(), pagProy.getMonto(), idRegTarjeta, pagProy.getEstado());
                    }
                }
                
                if(!lstobPagRealizados.isEmpty()){
                    for (pagosRealizados pagR: lstobPagRealizados){
                        pagReaDAO.insertarPagoRealizado(pagR.getFecha(), pagR.getMonto(), pagR.getTipo(), idRegTarjeta);
                    }
                }
                
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setTitle("Confirmación");
                alert.setContentText("Tarjeta Registrado con el Id #" + idRegTarjeta + " \n¿Deseas resgistrar otra?");
                Optional<ButtonType> action = alert.showAndWait();
                if (action.get() == ButtonType.OK) {
                    vbAreaTrabajo.getChildren().clear();
                    vbAreaTrabajo.getChildren().add(vNuevaTarjeta());
                } else {
                    removerVistas();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error-Faltan Datos");
                alert.setContentText("Error al Registrar Tarjeta \n¿Deseas resvisar?");
                Optional<ButtonType> action = alert.showAndWait();
                if (action.get() == ButtonType.OK) {
                    //vbAreaTrabajo.getChildren().clear();
                    //vbAreaTrabajo.getChildren().add(vNuevaTarjeta());
                } else {
                    removerVistas();
                }  
            }
            
        });
        
        Button btnAgregar = new Button("Agregar Producto");
        btnAgregar.setOnAction((event) -> {
            detVentaDTO = new detalle_venta();
            detVentaDTO.setCodigo_prod(tfCodigoProducto.getText());
            detVentaDTO.setDescprod(tfDescripcionProducto.getText());
            int cant = Integer.parseInt(tfCantidadProducto.getText());
            detVentaDTO.setCantidad(Integer.parseInt(tfCantidadProducto.getText()));
            float precioVenta = Float.parseFloat(tfCostoUnitarioProducto.getText());
            detVentaDTO.setPrecio_venta(Float.parseFloat(tfCostoUnitarioProducto.getText()));
            detVentaDTO.setSubTotal(cant*precioVenta);
            tvProductosSelccionados.getItems().add(detVentaDTO);
            
            // Resta Existencia a Inventario
            
            //int existencia = invDTO.getExistencia();
            //existencia = existencia - cant;
            //invDAO.modificarExistenciaProducto(invDTO.getIdProducto(), existencia);
            
            int existencia = invDTO.getExistencia();
            existencia = existencia - cant;
            invDAO.modificarExistenciaProducto(tfCodigoProducto.getText(), existencia);
            
            // Calculo Gran Total de la venta
            if (GranTotal>0) GranTotal = 0;
            ObservableList<detalle_venta> lstObDetVentTemp = tvProductosSelccionados.getItems();
            for (detalle_venta d :lstObDetVentTemp){
               GranTotal = GranTotal + d.getSubTotal();
            }
            
            tfPrecio.setText(String.valueOf(GranTotal));
            lbCostoTotal.setText("Gran Total: $ "+String.valueOf(GranTotal));
            
            
        });
        
        VBox vbNuevaTarjeta = new VBox();
        vbNuevaTarjeta.setAlignment(Pos.TOP_CENTER);
        vbNuevaTarjeta.getChildren().add(lbTitulo);
        vbNuevaTarjeta.getStyleClass().add("vbox-vistas");
        vbNuevaTarjeta.setPrefHeight(altoPantalla-100);
        
        
        VBox vbLeft = new VBox();
        GridPane gpDatosTarjeta = new GridPane();
        gpDatosTarjeta.setPadding(new Insets(5, 5, 5, 5));
        gpDatosTarjeta.setHgap(25);
        gpDatosTarjeta.setVgap(15);
        gpDatosTarjeta.add(lbDatosTarjeta, 1, 0);
        gpDatosTarjeta.add(lbFolio, 0, 1);
        gpDatosTarjeta.add(tfFolio, 1, 1);
        gpDatosTarjeta.add(lbFecha, 0, 2);
        gpDatosTarjeta.add(dpFecha, 1, 2);
        gpDatosTarjeta.add(lbPrecio, 0, 3);
        gpDatosTarjeta.add(tfPrecio, 1, 3);
        gpDatosTarjeta.add(lbTipoPrecio, 0, 4);
        gpDatosTarjeta.add(cbTipoPrecio, 1, 4);
        gpDatosTarjeta.add(lbEnganche, 0, 5);
        gpDatosTarjeta.add(tfEnganche, 1, 5);
        gpDatosTarjeta.add(lbPendEnganche, 0, 6);
        gpDatosTarjeta.add(tfPendEnganche, 1, 6);
        gpDatosTarjeta.add(lbSaldo, 0, 7);
        gpDatosTarjeta.add(tfSaldo, 1, 7);
        gpDatosTarjeta.add(lbPagos, 0, 8);
        gpDatosTarjeta.add(tfPagos, 1, 8);
        gpDatosTarjeta.add(lbDiaCobro, 0, 9);
        gpDatosTarjeta.add(cbDiasCobro, 1, 9);
        gpDatosTarjeta.add(lbTipoPago, 0, 10);
        gpDatosTarjeta.add(cbTipoPago, 1, 10);
        gpDatosTarjeta.add(lbRegion, 0, 11);
        gpDatosTarjeta.add(cbRegion, 1, 11);
        gpDatosTarjeta.add(lbVendedor, 0, 12);
        gpDatosTarjeta.add(cbVendedor, 1, 12);
        gpDatosTarjeta.add(btnRegistrarTarjeta, 1, 13);
        
        vbLeft.getChildren().addAll(gpDatosTarjeta);
        
        GridPane gpDatosCliente = new GridPane();
        gpDatosCliente.setPadding(new Insets(5,5,5,5));
        gpDatosCliente.setVgap(5);
        gpDatosCliente.setHgap(5);
        gpDatosCliente.add(lbDatosCliente, 0, 0);
        gpDatosCliente.add(lbIdCliente, 0, 1);
        gpDatosCliente.add(tfIdCliente, 0, 2);
        gpDatosCliente.add(lbNombreCliente, 1, 1);
        gpDatosCliente.add(tfNombreCliente, 1, 2);
        gpDatosCliente.add(lbDireccion, 2, 1);
        gpDatosCliente.add(tfDireccion, 2, 2);
        gpDatosCliente.add(lbTelefono, 3, 1);
        gpDatosCliente.add(tfTelefono, 3, 2);
        gpDatosCliente.add(btnAgregarCliente, 4, 5);
        
        GridPane gpTipoBusquedaClientes = new GridPane();
        gpTipoBusquedaClientes.setPadding(new Insets(5,5,5,5));
        gpTipoBusquedaClientes.setVgap(15);
        gpTipoBusquedaClientes.setHgap(15);
        gpTipoBusquedaClientes.add(lbTipoBusquedaCli, 0, 0);
        gpTipoBusquedaClientes.add(rbTodosCli, 0, 1);
        gpTipoBusquedaClientes.add(rbidCli, 1, 1);
        gpTipoBusquedaClientes.add(rbNombreCli, 2, 1);
        gpTipoBusquedaClientes.add(tfBuscarIdCliente, 1, 2);
        gpTipoBusquedaClientes.add(tfBuscarNombreCliente, 2, 2);
        gpTipoBusquedaClientes.add(btnSeleccionarCliente, 6, 2);
        
        VBox vbDatosCliente = new VBox();
        vbDatosCliente.setSpacing(10);
        vbDatosCliente.getChildren().addAll(gpTipoBusquedaClientes, tvClientes, gpDatosCliente);
        
        GridPane gpDatosProducto = new GridPane();
        gpDatosProducto.setPadding(new Insets(5,5,5,5));
        gpDatosProducto.setVgap(15);
        gpDatosProducto.setHgap(15);
        gpDatosProducto.add(lbDatosProducto, 0, 0);
        gpDatosProducto.add(lbCodigoProducto, 0, 1);
        gpDatosProducto.add(tfCodigoProducto, 1, 1);
        gpDatosProducto.add(lbDescripcionProducto, 0, 2);
        gpDatosProducto.add(tfDescripcionProducto, 1, 2);
        gpDatosProducto.add(lbCantidad, 0, 3);
        gpDatosProducto.add(tfCantidadProducto, 1, 3);
        gpDatosProducto.add(rbPrecioContado, 0, 4);
        gpDatosProducto.add(tfContado, 0, 5);
        gpDatosProducto.add(rbPrecioCrediContado, 1, 4);
        gpDatosProducto.add(tfCrediContado, 1, 5);
        gpDatosProducto.add(rbPrecioCredito, 2, 4);
        gpDatosProducto.add(tfCredito, 2, 5);
        
        
        gpDatosProducto.add(lbCostoUnitario, 0, 6);
        gpDatosProducto.add(tfCostoUnitarioProducto, 1, 6);
        gpDatosProducto.add(btnAgregar, 2, 6);
        //gpDatosProducto.add(lbCostoTotal, 0, 7);
        //gpDatosProducto.add(, 1, 5);
        
        GridPane gpTipoSeleccion = new GridPane();
        gpTipoSeleccion.setPadding(new Insets(5, 5, 5, 5));
        gpTipoSeleccion.setVgap(5);
        gpTipoSeleccion.setHgap(5);
        gpTipoSeleccion.add(lbTipoBusqueda, 0, 0);
        gpTipoSeleccion.add(rbTodos, 0, 1);
        gpTipoSeleccion.add(rbCodigo, 1, 1);
        gpTipoSeleccion.add(tfBuscarCodigo, 1, 2);
        gpTipoSeleccion.add(rbDescripcion, 2, 1);
        gpTipoSeleccion.add(tfBuscarDescripcion, 2, 2);
        gpTipoSeleccion.add(rbCategoria, 3, 1);
        gpTipoSeleccion.add(cbBuscarCategoria, 3, 2);
        gpTipoSeleccion.add(btnSeleccionar, 4, 2);
        
        HBox hbProductos = new HBox(tvProductos, gpDatosProducto);
        
        VBox vbProductosSelecionados = new VBox();
        vbProductosSelecionados.setSpacing(25);
        vbProductosSelecionados.setPrefWidth(555);
        //vbProductosSelecionados.setAlignment(Pos.TOP_RIGHT);
        vbProductosSelecionados.getChildren().addAll(tvProductosSelccionados, lbCostoTotal);
        
        VBox vbProductos = new VBox();
        vbProductos.setSpacing(25);
        vbProductos.getChildren().addAll(gpTipoSeleccion, hbProductos, vbProductosSelecionados);
        
        TabPane tpInfoTarjeta = new TabPane();
        
        Tab tCliente = new Tab();
        tCliente.setText("Cliente");
        tCliente.setContent(vbDatosCliente);
        
        Tab tProducto = new Tab();
        tProducto.setText("Productos");
        tProducto.setContent(vbProductos);

        Tab tPagos = new Tab();
        tPagos.setText("Pagos");
        tPagos.setContent(hbPagos);
        
        tpInfoTarjeta.getTabs().addAll(tCliente, tProducto, tPagos);
        tpInfoTarjeta.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        HBox hbMain = new HBox();
        hbMain.setSpacing(55);
        hbMain.getChildren().addAll(vbLeft, tpInfoTarjeta);
        hbMain.setAlignment(Pos.CENTER);
        vbNuevaTarjeta.getChildren().add(hbMain);
        return vbNuevaTarjeta;
    }
    private VBox vModificarConsultarTarjeta(){
        
        if (!lstobPagProyectados.isEmpty()){lstobPagProyectados.clear();}
        if (!lstobPagRealizados.isEmpty()){lstobPagRealizados.clear();}
        
         Label lbTitulo = new Label("M O D I F I C A R / C O N S U L T A R  T A R J E T A");
         lbTitulo.getStyleClass().add("titulo-vista");
         lbTitulo.setAlignment(Pos.CENTER);
         
        //Componentes busqueda de productos
        Label lbTipoBusquedaProductos = new Label("Buscar por: ");
        
        ToggleGroup tgBusquedas = new ToggleGroup();
        
        RadioButton rbTodos = new RadioButton("Todos");
        RadioButton rbCodigo = new RadioButton("Codigo");
        RadioButton rbDescripcion = new RadioButton("Descripción");
        RadioButton rbCategoria = new RadioButton("Categoria");
        
        rbTodos.setSelected(true);
        
        rbTodos.setToggleGroup(tgBusquedas);
        rbCodigo.setToggleGroup(tgBusquedas);
        rbDescripcion.setToggleGroup(tgBusquedas);
        rbCategoria.setToggleGroup(tgBusquedas);
        
        TextField tfBuscarCodigo = new TextField();
        TextField tfBuscarDescripcion = new TextField();
        tfBuscarDescripcion.setPrefWidth(250);
        ComboBox cbBuscarCategoria = new ComboBox();
        cbBuscarCategoria.setPrefWidth(180);
        
        Button btnSeleccionar = new Button("Seleccionar");
         
        //Seleccionar Busqueda
        
        Label lbTipoBusqueda = new Label("Buscar por: ");
        
        ToggleGroup tgSelecBusqueda = new ToggleGroup();
        
        RadioButton rbBusquedaTodo = new RadioButton("Todos");
        rbBusquedaTodo.setToggleGroup(tgSelecBusqueda);

        RadioButton rbBusquedaFolio = new RadioButton("Folio");
        rbBusquedaFolio.setToggleGroup(tgSelecBusqueda);
        
        RadioButton rbBusquedaCliente = new RadioButton("Cliente");
        rbBusquedaCliente.setToggleGroup(tgSelecBusqueda);
        
        RadioButton rbBusquedaFecha = new RadioButton("Fecha");
        rbBusquedaFecha.setToggleGroup(tgSelecBusqueda);
        
        RadioButton rbBusquedaAtraso = new RadioButton("Atraso Pagos");
        rbBusquedaAtraso.setToggleGroup(tgSelecBusqueda);
        
        RadioButton rbBusquedaCobradores = new RadioButton("Cobradores");
        rbBusquedaCobradores.setToggleGroup(tgSelecBusqueda);
        
        RadioButton rbBusquedaVendedor = new RadioButton("Vendedores");
        rbBusquedaVendedor.setToggleGroup(tgSelecBusqueda);
        
        RadioButton rbBusquedaRegion = new RadioButton("Region");
        rbBusquedaRegion.setToggleGroup(tgSelecBusqueda);
        
        rbBusquedaTodo.selectedProperty().setValue(Boolean.TRUE);
        
        TextField tfBusqFolio = new TextField();
        TextField tfBusqCliente = new TextField();
        DatePicker dpBusqFecha = new DatePicker(LocalDate.now());
        TextField tfBusqAtraso = new TextField();
        
        lstWhere.clear();
        lstWhere.add("idCobrador is not null");
        ObservableList<String> lstCobradores = FXCollections.observableArrayList(cobraDAO.consultarNombreCobradores(lstWhere));        
        ComboBox cbCobrador = new ComboBox(lstCobradores);
        
        ObservableList<String> lstBscVendedores = FXCollections.observableArrayList(venDAO.ListarVendedores());        
        ComboBox cbBuscarVendedor = new ComboBox(lstBscVendedores);
        
        ObservableList<String> lstRegion = FXCollections.observableArrayList(tarDAO.consultarDistinctRegion());
        ComboBox cbBusqRegion = new ComboBox(lstRegion);
        cbBusqRegion.setPrefWidth(250);
        
        Button btnBuscaTarjetas = new Button("Seleccionar Tarjetas");
        
        // Seleccion por Fecha Cobros
        Label lbFiltrarFechaCobro = new Label("Fechas Cobro: ");
        DatePicker dpFiltrarFechaCobro = new DatePicker();
        Button btnFiltrarFecha = new Button("Filtrar Fecha");
        
        GridPane gpFiltroFechaCobro = new GridPane();
        gpFiltroFechaCobro.setPadding(new Insets(5,5,5,5));
        gpFiltroFechaCobro.setVgap(5);
        gpFiltroFechaCobro.setHgap(5);
        gpFiltroFechaCobro.add(lbFiltrarFechaCobro, 0, 0);
        gpFiltroFechaCobro.add(dpFiltrarFechaCobro, 1, 0);
        gpFiltroFechaCobro.add(btnFiltrarFecha, 2, 0);
        
        //Tabla Tarjetas
       
        Label lbTablaTarjetas = new Label("Tarjetas Seleccionadas:");
        TableView tvTarjetas = new TableView();
         
        TableColumn folioTarjetaColum = new TableColumn("Folio ");
        folioTarjetaColum.setCellValueFactory(new PropertyValueFactory<>("folio"));
        folioTarjetaColum.setPrefWidth(40);
        
        TableColumn fechaTarjetaColumna = new TableColumn("Fecha");
        fechaTarjetaColumna.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        fechaTarjetaColumna.setPrefWidth(65);
        
        TableColumn precioColum = new TableColumn("Precio");
        precioColum.setCellValueFactory(new PropertyValueFactory<>("precio"));
        precioColum.setPrefWidth(50);

        TableColumn saldoColum = new TableColumn("Saldo");
        saldoColum.setCellValueFactory(new PropertyValueFactory<>("saldo"));
        saldoColum.setPrefWidth(50);
        
        TableColumn engancheColum = new TableColumn("Enganche");
        engancheColum.setCellValueFactory(new PropertyValueFactory<>("Enganche"));
        engancheColum.setPrefWidth(70);

        TableColumn ClienteColumna = new TableColumn("Cliente");
        ClienteColumna.setCellValueFactory(new PropertyValueFactory<>("nomCliente"));
        ClienteColumna.setPrefWidth(150);

        TableColumn regionColumna = new TableColumn("Region");
        regionColumna.setCellValueFactory(new PropertyValueFactory<>("Region"));
        regionColumna.setPrefWidth(135);
        
        TableColumn cobradorColumna = new TableColumn("Cobrador");
        cobradorColumna.setCellValueFactory(new PropertyValueFactory<>("Cobrador"));
        cobradorColumna.setPrefWidth(135);
        
        TableColumn vendedorColumna = new TableColumn("Vendedor");
        vendedorColumna.setCellValueFactory(new PropertyValueFactory<>("NomVendedor"));
        vendedorColumna.setPrefWidth(135);

        TableColumn proximoPagoColumna = new TableColumn("Fech Prox. Pago");
        proximoPagoColumna.setCellValueFactory(new PropertyValueFactory<>("FecProxPag"));
        proximoPagoColumna.setPrefWidth(135);

        TableColumn pagosColum = new TableColumn("Pagos");
        pagosColum.setCellValueFactory(new PropertyValueFactory<>("Pagos"));
        pagosColum.setPrefWidth(50);

        TableColumn fechaUltimoPagoColum = new TableColumn("fecha Ult. Pago");
        fechaUltimoPagoColum.setCellValueFactory(new PropertyValueFactory<>("FechaUltimoPago"));
        fechaUltimoPagoColum.setPrefWidth(50);

        TableColumn ultimoPagoColum = new TableColumn("Ult. Pago");
        ultimoPagoColum.setCellValueFactory(new PropertyValueFactory<>("UltimoPago"));
        ultimoPagoColum.setPrefWidth(50);
        
        tvTarjetas.getColumns().addAll(folioTarjetaColum, fechaTarjetaColumna, precioColum, saldoColum, engancheColum, ClienteColumna, 
                                       regionColumna, pagosColum);
        
        MenuItem item = new MenuItem("Copiar Todo");
        item.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ObservableList<tarjeta> posList = tvTarjetas.getItems();
                int old_r = -1;
                StringBuilder clipboardString = new StringBuilder();
                clipboardString.append("Id \t Folio \tidCliente \tPrecio \tEnganche \tId Vendedor \t Clasificacion \tTipo Pago \tRegion \tDia Cobro \tEnganche Pend. \tSaldo \tFecha \tPagos \n");
                for (tarjeta p : posList) {

                       clipboardString.append(
                                p.getIdTarjeta()+" \t"
                               +p.getFolio()+" \t"
                               +p.getIdCliente()+" \t"
                               +p.getPrecio()+" \t"
                               +p.getEnganche()+" \t"
                               +p.getIdVendedor()+" \t"
                               +p.getClasificacion()+" \t"
                               +p.getTipoPago()+" \t"
                               +p.getRegion()+" \t"
                               +p.getDiaCobro()+" \t"
                               +p.getEnganchePend()+" \t"
                               +p.getSaldo()+" \t"
                               +p.getFecha()+" \t"
                               +p.getPagos()+" \n");
                }  
            final ClipboardContent content = new ClipboardContent();
            content.putString(clipboardString.toString());
            Clipboard.getSystemClipboard().setContent(content);
            }
            } );
            ContextMenu menu = new ContextMenu();
            menu.getItems().addAll(item);
            tvTarjetas.setContextMenu(menu);
            
        lstWhere.clear();
        lstWhere.add("idTarjeta is not null");
        tvTarjetas.setItems(FXCollections.observableArrayList(tarDAO.consultarTarjetasCliente(lstWhere)));
 
        //Datos Tarjeta
        Label lbDatosTarjeta = new Label("Datos de Tarjeta");
        Label lbFolio = new Label("Folio");
        Label lbFecha = new Label("Fecha");
        Label lbPrecio = new Label("Precio");
        Label lbTipoPrecio = new Label("Tipo Precio");
        Label lbEnganche = new Label("Enganche");
        Label lbPendEnganche = new Label("Pend. \n Enganche");
        lbPendEnganche.setPrefHeight(55);
        Label lbSaldo = new Label("Saldo");
        Label lbPagos = new Label("Pagos");
        Label lbDiaCobro = new Label("DiasCobro");
        Label lbTipoPago = new Label("Tipo Pagos***");
        Label lbRegion = new Label("Region");
        Label lbVendedor = new Label("Vendedor: ");
        Label lbClasificacion = new Label("Clasificacion");
        
       
        
        TextField tfFolio = new TextField();
        tfFolio.setPrefWidth(80);
        DatePicker dpFecha = new DatePicker(LocalDate.now());
        dpFecha.setPrefWidth(120);
        TextField tfPrecio = new TextField();
        ObservableList<String> lstTipoPrecio = FXCollections.observableArrayList("Contado","Cre-Contado", "Credito");
        ComboBox cbTipoPrecio = new ComboBox(lstTipoPrecio);
        TextField tfEnganche = new TextField();
        tfEnganche.setPrefWidth(80);
        tfEnganche.setMinWidth(80);
        tfEnganche.setMaxWidth(80);
        TextField tfPendEnganche = new TextField();
        tfPendEnganche.setPrefWidth(80);
        tfPendEnganche.setMinWidth(80);
        tfPendEnganche.setMaxWidth(80);
        TextField tfSaldo = new TextField();
        tfSaldo.setPrefWidth(80);
        tfSaldo.setMinWidth(80);
        tfSaldo.setMaxWidth(80);
        TextField tfPagos = new TextField();
        tfPagos.setPrefWidth(80);
        tfPagos.setMinWidth(80);
        tfPagos.setMaxWidth(80);
        ObservableList<String> lstDiasCobro = FXCollections.observableArrayList(
                "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo");
        ComboBox cbDiasCobro = new ComboBox(lstDiasCobro);
        ObservableList<String> lstTipoPago = FXCollections.observableArrayList(
                "Semanal", "Quincenal", "Mensual");
        ComboBox cbTipoPago = new ComboBox(lstTipoPago);

        ComboBox cbRegion = new ComboBox(lstRegion);
        ObservableList<String> lstVendedores = FXCollections.observableArrayList(venDAO.ListarVendedores());
        ComboBox cbVendedor = new ComboBox();
        cbVendedor.setPrefWidth(250);
        cbVendedor.setMinWidth(250);
        cbVendedor.setMaxWidth(250);
        cbVendedor.setItems(lstVendedores);
        
        TextField tfClasificacion = new TextField();

        //Calculo Tarjetas.
        Label lbCantCobrada = new Label("Cant. Cobrada: ");
        Label lbCantCobradaValor = new Label("$ 00.00");
        
        // Calculo de Comision
        Label lbComision = new Label ("Comision: ");
        TextField tfComision = new TextField();
        Label lbPorcentajeComision = new Label ("% Comision: ");
        TextField tfPorcentajeComision = new TextField();
    
        Button btnCalulaComision = new Button("Calcular Comision");
        btnCalulaComision.setOnAction((event) -> {
            tfComision.setText(String.valueOf(PorComision));
            comision =  totalCobrado * Float.valueOf(tfPorcentajeComision.getText());
            tfComision.setText(String.valueOf(comision));
        });
        
        GridPane gpCalculoComision = new GridPane();
        gpCalculoComision.setPadding(new Insets(5,5,5,5));
        gpCalculoComision.setVgap(5);
        gpCalculoComision.setHgap(5);
        gpCalculoComision.add(lbPorcentajeComision, 0, 0);
        gpCalculoComision.add(tfPorcentajeComision, 1, 0);
        gpCalculoComision.add(btnCalulaComision, 2, 0);        
        gpCalculoComision.add(lbComision, 0, 1);        
        gpCalculoComision.add(tfComision, 1, 1);        
        
        
        
        //Label lbSaldoPendiente = new Label("Saldo Pendiente: ");
        //Label lbSaldoPendienteValor = new Label("$ 00.00");
        
        //Label lbCantXCob = new Label("Cantidad X Cobrar: ");
        //Label lbCantXCobValor = new Label("$ 00.00");

        //Label lbCantRecup = new Label("Cantidad Recuperada: ");
        //Label lbCantXRecupValor = new Label("$ 00.00");
        
        btnFiltrarFecha.setOnAction((event) -> {
            ObservableList<tarjeta> lstItems = FXCollections.observableArrayList(tvTarjetas.getItems());            
            Iterator<tarjeta> it = lstItems.iterator();
            while(it.hasNext()){
              tarjeta t = it.next();
              if (t.getFechaUltimoPago()!=null){  
                  System.out.println("Fecha de tarjeta: "+t.getFechaUltimoPago());
                  System.out.println("Fecha del DatePicker: "+dpFiltrarFechaCobro.getValue().toString());
                  if(t.getFechaUltimoPago().compareTo(dpFiltrarFechaCobro.getValue().toString())!=0){
                      it.remove();
                  }
              } else it.remove();
            }
            tvTarjetas.getItems().clear();
            tvTarjetas.setItems(lstItems);

            tfPorcentajeComision.setText(String.valueOf(PorComision));
            for(tarjeta ta:lstItems){
                totalCobrado = totalCobrado + ta.getUltimoPago();
            }
            comision =  totalCobrado * PorComision;
            tfComision.setText(String.valueOf(comision));
            lbCantCobradaValor.setText("$ "+String.valueOf(totalCobrado));
        });
        
        
        GridPane gpCalculo = new GridPane();
        gpCalculo.setPadding(new Insets(5,5,5,5));
        gpCalculo.setVgap(5);
        gpCalculo.setHgap(5);
        gpCalculo.add(lbCantCobrada, 0, 0);
        gpCalculo.add(lbCantCobradaValor, 1, 0);
        /*gpCalculo.add(lbSaldoPendiente, 0, 1);
        gpCalculo.add(lbSaldoPendienteValor, 1, 1);
        gpCalculo.add(lbCantXCob, 0, 2);
        gpCalculo.add(lbCantXCobValor, 1, 2);
        gpCalculo.add(lbCantRecup, 0, 3);
        gpCalculo.add(lbCantXRecupValor, 1, 3);*/
        
        
        Button btnActualizarTarjeta = new Button("Actualizar Tarjeta");
        
        btnActualizarTarjeta.setOnAction((event) -> {
            tarDTO = (tarjeta) tvTarjetas.getSelectionModel().getSelectedItem();
            int idVend =0;
            if (cbVendedor.getValue()!= null){
                lstWhere.clear();
                lstWhere.add("Nombre = '"+cbVendedor.getValue().toString()+"'");
                lstVendedor = venDAO.consultarVendedor(lstWhere);
                idVend =lstVendedor.get(0).getIdVendedor();
                System.out.println(idVend);
            }
            
            tarDAO.modificarTarjeta(tarDTO.getIdTarjeta(), tfFolio.getText(), tarDTO.getIdCliente(), Float.parseFloat(tfPrecio.getText()), 
                    Float.parseFloat(tfEnganche.getText()), idVend, tfClasificacion.getText(), cbTipoPago.getValue().toString(),
                    cbRegion.getValue().toString(), cbDiasCobro.getValue().toString(), Float.parseFloat(tfPendEnganche.getText()), Float.parseFloat(tfSaldo.getText()),
                    dpFecha.getValue().toString(), Float.parseFloat(tfPagos.getText()), cbTipoPrecio.getValue().toString());

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Informacion");
                    alert.setContentText("Tarjeta Actualizada");
                    alert.showAndWait();
                    removerVistas();
                    vbAreaTrabajo.getChildren().add(vModificarConsultarTarjeta());
                    
            
        });
        
        btnBuscaTarjetas.setOnMouseClicked((event) -> {
            tfFolio.setText("");
            dpFecha.setValue(null);
            tfPrecio.setText("");
            cbTipoPrecio.setValue(null);
            tfEnganche.setText("");
            tfPendEnganche.setText("");
            tfSaldo.setText("");
            tfPagos.setText("");
            cbDiasCobro.setValue(null);
            cbTipoPago.setValue("");
            cbRegion.setValue(null);
            cbVendedor.setValue("");
            tfClasificacion.setText("");
            
            if (rbBusquedaTodo.isSelected()){
                if(!tvTarjetas.getItems().isEmpty()) tvTarjetas.getItems().clear();
                lstWhere.clear();
                lstWhere.add("idTarjeta is not null");
                tvTarjetas.setItems(FXCollections.observableArrayList(tarDAO.consultarTarjetasCliente(lstWhere)));
                    tvTarjetas.getColumns().clear();
                    tvTarjetas.getColumns().addAll(folioTarjetaColum, fechaTarjetaColumna, precioColum,saldoColum, engancheColum, ClienteColumna, regionColumna, 
                                                   pagosColum);
                
            }
            if (rbBusquedaFolio.isSelected()){
                if(!tvTarjetas.getItems().isEmpty()) tvTarjetas.getItems().clear();
                lstWhere.clear();
                lstWhere.add("Folio = "+tfBusqFolio.getText());
                tvTarjetas.setItems(FXCollections.observableArrayList(tarDAO.consultarTarjetasCliente(lstWhere)));
                    tvTarjetas.getColumns().clear();
                    tvTarjetas.getColumns().addAll(folioTarjetaColum, fechaTarjetaColumna, precioColum,saldoColum, engancheColum, ClienteColumna, regionColumna, 
                                                   pagosColum);                
            }
            if (rbBusquedaCliente.isSelected()){
                lstWhere.clear();
                lstWhere.add("nomCliente like '%"+tfBusqCliente.getText()+"%' ");
                tvTarjetas.setItems(FXCollections.observableArrayList(tarDAO.consultarTarjetasCliente(lstWhere)));
                    tvTarjetas.getColumns().clear();
                    tvTarjetas.getColumns().addAll(folioTarjetaColum, fechaTarjetaColumna, precioColum,saldoColum, engancheColum, ClienteColumna, regionColumna, 
                                                   pagosColum);                
            }
            if (rbBusquedaFecha.isSelected()){
                lstWhere.clear();
                lstWhere.add("Fecha = '"+dpBusqFecha.getValue().toString()+"' ");
                tvTarjetas.setItems(FXCollections.observableArrayList(tarDAO.consultarTarjetasCliente(lstWhere)));
                    tvTarjetas.getColumns().clear();
                    tvTarjetas.getColumns().addAll(folioTarjetaColum, fechaTarjetaColumna, precioColum,saldoColum, engancheColum, ClienteColumna, regionColumna, 
                                                   pagosColum);                
            }
            if (rbBusquedaAtraso.isSelected()){
                lstWhere.clear();
                lstWhere.add("idTarjeta is not null");
                tvTarjetas.setItems(FXCollections.observableArrayList(tarDAO.consultarTarjetasCliente(lstWhere)));
                    tvTarjetas.getColumns().clear();
                    tvTarjetas.getColumns().addAll(folioTarjetaColum, fechaTarjetaColumna, precioColum,saldoColum, engancheColum, ClienteColumna, regionColumna, 
                                                   pagosColum);                
            }
            if (rbBusquedaCobradores.isSelected()){
                    
                    lstWhere.clear();
                    lstWhere.add("Nombre like '%"+cbCobrador.getValue().toString()+"%' ");
                    cobraDTO= cobraDAO.consultarCobradores(lstWhere).get(0);
                    
                    lstWhere.clear();
                    lstWhere.add("idCobrador = "+cobraDTO.getIdCobrador());
                    if (!tvTarjetas.getItems().isEmpty()) tvTarjetas.getItems().clear();
                    tvTarjetas.setItems(FXCollections.observableArrayList(tarDAO.consultarTarjetasPorCobrador(lstWhere)));
                    //tfTarjetasAsignadas.setText(String.valueOf(tvTarjetasAsignadas.getItems().size()));
                     
                    tvTarjetas.getColumns().clear();
                    tvTarjetas.getColumns().addAll(folioTarjetaColum, precioColum,saldoColum, engancheColum, ClienteColumna, regionColumna, 
                                                   fechaUltimoPagoColum, ultimoPagoColum);
            }
            if (rbBusquedaVendedor.isSelected()){
                    
                    lstWhere.clear();
                    lstWhere.add("Nombre like '%"+cbBuscarVendedor.getValue().toString()+"%' ");
                    venDTO= venDAO.consultarVendedor(lstWhere).get(0);
                    
                    lstWhere.clear();
                    lstWhere.add("t1.idVendedor = "+venDTO.getIdVendedor());
                    if (!tvTarjetas.getItems().isEmpty()) tvTarjetas.getItems().clear();
                    tvTarjetas.setItems(FXCollections.observableArrayList(tarDAO.consultarTarjetasPorVendedor(lstWhere)));
                    //tfTarjetasAsignadas.setText(String.valueOf(tvTarjetasAsignadas.getItems().size()));
                     
                    tvTarjetas.getColumns().clear();
                    tvTarjetas.getColumns().addAll(folioTarjetaColum, precioColum,saldoColum, engancheColum, ClienteColumna, vendedorColumna, regionColumna, 
                                                   fechaUltimoPagoColum, ultimoPagoColum);
            }            
            if (rbBusquedaRegion.isSelected()){
                lstWhere.clear();
                lstWhere.add("Region like '%"+cbBusqRegion.getValue().toString()+"%' ");
                tvTarjetas.setItems(FXCollections.observableArrayList(tarDAO.consultarTarjetasCliente(lstWhere)));
            }
        });       
        
        //Componentes busqueda de Clientes
        Label lbTipoBusquedaCli = new Label("Buscar Cliente por: ");
        
        ToggleGroup tgBusquedasCli = new ToggleGroup();
        
        RadioButton rbTodosCli = new RadioButton("Todos los Clientes");
        RadioButton rbidCli = new RadioButton("Id Cliente");
        RadioButton rbNombreCli = new RadioButton("Nombre Cliente");
        
        rbTodosCli.setSelected(true);
        
        rbTodosCli.setToggleGroup(tgBusquedasCli);
        rbidCli.setToggleGroup(tgBusquedasCli);
        rbNombreCli.setToggleGroup(tgBusquedasCli);
        
        TextField tfBuscarIdCliente = new TextField();
        tfBuscarIdCliente.setPrefWidth(80);
        TextField tfBuscarNombreCliente = new TextField();
        tfBuscarNombreCliente.setPrefWidth(200);
        
        Button btnSeleccionarCliente = new Button("Seleccionar Cliente");
        
       //Datos Cliente
        Label lbDatosCliente = new Label("Datos Cliente");
        Label lbIdCliente = new Label("id: ");
        Label lbNombreCliente = new Label("Nombre: ");
        Label lbDireccion = new Label("Direccion: ");
        Label lbTelefono = new Label("Telefono: ");
        
        TextField tfIdCliente = new TextField();
        tfIdCliente.setPrefWidth(180);
        TextField tfNombreCliente = new TextField();
        TextField tfDireccion = new TextField();
        TextField tfTelefono = new TextField();
        
        Button btnAgregarCliente =new Button("Agregar Cliente");
        btnAgregarCliente.setOnAction((event) -> {
            int result = cliDAO.insertarCliente(tfNombreCliente.getText(), tfDireccion.getText(), tfTelefono.getText(), "Nuevo");
            if (result != -1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Confirmación");
                alert.setContentText("Cliente Registrado con el Id #"+result);
                tfIdCliente.setText(String.valueOf(result));
                alert.showAndWait();

            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Este Cliente ya esta registrado"+result);
            
            }
        });
        
        TableView tvClientes = new TableView();
        tvClientes.setPrefSize(500, 300);
        
        TableColumn<inventario, String> idClienteColumna = new TableColumn<>("id Cliente");
        idClienteColumna.setMinWidth(60);
        idClienteColumna.setCellValueFactory(new PropertyValueFactory<>("IdCliente")); 
        
        TableColumn<inventario, String> nombreColumna = new TableColumn<>("Nombre");
        nombreColumna.setMinWidth(60);
        nombreColumna.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        
        TableColumn<inventario, String> direccionColumna = new TableColumn<>("Direccion");
        direccionColumna.setMinWidth(60);
        direccionColumna.setCellValueFactory(new PropertyValueFactory<>("Direccion"));
        
        TableColumn<inventario, String> telefonoColumna = new TableColumn<>("Telefono");
        telefonoColumna.setMinWidth(60);
        telefonoColumna.setCellValueFactory(new PropertyValueFactory<>("Telefono"));
        
        TableColumn<inventario, String> tipoClienteColumna = new TableColumn<>("Tipo CLiente");
        tipoClienteColumna.setMinWidth(60);
        tipoClienteColumna.setCellValueFactory(new PropertyValueFactory<>("TipoCLiente"));
        
        tvClientes.getColumns().addAll(idClienteColumna, nombreColumna, direccionColumna, telefonoColumna, tipoClienteColumna);
        lstWhere.clear();
        lstWhere.add("idCliente is not null");
        tvClientes.setItems(FXCollections.observableArrayList(cliDAO.consultarClientes(lstWhere)));
        tvClientes.setOnMouseClicked((event) -> {
            cliDTO = (cliente) tvClientes.getSelectionModel().getSelectedItem();
            tfIdCliente.setText(String.valueOf(cliDTO.getIdCliente()));
            tfNombreCliente.setText(cliDTO.getNombre());
            tfDireccion.setText(cliDTO.getDireccion());
            tfTelefono.setText(cliDTO.getTelefono());
        });
        
        btnSeleccionarCliente.setOnAction((event) -> {
            if (rbTodosCli.isSelected()){
                lstWhere.clear();
                lstWhere.add("idCliente is not null");
                tvClientes.setItems(FXCollections.observableArrayList(cliDAO.consultarClientes(lstWhere)));
            }
            if (rbidCli.isSelected()){
                if(!tvClientes.getItems().isEmpty()) tvClientes.getItems().clear();
                lstWhere.clear();
                lstWhere.add("idCliente ="+tfBuscarIdCliente.getText());
                tvClientes.setItems(FXCollections.observableArrayList(cliDAO.consultarClientes(lstWhere)));
            }
            if (rbNombreCli.isSelected()){
                if(!tvClientes.getItems().isEmpty()) tvClientes.getItems().clear();
                lstWhere.clear();
                lstWhere.add("Nombre like '%"+tfBuscarNombreCliente.getText()+"%' ");
                tvClientes.setItems(FXCollections.observableArrayList(cliDAO.consultarClientes(lstWhere)));
            }
        });
        
        //Datos Producto
        Label lbDatosProducto = new Label("Datos Productos");
        Label lbCodigoProducto = new Label("Codigo: ");
        Label lbDescripcionProducto = new Label("Descripcion:");
        Label lbCantidad = new Label("Cantidad: ");
        Label lbCostoUnitario = new Label("Costo Unitario: ");
        Label lbCostoTotal = new Label("Gran Total: $ 0.00");
        lbCostoTotal.getStyleClass().add("Gran-Total");
        lbCostoTotal.setAlignment(Pos.TOP_RIGHT);
        lbCostoTotal.setPrefWidth(150);
        
        ToggleGroup tgRadioPrecios = new ToggleGroup();
        RadioButton rbPrecioContado = new RadioButton("Precio Contado");
        rbPrecioContado.setSelected(true);
        rbPrecioContado.setToggleGroup(tgRadioPrecios);
        RadioButton rbPrecioCrediContado = new RadioButton("Precio Credi-Contado");
        rbPrecioCrediContado.setToggleGroup(tgRadioPrecios);
        RadioButton rbPrecioCredito = new RadioButton("Precio Credito");
        rbPrecioCredito.setToggleGroup(tgRadioPrecios);
        
        
        TextField tfCodigoProducto = new TextField();
        tfCodigoProducto.setPrefWidth(180);
        TextField tfDescripcionProducto = new TextField();
        TextField tfCostoUnitarioProducto = new TextField();
        TextField tfCantidadProducto = new TextField();
        TextField tfContado = new TextField();
        TextField tfCrediContado = new TextField();
        TextField tfCredito = new TextField();
        
        TableView tvProductos = new TableView();
        tvProductos.setPrefSize(500, 300);
        
        TableColumn<inventario, String> idProductoColumna = new TableColumn<>("idProducto");
        idProductoColumna.setMinWidth(60);
        idProductoColumna.setCellValueFactory(new PropertyValueFactory<>("Id Producto "));        
        
        TableColumn<inventario, String> codigoColumna = new TableColumn<>("Codigo Producto");
        codigoColumna.setMinWidth(120);
        codigoColumna.setCellValueFactory(new PropertyValueFactory<>("CodigoProducto"));
        
        TableColumn<inventario, Float> existenciaColumna = new TableColumn<>("Existencia");
        existenciaColumna.setMinWidth(80);
        existenciaColumna.setCellValueFactory(new PropertyValueFactory<>("Existencia"));

        TableColumn<inventario, Float> descripcionColumna = new TableColumn<>("Descripcion");
        descripcionColumna.setMinWidth(180);
        descripcionColumna.setCellValueFactory(new PropertyValueFactory<>("Descripcion"));

        TableColumn<inventario, Float> unidadMedidaColumna = new TableColumn<>("U. Med.");
        unidadMedidaColumna.setMinWidth(60);
        unidadMedidaColumna.setCellValueFactory(new PropertyValueFactory<>("UnidadMedida"));

        TableColumn<inventario, Float> precioContadoColumna = new TableColumn<>("Precio Contado");
        precioContadoColumna.setMinWidth(80);
        precioContadoColumna.setCellValueFactory(new PropertyValueFactory<>("PrecioContado"));

        TableColumn<inventario, Float> crediContadoColumna = new TableColumn<>("P. Credi-Contado");
        crediContadoColumna.setMinWidth(80);
        crediContadoColumna.setCellValueFactory(new PropertyValueFactory<>("Precio_crediContado"));

        TableColumn<inventario, Float> creditoColumna = new TableColumn<>("Precio Credito");
        creditoColumna.setMinWidth(80);
        creditoColumna.setCellValueFactory(new PropertyValueFactory<>("Precio_credito"));

        TableColumn<inventario, Float> proveedorColumna = new TableColumn<>("Proveedor");
        proveedorColumna.setMinWidth(80);
        proveedorColumna.setCellValueFactory(new PropertyValueFactory<>("Proveedor"));

        TableColumn<inventario, Float> categoriaColumna = new TableColumn<>("id Categoria");
        categoriaColumna.setMinWidth(80);
        categoriaColumna.setCellValueFactory(new PropertyValueFactory<>("idCategoria"));
        
        tvProductos.getColumns().setAll(codigoColumna, existenciaColumna, descripcionColumna, unidadMedidaColumna, precioContadoColumna,
                crediContadoColumna, creditoColumna, proveedorColumna, categoriaColumna);
        tvProductos.setOnMouseClicked((event) -> {
            invDTO = (inventario)tvProductos.getSelectionModel().getSelectedItem();
            tfCodigoProducto.setText(invDTO.getCodigoProducto());
            tfDescripcionProducto.setText(invDTO.getDescripcion());
            tfContado.setText(String.valueOf(invDTO.getPrecioContado()));
            tfCrediContado.setText(String.valueOf(invDTO.getPrecio_crediContado()));
            tfCredito.setText(String.valueOf(invDTO.getPrecio_credito()));
            if(rbPrecioContado.isSelected()){
                tfCostoUnitarioProducto.setText(tfContado.getText());
            }
        });
        
        rbPrecioContado.setOnMouseClicked((event) -> {
            tfCostoUnitarioProducto.setText(String.valueOf(tfContado.getText()));
        });
        rbPrecioCrediContado.setOnMouseClicked((event) -> {
            tfCostoUnitarioProducto.setText(String.valueOf(tfCrediContado.getText()));
        });
        rbPrecioCredito.setOnMouseClicked((event) -> {
            tfCostoUnitarioProducto.setText(String.valueOf(tfCredito.getText()));
        });
        
        lstWhere.clear();
        lstWhere.add("idProducto is not null");
        //lstInventario = invDAO.consultarInventario(lstWhere);
        tvProductos.setItems(FXCollections.observableArrayList(invDAO.consultarInventario(lstWhere)));
        
        //Datos Pagos Proyectados
        Label lbPagosProyectados = new Label("Pagos Proyectados:");
        Label lbFechaPago = new Label("Fecha Pago:");
        Label lbMonto = new Label("Monto:");
        Label lbEstado = new Label("Estado:");
        
        DatePicker dpFechaPago = new DatePicker();
        TextField tfMonto = new TextField();
        ComboBox cbEstado = new ComboBox(FXCollections.observableArrayList("Activo","Pagado","Parcialidad"));
        
        Button btnAgregarPagoProyectado = new Button("A");
        btnAgregarPagoProyectado.setId("btnIconoAdd");
        Button btnModificarPagoProyectado = new Button("M");
        btnModificarPagoProyectado.setId("btnIconoUpdate");
        Button btnEliminarPagoProyectado = new Button("E");
        btnEliminarPagoProyectado.setId("btnIconoRemove");
        
        TableView tvPagosProyectados = new TableView();
        tvPagosProyectados.setPrefSize(500, 300);
        
        TableColumn<pagosProyectados, String> fechaPagoColumna = new TableColumn<>("Fecha");
        fechaPagoColumna.setMinWidth(60);
        fechaPagoColumna.setCellValueFactory(new PropertyValueFactory<>("fechaPago"));        
        
        TableColumn<pagosProyectados, String> montoPagoColumna = new TableColumn<>("Monto");
        montoPagoColumna.setMinWidth(60);
        montoPagoColumna.setCellValueFactory(new PropertyValueFactory<>("monto"));        
        
        TableColumn<pagosProyectados, String> estadoColumna = new TableColumn<>("Estado");
        estadoColumna.setMinWidth(60);
        estadoColumna.setCellValueFactory(new PropertyValueFactory<>("estado"));        
        
        tvPagosProyectados.getColumns().setAll(fechaPagoColumna, montoPagoColumna, estadoColumna);
        
        btnAgregarPagoProyectado.setOnMouseClicked((event) -> {
            if (dpFechaPago.getValue()!=null && tfMonto.getText()!= null && cbEstado.getValue()!= null){
                pagosProyectados pagProy = new pagosProyectados(); 
                pagProy.setNumPago(ContNumPagosProyectados);
                pagProy.setFechaPago(dpFechaPago.getValue().toString());
                pagProy.setEstado(cbEstado.getValue().toString());
                pagProy.setMonto(Float.parseFloat(tfMonto.getText()));
                lstobPagProyectados.add(pagProy);
                tvPagosProyectados.setItems(lstobPagProyectados);
                dpFechaPago.setValue(null);
                tfMonto.setText(null);
                cbEstado.setValue(null);
            }
        });
        btnEliminarPagoProyectado.setOnMouseClicked((event) -> {
            //if (dpFechaPago.getValue()!=null && tfMonto.getText()!= null && cbEstado.getValue()!= null){
                System.out.println("Poscion a borrar: "+tvPagosProyectados.getSelectionModel().getSelectedIndex());
                lstobPagProyectados.remove(tvPagosProyectados.getSelectionModel().getSelectedIndex());
                tvPagosProyectados.setItems(lstobPagProyectados);
                dpFechaPago.setValue(null);
                tfMonto.setText(null);
                cbEstado.setValue(null);
            //}
        });
        btnModificarPagoProyectado.setOnMouseClicked((event) -> {
            if (dpFechaPago.getValue()!=null && tfMonto.getText()!= null && cbEstado.getValue()!= null){
                lstobPagProyectados.get(tvPagosProyectados.getSelectionModel().getSelectedIndex()).setFechaPago(dpFechaPago.getValue().toString());
                lstobPagProyectados.get(tvPagosProyectados.getSelectionModel().getSelectedIndex()).setMonto(Float.parseFloat(tfMonto.getText()));
                lstobPagProyectados.get(tvPagosProyectados.getSelectionModel().getSelectedIndex()).setEstado(cbEstado.getValue().toString());
                tvPagosProyectados.setItems(lstobPagProyectados);
                dpFechaPago.setValue(null);
                tfMonto.setText(null);
                cbEstado.setValue(null);
            }
        });
        
        tvPagosProyectados.setOnMouseClicked((event) -> {
            if (!lstobPagProyectados.isEmpty()){lstobPagProyectados.clear();}
            pagosProyectados pagProy = (pagosProyectados) tvPagosProyectados.getSelectionModel().getSelectedItem();
            dpFechaPago.setValue(LocalDate.parse(pagProy.getFechaPago()));
            tfMonto.setText(String.valueOf(pagProy.getMonto()));
            cbEstado.setValue(pagProy.getEstado());
            
        });
        
        //Datos Pagos Realizados
        Label lbPagosRealizados = new Label("Pagos Realizados:");
        Label lbFechaPagoR = new Label("Fecha Pago:");
        Label lbMontoR = new Label("Monto:");
        Label lbTipoR = new Label("Tipo:");
        
        DatePicker dpFechaPagoR = new DatePicker();
        TextField tfMontoR = new TextField();
        ComboBox cbTipoR = new ComboBox(FXCollections.observableArrayList("Enganche Pend.","Abono","Parcialidad"));
        
        Button btnAgregarPagoRealizado = new Button("A");
        btnAgregarPagoRealizado.setId("btnIconoAdd");
        Button btnModificarPagoRealizado = new Button("M");
        btnModificarPagoRealizado.setId("btnIconoUpdate");
        Button btnEliminarPagoRealizado = new Button("E");
        btnEliminarPagoRealizado.setId("btnIconoRemove");
        
        TableView tvPagosRealizado = new TableView();
        tvPagosRealizado.setPrefSize(500, 300);
        
        TableColumn<pagosRealizados, String> fechaPagoRealizadoColumna = new TableColumn<>("Fecha");
        fechaPagoRealizadoColumna.setMinWidth(60);
        fechaPagoRealizadoColumna.setCellValueFactory(new PropertyValueFactory<>("Fecha"));        
        
        TableColumn<pagosRealizados, String> montoPagoRealizadoColumna = new TableColumn<>("monto");
        montoPagoRealizadoColumna.setMinWidth(60);
        montoPagoRealizadoColumna.setCellValueFactory(new PropertyValueFactory<>("Monto"));        
        
        TableColumn<pagosRealizados, String> tipoColumna = new TableColumn<>("Tipo");
        tipoColumna.setMinWidth(60);
        tipoColumna.setCellValueFactory(new PropertyValueFactory<>("Tipo"));

        tvPagosRealizado.getColumns().setAll(fechaPagoRealizadoColumna, montoPagoRealizadoColumna, tipoColumna);
        
        btnAgregarPagoRealizado.setOnMouseClicked((event) -> {

            if (dpFechaPagoR.getValue()!=null && tfMontoR.getText()!= null && cbTipoR.getValue()!= null){
                pagReaDAO.insertarPagoRealizado(dpFechaPagoR.getValue().toString(), Float.parseFloat(tfMontoR.getText()), cbTipoR.getValue().toString(),tarDTO.getIdTarjeta());
     
                float saldoTemp = tarDTO.getSaldo();
                if(cbTipoR.getValue().toString()=="Abono"){
                    saldoTemp = saldoTemp - Float.parseFloat(tfMontoR.getText());
                    tfSaldo.setText(String.valueOf(saldoTemp));
                    tarDTO.setSaldo(saldoTemp);
                }
                lstWhere.clear();
                lstWhere.add("idTarjeta = "+ tarDTO.getIdTarjeta());
                if(!lstobPagRealizados.isEmpty()){lstobPagRealizados.clear();}
                lstobPagRealizados.addAll(pagReaDAO.consultarPagosRealizados(lstWhere));
                tvPagosRealizado.setItems(lstobPagRealizados);
                dpFechaPagoR.setValue(null);
                tfMontoR.setText(null);
                cbTipoR.setValue(null);
            }
        });
        btnEliminarPagoRealizado.setOnMouseClicked((event) -> {
                pagosRealizados pagR = (pagosRealizados)tvPagosRealizado.getSelectionModel().getSelectedItem();
                float saldoTemp = tarDTO.getSaldo();
                if(cbTipoR.getValue().toString()=="Abono"){
                    saldoTemp = saldoTemp + Float.parseFloat(tfMontoR.getText());
                    tfSaldo.setText(String.valueOf(saldoTemp));
                    tarDTO.setSaldo(saldoTemp);
                }
                lstWhere.clear();
                lstWhere.add("idPagoRealizado = "+ pagR.getIdPagoRealizado());
                if(!lstobPagRealizados.isEmpty()){lstobPagRealizados.clear();}
                lstobPagRealizados.addAll(pagReaDAO.consultarPagosRealizados(lstWhere));
                tvPagosRealizado.setItems(lstobPagRealizados);
                dpFechaPagoR.setValue(null);
                tfMontoR.setText(null);
                cbTipoR.setValue(null);
        });
        btnModificarPagoRealizado.setOnMouseClicked((event) -> {
            if (dpFechaPagoR.getValue()!=null && tfMontoR.getText()!= null && cbTipoR.getValue()!= null){
//                int indexSeleccionado =tvPagosRealizado.getSelectionModel().getSelectedIndex();
//                tvPagosRealizado.getSelectionModel().clearSelection();
//                lstobPagRealizados.get(indexSeleccionado).setFecha(dpFechaPagoR.getValue().toString());
//                lstobPagRealizados.get(indexSeleccionado).setMonto(Float.parseFloat(tfMontoR.getText()));
//                lstobPagRealizados.get(indexSeleccionado).setTipo(cbTipoR.getValue().toString());
//                tvPagosRealizado.setItems(lstobPagRealizados);
                dpFechaPagoR.setValue(null);
                tfMontoR.setText(null);
                cbTipoR.setValue(null);
            }
        });
        
        tvPagosRealizado.setOnMouseClicked((event) -> {

            pagosRealizados pagRealizado = (pagosRealizados) tvPagosRealizado.getSelectionModel().getSelectedItem();
            dpFechaPagoR.setValue(LocalDate.parse(pagRealizado.getFecha()));
            tfMontoR.setText(String.valueOf(pagRealizado.getMonto()));
            cbTipoR.setValue(pagRealizado.getTipo());
            
        });        
        
        
        GridPane gpPagosProyectados = new GridPane();
        gpPagosProyectados.setVgap(10);
        gpPagosProyectados.setHgap(10);
        gpPagosProyectados.setPadding(new Insets(5,5,5,5));
        
        gpPagosProyectados.add(lbPagosProyectados, 0, 0);
        gpPagosProyectados.add(lbFechaPago, 0, 1);
        gpPagosProyectados.add(dpFechaPago, 1, 1);
        gpPagosProyectados.add(lbMonto, 0, 2);
        gpPagosProyectados.add(tfMonto, 1, 2);
        gpPagosProyectados.add(lbEstado, 0, 3);
        gpPagosProyectados.add(cbEstado, 1, 3);
        gpPagosProyectados.add(btnAgregarPagoProyectado, 2, 3);
        gpPagosProyectados.add(btnModificarPagoProyectado, 3, 3);
        gpPagosProyectados.add(btnEliminarPagoProyectado, 4, 3);
        
        GridPane gpPagosRealizados = new GridPane();
        gpPagosRealizados.setVgap(10);
        gpPagosRealizados.setHgap(10);
        gpPagosRealizados.setPadding(new Insets(5,5,5,5));

        gpPagosRealizados.add(lbPagosRealizados,0,0);
        gpPagosRealizados.add(lbFechaPagoR,0,1);
        gpPagosRealizados.add(dpFechaPagoR,1,1);
        gpPagosRealizados.add(lbMontoR,0,2);
        gpPagosRealizados.add(tfMontoR,1,2);
        gpPagosRealizados.add(lbTipoR,0,3);
        gpPagosRealizados.add(cbTipoR,1,3);
        gpPagosRealizados.add(btnAgregarPagoRealizado, 2, 3);
        gpPagosRealizados.add(btnModificarPagoRealizado, 3, 3);
        gpPagosRealizados.add(btnEliminarPagoRealizado, 4, 3);
        
        VBox vbPagosProyectados = new VBox();
        vbPagosProyectados.setPadding(new Insets(5,5,5,5));
        vbPagosProyectados.setSpacing(5);
        vbPagosProyectados.getChildren().addAll(gpPagosProyectados, tvPagosProyectados);

        VBox vbPagosRealizados = new VBox();
        vbPagosRealizados.setPadding(new Insets(5,5,5,5));
        vbPagosRealizados.setSpacing(5);
        vbPagosRealizados.getChildren().addAll(gpPagosRealizados, tvPagosRealizado);

        //HBox hbPagos = new HBox(vbPagosProyectados, vbPagosRealizados);
        //hbPagos.setPadding(new Insets(5,5,5,5));
        //hbPagos.setSpacing(5);
        
        btnSeleccionar.setOnMouseClicked((event) -> {
            if(rbTodos.isSelected()){
                if(!tvProductos.getItems().isEmpty()) tvProductos.getItems().clear();
                lstWhere.clear();
                lstWhere.add("idProducto is not null");
                //lstInventario = invDAO.consultarInventario(lstWhere);
                tvProductos.setItems(FXCollections.observableArrayList(invDAO.consultarInventario(lstWhere)));
            }
            if (rbCodigo.isSelected()){
                if(!tvProductos.getItems().isEmpty()) tvProductos.getItems().clear();
                lstWhere.clear();
                lstWhere.add("CodigoProducto ='"+tfBuscarCodigo.getText()+"' ");
                tvProductos.setItems(FXCollections.observableArrayList(invDAO.consultarInventario(lstWhere)));
            }
            if (rbDescripcion.isSelected()){
                if(!tvProductos.getItems().isEmpty()) tvProductos.getItems().clear();
                lstWhere.clear();
                lstWhere.add("Descripcion like '%"+tfBuscarDescripcion.getText()+"%' ");
                tvProductos.setItems(FXCollections.observableArrayList(invDAO.consultarInventario(lstWhere)));
            }
        });
            
        TableView tvProductosSelccionados = new TableView();
        tvProductosSelccionados.setPrefSize(635, 200);
        tvProductosSelccionados.setMinSize(635, 200);
        tvProductosSelccionados.setMaxSize(635, 200);
        
        //TableColumn<detalle_venta, String> idProdSelecColumna = new TableColumn<>("id detalle venta");
        //idProdSelecColumna.setMinWidth(60);
        //idProdSelecColumna.setCellValueFactory(new PropertyValueFactory<>("Id Producto "));        
        
        TableColumn<detalle_venta, String> codigoProdSelecColumna = new TableColumn<>("Codigo Producto");
        codigoProdSelecColumna.setMinWidth(120);
        codigoProdSelecColumna.setCellValueFactory(new PropertyValueFactory<>("codigo_prod"));
        
        TableColumn<detalle_venta, Float> cantidadColumna = new TableColumn<>("Cantidad");
        cantidadColumna.setMinWidth(80);
        cantidadColumna.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        TableColumn<detalle_venta, Float> descProdSelecColumna = new TableColumn<>("Descripcion");
        descProdSelecColumna.setMinWidth(280);
        descProdSelecColumna.setCellValueFactory(new PropertyValueFactory<>("descprod"));

        TableColumn<detalle_venta, Float> precProdSelecColumna = new TableColumn<>("Precio Venta");
        precProdSelecColumna.setMinWidth(60);
        precProdSelecColumna.setCellValueFactory(new PropertyValueFactory<>("precio_venta"));

        TableColumn<detalle_venta, Float> subTotalColumna = new TableColumn<>("sub-Total");
        subTotalColumna.setMinWidth(80);
        subTotalColumna.setCellValueFactory(new PropertyValueFactory<>("subTotal"));

        tvProductosSelccionados.getColumns().setAll(codigoProdSelecColumna, descProdSelecColumna, cantidadColumna, 
                precProdSelecColumna, subTotalColumna);
        
        tvProductosSelccionados.setItems(FXCollections.observableArrayList(lstDetVenta));
        
        tvTarjetas.setOnMouseClicked((event) -> {
            if (!lstobPagRealizados.isEmpty()){
                lstobPagRealizados.clear();
                tvPagosRealizado.setItems(lstobPagRealizados);
            }
            tarDTO = (tarjeta) tvTarjetas.getSelectionModel().getSelectedItem();
            tfFolio.setText(tarDTO.getFolio());
            cbDiasCobro.setValue(tarDTO.getDiaCobro());
            dpFecha.setValue(LocalDate.parse(tarDTO.getFecha()));
            cbTipoPago.setValue(tarDTO.getTipoPago());
            tfClasificacion.setText(tarDTO.getClasificacion());
            tfPrecio.setText(String.valueOf(tarDTO.getPrecio()));
            cbRegion.setValue(tarDTO.getRegion());
            cbTipoPrecio.setValue(tarDTO.getTipoPrecio());
            tfEnganche.setText(String.valueOf(tarDTO.getEnganche()));
            tfSaldo.setText(String.valueOf(tarDTO.getSaldo()));
            tfPendEnganche.setText(String.valueOf(tarDTO.getEnganchePend()));
            tfPagos.setText(String.valueOf(tarDTO.getPagos()));
            lstWhere.clear();
            lstWhere.add("idTarjeta = "+ tarDTO.getIdTarjeta());
            lstobPagProyectados.addAll(pagProyDAO.consultarPagosProyectados(lstWhere));
            tvPagosProyectados.setItems(lstobPagProyectados);
            lstWhere.clear();
            lstWhere.add("idTarjeta = "+ tarDTO.getIdTarjeta());
            lstobPagRealizados.addAll(pagReaDAO.consultarPagosRealizados(lstWhere));
            tvPagosRealizado.setItems(lstobPagRealizados);
            lstWhere.clear();
            lstWhere.add("idVendedor ="+tarDTO.getIdVendedor());
            lstVendedor = venDAO.consultarVendedor(lstWhere);
            if (!lstVendedor.isEmpty()){
              cbVendedor.setValue(lstVendedor.get(0).getNombre());
            }else cbVendedor.setValue(null);
        });
        
        VBox vbModTarjeta = new VBox();
        vbModTarjeta.setAlignment(Pos.TOP_CENTER);
        vbModTarjeta.getChildren().add(lbTitulo);
        vbModTarjeta.getStyleClass().add("vbox-vistas");
        vbModTarjeta.setPrefHeight(altoPantalla-100);
        
        GridPane gpBusqueda = new GridPane();
        gpBusqueda.setPadding(new Insets(5, 5, 5, 5));
        gpBusqueda.setHgap(10);
        gpBusqueda.setVgap(10);
        
        gpBusqueda.add(lbTipoBusqueda, 0, 0);
        gpBusqueda.add(rbBusquedaTodo, 0, 1);
        gpBusqueda.add(rbBusquedaFolio, 1, 1);
        gpBusqueda.add(tfBusqFolio, 1, 2);
        gpBusqueda.add(rbBusquedaCliente, 2, 1);
        gpBusqueda.add(tfBusqCliente, 2, 2);
        gpBusqueda.add(rbBusquedaFecha, 3, 1);
        gpBusqueda.add(dpBusqFecha, 3, 2);
        gpBusqueda.add(rbBusquedaAtraso, 4, 1);
        gpBusqueda.add(tfBusqAtraso, 4, 2);
        gpBusqueda.add(rbBusquedaCobradores, 5, 1);
        gpBusqueda.add(cbCobrador, 5, 2);
        gpBusqueda.add(rbBusquedaRegion, 6, 1);
        gpBusqueda.add(cbBusqRegion, 6, 2);
        gpBusqueda.add(rbBusquedaVendedor, 7, 1);
        gpBusqueda.add(cbBuscarVendedor, 7, 2);
        gpBusqueda.add(btnBuscaTarjetas, 8, 2);
        
        
        GridPane gpDatosTarjeta = new GridPane();
        gpDatosTarjeta.setPadding(new Insets(5, 5, 5, 5));
        gpDatosTarjeta.setHgap(15);
        gpDatosTarjeta.setVgap(10);
        gpDatosTarjeta.add(lbDatosTarjeta, 0, 0);
        gpDatosTarjeta.add(lbFolio, 0, 1);
        gpDatosTarjeta.add(tfFolio, 1, 1);
        gpDatosTarjeta.add(lbFecha, 0, 2);
        gpDatosTarjeta.add(dpFecha, 1, 2);
        gpDatosTarjeta.add(lbPrecio, 0, 3);
        gpDatosTarjeta.add(tfPrecio, 1, 3);
        gpDatosTarjeta.add(lbTipoPrecio, 0, 4);
        gpDatosTarjeta.add(cbTipoPrecio, 1, 4);
        gpDatosTarjeta.add(lbEnganche, 0, 5);
        gpDatosTarjeta.add(tfEnganche, 1, 5);
        gpDatosTarjeta.add(lbPendEnganche, 0, 6);
        gpDatosTarjeta.add(tfPendEnganche, 1, 6);
        gpDatosTarjeta.add(lbPagos, 0, 7);
        gpDatosTarjeta.add(tfPagos, 1, 7);
        gpDatosTarjeta.add(lbDiaCobro, 2, 1);
        gpDatosTarjeta.add(cbDiasCobro, 3, 1);
        gpDatosTarjeta.add(lbTipoPago, 2, 2);
        gpDatosTarjeta.add(cbTipoPago, 3, 2);
        gpDatosTarjeta.add(lbClasificacion, 2, 3);
        gpDatosTarjeta.add(tfClasificacion, 3, 3);
        gpDatosTarjeta.add(lbRegion, 2, 4);
        gpDatosTarjeta.add(cbRegion, 3, 4);
        gpDatosTarjeta.add(lbSaldo, 2, 5);
        gpDatosTarjeta.add(tfSaldo, 3, 5);
        gpDatosTarjeta.add(lbVendedor, 2, 6);
        gpDatosTarjeta.add(cbVendedor, 3, 6);
        gpDatosTarjeta.add(btnActualizarTarjeta, 3, 7);
        
        
        VBox vbRigth = new VBox();
        
        GridPane gpDatosCliente = new GridPane();
        gpDatosCliente.setPadding(new Insets(5,5,5,5));
        gpDatosCliente.setVgap(5);
        gpDatosCliente.setHgap(5);
        gpDatosCliente.add(lbDatosCliente, 0, 0);
        gpDatosCliente.add(lbIdCliente, 0, 1);
        gpDatosCliente.add(tfIdCliente, 0, 2);
        gpDatosCliente.add(lbNombreCliente, 1, 1);
        gpDatosCliente.add(tfNombreCliente, 1, 2);
        gpDatosCliente.add(lbDireccion, 2, 1);
        gpDatosCliente.add(tfDireccion, 2, 2);
        gpDatosCliente.add(lbTelefono, 3, 1);
        gpDatosCliente.add(tfTelefono, 3, 2);
        gpDatosCliente.add(btnAgregarCliente, 4, 5);
        
        GridPane gpTipoBusquedaClientes = new GridPane();
        gpTipoBusquedaClientes.setPadding(new Insets(5,5,5,5));
        gpTipoBusquedaClientes.setVgap(15);
        gpTipoBusquedaClientes.setHgap(15);
        gpTipoBusquedaClientes.add(lbTipoBusquedaCli, 0, 0);
        gpTipoBusquedaClientes.add(rbTodosCli, 0, 1);
        gpTipoBusquedaClientes.add(rbidCli, 1, 1);
        gpTipoBusquedaClientes.add(rbNombreCli, 2, 1);
        gpTipoBusquedaClientes.add(tfBuscarIdCliente, 1, 2);
        gpTipoBusquedaClientes.add(tfBuscarNombreCliente, 2, 2);
        gpTipoBusquedaClientes.add(btnSeleccionarCliente, 6, 2);
        
        VBox vbDatosCliente = new VBox();
        vbDatosCliente.setSpacing(10);
        vbDatosCliente.getChildren().addAll(gpTipoBusquedaClientes, tvClientes, gpDatosCliente);

        GridPane gpDatosProducto = new GridPane();
        //gpDatosProducto.setPadding(new Insets(5,5,5,5));
        //gpDatosProducto.setVgap(5);
        //gpDatosProducto.setHgap(5);
        gpDatosProducto.add(lbDatosProducto, 0, 0);
        gpDatosProducto.add(lbCodigoProducto, 0, 1);
        gpDatosProducto.add(tfCodigoProducto, 1, 1);
        gpDatosProducto.add(lbDescripcionProducto, 0, 2);
        gpDatosProducto.add(tfDescripcionProducto, 1, 2);
        gpDatosProducto.add(lbCantidad, 0, 3);
        gpDatosProducto.add(tfCantidadProducto, 1, 3);
        gpDatosProducto.add(lbCostoUnitario, 0, 4);
        //gpDatosProducto.add(, 1, 4);
        gpDatosProducto.add(lbCostoTotal, 0, 5);
        //gpDatosProducto.add(, 1, 5);

        HBox hbProductos = new HBox();
        hbProductos.setSpacing(5);
        hbProductos.getChildren().addAll(tvProductos,gpDatosProducto);

        GridPane gpTipoSeleccion = new GridPane();
        gpTipoSeleccion.setPadding(new Insets(5, 5, 5, 5));
        gpTipoSeleccion.setVgap(5);
        gpTipoSeleccion.setHgap(5);
        gpTipoSeleccion.add(lbTipoBusqueda, 0, 0);
        gpTipoSeleccion.add(rbTodos, 0, 1);
        gpTipoSeleccion.add(rbCodigo, 1, 1);
        gpTipoSeleccion.add(tfBuscarCodigo, 1, 2);
        gpTipoSeleccion.add(rbDescripcion, 2, 1);
        gpTipoSeleccion.add(tfBuscarDescripcion, 2, 2);
        gpTipoSeleccion.add(rbCategoria, 3, 1);
        gpTipoSeleccion.add(cbBuscarCategoria, 3, 2);
        gpTipoSeleccion.add(btnSeleccionar, 4, 2);

        VBox vbProductos = new VBox();
        vbProductos.setSpacing(5);
        vbProductos.getChildren().addAll(gpTipoSeleccion, hbProductos);
        
        TabPane tpPpal = new TabPane();
        tpPpal.setPrefWidth(800);
        
        Tab tTarjeta = new Tab();
        tTarjeta.setText("Tarjeta");
        Tab tCliente = new Tab();
        tCliente.setText("Cliente");
        Tab tProducto = new Tab();
        tProducto.setText("Producto");
        Tab tPagosProyectados = new Tab();
        tPagosProyectados.setText("Proyeccion de Pagos");
        Tab tPagosRealizados = new Tab();
        tPagosRealizados.setText("Pagos Realizados");
        
        tTarjeta.setContent(gpDatosTarjeta);
        tCliente.setContent(vbDatosCliente);
        tProducto.setContent(vbProductos);
        tPagosProyectados.setContent(vbPagosProyectados);
        tPagosRealizados.setContent(vbPagosRealizados);
        
        tpPpal.getTabs().addAll(tTarjeta, tCliente, tProducto, tPagosProyectados, tPagosRealizados);
        tpPpal.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        
        VBox vbTablaTarjetas = new VBox();
        vbTablaTarjetas.getChildren().addAll(gpFiltroFechaCobro, lbTablaTarjetas, tvTarjetas, gpCalculo, gpCalculoComision);
        
        HBox hbMain = new HBox();
        hbMain.setSpacing(15);
        hbMain.getChildren().addAll(vbTablaTarjetas, tpPpal);
        hbMain.setAlignment(Pos.CENTER);
        vbModTarjeta.getChildren().addAll(gpBusqueda, hbMain);
        return vbModTarjeta;
    }
    private VBox vEliminarTarjeta(){
         Label lbTitulo = new Label("E L I M I N A R  T A R J E T A");
         lbTitulo.getStyleClass().add("titulo-vista");
         lbTitulo.setAlignment(Pos.CENTER);
         
        //Seleccionar Busqueda
        
        Label lbTipoBusqueda = new Label("Buscar por: ");
        
        ToggleGroup tgSelecBusqueda = new ToggleGroup();
        
        RadioButton rbBusquedaTodo = new RadioButton("Todos");
        rbBusquedaTodo.setToggleGroup(tgSelecBusqueda);

        RadioButton rbBusquedaFolio = new RadioButton("Folio");
        rbBusquedaFolio.setToggleGroup(tgSelecBusqueda);
        
        RadioButton rbBusquedaCliente = new RadioButton("Cliente");
        rbBusquedaCliente.setToggleGroup(tgSelecBusqueda);
        
        RadioButton rbBusquedaFecha = new RadioButton("Fecha");
        rbBusquedaFecha.setToggleGroup(tgSelecBusqueda);
        
        RadioButton rbBusquedaAtraso = new RadioButton("Atraso Pagos");
        rbBusquedaAtraso.setToggleGroup(tgSelecBusqueda);
        
        RadioButton rbBusquedaSemana = new RadioButton("Semana");
        rbBusquedaSemana.setToggleGroup(tgSelecBusqueda);
        
        RadioButton rbBusquedaRegion = new RadioButton("Region");
        rbBusquedaRegion.setToggleGroup(tgSelecBusqueda);
        
        rbBusquedaTodo.selectedProperty().setValue(Boolean.TRUE);
        
        TextField tfBusqFolio = new TextField();
        TextField tfBusqCliente = new TextField();
        DatePicker dpBusqFecha = new DatePicker(LocalDate.now());
        TextField tfBusqAtraso = new TextField();
        TextField tfBusqSemana = new TextField();
        ComboBox cbBusqRegion = new ComboBox();
        cbBusqRegion.setPrefWidth(250);
        
        Button btnBuscaTarjetas = new Button("Seleccionar Tarjetas");
            
        //Tabla Tarjetas
       
        Label lbTablaTarjetas = new Label("Tarjetas Seleccionadas:");
        TableView tvTarjetas = new TableView();
         
        TableColumn folioTarjetaColum = new TableColumn("Folio ");
        folioTarjetaColum.setCellValueFactory(new PropertyValueFactory<>("folio"));
        folioTarjetaColum.setPrefWidth(40);
        
        TableColumn fechaTarjetaColumna = new TableColumn("Fecha");
        fechaTarjetaColumna.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        fechaTarjetaColumna.setPrefWidth(65);
        
        
        TableColumn precioColum = new TableColumn("Precio");
        precioColum.setCellValueFactory(new PropertyValueFactory<>("precio"));
        precioColum.setPrefWidth(50);
        
        TableColumn engancheColum = new TableColumn("Enganche");
        engancheColum.setCellValueFactory(new PropertyValueFactory<>("Enganche"));
        engancheColum.setPrefWidth(70);

        TableColumn ClienteColumna = new TableColumn("Cliente");
        ClienteColumna.setCellValueFactory(new PropertyValueFactory<>("nomCliente"));
        ClienteColumna.setPrefWidth(150);

        TableColumn regionColumna = new TableColumn("Region");
        regionColumna.setCellValueFactory(new PropertyValueFactory<>("Region"));
        regionColumna.setPrefWidth(135);

        TableColumn pagosColum = new TableColumn("Pagos");
        pagosColum.setCellValueFactory(new PropertyValueFactory<>("Pagos"));
        pagosColum.setPrefWidth(50);
        
        tvTarjetas.getColumns().addAll(folioTarjetaColum, fechaTarjetaColumna, precioColum, engancheColum, ClienteColumna, regionColumna, 
                pagosColum);
        
        MenuItem item = new MenuItem("Copiar Todo");
        item.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ObservableList<tarjeta> posList = tvTarjetas.getItems();
                int old_r = -1;
                StringBuilder clipboardString = new StringBuilder();
                clipboardString.append("Id \t Folio \tidCliente \tPrecio \tEnganche \tId Vendedor \t Clasificacion \tTipo Pago \tRegion \tDia Cobro \tEnganche Pend. \tSaldo \tFecha \tPagos \n");
                for (tarjeta p : posList) {

                       clipboardString.append(
                                p.getIdTarjeta()+" \t"
                               +p.getFolio()+" \t"
                               +p.getIdCliente()+" \t"
                               +p.getPrecio()+" \t"
                               +p.getEnganche()+" \t"
                               +p.getIdVendedor()+" \t"
                               +p.getClasificacion()+" \t"
                               +p.getTipoPago()+" \t"
                               +p.getRegion()+" \t"
                               +p.getDiaCobro()+" \t"
                               +p.getEnganchePend()+" \t"
                               +p.getSaldo()+" \t"
                               +p.getFecha()+" \t"
                               +p.getPagos()+" \n");
                }  
            final ClipboardContent content = new ClipboardContent();
            content.putString(clipboardString.toString());
            Clipboard.getSystemClipboard().setContent(content);
            }
            } );
            ContextMenu menu = new ContextMenu();
            menu.getItems().addAll(item);
            tvTarjetas.setContextMenu(menu);
            
        lstWhere.clear();
        lstWhere.add("idTarjeta is not null");
        tvTarjetas.setItems(FXCollections.observableArrayList(tarDAO.consultarTarjetasCliente(lstWhere)));
        EventHandler<? super MouseEvent> value;
        
        btnBuscaTarjetas.setOnMouseClicked((event) -> {
            if (rbBusquedaTodo.isSelected()){
                if(!tvTarjetas.getItems().isEmpty()) tvTarjetas.getItems().clear();
                lstWhere.clear();
                lstWhere.add("idTarjeta is not null");
                tvTarjetas.setItems(FXCollections.observableArrayList(tarDAO.consultarTarjetasCliente(lstWhere)));
            }
            if (rbBusquedaFolio.isSelected()){
                if(!tvTarjetas.getItems().isEmpty()) tvTarjetas.getItems().clear();
                lstWhere.clear();
                lstWhere.add("Folio = "+tfBusqFolio.getText());
                tvTarjetas.setItems(FXCollections.observableArrayList(tarDAO.consultarTarjetasCliente(lstWhere)));
            }
            if (rbBusquedaCliente.isSelected()){
                lstWhere.clear();
                lstWhere.add("nomCliente like '%"+tfBusqCliente.getText()+"%' ");
                tvTarjetas.setItems(FXCollections.observableArrayList(tarDAO.consultarTarjetasCliente(lstWhere)));
            }
            if (rbBusquedaFecha.isSelected()){
                lstWhere.clear();
                lstWhere.add("Fecha = '"+dpBusqFecha.getValue().toString()+"' ");
                tvTarjetas.setItems(FXCollections.observableArrayList(tarDAO.consultarTarjetasCliente(lstWhere)));
            }
            if (rbBusquedaAtraso.isSelected()){
                lstWhere.clear();
                lstWhere.add("idTarjeta is not null");
                tvTarjetas.setItems(FXCollections.observableArrayList(tarDAO.consultarTarjetasCliente(lstWhere)));
            }
            if (rbBusquedaSemana.isSelected()){
                lstWhere.clear();
                lstWhere.add("idTarjeta is not null");
                tvTarjetas.setItems(FXCollections.observableArrayList(tarDAO.consultarTarjetasCliente(lstWhere)));
            }
            if (rbBusquedaRegion.isSelected()){
                lstWhere.clear();
                lstWhere.add("idTarjeta is not null");
                tvTarjetas.setItems(FXCollections.observableArrayList(tarDAO.consultarTarjetasCliente(lstWhere)));
            }
        });
 
        //Datos Tarjeta
        Label lbDatosTarjeta = new Label("Datos de Tarjeta");
        Label lbFolio = new Label("Folio");
        Label lbFecha = new Label("Fecha");
        Label lbPrecio = new Label("Precio");
        Label lbTipoPrecio = new Label("Tipo Precio");
        Label lbEnganche = new Label("Enganche");
        Label lbPendEnganche = new Label("Pend. \n Enganche");
        lbPendEnganche.setPrefHeight(55);
        Label lbSaldo = new Label("Saldo");
        Label lbPagos = new Label("Pagos");
        Label lbDiaCobro = new Label("DiasCobro");
        Label lbTipoPago = new Label("Tipo Pagos***");
        Label lbRegion = new Label("Region");
        Label lbVendedor = new Label("Vendedor: ");
        
        TextField tfFolio = new TextField();
        tfFolio.setPrefWidth(80);
        DatePicker dpFecha = new DatePicker(LocalDate.now());
        dpFecha.setPrefWidth(120);
        TextField tfPrecio = new TextField();
        ObservableList<String> lstTipoPrecio = FXCollections.observableArrayList("Contado","Cre-Contado", "Credito");
        ComboBox cbTipoPrecio = new ComboBox(lstTipoPrecio);
        TextField tfEnganche = new TextField();
        tfEnganche.setPrefWidth(80);
        tfEnganche.setMinWidth(80);
        tfEnganche.setMaxWidth(80);
        TextField tfPendEnganche = new TextField();
        tfPendEnganche.setPrefWidth(80);
        tfPendEnganche.setMinWidth(80);
        tfPendEnganche.setMaxWidth(80);
        TextField tfSaldo = new TextField();
        tfSaldo.setPrefWidth(80);
        tfSaldo.setMinWidth(80);
        tfSaldo.setMaxWidth(80);
        TextField tfPagos = new TextField();
        tfPagos.setPrefWidth(80);
        tfPagos.setMinWidth(80);
        tfPagos.setMaxWidth(80);
        ObservableList<String> lstDiasCobro = FXCollections.observableArrayList(
                "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo");
        ComboBox cbDiasCobro = new ComboBox(lstDiasCobro);
        ObservableList<String> lstTipoPago = FXCollections.observableArrayList(
                "Semanal", "Quincenal", "Mensual");
        ComboBox cbTipoPago = new ComboBox(lstTipoPago);
        ObservableList<String> lstRegion = FXCollections.observableArrayList(
                "Soledad de Doblado", "Manlio Fabio Altamirano", "Mata de Agua", "Rincon de Barradas", "Loma de los Carmona");
        ComboBox cbRegion = new ComboBox(lstRegion);
        TextField tfVendedor = new TextField();
        
        //Componentes busqueda de Clientes
        Label lbTipoBusquedaCli = new Label("Buscar Cliente por: ");
        
        ToggleGroup tgBusquedasCli = new ToggleGroup();
        
        RadioButton rbTodosCli = new RadioButton("Todos los Clientes");
        RadioButton rbidCli = new RadioButton("Id Cliente");
        RadioButton rbNombreCli = new RadioButton("Nombre Cliente");
        
        rbTodosCli.setSelected(true);
        
        rbTodosCli.setToggleGroup(tgBusquedasCli);
        rbidCli.setToggleGroup(tgBusquedasCli);
        rbNombreCli.setToggleGroup(tgBusquedasCli);
        
        TextField tfBuscarIdCliente = new TextField();
        tfBuscarIdCliente.setPrefWidth(80);
        TextField tfBuscarNombreCliente = new TextField();
        tfBuscarNombreCliente.setPrefWidth(200);
        
        Button btnSeleccionarCliente = new Button("Seleccionar Cliente");
        
       //Datos Cliente
        Label lbDatosCliente = new Label("Datos Cliente");
        Label lbIdCliente = new Label("id: ");
        Label lbNombreCliente = new Label("Nombre: ");
        Label lbDireccion = new Label("Direccion: ");
        Label lbTelefono = new Label("Telefono: ");
        
        TextField tfIdCliente = new TextField();
        tfIdCliente.setPrefWidth(180);
        TextField tfNombreCliente = new TextField();
        TextField tfDireccion = new TextField();
        TextField tfTelefono = new TextField();
        
        Button btnAgregarCliente =new Button("Agregar Cliente");
        btnAgregarCliente.setOnAction((event) -> {
            int result = cliDAO.insertarCliente(tfNombreCliente.getText(), tfDireccion.getText(), tfTelefono.getText(), "Nuevo");
            if (result != -1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Confirmación");
                alert.setContentText("Cliente Registrado con el Id #"+result);
                tfIdCliente.setText(String.valueOf(result));
                alert.showAndWait();

            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Este Cliente ya esta registrado"+result);
            
            }
        });
        
        TableView tvClientes = new TableView();
        tvClientes.setPrefSize(500, 300);
        
        TableColumn<inventario, String> idClienteColumna = new TableColumn<>("id Cliente");
        idClienteColumna.setMinWidth(60);
        idClienteColumna.setCellValueFactory(new PropertyValueFactory<>("IdCliente")); 
        
        TableColumn<inventario, String> nombreColumna = new TableColumn<>("Nombre");
        nombreColumna.setMinWidth(60);
        nombreColumna.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        
        TableColumn<inventario, String> direccionColumna = new TableColumn<>("Direccion");
        direccionColumna.setMinWidth(60);
        direccionColumna.setCellValueFactory(new PropertyValueFactory<>("Direccion"));
        
        TableColumn<inventario, String> telefonoColumna = new TableColumn<>("Telefono");
        telefonoColumna.setMinWidth(60);
        telefonoColumna.setCellValueFactory(new PropertyValueFactory<>("Telefono"));
        
        TableColumn<inventario, String> tipoClienteColumna = new TableColumn<>("Tipo CLiente");
        tipoClienteColumna.setMinWidth(60);
        tipoClienteColumna.setCellValueFactory(new PropertyValueFactory<>("TipoCLiente"));
        
        tvClientes.getColumns().addAll(idClienteColumna, nombreColumna, direccionColumna, telefonoColumna, tipoClienteColumna);
        lstWhere.clear();
        lstWhere.add("idCliente is not null");
        tvClientes.setItems(FXCollections.observableArrayList(cliDAO.consultarClientes(lstWhere)));
        tvClientes.setOnMouseClicked((event) -> {
            cliDTO = (cliente) tvClientes.getSelectionModel().getSelectedItem();
            tfIdCliente.setText(String.valueOf(cliDTO.getIdCliente()));
            tfNombreCliente.setText(cliDTO.getNombre());
            tfDireccion.setText(cliDTO.getDireccion());
            tfTelefono.setText(cliDTO.getTelefono());
        });
        
        btnSeleccionarCliente.setOnAction((event) -> {
            if (rbTodosCli.isSelected()){
                lstWhere.clear();
                lstWhere.add("idCliente is not null");
                tvClientes.setItems(FXCollections.observableArrayList(cliDAO.consultarClientes(lstWhere)));
            }
            if (rbidCli.isSelected()){
                if(!tvClientes.getItems().isEmpty()) tvClientes.getItems().clear();
                lstWhere.clear();
                lstWhere.add("idCliente ="+tfBuscarIdCliente.getText());
                tvClientes.setItems(FXCollections.observableArrayList(cliDAO.consultarClientes(lstWhere)));
            }
            if (rbNombreCli.isSelected()){
                if(!tvClientes.getItems().isEmpty()) tvClientes.getItems().clear();
                lstWhere.clear();
                lstWhere.add("Nombre like '%"+tfBuscarNombreCliente.getText()+"%' ");
                tvClientes.setItems(FXCollections.observableArrayList(cliDAO.consultarClientes(lstWhere)));
            }
        });
        
        //Datos Producto
        Label lbDatosProducto = new Label("Datos Productos");
        Label lbIdProducto = new Label("Id: ");
        Label lbDescripcionProducto = new Label("Descripcion:");
        Label lbCantidad = new Label("Cantidad: ");
        Label lbCostoUnitario = new Label("Costo Unitario: ");
        Label lbCostoTotal = new Label("CostoTotal");
        
        ComboBox cbIdProducto = new ComboBox();
        cbIdProducto.setPrefWidth(180);
        TextField tfDescripcionProducto = new TextField();
        TextField tfCostoUnitarioProducto = new TextField();
        TextField tfCantidadProducto = new TextField();
        
        tvTarjetas.setOnMouseClicked((event) -> {
            tarDTO = (tarjeta) tvTarjetas.getSelectionModel().getSelectedItem();
            tfFolio.setText(tarDTO.getFolio());
            cbDiasCobro.setValue(tarDTO.getDiaCobro());
            dpFecha.setValue(LocalDate.parse(tarDTO.getFecha()));
            cbTipoPago.setValue(tarDTO.getTipoPago());
            tfPrecio.setText(String.valueOf(tarDTO.getPrecio()));
            cbRegion.setValue(tarDTO.getRegion());
            //cbTipoPrecio.setValue(tarDTO.getTi);
            tfEnganche.setText(String.valueOf(tarDTO.getEnganche()));
            tfSaldo.setText(String.valueOf(tarDTO.getSaldo()));
            tfPendEnganche.setText(String.valueOf(tarDTO.getEnganchePend()));
            tfPagos.setText(String.valueOf(tarDTO.getPagos()));
            
            
        });
        
        Button btnSalir = new Button("Salir");
        btnSalir.setOnAction((event) -> {
            removerVistas();
        });
        
        Button btnEliminarTarjeta = new Button("Eliminar Tarjeta");
        btnEliminarTarjeta.setOnAction((event) -> {
            //tarDTO = (tarjeta) tvTarjetas.getSelectionModel().getSelectedItem();
            Alert alertMessage = new Alert(Alert.AlertType.CONFIRMATION);
            alertMessage.setTitle("CONFIRMATION");
            alertMessage.setContentText("¿Estas Seguro Eliminar el Tarjeta?");
            Optional<ButtonType> action = alertMessage.showAndWait();
            if (action.get() == ButtonType.OK) {
                tarDAO.eliminarTarjeta(tarDTO.getIdTarjeta());
                vbAreaTrabajo.getChildren().clear();
                vbAreaTrabajo.getChildren().add(vEliminarTarjeta());
            }
        });
        
        HBox hbBotones = new HBox(btnSalir, btnEliminarTarjeta);
        hbBotones.setAlignment(Pos.TOP_RIGHT);
        hbBotones.setSpacing(10);
        
        
        VBox vbModTarjeta = new VBox();
        vbModTarjeta.setAlignment(Pos.TOP_CENTER);
        vbModTarjeta.getChildren().add(lbTitulo);
        vbModTarjeta.getStyleClass().add("vbox-vistas");
        vbModTarjeta.setPrefHeight(altoPantalla-100);
        
        GridPane gpBusqueda = new GridPane();
        gpBusqueda.setPadding(new Insets(5, 5, 5, 5));
        gpBusqueda.setHgap(25);
        gpBusqueda.setVgap(25);
        
        gpBusqueda.add(lbTipoBusqueda, 0, 0);
        gpBusqueda.add(rbBusquedaTodo, 0, 1);
        gpBusqueda.add(rbBusquedaFolio, 1, 1);
        gpBusqueda.add(tfBusqFolio, 1, 2);
        gpBusqueda.add(rbBusquedaCliente, 2, 1);
        gpBusqueda.add(tfBusqCliente, 2, 2);
        gpBusqueda.add(rbBusquedaFecha, 3, 1);
        gpBusqueda.add(dpBusqFecha, 3, 2);
        gpBusqueda.add(rbBusquedaAtraso, 4, 1);
        gpBusqueda.add(tfBusqAtraso, 4, 2);
        gpBusqueda.add(rbBusquedaSemana, 5, 1);
        gpBusqueda.add(tfBusqSemana, 5, 2);
        gpBusqueda.add(rbBusquedaRegion, 6, 1);
        gpBusqueda.add(cbBusqRegion, 6, 2);
        gpBusqueda.add(btnBuscaTarjetas, 7, 2);
        
        
        GridPane gpDatosTarjeta = new GridPane();
        gpDatosTarjeta.setPadding(new Insets(5, 5, 5, 5));
        gpDatosTarjeta.setHgap(15);
        gpDatosTarjeta.setVgap(10);
        gpDatosTarjeta.add(lbDatosTarjeta, 0, 0);
        gpDatosTarjeta.add(lbFolio, 0, 1);
        gpDatosTarjeta.add(tfFolio, 1, 1);
        gpDatosTarjeta.add(lbFecha, 0, 2);
        gpDatosTarjeta.add(dpFecha, 1, 2);
        gpDatosTarjeta.add(lbPrecio, 0, 3);
        gpDatosTarjeta.add(tfPrecio, 1, 3);
        gpDatosTarjeta.add(lbTipoPrecio, 0, 4);
        gpDatosTarjeta.add(cbTipoPrecio, 1, 4);
        gpDatosTarjeta.add(lbEnganche, 0, 5);
        gpDatosTarjeta.add(tfEnganche, 1, 5);
        gpDatosTarjeta.add(lbPendEnganche, 0, 6);
        gpDatosTarjeta.add(tfPendEnganche, 1, 6);
        gpDatosTarjeta.add(lbPagos, 0, 7);
        gpDatosTarjeta.add(tfPagos, 1, 7);
        gpDatosTarjeta.add(lbDiaCobro, 2, 1);
        gpDatosTarjeta.add(cbDiasCobro, 3, 1);
        gpDatosTarjeta.add(lbTipoPago, 2, 2);
        gpDatosTarjeta.add(cbTipoPago, 3, 2);
        gpDatosTarjeta.add(lbRegion, 2, 3);
        gpDatosTarjeta.add(cbRegion, 3, 3);
        gpDatosTarjeta.add(lbSaldo, 2, 4);
        gpDatosTarjeta.add(tfSaldo, 3, 4);
        gpDatosTarjeta.add(lbVendedor, 2, 5);
        gpDatosTarjeta.add(tfVendedor, 3, 5);
        
        
        VBox vbRigth = new VBox();
        
        GridPane gpDatosCliente = new GridPane();
        gpDatosCliente.setPadding(new Insets(5,5,5,5));
        gpDatosCliente.setVgap(5);
        gpDatosCliente.setHgap(5);
        gpDatosCliente.add(lbDatosCliente, 0, 0);
        gpDatosCliente.add(lbIdCliente, 0, 1);
        gpDatosCliente.add(tfIdCliente, 0, 2);
        gpDatosCliente.add(lbNombreCliente, 1, 1);
        gpDatosCliente.add(tfNombreCliente, 1, 2);
        gpDatosCliente.add(lbDireccion, 2, 1);
        gpDatosCliente.add(tfDireccion, 2, 2);
        gpDatosCliente.add(lbTelefono, 3, 1);
        gpDatosCliente.add(tfTelefono, 3, 2);
        gpDatosCliente.add(btnAgregarCliente, 4, 5);
        
        GridPane gpTipoBusquedaClientes = new GridPane();
        gpTipoBusquedaClientes.setPadding(new Insets(5,5,5,5));
        gpTipoBusquedaClientes.setVgap(15);
        gpTipoBusquedaClientes.setHgap(15);
        gpTipoBusquedaClientes.add(lbTipoBusquedaCli, 0, 0);
        gpTipoBusquedaClientes.add(rbTodosCli, 0, 1);
        gpTipoBusquedaClientes.add(rbidCli, 1, 1);
        gpTipoBusquedaClientes.add(rbNombreCli, 2, 1);
        gpTipoBusquedaClientes.add(tfBuscarIdCliente, 1, 2);
        gpTipoBusquedaClientes.add(tfBuscarNombreCliente, 2, 2);
        gpTipoBusquedaClientes.add(btnSeleccionarCliente, 6, 2);
        
        VBox vbDatosCliente = new VBox();
        vbDatosCliente.setSpacing(10);
        vbDatosCliente.getChildren().addAll(gpTipoBusquedaClientes, tvClientes, gpDatosCliente);
        
        GridPane gpDatosProducto = new GridPane();
        gpDatosProducto.setPadding(new Insets(5,5,5,5));
        gpDatosProducto.setVgap(25);
        gpDatosProducto.setHgap(25);
        gpDatosProducto.add(lbDatosProducto, 0, 0);
        gpDatosProducto.add(lbIdProducto, 0, 1);
        gpDatosProducto.add(cbIdProducto, 1, 1);
        gpDatosProducto.add(lbDescripcionProducto, 0, 2);
        gpDatosProducto.add(tfDescripcionProducto, 1, 2);
        gpDatosProducto.add(lbCantidad, 0, 3);
        gpDatosProducto.add(tfCantidadProducto, 1, 3);
        gpDatosProducto.add(lbCostoUnitario, 0, 4);
        //gpDatosProducto.add(, 1, 4);
        gpDatosProducto.add(lbCostoTotal, 0, 5);
        //gpDatosProducto.add(, 1, 5);

        TabPane tpPpal = new TabPane();
        tpPpal.setPrefWidth(800);
        
        Tab tTarjeta = new Tab();
        tTarjeta.setText("Tarjeta");
        Tab tCliente = new Tab();
        tCliente.setText("Cliente");
        Tab tProducto = new Tab();
        tProducto.setText("Producto");
        Tab tPagos = new Tab();
        tPagos.setText("Pagos");
        
        tTarjeta.setContent(gpDatosTarjeta);
        tCliente.setContent(vbDatosCliente);
        tProducto.setContent(gpDatosProducto);
        
        tpPpal.getTabs().addAll(tTarjeta, tCliente, tProducto, tPagos);
        tpPpal.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        
        VBox vbTablaTarjetas = new VBox();
        vbTablaTarjetas.setSpacing(10);
        vbTablaTarjetas.getChildren().addAll(lbTablaTarjetas, tvTarjetas, hbBotones);
        
        HBox hbMain = new HBox();
        hbMain.setSpacing(15);
        hbMain.getChildren().addAll(vbTablaTarjetas, tpPpal);
        hbMain.setAlignment(Pos.CENTER);
        vbModTarjeta.getChildren().addAll(gpBusqueda, hbMain);
        return vbModTarjeta;
    }
    private VBox vAsignarTarjeta(){
         Label lbTitulo = new Label("A S I G N A R  T A R J E T A");
         lbTitulo.getStyleClass().add("titulo-vista");
         lbTitulo.setAlignment(Pos.CENTER);
         
        //Seleccionar Busqueda
         ToggleGroup rgTipoBusquedas = new ToggleGroup();
         
         RadioButton rbBuscarTodos = new RadioButton("Todos");
         rbBuscarTodos.setSelected(true);
         rbBuscarTodos.setToggleGroup(rgTipoBusquedas);
         
         RadioButton rbBuscarCobrador = new RadioButton("Por Cobrador");
         rbBuscarCobrador.setToggleGroup(rgTipoBusquedas);
         
        // RadioButton rbBuscarDireccion = new RadioButton("Por Direccion");
        // rbBuscarDireccion.setToggleGroup(rgTipoBusquedas);

        // RadioButton rbBuscarTelefono = new RadioButton("Por Telefono");
        // rbBuscarTelefono.setToggleGroup(rgTipoBusquedas);
         
         Label lbBuscarPor = new Label("Buscar: ");
         
         lstWhere.clear();
         lstWhere.add("idCobrador is not null");
         ObservableList<String> lstCobradores = FXCollections.observableArrayList(cobraDAO.consultarNombreCobradores(lstWhere));
         ComboBox cbBuscarCobradores = new ComboBox(lstCobradores);
         cbBuscarCobradores.setPrefWidth(180);
         
        // TextField tfBuscarDireccion = new TextField();
        // TextField tfBuscarTelefono = new TextField();
         
         Button btnSeleccionar = new Button("Seleccionar");
                  
        //Datos Cobrador
        Label lbDatosCobrador = new Label("Datos del Cobrador");
        Label lbNombre = new Label("Nombre: ");
        Label lbDireccion = new Label("Direccion: ");
        Label lbTelefono = new Label("Telefono: ");
        Label lbFechaAsignacion = new Label("Fecha Asignacion: ");
        
        TextField tfNombre = new TextField();
        tfNombre.setPrefWidth(180);
        TextField tfDireccion = new TextField();
        tfDireccion.setPrefWidth(250);
        TextField tfTelefono = new TextField();
        tfTelefono.setPrefWidth(180);
        DatePicker dpFechaAsignacion = new DatePicker(LocalDate.now());
        
        //Datos Tarjetas Asignadas y sin asignar
        
        Label lbTarjetasAsignadas = new Label("Asignadas : ");
        Label lbTarjetasSinAsignar = new Label("Sin Asignar: ");

        TextField tfTarjetasAsignadas = new TextField();
        TextField tfTarjetasSinAsignar = new TextField();
        
        //Tabla Tarjetas sin Asignar
       
        Label lbTablaTarjetasSinAsignar = new Label("Tarjetas Sin Asignar:");
        TableView tvTarjetasSinAsignar = new TableView();
        tvTarjetasSinAsignar.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
         
        TableColumn folioTarjetaColum = new TableColumn("Folio ");
        folioTarjetaColum.setCellValueFactory(new PropertyValueFactory<>("folio"));
        folioTarjetaColum.setPrefWidth(40);
        
        TableColumn fechaTarjetaColumna = new TableColumn("Fecha");
        fechaTarjetaColumna.setCellValueFactory(new PropertyValueFactory<>("Fecha"));
        fechaTarjetaColumna.setPrefWidth(65);
        
        TableColumn precioColum = new TableColumn("Precio");
        precioColum.setCellValueFactory(new PropertyValueFactory<>("Precio"));
        precioColum.setPrefWidth(50);

        TableColumn saldoColum = new TableColumn("Saldo");
        saldoColum.setCellValueFactory(new PropertyValueFactory<>("saldo"));
        saldoColum.setPrefWidth(50);
        
        TableColumn engancheColum = new TableColumn("Enganche");
        engancheColum.setCellValueFactory(new PropertyValueFactory<>("Enganche"));
        engancheColum.setPrefWidth(70);

        TableColumn ClienteColumna = new TableColumn("Cliente");
        ClienteColumna.setCellValueFactory(new PropertyValueFactory<>("nomCliente"));
        ClienteColumna.setPrefWidth(150);

        TableColumn regionColumna = new TableColumn("Region");
        regionColumna.setCellValueFactory(new PropertyValueFactory<>("Region"));
        regionColumna.setPrefWidth(135);

        TableColumn pagosColum = new TableColumn("Pagos");
        pagosColum.setCellValueFactory(new PropertyValueFactory<>("Pagos"));
        pagosColum.setPrefWidth(50);
        
        tvTarjetasSinAsignar.getColumns().addAll(folioTarjetaColum, fechaTarjetaColumna, precioColum, engancheColum, saldoColum,  ClienteColumna, 
                regionColumna, pagosColum);
        
        MenuItem item = new MenuItem("Copiar Todo");
        item.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ObservableList<tarjetaAsignadas> posList = tvTarjetasSinAsignar.getItems();
                int old_r = -1;
                StringBuilder clipboardString = new StringBuilder();
                clipboardString.append("Id \t Folio \tidCliente \tPrecio \tEnganche \tId Vendedor \t Clasificacion \tTipo Pago \tRegion \tDia Cobro \tEnganche Pend. \tSaldo \tFecha \tPagos \n");
                //clipboardString.append("Id \t Folio \tidCliente \tPrecio \tEnganche \tId Vendedor \t Clasificacion \tTipo Pago \tRegion \tDia Cobro \tEnganche Pend. \tSaldo \tFecha \tPagos \n");
                for (tarjetaAsignadas p : posList) {

                    clipboardString.append(
                            p.getIdTarjeta() + " \t"
                            + p.getFolio() + " \t"
                            + p.getIdCliente() + " \t"
                            + p.getPrecio() + " \t"
                            + p.getEnganche() + " \t"
                            + p.getIdVendedor() + " \t"
                            + p.getClasificacion() + " \t"
                            + p.getTipoPago() + " \t"
                            + p.getRegion() + " \t"
                            + p.getDiaCobro() + " \t"
                            + p.getEnganchePend() + " \t"
                            + p.getSaldo() + " \t"
                            + p.getFecha() + " \t"
                            + p.getPagos() + " \n");
                }
                final ClipboardContent content = new ClipboardContent();
                content.putString(clipboardString.toString());
                Clipboard.getSystemClipboard().setContent(content);
            }
        });
            ContextMenu menu = new ContextMenu();
            menu.getItems().addAll(item);
            tvTarjetasSinAsignar.setContextMenu(menu);
            
        lstWhere.clear();
        lstWhere.add("t1.idTarjeta is not null");
        lstWhere.add("t2.idAsignacion is null");
        if(!tvTarjetasSinAsignar.getItems().isEmpty()) tvTarjetasSinAsignar.getItems().clear();
        tvTarjetasSinAsignar.setItems(FXCollections.observableArrayList(tarAsigDAO.consultarTarjetasSinAsignar(lstWhere)));
        tfTarjetasSinAsignar.setText(String.valueOf(tvTarjetasSinAsignar.getItems().size()));
        
        //tvTarjetasSinAsignar.setOnMouseClicked((event) -> {     });


        //Tabla Tarjetas Asignadas
       
        Label lbTablaTarjetasAsisgnadas = new Label("Tarjetas Asignadas:");
        TableView tvTarjetasAsignadas = new TableView();
        tvTarjetasAsignadas.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                 
        TableColumn folioTarjColum = new TableColumn("Folio");
        folioTarjColum.setCellValueFactory(new PropertyValueFactory<>("Folio"));
        folioTarjColum.setPrefWidth(80);
        
        TableColumn precioTarjColum = new TableColumn("Precio");
        precioTarjColum.setCellValueFactory(new PropertyValueFactory<>("Precio"));
        precioTarjColum.setPrefWidth(90);

        TableColumn saldoActualColum = new TableColumn("Saldo");
        saldoActualColum.setCellValueFactory(new PropertyValueFactory<>("saldo"));
        saldoActualColum.setPrefWidth(90);

        TableColumn nombreCobradorColumna = new TableColumn("Nombre Cobrador");
        nombreCobradorColumna.setCellValueFactory(new PropertyValueFactory<>("nombreCobrador"));
        nombreCobradorColumna.setPrefWidth(160);

        TableColumn FechaAsignadoColumna = new TableColumn("Fecha Asig.");
        FechaAsignadoColumna.setCellValueFactory(new PropertyValueFactory<>("Fecha"));
        FechaAsignadoColumna.setPrefWidth(125);

        TableColumn estadoRecibidoColum = new TableColumn("Edo. Recib.");
        estadoRecibidoColum.setCellValueFactory(new PropertyValueFactory<>("EstadoRecibido"));
        estadoRecibidoColum.setPrefWidth(80);
        
        TableColumn estadoEntregadoColum = new TableColumn("Edo. Ent.");
        estadoEntregadoColum.setCellValueFactory(new PropertyValueFactory<>("EstadoEntregado"));
        estadoEntregadoColum.setPrefWidth(80);

        tvTarjetasAsignadas.getColumns().addAll(folioTarjColum, precioColum, saldoActualColum, 
                nombreCobradorColumna, FechaAsignadoColumna, estadoEntregadoColum, estadoRecibidoColum );
        
        MenuItem itemsTarjetasAsignadas = new MenuItem("Copiar Todo");  //Pendientes modificar codigo
        itemsTarjetasAsignadas.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ObservableList<tarjetaAsignadas> posList = tvTarjetasAsignadas.getItems();
                int old_r = -1;
                StringBuilder clipboardString = new StringBuilder();
                clipboardString.append("idTarjeta \tFolio \tPrecio \tSaldo \tCobrador \tFecha Asignacion \tEdo. Ent. \tEdo. Rec. \n");
                //clipboardString.append("Folio \tidCliente\tPrecio \tSaldo \tEnganche \tId Vendedor \t Clasificacion \tTipo Pago \tRegion \tDia Cobro \tEnganche Pend. \tFecha \tPagos \n");
                for (tarjetaAsignadas p : posList) {

                       clipboardString.append(
                                p.getIdTarjeta()+" \t"
                               +p.getFolio()+" \t"
                               +p.getPrecio()+" \t"
                               +p.getSaldo()+" \t"
                               +p.getNombreCobrador()+" \t"
                               +p.getFechaAsignado()+" \t"
                               +p.getEstadoEntregado()+" \t"
                               +p.getEstadoRecibido()+" \n");
                }  
            final ClipboardContent content = new ClipboardContent();
            content.putString(clipboardString.toString());
            Clipboard.getSystemClipboard().setContent(content);
            }
            } );
            ContextMenu menuContextTarjetaAsignadas = new ContextMenu();
            menuContextTarjetaAsignadas.getItems().addAll(itemsTarjetasAsignadas);
            tvTarjetasAsignadas.setContextMenu(menuContextTarjetaAsignadas);
            
        lstWhere.clear();
        lstWhere.add("t1.idTarjeta is not null");
        if(!tvTarjetasAsignadas.getItems().isEmpty()) tvTarjetasAsignadas.getItems().clear();
        tvTarjetasAsignadas.setItems(FXCollections.observableArrayList(tarAsigDAO.consultarTarjetasAsignadas(lstWhere)));
        tfTarjetasAsignadas.setText(String.valueOf(tvTarjetasAsignadas.getItems().size()));
        
        tvTarjetasAsignadas.setOnMouseClicked((event) -> {
            //Pendiente Asignar codigo

        });
        
        btnSeleccionar.setOnAction((event) -> {
            if (rbBuscarTodos.isSelected()){
                
                    lstWhere.clear();
                    lstWhere.add("t1.idTarjeta is not null");
                    lstWhere.add("t2.idAsignacion is null");
                    if(!tvTarjetasSinAsignar.getItems().isEmpty()) tvTarjetasSinAsignar.getItems().clear();
                    tvTarjetasSinAsignar.setItems(FXCollections.observableArrayList(tarAsigDAO.consultarTarjetasSinAsignar(lstWhere)));
                    tfTarjetasSinAsignar.setText(String.valueOf(tvTarjetasSinAsignar.getItems().size()));                

                    //lstWhere.clear();
                    //lstWhere.add("idCobrador is not null");
                    //tvTarjetasAsignadas.setItems(FXCollections.observableArrayList(cobraDAO.consultarCobradores(lstWhere)));
                    
                    lstWhere.clear();
                    lstWhere.add("t1.idTarjeta is not null");
                    if(!tvTarjetasAsignadas.getItems().isEmpty()) tvTarjetasAsignadas.getItems().clear();
                    tvTarjetasAsignadas.setItems(FXCollections.observableArrayList(tarAsigDAO.consultarTarjetasAsignadas(lstWhere)));
                    tfTarjetasAsignadas.setText(String.valueOf(tvTarjetasAsignadas.getItems().size()));
            }
            if (rbBuscarCobrador.isSelected()){
                    lstWhere.clear();
                    lstWhere.add("Nombre like '%"+cbBuscarCobradores.getValue().toString()+"%' ");
                    cobraDTO= cobraDAO.consultarCobradores(lstWhere).get(0);
                    tfNombre.setText(cobraDTO.getNombre());
                    tfDireccion.setText(cobraDTO.getDireccion());
                    tfTelefono.setText(cobraDTO.getTelefono());
                    
                    lstWhere.clear();
                    lstWhere.add("idCobrador = "+cobraDTO.getIdCobrador());
                    if (!tvTarjetasAsignadas.getItems().isEmpty()) tvTarjetasAsignadas.getItems().clear();
                    tvTarjetasAsignadas.setItems(FXCollections.observableArrayList(tarAsigDAO.consultarTarjetasAsignadas(lstWhere)));
                    tfTarjetasAsignadas.setText(String.valueOf(tvTarjetasAsignadas.getItems().size()));
            }
            /*if (rbBuscarDireccion.isSelected()){
                    lstWhere.clear();
                    lstWhere.add("Direccion like '%"+tfBuscarDireccion.getText()+"%' ");
                    tvTarjetasAsignadas.setItems(FXCollections.observableArrayList(cobraDAO.consultarCobradores(lstWhere)));
            }
            if (rbBuscarTelefono.isSelected()){
                    lstWhere.clear();
                    lstWhere.add("Telefono like '%"+tfBuscarTelefono.getText()+"%' ");
                    tvTarjetasAsignadas.setItems(FXCollections.observableArrayList(cobraDAO.consultarCobradores(lstWhere)));
            }*/

        });
        
        Button btnAsignar = new Button("Asignar");
        btnAsignar.setPrefSize(120, 120);
        Button btnDesasignar = new Button("Desasignar");
        btnDesasignar.setPrefSize(120, 120);
        
        
        btnAsignar.setOnAction((event) -> {
            List<tarjetaAsignadas> lstTarjetasSelecTemp = new ArrayList<>();            
            lstTarjetasSelecTemp.addAll(tvTarjetasSinAsignar.getSelectionModel().getSelectedItems());
            for (tarjetaAsignadas t : lstTarjetasSelecTemp){
                tarAsigDAO.insertarTarjetaAsignadas(t.getIdTarjeta(), cobraDTO.getIdCobrador(), dpFechaAsignacion.getValue().toString(), t.getClasificacion(), "SR");
            }

            lstWhere.clear();
            lstWhere.add("idCobrador = "+cobraDTO.getIdCobrador());
            if (!tvTarjetasAsignadas.getItems().isEmpty()) tvTarjetasAsignadas.getItems().clear();
            tvTarjetasAsignadas.setItems(FXCollections.observableArrayList(tarAsigDAO.consultarTarjetasAsignadas(lstWhere)));
            tfTarjetasAsignadas.setText(String.valueOf(tvTarjetasAsignadas.getItems().size()));
            
            lstWhere.clear();
            lstWhere.add("t1.idTarjeta is not null");
            lstWhere.add("t2.idAsignacion is null");
            if (!tvTarjetasSinAsignar.getItems().isEmpty()) tvTarjetasSinAsignar.getItems().clear();
            tvTarjetasSinAsignar.setItems(FXCollections.observableArrayList(tarAsigDAO.consultarTarjetasSinAsignar(lstWhere)));
            tfTarjetasSinAsignar.setText(String.valueOf(tvTarjetasSinAsignar.getItems().size()));
        
        });
        
        btnDesasignar.setOnAction((event) -> {
        
        if(!lstTarjetasAsignadas.isEmpty()) lstTarjetasAsignadas.clear();
        lstTarjetasAsignadas.addAll(tvTarjetasAsignadas.getSelectionModel().getSelectedItems());
        for(tarjetaAsignadas t: lstTarjetasAsignadas){
              tarAsigDAO.eliminarAsignacion(t.getIdAsignado());
        }
            
        lstWhere.clear();
        lstWhere.add("idCobrador = "+cobraDTO.getIdCobrador());
        if (!tvTarjetasAsignadas.getItems().isEmpty()) tvTarjetasAsignadas.getItems().clear();
        tvTarjetasAsignadas.setItems(FXCollections.observableArrayList(tarAsigDAO.consultarTarjetasAsignadas(lstWhere)));
        tfTarjetasAsignadas.setText(String.valueOf(tvTarjetasAsignadas.getItems().size()));

        lstWhere.clear();
        lstWhere.add("t1.idTarjeta is not null");
        lstWhere.add("t2.idAsignacion is null");
        if (!tvTarjetasSinAsignar.getItems().isEmpty()) tvTarjetasSinAsignar.getItems().clear();
        tvTarjetasSinAsignar.setItems(FXCollections.observableArrayList(tarAsigDAO.consultarTarjetasSinAsignar(lstWhere)));
        tfTarjetasSinAsignar.setText(String.valueOf(tvTarjetasSinAsignar.getItems().size()));
            
        });
        
        VBox vbAsignacionTarjetas = new VBox();
        vbAsignacionTarjetas.setAlignment(Pos.TOP_LEFT);
        vbAsignacionTarjetas.getChildren().add(lbTitulo);
        vbAsignacionTarjetas.getStyleClass().add("vbox-vistas");
        
        GridPane gpSeleccionarCobrador = new GridPane();
        gpSeleccionarCobrador.setPadding(new Insets(5,5,5,5));
        gpSeleccionarCobrador.setVgap(5);
        gpSeleccionarCobrador.setHgap(5);
        gpSeleccionarCobrador.add(lbBuscarPor, 0, 0);
        gpSeleccionarCobrador.add(rbBuscarTodos, 0, 1);
        gpSeleccionarCobrador.add(rbBuscarCobrador, 1, 1);
        gpSeleccionarCobrador.add(cbBuscarCobradores, 1, 2);
        //gpSeleccionarCobrador.add(rbBuscarDireccion, 2, 1);
        //gpSeleccionarCobrador.add(tfBuscarDireccion, 2, 2);
        //gpSeleccionarCobrador.add(rbBuscarTelefono, 3, 1);
        //gpSeleccionarCobrador.add(tfBuscarTelefono, 3, 2);
        gpSeleccionarCobrador.add(btnSeleccionar, 4, 2);
        gpSeleccionarCobrador.add(lbTarjetasSinAsignar, 15, 1);
        gpSeleccionarCobrador.add(tfTarjetasSinAsignar, 15, 2);
        gpSeleccionarCobrador.add(lbTarjetasAsignadas, 16, 1);
        gpSeleccionarCobrador.add(tfTarjetasAsignadas, 16, 2);
        
        GridPane gpDatosCobrador = new GridPane();
        gpDatosCobrador.setPadding(new Insets(5,5,5,5));
        gpDatosCobrador.setVgap(25);
        gpDatosCobrador.setHgap(25);
        gpDatosCobrador.add(lbDatosCobrador, 0, 0);
        gpDatosCobrador.add(lbNombre, 0, 1);
        gpDatosCobrador.add(tfNombre, 1, 1);
        gpDatosCobrador.add(lbDireccion, 0, 2);
        gpDatosCobrador.add(tfDireccion, 1, 2);
        gpDatosCobrador.add(lbTelefono, 0, 3);
        gpDatosCobrador.add(tfTelefono, 1, 3);
        gpDatosCobrador.add(lbFechaAsignacion, 0, 4);
        gpDatosCobrador.add(dpFechaAsignacion, 1, 4);
        
        GridPane gpDatosControl = new GridPane();
        gpDatosControl.setPadding(new Insets(5,5,5,5));
        gpDatosControl.setVgap(10);
        gpDatosControl.setHgap(10);
        gpDatosControl.add(btnAsignar, 0, 0);
        gpDatosControl.add(btnDesasignar, 1, 0);
        
        HBox hbControlAsignacion = new HBox();
        hbControlAsignacion.setPadding(new Insets(5,5,5,5));
        hbControlAsignacion.setSpacing(25);
        hbControlAsignacion.getChildren().addAll(gpDatosCobrador,gpDatosControl);
        
        VBox vbTablaTarjetasSinAsignar = new VBox();
        vbTablaTarjetasSinAsignar.setPadding(new Insets(5,5,5,5));
        vbTablaTarjetasSinAsignar.setSpacing(5);
        vbTablaTarjetasSinAsignar.setPrefWidth(600);
        vbTablaTarjetasSinAsignar.setMaxWidth(800);
        vbTablaTarjetasSinAsignar.setMinWidth(300);
        vbTablaTarjetasSinAsignar.setAlignment(Pos.TOP_LEFT);
        vbTablaTarjetasSinAsignar.getChildren().addAll(lbTablaTarjetasSinAsignar, tvTarjetasSinAsignar);

        VBox vbTablaTarjetasAsignadas = new VBox();
        vbTablaTarjetasAsignadas.setPadding(new Insets(5,5,5,5));
        vbTablaTarjetasAsignadas.setSpacing(5);
        vbTablaTarjetasAsignadas.setPrefWidth(600);
        vbTablaTarjetasAsignadas.setMaxWidth(800);
        vbTablaTarjetasAsignadas.setMinWidth(300);
        vbTablaTarjetasAsignadas.setAlignment(Pos.TOP_LEFT);
        vbTablaTarjetasAsignadas.getChildren().addAll(lbTablaTarjetasAsisgnadas, tvTarjetasAsignadas);
        
        HBox hbTablas = new HBox();
        hbTablas.setPadding(new Insets(5,5,5,5));
        hbTablas.setSpacing(5);
        hbTablas.setPrefWidth(1200);
        hbTablas.setMaxWidth(1600);
        hbTablas.setMinWidth(600);
        hbTablas.setAlignment(Pos.TOP_LEFT);
        hbTablas.getChildren().addAll(vbTablaTarjetasSinAsignar, vbTablaTarjetasAsignadas);
        
        Button btnSalir = new Button("Salir");
        btnSalir.setOnAction((event) -> {
            removerVistas();
        });
        
        GridPane gpBotonesControl = new GridPane();
        gpBotonesControl.setPadding(new Insets(5,5,5,5));
        gpBotonesControl.setVgap(25);
        gpBotonesControl.setHgap(25);
        gpBotonesControl.add(btnSalir, 0, 0);
        
        vbAsignacionTarjetas.getChildren().addAll(gpSeleccionarCobrador, hbTablas, hbControlAsignacion, gpBotonesControl);
        
        return vbAsignacionTarjetas;
    }
    
    //Modulos de Clientes
    private VBox vRegistrarCliente(){
         Label lbTitulo = new Label("R E G I S T R A R  C L I E N T E");
         lbTitulo.getStyleClass().add("titulo-vista");
         lbTitulo.setAlignment(Pos.CENTER);
         
        //Datos Cliente
        Label lbDatosCliente = new Label("Datos del CLiente");
        Label lbNombre = new Label("Nombre: ");
        Label lbDireccion = new Label("Direccion: ");
        Label lbTelefono = new Label("Telefono: ");
        Label lbTipoCliente = new Label("Tipo Cliente: ");
        
        TextField tfNombre = new TextField();
        tfNombre.setPrefWidth(180);
        TextField tfDireccion = new TextField();
        tfDireccion.setPrefWidth(250);
        TextField tfTelefono = new TextField();
        tfTelefono.setPrefWidth(180);
        ObservableList<String> lstTipoCliente = FXCollections.observableArrayList("Nuevo","Activo", "Moroso", "Lista Negra");
        ComboBox cbTipoCliente = new ComboBox(lstTipoCliente);
                
        VBox vbDatosCliente = new VBox();
        vbDatosCliente.setAlignment(Pos.CENTER);
        vbDatosCliente.getChildren().add(lbTitulo);
        vbDatosCliente.getStyleClass().add("vbox-vistas");
        
        GridPane gpDatosCliente = new GridPane();
        gpDatosCliente.setPadding(new Insets(5,5,5,5));
        gpDatosCliente.setVgap(25);
        gpDatosCliente.setHgap(25);
        gpDatosCliente.add(lbDatosCliente, 0, 0);
        gpDatosCliente.add(lbNombre, 0, 1);
        gpDatosCliente.add(tfNombre, 1, 1);
        gpDatosCliente.add(lbDireccion, 0, 2);
        gpDatosCliente.add(tfDireccion, 1, 2);
        gpDatosCliente.add(lbTelefono, 0, 3);
        gpDatosCliente.add(tfTelefono, 1, 3);
        gpDatosCliente.add(lbTipoCliente, 0, 4);
        gpDatosCliente.add(cbTipoCliente, 1, 4);
        
        Button btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction((event) -> {
            int result = cliDAO.insertarCliente(tfNombre.getText(), tfDireccion.getText(), tfTelefono.getText(), cbTipoCliente.getValue().toString());
            if (result != -1) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setTitle("Confirmación");
                alert.setContentText("Cliente Registrado con el Id #"+result+" \n¿Deseas resgistrar otro?");
                Optional<ButtonType> action = alert.showAndWait();
                if (action.get() == ButtonType.OK) {
                    vbAreaTrabajo.getChildren().clear();
                    vbAreaTrabajo.getChildren().add(vRegistrarCliente());
                } else {
                    removerVistas();
                }
            }
        });
        
        Button btnSalir = new Button("Salir");
        btnSalir.setOnAction((event) -> {
            removerVistas();
        });
        
        GridPane gpBotonesControl = new GridPane();
        gpBotonesControl.setPadding(new Insets(5,5,5,5));
        gpBotonesControl.setVgap(25);
        gpBotonesControl.setHgap(25);
        gpBotonesControl.add(btnSalir, 0, 0);
        gpBotonesControl.add(btnGuardar, 1, 0);
        
        vbDatosCliente.getChildren().addAll(gpDatosCliente,gpBotonesControl);
        
        return vbDatosCliente;
    }
    private VBox vModificarCliente(){
         Label lbTitulo = new Label("M O D I F I C A R / C O N S U L T A R   C L I E N T E");
         lbTitulo.getStyleClass().add("titulo-vista");
         lbTitulo.setPrefWidth(anchoPantalla);
         lbTitulo.setAlignment(Pos.CENTER);
         
         //Tipo busqueda
         ToggleGroup rgTipoBusquedas = new ToggleGroup();
         
         RadioButton rbBuscarTodos = new RadioButton("Todos");
         rbBuscarTodos.setSelected(true);
         rbBuscarTodos.setToggleGroup(rgTipoBusquedas);
         
         RadioButton rbBuscarNombre = new RadioButton("Por Nombre");
         rbBuscarNombre.setToggleGroup(rgTipoBusquedas);
         
         RadioButton rbBuscarDireccion = new RadioButton("Por Direccion");
         rbBuscarDireccion.setToggleGroup(rgTipoBusquedas);

         RadioButton rbBuscarTelefono = new RadioButton("Por Telefono");
         rbBuscarTelefono.setToggleGroup(rgTipoBusquedas);

         RadioButton rbBuscarTipoCliente = new RadioButton("Por Tipo Cliente");
         rbBuscarTipoCliente.setToggleGroup(rgTipoBusquedas);
         
         Label lbBuscarPor = new Label("Buscar: ");
         
         TextField tfBuscarNombre = new TextField();
         TextField tfBuscarDireccion = new TextField();
         TextField tfBuscarTelefono = new TextField();
         ObservableList<String> lstBuscTipoCliente = FXCollections.observableArrayList("Nuevo","Activo", "Moroso", "Lista Negra");
         ComboBox cbBuscarTipoCliente = new ComboBox(lstBuscTipoCliente);
         
         Button btnSeleccionar = new Button("Seleccionar");
                  
        //Datos Cliente
        Label lbDatosCliente = new Label("Datos del CLiente");
        Label lbNombre = new Label("Nombre: ");
        Label lbDireccion = new Label("Direccion: ");
        Label lbTelefono = new Label("Telefono: ");
        Label lbTipoCliente = new Label("Tipo Cliente: ");
        
        TextField tfNombre = new TextField();
        tfNombre.setPrefWidth(180);
        TextField tfDireccion = new TextField();
        tfDireccion.setPrefWidth(250);
        TextField tfTelefono = new TextField();
        tfTelefono.setPrefWidth(180);
        ObservableList<String> lstTipoCliente = FXCollections.observableArrayList("Nuevo","Activo", "Moroso", "Lista Negra");
        ComboBox cbTipoCliente = new ComboBox(lstTipoCliente);
        
        //Tabla Clientes
       
        Label lbTablaTarjetas = new Label("Clientes Seleccionados:");
        TableView tvCliente = new TableView();
        tvCliente.setPrefWidth(1150);
         
        TableColumn idClienteColum = new TableColumn("ID");
        idClienteColum.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        idClienteColum.setPrefWidth(120);
        
        TableColumn nombreColumna = new TableColumn("Nombre");
        nombreColumna.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        nombreColumna.setPrefWidth(260);
        
        TableColumn direccionColum = new TableColumn("Direccion");
        direccionColum.setCellValueFactory(new PropertyValueFactory<>("Direccion"));
        direccionColum.setPrefWidth(250);

        TableColumn telefonoColum = new TableColumn("Telefono");
        telefonoColum.setCellValueFactory(new PropertyValueFactory<>("Telefono"));
        telefonoColum.setPrefWidth(250);
        
        TableColumn tipoClienteColum = new TableColumn("Tipo Cliente");
        tipoClienteColum.setCellValueFactory(new PropertyValueFactory<>("TipoCLiente"));
        tipoClienteColum.setPrefWidth(270);
        
        tvCliente.getColumns().addAll(idClienteColum, nombreColumna, direccionColum, telefonoColum, tipoClienteColum);
        
        lstWhere.clear();
        lstWhere.add("idCliente is not null");
        tvCliente.setItems(FXCollections.observableArrayList(cliDAO.consultarClientes(lstWhere)));
        
        tvCliente.setOnMouseClicked((event) -> {
            cliDTO = (cliente) tvCliente.getSelectionModel().getSelectedItem();
            tfNombre.setText(cliDTO.getNombre());
            tfDireccion.setText(cliDTO.getDireccion());
            tfTelefono.setText(cliDTO.getTelefono());
            cbTipoCliente.setValue(cliDTO.getTipoCLiente());
        });
        
        btnSeleccionar.setOnAction((event) -> {
            if (rbBuscarTodos.isSelected()){
                    lstWhere.clear();
                    lstWhere.add("idCliente is not null");
                    tvCliente.setItems(FXCollections.observableArrayList(cliDAO.consultarClientes(lstWhere)));
            }
            if (rbBuscarNombre.isSelected()){
                    lstWhere.clear();
                    lstWhere.add("Nombre like '%"+tfBuscarNombre.getText()+"%' ");
                    tvCliente.setItems(FXCollections.observableArrayList(cliDAO.consultarClientes(lstWhere)));
            }
            if (rbBuscarDireccion.isSelected()){
                    lstWhere.clear();
                    lstWhere.add("Direccion like '%"+tfBuscarDireccion.getText()+"%' ");
                    tvCliente.setItems(FXCollections.observableArrayList(cliDAO.consultarClientes(lstWhere)));
            }
            if (rbBuscarTelefono.isSelected()){
                    lstWhere.clear();
                    lstWhere.add("Telefono like '%"+tfBuscarTelefono.getText()+"%' ");
                    tvCliente.setItems(FXCollections.observableArrayList(cliDAO.consultarClientes(lstWhere)));
            }
            if (rbBuscarTipoCliente.isSelected()){
                    lstWhere.clear();
                    lstWhere.add("TipoCliente = '"+cbBuscarTipoCliente.getValue()+"' ");
                    tvCliente.setItems(FXCollections.observableArrayList(cliDAO.consultarClientes(lstWhere)));
            }
        });
        
        VBox vbDatosCliente = new VBox();
        vbDatosCliente.setAlignment(Pos.TOP_LEFT);
        vbDatosCliente.getChildren().add(lbTitulo);
        vbDatosCliente.getStyleClass().add("vbox-vistas");
        
        GridPane gpSeleccionarClientes = new GridPane();
        gpSeleccionarClientes.setPadding(new Insets(5,5,5,5));
        gpSeleccionarClientes.setVgap(5);
        gpSeleccionarClientes.setHgap(5);
        gpSeleccionarClientes.add(lbBuscarPor, 0, 0);
        gpSeleccionarClientes.add(rbBuscarTodos, 0, 1);
        gpSeleccionarClientes.add(rbBuscarNombre, 1, 1);
        gpSeleccionarClientes.add(tfBuscarNombre, 1, 2);
        gpSeleccionarClientes.add(rbBuscarDireccion, 2, 1);
        gpSeleccionarClientes.add(tfBuscarDireccion, 2, 2);
        gpSeleccionarClientes.add(rbBuscarTelefono, 3, 1);
        gpSeleccionarClientes.add(tfBuscarTelefono, 3, 2);
        gpSeleccionarClientes.add(rbBuscarTipoCliente, 4, 1);
        gpSeleccionarClientes.add(cbBuscarTipoCliente, 4, 2);
        gpSeleccionarClientes.add(btnSeleccionar, 5, 2);
        
        GridPane gpDatosCliente = new GridPane();
        gpDatosCliente.setPadding(new Insets(5,5,5,5));
        gpDatosCliente.setVgap(25);
        gpDatosCliente.setHgap(25);
        gpDatosCliente.add(lbDatosCliente, 0, 0);
        gpDatosCliente.add(lbNombre, 0, 1);
        gpDatosCliente.add(tfNombre, 1, 1);
        gpDatosCliente.add(lbDireccion, 0, 2);
        gpDatosCliente.add(tfDireccion, 1, 2);
        gpDatosCliente.add(lbTelefono, 0, 3);
        gpDatosCliente.add(tfTelefono, 1, 3);
        gpDatosCliente.add(lbTipoCliente, 0, 4);
        gpDatosCliente.add(cbTipoCliente, 1, 4);
        
        VBox vbTablaCliente = new VBox();
        vbTablaCliente.setPadding(new Insets(5,5,5,5));
        vbTablaCliente.setSpacing(5);
        vbTablaCliente.setPrefWidth(1150);
        vbTablaCliente.setMaxWidth(1150);
        vbTablaCliente.setMinWidth(1150);
        vbTablaCliente.setAlignment(Pos.TOP_LEFT);
        vbTablaCliente.getChildren().addAll(lbTablaTarjetas, tvCliente);
        
        Button btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction((event) -> {
            cliDAO.modificarCliente(cliDTO.getIdCliente(), tfNombre.getText(), tfDireccion.getText(), tfTelefono.getText(), cbTipoCliente.getValue().toString());
            int result = 1;
            if (result != -1) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setTitle("Confirmación");
                alert.setContentText("Cliente Con Datos Actualizados \n¿Deseas modificar otro?");
                Optional<ButtonType> action = alert.showAndWait();
                if (action.get() == ButtonType.OK) {
                    tfNombre.setText("");
                    tfDireccion.setText("");
                    tfTelefono.setText("");
                    cbTipoCliente.setValue(null);
                    vbAreaTrabajo.getChildren().clear();
                    vbAreaTrabajo.getChildren().add(vModificarCliente());
                } else {
                    removerVistas();
                }
            }
        });
        
        Button btnSalir = new Button("Salir");
        btnSalir.setOnAction((event) -> {
            removerVistas();
        });
        
        GridPane gpBotonesControl = new GridPane();
        gpBotonesControl.setPadding(new Insets(5,5,5,5));
        gpBotonesControl.setVgap(25);
        gpBotonesControl.setHgap(25);
        gpBotonesControl.add(btnSalir, 0, 0);
        gpBotonesControl.add(btnGuardar, 1, 0);
        
        vbDatosCliente.getChildren().addAll(gpSeleccionarClientes, vbTablaCliente, gpDatosCliente,gpBotonesControl);
        
        return vbDatosCliente;
    }
    private VBox vEliminarCliente(){
         Label lbTitulo = new Label("E L I M I N A R   C L I E N T E");
         lbTitulo.getStyleClass().add("titulo-vista");
         lbTitulo.setPrefWidth(anchoPantalla);
         lbTitulo.setAlignment(Pos.CENTER);
         
         //Tipo busqueda
         ToggleGroup rgTipoBusquedas = new ToggleGroup();
         
         RadioButton rbBuscarTodos = new RadioButton("Todos");
         rbBuscarTodos.setSelected(true);
         rbBuscarTodos.setToggleGroup(rgTipoBusquedas);
         
         RadioButton rbBuscarNombre = new RadioButton("Por Nombre");
         rbBuscarNombre.setToggleGroup(rgTipoBusquedas);
         
         RadioButton rbBuscarDireccion = new RadioButton("Por Direccion");
         rbBuscarDireccion.setToggleGroup(rgTipoBusquedas);

         RadioButton rbBuscarTelefono = new RadioButton("Por Telefono");
         rbBuscarTelefono.setToggleGroup(rgTipoBusquedas);

         RadioButton rbBuscarTipoCliente = new RadioButton("Por Tipo Cliente");
         rbBuscarTipoCliente.setToggleGroup(rgTipoBusquedas);
         
         Label lbBuscarPor = new Label("Buscar: ");
         
         TextField tfBuscarNombre = new TextField();
         TextField tfBuscarDireccion = new TextField();
         TextField tfBuscarTelefono = new TextField();
         ObservableList<String> lstBuscTipoCliente = FXCollections.observableArrayList("Nuevo","Activo", "Moroso", "Lista Negra");
         ComboBox cbBuscarTipoCliente = new ComboBox(lstBuscTipoCliente);
         
         Button btnSeleccionar = new Button("Seleccionar");
                  
        //Datos Cliente
        Label lbDatosCliente = new Label("Datos del CLiente");
        Label lbNombre = new Label("Nombre: ");
        Label lbDireccion = new Label("Direccion: ");
        Label lbTelefono = new Label("Telefono: ");
        Label lbTipoCliente = new Label("Tipo Cliente: ");
        
        TextField tfNombre = new TextField();
        tfNombre.setPrefWidth(180);
        TextField tfDireccion = new TextField();
        tfDireccion.setPrefWidth(250);
        TextField tfTelefono = new TextField();
        tfTelefono.setPrefWidth(180);
        ObservableList<String> lstTipoCliente = FXCollections.observableArrayList("Nuevo","Activo", "Moroso", "Lista Negra");
        ComboBox cbTipoCliente = new ComboBox(lstTipoCliente);
        
        //Tabla Clientes
       
        Label lbTablaTarjetas = new Label("Clientes Seleccionados:");
        TableView tvCliente = new TableView();
        tvCliente.setPrefWidth(1150);
         
        TableColumn idClienteColum = new TableColumn("ID");
        idClienteColum.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        idClienteColum.setPrefWidth(120);
        
        TableColumn nombreColumna = new TableColumn("Nombre");
        nombreColumna.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        nombreColumna.setPrefWidth(260);
        
        TableColumn direccionColum = new TableColumn("Direccion");
        direccionColum.setCellValueFactory(new PropertyValueFactory<>("Direccion"));
        direccionColum.setPrefWidth(250);

        TableColumn telefonoColum = new TableColumn("Telefono");
        telefonoColum.setCellValueFactory(new PropertyValueFactory<>("Telefono"));
        telefonoColum.setPrefWidth(250);
        
        TableColumn tipoClienteColum = new TableColumn("Tipo Cliente");
        tipoClienteColum.setCellValueFactory(new PropertyValueFactory<>("TipoCLiente"));
        tipoClienteColum.setPrefWidth(270);
        
        tvCliente.getColumns().addAll(idClienteColum, nombreColumna, direccionColum, telefonoColum, tipoClienteColum);
        
        lstWhere.clear();
        lstWhere.add("idCliente is not null");
        tvCliente.setItems(FXCollections.observableArrayList(cliDAO.consultarClientes(lstWhere)));
        
        tvCliente.setOnMouseClicked((event) -> {
            cliDTO = (cliente) tvCliente.getSelectionModel().getSelectedItem();
            tfNombre.setText(cliDTO.getNombre());
            tfDireccion.setText(cliDTO.getDireccion());
            tfTelefono.setText(cliDTO.getTelefono());
            cbTipoCliente.setValue(cliDTO.getTipoCLiente());
        });
        
        btnSeleccionar.setOnAction((event) -> {
            if (rbBuscarTodos.isSelected()){
                    lstWhere.clear();
                    lstWhere.add("idCliente is not null");
                    tvCliente.setItems(FXCollections.observableArrayList(cliDAO.consultarClientes(lstWhere)));
            }
            if (rbBuscarNombre.isSelected()){
                    lstWhere.clear();
                    lstWhere.add("Nombre like '%"+tfBuscarNombre.getText()+"%' ");
                    tvCliente.setItems(FXCollections.observableArrayList(cliDAO.consultarClientes(lstWhere)));
            }
            if (rbBuscarDireccion.isSelected()){
                    lstWhere.clear();
                    lstWhere.add("Direccion like '%"+tfBuscarDireccion.getText()+"%' ");
                    tvCliente.setItems(FXCollections.observableArrayList(cliDAO.consultarClientes(lstWhere)));
            }
            if (rbBuscarTelefono.isSelected()){
                    lstWhere.clear();
                    lstWhere.add("Telefono like '%"+tfBuscarTelefono.getText()+"%' ");
                    tvCliente.setItems(FXCollections.observableArrayList(cliDAO.consultarClientes(lstWhere)));
            }
            if (rbBuscarTipoCliente.isSelected()){
                    lstWhere.clear();
                    lstWhere.add("TipoCliente = '"+cbBuscarTipoCliente.getValue()+"' ");
                    tvCliente.setItems(FXCollections.observableArrayList(cliDAO.consultarClientes(lstWhere)));
            }
        });
        
        VBox vbDatosCliente = new VBox();
        vbDatosCliente.setAlignment(Pos.TOP_LEFT);
        vbDatosCliente.getChildren().add(lbTitulo);
        vbDatosCliente.getStyleClass().add("vbox-vistas");
        
        GridPane gpSeleccionarClientes = new GridPane();
        gpSeleccionarClientes.setPadding(new Insets(5,5,5,5));
        gpSeleccionarClientes.setVgap(5);
        gpSeleccionarClientes.setHgap(5);
        gpSeleccionarClientes.add(lbBuscarPor, 0, 0);
        gpSeleccionarClientes.add(rbBuscarTodos, 0, 1);
        gpSeleccionarClientes.add(rbBuscarNombre, 1, 1);
        gpSeleccionarClientes.add(tfBuscarNombre, 1, 2);
        gpSeleccionarClientes.add(rbBuscarDireccion, 2, 1);
        gpSeleccionarClientes.add(tfBuscarDireccion, 2, 2);
        gpSeleccionarClientes.add(rbBuscarTelefono, 3, 1);
        gpSeleccionarClientes.add(tfBuscarTelefono, 3, 2);
        gpSeleccionarClientes.add(rbBuscarTipoCliente, 4, 1);
        gpSeleccionarClientes.add(cbBuscarTipoCliente, 4, 2);
        gpSeleccionarClientes.add(btnSeleccionar, 5, 2);
        
        GridPane gpDatosCliente = new GridPane();
        gpDatosCliente.setPadding(new Insets(5,5,5,5));
        gpDatosCliente.setVgap(25);
        gpDatosCliente.setHgap(25);
        gpDatosCliente.add(lbDatosCliente, 0, 0);
        gpDatosCliente.add(lbNombre, 0, 1);
        gpDatosCliente.add(tfNombre, 1, 1);
        gpDatosCliente.add(lbDireccion, 0, 2);
        gpDatosCliente.add(tfDireccion, 1, 2);
        gpDatosCliente.add(lbTelefono, 0, 3);
        gpDatosCliente.add(tfTelefono, 1, 3);
        gpDatosCliente.add(lbTipoCliente, 0, 4);
        gpDatosCliente.add(cbTipoCliente, 1, 4);
        
        VBox vbTablaCliente = new VBox();
        vbTablaCliente.setPadding(new Insets(5,5,5,5));
        vbTablaCliente.setSpacing(5);
        vbTablaCliente.setPrefWidth(1150);
        vbTablaCliente.setMaxWidth(1150);
        vbTablaCliente.setMinWidth(1150);
        vbTablaCliente.setAlignment(Pos.TOP_LEFT);
        vbTablaCliente.getChildren().addAll(lbTablaTarjetas, tvCliente);
        
        Button btnGuardar = new Button("Eliminar");
        btnGuardar.setOnAction((event) -> {
            cliDAO.eliminarCliente(cliDTO.getIdCliente());
            int result = 1;
            if (result != -1) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setTitle("Confirmación");
                alert.setContentText("Cliente Eliminado \n¿Deseas eliminar otro?");
                Optional<ButtonType> action = alert.showAndWait();
                if (action.get() == ButtonType.OK) {
                    tfNombre.setText("");
                    tfDireccion.setText("");
                    tfTelefono.setText("");
                    cbTipoCliente.setValue(null);
                    vbAreaTrabajo.getChildren().clear();
                    vbAreaTrabajo.getChildren().add(vEliminarCliente());
                } else {
                    removerVistas();
                }
            }
        });
        
        Button btnSalir = new Button("Salir");
        btnSalir.setOnAction((event) -> {
            removerVistas();
        });
        
        GridPane gpBotonesControl = new GridPane();
        gpBotonesControl.setPadding(new Insets(5,5,5,5));
        gpBotonesControl.setVgap(25);
        gpBotonesControl.setHgap(25);
        gpBotonesControl.add(btnSalir, 0, 0);
        gpBotonesControl.add(btnGuardar, 1, 0);
        
        vbDatosCliente.getChildren().addAll(gpSeleccionarClientes, vbTablaCliente, gpDatosCliente,gpBotonesControl);
        
        return vbDatosCliente;
    }   
    private VBox vListaNegraCliente(){
         Label lbTitulo = new Label("L I S T A  N E G R A  C L I E N T E");
         lbTitulo.getStyleClass().add("titulo-vista");
         lbTitulo.setPrefWidth(anchoPantalla);
         lbTitulo.setAlignment(Pos.CENTER);
         
         //Tipo busqueda
         ToggleGroup rgTipoBusquedas = new ToggleGroup();
         
         RadioButton rbBuscarTodos = new RadioButton("Todos");
         rbBuscarTodos.setSelected(true);
         rbBuscarTodos.setToggleGroup(rgTipoBusquedas);
         
         RadioButton rbBuscarNombre = new RadioButton("Por Nombre");
         rbBuscarNombre.setToggleGroup(rgTipoBusquedas);
         
         RadioButton rbBuscarDireccion = new RadioButton("Por Direccion");
         rbBuscarDireccion.setToggleGroup(rgTipoBusquedas);

         RadioButton rbBuscarTelefono = new RadioButton("Por Telefono");
         rbBuscarTelefono.setToggleGroup(rgTipoBusquedas);

         RadioButton rbBuscarTipoCliente = new RadioButton("Por Tipo Cliente");
         rbBuscarTipoCliente.setToggleGroup(rgTipoBusquedas);
         
         Label lbBuscarPor = new Label("Buscar: ");
         
         TextField tfBuscarNombre = new TextField();
         TextField tfBuscarDireccion = new TextField();
         TextField tfBuscarTelefono = new TextField();
         ObservableList<String> lstBuscTipoCliente = FXCollections.observableArrayList("Nuevo","Activo", "Moroso", "Lista Negra");
         ComboBox cbBuscarTipoCliente = new ComboBox(lstBuscTipoCliente);
         
         Button btnSeleccionar = new Button("Seleccionar");
                  
        //Datos Cliente
        Label lbDatosCliente = new Label("Datos del CLiente");
        Label lbNombre = new Label("Nombre: ");
        Label lbDireccion = new Label("Direccion: ");
        Label lbTelefono = new Label("Telefono: ");
        Label lbTipoCliente = new Label("Tipo Cliente: ");
        
        TextField tfNombre = new TextField();
        tfNombre.setPrefWidth(180);
        TextField tfDireccion = new TextField();
        tfDireccion.setPrefWidth(250);
        TextField tfTelefono = new TextField();
        tfTelefono.setPrefWidth(180);
        ObservableList<String> lstTipoCliente = FXCollections.observableArrayList("Nuevo","Activo", "Moroso", "Lista Negra");
        ComboBox cbTipoCliente = new ComboBox(lstTipoCliente);
        
        //Tabla Clientes
       
        Label lbTabClientes = new Label("Clientes Seleccionados:");
        TableView tvCliente = new TableView();
        tvCliente.setPrefWidth(600);
         
        TableColumn idClienteColum = new TableColumn("ID");
        idClienteColum.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        idClienteColum.setPrefWidth(80);
        
        TableColumn nombreColumna = new TableColumn("Nombre");
        nombreColumna.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        nombreColumna.setPrefWidth(260);
        
        TableColumn direccionColum = new TableColumn("Direccion");
        direccionColum.setCellValueFactory(new PropertyValueFactory<>("Direccion"));
        direccionColum.setPrefWidth(250);

        TableColumn telefonoColum = new TableColumn("Telefono");
        telefonoColum.setCellValueFactory(new PropertyValueFactory<>("Telefono"));
        telefonoColum.setPrefWidth(110);
        
        TableColumn tipoClienteColum = new TableColumn("Tipo Cliente");
        tipoClienteColum.setCellValueFactory(new PropertyValueFactory<>("TipoCLiente"));
        tipoClienteColum.setPrefWidth(270);
        
        tvCliente.getColumns().addAll(idClienteColum, nombreColumna, direccionColum, telefonoColum, tipoClienteColum);
        
        lstWhere.clear();
        lstWhere.add("idCliente is not null");
        tvCliente.setItems(FXCollections.observableArrayList(cliDAO.consultarClientes(lstWhere)));
        
        //Tabla Clientes Lista Negra
       
        Label lbTablaClientesListaNegra = new Label("Clientes en  Lista Negra:");
        TableView tvClienteLN = new TableView();
        tvClienteLN.setPrefWidth(600);
         
        TableColumn idClienteLNColum = new TableColumn("ID");
        idClienteLNColum.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        idClienteLNColum.setPrefWidth(80);
        
        TableColumn nombreLNColumna = new TableColumn("Nombre");
        nombreLNColumna.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        nombreLNColumna.setPrefWidth(260);
        
        TableColumn direccionLNColum = new TableColumn("Direccion");
        direccionLNColum.setCellValueFactory(new PropertyValueFactory<>("Direccion"));
        direccionLNColum.setPrefWidth(250);

        TableColumn telefonoLNColum = new TableColumn("Telefono");
        telefonoLNColum.setCellValueFactory(new PropertyValueFactory<>("Telefono"));
        telefonoLNColum.setPrefWidth(110);
        
        TableColumn tipoClienteLNColum = new TableColumn("Tipo Cliente");
        tipoClienteLNColum.setCellValueFactory(new PropertyValueFactory<>("TipoCLiente"));
        tipoClienteLNColum.setPrefWidth(270);
        
        tvClienteLN.getColumns().addAll(idClienteLNColum, nombreLNColumna, direccionLNColum, telefonoLNColum, tipoClienteLNColum);
        
        lstWhere.clear();
        lstWhere.add("TipoCliente = 'Lista Negra'");
        tvClienteLN.setItems(FXCollections.observableArrayList(cliDAO.consultarClientes(lstWhere)));        

        tvCliente.setOnMouseClicked((event) -> {
            cliDTO = (cliente) tvCliente.getSelectionModel().getSelectedItem();
            tfNombre.setText(cliDTO.getNombre());
            tfDireccion.setText(cliDTO.getDireccion());
            tfTelefono.setText(cliDTO.getTelefono());
            cbTipoCliente.setValue(cliDTO.getTipoCLiente());
        });
        
        btnSeleccionar.setOnAction((event) -> {
            if (rbBuscarTodos.isSelected()){
                    lstWhere.clear();
                    lstWhere.add("idCliente is not null");
                    tvCliente.setItems(FXCollections.observableArrayList(cliDAO.consultarClientes(lstWhere)));
            }
            if (rbBuscarNombre.isSelected()){
                    lstWhere.clear();
                    lstWhere.add("Nombre like '%"+tfBuscarNombre.getText()+"%' ");
                    tvCliente.setItems(FXCollections.observableArrayList(cliDAO.consultarClientes(lstWhere)));
            }
            if (rbBuscarDireccion.isSelected()){
                    lstWhere.clear();
                    lstWhere.add("Direccion like '%"+tfBuscarDireccion.getText()+"%' ");
                    tvCliente.setItems(FXCollections.observableArrayList(cliDAO.consultarClientes(lstWhere)));
            }
            if (rbBuscarTelefono.isSelected()){
                    lstWhere.clear();
                    lstWhere.add("Telefono like '%"+tfBuscarTelefono.getText()+"%' ");
                    tvCliente.setItems(FXCollections.observableArrayList(cliDAO.consultarClientes(lstWhere)));
            }
            if (rbBuscarTipoCliente.isSelected()){
                    lstWhere.clear();
                    lstWhere.add("TipoCliente = '"+cbBuscarTipoCliente.getValue()+"' ");
                    tvCliente.setItems(FXCollections.observableArrayList(cliDAO.consultarClientes(lstWhere)));
            }
        });
        
        VBox vbDatosCliente = new VBox();
        vbDatosCliente.setAlignment(Pos.TOP_LEFT);
        vbDatosCliente.getChildren().add(lbTitulo);
        vbDatosCliente.getStyleClass().add("vbox-vistas");
        
        GridPane gpSeleccionarClientes = new GridPane();
        gpSeleccionarClientes.setPadding(new Insets(5,5,5,5));
        gpSeleccionarClientes.setVgap(5);
        gpSeleccionarClientes.setHgap(5);
        gpSeleccionarClientes.add(lbBuscarPor, 0, 0);
        gpSeleccionarClientes.add(rbBuscarTodos, 0, 1);
        gpSeleccionarClientes.add(rbBuscarNombre, 1, 1);
        gpSeleccionarClientes.add(tfBuscarNombre, 1, 2);
        gpSeleccionarClientes.add(rbBuscarDireccion, 2, 1);
        gpSeleccionarClientes.add(tfBuscarDireccion, 2, 2);
        gpSeleccionarClientes.add(rbBuscarTelefono, 3, 1);
        gpSeleccionarClientes.add(tfBuscarTelefono, 3, 2);
        gpSeleccionarClientes.add(rbBuscarTipoCliente, 4, 1);
        gpSeleccionarClientes.add(cbBuscarTipoCliente, 4, 2);
        gpSeleccionarClientes.add(btnSeleccionar, 5, 2);
        
        GridPane gpDatosCliente = new GridPane();
        gpDatosCliente.setPadding(new Insets(5,5,5,5));
        gpDatosCliente.setVgap(25);
        gpDatosCliente.setHgap(25);
        gpDatosCliente.add(lbDatosCliente, 0, 0);
        gpDatosCliente.add(lbNombre, 0, 1);
        gpDatosCliente.add(tfNombre, 1, 1);
        gpDatosCliente.add(lbDireccion, 0, 2);
        gpDatosCliente.add(tfDireccion, 1, 2);
        gpDatosCliente.add(lbTelefono, 0, 3);
        gpDatosCliente.add(tfTelefono, 1, 3);
        gpDatosCliente.add(lbTipoCliente, 0, 4);
        gpDatosCliente.add(cbTipoCliente, 1, 4);
        
        VBox vbTablaCliente = new VBox();
        vbTablaCliente.setPadding(new Insets(5,5,5,5));
        vbTablaCliente.setSpacing(5);
        vbTablaCliente.setPrefWidth(600);
        vbTablaCliente.setMaxWidth(600);
        vbTablaCliente.setMinWidth(600);
        vbTablaCliente.setAlignment(Pos.TOP_LEFT);
        vbTablaCliente.getChildren().addAll(lbTabClientes, tvCliente);
        
        VBox vbTablaClienteLN = new VBox();
        vbTablaClienteLN.setPadding(new Insets(5,5,5,5));
        vbTablaClienteLN.setSpacing(5);
        vbTablaClienteLN.setPrefWidth(600);
        vbTablaClienteLN.setMaxWidth(600);
        vbTablaClienteLN.setMinWidth(600);
        vbTablaClienteLN.setAlignment(Pos.TOP_LEFT);
        vbTablaClienteLN.getChildren().addAll(lbTablaClientesListaNegra, tvClienteLN);
        
        HBox hbTablas = new HBox();
        hbTablas.setPadding(new Insets(5,5,5,5));
        hbTablas.setSpacing(5);
        hbTablas.setPrefWidth(1300);
        hbTablas.setMaxWidth(1300);
        hbTablas.setMinWidth(1300);
        hbTablas.setAlignment(Pos.TOP_LEFT);
        hbTablas.getChildren().addAll(vbTablaCliente, vbTablaClienteLN);
        
        Button btnGuardar = new Button("Lista Negra");
        btnGuardar.setOnAction((event) -> {
            cliDAO.modificarCliente(cliDTO.getIdCliente(), tfNombre.getText(), tfDireccion.getText(), tfTelefono.getText(), "Lista Negra");
            int result = 1;
            if (result != -1) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setTitle("Confirmación");
                alert.setContentText("Cliente en Lista Negra \n¿Deseas cambiar Otro?");
                Optional<ButtonType> action = alert.showAndWait();
                if (action.get() == ButtonType.OK) {
                    tfNombre.setText("");
                    tfDireccion.setText("");
                    tfTelefono.setText("");
                    cbTipoCliente.setValue(null);
                    vbAreaTrabajo.getChildren().clear();
                    vbAreaTrabajo.getChildren().add(vListaNegraCliente());
                } else {
                    removerVistas();
                }
            }
        });
        
        Button btnSalir = new Button("Salir");
        btnSalir.setOnAction((event) -> {
            removerVistas();
        });
        
        GridPane gpBotonesControl = new GridPane();
        gpBotonesControl.setPadding(new Insets(5,5,5,5));
        gpBotonesControl.setVgap(25);
        gpBotonesControl.setHgap(25);
        gpBotonesControl.add(btnSalir, 0, 0);
        gpBotonesControl.add(btnGuardar, 1, 0);
        
        vbDatosCliente.getChildren().addAll(gpSeleccionarClientes, hbTablas, gpDatosCliente,gpBotonesControl);
        
        return vbDatosCliente;
    } 
    
    //Modulos de Vendedor
    private VBox vRegistrarVendedor() {
        Label lbTitulo = new Label("R E G I S T R A R  V E N D E D O R");
        lbTitulo.getStyleClass().add("titulo-vista");
        lbTitulo.setAlignment(Pos.CENTER);

        //Datos Vendedor
        Label lbDatosVendedor = new Label("Datos del Vendedor");
        Label lbNombre = new Label("Nombre: ");
        Label lbDireccion = new Label("Direccion: ");
        Label lbTelefono = new Label("Telefono: ");

        TextField tfNombre = new TextField();
        tfNombre.setPrefWidth(180);
        TextField tfDireccion = new TextField();
        tfDireccion.setPrefWidth(250);
        TextField tfTelefono = new TextField();
        tfTelefono.setPrefWidth(180);

        VBox vbDatosVendedor = new VBox();
        vbDatosVendedor.setAlignment(Pos.CENTER);
        vbDatosVendedor.getChildren().add(lbTitulo);
        vbDatosVendedor.getStyleClass().add("vbox-vistas");

        GridPane gpDatosVendedor = new GridPane();
        gpDatosVendedor.setPadding(new Insets(5, 5, 5, 5));
        gpDatosVendedor.setVgap(25);
        gpDatosVendedor.setHgap(25);
        gpDatosVendedor.add(lbDatosVendedor, 0, 0);
        gpDatosVendedor.add(lbNombre, 0, 1);
        gpDatosVendedor.add(tfNombre, 1, 1);
        gpDatosVendedor.add(lbDireccion, 0, 2);
        gpDatosVendedor.add(tfDireccion, 1, 2);
        gpDatosVendedor.add(lbTelefono, 0, 3);
        gpDatosVendedor.add(tfTelefono, 1, 3);

        Button btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction((event) -> {
            int result = venDAO.insertarVendedor(tfNombre.getText(), tfDireccion.getText(), tfTelefono.getText());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Confirmación");
            alert.setContentText("Vendedor Registrado con el ID #"+result+"\n¿Deseas agregar otro?");
            Optional<ButtonType> action = alert.showAndWait();
            if(action.get()== ButtonType.OK){
                vbAreaTrabajo.getChildren().clear();
                vbAreaTrabajo.getChildren().add(vRegistrarVendedor());
            }
            else{
                removerVistas();
            }
        });
        
        Button btnSalir = new Button("Salir");
        btnSalir.setOnAction((event) -> {
            removerVistas();
        });

        GridPane gpBotonesControl = new GridPane();
        gpBotonesControl.setPadding(new Insets(5, 5, 5, 5));
        gpBotonesControl.setVgap(25);
        gpBotonesControl.setHgap(25);
        gpBotonesControl.add(btnSalir, 0, 0);
        gpBotonesControl.add(btnGuardar, 1, 0);

        vbDatosVendedor.getChildren().addAll(gpDatosVendedor, gpBotonesControl);

        return vbDatosVendedor;
    }
    private VBox vModificarVendedor(){
         Label lbTitulo = new Label("M O D I F I C A R  V E N D E D O R");
         lbTitulo.getStyleClass().add("titulo-vista");
         lbTitulo.setPrefWidth(anchoPantalla);
         lbTitulo.setAlignment(Pos.CENTER);
         
         //Tipo busqueda
         ToggleGroup rgTipoBusquedas = new ToggleGroup();
         
         RadioButton rbBuscarTodos = new RadioButton("Todos");
         rbBuscarTodos.setSelected(true);
         rbBuscarTodos.setToggleGroup(rgTipoBusquedas);
         
         RadioButton rbBuscarNombre = new RadioButton("Por Nombre");
         rbBuscarNombre.setToggleGroup(rgTipoBusquedas);
         
         RadioButton rbBuscarDireccion = new RadioButton("Por Direccion");
         rbBuscarDireccion.setToggleGroup(rgTipoBusquedas);

         RadioButton rbBuscarTelefono = new RadioButton("Por Telefono");
         rbBuscarTelefono.setToggleGroup(rgTipoBusquedas);
         
         Label lbBuscarPor = new Label("Buscar: ");
         
         TextField tfBuscarNombre = new TextField();
         TextField tfBuscarDireccion = new TextField();
         TextField tfBuscarTelefono = new TextField();
         
         Button btnSeleccionar = new Button("Seleccionar");
                  
        //Datos Vendedor
        Label lbDatosCliente = new Label("Datos del Vendor");
        Label lbNombre = new Label("Nombre: ");
        Label lbDireccion = new Label("Direccion: ");
        Label lbTelefono = new Label("Telefono: ");
        
        TextField tfNombre = new TextField();
        tfNombre.setPrefWidth(180);
        TextField tfDireccion = new TextField();
        tfDireccion.setPrefWidth(250);
        TextField tfTelefono = new TextField();
        tfTelefono.setPrefWidth(180);
        
        //Tabla Vendedor
       
        Label lbTablaVendedores = new Label("Vendedores Seleccionados:");
        TableView tvVendedores = new TableView();
        tvVendedores.setPrefWidth(1150);
         
        TableColumn idVendedorColum = new TableColumn("ID");
        idVendedorColum.setCellValueFactory(new PropertyValueFactory<>("idVendedor"));
        idVendedorColum.setPrefWidth(120);
        
        TableColumn nombreColumna = new TableColumn("Nombre");
        nombreColumna.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        nombreColumna.setPrefWidth(260);
        
        TableColumn direccionColum = new TableColumn("Direccion");
        direccionColum.setCellValueFactory(new PropertyValueFactory<>("Direccion"));
        direccionColum.setPrefWidth(250);

        TableColumn telefonoColum = new TableColumn("Telefono");
        telefonoColum.setCellValueFactory(new PropertyValueFactory<>("Telefono"));
        telefonoColum.setPrefWidth(250);
                
        tvVendedores.getColumns().addAll(idVendedorColum, nombreColumna, direccionColum, telefonoColum);
        
        lstWhere.clear();
        lstWhere.add("idVendedor is not null");
        tvVendedores.setItems(FXCollections.observableArrayList(venDAO.consultarVendedor(lstWhere)));
        
        tvVendedores.setOnMouseClicked((event) -> {
            venDTO = (vendedor) tvVendedores.getSelectionModel().getSelectedItem();
            tfNombre.setText(venDTO.getNombre());
            tfDireccion.setText(venDTO.getDireccion());
            tfTelefono.setText(venDTO.getTelefono());
        });
        
        btnSeleccionar.setOnAction((event) -> {
            if (rbBuscarTodos.isSelected()){
                    lstWhere.clear();
                    lstWhere.add("idVendedor is not null");
                    tvVendedores.setItems(FXCollections.observableArrayList(venDAO.consultarVendedor(lstWhere)));
            }
            if (rbBuscarNombre.isSelected()){
                    lstWhere.clear();
                    lstWhere.add("Nombre like '%"+tfBuscarNombre.getText()+"%' ");
                    tvVendedores.setItems(FXCollections.observableArrayList(venDAO.consultarVendedor(lstWhere)));
            }
            if (rbBuscarDireccion.isSelected()){
                    lstWhere.clear();
                    lstWhere.add("Direccion like '%"+tfBuscarDireccion.getText()+"%' ");
                    tvVendedores.setItems(FXCollections.observableArrayList(venDAO.consultarVendedor(lstWhere)));
            }
            if (rbBuscarTelefono.isSelected()){
                    lstWhere.clear();
                    lstWhere.add("Telefono like '%"+tfBuscarTelefono.getText()+"%' ");
                    tvVendedores.setItems(FXCollections.observableArrayList(venDAO.consultarVendedor(lstWhere)));
            }

        });
        
        VBox vbDatosVendedor = new VBox();
        vbDatosVendedor.setAlignment(Pos.TOP_LEFT);
        vbDatosVendedor.getChildren().add(lbTitulo);
        vbDatosVendedor.getStyleClass().add("vbox-vistas");
        
        GridPane gpSeleccionarVendedor = new GridPane();
        gpSeleccionarVendedor.setPadding(new Insets(5,5,5,5));
        gpSeleccionarVendedor.setVgap(5);
        gpSeleccionarVendedor.setHgap(5);
        gpSeleccionarVendedor.add(lbBuscarPor, 0, 0);
        gpSeleccionarVendedor.add(rbBuscarTodos, 0, 1);
        gpSeleccionarVendedor.add(rbBuscarNombre, 1, 1);
        gpSeleccionarVendedor.add(tfBuscarNombre, 1, 2);
        gpSeleccionarVendedor.add(rbBuscarDireccion, 2, 1);
        gpSeleccionarVendedor.add(tfBuscarDireccion, 2, 2);
        gpSeleccionarVendedor.add(rbBuscarTelefono, 3, 1);
        gpSeleccionarVendedor.add(tfBuscarTelefono, 3, 2);
        gpSeleccionarVendedor.add(btnSeleccionar, 4, 2);
        
        GridPane gpDatosVendedor = new GridPane();
        gpDatosVendedor.setPadding(new Insets(5,5,5,5));
        gpDatosVendedor.setVgap(25);
        gpDatosVendedor.setHgap(25);
        gpDatosVendedor.add(lbDatosCliente, 0, 0);
        gpDatosVendedor.add(lbNombre, 0, 1);
        gpDatosVendedor.add(tfNombre, 1, 1);
        gpDatosVendedor.add(lbDireccion, 0, 2);
        gpDatosVendedor.add(tfDireccion, 1, 2);
        gpDatosVendedor.add(lbTelefono, 0, 3);
        gpDatosVendedor.add(tfTelefono, 1, 3);
        
        VBox vbTablaVendedor = new VBox();
        vbTablaVendedor.setPadding(new Insets(5,5,5,5));
        vbTablaVendedor.setSpacing(5);
        vbTablaVendedor.setPrefWidth(1150);
        vbTablaVendedor.setMaxWidth(1150);
        vbTablaVendedor.setMinWidth(1150);
        vbTablaVendedor.setAlignment(Pos.TOP_LEFT);
        vbTablaVendedor.getChildren().addAll(lbTablaVendedores, tvVendedores);
        
        Button btnModificar = new Button("Modificar");
        btnModificar.setOnAction((event) -> {
            venDAO.modificarVendedor(venDTO.getIdVendedor(), tfNombre.getText(), tfDireccion.getText(), tfTelefono.getText());
            int result = 1;
            if (result != -1) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setTitle("Confirmación");
                alert.setContentText("Datos Actualizado del vendedor \n¿Deseas modificar otro?");
                Optional<ButtonType> action = alert.showAndWait();
                if (action.get() == ButtonType.OK) {
                    tfNombre.setText("");
                    tfDireccion.setText("");
                    tfTelefono.setText("");
                    vbAreaTrabajo.getChildren().clear();
                    vbAreaTrabajo.getChildren().add(vModificarVendedor());
                } else {
                    removerVistas();
                }
            }
        });
        
        Button btnSalir = new Button("Salir");
        btnSalir.setOnAction((event) -> {
            removerVistas();
        });
        
        GridPane gpBotonesControl = new GridPane();
        gpBotonesControl.setPadding(new Insets(5,5,5,5));
        gpBotonesControl.setVgap(25);
        gpBotonesControl.setHgap(25);
        gpBotonesControl.add(btnSalir, 0, 0);
        gpBotonesControl.add(btnModificar, 1, 0);
        
        vbDatosVendedor.getChildren().addAll(gpSeleccionarVendedor, vbTablaVendedor, gpDatosVendedor, gpBotonesControl);
        
        return vbDatosVendedor;
    }
    private VBox vEliminarVendedor(){
         Label lbTitulo = new Label("E L I M I N A R   V E N D E D O R");
         lbTitulo.getStyleClass().add("titulo-vista");
         lbTitulo.setPrefWidth(anchoPantalla);
         lbTitulo.setAlignment(Pos.CENTER);
         
         //Tipo busqueda
         ToggleGroup rgTipoBusquedas = new ToggleGroup();
         
         RadioButton rbBuscarTodos = new RadioButton("Todos");
         rbBuscarTodos.setSelected(true);
         rbBuscarTodos.setToggleGroup(rgTipoBusquedas);
         
         RadioButton rbBuscarNombre = new RadioButton("Por Nombre");
         rbBuscarNombre.setToggleGroup(rgTipoBusquedas);
         
         RadioButton rbBuscarDireccion = new RadioButton("Por Direccion");
         rbBuscarDireccion.setToggleGroup(rgTipoBusquedas);

         RadioButton rbBuscarTelefono = new RadioButton("Por Telefono");
         rbBuscarTelefono.setToggleGroup(rgTipoBusquedas);

         Label lbBuscarPor = new Label("Buscar: ");
         
         TextField tfBuscarNombre = new TextField();
         TextField tfBuscarDireccion = new TextField();
         TextField tfBuscarTelefono = new TextField();
         
         Button btnSeleccionar = new Button("Seleccionar");
                  
        //Datos Vendedor
        Label lbDatosVendedor = new Label("Datos del Vendedor");
        Label lbNombre = new Label("Nombre: ");
        Label lbDireccion = new Label("Direccion: ");
        Label lbTelefono = new Label("Telefono: ");
        Label lbTipoCliente = new Label("Tipo Cliente: ");
        
        TextField tfNombre = new TextField();
        tfNombre.setPrefWidth(180);
        TextField tfDireccion = new TextField();
        tfDireccion.setPrefWidth(250);
        TextField tfTelefono = new TextField();
        tfTelefono.setPrefWidth(180);
        
        //Tabla Vendedor
       
        Label lbTablaVendedores = new Label("Vendedores Seleccionados:");
        TableView tvVendedores = new TableView();
        tvVendedores.setPrefWidth(1150);
         
        TableColumn idVendedorColum = new TableColumn("ID");
        idVendedorColum.setCellValueFactory(new PropertyValueFactory<>("idVendedor"));
        idVendedorColum.setPrefWidth(120);
        
        TableColumn nombreColumna = new TableColumn("Nombre");
        nombreColumna.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        nombreColumna.setPrefWidth(260);
        
        TableColumn direccionColum = new TableColumn("Direccion");
        direccionColum.setCellValueFactory(new PropertyValueFactory<>("Direccion"));
        direccionColum.setPrefWidth(250);

        TableColumn telefonoColum = new TableColumn("Telefono");
        telefonoColum.setCellValueFactory(new PropertyValueFactory<>("Telefono"));
        telefonoColum.setPrefWidth(250);
        
        tvVendedores.getColumns().addAll(idVendedorColum, nombreColumna, direccionColum, telefonoColum);
        
        lstWhere.clear();
        lstWhere.add("idVendedor is not null");
        tvVendedores.setItems(FXCollections.observableArrayList(venDAO.consultarVendedor(lstWhere)));
        
        tvVendedores.setOnMouseClicked((event) -> {
            venDTO = (vendedor) tvVendedores.getSelectionModel().getSelectedItem();
            tfNombre.setText(venDTO.getNombre());
            tfDireccion.setText(venDTO.getDireccion());
            tfTelefono.setText(venDTO.getTelefono());
        });
        
        btnSeleccionar.setOnAction((event) -> {
            if (rbBuscarTodos.isSelected()){
                    lstWhere.clear();
                    lstWhere.add("idVendedor is not null");
                    tvVendedores.setItems(FXCollections.observableArrayList(venDAO.consultarVendedor(lstWhere)));
            }
            if (rbBuscarNombre.isSelected()){
                    lstWhere.clear();
                    lstWhere.add("Nombre like '%"+tfBuscarNombre.getText()+"%' ");
                    tvVendedores.setItems(FXCollections.observableArrayList(venDAO.consultarVendedor(lstWhere)));
            }
            if (rbBuscarDireccion.isSelected()){
                    lstWhere.clear();
                    lstWhere.add("Direccion like '%"+tfBuscarDireccion.getText()+"%' ");
                    tvVendedores.setItems(FXCollections.observableArrayList(venDAO.consultarVendedor(lstWhere)));
            }
            if (rbBuscarTelefono.isSelected()){
                    lstWhere.clear();
                    lstWhere.add("Telefono like '%"+tfBuscarTelefono.getText()+"%' ");
                    tvVendedores.setItems(FXCollections.observableArrayList(venDAO.consultarVendedor(lstWhere)));
            }
        });
        
        VBox vbDatosVendedor = new VBox();
        vbDatosVendedor.setAlignment(Pos.TOP_LEFT);
        vbDatosVendedor.getChildren().add(lbTitulo);
        vbDatosVendedor.getStyleClass().add("vbox-vistas");
        
        GridPane gpSeleccionarVendedor = new GridPane();
        gpSeleccionarVendedor.setPadding(new Insets(5,5,5,5));
        gpSeleccionarVendedor.setVgap(5);
        gpSeleccionarVendedor.setHgap(5);
        gpSeleccionarVendedor.add(lbBuscarPor, 0, 0);
        gpSeleccionarVendedor.add(rbBuscarTodos, 0, 1);
        gpSeleccionarVendedor.add(rbBuscarNombre, 1, 1);
        gpSeleccionarVendedor.add(tfBuscarNombre, 1, 2);
        gpSeleccionarVendedor.add(rbBuscarDireccion, 2, 1);
        gpSeleccionarVendedor.add(tfBuscarDireccion, 2, 2);
        gpSeleccionarVendedor.add(rbBuscarTelefono, 3, 1);
        gpSeleccionarVendedor.add(tfBuscarTelefono, 3, 2);
        gpSeleccionarVendedor.add(btnSeleccionar, 4, 2);
        
        GridPane gpDatosVendedor = new GridPane();
        gpDatosVendedor.setPadding(new Insets(5,5,5,5));
        gpDatosVendedor.setVgap(25);
        gpDatosVendedor.setHgap(25);
        gpDatosVendedor.add(lbDatosVendedor, 0, 0);
        gpDatosVendedor.add(lbNombre, 0, 1);
        gpDatosVendedor.add(tfNombre, 1, 1);
        gpDatosVendedor.add(lbDireccion, 0, 2);
        gpDatosVendedor.add(tfDireccion, 1, 2);
        gpDatosVendedor.add(lbTelefono, 0, 3);
        gpDatosVendedor.add(tfTelefono, 1, 3);
        
        VBox vbTablaVendedor = new VBox();
        vbTablaVendedor.setPadding(new Insets(5,5,5,5));
        vbTablaVendedor.setSpacing(5);
        vbTablaVendedor.setPrefWidth(1150);
        vbTablaVendedor.setMaxWidth(1150);
        vbTablaVendedor.setMinWidth(1150);
        vbTablaVendedor.setAlignment(Pos.TOP_LEFT);
        vbTablaVendedor.getChildren().addAll(lbTablaVendedores, tvVendedores);
        
        Button btnGuardar = new Button("Eliminar");
        btnGuardar.setOnAction((event) -> {
            venDAO.eliminarVendedor(venDTO.getIdVendedor());
            int result = 1;
            if (result != -1) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setTitle("Confirmación");
                alert.setContentText("Vendedor Eliminado \n¿Deseas eliminar otro?");
                Optional<ButtonType> action = alert.showAndWait();
                if (action.get() == ButtonType.OK) {
                    tfNombre.setText("");
                    tfDireccion.setText("");
                    tfTelefono.setText("");
                    vbAreaTrabajo.getChildren().clear();
                    vbAreaTrabajo.getChildren().add(vEliminarVendedor());
                } else {
                    removerVistas();
                }
            }
        });
        
        Button btnSalir = new Button("Salir");
        btnSalir.setOnAction((event) -> {
            removerVistas();
        });
        
        GridPane gpBotonesControl = new GridPane();
        gpBotonesControl.setPadding(new Insets(5,5,5,5));
        gpBotonesControl.setVgap(25);
        gpBotonesControl.setHgap(25);
        gpBotonesControl.add(btnSalir, 0, 0);
        gpBotonesControl.add(btnGuardar, 1, 0);
        
        vbDatosVendedor.getChildren().addAll(gpSeleccionarVendedor, vbTablaVendedor, gpDatosVendedor, gpBotonesControl);
        
        return vbDatosVendedor;
    } 
    private VBox vEstadoVendedores(){
        VBox vbPpal = new VBox();

        Label lbTitulo = new Label("ESTADO DE LOS VENDEDORES");
        lbTitulo.getStyleClass().add("titulo-vista");
        lbTitulo.setAlignment(Pos.CENTER);
        
        ObservableList<XYChart.Data> aList = FXCollections.observableArrayList(
            new XYChart.Data("Sem. 1", 7),
            new XYChart.Data("Sem. 2", 10),
            new XYChart.Data("Sem. 3", 5),
            new XYChart.Data("Sem. 4", 2),
            new XYChart.Data("Sem. 5", 15),
            new XYChart.Data("Sem. 6", 21)
        );
        //ObservableList<String> categorias = FXCollections.observableArrayList();
        ObservableList<String> categorias = FXCollections.observableArrayList("Sem. 1", "Sem. 2", "Sem. 3", "Sem. 4", "Sem. 5", "Sem. 6");

        // Componentes de Seleccion
        Label lbCoBradores = new Label("Vendedores: ");
        lstWhere.clear();
        lstWhere.add("idVendedor is not null");
        ComboBox cbVendedores = new ComboBox(FXCollections.observableList(venDAO.consultarNombreVendores(lstWhere)));
        cbVendedores.setPrefWidth(180);
        Label lbYear = new Label("Año: ");
        TextField tfYear = new TextField();
        tfYear.setPrefWidth(120);
        
        Label lbSemanInicial = new Label("Semana Inicial");
        TextField tfSemanaInicial = new TextField();
        tfSemanaInicial.setPrefWidth(220);
        
        Label lbSemanFinal = new Label("Semana Final");
        TextField tfSemanaFinal = new TextField();
        tfSemanaFinal.setPrefWidth(220);
        
        // Estadisticas
        Label lbcuentaVentas =  new Label("Cuenta Ventas:");
        Label lbSumaVentas =  new Label("Suma Ventas:");
        Label lbMinimo = new Label("Minimo :");
        Label lbMaximo = new Label("Maximo: ");
        Label lbPromedio = new Label("Promedio Ventas: ");
        
        TableView tvTablaSemanas = new TableView();

        Button btnBuscar = new Button ("Buscar");
        btnBuscar.setOnAction((event) -> {
            lstWhere.clear();
            lstWhere.add("Nombre = '"+cbVendedores.getValue()+"' ");
            vendedor vendDTO = venDAO.consultarVendedor(lstWhere).get(0);            
            lstWhere.clear();
            lstWhere.add("ta.idTarjeta is not null");
            lstWhere.add("ta.idVendedor = "+String.valueOf(vendDTO.getIdVendedor())); 
            lstWhere.add("year = '"+tfYear.getText()+"' ");
            lstWhere.add("cast(semana as integer ) between "+tfSemanaInicial.getText() +" and " + tfSemanaFinal.getText());              
            lstTarjetasPorSemanaConEnganches = FXCollections.observableList(tarDAO.consultarTarjetasEnganchesPorSemana(lstWhere));
            tvTablaSemanas.setItems(lstTarjetasPorSemanaConEnganches);
            
            //Calculo Estadisticas de ventas
            String strTotal = "Cuenta Ventas:"+String.valueOf(lstTarjetasPorSemanaConEnganches.size());
            lbcuentaVentas.setText(strTotal);
            float sumaMontosVentas =0;
            int cuentaNumVentas = 0;
            int minimoNumVentas = lstTarjetasPorSemanaConEnganches.get(0).getCuentaEngaches();
            int maximoNumVentas = 0;
            for (tarjetaPorSemana t : lstTarjetasPorSemanaConEnganches){
               sumaMontosVentas += t.getSumaEnganches();
               cuentaNumVentas += t.getCuentaEngaches();
               if (maximoNumVentas<t.getCuentaEngaches()) maximoNumVentas = t.getCuentaEngaches();
               if (minimoNumVentas>t.getCuentaEngaches()) minimoNumVentas = t.getCuentaEngaches();
            }
            float promedio = cuentaNumVentas/lstTarjetasPorSemanaConEnganches.size();
            lbPromedio.setText("Promedio Ventas: "+String.valueOf(promedio));
            lbMaximo.setText("Maximo: "+String.valueOf(maximoNumVentas));
            lbMinimo.setText("Minimo :"+String.valueOf(minimoNumVentas));
            lbSumaVentas.setText("Suma Ventas:"+String.valueOf(sumaMontosVentas));
                    
                    
            if (!aList.isEmpty())aList.clear();
            if (!categorias.isEmpty())categorias.clear();
            for (tarjetaPorSemana t : lstTarjetasPorSemanaConEnganches){
                String strSemana = "Sem.";
                String concat = strSemana.concat(t.getSemana());
                System.out.println("Semana: "+concat);
                categorias.add(concat);
                int totalPagosSemana = t.getCuentaEngaches();
                System.out.println("TotalporSemana: "+String.valueOf(totalPagosSemana) );
                aList.add(new XYChart.Data(concat, totalPagosSemana));
            }
            
        });
        
        // Grafica para evaluar el desempeño del cobrador por semana
        Label lbGraficaCobradores = new Label("Grafica Vendedores: ");
        ObservableList<XYChart.Series> seriesList = FXCollections.observableArrayList();
        
 
        seriesList.add(new XYChart.Series("Cant. Semanas", aList));

        CategoryAxis xAx = new CategoryAxis(categorias);
        Axis yAxis = new NumberAxis();// ("Cantidad", 0, 50, 5);
        
        BarChart bcGraficaCobrador = new BarChart(xAx, yAxis, seriesList);
        bcGraficaCobrador.setMinSize(1200,250);
        bcGraficaCobrador.setMaxSize(1200,250);
        bcGraficaCobrador.setPrefSize(1200,250);

        //Tabla Semanas
        TableColumn idVendedorColum = new TableColumn("id Vendedor");
        idVendedorColum.setPrefWidth(80);
        idVendedorColum.setCellValueFactory(new PropertyValueFactory<>("idVendedor"));
        
        TableColumn SemanaColum = new TableColumn("Semana");
        SemanaColum.setPrefWidth(80);
        SemanaColum.setCellValueFactory(new PropertyValueFactory<>("semana"));
  
        TableColumn CantidadEnganchesColum = new TableColumn("Cantidad");
        CantidadEnganchesColum.setPrefWidth(300);
        CantidadEnganchesColum.setCellValueFactory(new PropertyValueFactory<>("cuentaEngaches"));
        
        TableColumn SumaEnganchesColum = new TableColumn("Suma Enganches");
        SumaEnganchesColum.setPrefWidth(300);
        SumaEnganchesColum.setCellValueFactory(new PropertyValueFactory<>("sumaEnganches"));
        
        
        tvTablaSemanas.setPrefSize(850, 200);
        tvTablaSemanas.getColumns().addAll(idVendedorColum, SemanaColum, CantidadEnganchesColum, SumaEnganchesColum);
        
        
        
        // Layouts
        GridPane gpSeleccionVendedor = new GridPane();
        gpSeleccionVendedor.setPadding(new Insets(5,5,5,5));
        gpSeleccionVendedor.setVgap(10);
        gpSeleccionVendedor.setHgap(10);
        gpSeleccionVendedor.add(lbCoBradores, 0, 0);
        gpSeleccionVendedor.add(cbVendedores, 1, 0);
        gpSeleccionVendedor.add(lbYear, 2, 0);
        gpSeleccionVendedor.add(tfYear, 3, 0);
        gpSeleccionVendedor.add(lbSemanInicial, 4, 0);
        gpSeleccionVendedor.add(tfSemanaInicial, 5, 0);
        gpSeleccionVendedor.add(lbSemanFinal, 6, 0);
        gpSeleccionVendedor.add(tfSemanaFinal, 7, 0);
        gpSeleccionVendedor.add(btnBuscar, 8, 0);

        GridPane gpGraficador = new GridPane();
        gpGraficador.setPadding(new Insets(5,5,5,5));
        gpGraficador.setVgap(10);
        gpGraficador.setHgap(10);
        gpGraficador.add(lbGraficaCobradores, 0, 0);
        gpGraficador.add(bcGraficaCobrador, 0, 1);
        
        GridPane gpTablaSemanas = new GridPane();
        gpTablaSemanas.setPadding(new Insets(5,5,5,5));
        gpTablaSemanas.setVgap(5);
        gpTablaSemanas.setHgap(6);
        gpTablaSemanas.add(tvTablaSemanas, 0, 0);
        
        GridPane gpEstadisticas = new GridPane();
        gpEstadisticas.setPadding(new Insets(5,5,5,5));
        gpEstadisticas.setVgap(10);
        gpEstadisticas.setHgap(10);
        gpEstadisticas.add(lbSumaVentas,0,0);
        gpEstadisticas.add(lbcuentaVentas,0,1);
        gpEstadisticas.add(lbMaximo,0,2);
        gpEstadisticas.add(lbMinimo,0,3);
        gpEstadisticas.add(lbPromedio,0,4);
        
        HBox hbDatos = new HBox();
        hbDatos.getChildren().setAll(gpTablaSemanas,gpEstadisticas);
        
        vbPpal.getChildren().setAll(gpSeleccionVendedor, gpGraficador, hbDatos);
        return vbPpal;
    }
    
    //Modulos de Cobradores
    private VBox vRegistrarCobrador() {
        Label lbTitulo = new Label("R E G I S T R A R  C O B R A D O R");
        lbTitulo.getStyleClass().add("titulo-vista");
        lbTitulo.setAlignment(Pos.CENTER);

        //Datos Cobrador
        Label lbDatosVendedor = new Label("Datos del Cobrador");
        Label lbNombre = new Label("Nombre: ");
        Label lbDireccion = new Label("Direccion: ");
        Label lbTelefono = new Label("Telefono: ");

        TextField tfNombre = new TextField();
        tfNombre.setPrefWidth(180);
        TextField tfDireccion = new TextField();
        tfDireccion.setPrefWidth(250);
        TextField tfTelefono = new TextField();
        tfTelefono.setPrefWidth(180);

        VBox vbDatosCobrador = new VBox();
        vbDatosCobrador.setAlignment(Pos.CENTER);
        vbDatosCobrador.getChildren().add(lbTitulo);
        vbDatosCobrador.getStyleClass().add("vbox-vistas");

        GridPane gpDatosCobrador = new GridPane();
        gpDatosCobrador.setPadding(new Insets(5, 5, 5, 5));
        gpDatosCobrador.setVgap(25);
        gpDatosCobrador.setHgap(25);
        gpDatosCobrador.add(lbDatosVendedor, 0, 0);
        gpDatosCobrador.add(lbNombre, 0, 1);
        gpDatosCobrador.add(tfNombre, 1, 1);
        gpDatosCobrador.add(lbDireccion, 0, 2);
        gpDatosCobrador.add(tfDireccion, 1, 2);
        gpDatosCobrador.add(lbTelefono, 0, 3);
        gpDatosCobrador.add(tfTelefono, 1, 3);

        Button btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction((event) -> {
            int result = cobraDAO.insertarCobrador(tfNombre.getText(), tfDireccion.getText(), tfTelefono.getText());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Confirmación");
            alert.setContentText("Cobrador Registrado con el ID #"+result+"\n¿Deseas agregar otro?");
            Optional<ButtonType> action = alert.showAndWait();
            if(action.get()== ButtonType.OK){
                vbAreaTrabajo.getChildren().clear();
                vbAreaTrabajo.getChildren().add(vRegistrarVendedor());
            }
            else{
                removerVistas();
            }
        });
        
        Button btnSalir = new Button("Salir");
        btnSalir.setOnAction((event) -> {
            removerVistas();
        });

        GridPane gpBotonesControl = new GridPane();
        gpBotonesControl.setPadding(new Insets(5, 5, 5, 5));
        gpBotonesControl.setVgap(25);
        gpBotonesControl.setHgap(25);
        gpBotonesControl.add(btnSalir, 0, 0);
        gpBotonesControl.add(btnGuardar, 1, 0);

        vbDatosCobrador.getChildren().addAll(gpDatosCobrador, gpBotonesControl);

        return vbDatosCobrador;
    }
    private VBox vModificarCobrador(){
         Label lbTitulo = new Label("M O D I F I C A R  C O B R A D O R");
         lbTitulo.getStyleClass().add("titulo-vista");
         lbTitulo.setPrefWidth(anchoPantalla);
         lbTitulo.setAlignment(Pos.CENTER);
         
         //Tipo busqueda
         ToggleGroup rgTipoBusquedas = new ToggleGroup();
         
         RadioButton rbBuscarTodos = new RadioButton("Todos");
         rbBuscarTodos.setSelected(true);
         rbBuscarTodos.setToggleGroup(rgTipoBusquedas);
         
         RadioButton rbBuscarNombre = new RadioButton("Por Nombre");
         rbBuscarNombre.setToggleGroup(rgTipoBusquedas);
         
         RadioButton rbBuscarDireccion = new RadioButton("Por Direccion");
         rbBuscarDireccion.setToggleGroup(rgTipoBusquedas);

         RadioButton rbBuscarTelefono = new RadioButton("Por Telefono");
         rbBuscarTelefono.setToggleGroup(rgTipoBusquedas);
         
         Label lbBuscarPor = new Label("Buscar: ");
         
         TextField tfBuscarNombre = new TextField();
         TextField tfBuscarDireccion = new TextField();
         TextField tfBuscarTelefono = new TextField();
         
         Button btnSeleccionar = new Button("Seleccionar");
                  
        //Datos Cobrador
        Label lbDatosCobrador = new Label("Datos del Cobrador");
        Label lbNombre = new Label("Nombre: ");
        Label lbDireccion = new Label("Direccion: ");
        Label lbTelefono = new Label("Telefono: ");
        
        TextField tfNombre = new TextField();
        tfNombre.setPrefWidth(180);
        TextField tfDireccion = new TextField();
        tfDireccion.setPrefWidth(250);
        TextField tfTelefono = new TextField();
        tfTelefono.setPrefWidth(180);
        
        //Tabla Cobrador
       
        Label lbTablaCobradores = new Label("Cobradores Seleccionados:");
        TableView tvCobradores = new TableView();
        tvCobradores.setPrefWidth(1150);
         
        TableColumn idVendedorColum = new TableColumn("ID");
        idVendedorColum.setCellValueFactory(new PropertyValueFactory<>("idCobrador"));
        idVendedorColum.setPrefWidth(120);
        
        TableColumn nombreColumna = new TableColumn("Nombre");
        nombreColumna.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        nombreColumna.setPrefWidth(260);
        
        TableColumn direccionColum = new TableColumn("Direccion");
        direccionColum.setCellValueFactory(new PropertyValueFactory<>("Direccion"));
        direccionColum.setPrefWidth(250);

        TableColumn telefonoColum = new TableColumn("Telefono");
        telefonoColum.setCellValueFactory(new PropertyValueFactory<>("Telefono"));
        telefonoColum.setPrefWidth(250);
                
        tvCobradores.getColumns().addAll(idVendedorColum, nombreColumna, direccionColum, telefonoColum);
        
        lstWhere.clear();
        lstWhere.add("idCobrador is not null");
        tvCobradores.setItems(FXCollections.observableArrayList(cobraDAO.consultarCobradores(lstWhere)));
        
        tvCobradores.setOnMouseClicked((event) -> {
            cobraDTO = (cobrador) tvCobradores.getSelectionModel().getSelectedItem();
            tfNombre.setText(cobraDTO.getNombre());
            tfDireccion.setText(cobraDTO.getDireccion());
            tfTelefono.setText(cobraDTO.getTelefono());
        });
        
        btnSeleccionar.setOnAction((event) -> {
            if (rbBuscarTodos.isSelected()){
                    lstWhere.clear();
                    lstWhere.add("idCobrador is not null");
                    tvCobradores.setItems(FXCollections.observableArrayList(cobraDAO.consultarCobradores(lstWhere)));
            }
            if (rbBuscarNombre.isSelected()){
                    lstWhere.clear();
                    lstWhere.add("Nombre like '%"+tfBuscarNombre.getText()+"%' ");
                    tvCobradores.setItems(FXCollections.observableArrayList(cobraDAO.consultarCobradores(lstWhere)));
            }
            if (rbBuscarDireccion.isSelected()){
                    lstWhere.clear();
                    lstWhere.add("Direccion like '%"+tfBuscarDireccion.getText()+"%' ");
                    tvCobradores.setItems(FXCollections.observableArrayList(cobraDAO.consultarCobradores(lstWhere)));
            }
            if (rbBuscarTelefono.isSelected()){
                    lstWhere.clear();
                    lstWhere.add("Telefono like '%"+tfBuscarTelefono.getText()+"%' ");
                    tvCobradores.setItems(FXCollections.observableArrayList(cobraDAO.consultarCobradores(lstWhere)));
            }

        });
        
        VBox vbDatosCobrador = new VBox();
        vbDatosCobrador.setAlignment(Pos.TOP_LEFT);
        vbDatosCobrador.getChildren().add(lbTitulo);
        vbDatosCobrador.getStyleClass().add("vbox-vistas");
        
        GridPane gpSeleccionarCobrador = new GridPane();
        gpSeleccionarCobrador.setPadding(new Insets(5,5,5,5));
        gpSeleccionarCobrador.setVgap(5);
        gpSeleccionarCobrador.setHgap(5);
        gpSeleccionarCobrador.add(lbBuscarPor, 0, 0);
        gpSeleccionarCobrador.add(rbBuscarTodos, 0, 1);
        gpSeleccionarCobrador.add(rbBuscarNombre, 1, 1);
        gpSeleccionarCobrador.add(tfBuscarNombre, 1, 2);
        gpSeleccionarCobrador.add(rbBuscarDireccion, 2, 1);
        gpSeleccionarCobrador.add(tfBuscarDireccion, 2, 2);
        gpSeleccionarCobrador.add(rbBuscarTelefono, 3, 1);
        gpSeleccionarCobrador.add(tfBuscarTelefono, 3, 2);
        gpSeleccionarCobrador.add(btnSeleccionar, 4, 2);
        
        GridPane gpDatosCobrador = new GridPane();
        gpDatosCobrador.setPadding(new Insets(5,5,5,5));
        gpDatosCobrador.setVgap(25);
        gpDatosCobrador.setHgap(25);
        gpDatosCobrador.add(lbDatosCobrador, 0, 0);
        gpDatosCobrador.add(lbNombre, 0, 1);
        gpDatosCobrador.add(tfNombre, 1, 1);
        gpDatosCobrador.add(lbDireccion, 0, 2);
        gpDatosCobrador.add(tfDireccion, 1, 2);
        gpDatosCobrador.add(lbTelefono, 0, 3);
        gpDatosCobrador.add(tfTelefono, 1, 3);
        
        VBox vbTablaCobrador = new VBox();
        vbTablaCobrador.setPadding(new Insets(5,5,5,5));
        vbTablaCobrador.setSpacing(5);
        vbTablaCobrador.setPrefWidth(1150);
        vbTablaCobrador.setMaxWidth(1150);
        vbTablaCobrador.setMinWidth(1150);
        vbTablaCobrador.setAlignment(Pos.TOP_LEFT);
        vbTablaCobrador.getChildren().addAll(lbTablaCobradores, tvCobradores);
        
        Button btnModificar = new Button("Modificar");
        btnModificar.setOnAction((event) -> {
            cobraDAO.modificarVendedor(cobraDTO.getIdCobrador(), tfNombre.getText(), tfDireccion.getText(), tfTelefono.getText());
            int result = 1;
            if (result != -1) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setTitle("Confirmación");
                alert.setContentText("Datos Actualizados del cobrador \n¿Deseas modificar otro?");
                Optional<ButtonType> action = alert.showAndWait();
                if (action.get() == ButtonType.OK) {
                    tfNombre.setText("");
                    tfDireccion.setText("");
                    tfTelefono.setText("");
                    vbAreaTrabajo.getChildren().clear();
                    vbAreaTrabajo.getChildren().add(vModificarCobrador());
                } else {
                    removerVistas();
                }
            }
        });
        
        Button btnSalir = new Button("Salir");
        btnSalir.setOnAction((event) -> {
            removerVistas();
        });
        
        GridPane gpBotonesControl = new GridPane();
        gpBotonesControl.setPadding(new Insets(5,5,5,5));
        gpBotonesControl.setVgap(25);
        gpBotonesControl.setHgap(25);
        gpBotonesControl.add(btnSalir, 0, 0);
        gpBotonesControl.add(btnModificar, 1, 0);
        
        vbDatosCobrador.getChildren().addAll(gpSeleccionarCobrador, vbTablaCobrador, gpDatosCobrador, gpBotonesControl);
        
        return vbDatosCobrador;
    }
    private VBox vEliminarCobrador(){
         Label lbTitulo = new Label("E L I M I N A R   C O B R A D O R");
         lbTitulo.getStyleClass().add("titulo-vista");
         lbTitulo.setPrefWidth(anchoPantalla);
         lbTitulo.setAlignment(Pos.CENTER);
         
         //Tipo busqueda
         ToggleGroup rgTipoBusquedas = new ToggleGroup();
         
         RadioButton rbBuscarTodos = new RadioButton("Todos");
         rbBuscarTodos.setSelected(true);
         rbBuscarTodos.setToggleGroup(rgTipoBusquedas);
         
         RadioButton rbBuscarNombre = new RadioButton("Por Nombre");
         rbBuscarNombre.setToggleGroup(rgTipoBusquedas);
         
         RadioButton rbBuscarDireccion = new RadioButton("Por Direccion");
         rbBuscarDireccion.setToggleGroup(rgTipoBusquedas);

         RadioButton rbBuscarTelefono = new RadioButton("Por Telefono");
         rbBuscarTelefono.setToggleGroup(rgTipoBusquedas);

         Label lbBuscarPor = new Label("Buscar: ");
         
         TextField tfBuscarNombre = new TextField();
         TextField tfBuscarDireccion = new TextField();
         TextField tfBuscarTelefono = new TextField();
         
         Button btnSeleccionar = new Button("Seleccionar");
                  
        //Datos Cobrador
        Label lbDatosCobrador = new Label("Datos del Cobrador");
        Label lbNombre = new Label("Nombre: ");
        Label lbDireccion = new Label("Direccion: ");
        Label lbTelefono = new Label("Telefono: ");
        Label lbTipoCliente = new Label("Tipo Cliente: ");
        
        TextField tfNombre = new TextField();
        tfNombre.setPrefWidth(180);
        TextField tfDireccion = new TextField();
        tfDireccion.setPrefWidth(250);
        TextField tfTelefono = new TextField();
        tfTelefono.setPrefWidth(180);
        
        //Tabla Cobrador
       
        Label lbTablaCobradores = new Label("Cobrador Seleccionados:");
        TableView tvCobradores = new TableView();
        tvCobradores.setPrefWidth(1150);
         
        TableColumn idCobradorColum = new TableColumn("ID");
        idCobradorColum.setCellValueFactory(new PropertyValueFactory<>("idVendedor"));
        idCobradorColum.setPrefWidth(120);
        
        TableColumn nombreColumna = new TableColumn("Nombre");
        nombreColumna.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        nombreColumna.setPrefWidth(260);
        
        TableColumn direccionColum = new TableColumn("Direccion");
        direccionColum.setCellValueFactory(new PropertyValueFactory<>("Direccion"));
        direccionColum.setPrefWidth(250);

        TableColumn telefonoColum = new TableColumn("Telefono");
        telefonoColum.setCellValueFactory(new PropertyValueFactory<>("Telefono"));
        telefonoColum.setPrefWidth(250);
        
        tvCobradores.getColumns().addAll(idCobradorColum, nombreColumna, direccionColum, telefonoColum);
        
        lstWhere.clear();
        lstWhere.add("idCobrador is not null");
        tvCobradores.setItems(FXCollections.observableArrayList(cobraDAO.consultarCobradores(lstWhere)));
        
        tvCobradores.setOnMouseClicked((event) -> {
            cobraDTO = (cobrador) tvCobradores.getSelectionModel().getSelectedItem();
            tfNombre.setText(cobraDTO.getNombre());
            tfDireccion.setText(cobraDTO.getDireccion());
            tfTelefono.setText(cobraDTO.getTelefono());
        });
        
        btnSeleccionar.setOnAction((event) -> {
            if (rbBuscarTodos.isSelected()){
                    lstWhere.clear();
                    lstWhere.add("idCobrador is not null");
                    tvCobradores.setItems(FXCollections.observableArrayList(cobraDAO.consultarCobradores(lstWhere)));
            }
            if (rbBuscarNombre.isSelected()){
                    lstWhere.clear();
                    lstWhere.add("Nombre like '%"+tfBuscarNombre.getText()+"%' ");
                    tvCobradores.setItems(FXCollections.observableArrayList(cobraDAO.consultarCobradores(lstWhere)));
            }
            if (rbBuscarDireccion.isSelected()){
                    lstWhere.clear();
                    lstWhere.add("Direccion like '%"+tfBuscarDireccion.getText()+"%' ");
                    tvCobradores.setItems(FXCollections.observableArrayList(cobraDAO.consultarCobradores(lstWhere)));
            }
            if (rbBuscarTelefono.isSelected()){
                    lstWhere.clear();
                    lstWhere.add("Telefono like '%"+tfBuscarTelefono.getText()+"%' ");
                    tvCobradores.setItems(FXCollections.observableArrayList(cobraDAO.consultarCobradores(lstWhere)));
            }
        });
        
        VBox vbDatosCobrador = new VBox();
        vbDatosCobrador.setAlignment(Pos.TOP_LEFT);
        vbDatosCobrador.getChildren().add(lbTitulo);
        vbDatosCobrador.getStyleClass().add("vbox-vistas");
        
        GridPane gpSeleccionarCobrador = new GridPane();
        gpSeleccionarCobrador.setPadding(new Insets(5,5,5,5));
        gpSeleccionarCobrador.setVgap(5);
        gpSeleccionarCobrador.setHgap(5);
        gpSeleccionarCobrador.add(lbBuscarPor, 0, 0);
        gpSeleccionarCobrador.add(rbBuscarTodos, 0, 1);
        gpSeleccionarCobrador.add(rbBuscarNombre, 1, 1);
        gpSeleccionarCobrador.add(tfBuscarNombre, 1, 2);
        gpSeleccionarCobrador.add(rbBuscarDireccion, 2, 1);
        gpSeleccionarCobrador.add(tfBuscarDireccion, 2, 2);
        gpSeleccionarCobrador.add(rbBuscarTelefono, 3, 1);
        gpSeleccionarCobrador.add(tfBuscarTelefono, 3, 2);
        gpSeleccionarCobrador.add(btnSeleccionar, 4, 2);
        
        GridPane gpDatosCobrador = new GridPane();
        gpDatosCobrador.setPadding(new Insets(5,5,5,5));
        gpDatosCobrador.setVgap(25);
        gpDatosCobrador.setHgap(25);
        gpDatosCobrador.add(lbDatosCobrador, 0, 0);
        gpDatosCobrador.add(lbNombre, 0, 1);
        gpDatosCobrador.add(tfNombre, 1, 1);
        gpDatosCobrador.add(lbDireccion, 0, 2);
        gpDatosCobrador.add(tfDireccion, 1, 2);
        gpDatosCobrador.add(lbTelefono, 0, 3);
        gpDatosCobrador.add(tfTelefono, 1, 3);
        
        VBox vbTablaCobrador = new VBox();
        vbTablaCobrador.setPadding(new Insets(5,5,5,5));
        vbTablaCobrador.setSpacing(5);
        vbTablaCobrador.setPrefWidth(1150);
        vbTablaCobrador.setMaxWidth(1150);
        vbTablaCobrador.setMinWidth(1150);
        vbTablaCobrador.setAlignment(Pos.TOP_LEFT);
        vbTablaCobrador.getChildren().addAll(lbTablaCobradores, tvCobradores);
        
        Button btnGuardar = new Button("Eliminar");
        btnGuardar.setOnAction((event) -> {
            cobraDAO.eliminarVendedor(cobraDTO.getIdCobrador());
            int result = 1;
            if (result != -1) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setTitle("Confirmación");
                alert.setContentText("Cobrador Eliminado \n¿Deseas eliminar otro?");
                Optional<ButtonType> action = alert.showAndWait();
                if (action.get() == ButtonType.OK) {
                    tfNombre.setText("");
                    tfDireccion.setText("");
                    tfTelefono.setText("");
                    vbAreaTrabajo.getChildren().clear();
                    vbAreaTrabajo.getChildren().add(vEliminarCobrador());
                } else {
                    removerVistas();
                }
            }
        });
        
        Button btnSalir = new Button("Salir");
        btnSalir.setOnAction((event) -> {
            removerVistas();
        });
        
        GridPane gpBotonesControl = new GridPane();
        gpBotonesControl.setPadding(new Insets(5,5,5,5));
        gpBotonesControl.setVgap(25);
        gpBotonesControl.setHgap(25);
        gpBotonesControl.add(btnSalir, 0, 0);
        gpBotonesControl.add(btnGuardar, 1, 0);
        
        vbDatosCobrador.getChildren().addAll(gpSeleccionarCobrador, vbTablaCobrador, gpDatosCobrador, gpBotonesControl);
        
        return vbDatosCobrador;
    }
    private VBox vEstadoCobradores(){
        VBox vbPpal = new VBox();

        Label lbTitulo = new Label("ESTADO DE LOS COBRADORES");
        lbTitulo.getStyleClass().add("titulo-vista");
        lbTitulo.setAlignment(Pos.CENTER);
        
        ObservableList<XYChart.Data> aList = FXCollections.observableArrayList(
            new XYChart.Data("Sem. 1", 7),
            new XYChart.Data("Sem. 2", 10),
            new XYChart.Data("Sem. 3", 5),
            new XYChart.Data("Sem. 4", 2),
            new XYChart.Data("Sem. 5", 15),
            new XYChart.Data("Sem. 6", 21)
        );
        //ObservableList<String> categorias = FXCollections.observableArrayList();
        ObservableList<String> categorias = FXCollections.observableArrayList("Sem. 1", "Sem. 2", "Sem. 3", "Sem. 4", "Sem. 5", "Sem. 6");

        // Componentes de Seleccion
        Label lbCoBradores = new Label("Cobradores: ");
        lstWhere.clear();
        lstWhere.add("idCobrador is not null");
        ComboBox cbCobradores = new ComboBox(FXCollections.observableList(cobraDAO.consultarNombreCobradores(lstWhere)));
        cbCobradores.setPrefWidth(180);
        Label lbYear = new Label("Año: ");
        TextField tfYear = new TextField();
        tfYear.setPrefWidth(120);
        
        Label lbSemanInicial = new Label("Semana Inicial");
        TextField tfSemanaInicial = new TextField();
        tfSemanaInicial.setPrefWidth(220);
        
        Label lbSemanFinal = new Label("Semana Final");
        TextField tfSemanaFinal = new TextField();
        tfSemanaFinal.setPrefWidth(220);
        
        // Estadisticas
        Label lbCuentaCobros =  new Label("Cuenta Cobros:");
        Label lbSumaCobros =  new Label("Suma Cobros:");
        Label lbMinimo = new Label("Minimo :");
        Label lbMaximo = new Label("Maximo: ");
        Label lbPromedio = new Label("Promedio Cobros: ");        
        
        TableView tvTablaSemanas = new TableView();

        Button btnBuscar = new Button ("Buscar");
        btnBuscar.setOnAction((event) -> {
            lstWhere.clear();
            lstWhere.add("Nombre = '"+cbCobradores.getValue()+"' ");
            cobrador cbrDTO = cobraDAO.consultarCobradores(lstWhere).get(0);            
            lstWhere.clear();
            lstWhere.add("pr.idTarjeta is not null");
            lstWhere.add("pr.Tipo in('Parcialidad', 'Abono')");
            lstWhere.add("ta.idCobrador = "+String.valueOf(cbrDTO.getIdCobrador())); 
            lstWhere.add("year = '"+tfYear.getText()+"' ");
            lstWhere.add("cast(semana as integer ) between "+tfSemanaInicial.getText() +" and " + tfSemanaFinal.getText());              
            lstTarjetasAsigPorSemanaConPagos = FXCollections.observableList(tarAsigDAO.consultarTarjetasPagosPorSemana(lstWhere));
             tvTablaSemanas.setItems(lstTarjetasAsigPorSemanaConPagos);

            //Calculo Estadisticas de Cobros
            String strTotal = "Cuenta Cobros:"+String.valueOf(lstTarjetasAsigPorSemanaConPagos.size());
            lbCuentaCobros.setText(strTotal);
            float sumaMontosCobros =0;
            int cuentaNumCobros = 0;
            int minimoNumCobros = lstTarjetasAsigPorSemanaConPagos.get(0).getCuantosPagos();
            int maximoNumCobros = 0;
            for (tarjetasAsigPorSemanaConPagosDTO t : lstTarjetasAsigPorSemanaConPagos){
               sumaMontosCobros += t.getSumaMontos();
               cuentaNumCobros += t.getCuantosPagos();
               if (maximoNumCobros<t.getCuantosPagos()) maximoNumCobros = t.getCuantosPagos();
               if (minimoNumCobros>t.getCuantosPagos()) minimoNumCobros = t.getCuantosPagos();
            }
            float promedio = cuentaNumCobros/lstTarjetasAsigPorSemanaConPagos.size();
            lbPromedio.setText("Promedio Cobros: "+String.valueOf(promedio));
            lbMaximo.setText("Maximo: "+String.valueOf(maximoNumCobros));
            lbMinimo.setText("Minimo :"+String.valueOf(minimoNumCobros));
            lbSumaCobros.setText("Suma Cobros:"+String.valueOf(sumaMontosCobros));
              
            if (!aList.isEmpty())aList.clear();
            if (!categorias.isEmpty())categorias.clear();
            for (tarjetasAsigPorSemanaConPagosDTO t : lstTarjetasAsigPorSemanaConPagos){
                String strSemana = "Sem.";
                String concat = strSemana.concat(t.getSemana());
                System.out.println("Semana: "+concat);
                categorias.add(concat);
                int totalPagosSemana = t.getCuantosPagos();
                System.out.println("TotalporSemana: "+String.valueOf(totalPagosSemana) );
                aList.add(new XYChart.Data(concat, totalPagosSemana));
            }
            
        });
        
        // Grafica para evaluar el desempeño del cobrador por semana
        Label lbGraficaCobradores = new Label("Grafica Cobradores: ");
        ObservableList<XYChart.Series> seriesList = FXCollections.observableArrayList();
        
 
        seriesList.add(new XYChart.Series("Cant. Semanas", aList));

        CategoryAxis xAx = new CategoryAxis(categorias);
        Axis yAxis = new NumberAxis();// ("Cantidad", 0, 50, 5);
        
        BarChart bcGraficaCobrador = new BarChart(xAx, yAxis, seriesList);
        bcGraficaCobrador.setMinSize(1200,250);
        bcGraficaCobrador.setMaxSize(1200,250);
        bcGraficaCobrador.setPrefSize(1200,250);

        //Tabla Semanas
        TableColumn SemanaColum = new TableColumn("Semana");
        SemanaColum.setPrefWidth(300);
        SemanaColum.setCellValueFactory(new PropertyValueFactory<>("semana"));
  
        TableColumn CantidadColum = new TableColumn("Cantidad");
        CantidadColum.setPrefWidth(300);
        CantidadColum.setCellValueFactory(new PropertyValueFactory<>("cuantosPagos"));
        
        TableColumn MontoCobradoColum = new TableColumn("Monto Cobrado");
        MontoCobradoColum.setPrefWidth(300);
        MontoCobradoColum.setCellValueFactory(new PropertyValueFactory<>("sumaMontos"));
        
        
        tvTablaSemanas.setPrefSize(850, 200);
        tvTablaSemanas.getColumns().addAll(SemanaColum, CantidadColum, MontoCobradoColum);
       
        
        // Layouts
        GridPane gpSeleccionCobrador = new GridPane();
        gpSeleccionCobrador.setPadding(new Insets(5,5,5,5));
        gpSeleccionCobrador.setVgap(10);
        gpSeleccionCobrador.setHgap(10);
        gpSeleccionCobrador.add(lbCoBradores, 0, 0);
        gpSeleccionCobrador.add(cbCobradores, 1, 0);
        gpSeleccionCobrador.add(lbYear, 2, 0);
        gpSeleccionCobrador.add(tfYear, 3, 0);
        gpSeleccionCobrador.add(lbSemanInicial, 4, 0);
        gpSeleccionCobrador.add(tfSemanaInicial, 5, 0);
        gpSeleccionCobrador.add(lbSemanFinal, 6, 0);
        gpSeleccionCobrador.add(tfSemanaFinal, 7, 0);
        gpSeleccionCobrador.add(btnBuscar, 8, 0);

        GridPane gpGraficador = new GridPane();
        gpGraficador.setPadding(new Insets(5,5,5,5));
        gpGraficador.setVgap(10);
        gpGraficador.setHgap(10);
        gpGraficador.add(lbGraficaCobradores, 0, 0);
        gpGraficador.add(bcGraficaCobrador, 0, 1);
        
        GridPane gpTablaSemanas = new GridPane();
        gpTablaSemanas.setPadding(new Insets(5,5,5,5));
        gpTablaSemanas.setVgap(5);
        gpTablaSemanas.setHgap(6);
        gpTablaSemanas.add(tvTablaSemanas, 0, 0);
        
        GridPane gpEstadisticas = new GridPane();
        gpEstadisticas.setPadding(new Insets(5,5,5,5));
        gpEstadisticas.setVgap(10);
        gpEstadisticas.setHgap(10);
        gpEstadisticas.add(lbSumaCobros,0,0);
        gpEstadisticas.add(lbCuentaCobros,0,1);
        gpEstadisticas.add(lbMaximo,0,2);
        gpEstadisticas.add(lbMinimo,0,3);
        gpEstadisticas.add(lbPromedio,0,4);
        
        HBox hbDatos = new HBox();
        hbDatos.getChildren().setAll(gpTablaSemanas,gpEstadisticas);
 
        
        vbPpal.getChildren().setAll(gpSeleccionCobrador, gpGraficador, hbDatos);
        return vbPpal;
    }
    
    //Modulos de Inventario
    private VBox vRegistrarProducto(){
        VBox vbPpal = new VBox();
        Label lbTitulo = new Label("R E G I S T R A R  P R O D U C T O");
        lbTitulo.getStyleClass().add("titulo-vista");
        lbTitulo.setAlignment(Pos.CENTER);
        
        //idProducto,CodigoProducto,Existencia,Descripcion,UnidadMedida,PrecioContado,Precio_crediContado,Precio_credito,Proveedor,idCategoria,
        
        Label lbDatProduc = new Label ("Datos de Producto: ");
        //Label lbidProducto = new Label ("idProducto");
        Label lbCodigoProducto = new Label ("CodigoProducto");
        Label lbExistencia = new Label ("Existencia");
        Label lbDescripcion = new Label ("Descripcion");
        Label lbUnidadMedida = new Label ("UnidadMedida");
        Label lbPrecioContado = new Label ("PrecioContado");
        Label lbPrecio_crediContado = new Label ("Precio_crediContado");
        Label lbPrecio_credito = new Label ("Precio_credito");
        Label lbProveedor = new Label ("Proveedor");
        Label lbidCategoria = new Label ("idCategoria");    
        
        //TextField tfidProducto = new TextField();
        TextField tfCodigoProducto = new TextField();
        tfCodigoProducto.setPrefWidth(110);
        tfCodigoProducto.setMaxWidth(110);
        
        TextField tfExistencia = new TextField();
        tfExistencia.setPrefWidth(80);
        tfExistencia.setMaxWidth(80);
        
        TextField tfDescripcion = new TextField();
        tfDescripcion.setPrefWidth(350);
        tfDescripcion.setMaxWidth(350);
        
        TextField tfUnidadMedida = new TextField("Pza.");
        tfUnidadMedida.setPrefWidth(60);
        tfUnidadMedida.setMaxWidth(60);
        
        TextField tfPrecioContado = new TextField();
        tfPrecioContado.setPrefWidth(120);
        tfPrecioContado.setMaxWidth(120);
        
        TextField tfPrecio_crediContado = new TextField();
        tfPrecio_crediContado.setPrefWidth(120);
        tfPrecio_crediContado.setMaxWidth(120);
        
        TextField tfPrecio_credito = new TextField();
        tfPrecio_credito.setPrefWidth(120);
        tfPrecio_credito.setMaxWidth(120);
        
        TextField tfProveedor = new TextField();
        tfProveedor.setPrefWidth(220);
        tfProveedor.setMaxWidth(220);
        
        TextField tfidCategoria = new TextField();
        tfidCategoria.setPrefWidth(220);
        tfidCategoria.setMaxWidth(220);

        
        Button btnGeneraCodigo = new Button("Generar Codigo");
        btnGeneraCodigo.setOnMouseClicked((event) -> {
            LocalDateTime ldtFecha = LocalDateTime.now();
            String strYear = String.valueOf(ldtFecha.getYear());
            
            String strMes = String.valueOf(ldtFecha.getMonthValue());
            if (strMes.length()==1){strMes = "0"+strMes;}
            
            String strDiaMes = String.valueOf(ldtFecha.getDayOfMonth());
            if (strDiaMes.length()==1){strDiaMes = "0"+strDiaMes;}
            
            String strHr = String.valueOf(ldtFecha.getHour());
            if (strHr.length()==1){strHr = "0"+strHr;}
            
            String strMin = String.valueOf(ldtFecha.getMinute());
            if (strMin.length()==1){strMin = "0"+strMin;}
            
            String strSec = String.valueOf(ldtFecha.getSecond());
            if (strSec.length()==1){strSec = "0"+strSec;}
            
            String strCodGen = "A"+strYear+strMes+strDiaMes+strHr+strSec;
            tfCodigoProducto.setText(strCodGen);
            
        });
        
        Button btnSalir = new Button("Salir");
        btnSalir.setOnMouseClicked((event) -> {
            removerVistas();
        });
        
        Button btnAgregar = new Button("Agregar");
        btnAgregar.setOnAction((event) -> {
            int result = invDAO.insertarInventario(tfCodigoProducto.getText(), Integer.parseInt(tfExistencia.getText()), tfDescripcion.getText(), tfUnidadMedida.getText(),
                    Float.parseFloat(tfPrecioContado.getText()), Float.parseFloat(tfPrecio_crediContado.getText()), Float.parseFloat(tfPrecio_credito.getText()), 
                    tfProveedor.getText(), tfidCategoria.getText());
            if (result != -1) {
                Alert alertMessage = new Alert(Alert.AlertType.CONFIRMATION);
                alertMessage.setTitle("CONFIRMATION");
                alertMessage.setContentText("Se Registro el Producto con el Codigo Interno "+result+" \n ¿Deseas Generar Otro?");
                Optional<ButtonType> action = alertMessage.showAndWait();
                if (action.get() == ButtonType.OK) {
                    vbAreaTrabajo.getChildren().clear();
                    vbAreaTrabajo.getChildren().add(vRegistrarProducto());
                } else {
                    removerVistas();
                }                
            }else{
              Alert aMessageError = new Alert(Alert.AlertType.ERROR);
              aMessageError.setTitle("Error");
              aMessageError.setContentText("Error de Captura!!");
            }
            
        });
        
        HBox hbBotones = new HBox(btnSalir, btnAgregar);
        hbBotones.setSpacing(10);
        
        HBox hbCodigoProd = new HBox(tfCodigoProducto, btnGeneraCodigo);
        hbCodigoProd.setSpacing(10);
        
        GridPane gpPpal = new GridPane();
        gpPpal.setPadding(new Insets(5, 5, 5, 5));
        gpPpal.setVgap(10);
        gpPpal.setHgap(10);
        gpPpal.add(lbDatProduc, 0, 0);
        gpPpal.add(lbCodigoProducto, 0, 1);
        gpPpal.add(hbCodigoProd, 1, 1);
        gpPpal.add(lbDescripcion, 0, 2);
        gpPpal.add(tfDescripcion, 1, 2);
        gpPpal.add(lbExistencia, 0, 3);
        gpPpal.add(tfExistencia, 1, 3);
        gpPpal.add(lbUnidadMedida, 0, 4);
        gpPpal.add(tfUnidadMedida, 1, 4);
        gpPpal.add(lbPrecioContado, 0, 5);
        gpPpal.add(tfPrecioContado, 1, 5);
        gpPpal.add(lbPrecio_crediContado, 0, 6);
        gpPpal.add(tfPrecio_crediContado, 1, 6);
        gpPpal.add(lbPrecio_credito, 0, 7);
        gpPpal.add(tfPrecio_credito, 1, 7);
        gpPpal.add(lbProveedor, 0, 8);
        gpPpal.add(tfProveedor, 1, 8);
        gpPpal.add(lbidCategoria, 0, 9);
        gpPpal.add(tfidCategoria, 1, 9);
        gpPpal.add(hbBotones, 1, 10);
        
        vbPpal.getChildren().addAll(lbTitulo, gpPpal);
        
        return vbPpal;    
    }
    private VBox vModificarProducto(){
        VBox vbPpal = new VBox();
        Label lbTitulo = new Label("M O D I F I C A R  P R O D U C T O");
        lbTitulo.getStyleClass().add("titulo-vista");
        lbTitulo.setAlignment(Pos.CENTER);
        
        //idProducto,CodigoProducto,Existencia,Descripcion,UnidadMedida,PrecioContado,Precio_crediContado,Precio_credito,Proveedor,idCategoria,

        //Componentes busqueda de productos
        Label lbTipoBusqueda = new Label("Buscar por: ");
        
        ToggleGroup tgBusquedas = new ToggleGroup();
        
        RadioButton rbTodos = new RadioButton("Todos");
        RadioButton rbCodigo = new RadioButton("Codigo");
        RadioButton rbDescripcion = new RadioButton("Descripción");
        RadioButton rbCategoria = new RadioButton("Categoria");
        
        rbTodos.setSelected(true);
        
        rbTodos.setToggleGroup(tgBusquedas);
        rbCodigo.setToggleGroup(tgBusquedas);
        rbDescripcion.setToggleGroup(tgBusquedas);
        rbCategoria.setToggleGroup(tgBusquedas);
        
        TextField tfBuscarCodigo = new TextField();
        TextField tfBuscarDescripcion = new TextField();
        tfBuscarDescripcion.setPrefWidth(250);
        ComboBox cbBuscarCategoria = new ComboBox();
        cbBuscarCategoria.setPrefWidth(180);
        
        Button btnSeleccionar = new Button("Seleccionar Productos");
        
        //Datos del producto
        
        Label lbDatProduc = new Label ("Datos de Producto: ");
        //Label lbidProducto = new Label ("idProducto");
        Label lbCodigoProducto = new Label ("CodigoProducto");
        Label lbExistencia = new Label ("Existencia");
        Label lbDescripcion = new Label ("Descripcion");
        Label lbUnidadMedida = new Label ("UnidadMedida");
        Label lbPrecioContado = new Label ("PrecioContado");
        Label lbPrecio_crediContado = new Label ("Precio_crediContado");
        Label lbPrecio_credito = new Label ("Precio_credito");
        Label lbProveedor = new Label ("Proveedor");
        Label lbidCategoria = new Label ("idCategoria");    
        
        //TextField tfidProducto = new TextField();
        TextField tfCodigoProducto = new TextField();
        tfCodigoProducto.setPrefWidth(110);
        tfCodigoProducto.setMaxWidth(110);
        
        TextField tfExistencia = new TextField();
        tfExistencia.setPrefWidth(80);
        tfExistencia.setMaxWidth(80);
        
        TextField tfDescripcion = new TextField();
        tfDescripcion.setPrefWidth(350);
        tfDescripcion.setMaxWidth(350);
        
        TextField tfUnidadMedida = new TextField("Pza.");
        tfUnidadMedida.setPrefWidth(60);
        tfUnidadMedida.setMaxWidth(60);
        
        TextField tfPrecioContado = new TextField();
        tfPrecioContado.setPrefWidth(120);
        tfPrecioContado.setMaxWidth(120);
        
        TextField tfPrecio_crediContado = new TextField();
        tfPrecio_crediContado.setPrefWidth(120);
        tfPrecio_crediContado.setMaxWidth(120);
        
        TextField tfPrecio_credito = new TextField();
        tfPrecio_credito.setPrefWidth(120);
        tfPrecio_credito.setMaxWidth(120);
        
        TextField tfProveedor = new TextField();
        tfProveedor.setPrefWidth(220);
        tfProveedor.setMaxWidth(220);
        
        TextField tfidCategoria = new TextField();
        tfidCategoria.setPrefWidth(220);
        tfidCategoria.setMaxWidth(220);
        
        TableView tvProductos = new TableView();
        tvProductos.setPrefSize(500, 300);
        
        TableColumn<inventario, String> idProductoColumna = new TableColumn<>("idProducto");
        idProductoColumna.setMinWidth(60);
        idProductoColumna.setCellValueFactory(new PropertyValueFactory<>("Id Producto "));        
        
        TableColumn<inventario, String> codigoColumna = new TableColumn<>("Codigo Producto");
        codigoColumna.setMinWidth(120);
        codigoColumna.setCellValueFactory(new PropertyValueFactory<>("CodigoProducto"));
        
        TableColumn<inventario, Float> existenciaColumna = new TableColumn<>("Existencia");
        existenciaColumna.setMinWidth(80);
        existenciaColumna.setCellValueFactory(new PropertyValueFactory<>("Existencia"));

        TableColumn<inventario, Float> descripcionColumna = new TableColumn<>("Descripcion");
        descripcionColumna.setMinWidth(180);
        descripcionColumna.setCellValueFactory(new PropertyValueFactory<>("Descripcion"));

        TableColumn<inventario, Float> unidadMedidaColumna = new TableColumn<>("U. Med.");
        unidadMedidaColumna.setMinWidth(60);
        unidadMedidaColumna.setCellValueFactory(new PropertyValueFactory<>("UnidadMedida"));

        TableColumn<inventario, Float> precioContadoColumna = new TableColumn<>("Precio Contado");
        precioContadoColumna.setMinWidth(80);
        precioContadoColumna.setCellValueFactory(new PropertyValueFactory<>("PrecioContado"));

        TableColumn<inventario, Float> crediContadoColumna = new TableColumn<>("P. Credi-Contado");
        crediContadoColumna.setMinWidth(80);
        crediContadoColumna.setCellValueFactory(new PropertyValueFactory<>("Precio_crediContado"));

        TableColumn<inventario, Float> creditoColumna = new TableColumn<>("Precio Credito");
        creditoColumna.setMinWidth(80);
        creditoColumna.setCellValueFactory(new PropertyValueFactory<>("Precio_credito"));

        TableColumn<inventario, Float> proveedorColumna = new TableColumn<>("Proveedor");
        proveedorColumna.setMinWidth(80);
        proveedorColumna.setCellValueFactory(new PropertyValueFactory<>("Proveedor"));

        TableColumn<inventario, Float> categoriaColumna = new TableColumn<>("id Categoria");
        categoriaColumna.setMinWidth(80);
        categoriaColumna.setCellValueFactory(new PropertyValueFactory<>("idCategoria"));
        
        tvProductos.getColumns().setAll(codigoColumna, existenciaColumna, descripcionColumna, unidadMedidaColumna, precioContadoColumna,
                crediContadoColumna, creditoColumna, proveedorColumna, categoriaColumna);
        tvProductos.setOnMouseClicked((event) -> {
            invDTO = (inventario)tvProductos.getSelectionModel().getSelectedItem();
            tfCodigoProducto.setText(invDTO.getCodigoProducto());
            tfDescripcion.setText(invDTO.getDescripcion());
            tfExistencia.setText(String.valueOf(invDTO.getExistencia()));
            tfUnidadMedida.setText(invDTO.getUnidadMedida());
            tfPrecioContado.setText(String.valueOf(invDTO.getPrecioContado()));
            tfPrecio_crediContado.setText(String.valueOf(invDTO.getPrecio_crediContado()));
            tfPrecio_credito.setText(String.valueOf(invDTO.getPrecio_credito()));
            tfProveedor.setText(invDTO.getProveedor());
            tfidCategoria.setText(invDTO.getIdCategoria());
        });

        lstWhere.clear();
        lstWhere.add("idProducto is not null");
        //lstInventario = invDAO.consultarInventario(lstWhere);
        tvProductos.setItems(FXCollections.observableArrayList(invDAO.consultarInventario(lstWhere)));
        
        btnSeleccionar.setOnMouseClicked((event) -> {
            if(rbTodos.isSelected()){
                if(!tvProductos.getItems().isEmpty()) tvProductos.getItems().clear();
                lstWhere.clear();
                lstWhere.add("idProducto is not null");
                //lstInventario = invDAO.consultarInventario(lstWhere);
                tvProductos.setItems(FXCollections.observableArrayList(invDAO.consultarInventario(lstWhere)));
            }
            if (rbCodigo.isSelected()){
                if(!tvProductos.getItems().isEmpty()) tvProductos.getItems().clear();
                lstWhere.clear();
                lstWhere.add("CodigoProducto ='"+tfBuscarCodigo.getText()+"' ");
                tvProductos.setItems(FXCollections.observableArrayList(invDAO.consultarInventario(lstWhere)));
            }
            if (rbDescripcion.isSelected()){
                if(!tvProductos.getItems().isEmpty()) tvProductos.getItems().clear();
                lstWhere.clear();
                lstWhere.add("Descripcion like '%"+tfBuscarDescripcion.getText()+"%' ");
                tvProductos.setItems(FXCollections.observableArrayList(invDAO.consultarInventario(lstWhere)));
            }
        });
        
        Button btnGeneraCodigo = new Button("Generar Codigo");
        btnGeneraCodigo.setOnMouseClicked((event) -> {
            LocalDateTime ldtFecha = LocalDateTime.now();
            String strYear = String.valueOf(ldtFecha.getYear());
            
            String strMes = String.valueOf(ldtFecha.getMonthValue());
            if (strMes.length()==1){strMes = "0"+strMes;}
            
            String strDiaMes = String.valueOf(ldtFecha.getDayOfMonth());
            if (strDiaMes.length()==1){strDiaMes = "0"+strDiaMes;}
            
            String strHr = String.valueOf(ldtFecha.getHour());
            if (strHr.length()==1){strHr = "0"+strHr;}
            
            String strMin = String.valueOf(ldtFecha.getMinute());
            if (strMin.length()==1){strMin = "0"+strMin;}
            
            String strSec = String.valueOf(ldtFecha.getSecond());
            if (strSec.length()==1){strSec = "0"+strSec;}
            
            String strCodGen = "A"+strYear+strMes+strDiaMes+strHr+strSec;
            tfCodigoProducto.setText(strCodGen);
            
        });
        
        Button btnSalir = new Button("Salir");
        btnSalir.setOnMouseClicked((event) -> {
            removerVistas();
        });
        
        Button btnModificar = new Button("Modificar");
        btnModificar.setOnAction((event) -> {
            
            invDAO.modificarInventario(invDTO.getIdProducto(), tfCodigoProducto.getText(), Integer.parseInt(tfExistencia.getText()), tfDescripcion.getText(), tfUnidadMedida.getText(),
                    Float.parseFloat(tfPrecioContado.getText()), Float.parseFloat(tfPrecio_crediContado.getText()), Float.parseFloat(tfPrecio_credito.getText()), 
                    tfProveedor.getText(), tfidCategoria.getText());
                Alert alertMessage = new Alert(Alert.AlertType.CONFIRMATION);
                alertMessage.setTitle("CONFIRMATION");
                alertMessage.setContentText("Se Modifico el Producto con el Codigo Interno \n ¿Deseas Modificar Otro?");
                Optional<ButtonType> action = alertMessage.showAndWait();
                if (action.get() == ButtonType.OK) {
                    vbAreaTrabajo.getChildren().clear();
                    vbAreaTrabajo.getChildren().add(vModificarProducto());
                } else {
                    removerVistas();
                }                
        });
        
        HBox hbBotones = new HBox(btnSalir, btnModificar);
        hbBotones.setSpacing(10);
        
        HBox hbCodigoProd = new HBox(tfCodigoProducto, btnGeneraCodigo);
        hbCodigoProd.setSpacing(10);
        
        GridPane gpPpal = new GridPane();
        gpPpal.setPadding(new Insets(5, 5, 5, 5));
        gpPpal.setVgap(10);
        gpPpal.setHgap(10);
        gpPpal.add(lbDatProduc, 0, 0);
        gpPpal.add(lbCodigoProducto, 0, 1);
        gpPpal.add(hbCodigoProd, 1, 1);
        gpPpal.add(lbDescripcion, 0, 2);
        gpPpal.add(tfDescripcion, 1, 2);
        gpPpal.add(lbExistencia, 0, 3);
        gpPpal.add(tfExistencia, 1, 3);
        gpPpal.add(lbUnidadMedida, 0, 4);
        gpPpal.add(tfUnidadMedida, 1, 4);
        gpPpal.add(lbPrecioContado, 0, 5);
        gpPpal.add(tfPrecioContado, 1, 5);
        gpPpal.add(lbPrecio_crediContado, 0, 6);
        gpPpal.add(tfPrecio_crediContado, 1, 6);
        gpPpal.add(lbPrecio_credito, 0, 7);
        gpPpal.add(tfPrecio_credito, 1, 7);
        gpPpal.add(lbProveedor, 0, 8);
        gpPpal.add(tfProveedor, 1, 8);
        gpPpal.add(lbidCategoria, 0, 9);
        gpPpal.add(tfidCategoria, 1, 9);
        gpPpal.add(hbBotones, 1, 10);
        
        GridPane gpTipoSeleccion = new GridPane();
        gpTipoSeleccion.setPadding(new Insets(5, 5, 5, 5));
        gpTipoSeleccion.setVgap(5);
        gpTipoSeleccion.setHgap(5);
        gpTipoSeleccion.add(lbTipoBusqueda, 0, 0);
        gpTipoSeleccion.add(rbTodos, 0, 1);
        gpTipoSeleccion.add(rbCodigo, 1, 1);
        gpTipoSeleccion.add(tfBuscarCodigo, 1, 2);
        gpTipoSeleccion.add(rbDescripcion, 2, 1);
        gpTipoSeleccion.add(tfBuscarDescripcion, 2, 2);
        gpTipoSeleccion.add(rbCategoria, 3, 1);
        gpTipoSeleccion.add(cbBuscarCategoria, 3, 2);
        gpTipoSeleccion.add(btnSeleccionar, 4, 2);
        
        HBox hbBody = new HBox(tvProductos, gpPpal);
        hbBody.setPadding(new Insets(5, 5, 5, 5));
        
        hbBody.setSpacing(10);
        vbPpal.getChildren().addAll(lbTitulo,gpTipoSeleccion, hbBody);
        
        return vbPpal;    
    }
    private VBox vEliminarProducto(){
        VBox vbPpal = new VBox();
        Label lbTitulo = new Label("E L I M I N A R  P R O D U C T O");
        lbTitulo.getStyleClass().add("titulo-vista");
        lbTitulo.setAlignment(Pos.CENTER);
        
        //idProducto,CodigoProducto,Existencia,Descripcion,UnidadMedida,PrecioContado,Precio_crediContado,Precio_credito,Proveedor,idCategoria,

        //Componentes busqueda de productos
        Label lbTipoBusqueda = new Label("Buscar por: ");
        
        ToggleGroup tgBusquedas = new ToggleGroup();
        
        RadioButton rbTodos = new RadioButton("Todos");
        RadioButton rbCodigo = new RadioButton("Codigo");
        RadioButton rbDescripcion = new RadioButton("Descripción");
        RadioButton rbCategoria = new RadioButton("Categoria");
        
        rbTodos.setSelected(true);
        
        rbTodos.setToggleGroup(tgBusquedas);
        rbCodigo.setToggleGroup(tgBusquedas);
        rbDescripcion.setToggleGroup(tgBusquedas);
        rbCategoria.setToggleGroup(tgBusquedas);
        
        TextField tfBuscarCodigo = new TextField();
        TextField tfBuscarDescripcion = new TextField();
        tfBuscarDescripcion.setPrefWidth(250);
        ComboBox cbBuscarCategoria = new ComboBox();
        cbBuscarCategoria.setPrefWidth(180);
        
        Button btnSeleccionar = new Button("Seleccionar Productos");
        
        //Datos del producto
        
        Label lbDatProduc = new Label ("Datos de Producto: ");
        //Label lbidProducto = new Label ("idProducto");
        Label lbCodigoProducto = new Label ("CodigoProducto");
        Label lbExistencia = new Label ("Existencia");
        Label lbDescripcion = new Label ("Descripcion");
        Label lbUnidadMedida = new Label ("UnidadMedida");
        Label lbPrecioContado = new Label ("PrecioContado");
        Label lbPrecio_crediContado = new Label ("Precio_crediContado");
        Label lbPrecio_credito = new Label ("Precio_credito");
        Label lbProveedor = new Label ("Proveedor");
        Label lbidCategoria = new Label ("idCategoria");    
        
        //TextField tfidProducto = new TextField();
        TextField tfCodigoProducto = new TextField();
        tfCodigoProducto.setPrefWidth(110);
        tfCodigoProducto.setMaxWidth(110);
        tfCodigoProducto.setEditable(false);
        
        TextField tfExistencia = new TextField();
        tfExistencia.setPrefWidth(80);
        tfExistencia.setMaxWidth(80);
        tfExistencia.setEditable(false);
        
        TextField tfDescripcion = new TextField();
        tfDescripcion.setPrefWidth(350);
        tfDescripcion.setMaxWidth(350);
        tfDescripcion.setEditable(false);
        
        TextField tfUnidadMedida = new TextField("Pza.");
        tfUnidadMedida.setPrefWidth(60);
        tfUnidadMedida.setMaxWidth(60);
        tfUnidadMedida.setEditable(false);
        
        TextField tfPrecioContado = new TextField();
        tfPrecioContado.setPrefWidth(120);
        tfPrecioContado.setMaxWidth(120);
        tfPrecioContado.setEditable(false);
        
        TextField tfPrecio_crediContado = new TextField();
        tfPrecio_crediContado.setPrefWidth(120);
        tfPrecio_crediContado.setMaxWidth(120);
        tfPrecio_crediContado.setEditable(false);
        
        TextField tfPrecio_credito = new TextField();
        tfPrecio_credito.setPrefWidth(120);
        tfPrecio_credito.setMaxWidth(120);
        tfPrecio_credito.setEditable(false);
        
        TextField tfProveedor = new TextField();
        tfProveedor.setPrefWidth(220);
        tfProveedor.setMaxWidth(220);
        tfProveedor.setEditable(false);
        
        TextField tfidCategoria = new TextField();
        tfidCategoria.setPrefWidth(220);
        tfidCategoria.setMaxWidth(220);
        tfidCategoria.setEditable(false);
        
        TableView tvProductos = new TableView();
        tvProductos.setPrefSize(500, 300);
        
        TableColumn<inventario, String> idProductoColumna = new TableColumn<>("idProducto");
        idProductoColumna.setMinWidth(60);
        idProductoColumna.setCellValueFactory(new PropertyValueFactory<>("Id Producto "));        
        
        TableColumn<inventario, String> codigoColumna = new TableColumn<>("Codigo Producto");
        codigoColumna.setMinWidth(120);
        codigoColumna.setCellValueFactory(new PropertyValueFactory<>("CodigoProducto"));
        
        TableColumn<inventario, Float> existenciaColumna = new TableColumn<>("Existencia");
        existenciaColumna.setMinWidth(80);
        existenciaColumna.setCellValueFactory(new PropertyValueFactory<>("Existencia"));

        TableColumn<inventario, Float> descripcionColumna = new TableColumn<>("Descripcion");
        descripcionColumna.setMinWidth(180);
        descripcionColumna.setCellValueFactory(new PropertyValueFactory<>("Descripcion"));

        TableColumn<inventario, Float> unidadMedidaColumna = new TableColumn<>("U. Med.");
        unidadMedidaColumna.setMinWidth(60);
        unidadMedidaColumna.setCellValueFactory(new PropertyValueFactory<>("UnidadMedida"));

        TableColumn<inventario, Float> precioContadoColumna = new TableColumn<>("Precio Contado");
        precioContadoColumna.setMinWidth(80);
        precioContadoColumna.setCellValueFactory(new PropertyValueFactory<>("PrecioContado"));

        TableColumn<inventario, Float> crediContadoColumna = new TableColumn<>("P. Credi-Contado");
        crediContadoColumna.setMinWidth(80);
        crediContadoColumna.setCellValueFactory(new PropertyValueFactory<>("Precio_crediContado"));

        TableColumn<inventario, Float> creditoColumna = new TableColumn<>("Precio Credito");
        creditoColumna.setMinWidth(80);
        creditoColumna.setCellValueFactory(new PropertyValueFactory<>("Precio_credito"));

        TableColumn<inventario, Float> proveedorColumna = new TableColumn<>("Proveedor");
        proveedorColumna.setMinWidth(80);
        proveedorColumna.setCellValueFactory(new PropertyValueFactory<>("Proveedor"));

        TableColumn<inventario, Float> categoriaColumna = new TableColumn<>("id Categoria");
        categoriaColumna.setMinWidth(80);
        categoriaColumna.setCellValueFactory(new PropertyValueFactory<>("idCategoria"));
        
        tvProductos.getColumns().setAll(codigoColumna, existenciaColumna, descripcionColumna, unidadMedidaColumna, precioContadoColumna,
                crediContadoColumna, creditoColumna, proveedorColumna, categoriaColumna);
        tvProductos.setOnMouseClicked((event) -> {
            invDTO = (inventario)tvProductos.getSelectionModel().getSelectedItem();
            tfCodigoProducto.setText(invDTO.getCodigoProducto());
            tfDescripcion.setText(invDTO.getDescripcion());
            tfExistencia.setText(String.valueOf(invDTO.getExistencia()));
            tfUnidadMedida.setText(invDTO.getUnidadMedida());
            tfPrecioContado.setText(String.valueOf(invDTO.getPrecioContado()));
            tfPrecio_crediContado.setText(String.valueOf(invDTO.getPrecio_crediContado()));
            tfPrecio_credito.setText(String.valueOf(invDTO.getPrecio_credito()));
            tfProveedor.setText(invDTO.getProveedor());
            tfidCategoria.setText(invDTO.getIdCategoria());
        });

        lstWhere.clear();
        lstWhere.add("idProducto is not null");
        //lstInventario = invDAO.consultarInventario(lstWhere);
        tvProductos.setItems(FXCollections.observableArrayList(invDAO.consultarInventario(lstWhere)));
        
        btnSeleccionar.setOnMouseClicked((event) -> {
            if(rbTodos.isSelected()){
                if(!tvProductos.getItems().isEmpty()) tvProductos.getItems().clear();
                lstWhere.clear();
                lstWhere.add("idProducto is not null");
                //lstInventario = invDAO.consultarInventario(lstWhere);
                tvProductos.setItems(FXCollections.observableArrayList(invDAO.consultarInventario(lstWhere)));
            }
            if (rbCodigo.isSelected()){
                if(!tvProductos.getItems().isEmpty()) tvProductos.getItems().clear();
                lstWhere.clear();
                lstWhere.add("CodigoProducto ='"+tfBuscarCodigo.getText()+"' ");
                tvProductos.setItems(FXCollections.observableArrayList(invDAO.consultarInventario(lstWhere)));
            }
            if (rbDescripcion.isSelected()){
                if(!tvProductos.getItems().isEmpty()) tvProductos.getItems().clear();
                lstWhere.clear();
                lstWhere.add("Descripcion like '%"+tfBuscarDescripcion.getText()+"%' ");
                tvProductos.setItems(FXCollections.observableArrayList(invDAO.consultarInventario(lstWhere)));
            }
        });
        
        Button btnSalir = new Button("Salir");
        btnSalir.setOnMouseClicked((event) -> {
            removerVistas();
        });
        
        Button btnEliminar = new Button("Eliminar");
        btnEliminar.setOnAction((event) -> {
            
                Alert alertMessage = new Alert(Alert.AlertType.CONFIRMATION);
                alertMessage.setTitle("CONFIRMATION");
                alertMessage.setContentText("¿Estas Seguro Eliminar el producto?");
                Optional<ButtonType> action = alertMessage.showAndWait();
                if (action.get() == ButtonType.OK) {
                    invDAO.eliminarProducto(invDTO.getIdProducto());
                    vbAreaTrabajo.getChildren().clear();
                    vbAreaTrabajo.getChildren().add(vModificarProducto());
                } else {
                    //removerVistas();
                }                
        });
        
        HBox hbBotones = new HBox(btnSalir, btnEliminar);
        hbBotones.setSpacing(10);
        
        HBox hbCodigoProd = new HBox(tfCodigoProducto);
        hbCodigoProd.setSpacing(10);
        
        GridPane gpPpal = new GridPane();
        gpPpal.setPadding(new Insets(5, 5, 5, 5));
        gpPpal.setVgap(10);
        gpPpal.setHgap(10);
        gpPpal.add(lbDatProduc, 0, 0);
        gpPpal.add(lbCodigoProducto, 0, 1);
        gpPpal.add(hbCodigoProd, 1, 1);
        gpPpal.add(lbDescripcion, 0, 2);
        gpPpal.add(tfDescripcion, 1, 2);
        gpPpal.add(lbExistencia, 0, 3);
        gpPpal.add(tfExistencia, 1, 3);
        gpPpal.add(lbUnidadMedida, 0, 4);
        gpPpal.add(tfUnidadMedida, 1, 4);
        gpPpal.add(lbPrecioContado, 0, 5);
        gpPpal.add(tfPrecioContado, 1, 5);
        gpPpal.add(lbPrecio_crediContado, 0, 6);
        gpPpal.add(tfPrecio_crediContado, 1, 6);
        gpPpal.add(lbPrecio_credito, 0, 7);
        gpPpal.add(tfPrecio_credito, 1, 7);
        gpPpal.add(lbProveedor, 0, 8);
        gpPpal.add(tfProveedor, 1, 8);
        gpPpal.add(lbidCategoria, 0, 9);
        gpPpal.add(tfidCategoria, 1, 9);
        gpPpal.add(hbBotones, 1, 10);
        
        GridPane gpTipoSeleccion = new GridPane();
        gpTipoSeleccion.setPadding(new Insets(5, 5, 5, 5));
        gpTipoSeleccion.setVgap(5);
        gpTipoSeleccion.setHgap(5);
        gpTipoSeleccion.add(lbTipoBusqueda, 0, 0);
        gpTipoSeleccion.add(rbTodos, 0, 1);
        gpTipoSeleccion.add(rbCodigo, 1, 1);
        gpTipoSeleccion.add(tfBuscarCodigo, 1, 2);
        gpTipoSeleccion.add(rbDescripcion, 2, 1);
        gpTipoSeleccion.add(tfBuscarDescripcion, 2, 2);
        gpTipoSeleccion.add(rbCategoria, 3, 1);
        gpTipoSeleccion.add(cbBuscarCategoria, 3, 2);
        gpTipoSeleccion.add(btnSeleccionar, 4, 2);
        
        HBox hbBody = new HBox(tvProductos, gpPpal);
        hbBody.setPadding(new Insets(5, 5, 5, 5));
        
        hbBody.setSpacing(10);
        vbPpal.getChildren().addAll(lbTitulo,gpTipoSeleccion, hbBody);
        
        return vbPpal;    
    }
    
    //Modulos de Gastos
    private VBox vRegistrarGastos() {
        Label lbTitulo = new Label("R E G I S T R A R  G A S T O S");
        lbTitulo.getStyleClass().add("titulo-vista");
        lbTitulo.setAlignment(Pos.CENTER);

        //Datos de Gastos
        Label lbDatosGasto = new Label("Datos de Gastos");
        Label lbDescripcion = new Label("Descripcion: ");
        Label lbFecha = new Label("Fecha: ");
        Label lbMonto = new Label("monto: ");

        TextField tfDescripcion = new TextField();
        tfDescripcion.setPrefWidth(180);
        DatePicker dpFecha = new DatePicker(LocalDate.now());
        TextField tfMonto = new TextField();
        tfMonto.setPrefWidth(120);

        VBox vbDatosGasto = new VBox();
        vbDatosGasto.setAlignment(Pos.CENTER);
        vbDatosGasto.getChildren().add(lbTitulo);
        vbDatosGasto.getStyleClass().add("vbox-vistas");

        GridPane gpDatosGasto = new GridPane();
        gpDatosGasto.setPadding(new Insets(5, 5, 5, 5));
        gpDatosGasto.setVgap(25);
        gpDatosGasto.setHgap(25);
        gpDatosGasto.add(lbDatosGasto, 0, 0);
        gpDatosGasto.add(lbDescripcion, 0, 1);
        gpDatosGasto.add(tfDescripcion, 1, 1);
        gpDatosGasto.add(lbFecha, 0, 2);
        gpDatosGasto.add(dpFecha, 1, 2);
        gpDatosGasto.add(lbMonto, 0, 3);
        gpDatosGasto.add(tfMonto, 1, 3);

        Button btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction((event) -> {
            int result = gasDAO.insertarGasto(tfDescripcion.getText(), dpFecha.getValue().toString(), Float.parseFloat(tfMonto.getText()));
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Confirmación");
            alert.setContentText("Gasto Registrado con el ID #"+result+"\n¿Deseas agregar otro?");
            Optional<ButtonType> action = alert.showAndWait();
            if(action.get()== ButtonType.OK){
                vbAreaTrabajo.getChildren().clear();
                vbAreaTrabajo.getChildren().add(vRegistrarGastos());
            }
            else{
                removerVistas();
            }
        });
        
        Button btnSalir = new Button("Salir");
        btnSalir.setOnAction((event) -> {
            removerVistas();
        });

        GridPane gpBotonesControl = new GridPane();
        gpBotonesControl.setPadding(new Insets(5, 5, 5, 5));
        gpBotonesControl.setVgap(25);
        gpBotonesControl.setHgap(25);
        gpBotonesControl.add(btnSalir, 0, 0);
        gpBotonesControl.add(btnGuardar, 1, 0);

        vbDatosGasto.getChildren().addAll(gpDatosGasto, gpBotonesControl);

        return vbDatosGasto;
    }
    private VBox vModificarGastos(){
         Label lbTitulo = new Label("M O D I F I C A R  G A S T O S");
         lbTitulo.getStyleClass().add("titulo-vista");
         lbTitulo.setPrefWidth(anchoPantalla);
         lbTitulo.setAlignment(Pos.CENTER);
         
         //Tipo busqueda
         ToggleGroup rgTipoBusquedas = new ToggleGroup();
         
         RadioButton rbBuscarTodos = new RadioButton("Todos");
         rbBuscarTodos.setSelected(true);
         rbBuscarTodos.setToggleGroup(rgTipoBusquedas);
         
         RadioButton rbBuscarDescripcion = new RadioButton("Por Descripcion");
         rbBuscarDescripcion.setToggleGroup(rgTipoBusquedas);
         
         RadioButton rbBuscarFecha = new RadioButton("Por Fecha");
         rbBuscarFecha.setToggleGroup(rgTipoBusquedas);

         RadioButton rbBuscarMonto = new RadioButton("Por Monto");
         rbBuscarMonto.setToggleGroup(rgTipoBusquedas);
         
         Label lbBuscarPor = new Label("Buscar: ");
         
         TextField tfBuscarDescripcion = new TextField();
         DatePicker dpBuscarFecha = new DatePicker(LocalDate.now());
         TextField tfBuscarMonto = new TextField();
         
         Button btnSeleccionar = new Button("Seleccionar");
                  
        //Datos de Gasto
        Label lbDatosGasto = new Label("Datos de Gasto");
        Label lbDescripcion = new Label("Descripcion: ");
        Label lbFecha = new Label("Fecha: ");
        Label lbMonto = new Label("Monto: ");
        
        TextField tfDescripcion = new TextField();
        tfDescripcion.setPrefWidth(180);
        DatePicker dpFecha = new DatePicker(LocalDate.now());
        dpFecha.setPrefWidth(250);
        TextField tfMonto = new TextField();
        tfMonto.setPrefWidth(180);
        
        //Tabla Gastos
       
        Label lbTablaGastos = new Label("Gastos Seleccionados:");
        TableView tvGastos = new TableView();
        tvGastos.setPrefWidth(1150);
         
        TableColumn idGastoColum = new TableColumn("ID");
        idGastoColum.setCellValueFactory(new PropertyValueFactory<>("idGasto"));
        idGastoColum.setPrefWidth(120);
        
        TableColumn descripcionColumna = new TableColumn("Descripcion");
        descripcionColumna.setCellValueFactory(new PropertyValueFactory<>("Descripcion"));
        descripcionColumna.setPrefWidth(260);
        
        TableColumn fechaColum = new TableColumn("Fecha");
        fechaColum.setCellValueFactory(new PropertyValueFactory<>("Fecha"));
        fechaColum.setPrefWidth(250);

        TableColumn montoColum = new TableColumn("Monto");
        montoColum.setCellValueFactory(new PropertyValueFactory<>("monto"));
        montoColum.setPrefWidth(250);
                
        tvGastos.getColumns().addAll(idGastoColum, descripcionColumna, fechaColum, montoColum);
        
        lstWhere.clear();
        lstWhere.add("idGasto is not null");
        tvGastos.setItems(FXCollections.observableArrayList(gasDAO.consultarGastos(lstWhere)));
        
        tvGastos.setOnMouseClicked((event) -> {
            gasDTO = (gasto) tvGastos.getSelectionModel().getSelectedItem();
            tfDescripcion.setText(gasDTO.getDescripcion());
            dpFecha.setValue(LocalDate.parse(gasDTO.getFecha()));
            tfMonto.setText(gasDTO.getMonto());
        });
        
        btnSeleccionar.setOnAction((event) -> {
            if (rbBuscarTodos.isSelected()){
                    lstWhere.clear();
                    lstWhere.add("idGasto is not null");
                    tvGastos.setItems(FXCollections.observableArrayList(gasDAO.consultarGastos(lstWhere)));
            }
            if (rbBuscarDescripcion.isSelected()){
                    lstWhere.clear();
                    lstWhere.add("Descripcion like '%"+tfBuscarDescripcion.getText()+"%' ");
                    tvGastos.setItems(FXCollections.observableArrayList(gasDAO.consultarGastos(lstWhere)));
            }
            if (rbBuscarFecha.isSelected()){
                    lstWhere.clear();
                    lstWhere.add("Fecha like '%"+dpBuscarFecha.getValue().toString()+"%' ");
                    tvGastos.setItems(FXCollections.observableArrayList(gasDAO.consultarGastos(lstWhere)));
            }
            if (rbBuscarMonto.isSelected()){
                    lstWhere.clear();
                    lstWhere.add("monto = "+tfBuscarMonto.getText()+"%' ");
                    tvGastos.setItems(FXCollections.observableArrayList(gasDAO.consultarGastos(lstWhere)));
            }

        });
        
        VBox vbDatosGasto = new VBox();
        vbDatosGasto.setAlignment(Pos.TOP_LEFT);
        vbDatosGasto.getChildren().add(lbTitulo);
        vbDatosGasto.getStyleClass().add("vbox-vistas");
        
        GridPane gpSeleccionarGasto = new GridPane();
        gpSeleccionarGasto.setPadding(new Insets(5,5,5,5));
        gpSeleccionarGasto.setVgap(5);
        gpSeleccionarGasto.setHgap(5);
        gpSeleccionarGasto.add(lbBuscarPor, 0, 0);
        gpSeleccionarGasto.add(rbBuscarTodos, 0, 1);
        gpSeleccionarGasto.add(rbBuscarDescripcion, 1, 1);
        gpSeleccionarGasto.add(tfBuscarDescripcion, 1, 2);
        gpSeleccionarGasto.add(rbBuscarFecha, 2, 1);
        gpSeleccionarGasto.add(dpBuscarFecha, 2, 2);
        gpSeleccionarGasto.add(rbBuscarMonto, 3, 1);
        gpSeleccionarGasto.add(tfBuscarMonto, 3, 2);
        gpSeleccionarGasto.add(btnSeleccionar, 4, 2);
        
        GridPane gpDatosGasto = new GridPane();
        gpDatosGasto.setPadding(new Insets(5,5,5,5));
        gpDatosGasto.setVgap(25);
        gpDatosGasto.setHgap(25);
        gpDatosGasto.add(lbDatosGasto, 0, 0);
        gpDatosGasto.add(lbDescripcion, 0, 1);
        gpDatosGasto.add(tfDescripcion, 1, 1);
        gpDatosGasto.add(lbFecha, 0, 2);
        gpDatosGasto.add(dpFecha, 1, 2);
        gpDatosGasto.add(lbMonto, 0, 3);
        gpDatosGasto.add(tfMonto, 1, 3);
        
        VBox vbTablaGasto = new VBox();
        vbTablaGasto.setPadding(new Insets(5,5,5,5));
        vbTablaGasto.setSpacing(5);
        vbTablaGasto.setPrefWidth(1150);
        vbTablaGasto.setMaxWidth(1150);
        vbTablaGasto.setMinWidth(1150);
        vbTablaGasto.setAlignment(Pos.TOP_LEFT);
        vbTablaGasto.getChildren().addAll(lbTablaGastos, tvGastos);
        
        Button btnModificar = new Button("Modificar");
        btnModificar.setOnAction((event) -> {
            gasDAO.modificarGasto(gasDTO.getIdGasto(), tfDescripcion.getText(), dpFecha.getValue().toString(), Float.parseFloat(tfMonto.getText()));
            int result = 1;
            if (result != -1) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setTitle("Confirmación");
                alert.setContentText("Datos Actualizados del Gasto \n¿Deseas modificar otro?");
                Optional<ButtonType> action = alert.showAndWait();
                if (action.get() == ButtonType.OK) {
                    //tfDescripcion.setText("");
                    //dpFecha.setValue(null);
                    //tfMonto.setText("");
                    vbAreaTrabajo.getChildren().clear();
                    vbAreaTrabajo.getChildren().add(vModificarGastos());
                } else {
                    removerVistas();
                }
            }
        });
        
        Button btnSalir = new Button("Salir");
        btnSalir.setOnAction((event) -> {
            removerVistas();
        });
        
        GridPane gpBotonesControl = new GridPane();
        gpBotonesControl.setPadding(new Insets(5,5,5,5));
        gpBotonesControl.setVgap(25);
        gpBotonesControl.setHgap(25);
        gpBotonesControl.add(btnSalir, 0, 0);
        gpBotonesControl.add(btnModificar, 1, 0);
        
        vbDatosGasto.getChildren().addAll(gpSeleccionarGasto, vbTablaGasto, gpDatosGasto, gpBotonesControl);
        
        return vbDatosGasto;
    }
    private VBox vEliminarGastos(){
         Label lbTitulo = new Label("E L I M I N A R   G A S T O S");
         lbTitulo.getStyleClass().add("titulo-vista");
         lbTitulo.setPrefWidth(anchoPantalla);
         lbTitulo.setAlignment(Pos.CENTER);
         
         //Tipo busqueda
         ToggleGroup rgTipoBusquedas = new ToggleGroup();
         
         RadioButton rbBuscarTodos = new RadioButton("Todos");
         rbBuscarTodos.setSelected(true);
         rbBuscarTodos.setToggleGroup(rgTipoBusquedas);
         
         RadioButton rbBuscarDescripcion = new RadioButton("Por Descripcion");
         rbBuscarDescripcion.setToggleGroup(rgTipoBusquedas);
         
         RadioButton rbBuscarFecha = new RadioButton("Por Fecha");
         rbBuscarFecha.setToggleGroup(rgTipoBusquedas);

         RadioButton rbBuscarMonto = new RadioButton("Por Monto");
         rbBuscarMonto.setToggleGroup(rgTipoBusquedas);

         Label lbBuscarPor = new Label("Buscar: ");
         
         TextField tfBuscarDescripcion = new TextField();
         DatePicker dpBuscarFecha = new DatePicker(LocalDate.now());
         TextField tfBuscarMonto = new TextField();
         
         Button btnSeleccionar = new Button("Seleccionar");
                  
        //Datos de Gastos
        Label lbDatosGasto = new Label("Datos de Gasto");
        Label lbDescripcion = new Label("Descripcion: ");
        Label lbFecha = new Label("Fecha: ");
        Label lbMonto = new Label("Monto: ");
        
        TextField tfDescripcion = new TextField();
        tfDescripcion.setPrefWidth(180);
        DatePicker dpFecha = new DatePicker(LocalDate.now());
        dpFecha.setPrefWidth(250);
        TextField tfMonto = new TextField();
        tfMonto.setPrefWidth(180);
        
        //Tabla Gastos
       
        Label lbTablaGastos = new Label("Gastos Seleccionados:");
        TableView tvGastos = new TableView();
        tvGastos.setPrefWidth(1150);
         
        TableColumn idGastoColum = new TableColumn("ID");
        idGastoColum.setCellValueFactory(new PropertyValueFactory<>("idGasto"));
        idGastoColum.setPrefWidth(120);
        
        TableColumn descripcionColumna = new TableColumn("Descripcion");
        descripcionColumna.setCellValueFactory(new PropertyValueFactory<>("Descripcion"));
        descripcionColumna.setPrefWidth(260);
        
        TableColumn fechaColum = new TableColumn("Fecha");
        fechaColum.setCellValueFactory(new PropertyValueFactory<>("Fecha"));
        fechaColum.setPrefWidth(250);

        TableColumn montoColum = new TableColumn("Monto");
        montoColum.setCellValueFactory(new PropertyValueFactory<>("monto"));
        montoColum.setPrefWidth(250);
        TableColumn telefonoColum = new TableColumn("Telefono");
        telefonoColum.setCellValueFactory(new PropertyValueFactory<>("Telefono"));
        telefonoColum.setPrefWidth(250);
        
        tvGastos.getColumns().addAll(idGastoColum, descripcionColumna, fechaColum, montoColum);
        
        lstWhere.clear();
        lstWhere.add("idGasto is not null");
        tvGastos.setItems(FXCollections.observableArrayList(gasDAO.consultarGastos(lstWhere)));
        
        tvGastos.setOnMouseClicked((event) -> {
            gasDTO = (gasto) tvGastos.getSelectionModel().getSelectedItem();
            tfDescripcion.setText(gasDTO.getDescripcion());
            dpFecha.setValue(LocalDate.parse(gasDTO.getFecha()));
            tfMonto.setText(gasDTO.getMonto());
        });
        
        btnSeleccionar.setOnAction((event) -> {
            if (rbBuscarTodos.isSelected()){
                    lstWhere.clear();
                    lstWhere.add("idGasto is not null");
                    tvGastos.setItems(FXCollections.observableArrayList(gasDAO.consultarGastos(lstWhere)));
            }
            if (rbBuscarDescripcion.isSelected()){
                    lstWhere.clear();
                    lstWhere.add("Descripcion like '%"+tfBuscarDescripcion.getText()+"%' ");
                    tvGastos.setItems(FXCollections.observableArrayList(gasDAO.consultarGastos(lstWhere)));
            }
            if (rbBuscarFecha.isSelected()){
                    lstWhere.clear();
                    lstWhere.add("Fecha like '%"+dpBuscarFecha.getValue().toString()+"%' ");
                    tvGastos.setItems(FXCollections.observableArrayList(gasDAO.consultarGastos(lstWhere)));
            }
            if (rbBuscarMonto.isSelected()){
                    lstWhere.clear();
                    lstWhere.add("monto = "+tfBuscarMonto.getText()+"%' ");
                    tvGastos.setItems(FXCollections.observableArrayList(gasDAO.consultarGastos(lstWhere)));
            }

        });
        
        VBox vbDatosGasto = new VBox();
        vbDatosGasto.setAlignment(Pos.TOP_LEFT);
        vbDatosGasto.getChildren().add(lbTitulo);
        vbDatosGasto.getStyleClass().add("vbox-vistas");
        
        GridPane gpSeleccionarGasto = new GridPane();
        gpSeleccionarGasto.setPadding(new Insets(5,5,5,5));
        gpSeleccionarGasto.setVgap(5);
        gpSeleccionarGasto.setHgap(5);
        gpSeleccionarGasto.add(lbBuscarPor, 0, 0);
        gpSeleccionarGasto.add(rbBuscarTodos, 0, 1);
        gpSeleccionarGasto.add(rbBuscarDescripcion, 1, 1);
        gpSeleccionarGasto.add(tfBuscarDescripcion, 1, 2);
        gpSeleccionarGasto.add(rbBuscarFecha, 2, 1);
        gpSeleccionarGasto.add(dpBuscarFecha, 2, 2);
        gpSeleccionarGasto.add(rbBuscarMonto, 3, 1);
        gpSeleccionarGasto.add(tfBuscarMonto, 3, 2);
        gpSeleccionarGasto.add(btnSeleccionar, 4, 2);
        
        GridPane gpDatosGasto = new GridPane();
        gpDatosGasto.setPadding(new Insets(5,5,5,5));
        gpDatosGasto.setVgap(25);
        gpDatosGasto.setHgap(25);
        gpDatosGasto.add(lbDatosGasto, 0, 0);
        gpDatosGasto.add(lbDescripcion, 0, 1);
        gpDatosGasto.add(tfDescripcion, 1, 1);
        gpDatosGasto.add(lbFecha, 0, 2);
        gpDatosGasto.add(dpFecha, 1, 2);
        gpDatosGasto.add(lbMonto, 0, 3);
        gpDatosGasto.add(tfMonto, 1, 3);
        
        VBox vbTablaGasto = new VBox();
        vbTablaGasto.setPadding(new Insets(5,5,5,5));
        vbTablaGasto.setSpacing(5);
        vbTablaGasto.setPrefWidth(1150);
        vbTablaGasto.setMaxWidth(1150);
        vbTablaGasto.setMinWidth(1150);
        vbTablaGasto.setAlignment(Pos.TOP_LEFT);
        vbTablaGasto.getChildren().addAll(lbTablaGastos, tvGastos);
        
        Button btnEliminar = new Button("Eliminar");
        btnEliminar.setOnAction((event) -> {
            gasDAO.eliminarGasto(gasDTO.getIdGasto());
            int result = 1;
            if (result != -1) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setTitle("Confirmación");
                alert.setContentText("Gasto Eliminado \n¿Deseas eliminar otro?");
                Optional<ButtonType> action = alert.showAndWait();
                if (action.get() == ButtonType.OK) {
                    //tfDescripcion.setText("");
                    //dpFecha.setValue(null);
                    //tfMonto.setText("");
                    vbAreaTrabajo.getChildren().clear();
                    vbAreaTrabajo.getChildren().add(vEliminarGastos());
                } else {
                    removerVistas();
                }
            }
        });
        
        Button btnSalir = new Button("Salir");
        btnSalir.setOnAction((event) -> {
            removerVistas();
        });
        
        GridPane gpBotonesControl = new GridPane();
        gpBotonesControl.setPadding(new Insets(5,5,5,5));
        gpBotonesControl.setVgap(25);
        gpBotonesControl.setHgap(25);
        gpBotonesControl.add(btnSalir, 0, 0);
        gpBotonesControl.add(btnEliminar, 1, 0);
        
        vbDatosGasto.getChildren().addAll(gpSeleccionarGasto, vbTablaGasto, gpDatosGasto, gpBotonesControl);
        
        return vbDatosGasto;
    }  
    
    //Modulos Herramientas y Usuarios
    private VBox vistaNuevoUsuario(){
        VBox vbPpal = new VBox();
        vbPpal.setSpacing(10);
        vbPpal.setAlignment(Pos.CENTER);
        Label lbTituloVista = new Label("NUEVO USUARIO");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);

        TableView tvUsuarios = new TableView();
        tvUsuarios.setPrefSize(706, 350);        
        TableColumn<usuario, String> colUsuario = new TableColumn("USUARIO");
        colUsuario.setMinWidth(100);
        colUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        TableColumn<usuario, String> colNombre = new TableColumn("NOMBRE");
        colNombre.setPrefWidth(200);
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        TableColumn<usuario, String> colDireccion = new TableColumn("DIRECCION");
        colDireccion.setPrefWidth(200);
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        TableColumn<usuario, String> colTelefono = new TableColumn("TELEFONO");
        colTelefono.setPrefWidth(100);
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        TableColumn<usuario, String> colPuesto = new TableColumn("PUESTO");
        colPuesto.setPrefWidth(100);
        colPuesto.setCellValueFactory(new PropertyValueFactory<>("puesto"));        
        tvUsuarios.getColumns().addAll(colUsuario, colNombre, colDireccion, colTelefono, colPuesto);

        lstWhere.clear();
        lstUsuarios.clear();
        lstUsuarios.addAll(usuDAO.consultaUsuario(lstWhere));        
        tvUsuarios.setItems(lstUsuarios);
        
	Label lbUsuario  = new Label("Usuario: ");
        TextField tfUsuario = new TextField();
        tfUsuario.setPrefWidth(200);
	Label lbNombre  = new Label("Nombre Usuario: ");
        TextField tfNombre = new TextField();
        tfNombre.setPrefWidth(200);
	Label lbTipoUsuario  = new Label("Tipo Usuario: ");
        ObservableList<String> lstOpcionesTipoUsuario = FXCollections.observableArrayList("ADMINISTRADOR","SUPERVISOR");
        ComboBox cbTipoUsuario = new ComboBox(lstOpcionesTipoUsuario);
        cbTipoUsuario.setPrefWidth(200);
	Label lbContrasena  = new Label("Contraseña: ");
        PasswordField tfContrasena = new PasswordField();
        tfContrasena.setPrefWidth(200);
        tfContrasena.setPromptText("********");
	Label lbConfirmarContrasena  = new Label("Confirmar Contraseña: ");
        PasswordField tfCContrasena = new PasswordField();
        tfCContrasena.setPrefWidth(200);
        tfCContrasena.setPromptText("********");
        Label lbDirec = new Label("Dirección:");
        TextField tfDirec = new TextField();
        tfDirec.setPrefWidth(200);
        Label lbTel = new Label("Teléfono:");       
        TextField tfTel = new TextField();
        tfTel.setPrefWidth(200);
        
        Button btnSalir = new Button("  Salir  ");
        btnSalir.setOnAction((ActionEvent e)->{
            removerVistas();
        });
                
        Button btnAgregar = new Button(" Agregar ");
        btnAgregar.setOnAction((ActionEvent e)->{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Aviso");
            alert.setContentText("¿Desea crear el usuario indicado?");
            Optional<ButtonType> action = alert.showAndWait(); 
            if (action.get() == ButtonType.OK) {
                Alert aviso = new Alert(Alert.AlertType.WARNING);
                aviso.setHeaderText(null);
                aviso.setTitle("Aviso");
                if (tfUsuario.getText().isEmpty() || tfNombre.getText().isEmpty() || tfContrasena.getText().isEmpty() || tfCContrasena.getText().isEmpty()){
                   aviso.setContentText("¡Faltan datos!");
                   action = aviso.showAndWait();
                } else {
                    if (tfContrasena.getText().equals(tfCContrasena.getText())) {
                        usuario usua = new usuario();
                        
                        usua.setNombre(tfNombre.getText());
                        usua.setUsuario(tfUsuario.getText());
                        usua.setClaveAcceso(tfContrasena.getText());
                        usua.setPuesto(cbTipoUsuario.getValue().toString());
                        usua.setDireccion(tfDirec.getText());
                        usua.setTelefono(tfTel.getText());
                        usua.setEstatus("ACTIVO");
                        
                        lstUsuarios.add(usua);
                        usuDAO.insertarUsuario(usua);
                        
                        aviso.setContentText("Datos guardados");                                               
                        action = aviso.showAndWait();                        
                        tfUsuario.setText("");
                        tfNombre.setText("");
                        tfContrasena.setText("");
                        tfCContrasena.setText("");
                        tfDirec.setText("");
                        tfTel.setText("");                        
                    } else {
                        aviso.setContentText("¡La contraseña no coincide!");
                        action = aviso.showAndWait();
                    }
                }
            } 
        });

        HBox hbTablaDatos = new HBox();
        hbTablaDatos.setAlignment(Pos.CENTER);
        hbTablaDatos.getChildren().addAll(tvUsuarios);
        hbTablaDatos.setMaxWidth(750);
        
        HBox hbBotones = new HBox();
        hbBotones.setAlignment(Pos.CENTER);
        hbBotones.setSpacing(5);
        hbBotones.getChildren().addAll(btnSalir, btnAgregar);
        
        GridPane gpUsuario = new GridPane();
        gpUsuario.setAlignment(Pos.CENTER);
        gpUsuario.setPadding(new Insets(5, 5, 5, 5));
        gpUsuario.setVgap(10);
        gpUsuario.setHgap(10);
        
        gpUsuario.add(lbUsuario,0,0);
        gpUsuario.add(tfUsuario,1,0);
        gpUsuario.add(lbNombre, 0, 1);
        gpUsuario.add(tfNombre, 1, 1);
        gpUsuario.add(lbTipoUsuario, 2, 1);
        gpUsuario.add(cbTipoUsuario, 3, 1);
        
        gpUsuario.add(lbContrasena, 0, 2);
        gpUsuario.add(tfContrasena, 1, 2);
        gpUsuario.add(lbConfirmarContrasena, 2, 2);
        gpUsuario.add(tfCContrasena, 3, 2);
                
        gpUsuario.add(lbDirec, 0, 3);
        gpUsuario.add(tfDirec, 1, 3);
        gpUsuario.add(lbTel, 2, 3);
        gpUsuario.add(tfTel, 3, 3);
                
        vbPpal.getChildren().addAll(lbTituloVista, hbTablaDatos, gpUsuario, hbBotones);
        
        return vbPpal;
    } 
    private VBox vistaModificarUsuario(){
        VBox vbPpal = new VBox();
        vbPpal.setSpacing(10);
        vbPpal.setAlignment(Pos.CENTER);
        Label lbTituloVista = new Label("MODIFICAR USUARIO");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);

        TableView tvUsuarios = new TableView();
        tvUsuarios.setPrefSize(706, 350);        
        TableColumn<usuario, String> colUsuario = new TableColumn("USUARIO");
        colUsuario.setMinWidth(100);
        colUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        TableColumn<usuario, String> colNombre = new TableColumn("NOMBRE");
        colNombre.setPrefWidth(200);
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        TableColumn<usuario, String> colDireccion = new TableColumn("DIRECCION");
        colDireccion.setPrefWidth(200);
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        TableColumn<usuario, String> colTelefono = new TableColumn("TELEFONO");
        colTelefono.setPrefWidth(100);
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        TableColumn<usuario, String> colPuesto = new TableColumn("PUESTO");
        colPuesto.setPrefWidth(100);
        colPuesto.setCellValueFactory(new PropertyValueFactory<>("puesto"));
        tvUsuarios.getColumns().addAll(colUsuario, colNombre, colDireccion, colTelefono, colPuesto);
        
        lstWhere.clear();
        lstUsuarios.clear();
        lstUsuarios.addAll(usuDAO.consultaUsuario(lstWhere));        
        tvUsuarios.setItems(lstUsuarios);
        
	Label lbUsuario  = new Label("Usuario: ");
        TextField tfUsuario = new TextField();
        tfUsuario.setPrefWidth(200);
	Label lbNombre  = new Label("Nombre Usuario: ");
        TextField tfNombre = new TextField();
        tfNombre.setPrefWidth(200);
	Label lbTipoUsuario  = new Label("Tipo Usuario: ");
        ObservableList<String> lstOpcionesTipoUsuario = FXCollections.observableArrayList("ADMINISTRADOR","GERENTE", "EJECUTIVO", "CAPTURISTA","DIFUSION");
        ComboBox cbTipoUsuario = new ComboBox(lstOpcionesTipoUsuario);
        cbTipoUsuario.setPrefWidth(200);
	Label lbContrasena  = new Label("Contraseña: ");
        PasswordField tfContrasena = new PasswordField();
        tfContrasena.setPrefWidth(200);
	Label lbContrasena2  = new Label("Confirmar Contraseña: ");
        PasswordField tfContrasena2 = new PasswordField();
        tfContrasena2.setPrefWidth(200);
	Label lbDireccion  = new Label("Direccion: ");
        TextField tfDireccion = new TextField();
        tfDireccion.setPrefWidth(200);
	Label lbTelefono  = new Label("Telefono: ");
        TextField tfTelefono = new TextField();
        tfTelefono.setPrefWidth(200);

        tvUsuarios.setOnMouseClicked((event) -> {
            usuario modUsuario = (usuario) tvUsuarios.getSelectionModel().getSelectedItem();
            tfUsuario.setText(modUsuario.getUsuario());
            tfNombre.setText(modUsuario.getNombre());
            cbTipoUsuario.setValue(modUsuario.getPuesto());
            tfContrasena.setText(modUsuario.getClaveAcceso());
            tfContrasena2.setText(modUsuario.getClaveAcceso());
            tfDireccion.setText(modUsuario.getDireccion());
            tfTelefono.setText(modUsuario.getTelefono());
        });

        Button btnSalir = new Button("  Salir  ");
        btnSalir.setOnAction((ActionEvent e)->{
            removerVistas();
        });
        
        Button btnActualizar = new Button("Actualizar");
        btnActualizar.setOnAction((ActionEvent e)->{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Confirmación");
            alert.setContentText("¿Desea modificar el usuario seleccionado?");
            Optional<ButtonType> action = alert.showAndWait(); 
            if (action.get() == ButtonType.OK) {
                Alert aviso = new Alert(Alert.AlertType.WARNING);
                aviso.setHeaderText(null);
                aviso.setTitle("Aviso");
                if (tfUsuario.getText().isEmpty() || tfNombre.getText().isEmpty() || tfContrasena.getText().isEmpty() || tfContrasena2.getText().isEmpty()){
                   aviso.setContentText("¡Faltan datos!");
                   action = aviso.showAndWait();
                } else {
                    if (tfContrasena.getText().equals(tfContrasena2.getText())) {
                        usuario usua = new usuario();
                        usua = (usuario) tvUsuarios.getSelectionModel().getSelectedItem();
                        
                        usua.setNombre(tfNombre.getText());
                        usua.setUsuario(tfUsuario.getText());
                        usua.setClaveAcceso(tfContrasena.getText());
                        usua.setPuesto(cbTipoUsuario.getValue().toString());
                        usua.setDireccion(tfDireccion.getText());
                        usua.setTelefono(tfTelefono.getText());
                        usua.setEstatus("ACTIVO");
                        
                        tvUsuarios.refresh();                        
                        usuDAO.modificarUsuario(usua);
                        
                        aviso.setContentText("Datos actualizados");                                               
                        action = aviso.showAndWait();                        
                        tfUsuario.setText("");
                        tfNombre.setText("");
                        tfContrasena.setText("");
                        tfContrasena2.setText("");
                        tfDireccion.setText("");
                        tfTelefono.setText("");                        
                    } else {
                        aviso.setContentText("¡La contraseña no coincide!");
                        action = aviso.showAndWait();
                    }
                }
            } 
        });        
       
        HBox hbTablaDatos = new HBox();
        hbTablaDatos.setAlignment(Pos.CENTER);
        hbTablaDatos.getChildren().addAll(tvUsuarios);
        hbTablaDatos.setMaxWidth(750);
        
        HBox hbBotones = new HBox();
        hbBotones.setAlignment(Pos.CENTER);
        hbBotones.setSpacing(5);
        hbBotones.getChildren().addAll(btnSalir, btnActualizar);
        
        GridPane gpUsuario = new GridPane();
        gpUsuario.setAlignment(Pos.CENTER);
        gpUsuario.setPadding(new Insets(5, 5, 5, 5));
        gpUsuario.setVgap(10);
        gpUsuario.setHgap(10);
        
        gpUsuario.add(lbUsuario,0,0);
        gpUsuario.add(tfUsuario,1,0);
        
        gpUsuario.add(lbNombre, 0, 1);
        gpUsuario.add(tfNombre, 1, 1);
        gpUsuario.add(lbTipoUsuario, 2, 1);
        gpUsuario.add(cbTipoUsuario, 3, 1);
        
        gpUsuario.add(lbContrasena,0,2);
        gpUsuario.add(tfContrasena,1,2);
        gpUsuario.add(lbContrasena2,2,2);
        gpUsuario.add(tfContrasena2,3,2);
               
        gpUsuario.add(lbDireccion,0,3);
        gpUsuario.add(tfDireccion,1,3);
        gpUsuario.add(lbTelefono,2,3);
        gpUsuario.add(tfTelefono,3,3);
               
        vbPpal.getChildren().addAll(lbTituloVista, hbTablaDatos, gpUsuario, hbBotones);
        
        return vbPpal;
    }
    private VBox vistaEliminarUsuario(){
        VBox vbPpal = new VBox();
        vbPpal.setSpacing(10);
        vbPpal.setAlignment(Pos.CENTER);
        Label lbTituloVista = new Label("NUEVO USUARIO");
        Font fuente = new Font("Arial Bold", 36);
        lbTituloVista.setFont(fuente);

        TableView tvUsuarios = new TableView();
        tvUsuarios.setPrefSize(706, 350);        
        TableColumn<usuario, String> colUsuario = new TableColumn("USUARIO");
        colUsuario.setMinWidth(100);
        colUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        TableColumn<usuario, String> colNombre = new TableColumn("NOMBRE");
        colNombre.setPrefWidth(200);
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        TableColumn<usuario, String> colDireccion = new TableColumn("DIRECCION");
        colDireccion.setPrefWidth(200);
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        TableColumn<usuario, String> colTelefono = new TableColumn("TELEFONO");
        colTelefono.setPrefWidth(100);
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        TableColumn<usuario, String> colPuesto = new TableColumn("PUESTO");
        colPuesto.setPrefWidth(100);
        colPuesto.setCellValueFactory(new PropertyValueFactory<>("puesto"));
        tvUsuarios.getColumns().addAll(colUsuario, colNombre, colDireccion, colTelefono, colPuesto);
        
        lstWhere.clear();
        lstUsuarios.clear();
        lstUsuarios.addAll(usuDAO.consultaUsuario(lstWhere));        
        tvUsuarios.setItems(lstUsuarios);
        
        Button btnSalir = new Button("  Salir  ");
        btnSalir.setOnAction((ActionEvent e)->{
            removerVistas();
        });
        
        Button btnEliminar = new Button(" Eliminar ");
        btnEliminar.setOnAction((ActionEvent e)->{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Confirmación");
            alert.setContentText("¿Desea eliminar el usuario seleccionado?");
            Optional<ButtonType> action = alert.showAndWait();
            if (action.get() == ButtonType.OK){
                Alert aviso = new Alert(Alert.AlertType.WARNING);
                aviso.setHeaderText(null);
                aviso.setTitle("Aviso");
                if (tvUsuarios.getSelectionModel().isEmpty()){
                    aviso.setContentText("No se ha especificado el usuario a eliminar");
                    action = aviso.showAndWait();
                } else{
                    usuario elimUsua = new usuario();
                    elimUsua = (usuario) tvUsuarios.getSelectionModel().getSelectedItem();
                    
                    usuDAO.borrarUsuario(elimUsua.getUsuario());
                    aviso.setContentText("Usuario Eliminado");
                    action = aviso.showAndWait();
                    
                    lstUsuarios.remove(elimUsua);
                }                
            }
        });        

        HBox hbTablaDatos = new HBox();
        hbTablaDatos.setAlignment(Pos.CENTER);
        hbTablaDatos.getChildren().addAll(tvUsuarios);
        hbTablaDatos.setMaxWidth(750);
        
        HBox hbBotones = new HBox();
        hbBotones.setAlignment(Pos.CENTER);
        hbBotones.setSpacing(5);
        hbBotones.getChildren().addAll(btnSalir, btnEliminar);
        
        vbPpal.getChildren().addAll(lbTituloVista, hbTablaDatos, hbBotones);
        
        return vbPpal;
    }    
    private VBox vistaPermisosUsuario(){
        VBox vbPpal= new VBox();        
        Label lbTituloVista = new Label("PERMISOS USUARIO");
        Font fuente = new Font("Arial Bold", 30);
        lbTituloVista.setFont(fuente);        
        vbPpal.setAlignment(Pos.CENTER);

        Label lbAcceso = new Label("Acceso a Modulos");
        Label lbNuevaTarjeta = new Label("Nueva Tarjeta");
        CheckBox chNuevaTarjeta = new CheckBox();
        Label lbModificaTarjeta = new Label("Consultar/Mod. Tarjeta");    
        CheckBox chModificarTarjeta = new CheckBox();
        Label lbEliminarTarjeta = new Label("Eliminar Tarjeta");
        CheckBox chEliminarTarjeta = new CheckBox();  
        Label lbAsignarTarjeta = new Label("Asignar Tarjeta");
        CheckBox chAsignarTarjeta = new CheckBox();  
        Label lbEstadoCobranza = new Label("Estado Cobranza");
        CheckBox chEstadoCobranza = new CheckBox();
        Label lbAgregarCliente = new Label("Agregar Cliente");
        CheckBox chAgregarCliente = new CheckBox();
        Label lbModificarCliente = new Label("Modificar Consultar Cliente");
        CheckBox chModificarCliente = new CheckBox();
        Label lbEliminarCliente = new Label("Eliminar Cliente");
        CheckBox chEliminarCliente = new CheckBox();
        Label lbListaNegraCLiente = new Label("Lista Negra Cliente");
        CheckBox chListaNegraCliente = new CheckBox();
        Label lbAgregarVendedor = new Label("Agregar Vendedor");
        CheckBox chAgregarVendedor = new CheckBox();
        Label lbModificarVendedor = new Label("Modificar/Consultar Vendedor");
        CheckBox chModificarVendedor = new CheckBox();  
        Label lbEliminarVendedor = new Label("Eliminar Vendedor");
        CheckBox chEliminarVendedor = new CheckBox();
        Label lbAgregarCobrador = new Label("Agregar Cobrador");
        CheckBox chAgregarCobrador = new CheckBox();  
        Label lbModificarCobrador = new Label("Modificar/Consultar Cobrador");
        CheckBox chModificarCobrador = new CheckBox();  
        Label lbEliminarCobrador = new Label("Eliminar Cobrador");
        CheckBox chEliminarCobrador = new CheckBox();
        Label lbEstadoCobrador = new Label("Estado Corte Cobrador");
        CheckBox chEstadoCobrador = new CheckBox();
        Label lbAgregarProducto = new Label("Agregar Product");
        CheckBox chAgregarProducto = new CheckBox();  
        Label lbModificarProducto = new Label("Modificar/Consultar Producto");
        CheckBox chModificarProducto = new CheckBox();  
        Label lbEliminarProducto = new Label("Eliminar Producto");
        CheckBox chEliminarProducto = new CheckBox();
        Label lbEstadisticaProducto = new Label("Estadisticas de Productos");
        CheckBox chEstadisticaProducto = new CheckBox(); 
        Label lbAgregarGastos = new Label("Agregar Gastos");
        CheckBox chAgregarGastos = new CheckBox();  
        Label lbModificarGastos = new Label("Modificar/Consultar Gastos");
        CheckBox chModificadorGastos = new CheckBox();
        Label lbEliminarGasto = new Label("Eliminar Gasto");
        CheckBox chEliminarGasto = new CheckBox();
        Label lbAcumuladoCaja = new Label("Acumulado en caja");
        CheckBox chAcumuladoCaja = new CheckBox();
        Label lbNuevoUsuario = new Label("Nuevo Usuario");
        CheckBox chNuevoUsuario = new CheckBox();
        Label lbModificarUsuario = new Label("Modificar Usuario");
        CheckBox chModificarUsuario = new CheckBox();
        Label lbEliminarUsuario = new Label("Eliminar Usuario");
        CheckBox chEliminarUsuario = new CheckBox();
        Label lbAccesoUsuario = new Label("Acceso Usuarios");
        CheckBox chAccesoUsuario = new CheckBox(); 
        Label lbGestionBonos = new Label("Gestion Bonos");
        CheckBox chGestionBonos = new CheckBox();
                

        
        TextField tfNombre = new TextField();        
        TableView tvUsuario = new TableView();   
        
        TableColumn<Object, Integer> codigoColumna = new TableColumn<>("id_usuario");
        codigoColumna.setPrefWidth(60);
        codigoColumna.setCellValueFactory(new PropertyValueFactory<>("id_usuario"));
        TableColumn<Object, String> nomColumna = new TableColumn<>("Nombre");
        nomColumna.setPrefWidth(350);
        nomColumna.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        TableColumn<Object, String> usuarioColumna = new TableColumn<>("Usuario");
        usuarioColumna.setPrefWidth(150);
        usuarioColumna.setCellValueFactory(new PropertyValueFactory<>("Usuario"));
        TableColumn<Object, String> puestoColumna = new TableColumn<>("Puesto");
        puestoColumna.setPrefWidth(150);
        puestoColumna.setCellValueFactory(new PropertyValueFactory<>("Puesto"));        
        tvUsuario.getColumns().setAll(codigoColumna, nomColumna, usuarioColumna,puestoColumna);        
        List<String>  lstWhere= new ArrayList<>();             
        List<usuario> lstUsua=new ArrayList<>();
  
        lstWhere.clear();
        lstUsua.addAll(usuDAO.consultaUsuario(lstWhere));
        tvUsuario.getItems().addAll(lstUsua);        
        
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                vbAreaTrabajo.getChildren().remove(0);
            }
        });     
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        Button btnGuardar = new Button("Guardar");
        btnGuardar.setDisable(true);
        tvUsuario.setOnMouseClicked((event) -> {
                btnGuardar.setDisable(false);
                usua = (usuario)tvUsuario.getSelectionModel().getSelectedItem();
                int id=0;
                String tipo_usuario;
                id = usua.getId_usuario();
                tipo_usuario= usua.getPuesto();
                System.out.println("id Usuario "+usua.getId_usuario()+"Nombre Usuario "+usua.getNombre()+" Tipo Usuario: "+ tipo_usuario);
                lstWhere.clear();
                lstWhere.add("id_usuario = "+id);               
                
                    lstWhere.clear();
                    lstWhere.add("id_usuario = "+usua.getId_usuario());
                    List<accesos> lstAccesos = acceDAO.consultaAccesos(lstWhere);
                    if (lstAccesos.isEmpty()){
                            chNuevaTarjeta.setSelected(false);
                            chModificarTarjeta.setSelected(false);
                            chEliminarTarjeta.setSelected(false);
                            chAsignarTarjeta.setSelected(false);
                            chEstadoCobranza.setSelected(false);
                            chAgregarCliente.setSelected(false);
                            chModificarCliente.setSelected(false);
                            chEliminarCliente.setSelected(false);
                            chListaNegraCliente.setSelected(false);
                            chAgregarVendedor.setSelected(false);
                            chModificarVendedor.setSelected(false);
                            chEliminarVendedor.setSelected(false);
                            chAgregarCobrador.setSelected(false);
                            chModificarCobrador.setSelected(false);
                            chEliminarCobrador.setSelected(false);
                            chEstadoCobrador.setSelected(false);
                            chAgregarProducto.setSelected(false);
                            chModificarProducto.setSelected(false);
                            chEliminarProducto.setSelected(false);
                            chEstadisticaProducto.setSelected(false);
                            chAgregarGastos.setSelected(false);
                            chModificadorGastos.setSelected(false);
                            chEliminarGasto.setSelected(false);
                            chAcumuladoCaja.setSelected(false);
                            chNuevoUsuario.setSelected(false);
                            chModificarUsuario.setSelected(false);
                            chEliminarUsuario.setSelected(false);
                            chAccesoUsuario.setSelected(false);
                            chGestionBonos.setSelected(false);

                    switch ( usua.getPuesto().toString()) {
                        case "ADMINISTRADOR":                            
                            chNuevaTarjeta.setSelected(true);
                            chModificarTarjeta.setSelected(true);
                            chEliminarTarjeta.setSelected(true);
                            chAsignarTarjeta.setSelected(true);
                            chEstadoCobranza.setSelected(true);
                            chAgregarCliente.setSelected(true);
                            chModificarCliente.setSelected(true);
                            chEliminarCliente.setSelected(true);
                            chListaNegraCliente.setSelected(true);
                            chAgregarVendedor.setSelected(true);
                            chModificarVendedor.setSelected(true);
                            chEliminarVendedor.setSelected(true);
                            chAgregarCobrador.setSelected(true);
                            chModificarCobrador.setSelected(true);
                            chEliminarCobrador.setSelected(true);
                            chEstadoCobrador.setSelected(true);
                            chAgregarProducto.setSelected(true);
                            chModificarProducto.setSelected(true);
                            chEliminarProducto.setSelected(true);
                            chEstadisticaProducto.setSelected(true);
                            chAgregarGastos.setSelected(true);
                            chModificadorGastos.setSelected(true);
                            chEliminarGasto.setSelected(true);
                            chAcumuladoCaja.setSelected(true);
                            chNuevoUsuario.setSelected(true);
                            chModificarUsuario.setSelected(true);
                            chEliminarUsuario.setSelected(true);
                            chAccesoUsuario.setSelected(true);
                            chGestionBonos.setSelected(true);

                            break;
                        case "SUPERVISOR":
                            chNuevaTarjeta.setSelected(true);
                            chModificarTarjeta.setSelected(true);
                            chEliminarTarjeta.setSelected(false);
                            chAsignarTarjeta.setSelected(true);
                            chEstadoCobranza.setSelected(false);
                            chAgregarCliente.setSelected(false);
                            chModificarCliente.setSelected(false);
                            chEliminarCliente.setSelected(false);
                            chListaNegraCliente.setSelected(false);
                            chAgregarVendedor.setSelected(false);
                            chModificarVendedor.setSelected(false);
                            chEliminarVendedor.setSelected(false);
                            chAgregarCobrador.setSelected(false);
                            chModificarCobrador.setSelected(false);
                            chEliminarCobrador.setSelected(true);
                            chEstadoCobrador.setSelected(false);
                            chAgregarProducto.setSelected(false);
                            chModificarProducto.setSelected(false);
                            chEliminarProducto.setSelected(false);
                            chEstadisticaProducto.setSelected(false);
                            chAgregarGastos.setSelected(false);
                            chModificadorGastos.setSelected(false);
                            chEliminarGasto.setSelected(false);
                            chAcumuladoCaja.setSelected(false);
                            chNuevoUsuario.setSelected(false);
                            chModificarUsuario.setSelected(false);
                            chEliminarUsuario.setSelected(false);
                            chAccesoUsuario.setSelected(false);
                            chGestionBonos.setSelected(false);
                            break;
                        default:
                            System.out.println("error" );
                            break;
                        }
                    }else {
                            miAcce=acceDAO.consultaAccesos(lstWhere).get(0);
                            System.out.println("Si entro : "+miAcce.getNueva_tarjetas());
                            chNuevaTarjeta.setSelected(miAcce.getNueva_tarjetas());
                            chModificarTarjeta.setSelected(miAcce.getModificar_consultar_Tarjeta());
                            chEliminarTarjeta.setSelected(miAcce.getEliminar_tarjeta());
                            chAsignarTarjeta.setSelected(miAcce.getAsignar_tarjeta());
                            chEstadoCobranza.setSelected(miAcce.getEstado_cobranza());
                            chAgregarCliente.setSelected(miAcce.getAgregar_cliente());
                            chModificarCliente.setSelected(miAcce.getModificar_consultar_cliente());
                            chEliminarCliente.setSelected(miAcce.getEliminar_cliente());
                            chListaNegraCliente.setSelected(miAcce.getLista_negra_cliente());
                            chAgregarVendedor.setSelected(miAcce.getAgregar_vendedor());
                            chModificarVendedor.setSelected(miAcce.getModificar_consultar_vendedor());
                            chEliminarVendedor.setSelected(miAcce.getEliminar_vendedor());
                            chAgregarCobrador.setSelected(miAcce.getAgregar_cobrador());
                            chModificarCobrador.setSelected(miAcce.getModificar_consultar_cobrador());
                            chEliminarCobrador.setSelected(miAcce.getEliminar_cobrador());
                            chEstadoCobrador.setSelected(miAcce.getEstado_corte_cobrador());
                            chAgregarProducto.setSelected(miAcce.getAgregar_producto());
                            chModificarProducto.setSelected(miAcce.getModificar_consultar_producto());
                            chEliminarProducto.setSelected(miAcce.getEliminar_producto());
                            chEstadisticaProducto.setSelected(miAcce.getEstadisticas_producto());
                            chAgregarGastos.setSelected(miAcce.getAgregar_gasto());
                            chModificadorGastos.setSelected(miAcce.getModificar_consultar_gasto());
                            chEliminarGasto.setSelected(miAcce.getEliminar_gasto());
                            chAcumuladoCaja.setSelected(miAcce.getAcumulado_caja());
                            chNuevoUsuario.setSelected(miAcce.getNuevo_usuario());
                            chModificarUsuario.setSelected(miAcce.getModificar_usuario());
                            chEliminarUsuario.setSelected(miAcce.getEliminar_usuario());
                            chAccesoUsuario.setSelected(miAcce.getAcceso_usuarios());
                            chGestionBonos.setSelected(miAcce.getGestion_bonos());
 
                    }
                
        });               
       
        btnGuardar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               int id=0;
               int res=0;
               id = usua.getId_usuario();

                    accesos acce=new accesos();
                    acce.setId_usuario(id);
                    acce.setNueva_tarjetas(chNuevaTarjeta.isSelected());
                    acce.setModificar_consultar_Tarjeta(chModificarTarjeta.isSelected());
                    acce.setEliminar_tarjeta(chEliminarTarjeta.isSelected());
                    acce.setAsignar_tarjeta(chAsignarTarjeta.isSelected());
                    acce.setEstado_cobranza(chEstadoCobranza.isSelected());
                    acce.setAgregar_cliente(chAgregarCliente.isSelected());
                    acce.setModificar_consultar_cliente(chModificarCliente.isSelected());
                    acce.setEliminar_cliente(chEliminarCliente.isSelected());
                    acce.setLista_negra_cliente(chListaNegraCliente.isSelected());
                    acce.setAgregar_vendedor(chAgregarVendedor.isSelected());
                    acce.setModificar_consultar_vendedor(chModificarVendedor.isSelected());
                    acce.setEliminar_vendedor(chEliminarVendedor.isSelected());
                    acce.setAgregar_cobrador(chAgregarCobrador.isSelected());
                    acce.setModificar_consultar_cobrador(chModificarCobrador.isSelected());
                    acce.setEliminar_cobrador(chEliminarCobrador.isSelected());
                    acce.setEstado_corte_cobrador(chEstadoCobrador.isSelected());
                    acce.setAgregar_producto(chAgregarProducto.isSelected());
                    acce.setModificar_consultar_producto(chModificarProducto.isSelected());
                    acce.setEliminar_producto(chEliminarProducto.isSelected());
                    acce.setEstadisticas_producto(chEstadisticaProducto.isSelected());
                    acce.setAgregar_gasto(chAgregarGastos.isSelected());
                    acce.setModificar_consultar_gasto(chModificadorGastos.isSelected());
                    acce.setEliminar_gasto(chEliminarGasto.isSelected());
                    acce.setAcumulado_caja(chAcumuladoCaja.isSelected());
                    acce.setNuevo_usuario(chNuevoUsuario.isSelected());
                    acce.setModificar_usuario(chModificarUsuario.isSelected());
                    acce.setEliminar_usuario(chEliminarUsuario.isSelected());
                    acce.setAcceso_usuarios(chAccesoUsuario.isSelected());
                    acce.setGestion_bonos(chGestionBonos.isSelected());
                 
                lstWhere.clear();
                lstWhere.add("id_usuario = "+usua.getId_usuario());
                List<accesos> lstAccesos = acceDAO.consultaAccesos(lstWhere);
                if (lstAccesos.isEmpty()){
                  System.out.println("Inserto un registro");
                  res=acceDAO.insertarAccesos(acce);
                }else{
                  res=acceDAO.modificarAccesos(acce);  
                }    
                alert.setTitle("Informativo");
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Datos Actualizados "+res);
                alert.showAndWait();                   
            }
        });
        
        GridPane gpPpal = new GridPane();
        gpPpal.setPadding(new Insets(5,5,5,5));
        gpPpal.setVgap(5);
        gpPpal.setHgap(5);
        
        gpPpal.setColumnSpan(lbAcceso, 4);
        gpPpal.setHalignment(lbAcceso, HPos.CENTER);
        gpPpal.add(lbAcceso, 1,5 );       
        gpPpal.add(lbNuevaTarjeta , 1, 6);
        gpPpal.add(chNuevaTarjeta , 2, 6);
        gpPpal.add(lbModificaTarjeta , 1, 7);
        gpPpal.add(chModificarTarjeta , 2, 7);        
        gpPpal.add(lbEliminarTarjeta , 1, 8);
        gpPpal.add(chEliminarTarjeta , 2, 8);
        gpPpal.add(lbAsignarTarjeta , 1, 9);
        gpPpal.add(chAsignarTarjeta , 2, 9);
        gpPpal.add(lbEstadoCobranza  , 1, 10);
        gpPpal.add(chEstadoCobranza  , 2, 10);
        gpPpal.add(lbAgregarCliente  , 1, 11);
        gpPpal.add(chAgregarCliente  , 2, 11);
        gpPpal.add(lbModificarCliente  , 1, 12);
        gpPpal.add(chModificarCliente  , 2, 12);
        gpPpal.add(lbEliminarCliente  , 1, 13);
        gpPpal.add(chEliminarCliente  , 2, 13);
        gpPpal.add(lbListaNegraCLiente, 1, 14);
        gpPpal.add(chListaNegraCliente, 2, 14);
        gpPpal.add(lbAgregarVendedor, 1, 15);
        gpPpal.add(chAgregarVendedor, 2, 15);
        gpPpal.add(lbModificarVendedor, 1, 16);
        gpPpal.add(chModificarVendedor, 2, 16);
        gpPpal.add(lbEliminarVendedor, 1, 17);
        gpPpal.add(chEliminarVendedor, 2, 17);
        gpPpal.add(lbAgregarCobrador, 1, 18);
        gpPpal.add(chAgregarCobrador, 2, 18);//chModificarCobrador
        gpPpal.add(lbModificarCobrador, 1, 19);
        gpPpal.add(chModificarCobrador, 2, 19);
        gpPpal.add(lbEliminarCobrador, 1, 20);
        gpPpal.add(chEliminarCobrador, 2, 20);
        gpPpal.add(lbEstadoCobrador, 1, 21);
        gpPpal.add(chEstadoCobrador, 2, 21);
        gpPpal.add(lbAgregarProducto, 1, 22);
        gpPpal.add(chAgregarProducto, 2, 22);
        gpPpal.add(lbModificarProducto, 1, 23);
        gpPpal.add(chModificarProducto, 2, 23);        
        gpPpal.add(lbEliminarProducto , 1, 24);
        gpPpal.add(chEliminarProducto , 2, 24);
        gpPpal.add(lbEstadisticaProducto , 1, 25);
        gpPpal.add(chEstadisticaProducto , 2, 25);
        gpPpal.add(lbAgregarGastos , 1, 26);
        gpPpal.add(chAgregarGastos , 2, 26);        
        gpPpal.add(lbModificarGastos , 1, 27);
        gpPpal.add(chModificadorGastos , 2, 27);
        gpPpal.add(lbEliminarGasto , 3, 6);
        gpPpal.add(chEliminarGasto , 4, 6);
        gpPpal.add(lbAcumuladoCaja , 3, 7);
        gpPpal.add(chAcumuladoCaja , 4, 7);
        gpPpal.add(lbNuevoUsuario , 3, 8);
        gpPpal.add(chNuevoUsuario , 4, 8);
        gpPpal.add(lbModificarUsuario , 3, 9);
        gpPpal.add(chModificarUsuario , 4, 9);
        gpPpal.add(lbEliminarUsuario , 3, 10);
        gpPpal.add(chEliminarUsuario , 4, 10);
        gpPpal.add(lbAccesoUsuario , 3, 11);
        gpPpal.add(chAccesoUsuario , 4, 11);
        gpPpal.add(lbGestionBonos , 3, 12);
        gpPpal.add(chGestionBonos , 4, 12);

        
        gpPpal.setHalignment(btnCancelar, HPos.CENTER);
        gpPpal.setHalignment(btnGuardar, HPos.LEFT);
        gpPpal.add(btnCancelar,3,27);
        gpPpal.add(btnGuardar,4,27); 
        gpPpal.setAlignment(Pos.CENTER);
       
        HBox hbBuscar = new HBox();
        
        hbBuscar.setAlignment(Pos.CENTER);
        hbBuscar.setSpacing(10);
        hbBuscar.getChildren().addAll(tvUsuario, gpPpal);
        vbPpal.getChildren().addAll(lbTituloVista, hbBuscar);
       
        return vbPpal;  
    }     
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
