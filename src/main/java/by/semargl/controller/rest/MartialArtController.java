package by.semargl.controller.rest;

import by.semargl.domain.MartialArt;
import by.semargl.repository.MartialArtRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/martial_art")
@RequiredArgsConstructor
public class MartialArtController {

    private final MartialArtRepository martialArtRepository;

    @GetMapping
    public List<MartialArt> findAll() {
        System.out.println("martial art controller");
        return martialArtRepository.findAll();
    }
}
