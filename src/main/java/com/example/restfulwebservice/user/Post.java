package com.example.restfulwebservice.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue
    private Integer id;

    private String description;

    // User : Post -> 1 : (0 ~ N), Main : Sub -> Parent : Child
    @ManyToOne(fetch = FetchType.LAZY) //지연로딩 : 사용자 entity 조회시 매번 로딩 하는게 아니라 Post 데이터 로딩시 user 데이터 가져옴
    @JsonIgnore
    private User user;

}
