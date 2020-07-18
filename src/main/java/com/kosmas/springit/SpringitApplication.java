package com.kosmas.springit;

import com.kosmas.springit.config.SpringitProperties;
import com.kosmas.springit.domain.Comment;
import com.kosmas.springit.domain.Link;
import com.kosmas.springit.repository.CommentRepository;
import com.kosmas.springit.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties(SpringitProperties.class)
public class SpringitApplication {

    @Autowired
    private SpringitProperties springitProperties;

    public static void main(String[] args) {

        SpringApplication.run(SpringitApplication.class, args);
    }

    @Bean
    CommandLineRunner runner() {
        return args -> {
            System.out.println("WelcomeMessage: " + springitProperties.getWelcomeMessage());
        };
    };

    @Bean
    CommandLineRunner dbRunner(LinkRepository linkRepository, CommentRepository commentRepository) {
        return args -> {
            Link link = new Link("A new link","www.minime.org");
            linkRepository.save(link);

            Comment comment = new Comment("What a great resource", link);
            commentRepository.save(comment);
            link.addComment(comment);





        };

    }


}
