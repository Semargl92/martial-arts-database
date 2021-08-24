package by.semargl.service;

import java.sql.SQLException;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import by.semargl.requests.MartialArtRequest;
import by.semargl.requests.mappers.MartialArtMapper;
import by.semargl.domain.MartialArt;
import by.semargl.exception.NoSuchEntityException;
import by.semargl.repository.MartialArtRepository;


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

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    public void deleteMartialArt(Long id) {
        martialArtRepository.deleteById(id);
    }

    public MartialArt createMartialArt(MartialArtRequest martialArtRequest) {
        MartialArt martialArt = new MartialArt();

        martialArtMapper.updateMartialArtFromMartialArtRequest(martialArtRequest, martialArt);

        return martialArtRepository.save(martialArt);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    public MartialArt updateMartialArt(Long id, MartialArtRequest martialArtRequest) {
        MartialArt martialArt = martialArtRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException("Martial art not found by id " + id));

        martialArtMapper.updateMartialArtFromMartialArtRequest(martialArtRequest, martialArt);

        return martialArtRepository.save(martialArt);
    }

    public List<MartialArt> findMartialArtsByOrigin(String countryOfOrigin) {
        List<MartialArt> martialArts = martialArtRepository.findByOrigin(countryOfOrigin);
        if (martialArts.isEmpty()) {
            throw new NoSuchEntityException("There are no martial arts for this country");
        }
        return martialArts;
    }
}
