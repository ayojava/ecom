/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.ecom.facade;

import javax.ejb.Stateless;
import org.javasoft.ecom.entity.Comment;

/**
 *
 * @author ayojava
 */
@Stateless
public class CommentFacade extends AbstractFacade<Comment> {

    public CommentFacade() {
        super(Comment.class);
    }
    
    public Comment createComment(Comment commentObj){
        return getEntityManager().merge(commentObj);
    }
}
