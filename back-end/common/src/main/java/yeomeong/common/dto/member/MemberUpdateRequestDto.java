package yeomeong.common.dto.member;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class MemberUpdateRequestDto {

    Long id;
    String name;
    String tel;
    String password;
    MultipartFile picture;

}
