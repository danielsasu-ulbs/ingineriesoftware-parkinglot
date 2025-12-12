package org.parking.parkinglot.ejb;

import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.parking.parkinglot.common.UserDto;
import org.parking.parkinglot.entities.User;
import org.parking.parkinglot.entities.UserGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class UsersBean {

    private static final Logger LOG = Logger.getLogger(UsersBean.class.getName());

    @Inject
    PasswordBean passwordBean;

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

    public void createUser(String username, String email, String password, List<String> user_groups) {
        LOG.info("createUser");

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordBean.convertToSha256(password));
        entityManager.persist(user);

        assignGroupsToUser(username, user_groups);
    }

    public void assignGroupsToUser(String username, List<String> user_groups) {
        for(String user_group : user_groups) {
            UserGroup userGroup = new UserGroup();
            userGroup.setUsername(username);
            userGroup.setUserGroup(user_group);
            entityManager.persist(userGroup);
        }
    }

    public Collection<String> findUsernameByUserIds(Collection<Long> userIds) {
        List<String> usernames =
                entityManager.createQuery("SELECT u.username FROM User u WHERE u.id IN :userIds", String.class)
                        .setParameter("userIds", userIds)
                        .getResultList();
        return usernames;
    }
}
