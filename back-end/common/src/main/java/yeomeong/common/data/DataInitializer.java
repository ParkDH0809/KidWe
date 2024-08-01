package yeomeong.common.data;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import yeomeong.common.entity.member.Kid;
import yeomeong.common.entity.member.gtype;
import yeomeong.common.repository.KidReposiory;

import java.util.Date;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

        @Autowired
        private KidReposiory kidRepository; // Kid 엔티티를 위한 JPA 레포지토리

        @Override
        public void run(String... args) {
            // 초기 데이터 생성

            kidRepository.save(
                Kid.builder()
                    .name("John Doe")
                    .birthday(new Date(2005, 1, 15))
                    .startAttendanceDate(new Date())
                    .picture("url_to_johns_picture")
                    .allergies("Peanuts")
                    .gender(gtype.MALE)
                    .tall(120.5)
                    .weight(30.0)
                    .isTake(true)
                    .build()
            );

            kidRepository.save(
                Kid.builder()
                    .name("Jane Doe")
                    .birthday(new Date(2006, 5, 20))
                    .startAttendanceDate(new Date())
                    .picture("url_to_jane_picture")
                    .allergies("None")
                    .gender(gtype.FEMALE)
                    .tall(115.5)
                    .weight(28.0)
                    .isTake(false)
                    .build()
            );

            log.info("[Data] 초기 데이터가 삽입되었습니다.");
        }
}

