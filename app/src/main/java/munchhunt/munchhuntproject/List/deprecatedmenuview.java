//package munchhunt.munchhuntproject.List;
//
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageButton;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//
//import munchhunt.munchhuntproject.Map.MenuListAdapter;
//import munchhunt.munchhuntproject.R;
//
//public class MenuView extends AppCompatActivity implements Serializable {
//    ListView menuListView;
//    TextView restNameMenu;
//    ImageButton return1;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.maplist_menu);
//        Bundle b = this.getIntent().getExtras();
//        menuListView = (ListView)findViewById(R.id.menuListView);
//        return1 = (ImageButton) findViewById(R.id.returnMenu);
//        restNameMenu = (TextView) findViewById(R.id.restNameMenu);
//
//
//        if (b != null){
//             ArrayList<Menu> r1 = (ArrayList)b.getSerializable("obj");
//            String restName1 = b.getString("name").toString() + " Menu";
//            restNameMenu.setText("");
//            restNameMenu.setText(restName1);
//
//            // ArrayList<String> usernames = (ArrayList<String>)b.getSerializable("name");
//
//            MenuListAdapter menuList = new MenuListAdapter(this, R.layout.maplist_adapter_menuview, r1 );
//            menuListView.setAdapter(menuList);
//        }
//
//        return1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//    }
//}
