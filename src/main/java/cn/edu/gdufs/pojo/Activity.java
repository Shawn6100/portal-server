package cn.edu.gdufs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Activity {

  private long id;
  private String title;
  private String content;
  private String path;
  private long priority;
  private String participant;
  private java.sql.Date time;
  private String href;

}
