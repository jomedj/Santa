package com.example.johann.santa;

import android.app.Activity;
import android.widget.TextView;
import android.os.Bundle;

public class EnfantDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_enfant);

        Enfant p = this.getIntent().getExtras().getParcelable("selected");

        TextView tv1 = (TextView)this.findViewById(R.id.textView_detail_prenom);
        tv1.setText(String.valueOf(p.getPrenom()));

        TextView tv2 = (TextView)this.findViewById(R.id.textView_detail_sexe);
        tv2.setText(String.valueOf(p.getSexe()));

    }
}
