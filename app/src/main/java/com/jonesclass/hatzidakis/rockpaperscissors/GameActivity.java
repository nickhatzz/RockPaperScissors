package com.jonesclass.hatzidakis.rockpaperscissors;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

import pl.droidsonroids.gif.GifImageView;

public class GameActivity extends AppCompatActivity {

    final String TAG = "RPSGame";
    TextView messageTextView;
    ImageView aiChoiceImageView;
    ImageButton rockButton;
    ImageButton paperButton;
    ImageButton scissorsButton;
    Button newGameButton;
    GifImageView confettiGif;
    String styleChoice;
    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;


        });
        styleChoice = getIntent().getStringExtra("com.jonesclass.hatzidakis.RPS.OPTIONS");

        getWindow().setNavigationBarColor(Color.parseColor("#FEF7FF"));

        // MISC
        messageTextView = findViewById(R.id.textView_message);
        confettiGif = findViewById(R.id.gifImageView_confetti);

        // NEW GAME BUTTON
        newGameButton = findViewById(R.id.button_newGame);
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

        // AI IMAGE
        aiChoiceImageView = findViewById(R.id.imageView_aiChoice);
        aiChoiceImageView.setImageResource(getAsset("hidden"));

        // ROCK BUTTON
        rockButton = findViewById(R.id.imageButton_rock);
        rockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitMove("rock");
            }
        });

        // PAPER BUTTON
        paperButton = findViewById(R.id.imageButton_paper);
        paperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitMove("paper");
            }
        });

        // SCISSORS BUTTON
        scissorsButton = findViewById(R.id.imageButton_scissors);
        scissorsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitMove("scissors");
            }
        });

        resetGame();
    }

    public int getAsset(String name) {
        switch (name) {
            case "rock":
                if (styleChoice.equals("Creative")) {
                    return R.drawable.rock_creative;
                } else {
                    return R.drawable.rock_traditional;
                }
            case "paper":
                if (styleChoice.equals("Creative")) {
                    return R.drawable.paper_creative;
                } else {
                    return R.drawable.paper_traditional;
                }
            case "scissors":
                if (styleChoice.equals("Creative")) {
                    return R.drawable.scissors_creative;
                } else {
                    return R.drawable.scissors_traditional;
                }
            case "hidden":
                if (styleChoice.equals("Creative")) {
                    return R.drawable.hidden_creative;
                } else {
                    return R.drawable.hidden_traditional;
                }
            default:
                return -1;
        }
    }

    public void resetGame() {
        messageTextView.setText("Pick a move!");
        newGameButton.setVisibility(View.INVISIBLE);
        confettiGif.setVisibility(View.GONE);

        rockButton.setImageResource(getAsset("rock"));
        paperButton.setImageResource(getAsset("paper"));
        scissorsButton.setImageResource(getAsset("scissors"));
        aiChoiceImageView.setImageResource(getAsset("hidden"));

        rockButton.setScaleX(1);
        rockButton.setScaleY(1);
        rockButton.setImageAlpha(255);
        rockButton.setEnabled(true);

        paperButton.setScaleX(1);
        paperButton.setScaleY(1);
        paperButton.setImageAlpha(255);
        paperButton.setEnabled(true);

        scissorsButton.setScaleX(1);
        scissorsButton.setScaleY(1);
        scissorsButton.setImageAlpha(255);
        scissorsButton.setEnabled(true);
    }

    public void submitMove(String move) {
        int aiChoice = random.nextInt(3); // 0 = rock; 1 = paper; 2 = scissors
        newGameButton.setVisibility(View.VISIBLE);
        // changing aiChoiceImageView
        switch (aiChoice) {
            case 0:
                aiChoiceImageView.setImageResource(getAsset("rock"));
                break;
            case 1:
                aiChoiceImageView.setImageResource(getAsset("paper"));
                break;
            case 2:
                aiChoiceImageView.setImageResource(getAsset("scissors"));
                break;
        }

        // changing user choice buttons
        switch (move) {
            case "rock":
                rockButton.setEnabled(false);
                rockButton.setScaleX(1.5F);
                rockButton.setScaleY(1.5F);

                paperButton.setEnabled(false);
                paperButton.setImageAlpha(50);

                scissorsButton.setEnabled(false);
                scissorsButton.setImageAlpha(50);
                break;
            case "paper":
                rockButton.setEnabled(false);
                rockButton.setImageAlpha(50);

                paperButton.setEnabled(false);
                paperButton.setScaleX(1.5F);
                paperButton.setScaleY(1.5F);

                scissorsButton.setEnabled(false);
                scissorsButton.setImageAlpha(50);
                break;
            case "scissors":
                rockButton.setEnabled(false);
                rockButton.setImageAlpha(50);

                paperButton.setEnabled(false);
                paperButton.setImageAlpha(50);

                scissorsButton.setEnabled(false);
                scissorsButton.setScaleX(1.5F);
                scissorsButton.setScaleY(1.5F);
                break;
        }

        // who wins?
        switch (move) {
            case "rock":
                switch (aiChoice) {
                    case 0:
                        // DRAW
                        draw();
                        break;
                    case 1:
                        // AI WIN
                        aiWin();
                        break;
                    case 2:
                        // USER WIN
                        userWin();
                        break;
                }
                break;
            case "paper":
                switch (aiChoice) {
                    case 0:
                        // USER WIN
                        userWin();
                        break;
                    case 1:
                        // DRAW
                        draw();
                        break;
                    case 2:
                        // AI WIN
                        aiWin();
                        break;
                }
                break;
            case "scissors":
                switch (aiChoice) {
                    case 0:
                        // AI WIN
                        aiWin();
                        break;
                    case 1:
                        // USER WIN
                        userWin();
                        break;
                    case 2:
                        // DRAW
                        draw();
                        break;
                }
                break;
        }
    }

    public void userWin() {
        Log.d(TAG, "User win!");
        messageTextView.setText("You win!");
        confettiGif.setVisibility(View.VISIBLE);
    }

    public void aiWin() {
        Log.d(TAG, "AI win!");
        messageTextView.setText("You lose!");
    }

    public void draw() {
        Log.d(TAG, "Draw!");
        messageTextView.setText("DRAW!");
    }

}