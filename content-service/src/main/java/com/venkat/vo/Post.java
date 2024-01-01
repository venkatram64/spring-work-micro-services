package com.venkat.vo;

import java.util.Date;

public record Post(Integer id, Integer userId, String title, String body, Date createdAt, Date modifiedAt) {
}
