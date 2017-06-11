package sr.unasat.bewakingsbedrijf.ui;

import sr.unasat.bewakingsbedrijf.entities.Post;
import sr.unasat.bewakingsbedrijf.repositories.PostRepository;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Patrice on 6/10/2017.
 */
public class InsertPost extends JFrame{
        public InsertPost(){
            super("Post");

            // init frame
            JFrame insertPost = new JFrame();

            // post component
            JLabel postLabel = new JLabel("Locatie");
            JTextField postInput = new JTextField();

            // button
            JButton submitButton = new JButton("Locatie toevoegen");

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

            submitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // insert new rooster
                    Post newPost = new Post(1, postInput.getText());
                    PostRepository postRepository = new PostRepository();
                    postRepository.insertRecord(newPost);

                }
            });
        }
    }
