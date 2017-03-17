package cz.tc.learn.h2.dao;

import cz.tc.learn.h2.model.Person;
import java.util.List;

/**
 * @author tomas.cejka
 */
public interface PersonDao {
    /**
     * Insert given <code>person</code> entity
     * @param person domain which will be saved
     */
    void insert(Person person);
    
    /**
     * Update given <code>person</code> entity
     * @param person domain which will be modified
     */
    void update(Person person);
    
    /**
     * Find specific person entity identified by <code>idPerson</code>
     * @param idPerson identifier of person entity
     * @return null if not found otherwise person entity
     */
    Person find(Long idPerson);
    
    /**
     * @return find all person (no where condition) entities saved in database
     */
    List<Person> findAll();
}
