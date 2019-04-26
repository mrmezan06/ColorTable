package com.mezan.colortable;


import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    //TextView NS;

     String color[];
     String data[];
    ListView listView;
    LinearLayout ll;
    int size;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // NS=(TextView) findViewById(R.id.txt);

        listView=(ListView) findViewById(R.id.list);
        ll=(LinearLayout)findViewById(R.id.bg);
        /*btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean net= isConnectionAvailable();
                if(net){
                    getWebsite();

                }else{
                    Toast.makeText(MainActivity.this,"Error connection",Toast.LENGTH_LONG).show();
                }
            }
        });*/
        boolean net= isConnectionAvailable();
        if(net){
            getWebsite();

        }else{
            Toast.makeText(MainActivity.this,"Error connection",Toast.LENGTH_LONG).show();
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ll.setBackgroundColor(Color.parseColor(color[position]));
            }
        });

    }


    private boolean isConnectionAvailable(){

        boolean isWifi = false;
        boolean isNetwork = false;
        //String netinfo="";
        ConnectivityManager conn=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        for(Network network:conn.getAllNetworks()) {
            NetworkInfo networkInfo = conn.getNetworkInfo(network);

           // netinfo += networkInfo.toString() + "\n";

            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                if (networkInfo.isConnected()) {
                    isNetwork = true;
                  //  netinfo += "\nWifi : " + networkInfo.getExtraInfo();
                }

            }
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                if (networkInfo.isConnected()) {
                    isNetwork = true;
                  //  netinfo += "\nMobile Network : " + networkInfo.getExtraInfo();
                }
            }
        }
        return isNetwork;
    }
    private void getWebsite() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //final StringBuilder builder = new StringBuilder();

                try {
                    //Document doc = Jsoup.connect("https://www.rapidtables.com/web/color/html-color-codes.html").get();
                    Document doc = Jsoup.connect("https://www.rapidtables.com/web/color/html-color-codes.html").get();
                    /*String title = doc.title();
                    //Elements links = doc.select("a[href]");
                    Elements links = doc.select("a[title]");

                    builder.append(title).append("\n");

                    for (Element link : links) {
                       *//* builder.append("\n").append("Link : ").append(link.attr("href"))
                                .append("\n").append("Text : ").append(link.text()).append("\n");*//*
                        builder.append("\n").append("Link Title : ").append(link.attr("title"))
                                .append("\n").append("Text : ").append(link.text()).append("\n\n\n");
                    }*/

                    /*Elements elements = doc.select("h2[id]");
                    String title=doc.title();
                    builder.append(title).append("\n");
                    for(Element element:elements){
                        builder.append("\n").append("h2 id : ").append(element.attr("id"))
                                .append("\n").append("h2 value : ").append(element.text()).append("\n\n\n");*/

                    Element Div=doc.select("#doc").first();
                    Elements table = Div.select("table.dtable");
                    Elements trow = table.select("tr");
                    int j=0,k=0;
                    //size=trow.size();
                    color=new String[trow.size()];
                    data=new String[trow.size()];
                    Arrays.fill(color, "#00FF00");
                    Arrays.fill(data,"No Info\nDefault Color:#00FF00");
                    for(Element trowS:trow){
                        Elements tr=trowS.select("tr");


                        for(Element trd:tr){

                            /*Elements ttd = trd.select("th"); //select th
                            for(Element ttrd:ttd){
                                builder.append("Th :").append(ttrd.text()).append("\n");
                            }
*/


                            Elements ttdtd = trd.select("td");//select td
                            //  Elements ttdtd = trow.select("td");//select td


                        /*for(Element ttrd:ttdtd){
                            builder.append("Td :").append(ttrd.text()).append("\n");
                        }*/
                           // builder.append("\n");



                           // String value="";
                            final StringBuilder builder = new StringBuilder();
                            for(int i=1;i<ttdtd.size();i++){
                                if(i==1)
                                builder.append("Color Name :").append(ttdtd.get(i).text()).append("\n");//ttdtd.get(0) value is removed
                                if(i==3)
                                builder.append("RGB Code :").append(ttdtd.get(i).text()).append("\n");//ttdtd.get(0) value is removed

                                if(i==2){
                                    builder.append("Hash Code :").append(ttdtd.get(i).text()).append("\n");//ttdtd.get(0) value is removed

                                    final StringBuilder builder2 = new StringBuilder();
                                    color[j]=builder2.append(ttdtd.get(i).text()).toString();
                                    j++;
                                }
                            }
                            if(!builder.toString().isEmpty()){
                                data[k]=builder.toString();
                                k++;
                            }

                           // builder.append("\n\n");

                        }
                    }


                } catch (IOException e) {
                   // builder.append("Error : ").append(e.getMessage()).append("\n");
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       // NS.setText(builder.toString());
                        //
                       // NS.setText(data[0]);
                      //  NS.setText(""+size);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,R.layout.list_sample, R.id.text1, data);
                        listView.setAdapter(adapter);
                    }
                });
            }
        }).start();
    }
    /*
    shortcut check network
    */
    /*
    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
    */

}
