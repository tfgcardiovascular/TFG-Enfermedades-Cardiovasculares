package ucm.fdi.tfg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Repositorio ficticio de Categorys
 */
public class PacienteRepository {
    
    private static PacienteRepository repository = new PacienteRepository();
    private HashMap<String, Paciente> Categorys = new HashMap<>();
    private ArrayList <Paciente> medicValidate = new ArrayList <>();

    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;

    public static PacienteRepository getInstance() {
        return repository;
    }

    public void saveMedicList( ArrayList <Paciente> medicList )
    {

        medicValidate.clear();

        for ( int i = 0; i < medicList.size(); i++)
        {
            saveCategory( medicList.get( i ) );
        }
    }

    // Update repositories
    private PacienteRepository() {

    }

    public void saveCategory(Paciente Category)
    {
        medicValidate.add( Category );
       // Categorys.put(Category.getId(), Category);
    }

    // Get the fields of categories
    public List<Paciente> getCategorys() {

        return medicValidate;
        //return new ArrayList<>(Categorys.values());
    }
}
