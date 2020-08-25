package git.soulbgm.pojo;

import git.soulbgm.common.annotation.IdBuild;
import git.soulbgm.common.enums.IdType;
import lombok.Data;

import javax.persistence.Id;
import java.util.Date;

/**
 * @author SoulBGM
 * @version 1.0
 * @date 2020/6/18 23:56
 */
@Data
public class User {

    @Id
    @IdBuild(IdType.SEQUENCE)
    private Long id;

    private String name;

    private String address;

    private Integer age;

    private Date birthday;

}
