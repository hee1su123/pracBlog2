package spring.pracblog2.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class FileDb {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @Lob
    @Column(nullable = false)
    private byte[] data;

    @OneToOne
    @JoinColumn
    private Post post;

    public void setPost(Post post) {
        this.post = post;
    }

    public FileDb(String name, String type, byte[] data, Post post) {
        this.name = name;
        this.type = type;
        this.data = data;
        this.post = post;
    }

    public void update(String name, String type, byte[] data, Post post) {
        this.name = name;
        this.type = type;
        this.data = data;
        this.post = post;
    }
}
