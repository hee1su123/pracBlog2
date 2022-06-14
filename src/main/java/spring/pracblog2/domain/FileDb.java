package spring.pracblog2.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class FileDb {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column
    private String name;

    @Column
    private String type;

    @Lob
    private byte[] data;

    @OneToOne(mappedBy = "fileDb")
    private Post post;

    public FileDb(String name, String type, byte[] data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }
}
