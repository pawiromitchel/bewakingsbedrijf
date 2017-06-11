package sr.unasat.bewakingsbedrijf.ui;

import sr.unasat.bewakingsbedrijf.entities.Post;
import sr.unasat.bewakingsbedrijf.repositories.PostRepository;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Patrice on 6/10/2017.
 */
public class UpdatePost extends JFrame {
    public UpdatePost (int id){
        super ("Locatie aanpassen");

        //get specific post
        PostRepository postRepository = new PostRepository();
        Post post = postRepository.selectRecord(id);

        JFrame insertPost = new JFrame();

        // post component
        JLabel postLabel = new JLabel("post");
        JTextField postInput = new JTextField(post.getLocatie());

        //button
        JButton submitButton = new JButton("aanpassen");

        //position components
        postLabel.setBounds(100, 120, 120, 20);
        postInput.setBounds(180, 120, 120, 20);
        submitButton.setBounds(140, 145, 150, 20);

        //add components
        insertPost.getContentPane().add(postLabel);
        insertPost.getContentPane().add(postInput);
        insertPost.getContentPane().add(submitButton);

        //set visible
        insertPost.setBounds(100, 100, 450, 300);
        insertPost.getContentPane().setLayout(null);
        insertPost.setVisible(true);
        insertPost.setTitle("Post aanpassen");

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //insert new post
                Post newPost = new Post(id, postInput.getText());
                PostRepository postRepository = new PostRepository();
                postRepository.updateRecord(newPost);
            }
        });
    }
}
