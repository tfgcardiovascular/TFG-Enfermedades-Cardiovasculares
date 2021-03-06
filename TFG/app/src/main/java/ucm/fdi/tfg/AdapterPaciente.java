package ucm.fdi.tfg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class AdapterPaciente extends ArrayAdapter <Paciente> {

    public AdapterPaciente(Context activity, List< Paciente > objects ){
        super( activity, 0, objects );

        //System.out.println( "adapter category system" );
        //System.out.println( activity );
        //System.out.println( objects );
        //System.out.println( "fin adapter category " );
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inf = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (null == convertView) {
            convertView = inf.inflate(R.layout.activity_item_paciente, parent, false);
        }

        Paciente dir = getItem(position);

        TextView paciente = (TextView) convertView.findViewById(R.id.paciente);
        paciente.setText(dir.getId());

        TextView sexo = (TextView) convertView.findViewById(R.id.height);
        sexo.setText(dir.getSexo());

        TextView edad = (TextView) convertView.findViewById(R.id.weight);
        edad.setText(dir.getEdad());

        return convertView;
    }
}
