package com.example.personnelmanagement.Fragments;


import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.personnelmanagement.Activities.MainActivity;
import com.example.personnelmanagement.Models.Constant;
import com.example.personnelmanagement.Models.Methods;
import com.example.personnelmanagement.R;
import com.turkialkhateeb.materialcolorpicker.ColorChooserDialog;
import com.turkialkhateeb.materialcolorpicker.ColorListener;

public class AppSettings extends Fragment
        implements View.OnClickListener, AdapterView.OnItemSelectedListener, SeekBar.OnSeekBarChangeListener {
    Spinner spinnerFont;
    //    String[] fontName = {"B Kamran Bold.ttf", "0 Bardiya.ttf", "0 Davat.ttf", "B Mitra.ttf"};
    String[] fontName = {"andlso.ttf", "Gabriola.ttf", "times.ttf"};
    ArrayAdapter arrayAdapterFont;
    ImageView btnSave, btnCancel;
    SeekBar seekBarFontSize;
    TextView txtSample;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Button btnColor;
    Methods methods;
    Constant constant;
    String font;
    Typeface typeface;
    float size;

    public AppSettings() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_app_settings, container, false);

        spinnerFont = v.findViewById(R.id.spinner_font);
        seekBarFontSize = v.findViewById(R.id.seek_bar_font_size);
        txtSample = v.findViewById(R.id.txt_sample_font);
        btnSave = v.findViewById(R.id.btn_save_settings);
        btnCancel = v.findViewById(R.id.btn_cancel_settings);
        btnColor = v.findViewById(R.id.button_color);

        arrayAdapterFont = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, fontName);
        arrayAdapterFont.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFont.setAdapter(arrayAdapterFont);

        btnSave.setColorFilter(constant.color);
        btnCancel.setColorFilter(constant.color);

        spinnerFont.setOnItemSelectedListener(this);
        seekBarFontSize.setOnSeekBarChangeListener(this);
        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnColor.setOnClickListener(this);

        methods = new Methods();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = sharedPreferences.edit();

        colorize();

        return v;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void colorize() {
        ShapeDrawable d = new ShapeDrawable(new OvalShape());
        d.setBounds(58, 58, 58, 58);

        d.getPaint().setStyle(Paint.Style.FILL);
        d.getPaint().setColor(Constant.color);

        btnColor.setBackground(d);

        size = constant.fontSize;
        seekBarFontSize.setProgress((int) size);
        txtSample.setTextSize(size);

        font = constant.fontStyle;
        typeface = Typeface.createFromAsset(getActivity().getApplicationContext().getAssets(), "fonts/" + font);
        txtSample.setTypeface(typeface);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_color:
                ColorChooserDialog dialog = new ColorChooserDialog(getContext());
                dialog.setColorListener(new ColorListener() {
                    @Override
                    public void OnColorClick(View v, int color) {
                        constant.color = color;
                        colorize();
                    }
                });
                dialog.show();
                break;
            case R.id.btn_save_settings:
                methods.setColorTheme();
                constant.fontSize = seekBarFontSize.getProgress();
                constant.fontStyle = font;
                editor.putInt("color", constant.color);
                editor.putInt("theme", constant.theme);
                editor.putFloat("fontSize", constant.fontSize);
                editor.putString("fontStyle", constant.fontStyle);
                editor.commit();

                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.btn_cancel_settings:
                intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {

            case R.id.spinner_font:
                font = fontName[position];
                typeface = Typeface.createFromAsset(getActivity().getApplicationContext().getAssets(), "fonts/" + font);
                txtSample.setTypeface(typeface);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        txtSample.setTextSize(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}