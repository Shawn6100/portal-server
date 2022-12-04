package cn.edu.gdufs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Blog {

  private long id;
  private String title;
  private String content;
  private String cover;
  private long createUser;
  private long updateUser;

}
