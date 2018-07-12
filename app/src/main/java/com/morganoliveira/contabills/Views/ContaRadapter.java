package com.morganoliveira.contabills.Views;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.morganoliveira.contabills.R;
import com.morganoliveira.contabills.model.Conta;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

/**
 * Created by Morgan Oliveira on 30/03/2018.
 */

class ContaRadapter extends RecyclerView.Adapter<ContaRadapter.ViewHolder> {
    private List<Conta> contas;

    public ContaRadapter(List<Conta> contas) {
        this.contas = contas;
    }

    @Override
    public ContaRadapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.array_contas,parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ContaRadapter.ViewHolder holder, int position) {
        holder.nomeConta.setText(contas.get(position).getDescricao());
        NumberFormat nf = new DecimalFormat("0.00");

        holder.valor.setText(String.valueOf(nf.format(contas.get(position).getSaldo())));

    }

    @Override
    public int getItemCount() {
        return contas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView nomeConta;
        private TextView valor;

        public ViewHolder(View itemView) {
            super(itemView);
            nomeConta = (TextView)itemView.findViewById(R.id.Nome_Conta);
            valor  = itemView.findViewById(R.id.valorArray);
        }
    }
}
