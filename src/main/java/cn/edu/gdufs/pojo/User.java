package cn.edu.gdufs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

  private long id;
  private String username;
  private String password;
  private String salt;
  private long level;
  private String nickname;
  private String email;

}
