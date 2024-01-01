package com.venkat.vo;

import java.util.Date;

public record PostVO(Integer id, Integer userId, String title, String body, Date createdAt, Date modifiedAt) {
}
