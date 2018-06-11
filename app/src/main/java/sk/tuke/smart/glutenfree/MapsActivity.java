package sk.tuke.smart.glutenfree;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.OnClick;
import sk.tuke.smart.glutenfree.pojo.Category;
import sk.tuke.smart.glutenfree.pojo.Obchody;
import sk.tuke.smart.glutenfree.pojo.Podniky;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "MapsActivity";
    private GoogleMap mMap;
    private UiSettings mUiSettings;
    private FusedLocationProviderClient mFusedLocationClient;
    private static int REQUEST_ID_LOCATION_PERMISSIONS = 0;
    private LatLng coordinates;
    private List<Obchody> obchodyList;
    private List<Podniky> podnikyList;
    private List<LatLng> coordinatesList;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);

        button = (Button) findViewById(R.id.button_map_get_location);
        obchodyList = (List<Obchody>) getIntent().getSerializableExtra("obchody");
        podnikyList = (List<Podniky>) getIntent().getSerializableExtra("podniky");

        if (obchodyList!=null || podnikyList!=null){
            coordinatesList = new ArrayList<>();
            button.setVisibility(View.GONE);
            if (obchodyList!=null){
                for (Obchody obchod:obchodyList) {
                    coordinatesList.add(new LatLng(obchod.getLattitude(), obchod.getLongtitude()));
                }
            } else {
                for (Podniky podnik:podnikyList) {
                    coordinatesList.add(new LatLng(podnik.getLattitude(), podnik.getLongtitude()));
                }
            }
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getWindow().setStatusBarColor(getResources().getColor(R.color.brownThick));
        getWindow().setNavigationBarColor(getResources().getColor(R.color.brownThick));
        showLastKnownLocation();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mUiSettings = mMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);

        // Listener for getting coordinates on longClick and creating marker on map
        mMap.setOnMapLongClickListener(latLng -> {
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(latLng).title("Podnik ktory chces pridat"));
            coordinates = latLng;
        });
        if (obchodyList!=null || podnikyList!=null) {
            for (int i = 0;i< coordinatesList.size(); i++) {
                if (obchodyList!=null){
                    mMap.addMarker(new MarkerOptions().position(new LatLng(coordinatesList.get(i).latitude, coordinatesList.get(i).longitude))
                            .title(obchodyList.get(i).getName()));
                } else {
                    mMap.addMarker(new MarkerOptions().position(new LatLng(coordinatesList.get(i).latitude, coordinatesList.get(i).longitude))
                            .title(podnikyList.get(i).getName()));
                }
            }
            if (coordinatesList.size()>0){
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinatesList.get(0), 11));
            }
        }

    }

    private void showLastKnownLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                Snackbar.make(findViewById(R.id.layout_maps), R.string.maps_snackbar, Snackbar.LENGTH_INDEFINITE).setAction(
                        R.string.maps_okay, view -> ActivityCompat.requestPermissions(MapsActivity.this,
                                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                        Manifest.permission.ACCESS_FINE_LOCATION},
                                REQUEST_ID_LOCATION_PERMISSIONS)
                ).show();
                //return;
            } else {
                ActivityCompat.requestPermissions(MapsActivity.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_ID_LOCATION_PERMISSIONS);
            }

            //Toast.makeText(getApplicationContext(), "Permission not granted", Toast.LENGTH_SHORT).show();
            return;
        }
        mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    coordinates = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions()
                            .title(MapsActivity.this.getString(R.string.maps_current_position)) //todo fancy shmancy icon
                            .position(coordinates));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom((new LatLng(location.getLatitude(), location.getLongitude())), 15));
                } else {
                   // Toast.makeText(getApplicationContext(),"", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @OnClick(R.id.button_map_get_location)
    public void OnClickGetLocation(View view){
        if(coordinates != null) {
            List<Address> addresses = null;
            String address = null;
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(coordinates.latitude,coordinates.longitude,1);
            } catch (IOException e) {
                e.printStackTrace();
                //Toast.makeText(getApplicationContext(),"Geocoder not available!", Toast.LENGTH_SHORT).show();
            }
            if (addresses!=null){
                Log.i(TAG, String.valueOf(addresses));
                address = addresses.get(0).getAddressLine(0);
            }
            Intent intent = new Intent(this, FormActivity.class);
            intent.putExtra("latitude", coordinates.latitude);
            intent.putExtra("longitude",coordinates.longitude);
            intent.putExtra("address",address);
            intent.putExtra("sent",true);
            intent.putExtra("podnik",(Podniky)getIntent().getSerializableExtra("podnik"));
            intent.putExtra("obchod",(Obchody)getIntent().getSerializableExtra("obchod"));
            intent.putExtra("kategoria",(Category)getIntent().getSerializableExtra("kategoria"));
            intent.putStringArrayListExtra("additional",getIntent().getStringArrayListExtra("additional"));
            startActivity(intent);
        }
        //Toast.makeText(getApplicationContext(),"click", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_ID_LOCATION_PERMISSIONS) {
            showLastKnownLocation();
            /*for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                if ((permission.equals(Manifest.permission.ACCESS_COARSE_LOCATION) || permission.equals(Manifest.permission.ACCESS_FINE_LOCATION))
                        && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    showLastKnownLocation();
                }
            }*/
        }

    }
}
