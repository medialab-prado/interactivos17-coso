/*
desarrollado por danielrosero
 */

package me.danielrosero.coso;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.hardware.usb.UsbRequest;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import me.danielrosero.coso.modelo.Captura;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,SensorEventListener {

    private static final int MY_PERMISSIONS_REQUEST = 0;
    private GoogleMap mMap;
    private Button b_registrar;
    private Location mLastLocation;
    public LocationManager mLocationManager;
    int LOCATION_REFRESH_TIME = 25; //Estaba en 1000
    int LOCATION_REFRESH_DISTANCE = 0; //Estaba en 2
    private static ArrayList<Captura> ruta = new ArrayList<Captura>();
    private boolean registrando=false;
    private DatabaseReference rootDB = FirebaseDatabase.getInstance().getReference();
    private Polyline routePolyline;
    private String routeID;
    private static final UUID MY_UUID = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");
    private BluetoothAdapter BA;
    private ConnectThread hiloConectadorBT;
    private ConnectedThread hiloComBT;
    Boolean conectadoSS = false;

    private float diffZ;




    private Captura laCaptura;


    // The acceleration, in units of meters per second, as measured by the
    // accelerometer.
    private float[] acceleration = new float[3];

    // Handler for the UI plots so everything plots smoothly
    private Handler handler;

    private MeanFilterSmoothing meanFilter;

    private Runnable runable;

    // Sensor manager to access the accelerometer
    private SensorManager sensorManager;

    // Text views for real-time output
    private TextView textViewXAxis;
    private TextView textViewYAxis;
    private TextView textViewZAxis;

    private int MESSAGE_READ;
    private boolean btlibre;

    public static final int MY_BLUETOOTH_ENABLE_REQUEST_ID = 6;

    private boolean canceloPedidoConexion=false;

    private ArrayList<Float> medidasZ = new ArrayList<Float>();
    private ArrayList<Float> medidasDiferenciaZ = new ArrayList<Float>();



    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            //code
            System.out.println("onLocationChanged");
            mLastLocation = location;
            // mainLabel.setText("Latitude:" + String.valueOf(location.getLatitude()) + "\n" + "Longitude:" + String.valueOf(location.getLongitude()));
            //Toast.makeText(MapsActivity.this,"Latitude:" + String.valueOf(location.getLatitude()) + "\n" + "Longitude:" + String.valueOf(location.getLongitude()),Toast.LENGTH_SHORT).show();




            // mMap.getMyLocation();

            CameraPosition position = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(),location.getLongitude()))
                    .zoom(18).build();

            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));


//            Manejo la captura

         // if(!textViewXAxis.getText().toString().isEmpty() && !textViewYAxis.getText().toString().isEmpty() && !textViewZAxis.getText().toString().isEmpty()) {

            //laCaptura = new Captura(Float.parseFloat(textViewXAxis.getText().toString()), Float.parseFloat(textViewYAxis.getText().toString()), Float.parseFloat(textViewZAxis.getText().toString()), new LatLng(location.getLatitude(), location.getLongitude()));
            laCaptura = new Captura(acceleration[0],acceleration[1],acceleration[2], new LatLng(location.getLatitude(), location.getLongitude()));
            ruta.add(laCaptura);
          //}

