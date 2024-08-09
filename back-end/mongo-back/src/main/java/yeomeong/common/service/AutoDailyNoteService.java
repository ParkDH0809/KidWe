package yeomeong.common.service;

import org.springframework.stereotype.Service;

@Service
public class AutoDailyNoteService {

    LocalDate today = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String date = today.format(formatter);
    System.out.println(date); // 출력: 2024-08-09 (예시)
}
