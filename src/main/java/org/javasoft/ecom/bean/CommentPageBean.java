/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.ecom.bean;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.javasoft.ecom.entity.Comment;
import static org.javasoft.ecom.intf.RestUrl.COMMENT_URL;
import org.omnifaces.util.Messages;

/**
 *
 * @author ayojava
 */
@Slf4j
@Named("commentPageBean")
@ViewScoped
public class CommentPageBean extends AbstractBean<Comment> implements Serializable{
    
    @Getter
    private String subFolder;
    
    @Getter @Setter
    private Comment comment;
    
    private Client client;
            
    @Override
    @PostConstruct
    public void init() {
        subFolder = "comment/";
    }
    
    @Override
    public void setPageResource(String pageResource) {
        if (StringUtils.equals(NEW_COMMENT, pageResource)) {
            comment = new Comment();
            super.setContentPath(appendFolderPath(subFolder, NEW_COMMENT));
        }
    }
    
    public void saveComment(){
        try {
            client = ClientBuilder.newClient();
            Response response = client.target(COMMENT_URL).request(MediaType.APPLICATION_JSON).post(Entity.json(comment));
            if (Response.Status.INTERNAL_SERVER_ERROR.getStatusCode() == response.getStatus()) {
                Messages.addGlobalError("An Error has occured, Comment Not saved");
            } else if (Response.Status.OK.getStatusCode() == response.getStatus()) {
                Messages.addGlobalInfo("Comment Successfully Saved");
            }
            response.close();
            cleanup();
            setPageResource(NEW_COMMENT);
        }catch (Exception ex) {
            log.error("Exception ::: ", ex);
            Messages.addGlobalError("An Error Has occured");
        }
    }
    
    @PreDestroy
    public void cleanup() {
        comment = null;
    }
}