//            ****




            if(ruta.size()<=1){



            }else{

                drawLine(ruta.get(ruta.size()-2).getLatilongi(),ruta.get(ruta.size()-1).getLatilongi());



            }


            //if(!textViewXAxis.getText().toString().isEmpty() && !textViewYAxis.getText().toString().isEmpty() && !textViewZAxis.getText().toString().isEmpty()) {

                //Guardo en base de datos


                Map<String, Object> captura = new HashMap<>();

                captura.put("timesamp", ServerValue.TIMESTAMP);
                captura.put("lat", laCaptura.getLatilongi().latitude);
                captura.put("long", laCaptura.getLatilongi().longitude);
                //captura.put("accx", laCaptura.getAccX());
                //captura.put("accy", laCaptura.getAccY());
                captura.put("accz", laCaptura.getAccZ());

                if (ruta.size() > 1) {

//                    float diffX = Math.abs(ruta.get(ruta.size() - 2).getAccX() - ruta.get(ruta.size() - 1).getAccX());
//                    float diffY = Math.abs(ruta.get(ruta.size() - 2).getAccY() - ruta.get(ruta.size() - 1).getAccY());
//                    float diffZ = Math.abs(ruta.get(ruta.size() - 2).getAccZ() - ruta.get(ruta.size() - 1).getAccZ());


                    //captura.put("diffX", diffX);
                    //captura.put("diffY", diffY);
                    captura.put("diffZ", diffZ);
                    captura.put("medidasDiferenciaZ", medidasDiferenciaZ );


/*//                    Intervalos para validacion de vibración

//                    Liso
                    if(diffZ>=0 && diffZ<=0.29){
                        if(conectadoSS)
                        hiloComBT.write("u!".getBytes());
                    }
//                    Rugoso
                    if(diffZ>=0.30 && diffZ<=0.39){
                        if(conectadoSS)
                        hiloComBT.write("i!".getBytes());
                    }
//                    Accidentado
                    if(diffZ>=0.40 && diffZ<=0.49){
                        if(conectadoSS)
                        hiloComBT.write("o!".getBytes());
                    }
//                    Bache
                    if(diffZ>=0.50){
                        if(conectadoSS)
                        hiloComBT.write("p!".getBytes());
                    }


//                    *****/
                }

                rootDB.child("rutas").child(routeID).child("capturas").push().setValue(captura);

//            Flusheo arreglo medidas diferencial Z

            medidasDiferenciaZ.clear();

//            ****

//            ****
           // }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            System.out.println("onStatusChanged");
        }

        @Override
        public void onProviderEnabled(String provider) {
            System.out.println("onProviderEnabled");
        }

        @Override
        public void onProviderDisabled(String provider) {
            System.out.println("onProviderDisabled");
            //turns off gps services
        }
    };

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {


            byte[] readBuf = (byte[]) msg.obj;
            // construct a string from the valid bytes in the buffer
            String readMessage = new String(readBuf, 0, msg.arg1);
            //mConversationArrayAdapter.add(mConnectedDeviceName+":  " + readMessage);
            Log.e("Llega BT",readMessage);
            if(readMessage.equals("B")){
                btlibre=true;
            }



        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);



        btlibre=true;




        b_registrar = (Button)findViewById(R.id.b_registrar);


        //b_registrar.setEnabled(false);

        b_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!registrando){


//                    Limpio el mapa para nueva captura


                    if(routePolyline!=null) {
                        routePolyline.remove();
                        ruta.clear();
                    }

//                    ****


//                    Por ahora genero aqui el id de la ruta


                    routeID = rootDB.push().getKey();



//                    ****



                b_registrar.setText("Parar");


//                Arranco tracking
                if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }

                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME, LOCATION_REFRESH_DISTANCE, mLocationListener);


//                ****
            }else {

                    b_registrar.setText("Registrar");

                    mLocationManager.removeUpdates(mLocationListener);

                }

                registrando = !registrando;

        }});



//        Acelerometro

        textViewXAxis = (TextView) findViewById(R.id.value_x_axis);
        textViewYAxis = (TextView) findViewById(R.id.value_y_axis);
        textViewZAxis = (TextView) findViewById(R.id.value_z_axis);

        meanFilter = new MeanFilterSmoothing();
        meanFilter.setTimeConstant(0.2f);

        sensorManager = (SensorManager) this
                .getSystemService(Context.SENSOR_SERVICE);

        handler = new Handler();

        runable = new Runnable()
        {
            @Override
            public void run()
            {
                handler.postDelayed(this, 100);

                updateAccelerationText();
            }
        };



