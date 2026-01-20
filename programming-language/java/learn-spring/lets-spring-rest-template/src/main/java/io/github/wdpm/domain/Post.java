package io.github.wdpm.domain;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * @author evan
 * @date 2020/5/22
 */
public class Post implements Serializable {

    private Integer id;

    private Integer userId;

    private String title;

    private String body;

    public Post() {
    }

    public Post(Integer userId, String title, String body) {
        this.userId = userId;
        this.title = title;
        this.body = body;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Post.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("userId=" + userId)
                .add("title='" + title + "'")
                .add("body='" + body + "'")
                .toString();
    }
}
