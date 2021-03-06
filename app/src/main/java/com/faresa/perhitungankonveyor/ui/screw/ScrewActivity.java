package com.faresa.perhitungankonveyor.ui.screw;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.faresa.perhitungankonveyor.R;
import com.faresa.perhitungankonveyor.model.DataMaterial;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Objects;

public class ScrewActivity extends AppCompatActivity implements MaterialAdapter.OnItemClickListener {

    double Cfo = 1.1;
    double Fm = 1.4;
    double Ff = 1;
    double Fp = 1;
    double n1 = 0.94;
    double n2 = 0.94;
    double Fo = 1;
    double angle = 15;

    DataMaterial data;
    private String[] materialData;
    private String[] weightData;
    private String[] materialfacData;
    private String[] componentData;
    private String[] seriesData;

    RecyclerView recyclerView;
    MaterialAdapter adapter;
    private ArrayList<DataMaterial> list;
    TextInputLayout etKapasitas, etPanjang, etScrew1, etScrew2;
    TextInputLayout etMaterial, etWeight, etMaterialfac, etComponent, etSeries, etC1, etFb, etFd, etNormal, etEquivalent, etSpeed, etLo, etL;
    Button btnMaterial, btnHasil, btnReset, btnDone;
    CardView cardInput, cardHasil;
    LinearLayout linearHitung;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screw);
        data = getIntent().getParcelableExtra("MATERIAL_DATA");
        etMaterial = findViewById(R.id.materialName);
        etWeight = findViewById(R.id.materialWeight);
        etMaterialfac = findViewById(R.id.materialFactor);
        etComponent = findViewById(R.id.materialComponent);
        etSeries = findViewById(R.id.materialSeries);
        cardInput = findViewById(R.id.cardInput);
        etC1 = findViewById(R.id.materialC1);
        etFb = findViewById(R.id.materialFb);
        etFd = findViewById(R.id.materialFd);
        linearHitung = findViewById(R.id.linearHitung);
        cardHasil = findViewById(R.id.cardHasil);
        btnHasil = findViewById(R.id.btnHitung);
        btnReset = findViewById(R.id.btnReset);
        btnDone = findViewById(R.id.btnDone);

        etKapasitas = findViewById(R.id.screwKapasitas);
        etPanjang = findViewById(R.id.screwPanjang);
        etScrew1 = findViewById(R.id.screwJumlah);
        etScrew2 = findViewById(R.id.screwJumlah2);


        etNormal = findViewById(R.id.hasilNormal);
        etEquivalent = findViewById(R.id.hasilEquivalent);
        etSpeed = findViewById(R.id.hasilSpeed);
        etLo = findViewById(R.id.hasilLo);
        etL = findViewById(R.id.hasilL);

        etMaterial.getEditText().setFocusable(false);
        etWeight.getEditText().setFocusable(false);
        etMaterialfac.getEditText().setFocusable(false);
        etComponent.getEditText().setFocusable(false);
        etSeries.getEditText().setFocusable(false);
        etC1.getEditText().setFocusable(false);
        etFb.getEditText().setFocusable(false);
        etFd.getEditText().setFocusable(false);

        etNormal.getEditText().setFocusable(false);
        etEquivalent.getEditText().setFocusable(false);
        etSpeed.getEditText().setFocusable(false);
        etLo.getEditText().setFocusable(false);
        etL.getEditText().setFocusable(false);

        try {
            Objects.requireNonNull(etMaterial.getEditText()).setText(data.getMaterial());
            Objects.requireNonNull(etWeight.getEditText()).setText(data.getWeight());
            Objects.requireNonNull(etMaterialfac.getEditText()).setText(data.getMaterialfac());
            Objects.requireNonNull(etComponent.getEditText()).setText(data.getComponent());
            Objects.requireNonNull(etSeries.getEditText()).setText(data.getSeries());
        } catch (Exception e) {
            Log.d("catac", "catch");
        }
        String mater = etMaterial.getEditText().getText().toString();
        String getC1 = etComponent.getEditText().getText().toString();
        String getFd = etComponent.getEditText().getText().toString();
        String getFb = etSeries.getEditText().getText().toString();
        Log.d("mater", mater);
        if (mater.equals("")) {
            cardInput.setVisibility(View.GONE);
            linearHitung.setVisibility(View.GONE);
            cardHasil.setVisibility(View.GONE);
        } else {
            cardInput.setVisibility(View.VISIBLE);
            linearHitung.setVisibility(View.VISIBLE);
            cardHasil.setVisibility(View.VISIBLE);
        }
        if (!getC1.equals("")) {
            switch (getC1) {
                case "10":
                    etC1.getEditText().setText("7");
                    break;
                case "20":
                    etC1.getEditText().setText("62.5");
                    break;
                case "30":
                    etC1.getEditText().setText("109");
                    break;
            }
        }
        if (!getFd.equals("")) {
            switch (getFd) {
                case "10":
                    etFd.getEditText().setText("140");
                    break;
                case "20":
                    etFd.getEditText().setText("165");
                    break;
                case "30":
                    etFd.getEditText().setText("180");
                    break;
            }
        }
        if (!getFb.equals("")) {
            switch (getFb) {
                case "A":
                    etFb.getEditText().setText("1");
                    break;
                case "B":
                    etFb.getEditText().setText("1.7");
                    break;
                case "C":
                    etFb.getEditText().setText("2");
                    break;
                case "D":
                    etFb.getEditText().setText("4.4");
                    break;
            }
        }
        btnReset.setOnClickListener(view -> {
            Intent intent = new Intent(ScrewActivity.this, ScrewActivity.class);
            startActivity(intent);
            finish();
        });
        btnMaterial = findViewById(R.id.btnMaterial);
        btnMaterial.setOnClickListener(view -> {
            final Dialog dialog = new Dialog(ScrewActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.activity_material);
            recyclerView = dialog.findViewById(R.id.rv);
            adapter = new MaterialAdapter(getApplicationContext(), list);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));
            recyclerView.setAdapter(adapter);
            prepare();
            addItem();
            dialog.show();
        });
        btnHasil.setOnClickListener(view -> {
            try {
                String getKapasitas = etKapasitas.getEditText().getText().toString();
                String getWeight = etWeight.getEditText().getText().toString();
                double kapasitas = Double.parseDouble(getKapasitas);
                double weight = Double.parseDouble(getWeight);
                double normal = ((kapasitas * 1000) / 2.2) / weight;
                etNormal.getEditText().setText(Double.toString(normal));

                String getNormal = etNormal.getEditText().getText().toString();
                double normalD = Double.parseDouble(getNormal);
                double hasilEq = Cfo*normalD;
                etEquivalent.getEditText().setText(Double.toString(hasilEq));

                String getC1D = etC1.getEditText().getText().toString();
                double C1d = Double.parseDouble(getC1D);
                double hasilSpeed = (normalD*Cfo)/C1d;
                etSpeed.getEditText().setText(Double.toString(hasilSpeed));

                String getPanjang = etPanjang.getEditText().getText().toString();
                double panjang = Double.parseDouble(getPanjang);
                double hasilLo = panjang/0.3048;
                etLo.getEditText().setText(Double.toString(hasilLo));

                String getScrew1 = etScrew1.getEditText().getText().toString();
                String getScrew2 = etScrew2.getEditText().getText().toString();
                double screw1 = Double.parseDouble(getScrew1);
                double screw2 = Double.parseDouble(getScrew2);
                double hasilL = screw1*screw2;
                etL.getEditText().setText(Double.toString(hasilL));
            }catch (Exception e){
                Toast.makeText(this, "Mohon isi semua data", Toast.LENGTH_LONG).show();
            }

        });
        btnDone.setOnClickListener(view -> {
            if (validateNormal() | validateScrew()){
                String getFd1 = etFd.getEditText().getText().toString();
                String getSpeed = etSpeed.getEditText().getText().toString();
                String getL = etL.getEditText().getText().toString();
                String getFb1 = etFb.getEditText().getText().toString();
                double doubleFd = Double.parseDouble(getFd1);
                double doubleSpeed = Double.parseDouble(getSpeed);
                double doubleL = Double.parseDouble(getL);
                double doubleFb = Double.parseDouble(getFb1);
                double hasilHPF = (doubleL*doubleSpeed*doubleFd*doubleFb)/1000000;
                Log.d("hpf", String.valueOf(hasilHPF));

                String getEq = etEquivalent.getEditText().getText().toString();
                String getW = etWeight.getEditText().getText().toString();
                double doubleEq = Double.parseDouble(getEq);
                double doubleW = Double.parseDouble(getW);
                double hasilHPM = (doubleEq*doubleL*doubleW*Ff*Fm*Fp)/1000000;
                Log.d("hpm", String.valueOf(hasilHPM));

                double hasilHP = ((hasilHPF+hasilHPM)*Fo)/(n1*n2);
                Log.d("hp", String.valueOf(hasilHP));
                double hasilHP2 = hasilHP/1.341;
                Log.d("hp2", String.valueOf(hasilHP2));

                double hasilTorque = (63025*hasilHP)/doubleSpeed;
                Log.d("ht", String.valueOf(hasilTorque));
                double hasilTorque2 = (716.2*hasilHP)/doubleSpeed;
                Log.d("ht2", String.valueOf(hasilTorque2));
                //=============
                double inclineHpH = (doubleEq*doubleW*angle)/(33000*60);
                Log.d("hph", String.valueOf(inclineHpH));
                double inclineHP = ((hasilHPF+hasilHPM+inclineHpH)*Fo)/(n1*n2);
                Log.d("hp", String.valueOf(inclineHP));
                double inclineHP2 = inclineHP/1.341;
                Log.d("hp2", String.valueOf(inclineHP2));


                Intent intent = new Intent(ScrewActivity.this, HasilScrewActivity.class);
                intent.putExtra("hpf", String.valueOf(hasilHPF));
                intent.putExtra("hpm", String.valueOf(hasilHPM));
                intent.putExtra("hp", String.valueOf(hasilHP));
                intent.putExtra("hp2", String.valueOf(hasilHP2));
                intent.putExtra("torque", String.valueOf(hasilTorque));
                intent.putExtra("torque2", String.valueOf(hasilTorque2));
                intent.putExtra("angle", String.valueOf(angle));
                intent.putExtra("hph", String.valueOf(inclineHpH));
                intent.putExtra("inc_hp", String.valueOf(inclineHP));
                intent.putExtra("inc_hp2", String.valueOf(inclineHP2));
                startActivity(intent);
            }
        });


    }

    private void prepare() {
        materialData = getResources().getStringArray(R.array.array_material);
        weightData = getResources().getStringArray(R.array.array_weight);
        materialfacData = getResources().getStringArray(R.array.array_materialfac);
        componentData = getResources().getStringArray(R.array.array_component);
        seriesData = getResources().getStringArray(R.array.array_series);

    }

    private void addItem() {
        ArrayList<DataMaterial> list = new ArrayList<>();
        for (int i = 0; i < materialData.length; i++) {
            DataMaterial items = new DataMaterial();
            items.setMaterial(materialData[i]);

            list.add(items);
        }
        adapter = new MaterialAdapter(getApplicationContext(), list);
        adapter.setOnItemClickListener(ScrewActivity.this);
        recyclerView.setAdapter(adapter);
    }

    public void onItemClick(int position) {
        DataMaterial data = new DataMaterial();
        data.setMaterial(materialData[position]);
        data.setWeight(weightData[position]);
        data.setMaterialfac(materialfacData[position]);
        data.setComponent(componentData[position]);
        data.setSeries(seriesData[position]);
        Intent intent = new Intent(getApplication(), ScrewActivity.class);
        intent.putExtra("MATERIAL_DATA", data);
        startActivity(intent);
        finish();
    }
    private boolean validateNormal() {
        String email = (etNormal.getEditText()).getText().toString().trim();

        if (email.isEmpty()) {
            etNormal.setError("Tidak boleh kosong");
            return false;
        }  else {
            etNormal.setError(null);
            return true;
        }
    }

    private boolean validateScrew() {
        String pw = etL.getEditText().getText().toString().trim();

        if (pw.isEmpty()) {
            etL.setError("Tidak boleh kosong");
            return false;
        } else {
            etL.setError(null);
            return true;
        }
    }
}
