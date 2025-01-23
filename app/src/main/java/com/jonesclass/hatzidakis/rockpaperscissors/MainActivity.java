package com.jonesclass.hatzidakis.rockpaperscissors;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    AlertDialog.Builder dialogBuilder;
    String styleChoice = "Creative";

    Button playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getWindow().setNavigationBarColor(Color.parseColor("#FEF7FF"));


        playButton = findViewById(R.id.button_play);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                intent.putExtra("com.jonesclass.hatzidakis.RPS.OPTIONS", styleChoice);
                startActivity(intent);
            }
        });

        Button optionsButton = findViewById(R.id.button_options);
        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectOptionsDialog();
            }
        });
    }

    private void selectOptionsDialog() {
        final String[] STYLES = {"Traditional", "Creative"};

        dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Choose your game style!")
//                .setMessage("Hello World..")
                .setSingleChoiceItems(STYLES, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        styleChoice = STYLES[which];
                        if (styleChoice.equals("Traditional")) {
                            Toast.makeText(MainActivity.this, "Scared of Change?", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Feeling Adventurous?", Toast.LENGTH_SHORT).show();
                        }
                        playButton.setVisibility(View.VISIBLE);
                        dialog.dismiss();
                    }
                })
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Options Not Changed", Toast.LENGTH_SHORT).show();
                    }
                });

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

    }

}