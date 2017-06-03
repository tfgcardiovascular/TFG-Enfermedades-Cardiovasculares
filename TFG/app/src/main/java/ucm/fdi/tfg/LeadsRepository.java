package ucm.fdi.tfg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Repositorio ficticio de Categorys
 */
public class LeadsRepository {
    
    private static LeadsRepository repository = new LeadsRepository();
    private HashMap<String, Medico> Categorys = new HashMap<>();
    private ArrayList <Medico> medicValidate = new ArrayList <>();

    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;

    public static LeadsRepository getInstance() {
        return repository;
    }

    public void saveMedicList( ArrayList <Medico> medicList )
    {
        medicValidate.clear();

        for ( int i = 0; i < medicList.size(); i++)
        {
            saveCategory( medicList.get( i ) );

        }

    }

    // Update repositories
    private LeadsRepository() {
        //saveCategory(new Category("1", "Ricardo Cajigas", "555555555", "ricardo@gmail.com"));
        //saveCategory(new Category( "2", "Angel Sanchez", "666666666", "angel@gmail.com"));

       // saveCategory( new Medico( "35346", "345436346", "s", "35", "fgfdg", "fgd",  "sdgasdgasad@gmail", "1" ) );

    }

    public void saveCategory(Medico Category)
    {
        medicValidate.add( Category );
       // Categorys.put(Category.getId(), Category);
    }

    // Get the fields of categories
    public List<Medico> getCategorys() {

        return medicValidate;
        //return new ArrayList<>(Categorys.values());
    }
}
