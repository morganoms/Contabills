package com.morganoliveira.contabills.Views.Fragmentos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.morganoliveira.contabills.R;
import com.morganoliveira.contabills.Views.HistoricoMensal;
import com.morganoliveira.contabills.Views.OperacaoAdapter;
import com.morganoliveira.contabills.controller.Facade;
import com.morganoliveira.contabills.model.Operacao;

import java.util.List;

/**
 * Created by Morgan Oliveira on 07/04/2018.
 */

public class FragmentoDebito extends Fragment {

    private List<Operacao> ListaDebito;
    private ListView lv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragmento_debito, container, false);
        ListaDebito = Facade.getmInstance().getDebitosMes(Facade.getmInstance().getUsuarioLogado(), HistoricoMensal.mes, HistoricoMensal.ano);
        if(ListaDebito.isEmpty() || ListaDebito == null){
            Toast.makeText(getActivity(), getString(R.string.semDados), Toast.LENGTH_SHORT).show();
        }else {
            lv = v.findViewById(R.id.ListDeb);
            ArrayAdapter<Operacao> adapter = new OperacaoAdapter(getActivity(), ListaDebito);
            lv.setAdapter(adapter);
        }
        return v;
    }
}
