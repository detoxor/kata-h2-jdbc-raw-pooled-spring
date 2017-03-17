package cz.tc.learn.h2.dao;

import cz.tc.learn.h2.model.Person;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 * Data access object using raw JDBC data source API
 * 
 * @author tomas.cejka
 */
public class PersonDaoImpl implements PersonDao {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void insert(Person person) {
        String query = "insert into person (id, first_name, last_name) values (?,?,?)";
        try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setLong(1, person.getId());
            ps.setString(2, person.getFirstName());
            ps.setString(3, person.getLastName());
            int result = ps.executeUpdate();
            if (result != 0) {
                System.out.println("Person insert with id=" + person.getId());
            } else {
                System.out.println("Person insert failed with id=" + person.getId());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Problem insert person=" + person, e);
        }
    }

    @Override
    public void update(Person person) {
        String query = "update person set first_name=?, last_name=? where id=?";
        try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, person.getFirstName());
            ps.setString(2, person.getLastName());
            ps.setLong(3, person.getId());
            int result = ps.executeUpdate();
            if (result != 0) {
                System.out.println("Person update with id=" + person.getId());
            } else {
                System.out.println("Person update failed with id=" + person.getId());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Problem update person=" + person, e);
        }
    }

    @Override
    public Person find(Long idPerson) {
        String query = "select id, first_name, last_name from person where id = ?";
        try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(query);) {
            ps.setLong(1, idPerson);
            Person retval = null;
            try (ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    retval = new Person();
                    retval.setId(rs.getLong("id"));
                    retval.setFirstName(rs.getString("first_name"));
                    retval.setLastName(rs.getString("last_name"));
                    System.out.println("Person has been found::" + retval);
                } else {
                    System.out.println("No person has been found with id=" + idPerson);
                }
            }
            return retval;
        } catch (SQLException e) {
            throw new RuntimeException("Problem find person with id=" + idPerson, e);
        }
    }

    @Override
    public List<Person> findAll() {
        String query = "select id, first_name, last_name from person";
        try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(query);) {
            List<Person> retval = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    Person person = new Person();
                    person.setId(rs.getLong("id"));
                    person.setFirstName(rs.getString("first_name"));
                    person.setLastName(rs.getString("last_name"));
                    retval.add(person);
                }
            }
            return retval;
        } catch (SQLException e) {
            throw new RuntimeException("Problem find persons", e);
        }
    }

}
