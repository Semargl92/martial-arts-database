package by.semargl.controller.rest;

import by.semargl.controller.requests.MartialArtRequest;
import by.semargl.controller.requests.mappers.MartialArtMapper;
import by.semargl.domain.MartialArt;
import by.semargl.repository.MartialArtRepository;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/martial_art")
@RequiredArgsConstructor
public class MartialArtController {

    private final MartialArtRepository martialArtRepository;
    private final MartialArtMapper martialArtMapper;

    @ApiOperation(value = "find all martial arts")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Martial arts were successfully found")
    })
    @GetMapping("/all")
    public List<MartialArt> findAll() {
        return martialArtRepository.findAll();
    }

    @ApiOperation(value = "find one martial art")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "martialArtId", dataType = "string", paramType = "path",
                    value = "id of martial art for search", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Martial art was successfully found"),
            @ApiResponse(code = 500, message = "There is no martial art with such id")
    })
    @GetMapping("/{martialArtId}")
    public MartialArt findOne(@PathVariable("martialArtId") Long id) {
        return martialArtRepository.findById(id).orElseThrow();
    }

    @ApiOperation(value = "remove martial art from the database")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "martialArtId", dataType = "string", paramType = "path",
                    value = "id of martial art for deleting from database", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Martial art was successfully deleted"),
            @ApiResponse(code = 500, message = "There is no martial art with such id")
    })
    @DeleteMapping("/delete/{martialArtId}")
    public void deleteMartialArt(@PathVariable("martialArtId") Long id) {
       martialArtRepository.deleteById(id);
    }

    @ApiOperation(value = "create one martial art")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Martial art was successfully created")
    })
    @PostMapping("/create")
    public MartialArt createMartialArt(@RequestBody MartialArtRequest martialArtRequest) {
        MartialArt martialArt = new MartialArt();

        martialArtMapper.updateMartialArtFromMartialArtRequest(martialArtRequest, martialArt);

        return martialArtRepository.save(martialArt);
    }

    @ApiOperation(value = "update one martial art")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "martialArtId", dataType = "string", paramType = "path",
                    value = "id of martial art for update", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Martial art was successfully updated"),
            @ApiResponse(code = 500, message = "There is no martial art with such id")
    })
    @PutMapping("/update/{martialArtId}")
    public MartialArt updateMartialArt(@PathVariable("martialArtId") Long id, @RequestBody MartialArtRequest martialArtRequest) {
        MartialArt martialArt = martialArtRepository.findById(id).orElseThrow();

        martialArtMapper.updateMartialArtFromMartialArtRequest(martialArtRequest, martialArt);

        return martialArtRepository.save(martialArt);
    }
}
