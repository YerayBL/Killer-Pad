package cabrera.alvaro.gamepad;

import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class JoyStickActivity extends AppCompatActivity implements Runnable {

    JoyStick move_joystick, shoot_joystick;
    RelativeLayout move_joystick_layout, shoot_joystick_layout;
    TextView move_textview, shoot_textview;

    String ip, name, red, green, blue;
    int port;
    private Socket sock;
    private PrintWriter out;
    private BufferedReader in;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.joystick_activity);


        this.ip = getIntent().getExtras().getString("ip");
        this.name = getIntent().getExtras().getString("name");
        this.red = getIntent().getExtras().getString("red");
        this.green = getIntent().getExtras().getString("green");
        this.blue = getIntent().getExtras().getString("blue");
        this.port = getIntent().getExtras().getInt("port");


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        this.connect();

        new Thread(this).start();

        this.newMoveJoystick();
        this.newShootJoystick();

    }

    public void connect(){

        try {

            sock = new Socket(ip, port);
            out = new PrintWriter(sock.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));
            out.println("CONPAD" + this.red + this.green + this.blue + name);

        } catch (Exception e) {

            this.finish();

        }
    }

    public void newMoveJoystick() {

        move_textview = (TextView) findViewById(R.id.move_textview);

        move_joystick_layout = (RelativeLayout)findViewById(R.id.move_joystick_layout);

        move_joystick = new JoyStick(getApplicationContext(), move_joystick_layout, R.drawable.move);
        move_joystick.setStickSize(150, 150);
        move_joystick.setLayoutSize(500, 500);
        move_joystick.setLayoutAlpha(150);
        move_joystick.setStickAlpha(100);
        move_joystick.setOffset(90);
        move_joystick.setMinimumDistance(50);

        move_joystick_layout.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View arg0, MotionEvent arg1) {

                move_joystick.drawStick(arg1);

                if(arg1.getAction() == MotionEvent.ACTION_DOWN || arg1.getAction() == MotionEvent.ACTION_MOVE) {

                    int direction = move_joystick.get8Direction();

                    if(direction == JoyStick.STICK_UP) {
                        move_textview.setText("Direction : Up");
                        sendLine("MOVUPP");
                    } else if(direction == JoyStick.STICK_UPRIGHT) {
                        move_textview.setText("Direction : Up Right");
                        sendLine("MOVUPR");
                    } else if(direction == JoyStick.STICK_RIGHT) {
                        move_textview.setText("Direction : Right");
                        sendLine("MOVRIG");
                    } else if(direction == JoyStick.STICK_DOWNRIGHT) {
                        move_textview.setText("Direction : Down Right");
                        sendLine("MOVDOR");
                    } else if(direction == JoyStick.STICK_DOWN) {
                        move_textview.setText("Direction : Down");
                        sendLine("MOVDOW");
                    } else if(direction == JoyStick.STICK_DOWNLEFT) {
                        move_textview.setText("Direction : Down Left");
                        sendLine("MOVDOL");
                    } else if(direction == JoyStick.STICK_LEFT) {
                        move_textview.setText("Direction : Left");
                        sendLine("MOVLEF");
                    } else if(direction == JoyStick.STICK_UPLEFT) {
                        move_textview.setText("Direction : Up Left");
                        sendLine("MOVUPL");
                    } else if(direction == JoyStick.STICK_NONE) {
                        move_textview.setText("Direction : Center");
                        sendLine("STP");
                    }

                } else if(arg1.getAction() == MotionEvent.ACTION_UP) {
                    move_textview.setText("Direction : Center");
                    sendLine("STP");
                }

                return true;
            }
        });

    }

    public void newShootJoystick() {

        shoot_textview = (TextView) findViewById(R.id.shoot_textview);

        shoot_joystick_layout = (RelativeLayout)findViewById(R.id.shoot_joystick_layout);

        shoot_joystick = new JoyStick(getApplicationContext(), shoot_joystick_layout, R.drawable.shoot);
        shoot_joystick.setStickSize(150, 150);
        shoot_joystick.setLayoutSize(500, 500);
        shoot_joystick.setLayoutAlpha(150);
        shoot_joystick.setStickAlpha(100);
        shoot_joystick.setOffset(90);
        shoot_joystick.setMinimumDistance(50);

        shoot_joystick_layout.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View arg0, MotionEvent arg1) {

                shoot_joystick.drawStick(arg1);

                if(arg1.getAction() == MotionEvent.ACTION_DOWN || arg1.getAction() == MotionEvent.ACTION_MOVE) {

                    int direction = shoot_joystick.get8Direction();

                    if(direction == JoyStick.STICK_UP) {
                        shoot_textview.setText("Direction : Up");
                        sendLine("SHTUPP");
                    } else if(direction == JoyStick.STICK_UPRIGHT) {
                        shoot_textview.setText("Direction : Up Right");
                        sendLine("SHTUPR");
                    } else if(direction == JoyStick.STICK_RIGHT) {
                        shoot_textview.setText("Direction : Right");
                        sendLine("SHTRIG");
                    } else if(direction == JoyStick.STICK_DOWNRIGHT) {
                        shoot_textview.setText("Direction : Down Right");
                        sendLine("SHTDOR");
                    } else if(direction == JoyStick.STICK_DOWN) {
                        shoot_textview.setText("Direction : Down");
                        sendLine("SHTDOW");
                    } else if(direction == JoyStick.STICK_DOWNLEFT) {
                        shoot_textview.setText("Direction : Down Left");
                        sendLine("SHTDOL");
                    } else if(direction == JoyStick.STICK_LEFT) {
                        shoot_textview.setText("Direction : Left");
                        sendLine("SHTLEF");
                    } else if(direction == JoyStick.STICK_UPLEFT) {
                        shoot_textview.setText("Direction : Up Left");
                        out.println("SHTUPL");
                    } else if(direction == JoyStick.STICK_NONE) {
                        shoot_textview.setText("Direction : Center");
                    }

                } else if(arg1.getAction() == MotionEvent.ACTION_UP) {
                    move_textview.setText("Direction : Center");
                }

                return true;
            }
        });

    }

    private void sendLine(String line) {
        try {
            out.println(line);
        } catch (Exception error) {
            this.finish();
        }
    }

    private String getLine() {

        String line = null;

        try {
            line = this.in.readLine();
        } catch (IOException error) {

        }

        return line;

    }

    private boolean processLine(String line) {

        boolean done = false;
        String command = "NUL";

        try {
            command = line.toUpperCase().substring(0, 3);
        } catch (Exception error) {

        }

        // Precess BYE command
        if (!done && command.equals("BYE")) {

            processBye();
            done = true;
        }

        if (!done) {

        }

        return done;

    }

    private void processBye() {

        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            v.vibrate(500);
        }

        this.finish();

    }

    @Override
    public void run() {

        while (true) {
            this.processLine(this.getLine());
        }

    }
}
