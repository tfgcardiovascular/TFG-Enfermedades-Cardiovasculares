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
        /*System.out.println( "ashes phoenix" );
        System.out.println( medicList );
        medicValidate = ( ArrayList < Paciente > ) medicList.clone();
        System.out.println( medicValidate );
        System.out.println( "boreal phoenix" );*/
    }

    // Update repositories
    private PacienteRepository() {
        //saveCategory(new Category("1", "Ricardo Cajigas", "555555555", "ricardo@gmail.com"));
        //saveCategory(new Category( "2", "Angel Sanchez", "666666666", "angel@gmail.com"));

       // saveCategory( new Paciente( "35346", "345436346", "s", "35", "fgfdg", "fgd",  "sdgasdgasad@gmail", "1" ) );

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
