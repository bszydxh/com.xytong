package com.xytong.model.dto.comment;

import lombok.Data;

@Data
public class CommentAddRequestDTO {
    String module;
    String token;
    String cid;
    String timestamp;
    String username;
    String text;
}
