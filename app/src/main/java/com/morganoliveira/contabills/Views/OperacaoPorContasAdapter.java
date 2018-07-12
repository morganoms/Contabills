package com.morganoliveira.contabills.Views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.morganoliveira.contabills.R;
import com.morganoliveira.contabills.controller.Facade;
import com.morganoliveira.contabills.model.Operacao;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

/**
 * Created by Morgan Oliveira on 30/03/2018.
 */

public class OperacaoPorContasAdapter extends ArrayAdapter<Operacao> {

    private Context contexto;
    private List<Operacao> operacao;
    private String titulo;

    public OperacaoPorContasAdapter(Context contexto, List<Operacao> operacao, String titulo){
        super(contexto, R.layout.array_operacoes, operacao);
        this.contexto = contexto;
        this.operacao = operacao;
        this.titulo = titulo;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.array_operacoes,parent, false);

        TextView descricao = (TextView)row.findViewById(R.id.OperaDescricao);
        TextView valor  = row.findViewById(R.id.OperaValor);
        TextView data = row.findViewById(R.id.OperaData);
        TextView categ = row.findViewById(R.id.OperaCategoria);
        NumberFormat nf = new DecimalFormat("0.00");

        descricao.setText(operacao.get(position).getConta().getDescricao()+" - "+operacao.get(position).getDescricao());
        valor.setText("R$ "+String.valueOf(nf.format(operacao.get(position).getValor())));
        data.setText("Vencimento: "+operacao.get(position).getData_final().toString()+"\n Pagamento: "+operacao.get(position).getData_inicial().toString());
        categ.setText("Categoria: " + operacao.get(position).getCategoria().getDescricao() + " \nTipo: " + operacao.get(position).getTipo());

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Facade.getmInstance().RealizaOperacao(operacao.get(position));
                Bundle bundle = new Bundle();
                bundle.putString("titulo", titulo);
                Intent it = new Intent(contexto, ContasA.class);
                it.putExtras(bundle);
                contexto.startActivity(it);

            }
        });

        return row;
    }
}
