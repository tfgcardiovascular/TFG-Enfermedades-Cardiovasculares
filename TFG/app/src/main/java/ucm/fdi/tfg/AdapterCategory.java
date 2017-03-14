package ucm.fdi.tfg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


//public class AdapterCategory extends BaseAdapter {
public class AdapterCategory extends ArrayAdapter <Medico> {


    //public AdapterCategory (Activity activity, ArrayList<Category> items) {
    public AdapterCategory( Context activity, List< Medico > objects )
    {


        super( activity, 0, objects );

        System.out.println( "adapter category system" );
        System.out.println( activity );
        System.out.println( objects );
        System.out.println( "fin adapter category " );


    }

    //@Override
    /*public int getCount() {
        return items.size();
    }

    public void clear() {
        items.clear();
    }

    public void addAll(ArrayList<Category> category) {
        for (int i = 0; i < category.size(); i++) {
            items.add(category.get(i));
        }
    }

    @Override
    public Object getItem(int arg0) {
        return items.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }*/

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inf = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (null == convertView) {
            convertView = inf.inflate(R.layout.activity_item_category, parent, false);
        }

        Medico dir = getItem(position);

        TextView nombre = (TextView) convertView.findViewById(R.id.nombre);
        nombre.setText(dir.getNombre());

        TextView colegiado = (TextView) convertView.findViewById(R.id.colegiado);
        colegiado.setText(dir.getColegiado());

        TextView mail = (TextView) convertView.findViewById(R.id.mail);
        mail.setText(dir.getMail());

        //ImageView imagen = (ImageView) v.findViewById(R.id.imageView);
        //imagen.setImageDrawable(dir.getImage());

        return convertView;
    }
}
