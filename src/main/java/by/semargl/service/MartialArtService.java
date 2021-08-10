package by.semargl.service;

import by.semargl.controller.requests.MartialArtRequest;
import by.semargl.controller.requests.mappers.MartialArtMapper;
import by.semargl.domain.MartialArt;
import by.semargl.exception.NoSuchEntityException;
import by.semargl.repository.MartialArtRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MartialArtService {

    private final MartialArtRepository martialArtRepository;
    private final MartialArtMapper martialArtMapper;

    public List<MartialArt> findAllMartialArt() {
        return martialArtRepository.findAll();
    }

    public MartialArt findOneMartialArt(Long id) {
        return martialArtRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException("Martial art not found by id " + id));
    }

    public void deleteMartialArt(Long id) {
        martialArtRepository.deleteById(id);
    }

    public MartialArt createMartialArt(MartialArtRequest martialArtRequest) {
        MartialArt martialArt = new MartialArt();

        martialArtMapper.updateMartialArtFromMartialArtRequest(martialArtRequest, martialArt);

        return martialArtRepository.save(martialArt);
    }

    public MartialArt updateMartialArt(Long id, MartialArtRequest martialArtRequest) {
        MartialArt martialArt = martialArtRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException("Martial art not found by id " + id));

        martialArtMapper.updateMartialArtFromMartialArtRequest(martialArtRequest, martialArt);

        return martialArtRepository.save(martialArt);
    }
}
