package com.xytong.model.dto;

import lombok.Data;


/**
 * @author bszydxh
 */
@Data
public class ForumAddPostDTO {
    String module;
    String token;
    String timestamp;
    String username;
    String title;
    String text;
}
