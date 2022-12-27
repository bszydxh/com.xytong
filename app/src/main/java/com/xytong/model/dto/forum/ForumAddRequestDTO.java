package com.xytong.model.dto.forum;

import lombok.Data;


/**
 * @author bszydxh
 */
@Data
public class ForumAddRequestDTO {
    String module;
    String token;
    String timestamp;
    String username;
    String title;
    String text;
}
