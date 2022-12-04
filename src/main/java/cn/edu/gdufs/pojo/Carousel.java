package cn.edu.gdufs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Carousel {

  private long id;
  private String content;
  private String path;
  private long rank;
  private long createUser;
  private long updateUser;

}
