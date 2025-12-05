package org.parking.parkinglot.servlets;

import jakarta.annotation.security.DeclareRoles;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.parking.parkinglot.common.UserDto;
import org.parking.parkinglot.ejb.UsersBean;
import java.io.IOException;
import java.util.List;

@DeclareRoles({"READ_USERS", "WRITE_USERS"})
@ServletSecurity(value = @HttpConstraint(rolesAllowed = {"READ_USERS"}),
        httpMethodConstraints = {@HttpMethodConstraint(value = "POST", rolesAllowed = {"WRITE_USERS"})})

@WebServlet(name = "Users", value = "/Users")
public class Users extends HttpServlet {

    @Inject
    UsersBean usersBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        List<UserDto> users = usersBean.findAllUsers();
        request.setAttribute("users", users);
        request.getRequestDispatcher("/WEB-INF/pages/users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
    }
}
