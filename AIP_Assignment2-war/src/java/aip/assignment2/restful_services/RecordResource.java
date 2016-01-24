/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aip.assignment2.restful_services;

/**
 *
 * @author Zack Wang
 */
import aip.assignment2.domain.PostFacade;
import aip.assignment2.entity.Post;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.*;
import javax.ws.rs.*;

@Path("record")
public class RecordResource {

    @EJB
    private PostFacade postBean;

    //post service to read a list of posts created by a specific user and return it
    @POST
    public ArrayList<PostRecord> exportRecord(
            @FormParam("userid") String userid) {
        int i = Integer.parseInt(userid);
        try {
            //get a list of post created by specific user from database
            List<Post> postList = postBean.getPostByUserIdTest(i);
            ArrayList<PostRecord> recordList = new ArrayList<>();
            if (postList == null) {
                PostRecord postRecord = new PostRecord();
                postRecord.setContent("No record found");
                recordList.add(postRecord);
            } else {
                //store information into postRecord object
                for (Post p : postList) {
                    PostRecord postRecord = new PostRecord();
                    postRecord.setUsername(p.getUser().getUsername());
                    postRecord.setPosttype(p.getPosttype());
                    postRecord.setSubject(p.getSubject());
                    postRecord.setPostdate(p.getPostdate());
                    postRecord.setContent(p.getContent());
                    recordList.add(postRecord);
                }
            }
            return recordList;
        } catch (Exception e) {
            throw new WebApplicationException(404);
        }

    }
}
