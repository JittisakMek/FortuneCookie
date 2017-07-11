package com.egci428.fortunecookie;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static ListView list_view;
    private CommentsDataSource dataSource;
    ArrayAdapter<Comment> cookieArrayAdapter;
    List<Comment> values;
//    private static Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLUE));

        dataSource = new CommentsDataSource(this);
        dataSource.open(); //connect to database
        values = dataSource.getAllComments();
        cookieArrayAdapter = new CookieArrayAdapter(this,0,values);
        list_view = (ListView)findViewById(R.id.listCookie);
        list_view.setAdapter(cookieArrayAdapter);
        //dataSource.close();

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, final int position, long id)
            {
                view.animate().setDuration(2000).alpha(0).withEndAction(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Comment comment = values.get(position);
                        ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) list_view.getAdapter();
                        if(list_view.getAdapter().getCount()>0){
                            dataSource.deleteComment(comment);
                            adapter.remove(comment);
                        }
                        adapter.notifyDataSetChanged();
                        view.setAlpha(1);
                    }
                });
            }
        });

//        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//            @Override
//            public

//        date_data.add(getIntent().getStringExtra("date"));
//        result_data.add(getIntent().getStringExtra("result"));
//        OnClickButtonListener();
    }

    class CookieArrayAdapter extends ArrayAdapter<Comment>{
        Context context;
        List<Comment> objects;

        public CookieArrayAdapter(Context context,int resource, List<Comment> objects){
            super(context,resource,objects);
            this.context = context;
            this.objects = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            Comment Comment = objects.get(position);
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
             View view = inflater.inflate(R.layout.cookie_list,null);

            ImageView image = (ImageView)view.findViewById(R.id.imageCookie);
            TextView txt = (TextView)view.findViewById(R.id.descText);
            TextView dateText = (TextView)view.findViewById(R.id.dateText);

            dateText.setText("Date: "+Comment.getDate());
            System.out.println("getCount ="+Comment.getCount());
            if (Comment.getCount() == 1) {


                image.setImageResource(R.drawable.opened_cookie_gradea);
                txt.setTextColor(Color.parseColor("#5ca0d1"));
                txt.setText("You will get A");
                System.out.println("case 1");
            }
             else if (Comment.getCount() == 2) {
                image.setImageResource(R.drawable.opened_cookie_lucky);
                txt.setTextColor(Color.parseColor("#5ca0d1"));
                txt.setText("Result: You are lucky");
                System.out.println("case 2");
            }
            else if(Comment.getCount() == 3) {

                image.setImageResource(R.drawable.opened_cookie_panic);
                txt.setTextColor(Color.parseColor("#F8984B"));
                txt.setText("Result: Don't Panic !!!");
                System.out.println("case 3");
            }

             else if(Comment.getCount() == 4) {
                image.setImageResource(R.drawable.opened_cookie_surprise);
                txt.setTextColor(Color.parseColor("#5ca0d1"));
                txt.setText("Something will surprise you today");
                System.out.println("case 4");
            }
             else if(Comment.getCount() == 5){
                    image.setImageResource(R.drawable.opened_cookie_work);
                    txt.setTextColor(Color.parseColor("#F8984B"));
                    txt.setText("Result: Work Harder !!!");
                    System.out.println("case 5");
            }

            return view;
        }
    }


//    public void listView(){
//        list_view = (ListView)findViewById(R.id.listCookie);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.cookie_list,date_data);
//        list_view.setAdapter(adapter);
//    }

//    public void OnClickButtonListener(){
//        addBtn = (Button)findViewById(R.id.addBtn);
//        addBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent("com.egci428.fortunecookie.NewFortuneCookie");
//                startActivity(intent);
//
////                Intent i = new Intent(MainActivity.this, NewFortuneCookie.class);
////                i.putExtra("cookie", "hello");
////                startActivity(i);
//            }
//        });
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    public void onClickMenu(MenuItem item){
        Intent intent = new Intent("com.egci428.fortunecookie.NewFortuneCookie");
        startActivity(intent);
    }

}
