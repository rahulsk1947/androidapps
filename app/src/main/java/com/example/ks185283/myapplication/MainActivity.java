package com.example.ks185283.myapplication;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    Button click;
    public  static TextView data;
    private SwipeRefreshLayout mSwipeRefreshLayout = null;
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        click = (Button) findViewById(R.id.loadprice);
        data = (TextView) findViewById(R.id.pricedetails);

        /*initiate thread*/
        this.mHandler = new Handler();
        m_Runnable.run();

        /*to load latest data on start up.*/
        ReadPrices process = new ReadPrices();
        process.execute();

        /*when screen is swiped, refresh data.*/
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ReadPrices process = new ReadPrices();
                process.execute();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        /*Refresh the code,when the refresh button is clicked.*/
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReadPrices process = new ReadPrices();
                process.execute();
            }
        });
    }

    /*Refresh the price automatically every one minute.*/
    private final Runnable m_Runnable = new Runnable()    {
        public void run(){
            ReadPrices process = new ReadPrices();
            process.execute();
            MainActivity.this.mHandler.postDelayed(m_Runnable, 60000);
        }
    };
}
