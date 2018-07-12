package com.morganoliveira.contabills.Views.Fragmentos;

import android.os.Bundle;
import android.os.OperationCanceledException;
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

public class FragmentoCredito extends Fragment {

    private List<Operacao> ListaCredito;
    private ListView lv;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmento_credito, container, false);
        ListaCredito = Facade.getmInstance().getCreditosMes(Facade.getmInstance().getUsuarioLogado(), HistoricoMensal.mes, HistoricoMensal.ano);
        if(ListaCredito.isEmpty() || ListaCredito == null){
            Toast.makeText(getActivity(), getString(R.string.semDados), Toast.LENGTH_SHORT).show();
        }else {
            lv = v.findViewById(R.id.ListCred);
            ArrayAdapter<Operacao> adapter = new OperacaoAdapter(getActivity(), ListaCredito);
            lv.setAdapter(adapter);
        }
        return v;
    }
}
