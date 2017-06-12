package sr.unasat.bewakingsbedrijf.ui;

import sr.unasat.bewakingsbedrijf.entities.Post;
import sr.unasat.bewakingsbedrijf.repositories.PostRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * Created by Patrice on 6/10/2017.
 */
public class UpdatePost extends JFrame {
    public UpdatePost (int id){

        //get specific post
        PostRepository postRepository = new PostRepository();
        Post post = postRepository.selectRecord(id);

        JFrame postUpdate = new JFrame("Locatie bewerken");

        // post component
        JLabel postLabel = new JLabel("Post");
        JTextField postInput = new JTextField(post.getLocatie());

        //button
        JButton submitButton = new JButton("Aanpassen");

        //position components
        postLabel.setBounds(100, 120, 120, 20);
        postInput.setBounds(180, 120, 120, 20);
        submitButton.setBounds(140, 145, 150, 20);

        //add components
        postUpdate.getContentPane().add(postLabel);
        postUpdate.getContentPane().add(postInput);
        postUpdate.getContentPane().add(submitButton);

        //set visible
        postUpdate.setBounds(100, 100, 450, 300);
        postUpdate.getContentPane().setLayout(null);
        postUpdate.setVisible(true);
        postUpdate.setTitle("Post Aanpassen");

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //insert new post
                Post newPost = new Post(id, postInput.getText());
                PostRepository postRepository = new PostRepository();
                postRepository.updateRecord(newPost);

                WindowEvent winClosingEvent = new WindowEvent(postUpdate, WindowEvent.WINDOW_CLOSING);
                Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
            }
        });
    }
}
