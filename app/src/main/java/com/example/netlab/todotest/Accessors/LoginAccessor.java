package com.example.netlab.todotest.Accessors;

import com.example.netlab.todotest.LoginItem;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/login")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public interface LoginAccessor {
    @POST
    public abstract boolean login(LoginItem login) throws Exception;
}
