package com.jacobgreenland.igttask;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;

/**
 * Created by Jacob on 21/06/16.
 */
public class DetailsFragment extends Fragment{

    View v;
    ImageView artwork;
    TextView gameName;
    TextView jackpot;
    TextView date;
    Button addToBag;

    public Currency currency;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v =inflater.inflate(R.layout.game_details,container,false);

        return v;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        gameName = (TextView) v.findViewById(R.id.sName);
        jackpot = (TextView) v.findViewById(R.id.sJackpot);
        date = (TextView) v.findViewById(R.id.sDate);

        currency = Currency.getInstance(MainActivity.loadedGame.getCurrency());

        Date date2 = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        try {
            date2 = format.parse(MainActivity.chosenGame.getDate());
            Log.d("date test", date2.toString());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        gameName.setText(MainActivity.chosenGame.getName());
        jackpot.setText(currency.getSymbol() + MainActivity.chosenGame.getJackpot().toString());
        date.setText("" + date2.toString());

        Log.d("test", "Adapter should have been set by now!");
    }

}
