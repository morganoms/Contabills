package com.morganoliveira.contabills.Views.Fragmentos;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.morganoliveira.contabills.R;
import com.morganoliveira.contabills.Views.CategoriaAdapter;
import com.morganoliveira.contabills.Views.ClasseAux;
import com.morganoliveira.contabills.Views.Graficos;
import com.morganoliveira.contabills.Views.HistoricoMensal;
import com.morganoliveira.contabills.Views.OperacaoAdapter;
import com.morganoliveira.contabills.controller.Facade;
import com.morganoliveira.contabills.model.Operacao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Morgan Oliveira on 07/04/2018.
 */

public class GraficoDebito extends Fragment {

    private List<Operacao> ListaDebito;
    private ListView lv;
    private PieChart pieChat;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.grafico_debito, container, false);
        carregarGrafico(v);
        carregaList(v);
        return v;
    }

    private void carregaList(View v) {
        ArrayList<String > CategDeb;
        List<ClasseAux> categTotais = new ArrayList<>();
        CategDeb = Facade.getmInstance().DebitosPorCategoria(Graficos.mes, Graficos.ano);
        if(CategDeb.isEmpty() || CategDeb == null){
            Toast.makeText(getActivity(), getString(R.string.semDados), Toast.LENGTH_SHORT).show();
        }else {
            int k = 0;
            while (k < CategDeb.size()) {
                ClasseAux c = new ClasseAux(CategDeb.get(k), Double.valueOf(CategDeb.get(k + 1)));
                categTotais.add(c);
                k = k + 2;
            }
            ListView lv = v.findViewById(R.id.list_deb);
            ArrayAdapter<ClasseAux> adapter = new CategoriaAdapter(getActivity(), categTotais);
            lv.setAdapter(adapter);
        }
    }

    private void carregarGrafico(View v) {

        ArrayList<String > CategDeb;
        ArrayList<PieEntry> valoresDebitos = new ArrayList<>();
        float totalDebitos = 0;

        pieChat = v.findViewById(R.id.pie_chat_deb);
        pieChat.setUsePercentValues(true);
        pieChat.getDescription().setEnabled(false);
        pieChat.setExtraOffsets(5, 10, 5, 5);
        pieChat.setDragDecelerationFrictionCoef(0.95f);
        pieChat.setDrawHoleEnabled(true);
        pieChat.setHoleColor(Color.WHITE);
        pieChat.setTransparentCircleRadius(21f);


        totalDebitos = Facade.getmInstance().getTotalDebitos(Graficos.mes, Graficos.ano);
        CategDeb = Facade.getmInstance().DebitosPorCategoria(Graficos.mes, Graficos.ano);
        int k = 0;
        while (k < CategDeb.size()){
            float porcent =  Float.valueOf(Float.valueOf(CategDeb.get(k+1))) / totalDebitos;
            valoresDebitos.add(new PieEntry((porcent * 100), CategDeb.get(k)));
            k = k + 2;
        }


        pieChat.animateY(2000, Easing.EasingOption.EaseInOutCubic);

        PieDataSet pieDataset = new PieDataSet(valoresDebitos, "");
        pieDataset.setSliceSpace(3f);
        pieDataset.setSelectionShift(5f);
        pieDataset.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData pd = new PieData((pieDataset));
        pd.setValueTextSize(10f);
        pd.setValueTextColor(Color.YELLOW);


        pieChat.setData(pd);
    }
}
