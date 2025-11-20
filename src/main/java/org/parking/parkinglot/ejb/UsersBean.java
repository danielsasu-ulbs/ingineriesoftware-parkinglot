package org.parking.parkinglot.ejb;

import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.parking.parkinglot.common.UserDto;
import org.parking.parkinglot.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class UsersBean {

    private static final Logger LOG = Logger.getLogger(UsersBean.class.getName());

    @PersistenceContext
    private EntityManager entityManager;

    public List<UserDto> findAllUsers() {
        LOG.info("findingAllUsers");
        try {
            TypedQuery typedQuery = entityManager.createQuery("SELECT u FROM User u", User.class);
            List<User> users = typedQuery.getResultList();
            return copyUsersToDto(users);
        } catch (Exception e) {
            throw new EJBException(e);
        }
    }

    public List<UserDto> copyUsersToDto(List<User> users) {
        List<UserDto> dtos = new ArrayList<UserDto>();
        for (User user : users) {
            UserDto userDto = new UserDto(user.getId(), user.getUsername(), user.getEmail(), user.getPassword());
            dtos.add(userDto);
        }

        return dtos;
    }
}
