package git.soulbgm.pojo;

import lombok.Data;

import javax.persistence.Id;

/**
 * @author SoulBGM
 * @version 1.0
 * @date 2020/6/18 23:56
 */
@Data
public class User {

    @Id
    private String id;

    private String name;

    private String address;

    private String age;

    private String birthday;
}
