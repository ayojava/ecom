/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.ecom.rest.service;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.javasoft.ecom.entity.Comment;
import org.javasoft.ecom.facade.CommentFacade;
import org.javasoft.ecom.rest.intf.CommentResource;

/**
 *
 * @author ayojava
 */
@Slf4j
@Stateless
@LocalBean
public class CommentService implements CommentResource{

    @EJB
    private CommentFacade commentFacade;
    
    @Override
    public Response createComment(Comment commentObj) {
        try {
            commentFacade.createComment(commentObj);
        }catch (Exception ex) {
            log.error("An Exception has occured :: {}", ex);
            Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An Exception has occured").build();
        }
        return Response.status(Response.Status.OK).build();
    }
    
}
