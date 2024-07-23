package yeomeong.common.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yeomeong.common.entity.jpa.Homeconsent;
import yeomeong.common.repository.jpa.HomeconsentRepository;

@Service
@RequiredArgsConstructor
public class HomeconsentService {

    private final HomeconsentRepository homeconsentRepository;

    public Homeconsent findById(Long homesentId) {
        return homeconsentRepository.findOne(homesentId);
    }

//    public Homeconsent findById(Long id) {
//
//    }
}
