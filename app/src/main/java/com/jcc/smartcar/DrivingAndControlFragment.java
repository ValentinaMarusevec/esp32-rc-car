package com.jcc.smartcar;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.VideoView;
import androidx.fragment.app.Fragment;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;

public class DrivingAndControlFragment extends Fragment {

    private Socket socket;
    private PrintWriter out;
    private static final int SERVER_PORT = 80;
    private static final String SERVER_IP = "192.168.4.1";

    private Button forwardButton;
    private Button backButton;
    private Button leftButton;
    private Button rightButton;
    private ImageButton lightsON;
    private ImageButton lightsOFF;
    private SeekBar speed;
    private ToggleButton cameraOnOf;
    private VideoView videoView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_driving_and_control, container, false);

        new ConnectTask().execute("");

        forwardButton = view.findViewById(R.id.forward);
        backButton = view.findViewById(R.id.back);
        leftButton = view.findViewById(R.id.left);
        rightButton = view.findViewById(R.id.right);
        lightsON = view.findViewById(R.id.imageButtonLightOn);
        lightsOFF = view.findViewById(R.id.imageButtonLightOff);
        cameraOnOf = view.findViewById(R.id.toggleButton);
        videoView = view.findViewById(R.id.videoView);

        cameraOnOf.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getContext(), "Camera on.", Toast.LENGTH_SHORT).show();
                    videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            try {
                                Uri videoUri = Uri.parse("http://" +SERVER_IP +":81/stream");
                                videoView.setVideoURI(videoUri);
                                videoView.requestFocus();
                                videoView.start();
                            } catch (Exception e) {
                                Log.e("Error", e.getMessage());
                                e.printStackTrace();
                            }
                        }
                    });
                }else{
                    Toast.makeText(getContext(), "Camera off.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sendData(1);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            URL url = new URL("http://" + SERVER_IP + "/control?var=car&val=1");
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("GET");
                            connection.connect();
                            int responseCode = connection.getResponseCode();
                            Log.d("HTTP GET", "Response Code: " + responseCode);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // sendData(5);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL("http://" + SERVER_IP + "/control?var=car&val=5");
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("GET");
                            connection.connect();
                            int responseCode = connection.getResponseCode();
                            Log.d("HTTP GET", "Response Code: " + responseCode);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // sendData(2);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL("http://" + SERVER_IP + "/control?var=car&val=2");
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("GET");
                            connection.connect();
                            int responseCode = connection.getResponseCode();
                            Log.d("HTTP GET", "Response Code: " + responseCode);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        // Right button
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL("http://" + SERVER_IP + "/control?var=car&val=4");
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("GET");
                            connection.connect();
                            int responseCode = connection.getResponseCode();
                            Log.d("HTTP GET", "Response Code: " + responseCode);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });

        lightsON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL("http://" + SERVER_IP + "/control?var=flash&val=256");
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("GET");
                            connection.connect();
                            int responseCode = connection.getResponseCode();
                            Log.d("HTTP GET", "Response Code: " + responseCode);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        lightsOFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL("http://" + SERVER_IP + "/control?var=flashoff&val=0");
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("GET");
                            connection.connect();
                            int responseCode = connection.getResponseCode();
                            Log.d("HTTP GET", "Response Code: " + responseCode);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        speed = view.findViewById(R.id.seekBar);
        speed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    int desiredSpeed = progress;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                URL url = new URL("http://" + SERVER_IP + "/control?var=speed&val=" + 200);
                                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                                connection.setRequestMethod("GET");
                                connection.connect();
                                int responseCode = connection.getResponseCode();
                                Log.d("HTTP GET", "Response Code: " + responseCode);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        return view;
    }

    private class ConnectTask extends AsyncTask<String, String, Void> {

        @Override
        protected Void doInBackground(String... params) {
            try {
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                socket = new Socket(serverAddr, SERVER_PORT);
                out = new PrintWriter(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Toast.makeText(getContext(), "Connected to ESP32", Toast.LENGTH_SHORT).show();
        }
    }

}