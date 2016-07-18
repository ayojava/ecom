/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.ecom.rest.intf;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.javasoft.ecom.entity.Comment;

/**
 *
 * @author ayojava
 */
@Path("/comments")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public interface CommentResource {
    
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createComment(Comment commentObj);
}
