package ar.com.twoboot.microman;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import ar.com.twoboot.microman.objetos.Hito;
import ar.com.twoboot.microman.objetos.Ruta;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

@SuppressLint("NewApi")
public class MapaActivity extends FragmentActivity implements OnMapReadyCallback {
    private Bundle bundle;
	private Ruta rutaActiva;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getIntent().getExtras();
		
    	rutaActiva = (Ruta) bundle.getSerializable(MicroMan.PARAM_RUTA_ACTIVA);
    	
        setContentView(R.layout.activity_mapa);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapview);
        mapFragment.getMapAsync(this);
        
    }

    @Override
    public void onMapReady(GoogleMap map) {
        // Add a marker in Sydney, Australia, and move the camera.
    	LatLng punto=null;
        
    	for (Hito hito : rutaActiva.getHito()) {
    		punto=new LatLng(hito.getGpslatitud(), hito.getGpslongitud());
    		StringBuilder titulo=new StringBuilder();
    		titulo.append("Med:");
    		titulo.append(hito.getMedidor().getIdMedidor());
       		titulo.append("\n");
       		StringBuilder info=new StringBuilder();
    		
       		info.append("Uni:");
       		info.append(hito.getCliente().getIdCliente());
       		info.append("\n");
       		info.append(hito.getCliente().getRazonSocial());
    		MarkerOptions m;
    		if(hito.getEstado()==0){
    		 m=new MarkerOptions().position(punto).title(titulo.toString()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).snippet(info.toString());
    		}else{
    			 m=new MarkerOptions().position(punto).title(titulo.toString()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).snippet(info.toString());
        		
    		}
    		
    		map.addMarker(m);
           
		}
         map.moveCamera(CameraUpdateFactory.newLatLng(punto));
         CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);

         map.animateCamera(zoom);
    }
}
