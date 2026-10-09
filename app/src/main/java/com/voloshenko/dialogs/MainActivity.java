package com.voloshenko.dialogs;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

/**
 * Четыре кнопки: два Toast, диалог с иконкой и тест с множественным выбором.
 */
public class MainActivity extends AppCompatActivity
        implements ButtonThreeDialogFragment.Listener {

    private static final String STATE_RED = "buttons_red";
    private static final String STATE_HIDDEN = "buttons_hidden";
    private static final String STATE_CORRECT = "quiz_correct";

    private static final String TAG_BUTTON_THREE = "button3";

    private Button[] buttons;
    private ImageView correctIcon;
    private TextView resultView;

    private boolean buttonsRed;
    private boolean buttonsHidden;
    private boolean quizCorrect;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttons = new Button[]{
                findViewById(R.id.button1),
                findViewById(R.id.button2),
                findViewById(R.id.button3),
                findViewById(R.id.button4)
        };
        correctIcon = findViewById(R.id.correctIcon);
        resultView = findViewById(R.id.resultView);

        // LENGTH_SHORT держится 2 секунды (2000 мс), LENGTH_LONG — 3,5 секунды (3500 мс).
        buttons[0].setOnClickListener(v ->
                Toast.makeText(this, R.string.toast_button_1, Toast.LENGTH_SHORT).show());
        buttons[1].setOnClickListener(v ->
                Toast.makeText(this, R.string.toast_button_2, Toast.LENGTH_LONG).show());
        buttons[2].setOnClickListener(v -> showDialog(new ButtonThreeDialogFragment(), TAG_BUTTON_THREE));
        buttons[3].setOnClickListener(v -> showQuiz());

        if (savedInstanceState != null) {
            buttonsRed = savedInstanceState.getBoolean(STATE_RED);
            buttonsHidden = savedInstanceState.getBoolean(STATE_HIDDEN);
            quizCorrect = savedInstanceState.getBoolean(STATE_CORRECT);
            renderState();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_RED, buttonsRed);
        outState.putBoolean(STATE_HIDDEN, buttonsHidden);
        outState.putBoolean(STATE_CORRECT, quizCorrect);
    }

    @Override
    public void onPaintButtonsRed() {
        buttonsRed = true;
        renderState();
    }

    @Override
    public void onButtonThreeCancelled() {
        Toast.makeText(this, R.string.toast_closed, Toast.LENGTH_SHORT).show();
    }

    private void showQuiz() {
        final boolean[] checked = new boolean[Quiz.ITEMS.length];
        // setMessage вместе со списком прячет пункты, поэтому здесь только множественный выбор.
        new AlertDialog.Builder(this)
                .setTitle(R.string.quiz_title)
                .setMultiChoiceItems(Quiz.ITEMS, checked,
                        (dialog, which, isChecked) -> checked[which] = isChecked)
                .setPositiveButton(R.string.check, (dialog, which) -> onQuizChecked(checked))
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    private void onQuizChecked(boolean[] checked) {
        if (Quiz.isCorrect(checked)) {
            quizCorrect = true;
            renderState();
            Toast.makeText(this, R.string.toast_correct, Toast.LENGTH_LONG).show();
        } else {
            quizCorrect = false;
            buttonsHidden = true;
            renderState();
        }
    }

    private void showDialog(DialogFragment dialog, String tag) {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.findFragmentByTag(tag) == null) {
            dialog.show(manager, tag);
        }
    }

    private void renderState() {
        for (Button button : buttons) {
            if (buttonsRed) {
                button.setTextColor(Color.RED);
            }
            button.setVisibility(buttonsHidden ? View.INVISIBLE : View.VISIBLE);
        }

        correctIcon.setVisibility(quizCorrect ? View.VISIBLE : View.GONE);
        if (quizCorrect) {
            resultView.setText(R.string.result_correct);
            resultView.setTextColor(ContextCompat.getColor(this, R.color.blue));
            resultView.setVisibility(View.VISIBLE);
        } else if (buttonsHidden) {
            resultView.setText(R.string.result_wrong);
            resultView.setTextColor(ContextCompat.getColor(this, R.color.ink));
            resultView.setVisibility(View.VISIBLE);
        } else {
            resultView.setVisibility(View.GONE);
        }
    }
}
