package com.kosmas.springit.domain;

import com.kosmas.springit.domain.Auditable;
import com.kosmas.springit.domain.Comment;
import com.kosmas.springit.domain.Vote;
import com.kosmas.springit.service.BeanUtil;
import lombok.*;
import org.hibernate.validator.constraints.URL;
import org.ocpsoft.prettytime.PrettyTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@RequiredArgsConstructor
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Link extends Auditable implements Serializable {


    //Link(id=6, title=Build a Secure Progressive Web App With Spring Boot and React,
      //   url=https://dzone.com/articles/build-a-secure-progressive-web-app-with-spring-boo, votes=[], voteCount=0)


   // Link(id=6, title=Build a Secure Progressive Web App With Spring Boot and React,
     //    url=https://dzone.com/articles/build-a-secure-progressive-web-app-with-spring-boo,
            // comments=[Comment{id=7}, Comment{id=8}, Comment{id=9}], votes=[], voteCount=0)

  //  [Link(id=6, title=Build a Secure Progressive Web App With Spring Boot and React,
    //      url=https://dzone.com/articles/build-a-secure-progressive-web-app-with-spring-boo,
                  // comments=[Comment{id=7}, Comment{id=8}, Comment{id=9}], votes=[], voteCount=0)]
    @Id @GeneratedValue
    private Long id;

    @NonNull
    @NotEmpty(message = "Please enter a title.")
    private String title;

    @NonNull
    @NotEmpty(message = "Please enter a url.")
    @URL(message = "Please enter a valid url.")
    private String url;

    @OneToMany(mappedBy = "link")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "link")
    private List<Vote> votes = new ArrayList<>();

    private int voteCount = 0;

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public String getDomainName() throws URISyntaxException {
        URI uri = new URI(this.url);
        String domain = uri.getHost();
        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }

    public String getPrettyTime() {
        PrettyTime pt = BeanUtil.getBean(PrettyTime.class);
        return pt.format(convertToDateViaInstant(getCreationDate()));
    }

    private Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        return java.util.Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
    }

    @Override
    public String toString() {
        return  id.toString();
    }
}