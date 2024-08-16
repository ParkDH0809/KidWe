package yeomeong.common.entity;

import jakarta.persistence.*;

public class Image {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;
}
