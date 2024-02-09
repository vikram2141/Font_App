package com.TextView_App;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity {

Spinner Spinner ;
Button   button ;
TextView textView ,textsize;
    private static final String SHARED_PREF_NAME ="Mypref";
    private static final String KEY_FONT_FAMILY ="Fontfamily";
    private static final String KEY_FONT_Size ="Fontsize";
    private static final String KEY_FONT_Color ="FontColor";



    SharedPreferences sharedPreferences;
SharedPreferences.Editor editor;
SeekBar seekBar;
    int defaultColor;


    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner = findViewById(R.id.spinner);
        textView =findViewById(R.id.textView);
        seekBar =findViewById(R.id.seekbar);
        textsize =findViewById(R.id.fontsize);
        button =findViewById(R.id.btncolor);
        Button set_button =findViewById(R.id.set_button);
        EditText edit =findViewById(R.id.myText);
        sharedPreferences =getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        editor =sharedPreferences.edit();
        changeFontFamily();
        changeFontSize();

        set_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(edit.getText().toString());
            }
        });
        defaultColor = ContextCompat.getColor(MainActivity.this, com.google.android.material.R.color.design_default_color_primary);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openColorPicker();
            }
        });

    }

    private void openColorPicker() {
        AmbilWarnaDialog ambilWarnaDialog=new AmbilWarnaDialog(this, defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                defaultColor =color;
                textView.setBackgroundColor(defaultColor);
            }
        });
        ambilWarnaDialog.show();
    }


    private void changeFontSize() {
                int intFontSize = sharedPreferences.getInt(KEY_FONT_Size, 0);
                if (intFontSize != 0) {
                    textsize.setText(String.valueOf(intFontSize));
                    textView.setTextSize(intFontSize);
                    seekBar.setProgress(intFontSize);
                }

                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int position, boolean b) {
                        textsize.setText(String.valueOf(position));
                        textView.setTextSize(position);
                        editor.putInt(KEY_FONT_Size, position);
                        editor.apply();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
            }

            private void changeFontFamily() {
                final String[] fontList = new String[]{"Select Font Family", "Aclonica", "Akaya_telivig", "Architects_daughter", "Beth_ellen"};
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, fontList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Spinner.setAdapter(arrayAdapter);

                Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                        if (position != 0) {
                            editor.putInt(KEY_FONT_FAMILY, position);
                            editor.apply();
                        }
                        int intFontFamily = sharedPreferences.getInt(KEY_FONT_FAMILY, 0);
                        Spinner.setSelection(intFontFamily);

                        if (intFontFamily == 1) {
                            textView.setTypeface(ResourcesCompat.getFont(MainActivity.this, R.font.aclonica));
                        } else if (position == 2) {
                            textView.setTypeface(ResourcesCompat.getFont(MainActivity.this, R.font.akaya_telivigala));
                        } else if (position == 3) {
                            textView.setTypeface(ResourcesCompat.getFont(MainActivity.this, R.font.architects_daughter));

                        } else if (position == 4) {
                            textView.setTypeface(ResourcesCompat.getFont(MainActivity.this, R.font.beth_ellen));

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        }