//        ****



    }

    @Override
	public void onPause()
	{
		super.onPause();

		sensorManager.unregisterListener(this);

		handler.removeCallbacks(runable);
	}

	@Override
	public void onResume()
	{
		super.onResume();

		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);

		handler.post(runable);


//
        if (!conectadoSS){
            conectarBT();}


	}


    public void conectarBT() {
        BA = BluetoothAdapter.getDefaultAdapter();
        if (BA == null) {
            // Device does not support Bluetooth
        } else {
            //Tiene bluetooth

            if (!BA.isEnabled()) {
                // Bluetooth is not enable :)
//                buildAlertMessageNoBluetooth();
                if(!canceloPedidoConexion) {
                    startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), MY_BLUETOOTH_ENABLE_REQUEST_ID);
                }
            } else {

                String deviceName = "C.O.S.O";

                BluetoothDevice result = null;

                Set<BluetoothDevice> devices = BA.getBondedDevices();
                if (devices != null) {
                    for (BluetoothDevice device : devices) {
                        if (deviceName.equals(device.getName())) {
                            result = device;
                            break;
                        }
                    }
                }

                if (result != null) {

                    hiloConectadorBT = new ConnectThread(result);
                    hiloConectadorBT.start();


                }
//                else {
//                    if (switchEstado != null) {
//                        Context context = getApplicationContext();
//                        CharSequence text = "Parece que no tienes un SmartGLOW vinculado a tus dispositivos Bluetooth.";
//                        int duration = Toast.LENGTH_LONG;
//
//                        Toast toast = Toast.makeText(context, text, duration);
//                        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
//                        toast.show();
//                        switchEstado.setChecked(false);
//
////                        buildAlertMessageNoSmartGlow();
//
//
//                    }
//                }

            }
        }

    }



    @Override
	public void onSensorChanged(SensorEvent event)
	{
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
		{
			// Get a local copy of the acceleration measurements
			System.arraycopy(event.values, 0, acceleration, 0,
					event.values.length);

			acceleration = meanFilter.addSamples(acceleration);
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy)
	{

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


        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(
                this, R.raw.stilo));


        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(MapsActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MapsActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(MapsActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.


            }
        }
//        else{
            mMap.setMyLocationEnabled(true);
//        }



    }


    public void drawLine(LatLng a, LatLng b){
        PolylineOptions options = new
                PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
        options.add(a);
        options.add(b);


        routePolyline=mMap.addPolyline(options);
    }

    private void updateAccelerationText()
    {
        // Update the acceleration data
        textViewXAxis.setText(String.format("%.2f", acceleration[0]));
        textViewYAxis.setText(String.format("%.2f", acceleration[1]));
        textViewZAxis.setText(String.format("%.2f", acceleration[2]));


        if(registrando) {



            medidasZ.add(acceleration[2]);


            if (medidasZ.size() > 1) {


                diffZ = Math.abs(medidasZ.get(medidasZ.size() - 2) - medidasZ.get(medidasZ.size() - 1));

                if(diffZ != 0)
                medidasDiferenciaZ.add(diffZ);


                if (conectadoSS) {
//                    Intervalos para validacion de vibración

//                    Liso
                    if (diffZ >= 0 && diffZ <= 0.29) {
                        if (conectadoSS)
                            hiloComBT.write("u!".getBytes());
                    }
//                    Rugoso
                    if (diffZ >= 0.30 && diffZ <= 0.39) {
                        if (conectadoSS)
                            hiloComBT.write("i!".getBytes());
                    }
//                    Accidentado
                    if (diffZ >= 0.40 && diffZ <= 0.49) {
                        if (conectadoSS)
                            hiloComBT.write("o!".getBytes());
                    }
//                    Bache
                    if (diffZ >= 0.50) {
                        if (conectadoSS)
                            hiloComBT.write("p!".getBytes());
                    }


//                    ****

                }

            }


        }
    }

    @Override
    protected void onDestroy() {

        if(conectadoSS) {
            String cmd = "f!";
            hiloComBT.write(cmd.getBytes());
        }

        super.onDestroy();
    }


    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {
            // Use a temporary object that is later assigned to mmSocket,
            // because mmSocket is final
            BluetoothSocket tmp = null;
            mmDevice = device;

            // Get a BluetoothSocket to connect with the given BluetoothDevice
            try {
                // MY_UUID is the app's UUID string, also used by the server code
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
            }
            mmSocket = tmp;
        }

        public void run() {
            // Cancel discovery because it will slow down the connection
            BA.cancelDiscovery();

            try {
                // Connect the device through the socket. This will block
                // until it succeeds or throws an exception
                mmSocket.connect();


            } catch (IOException connectException) {
                // Unable to connect; close the socket and get out
                try {
                    mmSocket.close();
                    conectadoSS = false;
                    registrando = false;
                    //b_registrar.setEnabled(false);

                    //showToast("No se encuentra un SmartGLOW para conectarse");

                    //Toast.makeText(MapsActivity.this,"No se encuentra un C.O.S.O para conectarse",Toast.LENGTH_SHORT).show();

                } catch (IOException closeException) {
                }
                return;
            }

            // Do work to manage the connection (in a separate thread)
            hiloComBT = new ConnectedThread(mmSocket);
            hiloComBT.start();

            if (hiloComBT.mmSocket.isConnected()) {

                try {
                    Thread.sleep(1000);
                    String cmd="o!";
                    hiloComBT.write(cmd.getBytes());

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Log.e("ESTADO", "Contectado");


                conectadoSS = true;
                //b_registrar.setEnabled(true);

                //Actualizo menu
                invalidateOptionsMenu();
                //****





                //Toast.makeText(MapsActivity.this,"C.O.S.O Conectado exitosamente",Toast.LENGTH_SHORT).show();



                hiloComBT.write("u".getBytes());




                //String cmd2="c0.1.1.0!";

                //Manda configuración setup. Que va a usar smartglow? c<#p16>.<#p24>.<#p30>.<#p60>
                //if(configuracionSmartGlow!=null) {
                    //hiloComBT.write(configuracionSmartGlow.getBytes());
                //}

                //Vuelvo el switch de modo enabled
//                switchModoBeats.setEnabled(true);
            }

//            }else{
//                Context context = getApplicationContext();
//                CharSequence text = "Paila.";
//                int duration = Toast.LENGTH_SHORT;
//
//                Toast toast = Toast.maif(!hiloComBT.isInterrupted()){
//                conectadoSS=true;
//
//                Context context = getApplicationContext();
//                CharSequence text = "SISA SmartGLOW Conectado exitosamente.";
//                int duration = Toast.LENGTH_SHORT;
//
//                Toast toast = Toast.makeText(context, text, duration);
//                toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
//                toast.show();keText(context, text, duration);
//                toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
//                toast.show();
//            }
        }

        /** Will cancel an in-progress connection, and close the socket */
        public void cancel() {
            try {
                mmSocket.close();
                conectadoSS = false;

               // b_registrar.setEnabled(false);


            } catch (IOException e) {
            }
        }
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
//        private final Handler mHandler;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
//             mHandler = new Handler();

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[1024];  // buffer store for the stream
            int bytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);
                    // Send the obtained bytes to the UI activity
                    mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer)
                            .sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }

        /* Call this from the main activity to send data to the remote device */
        public void write(byte[] bytes) {
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) {
            }
        }

        /* Call this from the main activity to shutdown the connection */
        public void cancel() {
            try {
                mmSocket.close();
                conectadoSS = false;
               // showToast("NO");
               // b_registrar.setEnabled(false);


            } catch (IOException e) {
            }
        }
    }



}
