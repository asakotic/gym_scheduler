package raf.microservice.scheduletraining.service.impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import raf.microservice.scheduletraining.domain.Gym;
import raf.microservice.scheduletraining.dto.GymDto;
import raf.microservice.scheduletraining.mapper.GymMapper;
import raf.microservice.scheduletraining.repository.GymRepository;
import raf.microservice.scheduletraining.service.GymService;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class GymServiceImpl implements GymService {
    private GymRepository gymRepository;
    private GymMapper gymMapper;


    @Override
    public GymDto add(GymDto gymDto) {
        Gym gym = gymMapper.gymDtoToGym(gymDto);
        gymRepository.save(gym);
        return gymMapper.gymToGymDto(gym);
    }

    @Override
    public GymDto updateById(Long gymId, GymDto gymDto) {
        Gym gym = gymMapper.gymDtoToGym(gymDto);
        gymRepository.save(gym);
        return gymMapper.gymToGymDto(gym);
    }

    @Override
    public GymDto findById(Long gymId) {
        return gymRepository.findById(gymId)
                .map(gymMapper::gymToGymDto)
                .orElseThrow(() ->
                        new RuntimeException(String.format
                                ("Gym with id: %d does not exists.", gymId)));
    //todo: promeniti obavezno exception ovaj
    }

    @Override
    public Page<GymDto> findAll(Pageable pageable) {
        return gymRepository.findAll(pageable).map(gymMapper::gymToGymDto);
    }

    @Override
    public void deleteById(Long gymId) {
        gymRepository.deleteById(gymId);
    }
}
