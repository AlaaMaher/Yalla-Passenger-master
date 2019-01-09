package com.asi.yalla_passanger_eg;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ChoosePaymentMethod extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_payment_method);
    }

    public void goToMain(View view)
    {
        startActivity(new Intent(ChoosePaymentMethod.this,MainActivity.class));
    }
}
