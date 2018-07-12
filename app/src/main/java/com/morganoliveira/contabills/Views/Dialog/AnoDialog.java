package com.morganoliveira.contabills.Views.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.morganoliveira.contabills.R;

/**
 * Created by Morgan Oliveira on 12/04/2018.
 */

public class AnoDialog extends AppCompatDialogFragment {

    private EditText ano;
    private AnoDialogListener listener;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.activity_dialog, null);
        builder.setView(v)
                .setTitle(getString(R.string.ano))
                .setNegativeButton(getText(R.string.mdtp_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String anoNovo = ano.getText().toString();
                        listener.applyText(anoNovo);
                    }
                });

        ano = v.findViewById(R.id.anoedit);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (AnoDialogListener) context;
        }catch(ClassCastException e){
            throw new ClassCastException((context.toString()));
        }
    }

    public interface AnoDialogListener{
        void applyText(String ano);
    }
}
