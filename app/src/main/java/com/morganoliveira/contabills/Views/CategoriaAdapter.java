package com.morganoliveira.contabills.Views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.morganoliveira.contabills.R;
import com.morganoliveira.contabills.model.Categoria;
import com.morganoliveira.contabills.model.Operacao;

import java.util.List;

/**
 * Created by Morgan Oliveira on 08/04/2018.
 */

public class CategoriaAdapter extends ArrayAdapter<ClasseAux> {

    private Context contexto;
    private List<ClasseAux> categTotais;

    public CategoriaAdapter(Context contexto, List<ClasseAux> categorias) {
        super(contexto, R.layout.array_categorias, categorias);
        this.contexto = contexto;
        this.categTotais = categorias;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.array_categorias, parent, false);

        TextView descricao = (TextView)row.findViewById(R.id.CategDesc);
        TextView valor  = row.findViewById(R.id.CategValor);

        descricao.setText(categTotais.get(position).getDescricao());
        valor.setText("R$ "+String.valueOf(categTotais.get(position).getValor())+"0");

        return row;
    }

}
