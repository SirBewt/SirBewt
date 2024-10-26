package com.example.proyecto;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView; // Importa AdapterView para manejar eventos en vistas adaptables.
import android.widget.ArrayAdapter; // Importa ArrayAdapter para adaptar arrays a vistas como Spinners.
import android.widget.Spinner; // Importa Spinner, que es una lista desplegable en Android.
import org.osmdroid.config.Configuration; // Importa la clase Configuration para configurar el mapa.
import org.osmdroid.tileprovider.tilesource.TileSourceFactory; // Importa TileSourceFactory para definir los tipos de mapas disponibles.
import org.osmdroid.tileprovider.tilesource.XYTileSource; // Importa XYTileSource para crear un proveedor de azulejos específico para mapas personalizados.
import org.osmdroid.util.GeoPoint; // Importa GeoPoint, que representa coordenadas geográficas (latitud y longitud).
import org.osmdroid.views.MapView; // Importa MapView, que es el componente visual del mapa.
import org.osmdroid.views.overlay.Marker; // Importa Marker para agregar marcadores en el mapa.
import org.osmdroid.views.overlay.Polyline; // Importa Polyline para dibujar líneas en el mapa.

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        // carga la configuracioon del mapa usando las preferencias predeterminadas.
        Configuration.getInstance().load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        //obtiene la referencia al componente MapView del layout
        MapView mapView = findViewById(R.id.mapView);
        //establecemos la fuente de azulejos del mapa estandar
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        //ACTIVA LOS CONTROLES DE ZOOM PARA EL MAPA
        mapView.setBuiltInZoomControls(true);
        //activamos el zoom tactil
        mapView.setMultiTouchControls(true);

        //cordenadas de la tienda fisica
        double  tiendaLatitud = -33.440530; // latitud
        double  tiendaLongitud = -70.649186; //longitud

        GeoPoint TiendPoint = new GeoPoint(tiendaLatitud, tiendaLongitud);

        //vista inicial del mapa ya centrado
        mapView.getController().setZoom(19.0);
        //mapa centrado en el punto de santiago
        mapView.getController().setCenter(TiendPoint);

        //CREAMOS UN MARCADOR PARA LA TINEDA EN EL MAPA
         Marker marcadorTienda = new Marker(mapView);
         //se establece la posision del marcador
         marcadorTienda.setPosition(TiendPoint);
         //dejamos un punto como ancla de la tienda
        marcadorTienda.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        //TITULO DEL MARCADOR
        marcadorTienda.setTitle("MyTienda");
        // dejamos una descripcion para el marcador
        marcadorTienda.setSnippet("Una pequeña tienda de Libros");
        //agregamos los marcadores
        mapView.getOverlays().add(marcadorTienda);





        //cordenas del punto de partida
        double santoLatitud = -33.4493141; // latitud santo tomas
        double santoLongitud =-70.6624069; //longitud santo tomas

        GeoPoint santoPoint = new GeoPoint( santoLatitud, santoLongitud);
        // parametros del punto de partida
        Marker marcadorSanto = new Marker(mapView);
        marcadorSanto.setPosition(santoPoint);
        marcadorSanto.setAnchor(0.2f, 0.0f);
        marcadorSanto.setInfoWindowAnchor(0.2f, 0.0f);
        marcadorSanto.setTitle("Santo Tomas");
        marcadorSanto.setSnippet("Un instituto");
        marcadorSanto.setIcon(getResources().getDrawable(R.drawable.libro));

        mapView.getOverlays().add(marcadorSanto);



        // crear una linea que conecte los 2 puntos
        Polyline linea = new Polyline();
        //añadimos cada punto al trazo
        linea.addPoint(TiendPoint);
        linea.addPoint(santoPoint);

        //color de la linea
        linea.setColor(0xFF0000FF);
        //ANCHO DE LA LINEA
        linea.setWidth(5);
        //añadimos la linea al mapa
        mapView.getOverlayManager().add(linea);


        }
    }
