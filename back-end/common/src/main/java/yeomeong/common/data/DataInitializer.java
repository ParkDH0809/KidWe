package yeomeong.common.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import yeomeong.common.entity.member.Kid;
import yeomeong.common.entity.member.gtype;
import yeomeong.common.repository.KidReposiory;

import java.util.Arrays;
import java.util.Date;

@Component
public class DataInitializer implements CommandLineRunner {

        @Autowired
        private KidReposiory kidRepository; // Kid 엔티티를 위한 JPA 레포지토리

        @Override
        public void run(String... args) throws Exception {
            // 초기 데이터 생성
            Kid kid1 = new Kid();
            kid1.setName("John Doe");
            kid1.setBirthday(new Date(2005, 1, 15)); // 예시: 2005년 1월 15일
            kid1.setStartAttendanceDate(new Date());
            kid1.setPicture("url_to_johns_picture");
            kid1.setAllergies("Peanuts");
            kid1.setGender(gtype.MALE);
            kid1.setTall(120.5);
            kid1.setWeight(30.0);
            kid1.setTake(true);

            Kid kid2 = new Kid();
            kid2.setName("Jane Doe");
            kid2.setBirthday(new Date(2006, 5, 20)); // 예시: 2006년 5월 20일
            kid2.setStartAttendanceDate(new Date());
            kid2.setPicture("url_to_janes_picture");
            kid2.setAllergies("None");
            kid2.setGender(gtype.FEMALE);
            kid2.setTall(115.0);
            kid2.setWeight(28.0);
            kid2.setTake(false);

            // 레포지토리를 사용하여 데이터 저장
            kidRepository.saveAll(Arrays.asList(kid1, kid2));

            System.out.println("초기 데이터가 삽입되었습니다.");
        }
}

